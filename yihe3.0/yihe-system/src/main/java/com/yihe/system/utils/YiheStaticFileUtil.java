package com.yihe.system.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * 启动解压zhengAdmin-x.x.x.jar到resources目录
 * Created by shuzheng on 2016/12/18.
 */
public class YiheStaticFileUtil implements InitializingBean, ServletContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(YiheStaticFileUtil.class);

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        LOGGER.info("===== 开始解压yihe-static-client =====");
        String version = PropertiesFileUtil.getInstance("yihe-static-client").get("yihe.static.version");
        LOGGER.info("yihe-static-client.jar 版本: {}", version);
        String jarPath = servletContext.getRealPath("/WEB-INF/lib/yihe-static-file-" + version + ".jar");
        LOGGER.info("yihe-static-client.jar 包路径: {}", jarPath);
        String resources = servletContext.getRealPath("/") + "/resources/yihe-static-file";
        LOGGER.info("yihe-static-client.jar 解压到: {}", resources);
        JarUtil.decompress(jarPath, resources);
        LOGGER.info("===== 解压yihe-static-client完成 =====");
    }

}
