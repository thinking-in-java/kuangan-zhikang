package com.yihe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author lll 
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class YiheApplication
{
    public static void main(String[] args)
    { 
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(YiheApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  鲨鱼启动成功    ლ(´ڡ`ლ)ﾞ ");
    }
}