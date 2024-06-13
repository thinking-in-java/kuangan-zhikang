package com.yihe.system.domain;

public class YhFoodCookedWeb {
    private Integer fCookedid;

    private String fCookedelements;

    private String foodmethod;

    public Integer getfCookedid() {
        return fCookedid;
    }

    public void setfCookedid(Integer fCookedid) {
        this.fCookedid = fCookedid;
    }

    public String getfCookedelements() {
        return fCookedelements;
    }

    public void setfCookedelements(String fCookedelements) {
        this.fCookedelements = fCookedelements == null ? null : fCookedelements.trim();
    }

    public String getFoodmethod() {
        return foodmethod;
    }

    public void setFoodmethod(String foodmethod) {
        this.foodmethod = foodmethod == null ? null : foodmethod.trim();
    }
}