package com.yihe.system.service.impl;


import com.yihe.system.domain.CSOrgDoctorChat;
import com.yihe.system.mapper.CSOrgDoctorChatMapper;
import com.yihe.system.service.CSOrgDoctorChatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CSOrgDoctorChatServiceImpl implements CSOrgDoctorChatService {
    @Resource
    CSOrgDoctorChatMapper CSOrgDoctorChatMapper;

    @Override
    public CSOrgDoctorChat selectListByOrgAndType(String orgId, Integer type) {
        return CSOrgDoctorChatMapper.selectListByOrgAndType(orgId, type);
    }

    @Override
    public int deleteByOrgAndType(String orgId, Integer type) {
        return CSOrgDoctorChatMapper.deleteByOrgAndType(orgId, type);
    }

    @Override
    public int insertSelective(CSOrgDoctorChat record) {
        return CSOrgDoctorChatMapper.insertSelective(record);
    }
}
