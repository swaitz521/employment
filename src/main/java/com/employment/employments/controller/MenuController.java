package com.employment.employments.controller;


import com.employment.employments.entity.Menu;
import com.employment.employments.service.MenuService;
import com.employment.employments.util.Result;
import com.employment.employments.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {
   @Autowired
    private MenuService menuService;

    /**
     * 查询菜单列表
     * @param menuVo
     * @return
     */
   @GetMapping("list")
    public Result selectMenuList(@RequestBody MenuVo menuVo){
       List<Menu>menuList=menuService.selectMenuList(menuVo);
       return Result.success(menuList);
   }
   @GetMapping("/{menuId}")
    public Result selectMenuByMenuId(@PathVariable Long menuId){
       Menu menu=menuService.selectMenuByMenuId(menuId);
       return Result.success(menu);
   }
   @PostMapping
    public Result add(@RequestBody Menu menu){
      Integer i=menuService.add(menu);
      if(i>0)
      return Result.success().msg("菜单添加成功");
      return Result.error();
   }
   @PutMapping
    public Result update(@RequestBody Menu menu){
       Integer i=menuService.updateMenu(menu);
       if(i>0)
       return Result.success();
       return Result.error();
   }
   @DeleteMapping("/{menuId}")
    public Result delete(@PathVariable Long menuId){
       Integer i=menuService.delete(menuId);
       if(i>0)
           return Result.success();
       return Result.error();
   }

    /**
     * 查询子列表
     * @return
     */
   @GetMapping("parent/list")
    public Result parentList(){
       List<Menu>parentMenuList= menuService.selectParentMenuList();
       return Result.success(menuService);
   }

    /**
     * 检查菜单下是否有子菜单
     * @param id
     * @return
     */
   @GetMapping("check/{id}")
    public Result hasChildrenOfMenu(@PathVariable Long id){
       if(menuService.hasChildrenOfMenu(id)){
           return Result.exist().msg("该菜单下有子菜单，无法删除");
       }
       return Result.success();
   }
}

