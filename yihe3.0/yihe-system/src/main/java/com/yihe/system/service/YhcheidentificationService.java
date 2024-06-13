package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhChineseAnwserr;

public interface YhcheidentificationService {
    YhChineseAnwserr selectByPrimaryKey(Integer reportId);
    PageInfo<YhChineseAnwserr> queryUserByCriteriaWithPage(String userCode, String qtstrTime, String qtendTime, int page, int limit);
    PageInfo<YhChineseAnwserr> queryAllUserByCriteriaWithPage(String symptom, String orgCode, String name, String userCode, String qtstrTime, String qtendTime, int page, int limit);
}
