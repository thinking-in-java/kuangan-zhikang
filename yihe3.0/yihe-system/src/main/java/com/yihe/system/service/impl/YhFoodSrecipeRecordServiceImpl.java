/**
 * 
 */
package com.yihe.system.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yihe.system.mapper.YhFoodEnergyWebMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.InterventionAdjustment;
import com.yihe.system.domain.YhDicAssessmentScreen;
import com.yihe.system.domain.YhFoodSrecipeRecord;
import com.yihe.system.mapper.YhFoodSrecipeRecordMapper;
import com.yihe.system.service.YhFoodSrecipeRecordService;

/**
 * @author dong
 *
 */
@Service
public class YhFoodSrecipeRecordServiceImpl implements YhFoodSrecipeRecordService{
	
	 @Autowired
	 YhFoodSrecipeRecordMapper yhFoodSrecipeRecordMapper;
	@Autowired
	YhFoodEnergyWebMapper yhFoodEnergyWebMapper;

	@Override
	public List<YhFoodSrecipeRecord> selectSrecipeRecord(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.selectSrecipeRecord(map);
	}

	@Override
	public int selectSrecipeRecordConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.selectSrecipeRecordConut(map);
	}

	@Override
	public YhFoodSrecipeRecord selectByPrimaryKey(Integer recordId) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.selectByPrimaryKey(recordId);
	}

	@Override
	public int insertSelective(YhFoodSrecipeRecord record) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.insertSelective(record);
	}

	@Override
	public YhFoodSrecipeRecord selectNewest(String userCode) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.selectNewest(userCode);
	}

	@Override
	public int updateByPrimaryKeySelective(YhFoodSrecipeRecord record) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective1(YhFoodSrecipeRecord record) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.updateByPrimaryKeySelective1(record);
	}

	@Override
	public List<String> findInterventionAdjustmentInfo2(Map<String, Object> map) {
		// TODO Auto-generated method stub

		List<String> list = yhFoodSrecipeRecordMapper.findInterventionAdjustmentInfo2(map);
		return list;
	}

	@Override
	public PageInfo<InterventionAdjustment> findInterventionAdjustmentInfo(Map<String, Object> map,Integer pageNum,Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<InterventionAdjustment> list = yhFoodSrecipeRecordMapper.findInterventionAdjustmentInfo(map);
		return new PageInfo<InterventionAdjustment>(list);
	}


	@Override
	public YhFoodSrecipeRecord findFoodSrecipeRecordByUserCodeForNew(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.findFoodSrecipeRecordByUserCodeForNew(usercode);
	}

	@Override
	public int modifyFoodSrecipeRecordByUserCodeForType2(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.modifyFoodSrecipeRecordByUserCodeForType2(usercode);
	}

	@Override
	public YhFoodSrecipeRecord findTypeoneNewInfo(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.findTypeoneNewInfo(usercode);
	}

	@Override
	public double jisuanRiXuNengLiang(int sexInt, int ageInt, double height, double weight, String sport) {
		System.out.println(height);
		System.out.println(weight);
		//首先计算标准体重：男：身高(cm)-105；女：身高(cm)-107
		double standardWeight=0;
		if(sexInt==1){
			standardWeight=height-105;
		}else{
			standardWeight=height-107;
		}
		//其次计算体重指数：实际体重（kg）/身高的平方(㎡)，得到用户的体重指数等级。
		int shape=2;
		if(height ==0){
			return  0;
		}
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
		int findFoodEnergyBySupply = yhFoodEnergyWebMapper.findFoodEnergyBySupply(shape, Integer.valueOf(sport));
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
	public String findElemtInfoByFoodSrecipe(String usercode, String elemt) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.findElemtInfoByFoodSrecipe(usercode, elemt);
	}

	@Override
	public YhFoodSrecipeRecord findFoodSrecipeRecordByUserCodeForNewISInfo(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodSrecipeRecordMapper.findFoodSrecipeRecordByUserCodeForNewISInfo(usercode);
	}

	 
}
