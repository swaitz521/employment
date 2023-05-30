package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Notice;
import com.employment.employments.mapper.NoticeMapper;
import com.employment.employments.service.NoticeService;
import com.employment.employments.util.Result;
import com.employment.employments.vo.NewsVo;
import com.employment.employments.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public Result noticelist(NoticeVo noticeVo) {
        Page<Notice> noticePage = new Page<>(noticeVo.getCurrent(),noticeVo.getLimit());
        LambdaQueryWrapper<Notice> queryWrapper=new LambdaQueryWrapper<>();
       queryWrapper.like(StringUtils.isNotEmpty(noticeVo.getTitle()),Notice::getTitle,noticeVo.getTitle());
       noticeMapper.selectPage(noticePage,queryWrapper);
        return Result.success(noticePage);
    }

    @Override
    public Result add(Notice notice) {
        noticeMapper.insert(notice);
        return Result.success("添加成功！");
    }

    @Override
    public Result update(Notice notice) {
        int i = noticeMapper.updateById(notice);
        return Result.success("修改成功！");
    }

    @Override
    public Result deleteById(Long id) {
        int i = noticeMapper.deleteById(id);
        return Result.success("删除成功！");
    }

    @Override
    public Result search(String title) {
        LambdaQueryWrapper<Notice> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(title),Notice::getTitle,title);
        Notice notice = noticeMapper.selectOne(queryWrapper);
        return Result.success(notice);
    }

    @Override
    public Result list() {
        QueryWrapper<Notice>queryWrapper=new QueryWrapper<>();
        queryWrapper.last("limit 2");
        List<Notice> notices = noticeMapper.selectList(queryWrapper);

        return Result.success(notices.toArray());
    }

    @Override
    public Result selectNoticeById(Long id) {
        Notice notice = noticeMapper.selectById(id);
        return Result.success(notice);
    }

    @Override
    public Result list1() {
        QueryWrapper<Notice>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",2);
        List<Notice> notices = noticeMapper.selectList(queryWrapper);
        return Result.success(notices.toArray());
    }

    @Override
    public Result list2() {
        QueryWrapper<Notice>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",3);
        List<Notice> notices = noticeMapper.selectList(queryWrapper);
        return Result.success(notices.toArray());
    }

}
