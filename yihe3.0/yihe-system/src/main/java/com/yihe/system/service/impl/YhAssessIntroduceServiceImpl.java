package com.yihe.system.service.impl;

import com.yihe.system.domain.YhAssessIntroduce;
import com.yihe.system.mapper.YhAssessIntroduceMapper;
import com.yihe.system.service.YhAssessIntroduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YhAssessIntroduceServiceImpl implements YhAssessIntroduceService {
    @Autowired
    private YhAssessIntroduceMapper yhAssessIntroduceMapper;

    @Override
    public YhAssessIntroduce getByIntroduceName(String name) {
        return yhAssessIntroduceMapper.getByIntroduceName(name);
    }
}
