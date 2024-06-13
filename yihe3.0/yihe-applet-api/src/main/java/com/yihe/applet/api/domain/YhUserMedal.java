package com.yihe.applet.api.domain;

import java.util.Date;

public class YhUserMedal {
    private Integer medalId;

    private String medalName;

    private Integer medalType;

    private Integer medalStype;

    private String medalImg;

    private Integer medalNum;

    private Integer medalLevel;

    private Integer medalNum1;

    private Integer medalNum2;

    private String userCode;

    private String mealTime;

    public Integer getMedalId() {
        return medalId;
    }

    public void setMedalId(Integer medalId) {
        this.medalId = medalId;
    }

    public String getMedalName() {
        return medalName;
    }

    public void setMedalName(String medalName) {
        this.medalName = medalName == null ? null : medalName.trim();
    }

    public Integer getMedalType() {
        return medalType;
    }

    public void setMedalType(Integer medalType) {
        this.medalType = medalType;
    }

    public Integer getMedalStype() {
        return medalStype;
    }

    public void setMedalStype(Integer medalStype) {
        this.medalStype = medalStype;
    }

    public String getMedalImg() {
        return medalImg;
    }

    public void setMedalImg(String medalImg) {
        this.medalImg = medalImg == null ? null : medalImg.trim();
    }

    public Integer getMedalNum() {
        return medalNum;
    }

    public void setMedalNum(Integer medalNum) {
        this.medalNum = medalNum;
    }

    public Integer getMedalLevel() {
        return medalLevel;
    }

    public void setMedalLevel(Integer medalLevel) {
        this.medalLevel = medalLevel;
    }

    public Integer getMedalNum1() {
        return medalNum1;
    }

    public void setMedalNum1(Integer medalNum1) {
        this.medalNum1 = medalNum1;
    }

    public Integer getMedalNum2() {
        return medalNum2;
    }

    public void setMedalNum2(Integer medalNum2) {
        this.medalNum2 = medalNum2;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }
}