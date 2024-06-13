package com.yihe.system.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserFoodWeeklyWeb;

public interface YhUserFoodWeeklyWebService {
	YhUserFoodWeeklyWeb selectByPrimaryKey(Integer wid);

	PageInfo<YhUserFoodWeeklyWeb> findFoodWeekLyByUserCode(String usercode, int pageNum, int pageSize);
}
