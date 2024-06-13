package com.yihe.system.domain;

public class YhUserPersonageCondition {
    private Integer id;

    private String userCode;

    private String waist;

    private String habitas;

    private String breathe;

    private String stature;

    private String weight;

    private String waistline;

    private String hipline;

    private String heat;

    private String palse;

    private String dbp;

    private String sbp;

    private String hrrest;

    private String addtime;

    private String updatetime;

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

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist == null ? null : waist.trim();
    }

    public String getHabitas() {
        return habitas;
    }

    public void setHabitas(String habitas) {
        this.habitas = habitas == null ? null : habitas.trim();
    }

    public String getBreathe() {
        return breathe;
    }

    public void setBreathe(String breathe) {
        this.breathe = breathe == null ? null : breathe.trim();
    }

    public String getStature() {
        return stature;
    }

    public void setStature(String stature) {
        this.stature = stature == null ? null : stature.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline == null ? null : waistline.trim();
    }

    public String getHipline() {
        return hipline;
    }

    public void setHipline(String hipline) {
        this.hipline = hipline == null ? null : hipline.trim();
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat == null ? null : heat.trim();
    }

    public String getPalse() {
        return palse;
    }

    public void setPalse(String palse) {
        this.palse = palse == null ? null : palse.trim();
    }

    public String getDbp() {
        return dbp;
    }

    public void setDbp(String dbp) {
        this.dbp = dbp == null ? null : dbp.trim();
    }

    public String getSbp() {
        return sbp;
    }

    public void setSbp(String sbp) {
        this.sbp = sbp == null ? null : sbp.trim();
    }

    public String getHrrest() {
        return hrrest;
    }

    public void setHrrest(String hrrest) {
        this.hrrest = hrrest == null ? null : hrrest.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }
}