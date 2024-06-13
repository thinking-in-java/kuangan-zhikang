package com.yihe.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.common.core.text.Convert;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.InformationSummary;
import com.yihe.system.domain.YHUserSportDiaryVOJO;
import com.yihe.system.domain.YhSportDiary;
import com.yihe.system.mapper.YhSportDiaryMapper;
import com.yihe.system.service.IYhSportDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 运动记录Service业务层处理
 * 
 * @author yihe
 * @date 2020-04-08
 */
@Service
public class YhSportDiaryServiceImpl implements IYhSportDiaryService {
	@Autowired
	private YhSportDiaryMapper yhSportDiaryMapper;

	/**
	 * 查询运动记录
	 * 
	 * @param uSportId
	 *            运动记录ID
	 * @return 运动记录
	 */
	@Override
	public YhSportDiary selectYhSportDiaryById(Long uSportId) {
		return yhSportDiaryMapper.selectYhSportDiaryById(uSportId);
	}

	/**
	 * 查询运动记录列表
	 * 
	 * @param yhSportDiary
	 *            运动记录
	 * @return 运动记录
	 */
	@Override
	public List<YhSportDiary> selectYhSportDiaryList(YhSportDiary yhSportDiary) {
		return yhSportDiaryMapper.selectYhSportDiaryList(yhSportDiary);
	}

	/**
	 * 新增运动记录
	 * 
	 * @param yhSportDiary
	 *            运动记录
	 * @return 结果
	 */
	@Override
	public int insertYhSportDiary(YhSportDiary yhSportDiary) {
		return yhSportDiaryMapper.insertYhSportDiary(yhSportDiary);
	}

	/**
	 * 修改运动记录
	 * 
	 * @param yhSportDiary
	 *            运动记录
	 * @return 结果
	 */
	@Override
	public int updateYhSportDiary(YhSportDiary yhSportDiary) {
		return yhSportDiaryMapper.updateYhSportDiary(yhSportDiary);
	}

	/**
	 * 删除运动记录对象
	 * 
	 * @param ids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteYhSportDiaryByIds(String ids) {
		return yhSportDiaryMapper.deleteYhSportDiaryByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除运动记录信息
	 * 
	 * @param uSportId
	 *            运动记录ID
	 * @return 结果
	 */
	@Override
	public int deleteYhSportDiaryById(Long uSportId) {
		return yhSportDiaryMapper.deleteYhSportDiaryById(uSportId);
	}

	@Override
	public PageInfo<YHUserSportDiaryVOJO> selectYhSportDiaryByusercodeForWeb(String standard,String usercode, String nameLike,
			String usernameLike, String strTime, String endTime, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YHUserSportDiaryVOJO> list = yhSportDiaryMapper.selectYhSportDiaryByusercodeForWeb(standard,usercode, nameLike,
				usernameLike, strTime, endTime);
		return new PageInfo<>(list);
	}

	@Override
	public int findSportDiaryByUsercodeForCount(String usercode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.findSportDiaryByUsercodeForCount(usercode);
	}

	@Override
	public int findSportDiaryByusercodeForDetails(String usercode) {
		// TODO Auto-generated method stub
		
		//261=0.08=20秒;261=0.08=20秒;260=0.08=20秒;261=2.49=30分;
		List<String> findSportDiaryByusercodeForDetails = yhSportDiaryMapper
				.findSportDiaryByusercodeForDetails(usercode);

		List<String> list = new ArrayList<String>();
		for (String string : findSportDiaryByusercodeForDetails) {
			if(null==string){
				continue;
			}
			String[] split = string.split(";");
			for (String string2 : split) {
				////261=0.08=20秒
				String[] split2 = string2.split("=");
				String string3 = split[0];
				list.add(string3);
			}
			
		}

		if (list.size() == 0) {
			return 0;
		}
		List<String> removeDuplicate = removeDuplicate(list);

		return removeDuplicate.size();
	}

	public static List<String> removeDuplicate(List<String> list) {
		HashSet<String> h = new HashSet<String>(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	@Override
	public int[] findSportDiaryByUserCodeForWeekDay(String usercode) {
		// TODO Auto-generated method stub
		List<InformationSummary> findSportDiaryByUserCodeForWeekDay = yhSportDiaryMapper.findSportDiaryByUserCodeForWeekDay(usercode);
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
	public int[] findSportDiaryByUserCodeForMonth(String usercode, String year, String month) {
		// TODO Auto-generated method stub
		int[] yuelist=new int[31];
		int i=0;
		int num=0;
		HashMap<String, Integer> findFoodUserDuaryYue = yhSportDiaryMapper.findSportDiaryByUserCodeForMonth(usercode, year, month);
		
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
	public int[] findSportDiaryByuserCodeForyear(String usercode, String year) {
		// TODO Auto-generated method stub
		int[] yearlist=new int[12];
		HashMap<String, Integer> findFoodUserDiaryByYear = yhSportDiaryMapper.findSportDiaryByuserCodeForyear(usercode, year);
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
	public int findSportDiaryByuserCodeForyear1(String usercode, String year) {
		HashMap<String, Integer> findFoodUserDiaryByYear = yhSportDiaryMapper.findSportDiaryByuserCodeForyear(usercode, year);
		if(null==findFoodUserDiaryByYear){
			return 0;
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
		int num=0;
		for (Map.Entry<String, Integer> mapping : list){

			System.out.println(mapping.getKey()+": "+mapping.getValue());

			num += Integer.valueOf(""+mapping.getValue());
		}
		return num;
	}

	@Override
	public List<YhSportDiary> selectSportDiary(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectSportDiary(map);
	}

	@Override
	public int selectSportDiaryConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectSportDiaryConut(map);
	}

	@Override
	public List<YhSportDiary> selectSportDiaryCtime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectSportDiaryCtime(map);
	}

	@Override
	public List<YhSportDiary> showSportRecordAnalyze(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.showSportRecordAnalyze(map);
	}

	@Override
	public int[] findSportUserDuaryLastWeek(String usercode) {
		// TODO Auto-generated method stub
		List<InformationSummary> findSportDiaryByUserCodeForWeekDay = yhSportDiaryMapper.findSportUserDuaryLastWeek(usercode);
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
	public YhSportDiary showsky(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.showsky(map);
	}

	@Override
	public List<YhSportDiary> selectBySportTime(String userCode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectBySportTime(userCode);
	}

	@Override
	public List<YhSportDiary> selectBySportMonth(String userCode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectBySportMonth(userCode);
	}

	@Override
	public List<YhSportDiary> selectBySportlastMonth(String userCode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectBySportlastMonth(userCode);
	}

	@Override
	public List<YhSportDiary> selectBySportthisWeek(String userCode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectBySportthisWeek(userCode);
	}

	@Override
	public List<YhSportDiary> selectBySportlastWeek(String userCode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectBySportlastWeek(userCode);
	}

	@Override
	public List<YhSportDiary> selectBySportthisYear(String userCode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectBySportthisYear(userCode);
	}

	@Override
	public List<YhSportDiary> selectBySportlastYear(String userCode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectBySportlastYear(userCode);
	}

	@Override
	public YhSportDiary showSportWhetherThere(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.showSportWhetherThere(map);
	}

	@Override
	public List<YhSportDiary> showSportStepStatistics(String userCode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.showSportStepStatistics(userCode);
	}

	@Override
	public int insertSelective(YhSportDiary record) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhSportDiary record) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer uSportId) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.deleteByPrimaryKey(uSportId);
	}

	@Override
	public YhSportDiary selectByPrimaryKey(Integer uSportId) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.selectByPrimaryKey(uSportId);
	}

	@Override
	public YhSportDiary ShowpresentDiary(String userCode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.ShowpresentDiary(userCode);
	}

	@Override
	public int ShowpnumberDiary(String userCode) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.ShowpnumberDiary(userCode);
	}

	@Override
	public int updateByPrimaryKeySelective2(YhSportDiary record) {
		// TODO Auto-generated method stub
		return yhSportDiaryMapper.updateByPrimaryKeySelective2(record);
	}

}
