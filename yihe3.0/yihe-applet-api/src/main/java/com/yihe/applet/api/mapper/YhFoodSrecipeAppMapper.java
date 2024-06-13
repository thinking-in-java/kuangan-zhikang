package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhFoodSrecipeApp;
import com.yihe.system.domain.YhFoodSrecipe;

/**
 * Description:
 * Created by Administrator on 2020/5/28 9:27
 */
public interface YhFoodSrecipeAppMapper {
    /*
    查
     */
    YhFoodSrecipeApp selectYhFoodSrecipeById(YhFoodSrecipeApp yhFoodSrecipeApp);
    
    YhFoodSrecipe selectYhFoodSrecipeById2(YhFoodSrecipe yhFoodSrecipe);
}
