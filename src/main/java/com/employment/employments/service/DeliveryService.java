package com.employment.employments.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Biographical;
import com.employment.employments.entity.Delivery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.util.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-04-05
 */
public interface DeliveryService extends IService<Delivery> {
    Result deliveryAdd(Long empId, Long userId);

    Page<Delivery> page(Page<Delivery> deliveryPage,Delivery delivery);

    Integer add(Delivery delivery);

    Integer updateInfo(Delivery delivery);

    Integer delete(Long id);

    List<Delivery> number1();

    List<Delivery> week();

    Page<Delivery> selectByUserId(Page<Delivery> deliveryPage, Delivery delivery);
    Delivery selectByName(String name);
}
