package com.yihe.applet.api.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhWxSetting;

public interface YhWxSettingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(YhWxSetting record);

    int insertSelective(YhWxSetting record);


    YhWxSetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhWxSetting record);

    int updateByPrimaryKey(YhWxSetting record);
}