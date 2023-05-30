package com.employment.employments.mapper;

import com.employment.employments.entity.Company;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface CompanyMapper extends BaseMapper<Company> {
    List<Company>number();

    Company selectComById(Long id);

}
