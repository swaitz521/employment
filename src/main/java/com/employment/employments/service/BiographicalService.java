package com.employment.employments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Biographical;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.vo.BiographicalVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
public interface BiographicalService extends IService<Biographical> {

    List<Biographical> biographicalList();

    Biographical selectBiographicalById(Long id);

    Integer add(Biographical biographical);

    Integer updateBiographical(Biographical biographical);

    Integer delete(Long id);
    //根据用户Id查询简历
//    List<Biographical>biographicalListByUserId(Long userId);

    IPage<Biographical> biographicalPage(Page<Biographical> page, BiographicalVo biographicalVo);

    Biographical selectBioByUserId(String id);

    Biographical selectByUserName(String username);
}
