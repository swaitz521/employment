package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.Guidance;
import com.employment.employments.entity.Project;
import com.employment.employments.entity.User;
import com.employment.employments.mapper.GuidanceMapper;
import com.employment.employments.mapper.ProjectMapper;
import com.employment.employments.service.GuidanceService;
import com.employment.employments.service.ProjectService;
import com.employment.employments.service.UserService;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.Result;
import com.employment.employments.vo.GuidanceVo;
import com.employment.employments.vo.ProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shao
 * @since 2023-02-25
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController {
   @Autowired
    private ProjectService projectService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
   @Autowired
   private ProjectMapper projectMapper;
   @PostMapping("list")
    public Result page(@RequestBody ProjectVo projectVo, HttpServletRequest request){
       Page<Project>list=new Page<>();
       String token = request.getHeader("token");
       if(Objects.isNull(token)){
           token=request.getParameter("token");
       }
       String tokenKey="token_"+token;
       String s = redisService.get(tokenKey);
       String usernameFromToken = jwtUtils.getUsernameFromToken(s);
       User user = userService.selectUserByUserName(usernameFromToken);
//       projectVo.setUserId(Long.valueOf(user.getId()));
//       List<Project> projectListByUserId = projectMapper.findProjectListByUserId(Long.valueOf(user.getId()));
       List<Project> list1 = projectService.list();
       if(user.getRole()==0||user.getRole()==2){
           Page<Project>page=new Page<>(projectVo.getCurrent(),projectVo.getLimit());
          list= projectService.findProjectListByPage(page,projectVo);
       }
       List<Project>list3=new ArrayList<>();
       if(user.getRole()==3){
           Optional.ofNullable(list1).orElse(new ArrayList<>()).stream().filter(Objects::nonNull)
                   .forEach(item->{
                       if(item.getProjectStatus().equals("等待指导")||item.getProjectStatus().equals("指导中")||item.getProjectStatus().equals("等待评审")){
                           Project project=new Project();
                           BeanUtils.copyProperties(item,project);
                           list3.add(project);
                       }
                   });
           Page<Project>page=new Page<>(projectVo.getCurrent(),projectVo.getLimit());
           list= projectService.findProjectListByPage(page,projectVo);
           list.setRecords(list3);
       }
       List<Project>list2=new ArrayList<>();
       if(user.getRole()==4){
           Optional.ofNullable(list1).orElse(new ArrayList<>()).stream().filter(Objects::nonNull)
                   .forEach(item->{
                       if(item.getProjectStatus().equals("等待评审")||item.getProjectStatus().equals("评审中")||item.getProjectStatus().equals("评审通过")||item.getProjectStatus().equals("评审不通过")){
                           Project project=new Project();
                           BeanUtils.copyProperties(item,project);
                           list2.add(project);
                       }
                   });
           Page<Project>page=new Page<>(projectVo.getCurrent(),projectVo.getLimit());
           list= projectService.findProjectListByPage(page,projectVo);
           list.setRecords(list2);
       }

//       list.setRecords(projectListByUserId);
       return Result.success(list);
   }
   @DeleteMapping("select/{id}")
    public Result findProjectById(@PathVariable String id){
       Project project= projectService.findProjectById(Long.valueOf(id));
       return Result.success(project);
   }
   @PostMapping
    public Result add(@RequestBody Project project,HttpServletRequest request){
       String token = request.getHeader("token");
       if(Objects.isNull(token)){
           token=request.getParameter("token");
       }
       String tokenKey="token_"+token;
       String s = redisService.get(tokenKey);
       String usernameFromToken = jwtUtils.getUsernameFromToken(s);
       User user = userService.selectUserByUserName(usernameFromToken);
       Integer integer=projectService.add(project);
       projectMapper.saveUserProject(Long.valueOf(user.getId()),project.getId());
       if(integer>0)
           return Result.success().msg("项目添加成功");
       return Result.error().msg("项目添加失败");
   }
   @PutMapping
    public Result update(@RequestBody ProjectVo projectVo){
       Integer integer=projectService.updateProject(projectVo);
       if(integer>0)
           return Result.success().msg("项目修改成功");
       return Result.error().msg("项目修改失败");
   }
   @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
       Integer integer=projectService.delete(id);
       if(integer>0)
           return Result.success().msg("项目删除成功");
       return Result.error().msg("项目删除失败");
   }
   @GetMapping("count")
    public Result count(){
       int count = projectService.count();
       return Result.success(count);
   }
   @GetMapping("number")
    public Result number(){
       List<Project>projects=projectService.number();
       return Result.success(projects.toArray());
   }
   @GetMapping("week")
    public Result week(){
       List<Project>projects=projectService.week();
       return Result.success(projects);
   }
   @GetMapping("list1")
    public Result projectList(){
       List<Project> list = projectService.list();
       return Result.success(list.toArray());
   }
//   @GetMapping("select/{id}")
//    public Result project(@PathVariable Long id){
//       Project byId = projectService.getById(id);
//       return Result.success(byId);
//   }
   @GetMapping("select")
    public Result projectByuserId(HttpServletRequest request){
       String token = request.getHeader("token");
       if(Objects.isNull(token)){
           token=request.getParameter("token");
       }
       String tokenKey="token_"+token;
       String s = redisService.get(tokenKey);
       String usernameFromToken = jwtUtils.getUsernameFromToken(s);
       User user = userService.selectUserByUserName(usernameFromToken);
       Project project = projectService.getById(user.getId());
       return Result.success(project);
   }
}

