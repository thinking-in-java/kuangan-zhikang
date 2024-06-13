package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserMedal;

public interface YhUserMedalMapper {
    int selectmealcount(YhUserMedal meal);
    int addnum(YhUserMedal meal);
    int insertmeal(YhUserMedal meal);
}
