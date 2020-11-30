package cn.elyar.myProject.jwt;

import cn.elyar.myProject.SecurityProperties;
import cn.elyar.myProject.dto.OnlineUserDto;
import cn.elyar.myProject.service.OnlineUserService;
import cn.elyar.myProject.utils.SpringContextHolder;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author elyar
 * @date 2020/11/27 16:52
 * @description
 */
@Slf4j
public class SimpleTokenFilter extends GenericFilterBean {

    private final SimpleTokenHelper tokenProvider;

    public SimpleTokenFilter(SimpleTokenHelper tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = resolveToken(httpServletRequest);
        // 对于 Token 为空的不需要去查 Redis
        if(StrUtil.isNotBlank(token)){
            OnlineUserDto onlineUserDto = null;
            SecurityProperties properties = SpringContextHolder.getBean(SecurityProperties.class);
            try {
                OnlineUserService onlineUserService = SpringContextHolder.getBean(OnlineUserService.class);
                onlineUserDto = onlineUserService.getOne(properties.getOnlineKey() + token);
            } catch (ExpiredJwtException e) {
                log.error(e.getMessage());
            }
            if (onlineUserDto != null && StringUtils.hasText(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                // The SecurityContextHolder is where Spring Security stores the details of who is authenticated.
                // Spring Security does not care how the SecurityContextHolder is populated.
                // If it contains a value, then it is used as the currently authenticated user.
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Token 续期
                tokenProvider.checkRenewal(token);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        SecurityProperties properties = SpringContextHolder.getBean(SecurityProperties.class);
        String bearerToken = request.getHeader(properties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            // 去掉令牌前缀
            return bearerToken.replace(properties.getTokenStartWith(),"");
        }
        return null;
    }
}
