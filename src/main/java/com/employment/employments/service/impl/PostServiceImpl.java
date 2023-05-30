package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Post;
import com.employment.employments.mapper.PostMapper;
import com.employment.employments.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.vo.PostVo;
import javafx.geometry.Pos;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public IPage<Post> selectPostList(Page<Post>page, PostVo post) {
        QueryWrapper<Post>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(post.getName()),"name",post.getName());
        return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Post selectPostByPostId(Long postId) {
        return baseMapper.selectById(postId);
    }

    @Override
    public Integer add(Post post) {
        Post post1=new Post();
        BeanUtils.copyProperties(post,post1);
        return baseMapper.insert(post1);
    }

    @Override
    public Integer updatePost(Post post) {
        Post post1=new Post();
        BeanUtils.copyProperties(post,post1);
        return baseMapper.updateById(post1);
    }

    @Override
    public Integer delete(Long postId) {
        return baseMapper.deleteById(postId);
    }

    @Override
    public Post selectPostByName(String name) {
        QueryWrapper<Post>queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        return baseMapper.selectOne(queryWrapper);
    }
}
