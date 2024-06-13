package com.yihe.system.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserChatRecordWed;

public interface YhUserChatRecordWedService {
	
	Integer findWXuserStatus(String usercode); 
	PageInfo<YhUserChatRecordWed> findChatInfo(String usercode,Integer read,String name,Integer pageNum,Integer pageSize);
	
	List<YhUserChatRecordWed> findChatInfoByUserCode(String usercode,String tousercode);
	
	 int insertSelective(YhUserChatRecordWed record);
	 
	 
	 int modifyRead1(String usercode,String tousercode);
}
