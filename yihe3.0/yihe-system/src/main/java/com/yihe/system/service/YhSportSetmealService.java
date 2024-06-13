/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhSportSetmeal;

/**
 * 运动课堂
 * @author dong
 *
 */
public interface YhSportSetmealService {

List<YhSportSetmeal> selectSetmeal(Map<String, Object> map);
    
    int selectSetmealConut(Map<String, Object> map);
    /*
    根据疾病id以及套餐id查询，默认oneself为1
     */
    YhSportSetmeal selectYhSportSetmeal(YhSportSetmeal yhSportSetmeal);
    
    YhSportSetmeal selectByPrimaryKey(Integer id);
}
