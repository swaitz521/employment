package com.employment.employments.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.Biographical;
import com.employment.employments.entity.Delivery;
import com.employment.employments.entity.User;
import com.employment.employments.service.DeliveryService;
import com.employment.employments.service.UserService;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.Result;
import com.employment.employments.vo.DeliveryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    @DeleteMapping("add/{id}")
    public Result delivery(@PathVariable("id") Long empId,
                            HttpServletRequest request)
    {
        String token = request.getHeader("token");
        if(Objects.isNull(token)){
            token=request.getParameter("token");
        }
        String tokenKey="token_"+token;
        String s = redisService.get(tokenKey);
        String usernameFromToken = jwtUtils.getUsernameFromToken(s);
        User user = userService.selectUserByUserName(usernameFromToken);
        return   deliveryService.deliveryAdd(empId, Long.valueOf(user.getId()));
    }

    @PostMapping("page")
    public Result page(@RequestBody DeliveryVo delivery){
        Page<Delivery> deliveryPage=new Page<>(delivery.getCurrent(),delivery.getLimit());
        Page<Delivery>list= deliveryService.page(deliveryPage,delivery);
        return Result.success(list);
    }
    //根据用户Id查询出投递的简历
    @PostMapping("list")
    public Result selectByUserId(@RequestBody Delivery delivery,HttpServletRequest request){
        String token = request.getHeader("token");
        if(Objects.isNull(token)){
            token=request.getParameter("token");
        }
        String tokenKey="token_"+token;
        String s = redisService.get(tokenKey);
        String usernameFromToken = jwtUtils.getUsernameFromToken(s);
        User user = userService.selectUserByUserName(usernameFromToken);
        delivery.setUserId(Long.valueOf(user.getId()));
        Page<Delivery> deliveryPage=new Page<>(1,10);
        Page<Delivery>list= deliveryService.selectByUserId(deliveryPage,delivery);
        return Result.success(list);
    }
    @PostMapping
    public Result add(@RequestBody Delivery delivery){
        Integer integer=deliveryService.add(delivery);
        if(integer>0)
            return Result.success("添加成功");
        return Result.error();
    }
    @PutMapping
    public Result update(@RequestBody Delivery delivery){
        Integer integer=deliveryService.updateInfo(delivery);
        if(integer>0)
            return Result.success("修改成功");
        return Result.error();
    }
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        Integer integer=deliveryService.delete(id);
        if(integer>0)
            return Result.success("删除成功");
        return Result.error();
    }
    @GetMapping("count")
    public Result number(){
        int count = deliveryService.count();
        return Result.success(count);
    }
    @GetMapping("number")
    public Result count(){
        List<Delivery>deliveries=deliveryService.number1();
        return Result.success(deliveries);
    }
    //按周查询
    @GetMapping("week")
    public Result week(){
        List<Delivery>deliveries=deliveryService.week();
        return Result.success(deliveries);
    }

}
