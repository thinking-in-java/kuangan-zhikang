package com.yihe.system.domain;

public class YhSportGoals {
    private Integer id;

    private String goalsName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoalsName() {
        return goalsName;
    }

    public void setGoalsName(String goalsName) {
        this.goalsName = goalsName == null ? null : goalsName.trim();
    }
}