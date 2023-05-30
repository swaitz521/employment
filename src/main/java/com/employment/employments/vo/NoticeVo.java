package com.employment.employments.vo;

import com.employment.employments.entity.Notice;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Data
public class NoticeVo extends Notice {
    private Long current=1l;
    private  Long limit=10l;
}
