package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserLoginlog;

public interface YhUserLoginlogMapper {

    /**
     * 插入登录日志
     * @param yhUserLoginlog
     */
    void insertUserLoginlog(YhUserLoginlog yhUserLoginlog);
}
