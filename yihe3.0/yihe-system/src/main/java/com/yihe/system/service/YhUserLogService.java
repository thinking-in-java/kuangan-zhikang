package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhUserLog;
import com.yihe.system.domain.YhUserLogExample;

/**
* YhUserLogService接口
* Created by lll on 2020/3/6.
*/
public interface YhUserLogService {
	 int insert(YhUserLog record);
	 List<YhUserLog> selectByExampleForOffsetPage(YhUserLogExample upmsLogExample,int offset,int limit);
	 long countByExample(YhUserLogExample upmsLogExample);
	 int deleteByPrimaryKeys(String ids);
}