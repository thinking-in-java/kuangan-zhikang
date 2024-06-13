package com.yihe.applet.api.domain;

public class YhUserDeviceApp {
    private Integer tid;

    private String terminalid;

    private String usercode;

    private String online;

    private String sosNos;

    private String note;

    private String terminaltype;

    private String terminalname;

    private String mobileNo;

    private String bindingtime;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid == null ? null : terminalid.trim();
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online == null ? null : online.trim();
    }

    public String getSosNos() {
        return sosNos;
    }

    public void setSosNos(String sosNos) {
        this.sosNos = sosNos == null ? null : sosNos.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getTerminaltype() {
        return terminaltype;
    }

    public void setTerminaltype(String terminaltype) {
        this.terminaltype = terminaltype == null ? null : terminaltype.trim();
    }

    public String getTerminalname() {
        return terminalname;
    }

    public void setTerminalname(String terminalname) {
        this.terminalname = terminalname == null ? null : terminalname.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getBindingtime() {
        return bindingtime;
    }

    public void setBindingtime(String bindingtime) {
        this.bindingtime = bindingtime == null ? null : bindingtime.trim();
    }
}