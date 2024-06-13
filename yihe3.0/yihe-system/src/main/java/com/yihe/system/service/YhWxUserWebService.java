package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhWxUserWeb;

public interface YhWxUserWebService {
	YhWxUserWeb selectByPrimaryKey(String userId);
	 List<String> findUseroppenIdByDoctorAll(String usercode,String groupid);
}
