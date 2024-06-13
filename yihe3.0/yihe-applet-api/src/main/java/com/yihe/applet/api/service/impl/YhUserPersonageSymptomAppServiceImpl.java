package com.yihe.applet.api.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.applet.api.domain.YhUserPersonageSymptomApp;
import com.yihe.applet.api.mapper.YhUserPersonageSymptomAppMapper;
import com.yihe.applet.api.service.YhUserPersonageSymptomAppService;

/**
* YhUserPersonageSymptomService实现
* Created by dong on 2020/2/12.
*/
@Service
@Transactional

public class YhUserPersonageSymptomAppServiceImpl  implements YhUserPersonageSymptomAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonageSymptomAppServiceImpl.class);

    @Autowired
    YhUserPersonageSymptomAppMapper yhUserPersonageSymptomMapper;

	@Override
	public YhUserPersonageSymptomApp showsymptom(String usercode) {
		// TODO Auto-generated method stub
		return yhUserPersonageSymptomMapper.showsymptom(usercode);
	}

	@Override
	public int insertSelective(YhUserPersonageSymptomApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageSymptomMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonageSymptomApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageSymptomMapper.updateByPrimaryKeySelective(record);
	}

	

	

}