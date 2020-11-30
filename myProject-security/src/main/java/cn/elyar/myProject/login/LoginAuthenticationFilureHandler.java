package cn.elyar.myProject.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author elyar
 * @date 2020/11/27 16:30
 * @description
 */
@Slf4j
@Component
public class LoginAuthenticationFilureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("onAuthenticationFailure");

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(request.getRequestURI() + ": " + exception.getMessage());
        response.getWriter().close();
    }
}
