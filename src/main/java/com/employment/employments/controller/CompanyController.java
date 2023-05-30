package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.Company;
import com.employment.employments.entity.Delivery;
import com.employment.employments.entity.Post;
import com.employment.employments.entity.User;
import com.employment.employments.service.CompanyService;
import com.employment.employments.service.DeliveryService;
import com.employment.employments.service.UserService;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.Result;
import com.employment.employments.vo.CompanyVo;
import com.employment.employments.vo.DeliveryVo;
import javafx.geometry.Pos;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@RequestMapping("/api/company")
public class CompanyController {
      @Autowired
      private CompanyService companyService;
      @Autowired
      private RedisService redisService;
      @Autowired
      private JwtUtils jwtUtils;
      @Autowired
      private UserService userService;
      @Autowired
      private DeliveryService deliveryService;
//      @GetMapping("list")
//      public Result companyList(){
//            List<Company>companyList= companyService.selectCompanyList();
//            return Result.success(companyList);
//      }
      @GetMapping("/{id}")
      public Result selectCompanyById(@PathVariable Long id){
            Company company=companyService.selectCompanyById(id);
            return Result.success(company);
      }
      @PostMapping
      public Result add(@RequestBody CompanyVo company){
            Integer integer=companyService.add(company);
            if(integer>0)
                  return Result.success().msg("公司信息添加成功");
            return Result.error();
      }
      @PutMapping
      public Result update(@RequestBody CompanyVo company){
            Integer integer=companyService.updateCompany(company);
            if(integer>0)
                  return Result.success().msg("公司信息修改成功");
            return Result.error();
      }
      @DeleteMapping("/{id}")
      public Result delete(@PathVariable Long id){
            Integer integer=companyService.delete(id);
            if(integer>0)
                  return Result.success();
            return Result.error();
      }

      @PostMapping("list")
      public Result page(@RequestBody(required = false) CompanyVo companyVo){
            Page<Company> page=new Page<>(companyVo.getCurrent(),companyVo.getLimit());
            QueryWrapper<Company>queryWrapper=new QueryWrapper<>();
            queryWrapper.like(StringUtils.isNotEmpty(companyVo.getName()),"name",companyVo.getName());
            companyService.page(page,queryWrapper);
            return Result.success(page);
      }
      //统计公司数量
      @GetMapping("count")
      public Result number(){

            List<Company> companyList= companyService.number();
            return Result.success(companyList.toArray());
      }
      @GetMapping("number")
      public Result count(){
            int count = companyService.count();
            return Result.success(count);
      }
      @PostMapping ("/get")
      public  Result getdel(@RequestBody DeliveryVo deliveryVo , HttpServletRequest request){
            String token = request.getHeader("token");
            if(Objects.isNull(token)){
                  token=request.getParameter("token");
            }
            String tokenKey="token_"+token;
            String s = redisService.get(tokenKey);
            String usernameFromToken = jwtUtils.getUsernameFromToken(s);
            User user = userService.selectUserByUserName(usernameFromToken);
            if(user.getRole()==0){
                  Page<Delivery>page=new Page<>(deliveryVo.getCurrent(),deliveryVo.getLimit());
                 return Result.success(deliveryService.page(page,deliveryVo));
            }
            return companyService.getdel(Long.valueOf(user.getId()));
      }

}

