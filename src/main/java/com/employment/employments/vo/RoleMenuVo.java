package com.employment.employments.vo;

import com.employment.employments.entity.Menu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleMenuVo {
    //菜单数据
    private List<Menu>menuList=new ArrayList<>();
    //该角色原有的菜单数据
    private Object[]checkList;
}
