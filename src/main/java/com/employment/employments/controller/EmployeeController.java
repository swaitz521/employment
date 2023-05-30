package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.Employee;
import com.employment.employments.entity.Project;
import com.employment.employments.entity.User;
import com.employment.employments.mapper.EmployeeMapper;
import com.employment.employments.service.EmployeeService;
import com.employment.employments.service.UserService;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.Result;
import com.employment.employments.vo.EmployeeVo;
import org.apache.commons.logging.Log;
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
@RequestMapping("/api/employee")
public class EmployeeController {
     @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeMapper employeeMapper;
     @GetMapping("list")
    public Result employeeList(){
         List<Employee>employeeList=employeeService.selectEmployeeList();
         return Result.success(employeeList.toArray());
     }
    @GetMapping("list1")
    public Result employeeList1(){
        List<Employee>employeeList=employeeService.selectEmployeeList1();
        return Result.success(employeeList.toArray());
    }
     @DeleteMapping("/select/{id}")
    public Result selectEmployeeById(@PathVariable("id") Long id){
         Employee employee=employeeService.selectEmployeeById(id);
         return Result.success(employee);
     }
     @PostMapping
    public Result add(@RequestBody Employee employee){
         Integer add = employeeService.add(employee);
         if(add>0)
         return Result.success().msg("公司信息添加成功");
         return Result.error();
     }
     @PutMapping
    public Result update(@RequestBody Employee employee){
         Integer integer = employeeService.updateEmployee(employee);
         if(integer>0)
         return Result.success().msg("公司信息修改成功");
         return Result.error();
     }
     @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
         Integer delete = employeeService.delete(id);
         if(delete>0)
         return Result.success().msg("删除成功");
         return Result.error();
     }
    @PostMapping("page")
    public Result page(@RequestBody EmployeeVo employeeVo, HttpServletRequest request){

        String token = request.getHeader("token");
        if(Objects.isNull(token)){
            token=request.getParameter("token");
        }
        String tokenKey="token_"+token;
        String s = redisService.get(tokenKey);
        String usernameFromToken = jwtUtils.getUsernameFromToken(s);
        User user = userService.selectUserByUserName(usernameFromToken);
        Page<Employee>list=new Page<>();
        List<Employee>list1=new ArrayList<>();
        Page<Employee>page=new Page<>(employeeVo.getCurrent(),employeeVo.getLimit());
        Page<Employee> page1 = employeeService.selectEmployeelist(page, employeeVo);
        List<Employee> records = page1.getRecords();
        if(user.getRole()==0){

            list = employeeService.selectEmployeelist(page, employeeVo);
        }else if(user.getRole()==5){
            List<Employee> employeeList = employeeMapper.employeeInfo(Long.valueOf(user.getId()));
            list.setRecords(employeeList);
        }
        else{
//            Page<Employee>page=new Page<>(employeeVo.getCurrent(),employeeVo.getLimit());
            list = employeeService.selectEmployeelist(page, employeeVo);
            Optional.ofNullable(records).orElse(new ArrayList<>()).stream().filter(Objects::nonNull)
                    .forEach(item->{
                        if(item.getIsCommint().equals("0")){
                            Employee employee=new Employee();
                            BeanUtils.copyProperties(item,employee);
                            list1.add(employee);
                        }
                    });

            list.setRecords(list1);
        }
        return Result.success(list);
    }
    @PostMapping("page1")
    public Result page1(@RequestBody EmployeeVo employeeVo){
        Page<Employee>page=new Page<>(employeeVo.getCurrent(),employeeVo.getLimit());
        IPage<Employee> employeeIPage = employeeService.selectEmployeelist1(page, employeeVo);
        return Result.success(employeeIPage);
    }
//    @GetMapping("select")
//    public Result select(HttpServletRequest request){
//        String token = request.getHeader("token");
//        if(Objects.isNull(token)){
//            token=request.getParameter("token");
//        }
//        String tokenKey="token_"+token;
//        String s = redisService.get(tokenKey);
//        String usernameFromToken = jwtUtils.getUsernameFromToken(s);
//        User user = userService.selectUserByUserName(usernameFromToken);
//
//        List<Employee> employeeList = employeeMapper.employeeInfo(Long.valueOf(user.getId()));
//
//        return Result.success(employeeList);
//    }
    @GetMapping("{empId}")
    public Result selectUserNameByEmpId(@PathVariable Long empId){
         String username= employeeService.selectUserNameByEmpId(empId);
         return Result.success(username);
    }
}

