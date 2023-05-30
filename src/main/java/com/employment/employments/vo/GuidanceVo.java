package com.employment.employments.vo;

import com.employment.employments.entity.Guidance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuidanceVo extends Guidance {
    private Long current=1L;
    private Long limit=10L;
    private Long userId;//用户Id
    private String teacher;
    private String name;
    private String type;
    private String projectStatus;
}
