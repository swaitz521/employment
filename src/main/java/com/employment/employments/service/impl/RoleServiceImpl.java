package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.employment.employments.entity.Role;
import com.employment.employments.entity.User;
import com.employment.employments.mapper.RoleMapper;
import com.employment.employments.mapper.UserMapper;
import com.employment.employments.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.vo.RoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Role selectRoleByRoleId(Long roleId) {
        return baseMapper.selectById(roleId);
    }

    @Override
    public List<Role> selectRoleList() {

        return baseMapper.selectList(null);
    }

    @Override
    public Integer add(Role role) {
        Role role1=new Role();
        BeanUtils.copyProperties(role,role1);

//        role.setCreateTime(new Date());
        return baseMapper.insert(role1);
    }

    @Override
    public Integer updateRole(Role role) {
        Role role1=new Role();
        BeanUtils.copyProperties(role,role1);
        role.setUpdateTime(new Date());
        return baseMapper.updateById(role1);
    }

    @Override
    public Integer deleteRole(Long roleId) {
        return baseMapper.deleteById(roleId);
    }

    @Override
    public void selectRoleListByUserId(IPage<Role> page, RoleVo roleVo) {
        QueryWrapper<Role>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(!Objects.isNull(roleVo.getRoleName()),"role_name",roleVo.getRoleName());
        queryWrapper.orderByAsc("id");
        User user = userMapper.selectById(roleVo.getUserId());
        //如果用户不为空、且不是管理员，则只能查询自己创建的角色
        if(user!=null&&!Objects.isNull(user.getRole())&&user.getRole()!=0){
            queryWrapper.eq("create_user",roleVo.getUserId());
        }
        baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public boolean saveRoleMenu(Long roleId, List<Long> menuIds) {
        baseMapper.deleteRoleMenu(roleId);
        return baseMapper.saveRoleMenu(roleId,menuIds)>0;
    }

    @Override
    public List<Long> findRoleIdByUserId(Long userId) {
        List<Long> roleIdByUserId = baseMapper.findRoleIdByUserId(userId);
        return roleIdByUserId;
    }


}
