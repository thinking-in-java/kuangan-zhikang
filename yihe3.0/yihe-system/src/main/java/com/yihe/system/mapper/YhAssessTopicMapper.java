package com.yihe.system.mapper;

import com.yihe.system.domain.YhAssessTopic;

import java.util.List;

public interface YhAssessTopicMapper {

    List<YhAssessTopic> findByAssess(String assess);
}
