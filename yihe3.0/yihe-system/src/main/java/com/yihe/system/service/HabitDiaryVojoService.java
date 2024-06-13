package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.HabitDiaryVojo;

public interface HabitDiaryVojoService {
	
	PageInfo<HabitDiaryVojo> selectHabitDiaryUserAll(String usercode,String nameLike,String usernameLike,String strTime,String endTime,String standard,int pageNum,int pageSize);
}
