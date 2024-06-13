package com.yihe.system.service.impl;

import com.yihe.system.domain.YhUserLoginlog;
import com.yihe.system.mapper.YhUserLoginlogMapper;
import com.yihe.system.service.YhUserLoginlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YhUserLoginlogServiceImpl implements YhUserLoginlogService {

    @Autowired
    private YhUserLoginlogMapper yhUserLoginlogMapper;

    /**
     * 插入登录日志
     * @param yhUserLoginlog
     */
    @Override
    public void insertUserLoginlog(YhUserLoginlog yhUserLoginlog) {
        yhUserLoginlogMapper.insertUserLoginlog(yhUserLoginlog);
    }
}
