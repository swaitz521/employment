package com.employment.employments.service.impl;

import com.employment.employments.entity.Question;
import com.employment.employments.mapper.QuestionMapper;
import com.employment.employments.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.employment.employments.vo.QuestionNumber;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-05-27
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Override
    public Integer add(Question question) {
        question.setCreateTime(new Date());
        question.setUpdateTime(new Date());
        Question question1=new Question();
        BeanUtils.copyProperties(question,question1);
        return baseMapper.insert(question1);
    }

    @Override
    public Integer updateInfo(Question question) {
        question.setUpdateTime(new Date());
        Question question1=new Question();
        BeanUtils.copyProperties(question,question1);
        return baseMapper.updateById(question1);
    }

    @Override
    public QuestionNumber number() {
        QuestionNumber integers= baseMapper.number();
        return integers;
    }
}
