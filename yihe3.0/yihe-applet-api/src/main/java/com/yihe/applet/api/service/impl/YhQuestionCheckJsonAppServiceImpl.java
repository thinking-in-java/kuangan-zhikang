package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhQuestionCheckJsonApp;
import com.yihe.applet.api.mapper.YhQuestionCheckJsonAppMapper;
import com.yihe.applet.api.service.YhQuestionCheckJsonAppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 * Created by Administrator on 2020/5/28 8:13
 */
@Service
public class YhQuestionCheckJsonAppServiceImpl implements YhQuestionCheckJsonAppService {
    @Resource
    private YhQuestionCheckJsonAppMapper yhQuestionCheckJsonAppMapper;

    @Override
    public int insertYhQuestionCheckJson(YhQuestionCheckJsonApp yhQuestionCheckJsonApp) {
        return yhQuestionCheckJsonAppMapper.insertYhQuestionCheckJson(yhQuestionCheckJsonApp);
    }

    @Override
    public int updateYhQuestionCheckJson(YhQuestionCheckJsonApp yhQuestionCheckJsonApp) {
        return yhQuestionCheckJsonAppMapper.updateYhQuestionCheckJson(yhQuestionCheckJsonApp);
    }

    @Override
    public YhQuestionCheckJsonApp selectYhQuestionCheckJsonByUserCode(String userCode, Integer userCheckTs ) {
        return yhQuestionCheckJsonAppMapper.selectYhQuestionCheckJsonByUserCode(userCode, userCheckTs);
    }

	@Override
	public int selectYhQuestionCheckCount() {
		// TODO Auto-generated method stub
		return yhQuestionCheckJsonAppMapper.selectYhQuestionCheckCount();
	}

    @Override
    public int delYhQuestionCheckJson(String userCode) {
        return yhQuestionCheckJsonAppMapper.delYhQuestionCheckJson(userCode);
    }
}
