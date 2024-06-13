package com.yihe.applet.api.domain;

public class YhQuestionPainTitleApp {
    private Integer id;

    private String type;

    private String title;

    private String titleImg;

    private String choose1;

    private String choose1Value;

    private String choose2;

    private String choose2Value;

    private String choose3;

    private String choose3Value;

    private String choose4;

    private String choose4Value;

    private String choose5;

    private String choose5Value;

    private String choose6;

    private String choose6Value;

    private String parentId;

    private Integer painId;

    private Integer delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg == null ? null : titleImg.trim();
    }

    public String getChoose1() {
        return choose1;
    }

    public void setChoose1(String choose1) {
        this.choose1 = choose1 == null ? null : choose1.trim();
    }

    public String getChoose1Value() {
        return choose1Value;
    }

    public void setChoose1Value(String choose1Value) {
        this.choose1Value = choose1Value == null ? null : choose1Value.trim();
    }

    public String getChoose2() {
        return choose2;
    }

    public void setChoose2(String choose2) {
        this.choose2 = choose2 == null ? null : choose2.trim();
    }

    public String getChoose2Value() {
        return choose2Value;
    }

    public void setChoose2Value(String choose2Value) {
        this.choose2Value = choose2Value == null ? null : choose2Value.trim();
    }

    public String getChoose3() {
        return choose3;
    }

    public void setChoose3(String choose3) {
        this.choose3 = choose3 == null ? null : choose3.trim();
    }

    public String getChoose3Value() {
        return choose3Value;
    }

    public void setChoose3Value(String choose3Value) {
        this.choose3Value = choose3Value == null ? null : choose3Value.trim();
    }

    public String getChoose4() {
        return choose4;
    }

    public void setChoose4(String choose4) {
        this.choose4 = choose4 == null ? null : choose4.trim();
    }

    public String getChoose4Value() {
        return choose4Value;
    }

    public void setChoose4Value(String choose4Value) {
        this.choose4Value = choose4Value == null ? null : choose4Value.trim();
    }

    public String getChoose5() {
        return choose5;
    }

    public void setChoose5(String choose5) {
        this.choose5 = choose5 == null ? null : choose5.trim();
    }

    public String getChoose5Value() {
        return choose5Value;
    }

    public void setChoose5Value(String choose5Value) {
        this.choose5Value = choose5Value == null ? null : choose5Value.trim();
    }

    public String getChoose6() {
        return choose6;
    }

    public void setChoose6(String choose6) {
        this.choose6 = choose6 == null ? null : choose6.trim();
    }

    public String getChoose6Value() {
        return choose6Value;
    }

    public void setChoose6Value(String choose6Value) {
        this.choose6Value = choose6Value == null ? null : choose6Value.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getPainId() {
        return painId;
    }

    public void setPainId(Integer painId) {
        this.painId = painId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}