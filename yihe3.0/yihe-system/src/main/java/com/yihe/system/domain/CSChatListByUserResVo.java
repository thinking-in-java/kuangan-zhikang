package com.yihe.system.domain;

import lombok.Data;

@Data
public class CSChatListByUserResVo {
    private String type;
    private String docId;
    private String postCode;
    private String gender;
    private CSUserChatRecordWed chat;
    private String info;
}
