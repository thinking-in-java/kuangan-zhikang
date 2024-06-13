package com.yihe.system.domain;

import java.util.Date;

public class YhQuestionCheckJson2Web {
    private Integer id;

    private String userCode;

    private String userCheckAnswer;

    private Integer userCheckIndex;

    private Integer userCheckTs;

    private Date userCheckTime;

    private String requestSerialnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserCheckAnswer() {
        return userCheckAnswer;
    }

    public void setUserCheckAnswer(String userCheckAnswer) {
        this.userCheckAnswer = userCheckAnswer == null ? null : userCheckAnswer.trim();
    }

    public Integer getUserCheckIndex() {
        return userCheckIndex;
    }

    public void setUserCheckIndex(Integer userCheckIndex) {
        this.userCheckIndex = userCheckIndex;
    }

    public Integer getUserCheckTs() {
        return userCheckTs;
    }

    public void setUserCheckTs(Integer userCheckTs) {
        this.userCheckTs = userCheckTs;
    }

    public Date getUserCheckTime() {
        return userCheckTime;
    }

    public void setUserCheckTime(Date userCheckTime) {
        this.userCheckTime = userCheckTime;
    }

    public String getRequestSerialnum() {
        return requestSerialnum;
    }

    public void setRequestSerialnum(String requestSerialnum) {
        this.requestSerialnum = requestSerialnum == null ? null : requestSerialnum.trim();
    }
}