package com.yihe.system.service;


import com.yihe.system.domain.CSOrgDoctorChat;

public interface CSOrgDoctorChatService {
    CSOrgDoctorChat selectListByOrgAndType(String orgId, Integer type);
    int deleteByOrgAndType(String orgId, Integer type);
    int insertSelective(CSOrgDoctorChat record);


}
