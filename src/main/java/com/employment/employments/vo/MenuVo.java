package com.employment.employments.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo {
    private Long id;
    private String name;
    private Long parentId;
    private String code;
    private String path;
    private String url;
    private String type;
    private String icon;
    private Integer orderNum;
}
