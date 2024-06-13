package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhQuestionAssessTitleFrontApp;

import java.util.List;

/**
 * Description:
 * Created by Administrator on 2020/5/22 17:57
 */
public interface YhQuestionAssessTitleFrontAppService {
    /*
    查询所有问卷
     */
    List<YhQuestionAssessTitleFrontApp> selAllQuestion(int gender);
}
