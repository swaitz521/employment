package com.employment.employments.vo;

import com.employment.employments.entity.User;
import lombok.Data;

@Data
public class UserVo extends User {
    private Long current=1L;
    private Long limit=10L;
//    private Long id;
}
