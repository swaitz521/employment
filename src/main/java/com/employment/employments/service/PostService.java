package com.employment.employments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.vo.PostVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface PostService extends IService<Post> {

    IPage<Post> selectPostList(Page<Post>page, PostVo post);

    Post selectPostByPostId(Long postId);

    Integer add(Post post);

    Integer updatePost(Post post);

    Integer delete(Long postId);

    Post selectPostByName(String name);
}
