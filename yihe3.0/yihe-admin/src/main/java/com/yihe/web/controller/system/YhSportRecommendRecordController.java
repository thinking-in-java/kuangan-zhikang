/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.CampaignVojo;
import com.yihe.system.domain.InterventionAdjustment;
import com.yihe.system.domain.SportInterveneVojo;
import com.yihe.system.domain.SportmealVojo;
import com.yihe.system.domain.YhSportFrequencyWeb;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.domain.YhSportRecommendRecord;
import com.yihe.system.domain.YhSportSetmeal;
import com.yihe.system.domain.YhSportSubject;
import com.yihe.system.mapper.YhSportMotionMapper;
import com.yihe.system.mapper.YhSportSetmealMapper;
import com.yihe.system.mapper.YhSportSubjectMapper;
import com.yihe.system.service.YhSportFrequencyWebService;
import com.yihe.system.service.YhSportRecommendRecordService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;

/**
 * 运动套餐
 * 
 * @author dong
 * @date 2020-4-20 11:49:43
 */
@Controller
@RequestMapping("/YhSportRecommendRecordController")
public class YhSportRecommendRecordController {
	
	@Autowired
	private YhSportRecommendRecordService yhSportRecommendRecordService;
	
	@Autowired
	private YhSportSetmealMapper yhSportSetmealMapper;
	
	@Autowired
	private YhSportSubjectMapper yhSportSubjectMapper;
	
	@Autowired
	private YhSportMotionMapper yhSportMotionMapper;
	
	@Autowired
	private YhSportFrequencyWebService yhSportFrequencyWebService;
	
	
	@ApiOperation(value = "根据code查询用户运动频率")
    @RequestMapping(value = "/showUserFrequency", method = RequestMethod.POST)
	@ResponseBody
    public Result  showUserFrequency(String userCode) {
		YhSportFrequencyWeb fre=yhSportFrequencyWebService.showUserFrequency(userCode);
        return Result.ok(fre.getFrFrequency());
    }
	
	
	@ApiOperation(value = "根据code查询出有几分 套餐")
    @RequestMapping(value = "/seelctCode", method = RequestMethod.POST)
	@ResponseBody
    public Result  seelctCode(String userCode) {
    	List<YhSportRecommendRecord> list=yhSportRecommendRecordService.seelctCode(userCode);
        return Result.ok(list);
    }
	
	
	@ApiOperation(value = "查找运动")
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.POST)
	@ResponseBody
    public Map<String,Object>  selectByPrimaryKey(String diaryDetails) {
		 String[] arr1 =diaryDetails.split(";");
		 List<YhSportMotion> rests=new ArrayList<YhSportMotion>();
			List<YhSportMotion> course=new ArrayList<YhSportMotion>();
		for (int k = 0; k < arr1.length; k++) {
			 String[] arr2 =arr1[k].split("=");
			 char src=arr2[2].charAt(arr2[2].length()-1);
			 if(String.valueOf(src).equals("分")){
				 YhSportMotion cook=yhSportMotionMapper.selectByPrimaryKey(Integer.valueOf(arr2[0]));
					cook.setKcal(arr2[2]);
					rests.add(cook);
		    	}else{
		    		 YhSportMotion cook=yhSportMotionMapper.selectByPrimaryKey(Integer.valueOf(arr2[0]));
						cook.setKcal(arr2[2]);
						course.add(cook);
		    	}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rests", rests);
 		map.put("course", course);
 		return map;
    }
	
	
	
	
	@ApiOperation(value = "查找运动套餐详情")
    @RequestMapping(value = "/selectexerciseshow", method = RequestMethod.POST)
	@ResponseBody
    public Result  selectexerciseshow(int id) {
		SportmealVojo vojo=new SportmealVojo();
		
		YhSportSetmeal meal=yhSportSetmealMapper.selectByPrimaryKey(id);//运动套餐
		if(meal!=null) {
			 String[] arr = meal.getSubjectId().split(",");//主题id
			 List<CampaignVojo> zong=new ArrayList<CampaignVojo>();
			for (int i = 0; i < arr.length; i++) {
				List<YhSportMotion> sport=new ArrayList<YhSportMotion>();
				CampaignVojo camp=new CampaignVojo();
				YhSportSubject ject=yhSportSubjectMapper.selectByPrimaryKey(Integer.valueOf(arr[i]));//查询主题表
				 String[] arr1 = ject.getSubjectMotion().split(",");//动作id
				 for (int j = 0; j < arr1.length; j++) {//循环动作id
					 YhSportMotion tion=yhSportMotionMapper.selectByPrimaryKey(Integer.valueOf(arr1[j]));
					 sport.add(tion);
				}
				 camp.setSport(sport);
				 zong.add(camp);
			}
			vojo.setVojo(zong);
			vojo.setName(meal.getSetmealMame()+"套餐");
			vojo.setApply(meal.getSetmealMame()+"患者");
			vojo.setDescribe(meal.getDescribe());
			
		}
        return Result.ok(vojo);
    }
	
	
	@ApiOperation(value = "查找运动套餐所有")
    @RequestMapping(value = "/selectSetmealshow", method = RequestMethod.POST)
	@ResponseBody
    public Result  showheadline(String setmealMame,String userCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("setmealMame", setmealMame);
    	List<YhSportSetmeal> list=yhSportSetmealMapper.selectSetmealshow(map);
    	List<YhSportRecommendRecord> Record=yhSportRecommendRecordService.seelctCode(userCode);
    	for (YhSportSetmeal yhSportSetmeal : list) {
    	for (YhSportRecommendRecord yhSportRecommendRecord : Record) {
    			if(yhSportRecommendRecord.getSetmealid().equals(String.valueOf(yhSportSetmeal.getId()))) {
    				yhSportSetmeal.setOneself(1);
    				 break;
    			}else {
    				yhSportSetmeal.setOneself(0);
    			}
    		}
		}
    	
    	
    	
    	
        return Result.ok(list);
    }
	
	
	@ApiOperation(value = "查找运动套餐所有干预计划")
    @RequestMapping(value = "/showmeddle", method = RequestMethod.POST)
	@ResponseBody
    public Result  showmeddle(String setmealMame) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("setmealMame", setmealMame);
    	List<YhSportSetmeal> list=yhSportSetmealMapper.selectSetmealshow(map);
        return Result.ok(list);
    }
	
	@ApiOperation(value = "增加内容")
    @RequestMapping(value = "/insertSelective", method = RequestMethod.POST)
	@ResponseBody
    public Result  deleteByPrimaryKey(String pitch,String userCode) {
		 String[] strArr = pitch.split(",");
		
		 
		 List<YhSportRecommendRecord> list=yhSportRecommendRecordService.seelctCode(userCode);//原先的报告
		 for (YhSportRecommendRecord yhSportRecommendRecord : list) {
			 if(Arrays.asList(strArr).contains(yhSportRecommendRecord.getSetmealid())==false) {
				 System.out.println(yhSportRecommendRecord.getSetmealid());
				 yhSportRecommendRecord.setRecordType(2);
				 yhSportRecommendRecordService.updateByPrimaryKeySelective(yhSportRecommendRecord);
			 }
		}
		 int  k=0;
		 Map<String, Object> map=new HashMap<String, Object>();
		 map.put("userCode", userCode);
		 for (int i = 0; i < strArr.length; i++) {
			 map.put("setmealid", strArr[i]);
				int st=yhSportRecommendRecordService.seelctCodeCount(map);
				if(st==0) {
					YhSportRecommendRecord record=new YhSportRecommendRecord();
					YhSportSetmeal tao=yhSportSetmealMapper.selectByPrimaryKey(Integer.valueOf(strArr[i]));//查询套餐
					
					if(tao!=null) {
						 String[] arr2 =tao.getSubjectId().split(",");
						 record.setUserCode(userCode);
						record.setDiseaseid(String.valueOf(tao.getDiseaseCode()));
						record.setSetmealid(String.valueOf(tao.getId()));
						record.setRecordMode("医生手动");
						record.setSportName(tao.getSetmealMame());
						record.setStandby(String.valueOf(arr2.length));
						k=yhSportRecommendRecordService.insertSelective(record);
					}
				}
		}
		 
		
		
        return Result.ok(k);
    }
	
	
	@ApiOperation(value = "批量处理课程")
    @RequestMapping(value = "/batchinsertSelective", method = RequestMethod.POST)
	@ResponseBody
    public Result  batchdeleteByPrimaryKey(@RequestBody SportInterveneVojo info) {
	        
		 String[] strArr = info.getPitch().split(",");
		int succeed=0;
		int fail=0;
		 if (null != info && info.getSportarrayList().size() > 0) {
				for (int i = 0; i < info.getSportarrayList().size(); i++) {
					
					 List<YhSportRecommendRecord> list=yhSportRecommendRecordService.seelctCode(info.getSportarrayList().get(i));//原先的报告
					 for (YhSportRecommendRecord yhSportRecommendRecord : list) {
						 if(Arrays.asList(strArr).contains(yhSportRecommendRecord.getSetmealid())==false) {
							 System.out.println(yhSportRecommendRecord.getSetmealid());
							 yhSportRecommendRecord.setRecordType(2);
							 yhSportRecommendRecordService.updateByPrimaryKeySelective(yhSportRecommendRecord);
						 }
					}
					 Map<String, Object> map=new HashMap<String, Object>();
					 map.put("userCode", info.getSportarrayList().get(i));
					 for (int j = 0; j < strArr.length; j++) {
						 map.put("setmealid", strArr[j]);
							int st=yhSportRecommendRecordService.seelctCodeCount(map);
							if(st==0) {
								YhSportRecommendRecord record=new YhSportRecommendRecord();
								YhSportSetmeal tao=yhSportSetmealMapper.selectByPrimaryKey(Integer.valueOf(strArr[j]));//查询套餐
								
								if(tao!=null) {
									 String[] arr2 =tao.getSubjectId().split(",");
									 record.setUserCode(info.getSportarrayList().get(i));
									record.setDiseaseid(String.valueOf(tao.getDiseaseCode()));
									record.setSetmealid(String.valueOf(tao.getId()));
									record.setRecordMode("医生手动");
									record.setSportName(tao.getSetmealMame());
									record.setStandby(String.valueOf(arr2.length));
									 int  k=yhSportRecommendRecordService.insertSelective(record);
									 if(k==1) {
										 succeed+=1;
									 }else {
										 fail+=1;
									 }
								}
							}
					}
		 
				}
		 }
		 Map<String, Object> map = new HashMap<String, Object>();
			map.put("add", succeed);
			map.put("noadd", fail);
        return Result.ok(map);
    }
	
	
	
    //使用List
public static boolean useList(String[] arr,String targetValue){
    return Arrays.asList(arr).contains(targetValue);
}


	public static void main(String[] args) {
		 String[] arr=new String[]{"CD","BC","EF","DE","AB","JK"};
		 System.out.println(useList(arr, "CD1"));
	}
	
	
	
	/**
	 * 查询用户查看食物套餐内容
	 * @param page
	 * @param limit
	 * @return
	 */
     @ApiOperation(value = "用户查看运动套餐")
     @RequestMapping(value = "/selectPersonageSport", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectPersonageSport(Integer page,Integer limit,String usercode){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("userCode", usercode);
 		Map<String,Object> map = new HashMap<String,Object>();
 		List<YhSportRecommendRecord> list = yhSportRecommendRecordService.selectPersonageSport(map1);
 		for (YhSportRecommendRecord yhSportRecommendRecord : list) {
 			yhSportRecommendRecord.setEvolve(yhSportRecommendRecord.getNumber()+"/"+yhSportRecommendRecord.getStandby());
		}
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhSportRecommendRecordService.selectPersonageSportConut(map1));
 		map.put("data", list);
 		return map;
 	}

}
