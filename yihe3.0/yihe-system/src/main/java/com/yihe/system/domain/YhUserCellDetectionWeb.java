package com.yihe.system.domain;

public class YhUserCellDetectionWeb {
    private Integer cid;

    private String img;

    private String ctime;

    private String usercode;

    private Integer isdel;

    private String resurtText;

    private String dataSource;

    private int insertcount;

    public int getInsertcount() {
        return insertcount;
    }

    public void setInsertcount(int insertcount) {
        this.insertcount = insertcount;
    }

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

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime == null ? null : ctime.trim();
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