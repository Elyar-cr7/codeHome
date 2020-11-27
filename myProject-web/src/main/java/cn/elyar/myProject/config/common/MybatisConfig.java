package cn.elyar.myProject.config.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author elyar
 * @date 2020/11/27 14:17
 * @description
 */
@Configuration
@ComponentScan({"cn.elyar.myProject.mapper"})
public class MybatisConfig  {
}
