package cn.elyar.myProject.entity;

import cn.elyar.myProject.base.BaseEntity;
import io.swagger.annotations.ApiModel;
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

    //"姓名"
    private String name;

    //"用户名"
    private String username;

    //"密码"
    private String password;

    //"电话"
    private String phone;

    //"邮箱"
    private String email;

    //等级
    private Integer level;

}
