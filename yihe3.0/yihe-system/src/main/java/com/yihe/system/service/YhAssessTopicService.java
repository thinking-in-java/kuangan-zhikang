package com.yihe.system.service;

import com.yihe.system.domain.YhAssessTopic;

import java.util.List;

public interface YhAssessTopicService {

    List<YhAssessTopic> findByAssess(String assess);
}
