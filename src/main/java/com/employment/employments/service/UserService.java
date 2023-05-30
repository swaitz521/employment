package com.employment.employments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.util.Result;
import com.employment.employments.vo.Student;
import com.employment.employments.vo.UserParams;
import com.employment.employments.vo.UserVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface UserService extends IService<User> {

    User selectUserByUserId(Long id);

    List<User> selectUserList(Long current,Long limit);
    Integer add(User user);

    Integer updateUser(User user);

    Integer deleteUserByUserId(String id);

    boolean  login(UserParams params);

    void restPassword(User user);

    Integer register(User user);

    void updateAvatar(User user);
    User selectUserByUserName(String username);

    IPage<User> findUserListByPage(IPage<User> page, UserVo userVo);

    List<User> number();

    Page<Student> selectByRoleId(Student student);

    Integer updateStudent(Student student);

    void deleteStudent(Long id);

    Student selectStudentByUserName(String username);

    boolean saveUserRole(Long userId,List<Long>roleIds);
}
