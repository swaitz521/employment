package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.entity.News;
import com.employment.employments.mapper.NewsMapper;
import com.employment.employments.service.NewsService;
import com.employment.employments.util.Result;
import com.employment.employments.vo.NewsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper,News> implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public Result pagelist(NewsVo newsVo) {
        Page<News> newsPage = new Page<>(newsVo.getCurrent(),newsVo.getLimit());
        LambdaQueryWrapper<News> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(newsVo.getTitle()),News::getTitle,newsVo.getTitle());
         newsMapper.selectPage(newsPage, queryWrapper);
        return Result.success(newsPage);
    }

    @Override
    public Result add(News news) {
        int insert = newsMapper.insert(news);
        return Result.success("添加成功!");
    }

    @Override
    public Result update(News news) {
        newsMapper.updateById(news);
        return Result.success("更新成功！");
    }

    @Override
    public Result deleteById(long id) {
        newsMapper.deleteById(id);
        return Result.success("删除成功！");
    }

    @Override
    public Result search(String name) {
        LambdaQueryWrapper<News> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),News::getTitle,name);
        News one = newsMapper.selectOne(queryWrapper);
        return Result.success(one);
    }

    @Override
    public Result selectEmployeeList() {
        QueryWrapper<News>queryWrapper=new QueryWrapper<>();
        queryWrapper.last("limit 2");
        List<News> news = newsMapper.selectList(queryWrapper);
        return Result.success(news.toArray());
    }

    @Override
    public Result selectNewsById(Long id) {
        News news = newsMapper.selectById(id);
        return Result.success(news);
    }
}
