package com.employment.employments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.vo.ProjectVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-02-25
 */
public interface ProjectService extends IService<Project> {

    Page<Project> findProjectListByPage(Page<Project> page, ProjectVo projectVo);

    Project findProjectById(Long id);

    Integer add(Project project);

    Integer updateProject(ProjectVo projectVo);

    Integer delete(Long id);

    List<Project> number();

    List<Project> week();
    Integer updateProjectStatus(Project project);
}
