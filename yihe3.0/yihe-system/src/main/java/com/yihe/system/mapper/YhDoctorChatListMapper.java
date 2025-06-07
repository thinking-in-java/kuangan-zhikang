package com.yihe.system.mapper;

import com.yihe.system.domain.ChatListByUserResqVo;
import com.yihe.system.domain.YhDoctorChatList;

import java.util.List;

public interface YhDoctorChatListMapper {
    List<ChatListByUserResqVo> findSessionList(String usercode);

    List<ChatListByUserResqVo> findSessionListByorgCode();

    String findUserForDoctor(String usercode);

    int deleteByPrimaryKey(Integer id);

    int insert(YhDoctorChatList record);

    int insertSelective(YhDoctorChatList record);

    YhDoctorChatList selectByPrimaryKey(Integer id);

    List<YhDoctorChatList> selectByPrimaryKeyAll();

    int updateByPrimaryKeySelective(YhDoctorChatList record);

    int updateByPrimaryKey(YhDoctorChatList record);
}