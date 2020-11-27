package cn.elyar.myProject.moudle.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

/**
 * @author elyar
 * @date 2020/11/26 16:06
 * @description
 */
@Data
public class SysUserDto {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户名")
    @NonNull
    private String username;

    @ApiModelProperty(value = "密码")
    @NonNull
    private String password;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;


}
