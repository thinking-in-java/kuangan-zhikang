package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhSportSetmealApp;
import com.yihe.applet.api.mapper.YhSportSetmealAppMapper;
import com.yihe.applet.api.service.YhSportSetmealAppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 * Created by Administrator on 2020/5/28 14:24
 */
@Service
public class YhSportSetmealAppServiceImpl implements YhSportSetmealAppService {
    @Resource
    private YhSportSetmealAppMapper yhSportSetmealAppMapper;

    @Override
    public YhSportSetmealApp selectYhSportSetmeal(YhSportSetmealApp yhSportSetmealApp) {
        return yhSportSetmealAppMapper.selectYhSportSetmeal(yhSportSetmealApp);
    }
}
