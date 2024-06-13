package com.yihe.applet.api.domain;

import java.util.List;

public class YhGiveupSmokingDirectory {
    private Integer secret_Id;
    private String name;
    private String parent_id;
    private Integer sort_id;
    private String tip_one;
    private Integer watch;
    private List<YhGiveupSmokingDirectory> yhGiveupSmokingDirectory;

    public Integer getWatch() {
        return watch;
    }

    public void setWatch(Integer watch) {
        this.watch = watch;
    }

    public String getTip_one() {
        return tip_one;
    }

    public void setTip_one(String tip_one) {
        this.tip_one = tip_one;
    }

    public Integer getSecret_Id() {
        return secret_Id;
    }

    public void setSecret_Id(Integer secret_Id) {
        this.secret_Id = secret_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public Integer getSort_id() {
        return sort_id;
    }

    public void setSort_id(Integer sort_id) {
        this.sort_id = sort_id;
    }

    public List<YhGiveupSmokingDirectory> getYhGiveupSmokingDirectory() {
        return yhGiveupSmokingDirectory;
    }

    public void setYhGiveupSmokingDirectory(List<YhGiveupSmokingDirectory> yhGiveupSmokingDirectory) {
        this.yhGiveupSmokingDirectory = yhGiveupSmokingDirectory;
    }
}
