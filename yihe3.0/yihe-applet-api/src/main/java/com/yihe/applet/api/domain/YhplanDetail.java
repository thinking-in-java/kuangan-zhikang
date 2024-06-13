package com.yihe.applet.api.domain;

public class YhplanDetail {
    private String Usercode;
    private String weight;
    private String height;
    //能量
    private Double ennage;
    //疾病id
    private Integer diseassid;

    private String brethday;
    private String sex;
    private String laodong;

    public String getLaodong() {
        return laodong;
    }

    public void setLaodong(String laodong) {
        this.laodong = laodong;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrethday() {
        return brethday;
    }

    public void setBrethday(String brethday) {
        this.brethday = brethday;
    }

    public Integer getDiseassid() {
        return diseassid;
    }

    public void setDiseassid(Integer diseassid) {
        this.diseassid = diseassid;
    }

    public String getUsercode() {
        return Usercode;
    }

    public void setUsercode(String usercode) {
        Usercode = usercode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Double getEnnage() {
        return ennage;
    }

    public void setEnnage(Double ennage) {
        this.ennage = ennage;
    }
}
