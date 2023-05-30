package com.employment.employments.vo;

import com.employment.employments.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo extends Result {
    private Long current=1L;
    private Long limit=10L;
    private Long userId;
    private String type;
    private String name;
    private String teacher;
    private String student;
    private String introduction;
    private String projectStatus;
    private String score;
}
