package com.yihe.system.mapper;


import com.yihe.system.domain.CSChatListByUserResVo;
import com.yihe.system.domain.CSDoctorChatList;

import java.util.List;

public interface CSDoctorChatListMapper {
    List<CSChatListByUserResVo> findSessionList(String usercode);

    String findUserForDoctor(String usercode);

    int deleteByPrimaryKey(Integer id);

    int insert(CSDoctorChatList record);

    int insertSelective(CSDoctorChatList record);

    CSDoctorChatList selectByPrimaryKey(Integer id);

    List<CSDoctorChatList> selectByPrimaryKeyAll();

    int updateByPrimaryKeySelective(CSDoctorChatList record);

    int updateByPrimaryKey(CSDoctorChatList record);
}