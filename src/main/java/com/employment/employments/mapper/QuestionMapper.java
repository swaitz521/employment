package com.employment.employments.mapper;

import com.employment.employments.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employment.employments.vo.QuestionNumber;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-05-27
 */
public interface QuestionMapper extends BaseMapper<Question> {

    QuestionNumber number();
}
