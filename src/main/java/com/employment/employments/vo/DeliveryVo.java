package com.employment.employments.vo;

import com.employment.employments.entity.Delivery;
import lombok.Data;

@Data
public class DeliveryVo extends Delivery {
    private Long current;
    private Long limit;
}
