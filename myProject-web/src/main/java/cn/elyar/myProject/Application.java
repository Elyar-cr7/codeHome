package cn.elyar.myProject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author elyar
 * @date 2020/11/27 10:04
 * @description
 */
@Slf4j
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan({"cn.elyar.myProject.mapper"})
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext=SpringApplication.run(Application.class,args);
        Environment env=applicationContext.getEnvironment();
        try {
            log.info(
                    "Swagger地址: http://{}:{}{}/swagger-ui.html",
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port") == null ? 8080 : env.getProperty("server.port"),
                    env.getProperty("server.servlet.context-path"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
