package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhFoodSrecipeApiNew;
import com.yihe.applet.api.domain.YhFoodSrecipeRecordApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.mapper.YhFoodSrecipeRecordAppMapper;
import com.yihe.applet.api.service.YhFoodSrecipeRecordAppService;
import com.yihe.system.domain.YhFoodSrecipe;
import com.yihe.system.mapper.YhFoodEnergyWebMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * Description:
 * Created by Administrator on 2020/5/28 9:08
 */
@Service
public class YhFoodSrecipeRecordAppServiceImpl implements YhFoodSrecipeRecordAppService{
    @Resource
    private YhFoodSrecipeRecordAppMapper yhFoodSrecipeRecordAppMapper;
    @Autowired
	YhFoodSrecipeApiNewServiceImpl YhFoodSrecipeApiNewServiceImpl;
    @Autowired
	private YhUserReginfoAppServiceImpl YhUserReginfoAppServiceImpl;
    
    
    @Autowired
    YhFoodEnergyWebMapper YhFoodEnergyWebMapper;
    
    public String tuijianNum(String usercode) {
		YhFoodSrecipeRecordApp findFoodSrecipeRecordByUsercodeForNow = findFoodSrecipeRecordByUsercodeForNow(usercode);
		// 推荐摄入量
		Long recordKay = 1733l;
		if (null != findFoodSrecipeRecordByUsercodeForNow
				&& null != findFoodSrecipeRecordByUsercodeForNow.getRecordKay()) {
			recordKay = findFoodSrecipeRecordByUsercodeForNow.getRecordKay();
		} else {
			// 如果等于空则获取 初始化膳食热量评估 3.1等膳食推荐数据库做好改（新用户为评估的默认膳食推荐能量）
			YhUserReginfoApp findYhUserReginfo = YhUserReginfoAppServiceImpl.findYhUserReginfo(usercode);
			if(null!=findYhUserReginfo){
				Integer gender = findYhUserReginfo.getGender();
				YhFoodSrecipeApiNew findNewFoodSrecipeForSex = YhFoodSrecipeApiNewServiceImpl.findNewFoodSrecipeForSex(usercode,gender);
				recordKay = Long.valueOf(findNewFoodSrecipeForSex.geteLevel());
			}
			
		}
		return String.valueOf(recordKay);
	}


    @Override
    public int insertYhFoodSrecipeRecord(YhFoodSrecipeRecordApp yhFoodSrecipeRecordApp) {
        return yhFoodSrecipeRecordAppMapper.insertYhFoodSrecipeRecord(yhFoodSrecipeRecordApp);
    }

    @Override
    public int uptYhFoodSrecipeRecord(YhFoodSrecipeRecordApp yhFoodSrecipeRecordApp) {
        return yhFoodSrecipeRecordAppMapper.uptYhFoodSrecipeRecord(yhFoodSrecipeRecordApp);
    }

    @Override
    public int selYhFoodSrecipeRecordCount(YhFoodSrecipeRecordApp yhFoodSrecipeRecordApp) {
        return yhFoodSrecipeRecordAppMapper.selYhFoodSrecipeRecordCount(yhFoodSrecipeRecordApp);
    }

	@Override
	public YhFoodSrecipeRecordApp findFoodSrecipeRecordByUsercodeForNow(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordAppMapper.findFoodSrecipeRecordByUsercodeForNow(usercode);
	}
	
	 /**
     * 全日能量推荐计算
     */
    public double findEnergyNum(int sexInt,int ageInt,double height,double weight,String sport){
    			 
    			//首先计算标准体重：男：身高(cm)-105；女：身高(cm)-107
    			double standardWeight=0;
    			if(sexInt==1){
    				standardWeight=height-105;
    			}else{
    				standardWeight=height-107;
    			}
    			//其次计算体重指数：实际体重（kg）/身高的平方(㎡)，得到用户的体重指数等级。
    			int shape=2;
    	         Double bf = weight/((height/ 100) * (height / 100));
    				BigDecimal b = new BigDecimal(bf);
    				float bmi = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    				if(bmi<18.5){
    					shape=1;
    				}else if(bmi>=18.5&&bmi<=23.9){
    					shape=2;
    				}else if(bmi>=24&&bmi<=27.9){
    					shape=3;
    				}else if(bmi>=28){
    					shape=4;
    				}
    			// 日能量供给量
    				int findFoodEnergyBySupply = YhFoodEnergyWebMapper.findFoodEnergyBySupply(shape, Integer.valueOf(sport));
    				
    				//全日能量供给量
    				double dayEnergy=standardWeight*findFoodEnergyBySupply;
    				
    				if(ageInt>50&&ageInt<=60){
    					dayEnergy=(int) (dayEnergy*0.9);
    				}else if(ageInt>60&&ageInt<=70){
    					dayEnergy=(int) (dayEnergy*0.8);
    				}else if(ageInt>70&&ageInt<=80){
    					dayEnergy=(int) (dayEnergy*0.7);
    				}else if(ageInt>80&&ageInt<=90){
    					dayEnergy=(int) (dayEnergy*0.6);
    				}else if(ageInt>90){
    					dayEnergy=(int) (dayEnergy*0.5);
    				}
    				return dayEnergy;
    				
    }

	@Override
	public YhFoodSrecipeRecordApp findInProgressFoodRecord(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordAppMapper.findInProgressFoodRecord(usercode);
	}

	@Override
	public int modifyType3Fortype2(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordAppMapper.modifyType3Fortype2(usercode);
	}

	@Override
	public int modifyType1Fortype2(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordAppMapper.modifyType1Fortype2(usercode);
	}
}
