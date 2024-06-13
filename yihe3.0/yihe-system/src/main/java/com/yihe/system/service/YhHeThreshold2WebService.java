package com.yihe.system.service;

import com.yihe.system.domain.YhHeThreshold2Web;

public interface YhHeThreshold2WebService {
	 YhHeThreshold2Web selectUserYhHeThreshold2(String userCode);
	 int updateByPrimaryKeySelective(YhHeThreshold2Web record);
	 int insertSelective(YhHeThreshold2Web record);
	 int deleteByPrimaryKey(String userCode);
}
