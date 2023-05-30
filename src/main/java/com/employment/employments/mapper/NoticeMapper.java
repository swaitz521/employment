package com.employment.employments.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employment.employments.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    List<Notice> selectTitle();
}
