package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhUserPersonageNonimmune;

public interface YhUserPersonageNonimmuneService {
		int deleteByPrimaryKey(Integer id);

	    int insert(YhUserPersonageNonimmune yhUserPersonageNonimmune);

	    List<YhUserPersonageNonimmune> selectByPrimaryKey(String userCode);

	    int updateByPrimaryKey(YhUserPersonageNonimmune yhUserPersonageNonimmune);
}
