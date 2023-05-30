package com.employment.employments.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    private String id;
    private String name;
    private String avatar;
    private String introduction;
    private Object[] roles;//角色权限集合
}
