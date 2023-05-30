package com.employment.employments.service;

import com.employment.employments.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.vo.MenuVo;
import com.employment.employments.vo.RoleMenuVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface MenuService extends IService<Menu> {

    List<Menu> selectMenuList(MenuVo menuVo);

    Menu selectMenuByMenuId(Long menuId);

    Integer add(Menu menu);

    Integer updateMenu(Menu menu);

    Integer delete(Long menuId);
    List<Menu>selectMenuListByUserId(String userId);

    List<Menu> selectParentMenuList();
    boolean hasChildrenOfMenu(Long id);
    RoleMenuVo findMenuTree(Long roleId,Long userId);
}
