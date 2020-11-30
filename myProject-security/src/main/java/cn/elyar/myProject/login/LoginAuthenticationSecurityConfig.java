package cn.elyar.myProject.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author elyar
 * @date 2020/11/27 16:31
 * @description
 */
@Component
public class LoginAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Resource
    private AuthenticationSuccessHandler loginAuthenticationSuccessHandler;

    @Resource
    private AuthenticationFailureHandler loginAuthenticationFailureHandler;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserDetailsService userDetailsService;

    public void configure(HttpSecurity http){

        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter();
        loginAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        loginAuthenticationFilter.setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler);
        loginAuthenticationFilter.setAuthenticationFailureHandler(loginAuthenticationFailureHandler);

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        http.authenticationProvider(daoAuthenticationProvider)
                .addFilterBefore(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
