package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhUserPersonageExaminationApp;

public interface YhUserPersonageExaminationAppService {
    int insertSelective(YhUserPersonageExaminationApp record);

    YhUserPersonageExaminationApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageExaminationApp record);

    int updateByPrimaryKey(YhUserPersonageExaminationApp record);
    YhUserPersonageExaminationApp showexamination(String userCode);
}
