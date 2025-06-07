package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.DoctorXcxHomePageChat;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserChatRecord;

public interface YhUserChatRecordMapper {
	String findDoctorIdByUserCode(String usercode);
	
	List<YhUserChatRecord> findInfoList(@Param("list") List<String> id,@Param("pagenum")int pagenum,@Param("pagesize")int pagesize);
	
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserChatRecord record);

    int insertSelective(YhUserChatRecord record);
    
    int modifyRead1(@Param("usercode")String usercode,@Param("tousercode")String tousercode);

    YhUserChatRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserChatRecord record);

    int updateByPrimaryKey(YhUserChatRecord record);
    
    String findUserGroupByInfo(String usercode);

    List<DoctorXcxHomePageChat> latestChat(@Param("doctor")String doctor,@Param("limit") int limit);

    int doctorUnreadNum(String doctor);

}