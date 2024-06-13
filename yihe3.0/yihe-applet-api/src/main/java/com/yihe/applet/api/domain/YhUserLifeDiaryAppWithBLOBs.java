package com.yihe.applet.api.domain;

public class YhUserLifeDiaryAppWithBLOBs extends YhUserLifeDiaryApp {
    private String foodRationalNutrient;

    private String foodUnreasonableNutrient;

    private String foodRationalGuide;

    private String foodUnreasonableGuide;

    private String foodLanguage;

    private String sportRecommendContent;

    private String sportLanguage;

    private String sleepDurationContent;

    private String sleepTimeContent;

    private String sleepLanguage;

    public String getFoodRationalNutrient() {
        return foodRationalNutrient;
    }

    public void setFoodRationalNutrient(String foodRationalNutrient) {
        this.foodRationalNutrient = foodRationalNutrient == null ? null : foodRationalNutrient.trim();
    }

    public String getFoodUnreasonableNutrient() {
        return foodUnreasonableNutrient;
    }

    public void setFoodUnreasonableNutrient(String foodUnreasonableNutrient) {
        this.foodUnreasonableNutrient = foodUnreasonableNutrient == null ? null : foodUnreasonableNutrient.trim();
    }

    public String getFoodRationalGuide() {
        return foodRationalGuide;
    }

    public void setFoodRationalGuide(String foodRationalGuide) {
        this.foodRationalGuide = foodRationalGuide == null ? null : foodRationalGuide.trim();
    }

    public String getFoodUnreasonableGuide() {
        return foodUnreasonableGuide;
    }

    public void setFoodUnreasonableGuide(String foodUnreasonableGuide) {
        this.foodUnreasonableGuide = foodUnreasonableGuide == null ? null : foodUnreasonableGuide.trim();
    }

    public String getFoodLanguage() {
        return foodLanguage;
    }

    public void setFoodLanguage(String foodLanguage) {
        this.foodLanguage = foodLanguage == null ? null : foodLanguage.trim();
    }

    public String getSportRecommendContent() {
        return sportRecommendContent;
    }

    public void setSportRecommendContent(String sportRecommendContent) {
        this.sportRecommendContent = sportRecommendContent == null ? null : sportRecommendContent.trim();
    }

    public String getSportLanguage() {
        return sportLanguage;
    }

    public void setSportLanguage(String sportLanguage) {
        this.sportLanguage = sportLanguage == null ? null : sportLanguage.trim();
    }

    public String getSleepDurationContent() {
        return sleepDurationContent;
    }

    public void setSleepDurationContent(String sleepDurationContent) {
        this.sleepDurationContent = sleepDurationContent == null ? null : sleepDurationContent.trim();
    }

    public String getSleepTimeContent() {
        return sleepTimeContent;
    }

    public void setSleepTimeContent(String sleepTimeContent) {
        this.sleepTimeContent = sleepTimeContent == null ? null : sleepTimeContent.trim();
    }

    public String getSleepLanguage() {
        return sleepLanguage;
    }

    public void setSleepLanguage(String sleepLanguage) {
        this.sleepLanguage = sleepLanguage == null ? null : sleepLanguage.trim();
    }
}