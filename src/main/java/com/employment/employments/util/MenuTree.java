package com.employment.employments.util;

import com.employment.employments.entity.Menu;
import com.employment.employments.vo.RouterVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuTree {

    /**
     *生成路由
     * @param menuList
     * @param pid
     * @return
     */
    public static List<RouterVo>makeRouter(List<Menu>menuList,Long pid){
        //创建集合保存路由列表
        List<RouterVo>routerVoList=new ArrayList<>();
        //如果menuList菜单列表不为空，则使用菜单列表，否则创建集合对象
        Optional.ofNullable(menuList).orElse(new ArrayList<Menu>())
                //筛选不为空的菜单及菜单父id相同的数据
                .stream().filter(item->item!=null&&item.getParentId()==(pid))
                .forEach(item->{
                    RouterVo routerVo=new RouterVo();
                    routerVo.setPath(item.getPath());
                    routerVo.setName(item.getName());
                    //判断是否是一级菜单
                    if(item.getParentId()==0L){
                        routerVo.setComponent("Layout");//一级菜单组件
                        routerVo.setAlwaysShow(true);//显示路由
                    }else {
                        routerVo.setComponent(item.getUrl());//具体组件
                        routerVo.setAlwaysShow(false);//折叠路由
                    }
                    routerVo.setMeta(routerVo.new Meta(item.getLabel(),item.getIcon(),item.getCode().split(",")));
                    //递归生成路由
                    List<RouterVo> child = makeRouter(menuList, Long.valueOf(item.getId()));
                    routerVo.setChildren(child);
                    routerVoList.add(routerVo);
                });
        return routerVoList;
    }

    /**
     * 生成菜单
     * @param menuList
     * @param pid
     * @return
     */
    public static List<Menu>makeMenu(List<Menu>menuList,Long pid){
        List<Menu>menus=new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<Menu>())
                .stream().filter(item->item!=null&&item.getParentId().equals(pid))
                .forEach(item->{
                    Menu menu=new Menu();
                    BeanUtils.copyProperties(item,menu);
                    List<Menu> child = makeMenu(menuList, Long.valueOf(item.getId()));
                    menu.setChildren(child);
                    menus.add(menu);
                });
        return menus;
    }
}
