package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserNotifyApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhUserNotifyAppMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YhUserNotifyApp record);

    int insertSelective(YhUserNotifyApp record);

    YhUserNotifyApp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YhUserNotifyApp record);

    int updateByPrimaryKey(YhUserNotifyApp record);
    
 List<YhUserNotifyApp> showInteraction(@Param("userId") String userId, @Param("type") Integer type);
    
    List<YhUserNotifyApp> showNotifyType (Map<String, Object> map);
    
    List<YhUserNotifyApp> selectAwardInform (@Param("userId") String userId, @Param("type") Integer type);
    
    int deleteByid(Long id);
    
    int selectIsRead(@Param("isRead") Integer isRead,@Param("userId") String userId);
    
    List<YhUserNotifyApp> selectcomment(String userId);

    List<YhUserNotifyApp> selecthabit(String userId);

    List<YhUserNotifyApp> selectdianzan(String userId);
}