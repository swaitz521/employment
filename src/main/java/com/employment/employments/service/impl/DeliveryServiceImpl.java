package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.*;
import com.employment.employments.mapper.*;
import com.employment.employments.service.DeliveryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-04-05
 */
@Service
public class DeliveryServiceImpl extends ServiceImpl<DeliveryMapper, Delivery> implements DeliveryService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DeliveryMapper deliveryMapper;
    @Autowired
    private BioEmpMapper bioEmpMapper;
    @Autowired
    private UserDeliveryMapper userDeliveryMapper;
    @Autowired
    private BiographicalMapper biographicalMapper;
    @Override
    public Result deliveryAdd(Long empId, Long userId) {
        Employee employee = employeeMapper.selectById(empId);
        Biographical biographicalByUserId = biographicalMapper.findBiographicalByUserId(String.valueOf(userId));
        Delivery delivery=new Delivery();
        delivery.setBioName(biographicalByUserId.getName());
        delivery.setName(biographicalByUserId.getUsername());
        delivery.setAddress(employee.getAddress());
        delivery.setNumber(employee.getNumber());
        delivery.setPhone(employee.getPhone());
        delivery.setPost(employee.getPost());
        delivery.setSalary(employee.getSalary());
        delivery.setTitle(employee.getTitle());
        // delivery.setCreateTime();
        BioEmp bioEmp=new BioEmp();
        bioEmp.setBioId(biographicalByUserId.getId());
        bioEmp.setEmpId(empId);
        deliveryMapper.insert(delivery);
        bioEmpMapper.insert(bioEmp);
        UserDelivery userDelivery=new UserDelivery();
        userDelivery.setUserId(userId);
        userDelivery.setDeliveryId(delivery.getId());
        userDeliveryMapper.insert(userDelivery);
        return Result.success("投递成功！！");
    }

    @Override
    public Page<Delivery> page(Page<Delivery> deliveryPage,Delivery delivery) {

        QueryWrapper<Delivery>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(delivery.getBioName()),"bio_name",delivery.getBioName());
        return baseMapper.selectPage(deliveryPage,queryWrapper);
    }

    @Override
    public Integer add(Delivery delivery) {
        Delivery delivery1=new Delivery();
        BeanUtils.copyProperties(delivery,delivery1);
        return baseMapper.insert(delivery1);
    }

    @Override
    public Integer updateInfo(Delivery delivery) {
        Delivery delivery1=new Delivery();
        BeanUtils.copyProperties(delivery,delivery1);
        return baseMapper.updateById(delivery1);
    }

    @Override
    public Integer delete(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public List<Delivery> number1() {
        List<Delivery>deliveries= baseMapper.number1();
        return deliveries;
    }

    @Override
    public List<Delivery> week() {
        List<Delivery>deliveries=baseMapper.week();
        return deliveries;
    }

    @Override
    public Page<Delivery> selectByUserId(Page<Delivery> deliveryPage, Delivery delivery) {
        List<Delivery>deliveries=baseMapper.selectByUserId(delivery);
        Page<Delivery> deliveryPage1 = deliveryPage.setRecords(deliveries);
        return deliveryPage1;
    }

    @Override
    public Delivery selectByName(String name) {
        QueryWrapper<Delivery>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("bio_name",name);
        return baseMapper.selectOne(queryWrapper);
    }

}
