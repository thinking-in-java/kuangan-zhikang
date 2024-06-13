package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhHomeinfoConfig;

public interface YhHomeinfoConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhHomeinfoConfig record);

    int insertSelective(YhHomeinfoConfig record);

    YhHomeinfoConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhHomeinfoConfig record);

    int updateByPrimaryKey(YhHomeinfoConfig record);
    
    List<YhHomeinfoConfig> selectByslideshow();
    
    int selectByslideshownumber();
    
    List<YhHomeinfoConfig> selectByheadline();
    
    int selectByheadlinenumber();
    
    YhHomeinfoConfig amendinfotip(int infoTip);
    
    String InfoTipMax();
    
    List<YhHomeinfoConfig> selectByOrder();
}