/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.InformationSummary;
import com.yihe.system.domain.YHUserSleepDiaryVOJO;
import com.yihe.system.domain.YhSleepUserDiary;
import com.yihe.system.mapper.YhSleepUserDiaryMapper;
import com.yihe.system.service.YhSleepUserDiaryService;

/**
 * @author dong
 *
 */
@Service
@Transactional
public class YhSleepUserDiaryServiceImpl implements YhSleepUserDiaryService{

	@Autowired
	YhSleepUserDiaryMapper yhSleepUserDiaryMapper;

	@Override
	public List<YhSleepUserDiary> selectSleepDiary(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryMapper.selectSleepDiary(map);
	}

	@Override
	public int selectSleepDiaryConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return  yhSleepUserDiaryMapper.selectSleepDiaryConut(map);
	}

	@Override
	public List<YhSleepUserDiary> selectSleepDiaryCtime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryMapper.selectSleepDiaryCtime(map);
	}

	@Override
	public List<YHUserSleepDiaryVOJO> showSleepDiaryPor(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryMapper.showSleepDiaryPor(map);
	}

	@Override
	public int showSleepDiaryPorConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryMapper.showSleepDiaryPorConut(map);
	}

	@Override
	public List<YhSleepUserDiary> showSleepRecordAnalyze(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryMapper.showSleepRecordAnalyze(map);
	}

	@Override
	public int[] findsleepDiaryByUserCodeForWeekDay(String usercode) {
		// TODO Auto-generated method stub
		List<InformationSummary> findSportDiaryByUserCodeForWeekDay = yhSleepUserDiaryMapper.findsleepDiaryByUserCodeForWeekDay(usercode);
		int[] zhou=new int[7];
		int i=0;
		for (int j=0;j<7;j++) {
			 
			int daynum = findSportDiaryByUserCodeForWeekDay.get(j).getDaynum();
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
	public int[] findsleepDiaryByUserCodeForMonth(String usercode, String year, String month) {
		// TODO Auto-generated method stub
		int[] yuelist=new int[31];
		HashMap<String, Integer> findFoodUserDuaryYue = yhSleepUserDiaryMapper.findsleepDiaryByUserCodeForMonth(usercode, year, month);
		
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
	public int[] findsleepDiaryByuserCodeForyear(String usercode, String year) {
		// TODO Auto-generated method stub
		int[] yearlist=new int[12];
		HashMap<String, Integer> findFoodUserDiaryByYear = yhSleepUserDiaryMapper.findsleepDiaryByuserCodeForyear(usercode, year);
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
	public int[] findSleepUserDuaryLastWeek(String usercode) {
		// TODO Auto-generated method stub
		List<InformationSummary> findSportDiaryByUserCodeForWeekDay = yhSleepUserDiaryMapper.findSleepUserDuaryLastWeek(usercode);
		int[] zhou=new int[7];
		int i=0;
		for (int j=0;j<7;j++) {
			 
			int daynum = findSportDiaryByUserCodeForWeekDay.get(j).getDaynum();
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
	public String findSleepUserDiaryByUserCodeForNew(String usercode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryMapper.findSleepUserDiaryByUserCodeForNew(usercode);
	}

	@Override
	public List<YhSleepUserDiary> showSleepMonthUser(String usercode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryMapper.showSleepMonthUser(usercode);
	}

	@Override
	public List<YhSleepUserDiary> showSleepQuarter(String usercode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryMapper.showSleepQuarter(usercode);
	}

	@Override
	public List<YhSleepUserDiary> showSleepYear(String usercode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryMapper.showSleepYear(usercode);
	}

	
	
}
