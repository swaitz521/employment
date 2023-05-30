package com.employment.employments.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuDto {
    private Long roleId;
    private List<Long>menuIds;
}
