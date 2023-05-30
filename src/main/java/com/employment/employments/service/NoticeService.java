package com.employment.employments.service;

import com.employment.employments.entity.Notice;
import com.employment.employments.util.Result;
import com.employment.employments.vo.NewsVo;
import com.employment.employments.vo.NoticeVo;

public interface NoticeService {
    Result noticelist(NoticeVo noticeVo);

    Result add(Notice notice);

    Result update(Notice notice);

    Result deleteById(Long id);

    Result search(String title);

    Result list();

    Result selectNoticeById(Long id);

    Result list1();

    Result list2();
}
