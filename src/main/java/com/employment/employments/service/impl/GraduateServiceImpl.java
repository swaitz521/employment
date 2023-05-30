package com.employment.employments.service.impl;

import com.employment.employments.entity.Graduate;
import com.employment.employments.mapper.GraduateMapper;
import com.employment.employments.service.GraduateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-05-27
 */
@Service
public class GraduateServiceImpl extends ServiceImpl<GraduateMapper, Graduate> implements GraduateService {

    @Override
    public Integer add(Graduate graduate) {
        graduate.setCreateTime(new Date());
        graduate.setUpdateTime(new Date());
        Graduate graduate1=new Graduate();
        BeanUtils.copyProperties(graduate,graduate1);
        return baseMapper.insert(graduate1);
    }

    @Override
    public Integer updateInfo(Graduate graduate) {
        graduate.setUpdateTime(new Date());
        Graduate graduate1=new Graduate();
        BeanUtils.copyProperties(graduate,graduate1);
        return baseMapper.updateById(graduate1);
    }
}
