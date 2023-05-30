package com.employment.employments.vo;

import com.employment.employments.entity.Company;
import lombok.Data;

@Data
public class CompanyVo  {
    private Long current=1L;
    private Long limit=10L;
    private Long id;
    private String name;
    private String title;
    private String address;
    private String email;
    private String phone;

}
