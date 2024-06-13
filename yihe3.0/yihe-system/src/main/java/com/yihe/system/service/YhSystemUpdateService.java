package com.yihe.system.service;

import com.yihe.system.domain.YhSystemUpdate;

public interface YhSystemUpdateService{
		YhSystemUpdate selectsystemupdate();
		int updatedate(YhSystemUpdate yhsystemupdate);
		int updatechexiao(YhSystemUpdate yhsystemupdate);
		int updatefabu(YhSystemUpdate yhsystemupdate);
}
