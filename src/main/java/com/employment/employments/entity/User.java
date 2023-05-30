package com.employment.employments.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author shao
 * @since 2023-02-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String account;

    private String username;

    private String password;

    private String avater;

    private String phone;

    private String address;

    private String email;

    @ApiModelProperty(value = "0代表男 1代表女")
    private String sex;

    @ApiModelProperty(value = "0代表管理员,1代表学生，2代表企业")
    private Long role;

    @TableField("create_time")

    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField(exist = false)
    public Collection<? extends GrantedAuthority> Authorities;

    @ApiModelProperty(value = "帐户是否过期(1-未过期，0-已过期)")
    public boolean isAccountNonExpired;

    @ApiModelProperty(value = "帐户是否被锁定(1-未过期，0-已过期)")
    public boolean isAccountNonLocked;

    @ApiModelProperty(value = "密码是否过期(1-未过期，0-已过期)")
    public boolean isCredentialsNonExpired;

    @ApiModelProperty(value = "帐户是否可用(1-可用，0-禁用)")
    public boolean isEnabled;

    @TableField(exist = false)
    private List<Menu>menuList;

    @TableField(exist = false)
    private String days;

    @TableField(exist = false)
    private Integer number;
}
