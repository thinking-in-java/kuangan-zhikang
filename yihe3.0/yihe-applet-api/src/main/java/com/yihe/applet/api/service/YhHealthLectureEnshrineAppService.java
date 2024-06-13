package com.yihe.applet.api.service;


import com.yihe.applet.api.domain.YhHealthLectureEnshrineApp;

public interface YhHealthLectureEnshrineAppService {
	  YhHealthLectureEnshrineApp selectWhetherEnshrineByParam( String userCode,Integer categoryid);
	    int deleteWhetherEnshrineByParam(String userCode,Integer categoryid);
	    int insertSelective(YhHealthLectureEnshrineApp record);

}
