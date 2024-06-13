package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserChatRecordWed;

public interface YhUserChatRecordWedMapper {
	
	String findChatRecordByAddTime(@Param("usercode")String usercode);
	 
	Integer findWXuserStatus(@Param("usercode")String usercode); 
	
	List<YhUserChatRecordWed> findChatInfoByUserCode(@Param("usercode")String usercode,@Param("tousercode")String tousercode);
	
	List<YhUserChatRecordWed> getList(@Param("roomid")String roomid,@Param("roomid2")String roomid2,@Param("lastId")Integer lastId);
	
	List<YhUserChatRecordWed> findChatInfo(@Param("usercode")String usercode,@Param("read")Integer read,@Param("name")String name);
	
	int modifyRead1(@Param("usercode")String usercode,@Param("tousercode")String tousercode);
	
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserChatRecordWed record);

    int insertSelective(YhUserChatRecordWed record);

    YhUserChatRecordWed selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserChatRecordWed record);

    int updateByPrimaryKey(YhUserChatRecordWed record);
}