package com.yihe.system.domain;

public class YhUserPersonageConstitution {
    private Integer id;

    private String userCode;

    private String peaceQuality;

    private String deficiencyQuality;

    private String sunQuality;

    private String cloudyQuality;

    private String phlegmQuality;

    private String dampQuality;

    private String bloodQuality;

    private String gasQuality;

    private String specialQuality;

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

    public String getPeaceQuality() {
        return peaceQuality;
    }

    public void setPeaceQuality(String peaceQuality) {
        this.peaceQuality = peaceQuality == null ? null : peaceQuality.trim();
    }

    public String getDeficiencyQuality() {
        return deficiencyQuality;
    }

    public void setDeficiencyQuality(String deficiencyQuality) {
        this.deficiencyQuality = deficiencyQuality == null ? null : deficiencyQuality.trim();
    }

    public String getSunQuality() {
        return sunQuality;
    }

    public void setSunQuality(String sunQuality) {
        this.sunQuality = sunQuality == null ? null : sunQuality.trim();
    }

    public String getCloudyQuality() {
        return cloudyQuality;
    }

    public void setCloudyQuality(String cloudyQuality) {
        this.cloudyQuality = cloudyQuality == null ? null : cloudyQuality.trim();
    }

    public String getPhlegmQuality() {
        return phlegmQuality;
    }

    public void setPhlegmQuality(String phlegmQuality) {
        this.phlegmQuality = phlegmQuality == null ? null : phlegmQuality.trim();
    }

    public String getDampQuality() {
        return dampQuality;
    }

    public void setDampQuality(String dampQuality) {
        this.dampQuality = dampQuality == null ? null : dampQuality.trim();
    }

    public String getBloodQuality() {
        return bloodQuality;
    }

    public void setBloodQuality(String bloodQuality) {
        this.bloodQuality = bloodQuality == null ? null : bloodQuality.trim();
    }

    public String getGasQuality() {
        return gasQuality;
    }

    public void setGasQuality(String gasQuality) {
        this.gasQuality = gasQuality == null ? null : gasQuality.trim();
    }

    public String getSpecialQuality() {
        return specialQuality;
    }

    public void setSpecialQuality(String specialQuality) {
        this.specialQuality = specialQuality == null ? null : specialQuality.trim();
    }
}