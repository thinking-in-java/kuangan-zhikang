package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhFodClassifenergyWeb;
import com.yihe.system.domain.YhFoodCookBook;
import com.yihe.system.domain.YhFoodUserScrecipeWebNew;
import com.yihe.system.domain.addRecipesInfoVojo;
import com.yihe.system.mapper.YhFodClassifenergyWebMapper;
import com.yihe.system.mapper.YhFoodCookBookMapper;
import com.yihe.system.mapper.YhFoodUserScrecipeWebNewMapper;
import com.yihe.system.service.YhFoodUserScrecipeWebNewService;
@Service
public class YhFoodUserScrecipeWebNewServiceImpl implements YhFoodUserScrecipeWebNewService {
@Autowired
YhFoodUserScrecipeWebNewMapper YhFoodUserScrecipeWebNewMapper;
@Autowired
YhFodClassifenergyWebMapper YhFodClassifenergyWebMapper;
@Autowired
private YhFoodCookBookMapper yhFoodCookBookMapper;


	@Override
	public PageInfo<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo(Integer oneself, String usercode,Integer eLevel, String diseaseid,Integer pageNum,Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		
		List<YhFoodUserScrecipeWebNew> list = YhFoodUserScrecipeWebNewMapper.findTodayScrecipeInfo(oneself,usercode, eLevel, diseaseid);
		
		return new PageInfo<YhFoodUserScrecipeWebNew>(list);
	}
	@Override
	public PageInfo<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo2(Integer oneself, String usercode,Integer eLevel, List<String> diseaseid,Integer pageNum,Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		
		List<YhFoodUserScrecipeWebNew> list = YhFoodUserScrecipeWebNewMapper.findTodayScrecipeInfo2(oneself,usercode, eLevel, diseaseid,null);
		
		return new PageInfo<YhFoodUserScrecipeWebNew>(list);
	}
	public PageInfo<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo3(Integer oneself, String usercode,Integer eLevel, List<String> diseaseid,Integer pageNum,Integer pageSize,List<Integer> idlist) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		
		List<YhFoodUserScrecipeWebNew> list = YhFoodUserScrecipeWebNewMapper.findTodayScrecipeInfo2(oneself,usercode, eLevel, diseaseid,idlist);
		
		return new PageInfo<YhFoodUserScrecipeWebNew>(list);
	}
	
	 

	@Override
	public List<String> findFoodUserScrecipeGroupLevel() {
		// TODO Auto-generated method stub
		return YhFoodUserScrecipeWebNewMapper.findFoodUserScrecipeGroupLevel();
	}

	@Override
	public int updateByPrimaryKeySelective(YhFoodUserScrecipeWebNew record) {
		// TODO Auto-generated method stub
		return YhFoodUserScrecipeWebNewMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(YhFoodUserScrecipeWebNew record) {
		// TODO Auto-generated method stub
		return YhFoodUserScrecipeWebNewMapper.insertSelective(record);
	}

	@Override
	public Integer findMaxSrecipeId() {
		// TODO Auto-generated method stub
		return YhFoodUserScrecipeWebNewMapper.findMaxSrecipeId();
	}
	
	public String fElements(addRecipesInfoVojo reinfo){
		String fElements="";
		YhFodClassifenergyWeb findFenergyByFlevel = YhFodClassifenergyWebMapper
				.findFenergyByFlevel(reinfo.getEfLevel());
		String[][] selectArrzao = reinfo.getSelectArrzao();
		String[][] selectArrwu = reinfo.getSelectArrwu();
		String[][] selectArrwan = reinfo.getSelectArrwan();
		String[][] selectArrjia = reinfo.getSelectArrjia();
		//1_90;1_90;4_18;3_90;2_90;早餐
		String getfBreakfast = findFenergyByFlevel.getfBreakfast();
		String getfLunch = findFenergyByFlevel.getfLunch();
		String getfDinner = findFenergyByFlevel.getfDinner();
		String getfExtrameal = findFenergyByFlevel.getfExtrameal();
		
		
		
		//早餐操作
		String[] split = getfBreakfast.split(";");
		for(int i = 0;i < selectArrzao.length;i++){   
			//System.out.println(arr[i]);//arr中元素：2个数组的地址
			//遍历arr[0]，arr中元素第一个数组
			String string2 = split[i];
			String[] split2 = string2.split("_");
			//热量
			float string3 = Float.valueOf(split2[1]);
			for(int j = 0;j < selectArrzao[i].length;j++){
				String string = selectArrzao[i][j];
				YhFoodCookBook selectByPrimaryKey = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(string));
				if (null == selectByPrimaryKey) {
					continue;
				}
				float v = (float) (Math.round((100 / selectByPrimaryKey.getEnergyKcal() * string3) * 100)) / 100;

				fElements=fElements+"1_"+selectByPrimaryKey.getfCId()+"_"+Math.round(v)+";";
			}
		}
		
		
		//午餐操作
				String[] splitwu = getfLunch.split(";");
				for(int i = 0;i < selectArrwu.length;i++){   
					//System.out.println(arr[i]);//arr中元素：2个数组的地址
					//遍历arr[0]，arr中元素第一个数组
					String string2 = splitwu[i];
					String[] split2 = string2.split("_");
					//热量
					float string3 = Float.valueOf(split2[1]);
					for(int j = 0;j < selectArrwu[i].length;j++){
						String string = selectArrwu[i][j];
						YhFoodCookBook selectByPrimaryKey = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(string));
						if (null == selectByPrimaryKey) {
							continue;
						}
						float num= 100/selectByPrimaryKey.getEnergyKcal()*string3;
						fElements=fElements+"2_"+selectByPrimaryKey.getfCId()+"_"+Math.round(num)+";";
					}
				}
				
				//晚餐操作
				String[] splitwan = getfDinner.split(";");
				for(int i = 0;i < selectArrwan.length;i++){   
					//System.out.println(arr[i]);//arr中元素：2个数组的地址
					//遍历arr[0]，arr中元素第一个数组
					String string2 = splitwan[i];
					String[] split2 = string2.split("_");
					//热量
					float string3 = Float.valueOf(split2[1]);
					for(int j = 0;j < selectArrwan[i].length;j++){
						String string = selectArrwan[i][j];
						YhFoodCookBook selectByPrimaryKey = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(string));
						if (null == selectByPrimaryKey) {
							continue;
						}
						float num= 100/selectByPrimaryKey.getEnergyKcal()*string3;
						fElements=fElements+"3_"+selectByPrimaryKey.getfCId()+"_"+Math.round(num)+";";
					}
				}
				
				
				//晚餐操作
				String[] splitjia = getfExtrameal.split(";");
				for(int i = 0;i < selectArrjia.length;i++){   
					//System.out.println(arr[i]);//arr中元素：2个数组的地址
					//遍历arr[0]，arr中元素第一个数组
					String string2 = splitjia[i];
					String[] split2 = string2.split("_");
					//热量
					float string3 = Float.valueOf(split2[1]);
					for(int j = 0;j < selectArrjia[i].length;j++){
						String string = selectArrjia[i][j];
						YhFoodCookBook selectByPrimaryKey = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(string));
						if (null == selectByPrimaryKey) {
							continue;
						}
						float num= 100/selectByPrimaryKey.getEnergyKcal()*string3;
						fElements=fElements+"4_"+selectByPrimaryKey.getfCId()+"_"+Math.round(num)+";";
					}
				}
				
		
		
		
		return fElements;
	}
	@Override
	public YhFoodUserScrecipeWebNew selectByPrimaryKey(Integer uFoodErecipeId) {
		// TODO Auto-generated method stub
		return YhFoodUserScrecipeWebNewMapper.selectByPrimaryKey(uFoodErecipeId);
	}
	@Override
	public List<YhFoodUserScrecipeWebNew> findFoodUserScrecipe2ByIdList(List<Integer> id) {
		// TODO Auto-generated method stub
		return YhFoodUserScrecipeWebNewMapper.findFoodUserScrecipe2ByIdList(id);
	}

}
