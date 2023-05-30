package com.employment.employments.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@ApiModel(value="Graduate对象", description="")
public class Graduate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String sex;

    private String phone;

    private String dept;

    private String graduateTime;

    private String status;

    private String address;

    private String present;

    private BigDecimal salary;

    private String introduction;

    private String advice;

    private Long userId;

    @TableField(exist = false)
    private Long current;

    @TableField(exist = false)
    private Long limit;

    private Date createTime;

    private Date updateTime;
}
