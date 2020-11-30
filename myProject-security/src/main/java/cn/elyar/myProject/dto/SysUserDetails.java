package cn.elyar.myProject.dto;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author elyar
 * @date 2020/11/27 17:38
 * @description
 */
public interface SysUserDetails  extends UserDetails {
    /**
     * 获取当前用户ID
     * @return
     */
    String getId();

    /**
     * 获取当前用户所属组ID
     * @return
     */
    String getOrgId();

    /**
     * 获取用户信息
     * @return
     */
    Object getPrincipal();
}
