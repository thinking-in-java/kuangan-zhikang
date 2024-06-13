package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhSportRecommendRecordApp;

/**
 * Description:
 * Created by Administrator on 2020/5/28 14:09
 */
public interface YhSportRecommendRecordAppService {
    /*
    增
     */
    int insertYhSportRecommendRecord(YhSportRecommendRecordApp yhSportRecommendRecordApp);
    /*
    根据疾病id与userCode查询是否已存在推荐，存在则改为过期状态
     */
    int uptYhSportRecommendRecord(YhSportRecommendRecordApp yhSportRecommendRecordApp);
    /*
    根据疾病id与userCode查询是否已存在推荐
     */
    int selYhSportRecordCount(YhSportRecommendRecordApp yhSportRecommendRecordApp);
}
