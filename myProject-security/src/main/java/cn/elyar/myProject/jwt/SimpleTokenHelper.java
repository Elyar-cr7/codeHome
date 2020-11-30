package cn.elyar.myProject.jwt;

import cn.elyar.myProject.SecurityProperties;
import cn.elyar.myProject.dto.SysUserDetails;
import cn.elyar.myProject.dto.UserPrincipal;
import cn.elyar.myProject.utils.RedisUtils;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author elyar
 * @date 2020/11/27 16:55
 * @description
 */
public class SimpleTokenHelper {

    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String ORG_KEY = "orgId";
    private Key key;

    public SimpleTokenHelper(SecurityProperties properties, RedisUtils redisUtils) {
        this.properties = properties;
        this.redisUtils = redisUtils;
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        SysUserDetails principal = (SysUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setId(principal.getId())
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(ORG_KEY, principal.getOrgId())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Object authoritiesStr = claims.get(AUTHORITIES_KEY);
        Collection<? extends GrantedAuthority> authorities =
                ObjectUtil.isNotEmpty(authoritiesStr) ?
                        Arrays.stream(authoritiesStr.toString().split(","))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList()) : Collections.emptyList();

        UserPrincipal caterPrincipal = new UserPrincipal(claims.getId(), claims.getSubject(), String.valueOf(claims.get(ORG_KEY)), authorities);

        return new UsernamePasswordAuthenticationToken(caterPrincipal, token, authorities);
    }

    /**
     * @param token 需要检查的token
     */
    public void checkRenewal(String token){
        // 判断是否续期token,计算token的过期时间
        long time = redisUtils.getExpire(properties.getOnlineKey() + token) * 1000;
        Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
        // 判断当前时间与过期时间的时间差
        long differ = expireDate.getTime() - System.currentTimeMillis();
        // 如果在续期检查的范围内，则续期
        if(differ <= properties.getDetect()){
            long renew = time + properties.getRenew();
            redisUtils.expire(properties.getOnlineKey() + token, renew, TimeUnit.MILLISECONDS);
        }
    }

    public String getToken(HttpServletRequest request){
        final String requestHeader = request.getHeader(properties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }
}
