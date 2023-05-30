package com.employment.employments.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Post;
import com.employment.employments.service.PostService;
import com.employment.employments.util.Result;
import com.employment.employments.vo.PostVo;
import javafx.geometry.Pos;
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
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;
    @GetMapping("list")
    public Result selectPostList(){
        List<Post> postList= postService.list();
        return Result.success(postList);
    }
    @GetMapping("/{postId}")
    public Result selectPostByPostId(@PathVariable Long postId){
        Post post=postService.selectPostByPostId(postId);
        return Result.success(post);
    }
    @PostMapping
    public Result add(@RequestBody Post post){
        Integer i=postService.add(post);
        return Result.success().msg("岗位信息添加成功");
    }
    @PutMapping
    public Result update(@RequestBody Post post){
        Integer i=postService.updatePost(post);
        return Result.success().msg("岗位信息修改成功");
    }
    @DeleteMapping("/{postId}")
    public Result delete(@PathVariable Long postId){
        Integer i=postService.delete(postId);
        return Result.success().msg("岗位信息删除成功");
    }

    /**
     * 根据岗位名查询岗位信息
     * @param name
     * @return
     */
    @GetMapping("/{name}")
    public Result selectPostByName(@PathVariable String name){
        Post post=postService.selectPostByName(name);
        if(post==null)
            return Result.error().msg("招聘岗位不存在");
        return Result.success(post);
    }
    @PostMapping("page")
    public Result page(@RequestBody PostVo post){
        Page<Post>page=new Page<>(post.getCurrent(),post.getLimit());
        IPage<Post> list = postService.selectPostList(page, post);
        return Result.success(list);
    }
}

