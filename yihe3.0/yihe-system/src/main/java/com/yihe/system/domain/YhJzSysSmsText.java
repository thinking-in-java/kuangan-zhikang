package com.yihe.system.domain;

public class YhJzSysSmsText {
    private Integer tId;

    private Integer tSmsid;

    private String tText;

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Integer gettSmsid() {
        return tSmsid;
    }

    public void settSmsid(Integer tSmsid) {
        this.tSmsid = tSmsid;
    }

    public String gettText() {
        return tText;
    }

    public void settText(String tText) {
        this.tText = tText == null ? null : tText.trim();
    }
}