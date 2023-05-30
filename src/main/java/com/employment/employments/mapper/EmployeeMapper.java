package com.employment.employments.mapper;

import com.employment.employments.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    List<Employee>employeeInfo(Long userId);
    String selectUserNameByEmpId(Long empId);
}
