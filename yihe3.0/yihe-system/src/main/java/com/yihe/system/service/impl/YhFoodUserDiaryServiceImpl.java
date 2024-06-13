package com.yihe.system.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhFoodCookBookMapper;
import com.yihe.system.mapper.YhFoodCookedWebMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.common.core.text.Convert;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.mapper.YhFoodSrecipeRecordMapper;
import com.yihe.system.mapper.YhFoodUserDiaryMapper;
import com.yihe.system.service.IYhFoodUserDiaryService;
import com.yihe.system.utils.StringUtil;

/**
 * 用户膳食日志Service业务层处理
 * 
 * @author yihe
 * @date 2020-04-07
 */
@Service
public class YhFoodUserDiaryServiceImpl implements IYhFoodUserDiaryService  
{
    @Autowired
    private YhFoodUserDiaryMapper yhFoodUserDiaryMapper;

	@Autowired
	private YhFoodCookBookMapper yhFoodCookBookMapper;
  
 
    /**
     * 查询用户膳食日志
     * 
     * @param uFdId 用户膳食日志ID
     * @return 用户膳食日志
     */
    @Override
    public YhFoodUserDiary selectYhFoodUserDiaryById(Long uFdId)
    {
        return yhFoodUserDiaryMapper.selectYhFoodUserDiaryById(uFdId);
    }

    /**
     * 查询用户膳食日志列表
     * 
     * @param yhFoodUserDiary 用户膳食日志
     * @return 用户膳食日志
     */
    @Override
    public List<YhFoodUserDiary> selectYhFoodUserDiaryList(YhFoodUserDiary yhFoodUserDiary)
    {
        return yhFoodUserDiaryMapper.selectYhFoodUserDiaryList(yhFoodUserDiary);
    }

    /**
     * 新增用户膳食日志
     * 
     * @param yhFoodUserDiary 用户膳食日志
     * @return 结果
     */
    @Override
    public int insertYhFoodUserDiary(YhFoodUserDiary yhFoodUserDiary)
    {
        return yhFoodUserDiaryMapper.insertYhFoodUserDiary(yhFoodUserDiary);
    }

    /**
     * 修改用户膳食日志
     * 
     * @param yhFoodUserDiary 用户膳食日志
     * @return 结果
     */
    @Override
    public int updateYhFoodUserDiary(YhFoodUserDiary yhFoodUserDiary)
    {
        return yhFoodUserDiaryMapper.updateYhFoodUserDiary(yhFoodUserDiary);
    }

    /**
     * 删除用户膳食日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYhFoodUserDiaryByIds(String ids)
    {
        return yhFoodUserDiaryMapper.deleteYhFoodUserDiaryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户膳食日志信息
     * 
     * @param uFdId 用户膳食日志ID
     * @return 结果
     */
    @Override
    public int deleteYhFoodUserDiaryById(Long uFdId)
    {
        return yhFoodUserDiaryMapper.deleteYhFoodUserDiaryById(uFdId);
    }

	@Override
	public PageInfo<YHUserFoodDiaryVOJO> findUserFoodDiaryInfo(String standard,String usercode, String nameLike, String usernameLike,
			String strTime, String endTime, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YHUserFoodDiaryVOJO> list = yhFoodUserDiaryMapper.findUserFoodDiaryInfo(standard,usercode, nameLike, usernameLike, strTime, endTime);
		return new PageInfo<>(list);
	}

	@Override
	public int findFlldUserDiaryByusercodeForCount(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFlldUserDiaryByusercodeForCount(usercode);
	}
	
	
	

	@Override
	public int findFoodUserDiaRyBYusercodeForElements(String usercode) {
		// TODO Auto-generated method stub
		
		//1_2_100;1_1_100;1_8_101;2_15_101;
		List<String> list = yhFoodUserDiaryMapper.findFoodUserDiaRyBYusercodeForElements(usercode);
		List<String> listfoodid=new ArrayList<String>();
		for (String string : list) {
			if(StringUtils.isEmpty(string)){
				continue;
			}
			//1_2_100;1_1_100;1_8_101;2_15_101;
			String[] split = string.split(";");
			//1_2_100
			for (String string2 : split) {
				String[] split2 = string2.split("_");
				//食物id
				String string3 = split2[1];
				listfoodid.add(string3);
			}
			
		}
		if(listfoodid.size()==0){
			return 0;
		}
		List<String> removeDuplicate = removeDuplicate(listfoodid);
		
		return removeDuplicate.size();
	}
	
	
	public static List<String> removeDuplicate(List<String> list) {
		HashSet<String> h = new HashSet<String>(list);
		list.clear();
		list.addAll(h);
		return list;
		}

	@Override
	public int[] findFoodUserDuary(String usercode) {
		// TODO Auto-generated method stub
		int[] zhou=new int[7];
		List<InformationSummary> findFoodUserDuary = yhFoodUserDiaryMapper.findFoodUserDuary(usercode);
		int i=0;
		for (int j=0;j<7;j++) {
			 
			int daynum = findFoodUserDuary.get(j).getDaynum();
			if(daynum>0){
				i++;
			}else{
				i=0;
			}
			zhou[j]=i;
			
		}
		return zhou;
	}
	/**
	 * 根据年 月 获取对应的月份 天数
	 * */
	public static int getDaysByYearMonth(int year, int month) {
		
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
	public static void main(String[] args) {
		//System.out.println(getDaysByYearMonth(2020, 2));
		//System.out.println(getTuesday());
	}
	
	
	@Override
	public int[] findFoodUserDuaryYue(String usercode, String year, String month) {
		// TODO Auto-generated method stub
		int[] yuelist=new int[31];
		HashMap<String, Integer> findFoodUserDuaryYue = yhFoodUserDiaryMapper.findFoodUserDuaryYue(usercode, year, month);
		if(null==findFoodUserDuaryYue){
			return yuelist;
		}
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(findFoodUserDuaryYue.entrySet()); //转换为list
		
		list.sort(new Comparator<Map.Entry<String, Integer>>() {
	          @Override
	          public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	        	  String key2 = o2.getKey();
	        	  String key1 = o1.getKey();
	        	  if(StringUtils.isNumeric(key2)&&StringUtils.isNumeric(key1)){
	        		  Integer intkey2=Integer.valueOf(key2);
	        		  Integer intkey1=Integer.valueOf(key1);
	        		  return intkey1.compareTo(intkey2);
	        	  }
	              return o1.getKey().compareTo(o2.getKey());
	          }
	      });
		list.remove(list.size()-1);
		list.remove(list.size()-1);
		int i=0;
		int num=0;
		 for (Map.Entry<String, Integer> mapping : list){
			
	            System.out.println(mapping.getKey()+": "+mapping.getValue());
	            
	            	Integer valueOf = Integer.valueOf(""+mapping.getValue());
	            	if(valueOf>0){
	            		num++;
	            	}else{
	            		num=0;
	            	}
	            	yuelist[i]=num;
	          
	            
	            
	            
	            i++;
	        }    
		 
		  for (int j : yuelist) {
			System.out.println("intlist+++++"+j);
		}
		 
		return yuelist;
	}
	
	 

	@Override
	public int[] findFoodUserDiaryByYear(String usercode, String year) {
		// TODO Auto-generated method stub

		int[] yearlist=new int[12];
		HashMap<String, Integer> findFoodUserDiaryByYear = yhFoodUserDiaryMapper.findFoodUserDiaryByYear(usercode, year);
		if(null==findFoodUserDiaryByYear){
			return yearlist;
		}
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(findFoodUserDiaryByYear.entrySet()); //转换为list


		list.sort(new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				String key2 = o2.getKey();
				String key1 = o1.getKey();
				if(StringUtils.isNumeric(key2)&&StringUtils.isNumeric(key1)){
					Integer intkey2=Integer.valueOf(key2);
					Integer intkey1=Integer.valueOf(key1);
					return intkey1.compareTo(intkey2);
				}
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		list.remove(list.size()-1);
		int i=0;
		int num=0;
		for (Map.Entry<String, Integer> mapping : list){

			System.out.println(mapping.getKey()+": "+mapping.getValue());

			Integer valueOf = Integer.valueOf(""+mapping.getValue());
			if(valueOf>0){
				num++;
			}else{
				num=0;
			}
			yearlist[i]=num;


			i++;
		}



		return yearlist;
	}

	@Override
	public int[] findFoodUserDuaryLastWeek(String usercode) {
		// TODO Auto-generated method stub
		List<InformationSummary> findFoodUserDuary = yhFoodUserDiaryMapper.findFoodUserDuaryLastWeek(usercode);
		int[] zhou=new int[7];
		int i=0;
		for (int j=0;j<7;j++) {
			 
			int daynum = findFoodUserDuary.get(j).getDaynum();
			if(daynum>0){
				i++;
			}else{
				i=0;
			}
			zhou[j]=i;
			
		}
		return zhou;
		
	}

	@Override
	public List<YhFoodUserDiary> selectDiary(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.selectDiary(map);
	}

	@Override
	public int selectDiaryConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.selectDiaryConut(map);
	}

	@Override
	public List<YhFoodUserDiary> selectDiaryCtime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.selectDiaryCtime(map);
	}

	@Override
	public List<YhFoodUserDiary> showRecordAnalyze(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.showRecordAnalyze(map);
	}

	@Override
	public YhFoodUserDiary findFoodUserDiaryInfoByUserCodeForNow(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFoodUserDiaryInfoByUserCodeForNow(usercode);
	}

	@Override
	public List<YhFoodUserDiary> findFoodUserDiaryByusercodeAndTime(String usercode, String time) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFoodUserDiaryByusercodeAndTime(usercode, time);
	}

	@Override
	public List<String> findFoodUserDiaryForTimeList(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFoodUserDiaryForTimeList(usercode);
	}

	@Override
	public YhFoodUserDiary selectByUserCodeAndTime(String usercode, String diaryData) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.selectByUserCodeAndTime(usercode, diaryData);
	}

	@Override
	public int updateYhFoodUserDiaryDel(YhFoodUserDiary yhFoodUserDiary) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.updateYhFoodUserDiaryDel(yhFoodUserDiary);
	}

	@Override
	public Integer findToExamine() {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findToExamine();
	}

	@Override
	public List<YhFoodUserDiary> findFoodDiaryThisWeekInfo(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFoodDiaryThisWeekInfo(usercode);
	}

	@Override
	public List<YhFoodUserDiary> findFoodDiaryLastWeekInfo(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFoodDiaryLastWeekInfo(usercode);
	}

	@Override
	public List<YhFoodUserDiary> findFoodDiaryThisMonthInfo(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFoodDiaryThisMonthInfo(usercode);
	}

	@Override
	public List<YhFoodUserDiary> findFoodDiaryLastMonthInfo(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFoodDiaryLastMonthInfo(usercode);
	}

	@Override
	public List<YhFoodUserDiary> findFoodDiaryThisYearInfo(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFoodDiaryThisYearInfo(usercode);
	}

	@Override
	public List<YhFoodUserDiary> findFoodDiaryLastYearInfo(String usercode) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFoodDiaryLastYearInfo(usercode);
	}


	@Override
	public String findFoodDiaryinfoNewCanci(String usercode) {
		return yhFoodUserDiaryMapper.findFoodDiaryinfoNewCanci(usercode);
	}

	@Override
	public String findFoodDiaryinfoNewTime(String usercode) {
		return yhFoodUserDiaryMapper.findFoodDiaryinfoNewTime(usercode);
	}

	@Override
	public List<YhFoodUserDiary> findFoodInfoByTimeAnduserCodeForReport(String strTime, String endTime,
			String usercode) {
		// TODO Auto-generated method stub
		return yhFoodUserDiaryMapper.findFoodInfoByTimeAnduserCodeForReport(strTime, endTime, usercode);
	}
	

}
