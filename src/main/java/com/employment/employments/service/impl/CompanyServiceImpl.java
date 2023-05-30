package com.employment.employments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.ComDel;
import com.employment.employments.entity.Company;

import com.employment.employments.entity.Delivery;
import com.employment.employments.entity.UserCom;
import com.employment.employments.mapper.ComDelMapper;
import com.employment.employments.mapper.CompanyMapper;

import com.employment.employments.mapper.DeliveryMapper;
import com.employment.employments.mapper.UserComMapper;
import com.employment.employments.service.CompanyService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.util.Result;
import com.employment.employments.vo.CompanyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Override
    public void selectCompanyList(Page<Company>page,CompanyVo company) {
        QueryWrapper<Company>queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",company.getName());
        baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Company selectCompanyById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Integer add(CompanyVo company) {
        Company company1=new Company();
        BeanUtils.copyProperties(company,company1);
        return baseMapper.insert(company1);
    }

    @Override
    public Integer updateCompany(CompanyVo company) {
        Company company1=new Company();
        BeanUtils.copyProperties(company,company1);
        return baseMapper.updateById(company1);
    }

    @Override
    public Integer delete(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public List<Company> number() {
        List<Company> number = baseMapper.number();
        return number;
    }
    @Autowired
    private ComDelMapper comDelMapper;
    @Autowired
    private DeliveryMapper deliveryMapper;
    @Autowired
    private UserComMapper userComMapper;
    @Override
    public Result getdel(Long userId) {
        LambdaQueryWrapper<UserCom> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.eq(UserCom::getUserId,userId);
        UserCom userCom = userComMapper.selectOne(queryWrapper1);
        LambdaQueryWrapper<ComDel> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ComDel::getComId,userCom.getComId());
        List<ComDel> comDelList = comDelMapper.selectList(queryWrapper);
        List<Delivery> deliveryList =new ArrayList<>();
        for (ComDel comDel : comDelList) {
            Delivery delivery = deliveryMapper.selectById(comDel.getDelId());
            deliveryList.add(delivery);
        }
        Page<Delivery>page=new Page<>(1,10);
        page.setRecords(deliveryList);
        return Result.success(page);
    }

//    @Override
//    public Company selectCompanyByName(String name) {
//        QueryWrapper<Company>queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("name",name);
//        return baseMapper.selectOne(queryWrapper);
//    }
}
