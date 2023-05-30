package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Company;
import com.employment.employments.entity.Employee;
import com.employment.employments.mapper.CompanyMapper;
import com.employment.employments.mapper.EmployeeMapper;
import com.employment.employments.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.vo.EmployeeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
   @Autowired
   private CompanyMapper companyMapper;
    @Override
    public List<Employee> selectEmployeeList() {
        QueryWrapper<Employee>queryWrapper=new QueryWrapper<>();
        queryWrapper.last("limit 4");
        return baseMapper.selectList(queryWrapper);
    }
    @Override
    public List<Employee> selectEmployeeList1() {
        QueryWrapper<Employee>queryWrapper=new QueryWrapper<>();
        queryWrapper.last("limit 3");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Page<Employee> selectEmployeelist1(Page<Employee> page, EmployeeVo employeeVo) {
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(employeeVo.getName()),Employee::getName,employeeVo.getName());
        queryWrapper.eq(Employee::getStatus,"1");
        Page<Employee> page1 = baseMapper.selectPage(page, queryWrapper);
        List<Employee> records = page1.getRecords();
        List<Employee>employeeList=new ArrayList<>();
        Optional.ofNullable(records).orElse(new ArrayList<>())
                .stream().filter(Objects::nonNull)
                .forEach(item->{
                    Company company = companyMapper.selectComById(item.getId());
                    Employee employee=new Employee();
                    BeanUtils.copyProperties(item,employee);
                    employee.setComName(company.getName());

                    employeeList.add(employee);
                });
        page1.setRecords(employeeList);
        return page1;
    }

    @Override
    public String selectUserNameByEmpId(Long empId) {
        String s = baseMapper.selectUserNameByEmpId(empId);
        return s;
    }

    @Override
    public Employee selectEmployeeById(Long id) {
//        Company company= companyMapper.selectComById(id);
        Employee employee = baseMapper.selectById(id);
//        employee.setComName(company.getName());
        return employee;
    }

    @Override
    public Integer add(Employee employee) {
        Employee employee1=new Employee();
        BeanUtils.copyProperties(employee,employee1);
        int insert = baseMapper.insert(employee1);
        return insert;
    }

    @Override
    public Integer updateEmployee(Employee employee) {
        Employee employee1=new Employee();
        BeanUtils.copyProperties(employee,employee1);
        return baseMapper.updateById(employee1);
    }

    @Override
    public Integer delete(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Page<Employee> selectEmployeelist(Page<Employee> page, EmployeeVo employeeVo) {
//        Company company = companyMapper.selectComById(employeeVo.getId());
//        employeeVo.setComName(company.getName());
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(employeeVo.getName()),Employee::getName,employeeVo.getName());
//        queryWrapper.last("4");
        Page<Employee> page1 = baseMapper.selectPage(page, queryWrapper);
        List<Employee> records = page1.getRecords();
        List<Employee>employeeList=new ArrayList<>();
        Optional.ofNullable(records).orElse(new ArrayList<>())
                .stream().filter(Objects::nonNull)
                .forEach(item->{
                    Company company = companyMapper.selectComById(item.getId());
                    Employee employee=new Employee();
                    BeanUtils.copyProperties(item,employee);
                    employee.setComName(company.getName());

                    employeeList.add(employee);
                });
        page1.setRecords(employeeList);
//        log.info(queryWrapper.getSqlSelect());
        return page1;
    }
}
