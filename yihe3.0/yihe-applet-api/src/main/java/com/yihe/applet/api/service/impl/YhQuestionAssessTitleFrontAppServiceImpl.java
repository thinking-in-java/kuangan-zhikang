package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhQuestionAssessTitleFrontApp;
import com.yihe.applet.api.mapper.YhQuestionAssessTitleFrontAppMapper;
import com.yihe.applet.api.service.YhQuestionAssessTitleFrontAppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 * Created by Administrator on 2020/5/22 17:58
 */
@Service
public class YhQuestionAssessTitleFrontAppServiceImpl implements YhQuestionAssessTitleFrontAppService {
    @Resource
    private YhQuestionAssessTitleFrontAppMapper yhQuestionAssessTitleFrontAppMapper;
    @Override
    public List<YhQuestionAssessTitleFrontApp> selAllQuestion(int gender) {
        return yhQuestionAssessTitleFrontAppMapper.selAllQuestion(gender);
    }
}
