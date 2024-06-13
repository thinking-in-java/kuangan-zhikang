package com.yihe.applet.api.domain;

import java.util.Date;

public class YhUserSportChRecordApp {
    private Integer id;

    private Integer chId;

    private Date uploadTome;

    private String step;

    private String whether;

    private String reach;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChId() {
        return chId;
    }

    public void setChId(Integer chId) {
        this.chId = chId;
    }

    public Date getUploadTome() {
        return uploadTome;
    }

    public void setUploadTome(Date uploadTome) {
        this.uploadTome = uploadTome;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step == null ? null : step.trim();
    }

    public String getWhether() {
        return whether;
    }

    public void setWhether(String whether) {
        this.whether = whether == null ? null : whether.trim();
    }

    public String getReach() {
        return reach;
    }

    public void setReach(String reach) {
        this.reach = reach == null ? null : reach.trim();
    }
}