package com.employment.employments.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;
@Data
public class Notice {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String title;
    private  String body;
    private String avater;
    private String status;
    private Date createTime;
    private Date updateTime;
}
