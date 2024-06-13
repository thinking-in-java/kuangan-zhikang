package com.yihe.applet.api.domain;

import lombok.Data;

import java.util.List;

/**
 * Description:问卷题目表
 * Created by Administrator on 2020/5/22 17:50
 */
@Data
public class YhQuestionAssessTitleFrontApp {
    private int id;
    private String type;
    private String title;
    private String questionItemName;
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
    private int gender;
    private int delFlag;
    private List<YhQuestionAssessTitleFrontApp> childList;
    private int index;
    private int zIndex;
}
