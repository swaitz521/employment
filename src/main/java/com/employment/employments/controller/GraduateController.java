package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Graduate;
import com.employment.employments.service.GraduateService;
import com.employment.employments.util.Result;
import com.employment.employments.vo.GraduateNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shao
 * @since 2023-05-27
 */
@RestController
@RequestMapping("/api/graduate")
public class GraduateController {
     @Autowired
     private GraduateService graduateService;
    @PostMapping("page")
    public Result page(@RequestBody Graduate graduate){
        Page<Graduate>graduatePage=new Page<>(graduate.getCurrent(), graduate.getLimit());
        QueryWrapper<Graduate>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(graduate.getName()),"name",graduate.getName());
        Page<Graduate> page = graduateService.page(graduatePage, queryWrapper);
        return Result.success(page);
    }
    @PostMapping
    public Result add(@RequestBody Graduate graduate){
        Integer integer=graduateService.add(graduate);
        if(integer>0)
            return Result.success().msg("提交成功");
        return Result.error();
    }
    @PutMapping
    public Result update(@RequestBody Graduate graduate){
        Integer integer=graduateService.updateInfo(graduate);
        if(integer>0)
            return Result.success().msg("修改成功");
        return Result.error();
    }
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        boolean b = graduateService.removeById(id);
        if(b)
            return Result.success().msg("删除成功");
        return Result.error();
    }
    @GetMapping("/{userId}")
    public Result selectByUserId(@PathVariable Long userId){
        QueryWrapper<Graduate>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        Graduate one = graduateService.getOne(queryWrapper);
        return Result.success(one);
    }

    /**
     * 统计各问题的数量
     * @return
     */
    @GetMapping("count")
    public Result number(){
        GraduateNumber graduate = graduateService.number();
        return Result.success(graduate);
    }
}

