package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.Guidance;
import com.employment.employments.entity.Project;
import com.employment.employments.entity.User;
import com.employment.employments.mapper.ProjectMapper;
import com.employment.employments.mapper.RoleMapper;
import com.employment.employments.service.GuidanceService;
import com.employment.employments.service.ProjectService;
import com.employment.employments.service.UserService;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.Result;
import com.employment.employments.vo.GuidanceVo;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/api/guidance")
public class GuidanceController {
     @Autowired
    private GuidanceService guidanceService;
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

    /**
     * 修改
     * @param guidance
     * @return
     */
     @PutMapping
    public Result update(@RequestBody Guidance guidance,HttpServletRequest request){
//
         Integer integer= guidanceService.updateGuidance(guidance);
         if(integer>0)
             return Result.success().msg("指导信息修改成功");
         return Result.error().msg("指导信息修改失败");
     }

    /**
     * 添加
     * @param guidance
     * @return
     */
     @PostMapping("add")
    public Result add(@RequestBody Guidance guidance,HttpServletRequest request){
         String token = request.getHeader("token");
         if(Objects.isNull(token)){
             token=request.getParameter("token");
         }
         String tokenKey="token_"+token;
         String s = redisService.get(tokenKey);
         String usernameFromToken = jwtUtils.getUsernameFromToken(s);
         User user = userService.selectUserByUserName(usernameFromToken);
         Long aLong = roleMapper.selectRoleIdByUserId(Long.valueOf(user.getId()));
         Project projectById = projectService.findProjectById(guidance.getProjectId());
         projectById.setProjectStatus("指导中");
         log.info(String.valueOf(projectById));
         projectService.updateProjectStatus(projectById);
         Guidance byId = guidanceService.findById(String.valueOf(projectById.getId()));
         if(byId!=null){
             guidanceService.delete(byId.getId());
         }
         Integer integer= guidanceService.add(guidance);
         roleMapper.saveRoleGuidance(aLong,guidance.getId());
//         projectService.delete(projectById.getId());
         if(integer>0)
             return Result.success().msg("指导项目添加成功");
         return Result.error().msg("指导项目添加失败");
     }
     @PostMapping("list")
    public Result findGuidanceById(@RequestBody GuidanceVo guidanceVo,HttpServletRequest request){
         String token = request.getHeader("token");
         if(Objects.isNull(token)){
             token=request.getParameter("token");
         }
         String tokenKey="token_"+token;
         String s = redisService.get(tokenKey);
         String usernameFromToken = jwtUtils.getUsernameFromToken(s);
         User user = userService.selectUserByUserName(usernameFromToken);
         guidanceVo.setUserId(Long.valueOf(user.getId()));
//         IPage<Guidance>page=new Page<>(guidanceVo.getCurrent(),guidanceVo.getLimit());
         Page<GuidanceVo>guidancePage= guidanceService.findGuidanceById(guidanceVo);
         return Result.success(guidancePage);
     }
     @DeleteMapping("infos/{projectId}")
    public Result findGuidanceByProjectId(@PathVariable Long projectId){
//         String token = request.getHeader("token");
//         if(Objects.isNull(token)){
//             token=request.getParameter("token");
//         }
//         String tokenKey="token_"+token;
//         String s = redisService.get(tokenKey);
//         String usernameFromToken = jwtUtils.getUsernameFromToken(s);
//         User user = userService.selectUserByUserName(usernameFromToken);
         Guidance guidanceInfo=guidanceService.findById(String.valueOf(projectId));
         if(Objects.isNull(guidanceInfo))
             return Result.error();
         return Result.success(guidanceInfo);
     }
     @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
         Integer integer=guidanceService.delete(id);
         if(integer>0)
             return Result.success().msg("指导删除成功");
         return Result.error().msg("指导删除失败");
     }
     @GetMapping("select/{id}")
    public Result guidance(@PathVariable Long id){
         Guidance byId = guidanceService.getById(id);
         Project projectById = projectService.findProjectById(byId.getProjectId());
         projectById.setProjectStatus("指导通过");
         projectService.updateProjectStatus(projectById);
         return Result.success();
     }
}

