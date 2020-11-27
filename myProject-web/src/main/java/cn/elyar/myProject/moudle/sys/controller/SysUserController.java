package cn.elyar.myProject.moudle.sys.controller;

import cn.elyar.myProject.base.BaseController;
import cn.elyar.myProject.base.Result;
import cn.elyar.myProject.moudle.sys.service.impl.SysServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author elyar
 * @date 2020/11/27 14:21
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class SysUserController extends BaseController {

    @Autowired
    SysServiceImpl sysService;

    @PostMapping(value = "/SysRegister")
    public Result register(){

        return successMessage();
    }
}
