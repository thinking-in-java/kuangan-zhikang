package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhSportRecommendRecordApp;

/**
 * Description:运动改善推荐
 * Created by Administrator on 2020/5/28 14:09
 */
public interface YhSportRecommendRecordAppMapper {
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
