package cn.elyar.myProject.jwt;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author elyar
 * @date 2020/11/30 10:34
 * @description
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // ~ Static fields/initializers
    // =====================================================================================

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "token";

    private String tokenHead = SPRING_SECURITY_FORM_USERNAME_KEY;
    private boolean postOnly = false;

    // ~ Constructors
    // ===================================================================================================

    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/**"));
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
//        if (postOnly && !request.getMethod().equals("POST")) {
//            throw new AuthenticationServiceException(
//                    "Authentication method not supported: " + request.getMethod());
//        }

        String token = obtainToken(request);

        if (token == null) {
            token = "";
        }

        token = token.trim();

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(token);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Enables subclasses to override the composition of the password, such as by
     * including additional values and a separator.
     * <p>
     * This might be used for example if a postcode/zipcode was required in addition to
     * the password. A delimiter such as a pipe (|) should be used to separate the
     * password and extended value(s). The <code>AuthenticationDao</code> will need to
     * generate the expected password in a corresponding manner.
     * </p>
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the password that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    @Nullable
    protected String obtainToken(HttpServletRequest request) {
        return request.getParameter(tokenHead);
    }


    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request,
                              JwtAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


}
