package com.yihe.system.domain;

public class YhSportStrength {
    private Integer id;

    private String strengthName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrengthName() {
        return strengthName;
    }

    public void setStrengthName(String strengthName) {
        this.strengthName = strengthName == null ? null : strengthName.trim();
    }
}