package cn.elyar.myProject.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author elyar
 * @date 2020/11/30 10:32
 * @description
 */
public class SimpleTokenAuthenticationConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final SimpleTokenHelper tokenProvider;

    public SimpleTokenAuthenticationConfigure(SimpleTokenHelper tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) {
        SimpleTokenFilter tokenFilter = new SimpleTokenFilter(tokenProvider);
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
