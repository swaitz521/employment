package com.employment.employments.service;

import com.employment.employments.entity.Graduate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-05-27
 */
public interface GraduateService extends IService<Graduate> {

    Integer add(Graduate graduate);

    Integer updateInfo(Graduate graduate);
}
