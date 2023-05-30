package com.employment.employments.mapper;

import com.employment.employments.entity.Project;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-02-25
 */
public interface ProjectMapper extends BaseMapper<Project> {
   //根据用户Id查询项目
    List<Project>findProjectListByUserId(Long userId);
    Project findProjectByUserId(Long userId);
    List<Project> number();
    @Insert("insert into user_project(user_id,project_id) values (#{userId},#{projectId})")
    void saveUserProject(Long userId,Long projectId);
    List<Project> week();
}
