package com.employment.employments.mapper;

import com.employment.employments.entity.Delivery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-04-05
 */
public interface DeliveryMapper extends BaseMapper<Delivery> {

    List<Delivery> number1();

    List<Delivery> week();

    List<Delivery> selectByUserId(Delivery delivery);

}
