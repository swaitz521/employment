package com.employment.employments.entity;

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
 * @since 2023-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Project对象", description="")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "项目名称")
    private String name;

//    @ApiModelProperty(value = "项目标题")
//    private String title;

    @ApiModelProperty(value = "负责人")
    private String student;

    @ApiModelProperty(value = "指导老师")
    private String teacher;

    @ApiModelProperty(value = "项目类型")
    private String type;

    private String projectPresent;

//    @ApiModelProperty(value = "项目级别")
//    private String stage;

    @ApiModelProperty(value = "项目图片")
    private String avater;

    @ApiModelProperty(value = "项目介绍")
    private String introduction;

    @ApiModelProperty(value = "项目计划")
    private String projectPlan;

    @ApiModelProperty(value = "项目预算")
    private String projectBudget;

    @ApiModelProperty(value = "状态")
    private String projectStatus;

    private String projectResult;

    @ApiModelProperty(value = "是否删除（0代表正常，1代表删除）")
    private Boolean isDelete;

//    @ApiModelProperty(value = "提交时间")
//    private Date commitTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @TableField(exist = false)
    private String days;

    @TableField(exist = false)
    private Integer number;

    private String score;
}
