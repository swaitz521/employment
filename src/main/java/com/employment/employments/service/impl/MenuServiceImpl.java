package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.employment.employments.entity.Menu;
import com.employment.employments.entity.User;
import com.employment.employments.mapper.MenuMapper;
import com.employment.employments.mapper.UserMapper;
import com.employment.employments.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.util.MenuTree;
import com.employment.employments.vo.MenuVo;
import com.employment.employments.vo.RoleMenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Menu> selectMenuList(MenuVo menuVo) {
        QueryWrapper<Menu>queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("order_num",menuVo.getOrderNum());
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Menu selectMenuByMenuId(Long menuId) {
        return baseMapper.selectById(menuId);
    }

    @Override
    public Integer add(Menu menu) {
        Menu menu1=new Menu();
        BeanUtils.copyProperties(menu,menu1);
        return baseMapper.insert(menu1);
    }

    @Override
    public Integer updateMenu(Menu menu) {
        Menu menu1=new Menu();
        BeanUtils.copyProperties(menu,menu1);
        return baseMapper.updateById(menu1);
    }

    @Override
    public Integer delete(Long menuId) {
        return baseMapper.deleteById(menuId);
    }

    @Override
    public List<Menu> selectMenuListByUserId(String userId) {
        return baseMapper.selectMenuListByUserId(Long.valueOf(userId));
    }
    //查询是否有子菜单列表
    @Override
    public List<Menu> selectParentMenuList() {
        QueryWrapper<Menu>queryWrapper=new QueryWrapper<>();
        //只查询type为目录和菜单的数据(type=M或type=C)
        queryWrapper.in("type", Arrays.asList("M","C"));
//        queryWrapper.orderByAsc("order_num");
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        //构造顶级菜单信息，如果数据库中的菜单表没有数据，选择上级菜单时则显示顶级菜单
        Menu menu=new Menu();
        menu.setId("0L");
        menu.setParentId(-1L);
        menu.setName("顶级菜单");
        menus.add(menu);
        List<Menu> menuList = MenuTree.makeMenu(menus, -1L);
        return menuList;
    }
    //是否有子菜单
    public boolean hasChildrenOfMenu(Long id){
        QueryWrapper<Menu>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        if(baseMapper.selectCount(queryWrapper)>0){
            return true;
        }
        return false;
    }
    /**
     * 查询分配权限树列表
     *
     * @param userId
     * @param roleId
     * @return
     */
    public RoleMenuVo findMenuTree(Long roleId,Long userId){
        User user = userMapper.selectById(userId);
        List<Menu>list=null;
        //2.判断当前用户角色，如果是管理员，则查询所有权限；如果不是管理员，则只查询自己所拥有的的权限
        if(!Objects.isNull(user.getRole())&&user.getRole()==0){
            list=baseMapper.selectList(null);
        }else{
            list=baseMapper.selectMenuListByUserId(userId);
        }
        //生成菜单树
        List<Menu> menus = MenuTree.makeMenu(list, 0L);
        //查询要分配角色的权限
        List<Menu> menuListByRoleId = baseMapper.findMenuListByRoleId(roleId);
        //找出角色已存在的数据
        List<Long>listIds=new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream().filter(Objects::nonNull)
                .forEach(item->{
                    Optional.ofNullable(menuListByRoleId).orElse(new ArrayList<>())
                            .stream().filter(Objects::nonNull).forEach(obj->{
                                if(item.getId().equals(obj.getId())){
                                    listIds.add(Long.valueOf(obj.getId()));
                                    return;
                                }
                            });
                });
        RoleMenuVo roleMenuVo=new RoleMenuVo();
        roleMenuVo.setMenuList(menus);
        roleMenuVo.setCheckList(listIds.toArray());
        return roleMenuVo;
    }
}
