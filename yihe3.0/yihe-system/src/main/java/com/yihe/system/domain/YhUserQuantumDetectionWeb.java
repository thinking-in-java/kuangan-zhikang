package com.yihe.system.domain;

public class YhUserQuantumDetectionWeb {
    private Integer qid;

    private String img;

    private String qtime;

    private String usercode;

    private Integer isdel;

    private String resurtText;

    private int insertcount;

    private String dataSource;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getResurtText() {
        return resurtText;
    }

    public void setResurtText(String resurtText) {
        this.resurtText = resurtText;
    }

    public int getInsertcount() {
        return insertcount;
    }

    public void setInsertcount(int insertcount) {
        this.insertcount = insertcount;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getQtime() {
        return qtime;
    }

    public void setQtime(String qtime) {
        this.qtime = qtime == null ? null : qtime.trim();
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
}