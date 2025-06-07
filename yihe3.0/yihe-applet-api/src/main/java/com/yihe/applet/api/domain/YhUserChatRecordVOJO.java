package com.yihe.applet.api.domain;

import lombok.Data;

@Data
public class YhUserChatRecordVOJO {
    //消息id
    private String messageId;
    //消息体
    private YhUserChatRecordPayloadVOJO payload;
    //是否已读
    private Boolean read;
    //接收方
    private String receiverId;
    //发送方
    private String senderId;
    //消息状态
    private String status;
    //时间戳
    private Long timestamp;
    //消息类型
    private String type;
    //消息私聊1群聊2
    private Integer mtype;



}
