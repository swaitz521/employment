package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.Biographical;
import com.employment.employments.entity.User;
import com.employment.employments.service.BiographicalService;
import com.employment.employments.service.ex.PasswordNotMatchException;
import com.employment.employments.service.ex.UserNotFoundException;
import com.employment.employments.mapper.UserMapper;
import com.employment.employments.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.service.ex.UsernameDuplicateException;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.Result;
import com.employment.employments.vo.Student;
import com.employment.employments.vo.UserParams;
import com.employment.employments.vo.UserVo;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@Service

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BiographicalService biographicalService;
    @Autowired
    private UserMapper userMapper;
    @Override
    public User selectUserByUserId(Long id) {
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return baseMapper.selectOne(queryWrapper);
    }
    @Override
    public List<User> selectUserList(Long current,Long limit) {
        IPage<User>page=new Page<>(current,limit);
        IPage<User> page1 = baseMapper.selectPage(page, null);
        List<User> records = page1.getRecords();
        return records;
    }

    @Override
    public Integer add(User user) {
        User user1=new User();
        BeanUtils.copyProperties(user,user1);
        user.setCreateTime(new Date());
        return baseMapper.insert(user1);
    }

    @Override
    public Integer updateUser(User user) {
        User user1=new User();
        BeanUtils.copyProperties(user,user1);
        user.setUpdateTime(new Date());
        return baseMapper.updateById(user1);
    }

    @Override
    public Integer deleteUserByUserId(String id) {
        return baseMapper.deleteById(id);
    }

    //登录
    @Override
    public boolean login(UserParams params) {
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",params.getUsername());
        queryWrapper.eq("password",params.getPassword());
        Integer integer = baseMapper.selectCount(queryWrapper);
        if(integer>0)
            return true;
        return false;
    }
    //修改密码
    @Override
    public void restPassword(User user) {
        baseMapper.updateById(user);
    }

    @Override
    public Integer register(User user) {
//        String username = user.getUsername();
//        if(username!=null){
//            throw new UsernameDuplicateException("该用户已经被注册");
//        }
        //加密密码先不写
        Biographical biographical=new Biographical();
        biographical.setAvater(user.getAvater());
        biographical.setUsername(user.getUsername());
        biographical.setAddress(user.getAddress());
        biographical.setPhone(user.getPhone());
        biographical.setEmail(user.getEmail());
        biographicalService.add(biographical);
        Biographical biographical1 = biographicalService.selectByUserName(biographical.getUsername());
        System.out.println(biographical1.getId());
        User user1=new User();
        BeanUtils.copyProperties(user,user1);
        user1.setAccountNonExpired(true);
        user1.setAccountNonLocked(true);
        user1.setCredentialsNonExpired(true);
        user1.setEnabled(true);
        user1.setPassword(encryptPassword(user.getPassword()));
        int insert = baseMapper.insert(user1);

        userMapper.saveUserBio(Long.valueOf(user1.getId()),biographical1.getId());

        if(user1.getRole()==3){
            baseMapper.saveUserRole(Long.valueOf(user1.getId()), Collections.singletonList(user.getRole()));
        }else if(user1.getRole()==2){
            baseMapper.saveUserRole(Long.valueOf(user1.getId()), Collections.singletonList(user.getRole()));
        }
        return insert;
    }
   //修改图片
    @Override
    public void updateAvatar(User user) {
        User user1=new User();
        BeanUtils.copyProperties(user,user1);
      baseMapper.updateById(user1);
    }

    public User selectUserByUserName(String username){
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.like("username",username);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<User> findUserListByPage(IPage<User> page, UserVo userVo) {
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(userVo.getUsername()),"username",userVo.getUsername());
        return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public List<User> number() {
        List<User>userList=baseMapper.number();
        return userList;
    }

    @Override
    public Page<Student> selectByRoleId(Student student) {
        List<Student>students=baseMapper.selectByRoleId();
        Page<Student>page=new Page<>(student.getCurrent(),student.getLimit());
        Page<Student> page1 = page.setRecords(students);
        return page1;
    }

    @Override
    public Integer updateStudent(Student student) {
        Integer integer=baseMapper.updateStudent(student);
        return null;
    }

    @Override
    public void deleteStudent(Long id) {
        baseMapper.deleteStudent(id);
    }

    @Override
    public Student selectStudentByUserName(String username) {
        Student student=baseMapper.selectStudentByUserName(username);
        return student;
    }

    @Override
    public boolean saveUserRole(Long userId, List<Long> roleIds) {
        baseMapper.deleteStudent(userId);

        return baseMapper.saveUserRole(userId,roleIds)>0;
    }
    private String encryptPassword(String password) {
        // BCryptPasswordEncoder 加密
        return new BCryptPasswordEncoder().encode(password);
    }

}
