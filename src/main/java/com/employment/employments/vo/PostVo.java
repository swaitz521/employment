package com.employment.employments.vo;

import lombok.Data;

@Data
public class PostVo {
    private Long current=1L;
    private Long limit=10L;
    private Long id;
    private String code;
    private String name;
}
