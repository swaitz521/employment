package com.employment.employments.mapper;

import com.employment.employments.entity.Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employment.employments.vo.ResultVo;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-02-25
 */
public interface ResultMapper extends BaseMapper<Result> {
     List<ResultVo>findResultById(ResultVo resultVo);

    List<ResultVo> findResultByName(ResultVo resultVo);

    @Delete("delete from role_result where result_id=#{resultId}")
    void deleteRoleResult(Long resultId);
}
