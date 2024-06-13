package com.yihe.system.service;

import com.yihe.system.domain.YhUserLoginlog;

public interface YhUserLoginlogService {

    /**
     * 插入登录日志
     * @param yhUserLoginlog
     */
    void insertUserLoginlog(YhUserLoginlog yhUserLoginlog);
}
