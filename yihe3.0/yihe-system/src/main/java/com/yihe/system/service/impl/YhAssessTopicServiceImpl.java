package com.yihe.system.service.impl;

import com.yihe.system.domain.YhAssessTopic;
import com.yihe.system.mapper.YhAssessTopicMapper;
import com.yihe.system.service.YhAssessTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YhAssessTopicServiceImpl implements YhAssessTopicService {
    @Autowired
    private YhAssessTopicMapper yhAssessTopicMapper;

    @Override
    public List<YhAssessTopic> findByAssess(String assess) {
        return yhAssessTopicMapper.findByAssess(assess);
    }
}
