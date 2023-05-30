package com.employment.employments.vo;

import com.employment.employments.entity.Biographical;
import lombok.Data;

@Data
public class BiographicalVo extends Biographical {
    private Long current=1L;
    private Long limit=10L;
    private Long userId;
    private String username;
    private String phone;
}
