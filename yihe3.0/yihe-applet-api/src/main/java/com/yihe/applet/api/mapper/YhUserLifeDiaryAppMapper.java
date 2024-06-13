package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserLifeDiaryApp;
import com.yihe.applet.api.domain.YhUserLifeDiaryAppWithBLOBs;

public interface YhUserLifeDiaryAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserLifeDiaryAppWithBLOBs record);

    int insertSelective(YhUserLifeDiaryAppWithBLOBs record);

    YhUserLifeDiaryAppWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserLifeDiaryAppWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(YhUserLifeDiaryAppWithBLOBs record);

    int updateByPrimaryKey(YhUserLifeDiaryApp record);
}