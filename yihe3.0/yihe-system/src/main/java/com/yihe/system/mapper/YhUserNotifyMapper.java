package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserNotify;
import com.yihe.system.domain.YhUserNotifyVo;

public interface YhUserNotifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YhUserNotify record);

    int insertSelective(YhUserNotifyVo record);

    int insertnotify(YhUserNotify record);

    YhUserNotify selectByPrimaryKey(Long id);
    
    YhUserNotifyVo selectByPrimaryKeyVo(Long id);
    

    int updateByPrimaryKeySelective(YhUserNotify record);

    int updateByPrimaryKey(YhUserNotify record);
}