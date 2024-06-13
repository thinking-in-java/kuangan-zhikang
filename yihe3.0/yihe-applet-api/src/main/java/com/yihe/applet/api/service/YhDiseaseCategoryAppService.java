package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhDiseaseCategoryApp;

import java.util.List;

/**
 * Description:
 * Created by Administrator on 2020/5/26 14:08
 */
public interface YhDiseaseCategoryAppService {
    /*
    查询前十条记录
     */
    List<YhDiseaseCategoryApp> selTopTenData(int diseaseCode);
    /*
    根据疾病id查询name
     */
    YhDiseaseCategoryApp selCategoryByCode(int diseaseCode);

    List<YhDiseaseCategoryApp> newFindDis(int sex);
}
