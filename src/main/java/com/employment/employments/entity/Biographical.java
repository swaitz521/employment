package com.employment.employments.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
import java.util.Date;
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
@ApiModel(value="Biographical对象", description="")
public class Biographical implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    @ApiModelProperty(value = "姓名")
    private String username;

    private String phone;

    private String sex;

    private String avater;

    @ApiModelProperty(value = "优点")
    private String advantage;

    @ApiModelProperty(value = "期望")
    private String introduction;

    private String address;

    private BigDecimal salary;

    @ApiModelProperty(value = "项目经验")
    private String projectExperience;

    @ApiModelProperty(value = "政治面貌")
    private String political;

    private Date birthday;

    @ApiModelProperty(value = "学位")
    private String degree;

    private String school;

    @ApiModelProperty(value = "专业")
    private String dept;

    private String email;

    @ApiModelProperty(value = "求职行业")
    private String post;

    private String experience;

    private Date createTime;

    private Date updateTime;


}
