package com.yihe.system.service;

import com.yihe.system.domain.YhUserPersonageExamination;

public interface YhUserPersonageExaminationService {
	  YhUserPersonageExamination selectByPrimaryKey(String userCode);
	  int updateByPrimaryKey(YhUserPersonageExamination yhUserPersonageExamination);
	  int insertSelective(YhUserPersonageExamination yhUserPersonageExamination);
}
