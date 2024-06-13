package com.yihe.applet.api.domain;

import com.yihe.system.utils.Data;

public class YhUserSmoking {
    private Integer smoking_id;
    private String user_code;
    private Integer smoking_age;
    private Integer smoking_amount;
    private Double smoking_prices;
    private String smoking_tar_content;
    private String smoking_amount_time;
    private String smoking_quit_times;
    private String smoking_days;
    private String smoking_remark;
    private Integer log_state;
    private String smoking_date;
    private String birth_date;
    private String head_img;
    private String name;

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getSmoking_date() {
        return smoking_date;
    }

    public void setSmoking_date(String smoking_date) {
        this.smoking_date = smoking_date;
    }

    public Integer getSmoking_id() {
        return smoking_id;
    }

    public void setSmoking_id(Integer smoking_id) {
        this.smoking_id = smoking_id;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public Integer getSmoking_age() {
        return smoking_age;
    }

    public void setSmoking_age(Integer smoking_age) {
        this.smoking_age = smoking_age;
    }

    public Integer getSmoking_amount() {
        return smoking_amount;
    }

    public void setSmoking_amount(Integer smoking_amount) {
        this.smoking_amount = smoking_amount;
    }

    public Double getSmoking_prices() {
        return smoking_prices;
    }

    public void setSmoking_prices(Double smoking_prices) {
        this.smoking_prices = smoking_prices;
    }

    public String getSmoking_tar_content() {
        return smoking_tar_content;
    }

    public void setSmoking_tar_content(String smoking_tar_content) {
        this.smoking_tar_content = smoking_tar_content;
    }

    public String getSmoking_amount_time() {
        return smoking_amount_time;
    }

    public void setSmoking_amount_time(String smoking_amount_time) {
        this.smoking_amount_time = smoking_amount_time;
    }

    public String getSmoking_quit_times() {
        return smoking_quit_times;
    }

    public void setSmoking_quit_times(String smoking_quit_times) {
        this.smoking_quit_times = smoking_quit_times;
    }

    public String getSmoking_days() {
        return smoking_days;
    }

    public void setSmoking_days(String smoking_days) {
        this.smoking_days = smoking_days;
    }

    public String getSmoking_remark() {
        return smoking_remark;
    }

    public void setSmoking_remark(String smoking_remark) {
        this.smoking_remark = smoking_remark;
    }

    public Integer getLog_state() {
        return log_state;
    }

    public void setLog_state(Integer log_state) {
        this.log_state = log_state;
    }
}
