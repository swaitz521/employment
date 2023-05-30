package com.employment.employments.entity;

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
 * @since 2023-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserDelivery对象", description="")
public class UserDelivery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long deliveryId;


}
