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
 * @since 2023-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Delivery对象", description="")
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String bioName;

    private String name;

    private String title;

    private BigDecimal salary;

    private String address;

    private Integer number;

    private String post;

    private String phone;

    private Date createTime;

    @TableField(exist = false)
    private String days;

    @TableField(exist = false)
    private Integer count1;

    private String status;

    @TableField(exist = false)
    private Long userId;
}
