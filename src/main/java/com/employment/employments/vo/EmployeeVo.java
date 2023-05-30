package com.employment.employments.vo;

import com.employment.employments.entity.Employee;
import lombok.Data;

@Data
public class EmployeeVo extends Employee {
    private Long current;
    private Long limit;
}
