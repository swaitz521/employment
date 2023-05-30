package com.employment.employments.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Guidance对象", description="")
public class Guidance implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "指导内容")
    private String guidance;

    @ApiModelProperty(value = "教师Id")
    private Long teacherId;

    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    private Date createTime;

    private Date updateTime;


}
