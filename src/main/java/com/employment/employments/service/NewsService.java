package com.employment.employments.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.entity.News;
import com.employment.employments.util.Result;
import com.employment.employments.vo.NewsVo;

public interface NewsService extends IService<News> {
    Result pagelist(NewsVo newsVo);

    Result add(News news);

    Result update(News news);

    Result deleteById(long id);

    Result search(String name);

    Result selectEmployeeList();

    Result selectNewsById(Long id);
}
