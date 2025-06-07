package com.yihe.system.domain;

import lombok.Data;

@Data
public class ChatListByUserResqVo {
    private String type;
    private String docId;
    private String postCode;
    private String gender;
    private YhUserChatRecordWed chat;
    private String info;
}
