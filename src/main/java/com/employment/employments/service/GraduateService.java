package com.employment.employments.service;

import com.employment.employments.entity.Graduate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.vo.GraduateNumber;

import java.util.List;

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

   GraduateNumber number();
}
