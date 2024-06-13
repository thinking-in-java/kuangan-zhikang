package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhHealthLectureWatch;



public interface YhHealthLectureWatchService {
	List<Map<String, Object>>  selectexportClassroomData(Integer infoid,Integer categoryId);
	 List<Map<String, Object>>  selecteAllClassroomData();
	 List<Map<String, Object>>  selecteYesterdayAllClassroomData();
	 List<Map<String, Object>>  selecteThisweekAllClassroomData();
	 List<Map<String, Object>> selectelaskweekAllClassroomData();
}
