package com.employment.employments.service;

import com.employment.employments.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-05-27
 */
public interface QuestionService extends IService<Question> {

    Integer add(Question question);

    Integer updateInfo(Question question);
}
