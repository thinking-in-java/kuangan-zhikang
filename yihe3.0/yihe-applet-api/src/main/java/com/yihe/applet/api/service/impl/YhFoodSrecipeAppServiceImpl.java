package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhFoodSrecipeApp;
import com.yihe.applet.api.mapper.YhFoodSrecipeAppMapper;
import com.yihe.applet.api.service.YhFoodSrecipeAppService;
import com.yihe.system.domain.YhFoodSrecipe;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 * Created by Administrator on 2020/5/28 9:27
 */
@Service
public class YhFoodSrecipeAppServiceImpl implements YhFoodSrecipeAppService {
    @Resource
    private YhFoodSrecipeAppMapper yhFoodSrecipeAppMapper;

    @Override
    public YhFoodSrecipeApp selectYhFoodSrecipeById(YhFoodSrecipeApp yhFoodSrecipeApp) {
        return yhFoodSrecipeAppMapper.selectYhFoodSrecipeById(yhFoodSrecipeApp);
    }

	@Override
	public YhFoodSrecipe selectYhFoodSrecipeById2(YhFoodSrecipe yhFoodSrecipe) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeAppMapper.selectYhFoodSrecipeById2(yhFoodSrecipe);
	}
}
