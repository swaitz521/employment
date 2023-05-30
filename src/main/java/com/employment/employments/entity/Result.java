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
@ApiModel(value="Result对象", description="")
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "审核结果")
    private String reviewResults;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "专家Id")
    private Long expertId;

    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    private Date createTime;

    private Date updateTime;


}
