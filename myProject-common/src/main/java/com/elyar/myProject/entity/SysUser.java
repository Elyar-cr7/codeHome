package com.elyar.myProject.entity;

import com.elyar.myProject.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author elyar
 * @date 2020/11/25 14:19
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SysUser对象", description="系统用户表")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "店铺id")
    private String storeId;


}
