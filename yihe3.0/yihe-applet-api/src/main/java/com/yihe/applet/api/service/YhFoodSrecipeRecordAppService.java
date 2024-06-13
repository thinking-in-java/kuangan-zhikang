package com.yihe.applet.api.service;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhFoodSrecipeRecordApp;

/**
 * Description:
 * Created by Administrator on 2020/5/28 9:08
 */
public interface YhFoodSrecipeRecordAppService {
	/**
	 * 修改状态结束（新评估的时候）
	 */
	int modifyType1Fortype2(String usercode);
	
	/**
	 * 修改状态挂起的结束（新评估的时候）
	 */
	int modifyType3Fortype2(String usercode);
	
	YhFoodSrecipeRecordApp findInProgressFoodRecord(String usercode);
	
	 public double findEnergyNum(int sexInt,int ageInt,double height,double weight,String sport);
	/**
	 * 查询用户膳食推荐能量
	 * @param yhFoodSrecipeRecordApp
	 * @return
	 */
	YhFoodSrecipeRecordApp findFoodSrecipeRecordByUsercodeForNow(String usercode);
    /*
    增
     */
    int insertYhFoodSrecipeRecord(YhFoodSrecipeRecordApp yhFoodSrecipeRecordApp);
    /*
    根据疾病id与userCode查询是否已存在推荐，存在则改为过期状态
     */
    int uptYhFoodSrecipeRecord(YhFoodSrecipeRecordApp yhFoodSrecipeRecordApp);
    /*
    根据疾病id与userCode查询是否已存在推荐
     */
    int selYhFoodSrecipeRecordCount(YhFoodSrecipeRecordApp yhFoodSrecipeRecordApp);
   
}
