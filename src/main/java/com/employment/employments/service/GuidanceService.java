package com.employment.employments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Guidance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.vo.GuidanceVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-02-25
 */
public interface GuidanceService extends IService<Guidance> {

    Integer updateGuidance(Guidance guidance);

    Integer add(Guidance guidance);

    Page<GuidanceVo> findGuidanceById(GuidanceVo guidanceVo);

    Guidance findById(String id);

    Integer delete(Long id);
}
