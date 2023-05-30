package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.controller.ex.FileEmptyException;
import com.employment.employments.controller.ex.FileSizeException;
import com.employment.employments.controller.ex.FileTypeException;
import com.employment.employments.entity.Biographical;
import com.employment.employments.entity.Role;
import com.employment.employments.entity.User;
import com.employment.employments.mapper.UserMapper;
import com.employment.employments.service.BiographicalService;
import com.employment.employments.service.RoleService;
import com.employment.employments.service.UserService;
import com.employment.employments.service.ex.UserNotFoundException;
import com.employment.employments.service.ex.UsernameDuplicateException;
import com.employment.employments.util.Result;
import com.employment.employments.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@RestController
@RequestMapping("/api/user")

public class UserController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private BiographicalService biographicalService;
    @Autowired
    private UserMapper userMapper;
    /**
     * 查询用户信息
     * @return
     */
    @GetMapping("select/{id}")
     public Result<User> selectUserByUserId(@PathVariable Long id){
         User user= userService.selectUserByUserId(id);
         return Result.success(user);
     }

     @PostMapping("list")
     public Result selectUserList(@RequestBody UserVo userVo){
        IPage<User>page=new Page<>(userVo.getCurrent(),userVo.getLimit());
         IPage<User> list = userService.findUserListByPage(page, userVo);
         return Result.success(list);
     }
    /**
     * 添加用户信息
     * @param user
     * @return
     */
     @PostMapping

     public Result add(@RequestBody UserVo user){
         //加密
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Integer i= userService.add(user);
        if(i>0)
        return Result.success().msg("添加成功");
        return Result.error().msg("添加失败");
     }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
     @PutMapping
    public Result update(@RequestBody UserVo user){
        Integer b= userService.updateUser(user);
        if(b>0)
            return Result.success().msg("修改成功");
        return Result.success().msg("修改失败");
     }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
     @DeleteMapping("{id}")
    public Result deleteUser(@PathVariable String id){
         boolean b = userService.removeById(id);
         if(b)
        return Result.success().msg("删除成功");
         return Result.success().msg("删除失败");
     }

    /**
     * 登录
     * @return
     */
     @PostMapping("login")
    public Result login(@RequestBody UserParams userParams){
         User user = userService.selectUserByUserName(userParams.getUsername());
         if(user==null){
             throw new UserNotFoundException("用户不存在");
         }
         boolean login = userService.login(userParams);
         if(login)
             return Result.success().msg("登录成功");
         return Result.error();
     }

    /**
     * 修改密码
     * @param user
     * @return
     */
     @PutMapping("password")
    public Result restPassword(@RequestBody User user){
         BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
         //加密
         user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
         userService.restPassword(user);
         return Result.success();
     }

    /**
     * 注册
     * @param user
     * @return
     */
     @PostMapping("register")
    public Result register(@RequestBody User user){
         User user1 = userService.selectUserByUserName(user.getUsername());
         if(user1.getUsername()!=null){
             throw new UsernameDuplicateException("该用户已经被注册");
         }
         userService.register(user);

         return Result.success().msg("注册成功");
     }
     private static final int AVATAR_MAX_SIZE=10*1024*1024;
     private static final List<String>AVATAR_TYPE=new ArrayList<>();
     static {
         AVATAR_TYPE.add("image/jpg");
         AVATAR_TYPE.add("image/png");
         AVATAR_TYPE.add("image/gif");
         AVATAR_TYPE.add("image/jpeg");
     }
     @PostMapping("/avator/update")
    public Result updateAvatar(@RequestParam("file")MultipartFile file,@RequestParam("id")Long id){
         if(file.isEmpty()){
           throw new FileEmptyException("上传的文件不允许为空");
         }
         if(file.getSize()>AVATAR_MAX_SIZE){
           throw new FileSizeException("不允许上传超过\" + (AVATAR_MAX_SIZE / 1024) + \"KB的头像文件");
         }
         String contentType = file.getContentType();
         if(!AVATAR_TYPE.contains(contentType)){
             throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：\" "+AVATAR_TYPE);
         }
         String originalFilename = file.getOriginalFilename();
         String fileName= UUID.randomUUID().toString()+StringUtils.substringAfterLast(originalFilename,",");
         String avatar="/upload/"+fileName;
         User user=new User();
         user.setId((String.valueOf(id)));
         user.setAvater(avatar);
         userService.updateAvatar(user);
         return Result.success().msg("图片修改成功");
     }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
     @PostMapping("logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response){
         String token = request.getHeader("token");
         if(Objects.isNull(token)){
             token=request.getParameter("token");
         }
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if(authentication!=null){
             //清空用户信息
             new SecurityContextLogoutHandler().logout(request,response,authentication);
             String key="token_"+token;
             redisService.del(key);
         }
         return Result.success().msg("用户退出登录成功");
     }

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
     @PostMapping("select/{username}")
    public Result selectUserByUserName(@PathVariable("username") String username){
         User user = userService.selectUserByUserName(username);
         if(user==null){
             return Result.error().msg("用户不存在");
         }
         return Result.success(user);
     }
     @GetMapping("count")
    public Result number(){
         List<User>userList=userService.number();
         return Result.success(userList);
     }
     @GetMapping("number")
    public Result count(){
         int count = userService.count();
         return Result.success(count);
     }
     @PostMapping("student")
    public Result selectByRoleId(@RequestBody Student student){
         Page<Student>page=userService.selectByRoleId(student);
         return Result.success(page);
     }
     @PutMapping("update")
    public Result updateStudent(@RequestBody Student student){
         userService.updateStudent(student);
         return Result.success();
     }
     @DeleteMapping("delete/{id}")
    public Result deleteStudent(@PathVariable Long id){
         userService.deleteStudent(id);
         return Result.success();
     }
//     @PostMapping("search/{username}")
//    public Result addStudent(@PathVariable String username){
//         Student student=userService.selectStudentByUserName(username);
//         return Result.success(student);
//     }
    //所选用户的角色数据
    @DeleteMapping("getRoleIdByUserId/{userId}")
     public Result getRoleIdByUserId(@PathVariable Long userId){
    List<Long> roleIdByUserId = roleService.findRoleIdByUserId(userId);
    return Result.success(roleIdByUserId);
}
    @PostMapping("getRoleListForAssign")
    public Result getRoleListForAssign(RoleVo roleVo){
         Page<Role>page=new Page<>(roleVo.getCurrent(),roleVo.getLimit());
         roleService.selectRoleListByUserId(page,roleVo);
         return Result.success(page);
    }
    @PostMapping("saveUserRole")
    public Result saveUserRole(@RequestBody UserRoleDto userRoleDto){
         if(userService.saveUserRole(userRoleDto.getUserId(),userRoleDto.getRoleIds())){
             return Result.success().msg("角色分配成功");
         }
         return Result.error().msg("角色分配失败");
    }

}

