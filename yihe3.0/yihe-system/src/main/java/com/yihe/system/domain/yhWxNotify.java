package com.yihe.system.domain;

public class yhWxNotify {
    private Integer wxNotifyId;

    private String wxNotifyContent;

    private String wxNotifyFile;

    private String wxNotifyBackup;

    private String wxNotifyBackup2;

    private String Usercode;

    private String send_date;

    private String wx_notify_type;

    private Integer count;

    private Integer start;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public String getWx_notify_type() {
        return wx_notify_type;
    }

    public void setWx_notify_type(String wx_notify_type) {
        this.wx_notify_type = wx_notify_type;
    }

    private String[] ids;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getUsercode() {
        return Usercode;
    }

    public void setUsercode(String usercode) {
        Usercode = usercode;
    }

    public Integer getWxNotifyId() {
        return wxNotifyId;
    }

    public void setWxNotifyId(Integer wxNotifyId) {
        this.wxNotifyId = wxNotifyId;
    }

    public String getWxNotifyContent() {
        return wxNotifyContent;
    }

    public void setWxNotifyContent(String wxNotifyContent) {
        this.wxNotifyContent = wxNotifyContent == null ? null : wxNotifyContent.trim();
    }

    public String getWxNotifyFile() {
        return wxNotifyFile;
    }

    public void setWxNotifyFile(String wxNotifyFile) {
        this.wxNotifyFile = wxNotifyFile == null ? null : wxNotifyFile.trim();
    }

    public String getWxNotifyBackup() {
        return wxNotifyBackup;
    }

    public void setWxNotifyBackup(String wxNotifyBackup) {
        this.wxNotifyBackup = wxNotifyBackup == null ? null : wxNotifyBackup.trim();
    }

    public String getWxNotifyBackup2() {
        return wxNotifyBackup2;
    }

    public void setWxNotifyBackup2(String wxNotifyBackup2) {
        this.wxNotifyBackup2 = wxNotifyBackup2 == null ? null : wxNotifyBackup2.trim();
    }
}