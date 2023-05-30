package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.Biographical;
import com.employment.employments.entity.Delivery;
import com.employment.employments.entity.User;
import com.employment.employments.service.BiographicalService;
import com.employment.employments.service.DeliveryService;
import com.employment.employments.service.UserService;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.Result;
import com.employment.employments.vo.BiographicalVo;
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
 * @since 2023-02-18
 */
@RestController
@RequestMapping("/api/biographical")
public class BiographicalController {
        @Autowired
        private RedisService redisService;
        @Autowired
        private JwtUtils jwtUtils;
        @Autowired
        private UserService userService;
        @Autowired
        private DeliveryService deliveryService;
        @Autowired
        private BiographicalService biographicalService;
        @GetMapping("list")
        public Result biographicalList(){
            List<Biographical>biographicalList=biographicalService.biographicalList();
            return Result.success(biographicalList);
        }
        @GetMapping("/{id}")
    public Result  selectBiographicalById(@PathVariable Long id){
            Biographical biographical=biographicalService.selectBiographicalById(id);
            return Result.success(biographical);
        }
        @PostMapping
    public Result add(@RequestBody Biographical biographical){
            Integer integer=biographicalService.add(biographical);
            if(integer>0)
                return Result.success().msg("简历添加成功");
            return Result.error();
        }
        @PutMapping
    public Result update(@RequestBody Biographical biographical){
            Integer integer=biographicalService.updateBiographical(biographical);
            if(integer>0)
                return Result.success().msg("简历修改成功");
            return Result.error();
        }
        @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
            Integer integer=biographicalService.delete(id);
            if(integer>0)
                return Result.success();
            return Result.error();
        }
        @PostMapping("page")
    public Result page(@RequestBody BiographicalVo biographicalVo,HttpServletRequest request){
//            String token = request.getHeader("token");
//            if(Objects.isNull(token)){
//                //如果请求头中无token信息，从参数中获取token信息
//                token= request.getParameter("token");
//            }
//            String tokenKey="token_"+token;
//            String s=redisService.get(tokenKey);
//            String username = jwtUtils.getUsernameFromToken(s);
//            User user = userService.selectUserByUserName(username);
//            biographicalVo.setUsername(username);
            Page<Biographical>page=new Page<>(biographicalVo.getCurrent(),biographicalVo.getLimit());
            IPage<Biographical> biographicalIPage = biographicalService.biographicalPage(page, biographicalVo);
//            List<Biographical> records = biographicalIPage.getRecords();
//            List<BiographicalVo>biographicalVoList=new ArrayList<>();
//            Optional.ofNullable(records).orElse(new ArrayList<>())
//                    .stream().filter(Objects::nonNull)
//                    .forEach(item->{
//                        BiographicalVo biographicalVo1=new BiographicalVo();
////                        biographicalVo.setUsername(username);
//                        biographicalVo1.setPhone(user.getPhone());
//                        BeanUtils.copyProperties(item,biographicalVo1);
//                        biographicalVoList.add(biographicalVo1);
//                    });
            return Result.success(biographicalIPage);
        }
       @GetMapping("bio")
    public Result getBio(HttpServletRequest request){
           String token = request.getHeader("token");
           if(Objects.isNull(token)){
               token=request.getParameter("token");
           }
           String tokenKey="token_"+token;
           String s = redisService.get(tokenKey);
           String usernameFromToken = jwtUtils.getUsernameFromToken(s);
           User user = userService.selectUserByUserName(usernameFromToken);
           Biographical biographical= biographicalService.selectBioByUserId(String.valueOf(user.getId()));
           return Result.success(biographical);
       }
       @GetMapping("/select/{name}")
    public Result selectByName(@PathVariable String name){
           QueryWrapper<Biographical>biographicalQueryWrapper=new QueryWrapper<>();
           biographicalQueryWrapper.eq("name",name);
           Biographical one = biographicalService.getOne(biographicalQueryWrapper);
           Delivery delivery = deliveryService.selectByName(name);
           delivery.setStatus("1");
           deliveryService.updateInfo(delivery);
           return Result.success(one);
       }
}

