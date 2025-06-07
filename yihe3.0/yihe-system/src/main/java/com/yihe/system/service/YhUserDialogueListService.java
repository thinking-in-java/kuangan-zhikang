package com.yihe.system.service;

import com.yihe.system.domain.ChatListByUserResqVo;

import java.util.List;

public interface YhUserDialogueListService {
    /**
     * 查询用户聊天会话列表
     */

    List<ChatListByUserResqVo> findSessionList(String usercode);

}
