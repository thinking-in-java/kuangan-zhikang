package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserDialogueList;

public interface YhUserDialogueListMapper {
	
	
	List<YhUserDialogueList> findListInfo(String usercode);
	
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserDialogueList record);

    int insertSelective(YhUserDialogueList record);

    YhUserDialogueList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserDialogueList record);

    int updateByPrimaryKey(YhUserDialogueList record);
}