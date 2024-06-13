package com.yihe.applet.api.domain;

import java.util.List;

public class Smokequestion {
    private int id;
    private String title;
    private int zIndex;
    private List<YhQuestionSmokingSon> sonquestion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public List<YhQuestionSmokingSon> getSonquestion() {
        return sonquestion;
    }

    public void setSonquestion(List<YhQuestionSmokingSon> sonquestion) {
        this.sonquestion = sonquestion;
    }
}
