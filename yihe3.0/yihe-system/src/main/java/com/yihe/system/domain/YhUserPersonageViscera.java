package com.yihe.system.domain;

public class YhUserPersonageViscera {
    private Integer id;

    private String userCode;

    private String lips;

    private String tooth;

    private String throat;

    private String visionleft;

    private String visionright;

    private String correctleft;

    private String correctright;

    private String audiometry;

    private String sportssituation;

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

    public String getLips() {
        return lips;
    }

    public void setLips(String lips) {
        this.lips = lips == null ? null : lips.trim();
    }

    public String getTooth() {
        return tooth;
    }

    public void setTooth(String tooth) {
        this.tooth = tooth == null ? null : tooth.trim();
    }

    public String getThroat() {
        return throat;
    }

    public void setThroat(String throat) {
        this.throat = throat == null ? null : throat.trim();
    }

    public String getVisionleft() {
        return visionleft;
    }

    public void setVisionleft(String visionleft) {
        this.visionleft = visionleft == null ? null : visionleft.trim();
    }

    public String getVisionright() {
        return visionright;
    }

    public void setVisionright(String visionright) {
        this.visionright = visionright == null ? null : visionright.trim();
    }

    public String getCorrectleft() {
        return correctleft;
    }

    public void setCorrectleft(String correctleft) {
        this.correctleft = correctleft == null ? null : correctleft.trim();
    }

    public String getCorrectright() {
        return correctright;
    }

    public void setCorrectright(String correctright) {
        this.correctright = correctright == null ? null : correctright.trim();
    }

    public String getAudiometry() {
        return audiometry;
    }

    public void setAudiometry(String audiometry) {
        this.audiometry = audiometry == null ? null : audiometry.trim();
    }

    public String getSportssituation() {
        return sportssituation;
    }

    public void setSportssituation(String sportssituation) {
        this.sportssituation = sportssituation == null ? null : sportssituation.trim();
    }
}