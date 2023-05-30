package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Question;
import com.employment.employments.service.QuestionService;
import com.employment.employments.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("page")
    public Result page(@RequestBody Question question){
        Page<Question>questionPage=new Page<>(question.getCurrent(),question.getLimit());
        QueryWrapper<Question>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(Objects.nonNull(question.getName()),"name",question.getName());
        Page<Question> page = questionService.page(questionPage, queryWrapper);
        return Result.success(page);
    }
    @PostMapping
    public Result add(@RequestBody Question question){
        Integer integer=questionService.add(question);
        if(integer>0)
            return Result.success().msg("提交成功");
        return Result.error();
    }
    @PutMapping
    public Result update(@RequestBody Question question){
        Integer integer=questionService.updateInfo(question);
        if(integer>0)
            return Result.success().msg("修改成功");
        return Result.error();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        boolean b = questionService.removeById(id);
        if(b)
            return Result.success();
        return Result.error();
    }
    @GetMapping("/{userId}")
    public Result selectByUserId(@PathVariable Long userId){
        QueryWrapper<Question>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        Question one = questionService.getOne(queryWrapper);
        return Result.success(one);
    }
}

