package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Biographical;
import com.employment.employments.mapper.BiographicalMapper;
import com.employment.employments.service.BiographicalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.vo.BiographicalVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@Service
public class BiographicalServiceImpl extends ServiceImpl<BiographicalMapper, Biographical> implements BiographicalService {

    @Autowired
    private BiographicalMapper biographicalMapper;

    @Override
    public List<Biographical> biographicalList() {
        return baseMapper.selectList(null);
    }

    @Override
    public Biographical selectBiographicalById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Integer add(Biographical biographical) {
        Biographical biographical1=new Biographical();
        BeanUtils.copyProperties(biographical,biographical1);
        return baseMapper.insert(biographical1);
    }

    @Override
    public Integer updateBiographical(Biographical biographical) {
        Biographical biographical1=new Biographical();
        BeanUtils.copyProperties(biographical,biographical1);
        return baseMapper.updateById(biographical1);
    }

    @Override
    public Integer delete(Long id) {
        return baseMapper.deleteById(id);
    }

//    @Override
//    public Biographical biographicalListByUserId(Long userId) {
//       Biographical biographicalByUserId = baseMapper.findBiographicalByUserId(String.valueOf(userId));
//        return biographicalByUserId;
//    }

    @Override
    public IPage<Biographical> biographicalPage(Page<Biographical> page, BiographicalVo biographicalVo) {
        QueryWrapper<Biographical>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(biographicalVo.getName()),"name",biographicalVo.getName());
        queryWrapper.like(StringUtils.isNotEmpty(biographicalVo.getUsername()),"username",biographicalVo.getUsername());
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Biographical selectBioByUserId(String id) {
        Biographical biographicalByUserId = biographicalMapper.findBiographicalByUserId(id);
        return biographicalByUserId;
    }

    @Override
    public Biographical selectByUserName(String username) {
        QueryWrapper<Biographical>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return baseMapper.selectOne(queryWrapper);
    }
}
