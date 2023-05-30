package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.Role;
import com.employment.employments.entity.User;
import com.employment.employments.service.MenuService;
import com.employment.employments.service.RoleService;
import com.employment.employments.service.UserService;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.Result;
import com.employment.employments.vo.RoleMenuDto;
import com.employment.employments.vo.RoleMenuVo;
import com.employment.employments.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @GetMapping("/{roleId}")
    public Result selectRoleByRoleId(@PathVariable Long roleId){
        Role role= roleService.selectRoleByRoleId(roleId);
        return Result.success(role);
    }
    @PostMapping("list")
    public Result selectRoleList(@RequestBody RoleVo roleVo, HttpServletRequest request){
        String token = request.getHeader("token");
        if(Objects.isNull(token)){
            token=request.getParameter("token");
        }
        String tokenKey="token_"+token;
        String s = redisService.get(tokenKey);
        String usernameFromToken = jwtUtils.getUsernameFromToken(s);
        User user = userService.selectUserByUserName(usernameFromToken);
        roleVo.setUserId(Long.valueOf(user.getId()));
        IPage<Role> page=new Page<>(roleVo.getCurrent(),roleVo.getLimit());
         roleService.selectRoleListByUserId(page,roleVo);
        return Result.success(page);
    }
    @PostMapping
    public Result add(@RequestBody Role role){
        Integer i= roleService.add(role);
        if(i>0)
        return Result.success().msg("角色添加成功");
        return Result.error();
    }
    @PutMapping
    public Result update(@RequestBody Role role){
        Integer i=roleService.updateRole(role);
        return Result.success().msg("角色修改成功");
    }
    @DeleteMapping("/{roleId}")
    public Result deleteRole(@PathVariable Long roleId){
        Integer i=roleService.deleteRole(roleId);
        return Result.success().msg("角色删除成功");
    }
    @GetMapping("getRoleMenuTree")
    public Result getRoleMenuTree(Long roleId,Long userId){
        RoleMenuVo menuTree = menuService.findMenuTree(roleId, userId);
       return Result.success(menuTree);
    }
    @PostMapping("saveRoleMenu")
    public Result saveRoleMenu(@RequestBody RoleMenuDto roleMenuDto){
        boolean b = roleService.saveRoleMenu(roleMenuDto.getRoleId(), roleMenuDto.getMenuIds());
        if(b)
        return Result.success().msg("权限分配成功");
        return Result.error().msg("权限分配失败");
    }

}

