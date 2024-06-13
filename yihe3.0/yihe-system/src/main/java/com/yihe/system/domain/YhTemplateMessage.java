package com.yihe.system.domain;

public class YhTemplateMessage {
    private Integer tMId;

    private String tMContent;

    public Integer gettMId() {
        return tMId;
    }

    public void settMId(Integer tMId) {
        this.tMId = tMId;
    }

    public String gettMContent() {
        return tMContent;
    }

    public void settMContent(String tMContent) {
        this.tMContent = tMContent == null ? null : tMContent.trim();
    }
}