package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserArtery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhUserArteryService {
	YhUserArtery selectByPrimaryKeyNewest(String usercode);
	 public PageInfo<YhUserArtery> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	 YhUserArtery selectUserYhUserArteryByLimitParm(String userCode);
	YhUserArtery	findNewInfoByUserCode(String usercode);

	List<YhUserArtery> findArteryAllByUserCode(String usercode, Integer timetype, String strTime, String endTime);

	int updateByPrimaryKeySelective(YhUserArtery record);

	int insertSelective(YhUserArtery record);

	List<YhUserArtery> selectByPrimaryKeyFindEntryTime(String usercode);

	List<YhUserArtery> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode);

	YhUserArtery selectByPrimaryKey(Integer id);


}
