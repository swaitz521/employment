package com.employment.employments.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author shao
 * @since 2023-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Question对象", description="")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    private String name;

    private String nature;

    private String direction;

    private String number;

    private String degree;

    private String talent;

    private String post;

    private String address;

    private String salary;

    private Long userId;

    private String introduction;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private Long current;

    @TableField(exist = false)
    private Long limit;
}
