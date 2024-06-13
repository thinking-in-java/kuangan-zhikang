package com.yihe.applet.api.service;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhScoreEventCategoryApp;

public interface YhScoreEventCategoryAppService {
	YhScoreEventCategoryApp selectEventType (String eventType);
	List<Map<String,Object>> selectIntegralStatus (Map<String, Object> map);
	String seleventType(String eventName);
}
