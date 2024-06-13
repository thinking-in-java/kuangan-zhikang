package com.yihe.applet.api.domain;

public class YhMemberGrade {
    private Integer id;
    private Integer vipGrade;
    private Integer minEnergy;
    private Integer maxEnergy;
    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipGrade() {
        return vipGrade;
    }

    public void setVipGrade(Integer vipGrade) {
        this.vipGrade = vipGrade;
    }

    public Integer getMinEnergy() {
        return minEnergy;
    }

    public void setMinEnergy(Integer minEnergy) {
        this.minEnergy = minEnergy;
    }

    public Integer getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(Integer maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
