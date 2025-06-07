package com.yihe.applet.api.service;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhScoreEventCategoryApp;
import org.apache.ibatis.annotations.Param;

public interface YhScoreEventCategoryAppService {
	YhScoreEventCategoryApp selectEventType ( String eventType, String orgCode);
	List<Map<String,Object>> selectIntegralStatus (Map<String, Object> map);
	String seleventType(String eventName);

	Integer stepPoints(String usercode,String step);


}
