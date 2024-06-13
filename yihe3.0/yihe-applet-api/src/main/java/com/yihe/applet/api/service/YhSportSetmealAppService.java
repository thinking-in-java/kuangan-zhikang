package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhSportSetmealApp;

/**
 * Description:
 * Created by Administrator on 2020/5/28 14:23
 */
public interface YhSportSetmealAppService {
    /*
    根据疾病id以及套餐id查询，默认oneself为1
     */
    YhSportSetmealApp selectYhSportSetmeal(YhSportSetmealApp yhSportSetmealApp);
}
