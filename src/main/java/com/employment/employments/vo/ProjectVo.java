package com.employment.employments.vo;

import com.employment.employments.entity.Project;
import lombok.Data;

@Data
public class ProjectVo extends Project {
    private Long current=1L;
    private Long limit=10L;
    private String guidance;
    private Long userId;
}
