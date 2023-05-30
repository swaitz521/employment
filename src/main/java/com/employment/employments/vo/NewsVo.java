package com.employment.employments.vo;

import com.employment.employments.entity.News;
import lombok.Data;

@Data
public class NewsVo extends News {
     private Long current=1L;
     private Long limit=10L;

}
