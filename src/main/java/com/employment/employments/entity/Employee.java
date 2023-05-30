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
 * @since 2023-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Employee对象", description="")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String title;

    private BigDecimal salary;

    private String address;

    private String phoneName;

    private String phone;

    @ApiModelProperty(value = "岗位")
    private String post;

    @ApiModelProperty(value = "招聘人数")
    private Integer number;

    @ApiModelProperty(value = "0代表上架，1代表未上架")
    private String isCommint;

    private Date createTime;

    private Date updateTime;

    private String demand;

    private String postIntroduction;

    private String status;

    private String workTime;

    @TableField(exist = false)
    private String comName;
}
