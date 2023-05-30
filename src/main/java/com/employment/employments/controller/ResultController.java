package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.Project;
import com.employment.employments.entity.User;
import com.employment.employments.mapper.ResultMapper;
import com.employment.employments.mapper.RoleMapper;
import com.employment.employments.service.ProjectService;
import com.employment.employments.service.ResultService;
import com.employment.employments.service.UserService;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.Result;
import com.employment.employments.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shao
 * @since 2023-02-25
 */
@RestController
@RequestMapping("/api/result")
public class ResultController {
     @Autowired
    private ResultService resultService;
     @Autowired
     private RedisService redisService;
     @Autowired
     private JwtUtils jwtUtils;
     @Autowired
     private UserService userService;
     @Autowired
     private RoleMapper roleMapper;
     @Autowired
     private ProjectService projectService;
     @Autowired
     private ResultMapper resultMapper;
     //添加
     @PostMapping("add")
    public Result add(@RequestBody com.employment.employments.entity.Result result,HttpServletRequest request){
         String token = request.getHeader("token");
         if(Objects.isNull(token)){
             token=request.getParameter("token");
         }
         String tokenKey="token_"+token;
         String s = redisService.get(tokenKey);
         String usernameFromToken = jwtUtils.getUsernameFromToken(s);
         User user = userService.selectUserByUserName(usernameFromToken);
         Long aLong = roleMapper.selectRoleIdByUserId(Long.valueOf(user.getId()));
         Project projectById = projectService.findProjectById(result.getProjectId());
         projectById.setProjectStatus("评审中");
         projectService.updateProjectStatus(projectById);
         com.employment.employments.entity.Result resultByProjectId = resultService.findResultByProjectId(result.getProjectId());
         if(resultByProjectId!=null){
             resultService.delete(resultByProjectId.getId());
             resultMapper.deleteRoleResult(resultByProjectId.getId());
         }
         Integer integer=resultService.add(result);

         roleMapper.saveRoleResult(aLong,result.getId());
//         projectService.delete(projectById.getId());
         if(integer>0)
             return Result.success().msg("评审添加成功");
         return Result.error().msg("评审添加失败");
     }
    //修改内容
    @PutMapping("update")
    public Result update(@RequestBody com.employment.employments.entity.Result result){
         Integer integer=resultService.updateResult(result);
         if(integer>0)
             return Result.success().msg("评审修改成功");
         return Result.error().msg("评审修改失败");
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
         Integer integer=resultService.delete(id);
         if(integer>0)
             return Result.success().msg("评审删除成功");
         return Result.error().msg("评审删除失败");
    }
    //查询
    @PostMapping("list")
    public Result pageList(@RequestBody ResultVo resultVo, HttpServletRequest request){
        String token = request.getHeader("token");
        if(Objects.isNull(token)){
            token=request.getParameter("token");
        }
        String tokenKey="token_"+token;
        String s = redisService.get(tokenKey);
        String usernameFromToken = jwtUtils.getUsernameFromToken(s);
        User user = userService.selectUserByUserName(usernameFromToken);
        resultVo.setUserId(Long.valueOf(user.getId()));

        IPage<ResultVo> page=resultService.findResultById(resultVo);
        return Result.success(page);
    }
    //根据项目名查询
    @PostMapping("name")
    public Result ListByName(@RequestBody ResultVo resultVo, HttpServletRequest request){
        String token = request.getHeader("token");
        if(Objects.isNull(token)){
            token=request.getParameter("token");
        }
        String tokenKey="token_"+token;
        String s = redisService.get(tokenKey);
        String usernameFromToken = jwtUtils.getUsernameFromToken(s);
        User user = userService.selectUserByUserName(usernameFromToken);
        resultVo.setUserId(Long.valueOf(user.getId()));
        Page<ResultVo>page=resultService.findResultByName(resultVo);
        return Result.success(page);
    }
    @GetMapping("select/{id}")
    public Result selectById(@PathVariable Long id){
        com.employment.employments.entity.Result byId = resultService.getById(id);
        Project projectById = projectService.findProjectById(byId.getProjectId());
        projectById.setProjectStatus("评审通过");
        projectService.updateProjectStatus(projectById);
        return Result.success();
    }
    @GetMapping("select1/{id}")
    public Result select1ById(@PathVariable Long id){
        com.employment.employments.entity.Result byId = resultService.getById(id);
        Project projectById = projectService.findProjectById(byId.getProjectId());
        projectById.setProjectStatus("评审不通过");
        projectService.updateProjectStatus(projectById);
        return Result.success();
    }
}

