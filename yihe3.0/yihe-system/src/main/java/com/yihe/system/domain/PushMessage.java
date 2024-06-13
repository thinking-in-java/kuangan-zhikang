package com.yihe.system.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushMessage {
    //"登录用户编号"
    private String loginUserNum;

    //(value = "推送内容")
    private String content;

    public String getLoginUserNum() {
        return loginUserNum;
    }

    public void setLoginUserNum(String loginUserNum) {
        this.loginUserNum = loginUserNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "loginUserNum=" + loginUserNum +
                ", content='" + content + '\'' +
                '}';
    }
}
