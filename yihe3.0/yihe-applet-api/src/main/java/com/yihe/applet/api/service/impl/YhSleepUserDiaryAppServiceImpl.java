package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhSleepUserDiaryApp;
import com.yihe.applet.api.mapper.YhSleepUserDiaryAppMapper;
import com.yihe.applet.api.service.YhSleepUserDiaryAppService;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.InformationSummary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version: shayu3.0
 * @Description: 睡眠用户记录service实现层
 * @author: lhh
 * @date: 2020年4月15日 下午5:22:50
 */
@Service
public class YhSleepUserDiaryAppServiceImpl implements YhSleepUserDiaryAppService {
	@Autowired
	private YhSleepUserDiaryAppMapper yhSleepUserDiaryAppMapper;

	@Override
	public int addUserDiaryReport(YhSleepUserDiaryApp yhSleepUserDiary) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.addUserDiaryReport(yhSleepUserDiary);
	}

	@Override
	public YhSleepUserDiaryApp selUserDiaryByDate(YhSleepUserDiaryApp yhSleepUserDiary) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selUserDiaryByDate(yhSleepUserDiary);
	}

	@Override
	public List<YhSleepUserDiaryApp> selAllDiaryByUser(String userCode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selAllDiaryByUser(userCode);
	}

	@Override
	public Map<Object, Object> selContinueDays(String userCode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selContinueDays(userCode);
	}

	@Override
	public List<YhSleepUserDiaryApp> selSleepDiaryByTimeAndCode(String userCode, String startDiaryT, String endDiaryT) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selSleepDiaryByTimeAndCode(userCode, startDiaryT, endDiaryT);
	}

	@Override
	public int uptSleepDiaryByCode(YhSleepUserDiaryApp yhSleepUserDiary) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.uptSleepDiaryByCode(yhSleepUserDiary);
	}

	@Override
	public Integer findSleepByuserCodeCount(String usercode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.findSleepByuserCodeCount(usercode);
	}

	@Override
	public Integer findUserSleepLXNum(String usercode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.findUserSleepLXNum(usercode);
	}

	@Override
	public YhSleepUserDiaryApp findTodaySleepInfo(String usercode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.findTodaySleepInfo(usercode);
	}

	@Override
	public List<YhSleepUserDiaryApp> selectThisMonthData(String usercode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selectThisMonthData(usercode);
	}

	@Override
	public YhSleepUserDiaryApp findTodaySleepInfo2(String usercode, String sleeptime) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.findTodaySleepInfo2(usercode,sleeptime);
	}

	@Override
	public List<YhSleepUserDiaryApp> selectlastmonthData(String userCode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selectlastmonthData(userCode);
	}

	@Override
	public List<YhSleepUserDiaryApp> selectlastweekData(String userCode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selectlastweekData(userCode);
	}

	@Override
	public List<YhSleepUserDiaryApp> selectthisweekData(String userCode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selectthisweekData(userCode);
	}

	@Override
	public List<YhSleepUserDiaryApp> selectthisyearData(String userCode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selectthisyearData(userCode);
	}

	@Override
	public List<YhSleepUserDiaryApp> selectlastyearData(String userCode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selectlastyearData(userCode);
	}

	@Override
	public Integer findUserSleepLXNum2(String usercode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.findUserSleepLXNum2(usercode);
	}
//本周
	@Override
	public  int[] findSleepUserDuary(String userCode) {
		// TODO Auto-generated method stub
				int[] zhou=new int[7];
				List<YhSleepUserDiaryApp> findFoodUserDuary = yhSleepUserDiaryAppMapper.findSleepUserDuary(userCode);
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
	public int[] findSleepastWeekUserData(String userCode) {
		int[] lastweek=new int[7];
		List<YhSleepUserDiaryApp> list = yhSleepUserDiaryAppMapper.findSleepastWeekUserData(userCode);
		int i = 0;
		for (int j=0;j<7;j++) {
			int daynum = list.get(j).getDaynum();
			if(daynum>0){
				i++;
			}else{
				i=0;
			}
			lastweek[j]=i;
		}
		return lastweek;
	}

	@Override
	public int[] findSleepUserDuaryYue(String userCode, String year, String month) {
		// TODO Auto-generated method stub
				int[] yuelist=new int[31];
				HashMap<String, Integer> findFoodUserDuaryYue = yhSleepUserDiaryAppMapper.findSleepUserDuaryYue(userCode, year, month);
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
	public int[] findSleepUserDiaryByYear(String userCode, String year) {
		// TODO Auto-generated method stub
				int[] yearlist=new int[12];
				int[] yuelist=new int[31];
				 for (int i = 1; i < yearlist.length+1; i++) {
					 HashMap<String, Integer> findFoodUserDiaryByYear = yhSleepUserDiaryAppMapper.findSleepUserDiaryByYear(userCode, year, String.valueOf(i));
					 if(null==findFoodUserDiaryByYear){
						 yearlist[i-1]=0;
						 continue;
						
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
						list.remove(list.size()-1);
						int K=0;
						int num=0;
						for (Map.Entry<String, Integer> mapping : list){
							
				            System.out.println(mapping.getKey()+": "+mapping.getValue());
				            
				            	Integer valueOf = Integer.valueOf(""+mapping.getValue());
				            	if(valueOf>0){
				            		num++;
				            	}else{
				            		num=0;
				            	}
				            	yuelist[K]=num;
				          
				            
				            
				            
				            K++;
				        }    
						System.out.println("List++++"+i+" : "+yuelist.toString());
						int asInt = Arrays.stream(yuelist).max().getAsInt();
						 yearlist[i-1]=asInt;
						
					
				}
				 
				
			 
				 
				 
				 
				return yearlist;
			}

	@Override
	public YhSleepUserDiaryApp selectUserIntradayData(String userCode) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selectUserIntradayData(userCode);
	}

	@Override
	public YhSleepUserDiaryApp selectsleepByParam(String userCode, String sleepDiarytime) {
		// TODO Auto-generated method stub
		return yhSleepUserDiaryAppMapper.selectsleepByParam(userCode, sleepDiarytime);
	}
	

	

	
}
