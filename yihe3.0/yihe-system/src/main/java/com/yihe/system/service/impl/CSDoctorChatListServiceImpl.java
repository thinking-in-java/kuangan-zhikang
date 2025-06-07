package com.yihe.system.service.impl;

import com.yihe.system.domain.CSDoctorChatList;
import com.yihe.system.mapper.CSDoctorChatListMapper;
import com.yihe.system.service.CSDoctorChatListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CSDoctorChatListServiceImpl implements CSDoctorChatListService {
    @Resource
    CSDoctorChatListMapper CSDoctorChatListMapper;

    @Override
    public List<CSDoctorChatList> getList() {
        return CSDoctorChatListMapper.selectByPrimaryKeyAll();
    }
}
