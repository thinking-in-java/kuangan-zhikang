package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhHealthStarApp;

import java.util.List;
import java.util.Map;

public interface YhHealthStarAppService {
    String selectRecordtypeAndTime(Map<String, Object> map);

    String selectRecordtypeAndTime2(Map<String, Object> map);

    int insertSelective(YhHealthStarApp record);

    List<YhHealthStarApp> selectYhHealthStarAppParam(Integer page, Integer pageSize, String userCode, Integer type);

    public int integralCompletioStatus(String eventType, String userCode);

    YhHealthStarApp selectNewestStar(String userCode, String healthstarsEvents);

    int selectUserContinuousRecord(String userCode, String healthstarsEvents);

    List<YhHealthStarApp> selectUserYhHealthStar(String userCode, Integer type);

    Integer selectUserYhHealthStarSum(String userCode, Integer type);

}
