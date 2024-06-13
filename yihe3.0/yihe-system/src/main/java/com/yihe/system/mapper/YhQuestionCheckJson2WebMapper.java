package com.yihe.system.mapper;

import com.yihe.system.domain.YhQuestionCheckJson2Web;
 
public interface YhQuestionCheckJson2WebMapper {
	
	YhQuestionCheckJson2Web selectByRsLimit1(String rs);
    int deleteByPrimaryKey(Integer id);

    int insert(YhQuestionCheckJson2Web record);

    int insertSelective(YhQuestionCheckJson2Web record);

    YhQuestionCheckJson2Web selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhQuestionCheckJson2Web record);

    int updateByPrimaryKey(YhQuestionCheckJson2Web record);
    
    YhQuestionCheckJson2Web selectUserByParam(String userCode);
}