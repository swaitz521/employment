package com.employment.employments.mapper;

import com.employment.employments.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Delete("delete from role_menu where role_id=#{roleId}")
    void deleteRoleMenu(Long roleId);
    int saveRoleMenu(Long roleId, List<Long> menuIds);
    @Select("select role_id from user_role where user_id=#{userId}")
    List<Long>findRoleIdByUserId(Long userId);
    @Select("select role_id from user_role where user_id=#{userId}")
    Long selectRoleIdByUserId(Long userId);
    @Insert("insert into role_guidance(role_id,guidance_id) values (#{roleId},#{guidanceId})")
    void saveRoleGuidance(Long roleId,Long guidanceId);

    @Insert("insert into role_result(role_id,result_id) values (#{roleId},#{resultId})")
    void saveRoleResult(Long roleId,Long resultId);
}
