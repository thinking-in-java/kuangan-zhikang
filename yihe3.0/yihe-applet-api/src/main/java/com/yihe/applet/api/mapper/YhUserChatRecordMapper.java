package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserChatRecord;

public interface YhUserChatRecordMapper {
	String findDoctorIdByUserCode(String usercode);
	
	List<YhUserChatRecord> findInfoList(List<String> id);
	
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserChatRecord record);

    int insertSelective(YhUserChatRecord record);
    
    int modifyRead1(@Param("usercode")String usercode,@Param("tousercode")String tousercode);

    YhUserChatRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserChatRecord record);

    int updateByPrimaryKey(YhUserChatRecord record);
    
    String findUserGroupByInfo(String usercode);
}