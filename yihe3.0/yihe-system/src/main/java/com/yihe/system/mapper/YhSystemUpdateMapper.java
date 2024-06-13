package com.yihe.system.mapper;

import com.yihe.system.domain.YhSystemUpdate;

public interface YhSystemUpdateMapper {
	YhSystemUpdate selectsystemupdate();
	int updatedate(YhSystemUpdate yhsystemupdate);
	int insertupdate(YhSystemUpdate yhsystemupdate);
	int updatechexiao(YhSystemUpdate yhsystemupdate);
	int updatedatefabu(YhSystemUpdate yhsystemupdate);
	int insertupdatefabu(YhSystemUpdate yhsystemupdate);
}
