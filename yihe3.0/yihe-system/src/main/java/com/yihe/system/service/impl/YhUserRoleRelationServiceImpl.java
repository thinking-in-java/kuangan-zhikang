package com.yihe.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.mapper.YhUserRoleRelationMapper;
import com.yihe.system.service.YhUserRoleRelationService;

/**
* YhUserRoleRelationService实现
* Created by lll on 2020/3/6.
*/
@Service
@Transactional

public class YhUserRoleRelationServiceImpl  implements YhUserRoleRelationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserRoleRelationServiceImpl.class);

    @Autowired
    YhUserRoleRelationMapper yhUserRoleRelationMapper;

}