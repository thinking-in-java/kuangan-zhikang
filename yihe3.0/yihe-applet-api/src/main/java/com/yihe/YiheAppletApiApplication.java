package com.yihe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 * @author lll 1
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class YiheAppletApiApplication
{
    public static void main(String[] args)
    { 
        //System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(YiheAppletApiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  鲨鱼小程序启动成功    ლ(´ڡ`ლ)ﾞ ");
    }
}