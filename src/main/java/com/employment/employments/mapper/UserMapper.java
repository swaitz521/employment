package com.employment.employments.mapper;

import com.employment.employments.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employment.employments.vo.Student;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<User> number();

    List<Student> selectByRoleId();

    Integer updateStudent(Student student);

    void deleteStudent(Long id);

    Student selectStudentByUserName(String username);

    int saveUserRole(Long userId,List<Long>roleIds);

    @Insert("insert into user_bio(user_id,biographical_id) values (#{userId},#{biographicalId})")
    void saveUserBio(Long userId,Long biographicalId);
}
