package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Result;
import com.employment.employments.mapper.ResultMapper;
import com.employment.employments.service.ResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-02-25
 */
@Service
public class ResultServiceImpl extends ServiceImpl<ResultMapper, Result> implements ResultService {

    @Override
    public Integer add(Result result) {
        result.setCreateTime(new Date());
        result.setUpdateTime(new Date());
        return baseMapper.insert(result);
    }

    @Override
    public Integer updateResult(Result result) {
        Result result1=new Result();
        BeanUtils.copyProperties(result,result1);
        return baseMapper.updateById(result1);
    }

    @Override
    public Integer delete(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public IPage<ResultVo> findResultById(ResultVo resultVo) {
        IPage<ResultVo> page=new Page<>(resultVo.getCurrent(),resultVo.getLimit());
        List<ResultVo> resultById = baseMapper.findResultById(resultVo);
        IPage<ResultVo> resultVoPage = page.setRecords(resultById);
        resultVoPage.setTotal(page.getTotal());
        resultVoPage.setSize(page.getSize());
        return resultVoPage;
    }

    @Override
    public Page<ResultVo> findResultByName(ResultVo resultVo) {
        Page<ResultVo>page=new Page<>(resultVo.getCurrent(),resultVo.getLimit());
        List<ResultVo> resultById = baseMapper.findResultByName(resultVo);
        Page<ResultVo> resultVoPage = page.setRecords(resultById);
        return resultVoPage;
    }

    @Override
    public Result findResultByProjectId(Long projectId) {
        QueryWrapper<Result>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("project_id",projectId);
        return baseMapper.selectOne(queryWrapper);
    }
}
