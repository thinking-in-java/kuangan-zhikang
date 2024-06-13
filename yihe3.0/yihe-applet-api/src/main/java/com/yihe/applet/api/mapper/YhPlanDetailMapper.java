package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhplanDetail;
import com.yihe.system.domain.YhFoodSrecipe;
import com.yihe.system.domain.YhFoodSrecipeRecord;

import java.util.List;

public interface YhPlanDetailMapper {
    int selectfoodcount(String usercode);
    int selectsportcount(String usercode);
    List<YhFoodSrecipe> changhFoodSrecipes(YhplanDetail yhplanDetail);
    int insertyhfoodlog(YhFoodSrecipeRecord yhFoodSrecipeRecord);
    int upatefoodlogispaln(String Usercode);
    int upatefoodlogisnotpaln(String Usercode);
    int isfinishi(String Usercode);
    int updatereg(YhplanDetail yhplanDetail);
    int updateperson(YhplanDetail yhplanDetail);
    int selectperson(YhplanDetail yhplanDetail);
    int insertperson(YhplanDetail yhplanDetail);
}
