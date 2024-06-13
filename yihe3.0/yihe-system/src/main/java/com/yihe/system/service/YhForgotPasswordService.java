package com.yihe.system.service;

import com.yihe.system.domain.YhJzSysSms;
import com.yihe.system.domain.YhUserReginfo;

public interface YhForgotPasswordService {
	int selectcount(String name,String phone);
	int updatepwd(YhUserReginfo regin);
	YhJzSysSms sms();
}
