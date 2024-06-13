package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserSportChRecordApp;
import com.yihe.system.domain.YhUserReginfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhUserSportChRecordAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserSportChRecordApp record);

    int insertSelective(YhUserSportChRecordApp record);

    YhUserSportChRecordApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserSportChRecordApp record);

    int updateByPrimaryKey(YhUserSportChRecordApp record);
    
    List<YhUserSportChRecordApp> showChidRecord(int chId);
    
    YhUserSportChRecordApp showIploadTomeCh(String uploadTome);
    
    YhUserSportChRecordApp showChidRecordMap(Map<String, Object> map);
    
    int selectByWhetherCount(int chId);

    /**
     * 获取用户userCode
     * @param chId `yh_user_sport_challenge`表的`ch_id`字段
     * @param currentTime 当前时间 yyyy-MM-dd
     * @return 用户实体
     */
    YhUserReginfo selectByChId(@Param("chId") int chId, @Param("currentTime") String currentTime);
}