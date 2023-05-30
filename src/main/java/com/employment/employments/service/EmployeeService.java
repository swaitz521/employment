package com.employment.employments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.vo.EmployeeVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface EmployeeService extends IService<Employee> {

    List<Employee> selectEmployeeList();

    Employee selectEmployeeById(Long id);

    Integer add(Employee employee);

    Integer updateEmployee(Employee employee);

    Integer delete(Long id);

    Page<Employee> selectEmployeelist(Page<Employee> page, EmployeeVo employeeVo);

    List<Employee> selectEmployeeList1();

    Page<Employee> selectEmployeelist1(Page<Employee> page, EmployeeVo employeeVo);

    String selectUserNameByEmpId(Long empId);
}
