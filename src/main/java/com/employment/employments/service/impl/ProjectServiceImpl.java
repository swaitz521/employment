package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Project;
import com.employment.employments.mapper.ProjectMapper;
import com.employment.employments.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.vo.ProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-02-25
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {


    @Override
    public Page<Project> findProjectListByPage(Page<Project> page, ProjectVo projectVo) {
        QueryWrapper<Project>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(!Objects.isNull(projectVo.getName()),"name",projectVo.getName());

       return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Project findProjectById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Integer add(Project project) {
        Project project1=new Project();
        BeanUtils.copyProperties(project,project1);

        return baseMapper.insert(project1);
    }

    @Override
    public Integer updateProject(ProjectVo projectVo) {
        Project project=new Project();
        BeanUtils.copyProperties(projectVo,project);
        return baseMapper.updateById(project);
    }

    @Override
    public Integer delete(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public List<Project> number() {
        List<Project>projects= baseMapper.number();
        return projects;
    }

    @Override
    public List<Project> week() {
        List<Project>projects=baseMapper.week();
        return projects;
    }

    @Override
    public Integer updateProjectStatus(Project projectVo) {
//        UpdateWrapper<Project>updateWrapper=new UpdateWrapper<>();
//        updateWrapper.set("project_status",projectVo.getProjectStatus());

        return baseMapper.updateById(projectVo);
    }
}
