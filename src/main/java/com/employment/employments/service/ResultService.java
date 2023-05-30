package com.employment.employments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.vo.ResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-02-25
 */
public interface ResultService extends IService<Result> {

    Integer add(Result result);

    Integer updateResult(Result result);

    Integer delete(Long id);

    IPage<ResultVo> findResultById(ResultVo resultVo);

    Page<ResultVo> findResultByName(ResultVo resultVo);

    Result findResultByProjectId(Long projectId);
}
