package com.yihe.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhJzSysSms;
import com.yihe.system.domain.YhUserReginfo;

public interface YhForgotPasswordMapper {
	int selectcount(@Param("name")String name,@Param("phone")String phone);
	int updatepwd(YhUserReginfo regin);
	YhJzSysSms sms();
}
