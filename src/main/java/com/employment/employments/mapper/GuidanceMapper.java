package com.employment.employments.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.employment.employments.entity.Guidance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employment.employments.vo.GuidanceVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-02-25
 */
public interface GuidanceMapper extends BaseMapper<Guidance> {
    List<GuidanceVo> findGuidanceById(GuidanceVo guidanceVo);
    GuidanceVo findGuidanceByGuidance(Long id);
//    Integer deleteguidanceById(Long id);

}
