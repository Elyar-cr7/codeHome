package cn.elyar.myProject.service;

import cn.elyar.myProject.SecurityProperties;
import cn.elyar.myProject.dto.OnlineUserDto;
import cn.elyar.myProject.utils.EncryptUtils;
import cn.elyar.myProject.utils.RedisUtils;
import cn.elyar.myProject.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author elyar
 * @date 2020/11/27 17:01
 * @description
 */
@Service
@Slf4j
public class OnlineUserService {

    @Resource
    private SecurityProperties properties;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 保存在线用户信息
     * @param userDetails /
     * @param token /
     * @param request /
     */
    public void save(UserDetails userDetails, String token, HttpServletRequest request){
//        String dept = jwtUserDto.getUser().getDept().getName();
        String ip = StringUtils.getIp(request);
//        String browser = StringUtils.getBrowser(request);
//        String address = StringUtils.getCityInfo(ip);
        OnlineUserDto onlineUserDto = null;
        try {
            onlineUserDto = new OnlineUserDto(userDetails.getUsername(), "", "", "" , ip, "", EncryptUtils.desEncrypt(token), new Date());
//            onlineUserDto = new OnlineUserDto(jwtUserDto.getUsername(), "", "", browser , ip, address, EncryptUtils.desEncrypt(token), new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisUtils.set(properties.getOnlineKey() + token, onlineUserDto, properties.getTokenValidityInSeconds()/1000);
    }

//    /**
//     * 查询全部数据
//     * @param filter /
//     * @param pageable /
//     * @return /
//     */
//    public Map<String,Object> getAll(String filter, Pageable pageable){
//        List<OnlineUserDto> onlineUserDtos = getAll(filter);
//        return PageUtil.toPage(
//                PageUtil.toPage(pageable.getPageNumber(),pageable.getPageSize(), onlineUserDtos),
//                onlineUserDtos.size()
//        );
//    }

    /**
     * 查询全部数据，不分页
     * @param filter /
     * @return /
     */
    public List<OnlineUserDto> getAll(String filter){
        List<String> keys = redisUtils.scan(properties.getOnlineKey() + "*");
        Collections.reverse(keys);
        List<OnlineUserDto> onlineUserDtos = new ArrayList<>();
        for (String key : keys) {
            OnlineUserDto onlineUserDto = (OnlineUserDto) redisUtils.get(key);
            if(StringUtils.isNotBlank(filter)){
                if(onlineUserDto.toString().contains(filter)){
                    onlineUserDtos.add(onlineUserDto);
                }
            } else {
                onlineUserDtos.add(onlineUserDto);
            }
        }
        onlineUserDtos.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUserDtos;
    }

    /**
     * 踢出用户
     * @param key /
     */
    public void kickOut(String key){
        key = properties.getOnlineKey() + key;
        redisUtils.del(key);
    }

    /**
     * 退出登录
     * @param token /
     */
    public void logout(String token) {
        String key = properties.getOnlineKey() + token;
        redisUtils.del(key);
    }

//    /**
//     * 导出
//     * @param all /
//     * @param response /
//     * @throws IOException /
//     */
//    public void download(List<OnlineUserDto> all, HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (OnlineUserDto user : all) {
//            Map<String,Object> map = new LinkedHashMap<>();
//            map.put("用户名", user.getUserName());
//            map.put("部门", user.getDept());
//            map.put("登录IP", user.getIp());
//            map.put("登录地点", user.getAddress());
//            map.put("浏览器", user.getBrowser());
//            map.put("登录日期", user.getLoginTime());
//            list.add(map);
//        }
//        FileUtil.downloadExcel(list, response);
//    }

    /**
     * 查询用户
     * @param key /
     * @return /
     */
    public OnlineUserDto getOne(String key) {
        return (OnlineUserDto)redisUtils.get(key);
    }

    /**
     * 检测用户是否在之前已经登录，已经登录踢下线
     * @param userName 用户名
     */
    public void checkLoginOnUser(String userName, String igoreToken){
        List<OnlineUserDto> onlineUserDtos = getAll(userName);
        if(onlineUserDtos ==null || onlineUserDtos.isEmpty()){
            return;
        }
        for(OnlineUserDto onlineUserDto : onlineUserDtos){
            if(onlineUserDto.getUserName().equals(userName)){
                try {
                    String token =EncryptUtils.desDecrypt(onlineUserDto.getKey());
                    if(StringUtils.isNotBlank(igoreToken)&&!igoreToken.equals(token)){
                        this.kickOut(token);
                    }else if(StringUtils.isBlank(igoreToken)){
                        this.kickOut(token);
                    }
                } catch (Exception e) {
                    log.error("checkUser is error",e);
                }
            }
        }
    }

}
