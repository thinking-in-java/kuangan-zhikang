package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhUserMedal;
import com.yihe.applet.api.mapper.YhUserMedalMapper;
import com.yihe.applet.api.service.YhUserMedalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class YhUserMedalServiceImpl implements YhUserMedalService{
    @Resource
    YhUserMedalMapper yhUserMedalMapper;

    @Override
    public int insertmeal(YhUserMedal meal) {
        //先查询是否拥有这个勋章
        int usermealcount=yhUserMedalMapper.selectmealcount(meal);
        if(usermealcount!=0){
            return(yhUserMedalMapper.addnum(meal));
        }else{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            meal.setMealTime(df.format(new Date()));
            meal.setMedalNum(1);
            return (yhUserMedalMapper.insertmeal(meal));
        }

    }
}
