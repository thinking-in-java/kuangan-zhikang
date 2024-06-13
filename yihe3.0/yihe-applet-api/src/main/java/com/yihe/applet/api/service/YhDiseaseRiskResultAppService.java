package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhDiseaseRiskResultApp;
import com.yihe.applet.api.domain.YhDiseaseRiskResultNewApp;

/**
 * Description:
 * Created by Administrator on 2020/5/27 14:12
 */
public interface YhDiseaseRiskResultAppService {
    /*
        插入数据
         */
    int insertYhDiseaseRiskResult(YhDiseaseRiskResultApp yhDiseaseRiskResultApp);
    
    YhDiseaseRiskResultApp selectRiskResulByRequestNum(String RequestNum);

    /* 插入数据 */
    int insertYhDiseaseRiskResultNew(YhDiseaseRiskResultNewApp record);
}
