package com.yihe.system.domain;

public class YhPsychologicalAssessment {
    private Integer paId;

    private String paQue;

    private Integer paDel;

    private Integer paOften;

    private Integer paOccasional;

    private Integer paRare;

    private Integer paNever;

    public Integer getPaId() {
        return paId;
    }

    public void setPaId(Integer paId) {
        this.paId = paId;
    }

    public String getPaQue() {
        return paQue;
    }

    public void setPaQue(String paQue) {
        this.paQue = paQue == null ? null : paQue.trim();
    }

    public Integer getPaDel() {
        return paDel;
    }

    public void setPaDel(Integer paDel) {
        this.paDel = paDel;
    }

    public Integer getPaOften() {
        return paOften;
    }

    public void setPaOften(Integer paOften) {
        this.paOften = paOften;
    }

    public Integer getPaOccasional() {
        return paOccasional;
    }

    public void setPaOccasional(Integer paOccasional) {
        this.paOccasional = paOccasional;
    }

    public Integer getPaRare() {
        return paRare;
    }

    public void setPaRare(Integer paRare) {
        this.paRare = paRare;
    }

    public Integer getPaNever() {
        return paNever;
    }

    public void setPaNever(Integer paNever) {
        this.paNever = paNever;
    }
}