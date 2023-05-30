package com.employment.employments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.employment.employments.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.vo.RoleVo;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface RoleService extends IService<Role> {

    Role selectRoleByRoleId(Long roleId);

    List<Role> selectRoleList();

    Integer add(Role role);

    Integer updateRole(Role role);

    Integer deleteRole(Long roleId);

    void selectRoleListByUserId(IPage<Role> page, RoleVo roleVo);

    boolean saveRoleMenu(Long roleId,List<Long>menuIds);
    List<Long>findRoleIdByUserId(Long userId);


}
