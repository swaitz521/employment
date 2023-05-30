package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Guidance;
import com.employment.employments.entity.Project;
import com.employment.employments.mapper.GuidanceMapper;
import com.employment.employments.service.GuidanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.service.ProjectService;
import com.employment.employments.vo.GuidanceVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GuidanceServiceImpl extends ServiceImpl<GuidanceMapper, Guidance> implements GuidanceService {
    @Autowired
    private ProjectService projectService;
    @Override
    public Integer updateGuidance(Guidance guidance) {
        Guidance guidance1=new Guidance();
        guidance.setUpdateTime(new Date());
        BeanUtils.copyProperties(guidance,guidance1);
//        Project project=new Project();
//        project.setId(guidance1.getProjectId());


        return baseMapper.updateById(guidance1);
    }

    @Override
    public Integer add(Guidance guidance) {
        guidance.setCreateTime(new Date());
        guidance.setUpdateTime(new Date());

        return baseMapper.insert(guidance);
    }

    @Override
    public Page<GuidanceVo> findGuidanceById(GuidanceVo guidanceVo) {
         Page<GuidanceVo>page=new Page<>(guidanceVo.getCurrent(),guidanceVo.getLimit());
        List<GuidanceVo> guidanceById = baseMapper.findGuidanceById(guidanceVo);
        Page<GuidanceVo> guidancePage = page.setRecords(guidanceById);
        return guidancePage;
    }

    @Override
    public Guidance findById(String id) {
        QueryWrapper<Guidance>guidanceQueryWrapper=new QueryWrapper<>();
        guidanceQueryWrapper.eq("project_id",id);
        return baseMapper.selectOne(guidanceQueryWrapper);
    }

    @Override
    public Integer delete(Long id) {
        return baseMapper.deleteById(id);
    }
}
