package com.employment.employments.mapper;

import com.employment.employments.entity.Biographical;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employment.employments.entity.Delivery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface BiographicalMapper extends BaseMapper<Biographical> {

//    List<Biographical>findBiographicalByUserId(Long userId);
        //通过用户Id查询简历
    Biographical findBiographicalByUserId(String id);

}
