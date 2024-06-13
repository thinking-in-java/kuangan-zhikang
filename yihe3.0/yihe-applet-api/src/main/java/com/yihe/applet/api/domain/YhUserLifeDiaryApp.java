package com.yihe.applet.api.domain;

public class YhUserLifeDiaryApp {
    private Integer id;

    private String beginTime;

    private String finishTime;

    private Integer type;

    private String userCode;

    private String foodPracticalNum;

    private String foodRecommendHeat;

    private String foodPracticalHeat;

    private String sportPracticalNum;

    private String sportRecommendHeat;

    private String sportPracticalHeat;

    private String sleepWeek;

    private String sleepDuration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime == null ? null : finishTime.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getFoodPracticalNum() {
        return foodPracticalNum;
    }

    public void setFoodPracticalNum(String foodPracticalNum) {
        this.foodPracticalNum = foodPracticalNum == null ? null : foodPracticalNum.trim();
    }

    public String getFoodRecommendHeat() {
        return foodRecommendHeat;
    }

    public void setFoodRecommendHeat(String foodRecommendHeat) {
        this.foodRecommendHeat = foodRecommendHeat == null ? null : foodRecommendHeat.trim();
    }

    public String getFoodPracticalHeat() {
        return foodPracticalHeat;
    }

    public void setFoodPracticalHeat(String foodPracticalHeat) {
        this.foodPracticalHeat = foodPracticalHeat == null ? null : foodPracticalHeat.trim();
    }

    public String getSportPracticalNum() {
        return sportPracticalNum;
    }

    public void setSportPracticalNum(String sportPracticalNum) {
        this.sportPracticalNum = sportPracticalNum == null ? null : sportPracticalNum.trim();
    }

    public String getSportRecommendHeat() {
        return sportRecommendHeat;
    }

    public void setSportRecommendHeat(String sportRecommendHeat) {
        this.sportRecommendHeat = sportRecommendHeat == null ? null : sportRecommendHeat.trim();
    }

    public String getSportPracticalHeat() {
        return sportPracticalHeat;
    }

    public void setSportPracticalHeat(String sportPracticalHeat) {
        this.sportPracticalHeat = sportPracticalHeat == null ? null : sportPracticalHeat.trim();
    }

    public String getSleepWeek() {
        return sleepWeek;
    }

    public void setSleepWeek(String sleepWeek) {
        this.sleepWeek = sleepWeek == null ? null : sleepWeek.trim();
    }

    public String getSleepDuration() {
        return sleepDuration;
    }

    public void setSleepDuration(String sleepDuration) {
        this.sleepDuration = sleepDuration == null ? null : sleepDuration.trim();
    }
}