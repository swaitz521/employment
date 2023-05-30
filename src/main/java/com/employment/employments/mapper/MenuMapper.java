package com.employment.employments.mapper;

import com.employment.employments.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface MenuMapper extends BaseMapper<Menu> {
     List<Menu>selectMenuListByUserId(Long userId);
     //根据角色Id查询权限列表
     List<Menu>findMenuListByRoleId(Long roleId);
}
