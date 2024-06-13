package com.yihe.applet.api.service;


import com.yihe.applet.api.domain.YhQuestionCheckJsonApp;

/**
 * Description:
 * Created by Administrator on 2020/5/28 8:13
 */
public interface YhQuestionCheckJsonAppService {
    /*
    增
     */
    int insertYhQuestionCheckJson(YhQuestionCheckJsonApp yhQuestionCheckJsonApp);
    /*
    改
     */
    int updateYhQuestionCheckJson(YhQuestionCheckJsonApp yhQuestionCheckJsonApp);
    /*
    查
     */
    YhQuestionCheckJsonApp selectYhQuestionCheckJsonByUserCode(String userCode, Integer userCheckTs);
    
    int selectYhQuestionCheckCount();

    int delYhQuestionCheckJson(String userCode);
}
