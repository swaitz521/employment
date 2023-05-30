package com.employment.employments.vo;

import com.employment.employments.entity.User;
import lombok.Data;

@Data
public class Student extends User {
    private Long current;
    private Long limit;
    private String dept;
    private String degree;
}
