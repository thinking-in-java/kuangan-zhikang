package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhDiseaseRiskResultApp;
import com.yihe.applet.api.domain.YhDiseaseRiskResultNewApp;

/**
 * Description:
 * Created by Administrator on 2020/5/27 14:12
 */
public interface YhDiseaseRiskResultAppMapper {
    /*
    插入数据
     */
    int insertYhDiseaseRiskResult(YhDiseaseRiskResultApp yhDiseaseRiskResultApp);
    
    YhDiseaseRiskResultApp selectRiskResulByRequestNum(String RequestNum);

    int selectCount(YhDiseaseRiskResultNewApp record);

    int insertYhDiseaseRiskResultNew(YhDiseaseRiskResultNewApp yhDiseaseRiskResultApp);
}
