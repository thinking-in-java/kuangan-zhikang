package com.yihe.applet.api.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhUserSportChallengeApp;

public interface YhUserSportChallengeAppMapper {
    int deleteByPrimaryKey(Integer chId);

    int insert(YhUserSportChallengeApp record);

    int insertSelective(YhUserSportChallengeApp record);

    YhUserSportChallengeApp selectByPrimaryKey(Integer chId);

    int updateByPrimaryKeySelective(YhUserSportChallengeApp record);

    int updateByPrimaryKey(YhUserSportChallengeApp record);
    
    YhUserSportChallengeApp selectByCodeDnow(String userCode);
    
    List<YhUserSportChallengeApp> selectByCodeXnow(Map<String,Object> map);
    
    List<YhUserSportChallengeApp> selectByDnow(Map<String,Object> map);
    
    int showChWhetherNow(Map<String,Object> map);
}