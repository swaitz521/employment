package com.employment.employments.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouterVo implements Serializable {
    private String path;
    private String component;
    private String name;
    private boolean alwaysShow;
    private Meta meta;

    @Data
    @AllArgsConstructor
    public class Meta{
        private String title;
        private String icon;
        private Object[]roles;
    }
    //子路由
    public List<RouterVo>children=new ArrayList<>();
}
