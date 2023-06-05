package com.employment.employments.mapper;

import com.employment.employments.entity.Graduate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employment.employments.vo.GraduateNumber;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-05-27
 */
public interface GraduateMapper extends BaseMapper<Graduate> {

    GraduateNumber number();
}
