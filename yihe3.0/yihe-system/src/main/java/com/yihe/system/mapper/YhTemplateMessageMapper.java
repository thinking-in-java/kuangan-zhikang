package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhTemplateMessage;

public interface YhTemplateMessageMapper {
    int deleteByPrimaryKey(Integer tMId);

    int insert(YhTemplateMessage record);

    int insertSelective(YhTemplateMessage record);

    YhTemplateMessage selectByPrimaryKey(Integer tMId);

    int updateByPrimaryKeySelective(YhTemplateMessage record);

    int updateByPrimaryKey(YhTemplateMessage record);
    
    List<YhTemplateMessage> selectByMessage();
}