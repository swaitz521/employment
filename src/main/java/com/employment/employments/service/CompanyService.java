package com.employment.employments.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Company;

import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.util.Result;
import com.employment.employments.vo.CompanyVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface CompanyService extends IService<Company> {

    void selectCompanyList(Page<Company>page, CompanyVo companyVo);

    Company selectCompanyById(Long id);

    Integer add(CompanyVo company);

    Integer updateCompany(CompanyVo company);

    Integer delete(Long id);

    List<Company> number();

    Result getdel(Long userId);

//    Company selectCompanyByName(String name);
}
