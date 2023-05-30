package com.employment.employments.vo;

import com.employment.employments.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo extends Role {
    private Long current=1L;
    private Long limit=10L;
    private Long userId;
}
