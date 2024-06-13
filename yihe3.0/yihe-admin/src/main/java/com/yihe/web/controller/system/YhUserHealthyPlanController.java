package com.yihe.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.yihe.common.annotation.Log;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.common.core.page.TableDataInfo;
import com.yihe.common.enums.BusinessType;
import com.yihe.common.utils.poi.ExcelUtil;
import com.yihe.system.domain.HealthPlanVOJO;
import com.yihe.system.domain.YhUserHealthyPlan;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.IYhUserHealthyPlanService;
import com.yihe.system.service.impl.YhUserHealthyPlanServiceImpl;
import com.yihe.system.service.impl.YhUserMakeCardServiceImpl;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户健康计划日志Controller
 * 
 * @author yihe
 * @date 2020-04-27
 */
@Controller
@RequestMapping("/system/plan")
public class YhUserHealthyPlanController extends BaseController
{
	
	@Autowired
	private YhUserMakeCardServiceImpl yhUserMakeCardServiceImpl;
	@Autowired
	private YhUserHealthyPlanServiceImpl yhUserHealthyPlanServiceImpl;
	
	@RequestMapping(value = "/findUserHealthyPlanInfoByDoctor", method = RequestMethod.GET)
	@ResponseBody
	public Result findUserHealthyPlanInfoByDoctor(@RequestParam(required = false) String nameLike,
			@RequestParam(required = false) String planName,
			@RequestParam(required = false) String usernameLike,
			@RequestParam(required = false) String strTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {
		 if (StringUtil.isNotEmpty(startTime)) {
				String[] split = startTime.split(" - ");
				strTime = split[0];
				endTime = split[1];
			}
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlan = yhUserHealthyPlanServiceImpl.findUserHealthyPlanInfoByDoctor(usercode, nameLike, usernameLike, planName, strTime, endTime, page, limit);
		List<HealthPlanVOJO> list = findUserInfoBydoctorForPlan.getList();
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", findUserInfoBydoctorForPlan.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}
	
	
	
	@RequestMapping(value = "/findplanInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result findplanInfo(@RequestParam(required = false) String nameLike,
			@RequestParam(required = false) String orgid,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlan = yhUserMakeCardServiceImpl.findUserInfoBydoctorForPlan(usercode,orgid,nameLike, page, limit);
		List<HealthPlanVOJO> list = findUserInfoBydoctorForPlan.getList();
		for (HealthPlanVOJO healthPlanVOJO : list) {
			String usercode2 = healthPlanVOJO.getUsercode();
			HealthPlanVOJO findHealthyPlanUserIsNum = yhUserHealthyPlanServiceImpl.findHealthyPlanUserIsNum(usercode2);
			if(null==findHealthyPlanUserIsNum){
				continue;
			}
			//计划次数
			healthPlanVOJO.setPlanNum(findHealthyPlanUserIsNum.getPlanNum());
			//当前计划开始时间
			healthPlanVOJO.setPlanStrTime(findHealthyPlanUserIsNum.getPlanStrTime());
			//计划名称
			healthPlanVOJO.setPlanName(findHealthyPlanUserIsNum.getPlanName());
			//计划执行天数
			healthPlanVOJO.setPlanRunDay(findHealthyPlanUserIsNum.getPlanRunDay());
			healthPlanVOJO.setPlanState(findHealthyPlanUserIsNum.getPlanState());
		}
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", findUserInfoBydoctorForPlan.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}



	@RequestMapping(value = "/findplanInfo2", method = RequestMethod.GET)
	@ResponseBody
	public Result findplanInfo2(@RequestParam(required = false) String nameLike,
							   @RequestParam(required = false) String orgid,
							   @RequestParam(required = false, defaultValue = "1") int page,
							   @RequestParam(required = false, defaultValue = "5") int limit) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlan = yhUserMakeCardServiceImpl.findUserInfoBydoctorForPlan2(usercode,orgid,nameLike, page, limit);
		List<HealthPlanVOJO> list = findUserInfoBydoctorForPlan.getList();
		for (HealthPlanVOJO healthPlanVOJO : list) {
			String usercode2 = healthPlanVOJO.getUsercode();
			HealthPlanVOJO findHealthyPlanUserIsNum = yhUserHealthyPlanServiceImpl.findHealthyPlanUserIsNum2(usercode2);
			if(null==findHealthyPlanUserIsNum){
				continue;
			}
			//计划次数
			healthPlanVOJO.setPlanNum(findHealthyPlanUserIsNum.getPlanNum());
			//当前计划开始时间
			healthPlanVOJO.setPlanStrTime(findHealthyPlanUserIsNum.getPlanStrTime());
			//计划名称
			healthPlanVOJO.setPlanName(findHealthyPlanUserIsNum.getPlanName());
			//计划执行天数
			healthPlanVOJO.setPlanRunDay(findHealthyPlanUserIsNum.getPlanRunDay());
			healthPlanVOJO.setPlanState(findHealthyPlanUserIsNum.getPlanState());
		}
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", findUserInfoBydoctorForPlan.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}

	
	@RequestMapping(value = "/findPlanDiaryInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result findPlanDiaryInfo(@RequestParam(required = false) String nameLike,
			@RequestParam(required = false) String planName,
			@RequestParam(required = false) String usernameLike,
			@RequestParam(required = false) String strTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {
		if (StringUtil.isNotEmpty(startTime)) {
			String[] split = startTime.split(" - ");
			strTime = split[0];
			endTime = split[1];
		}
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlan = yhUserMakeCardServiceImpl.findUserInfoBydoctorForPlanByjilv(usercode, nameLike, usernameLike, planName, strTime, endTime, page, limit);
		List<HealthPlanVOJO> list = findUserInfoBydoctorForPlan.getList();
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", findUserInfoBydoctorForPlan.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}
	
	@RequestMapping(value = "/findPlanDiaryInfoByUsercode", method = RequestMethod.POST)
	@ResponseBody
	public Result findPlanDiaryInfoByUsercode(@RequestParam(required = false) String usercode,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {
	 
		 
		PageInfo<HealthPlanVOJO> findUserInfoBydoctorForPlan = yhUserHealthyPlanServiceImpl.findHealthyPlanUserByuserCode(usercode, page, limit);
		List<HealthPlanVOJO> list = findUserInfoBydoctorForPlan.getList();
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", findUserInfoBydoctorForPlan.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}
	
	
	
	 @ApiOperation(value = "查询个人用户计划日记")
     @RequestMapping(value = "/selectPlanDiary", method = RequestMethod.POST)
 	@ResponseBody
 	public Result selectPlanDiary(@RequestParam(required = false) String usercode,
 			@RequestParam(required = false) String complete,
 			@RequestParam(required = false) String planName,
 			@RequestParam(required = false) String strTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit
			){
		 if (StringUtil.isNotEmpty(startTime)) {
				String[] split = startTime.split(" - ");
				strTime = split[0];
				endTime = split[1];
			}
		 
		 PageInfo<HealthPlanVOJO> findUserPlanInfoByUserCode = yhUserHealthyPlanServiceImpl.findUserPlanInfoByUserCode(usercode,complete, planName, strTime, endTime, page, limit);
		 List<HealthPlanVOJO> list = findUserPlanInfoByUserCode.getList();
 		 
		 Map<String, Object> rsMap = new HashMap<String, Object>();
			rsMap.put("count", findUserPlanInfoByUserCode.getTotal());
			rsMap.put("code", 0);
			rsMap.put("msg", "ok");
			rsMap.put("data", list);
			return Result.ok(rsMap);
 	}
	
	 @ApiOperation(value = "查询用户健康计划记录内容统计图")
     @RequestMapping(value = "/selectPlanDiaryCtime", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectDiaryCtime(String usercode,String ctime) throws ParseException{
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		map1.put("userCode", usercode);
 		if(ctime!=null  && !ctime.equals("")) {
 			String[] strArr = ctime.split(" - ");
 		map1.put("cTime", strArr[0]);
 		map1.put("cTime1", strArr[1]);
 		}
 		
 		 List<HealthPlanVOJO> list = yhUserHealthyPlanServiceImpl.findUserPlanTongJiTu(usercode);
 		String[] cTime = new String[list.size()];
 		String[] energyLevel = new String[list.size()];
 		String[] energyKcal = new String[list.size()];
 		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
 		Date date = null;   
 		int i=0;
 		for (HealthPlanVOJO yhFoodUserDiary : list) {
 			i++;
 			 date = format1.parse(yhFoodUserDiary.getPlanStrTime()); 
 			String dateTime = format1.format(date);
 			cTime[i-1]=""+dateTime+"";
 			energyLevel[i-1]=""+yhFoodUserDiary.getCourseseq()+"";
 			energyKcal[i-1]=""+yhFoodUserDiary.getPlanNum()+"";
		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("cTime", cTime);
 		map.put("energyLevel", energyLevel);
 		map.put("energyKcal",energyKcal);
 		return map;
 	}
	 
	 
	 
	 @ApiOperation(value = "查询完成百分比")
	    @RequestMapping(value = "/selectplancompleteInfo", method = RequestMethod.POST)
		@ResponseBody
	    public Map<String,Object>  selectplancompleteInfo(Integer planId,String planStrTime,String usercode,String planUnique) {
		 int numAllInfo = yhUserHealthyPlanServiceImpl.findPlanByPlanId(planId);
		 int completeNum = yhUserHealthyPlanServiceImpl.findPlanByTimeAndUserCodeAndPlanUnique(planStrTime, usercode, planUnique);
		 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("numAllInfo", numAllInfo);
	 		map.put("completeNum", completeNum);
	 		return map;
	    }
	 
	 @ApiOperation(value = "查询累计和连续天数")
	    @RequestMapping(value = "/selectCumulativeInfo", method = RequestMethod.POST)
		@ResponseBody
	    public Map<String,Object>  selectCumulativeInfo(String planUnique,String usercode) {
		 int cumulative = yhUserHealthyPlanServiceImpl.findPlanCumulativeInfo(planUnique,usercode);
		 int continuity = yhUserHealthyPlanServiceImpl.findPlanContinuityInfo(planUnique,usercode);
		 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("cumulative", cumulative);
	 		map.put("completeNum", continuity);
	 		return map;
	    }
	 
	 @ApiOperation(value = "查询已完成任务和未完成任务")
	    @RequestMapping(value = "/selectPlanDetailInfo", method = RequestMethod.POST)
		@ResponseBody
	    public Map<String,Object>  selectPlanDetailInfo(Integer id,Integer planId,Integer planDay) {
		 List<String> ywc=new ArrayList<String>();//已完成
		 List<String> wwc=new ArrayList<String>();//未完成
		 //当日已完成 13;2
		 String findPlanDetailByid = yhUserHealthyPlanServiceImpl.findPlanDetailByid(id);
		 if(StringUtil.isNotEmpty(findPlanDetailByid)){
			 String[] split = findPlanDetailByid.split(";");
			 for (String string : split) {
				 String findPlanDetailTitleById = yhUserHealthyPlanServiceImpl.findPlanDetailTitleById(string);
				 ywc.add(findPlanDetailTitleById);
			}
		 }
		 //当日全部任务1;2;3;4;13
		 String findPlanDetailByPlanIdAndPlanDay = yhUserHealthyPlanServiceImpl.findPlanDetailByPlanIdAndPlanDay(planId, planDay);
		 if(StringUtil.isNotEmpty(findPlanDetailByid)){
			 String[] split = findPlanDetailByPlanIdAndPlanDay.split(";");
			 for (String string : split) {
				 String findPlanDetailTitleById = yhUserHealthyPlanServiceImpl.findPlanDetailTitleById(string);
				 wwc.add(findPlanDetailTitleById);
			 }
		 }
		 
			  
				if(null!=ywc&&ywc.size()>0){
				 
					for (String string2 : ywc) {
						remove11(wwc, string2); 
					}
				}
		 
		 
		 
		 
		 
		 
		 Map<String,Object> map = new HashMap<String,Object>();
			map.put("wwc", wwc);
	 		map.put("ywc", ywc);
	 		return map;
	    }
	 
	  public static void main(String[] args) {
		  List<String> ywc=new ArrayList<String>();//已完成
			 List<String> wwc=new ArrayList<String>();//未完成
			 wwc.add("a");
			 wwc.add("b");
			 wwc.add("c");
			 wwc.add("d");
			 ywc.add("a");
			 for (String string : ywc) {
				 remove11(wwc, string); 
			}
			 
			 for (String string : wwc) {
				System.out.println(string);
			}
			 
	}
	  
	  public static void remove11(List<String> list, String target){
		  for(int i = list.size() - 1; i >= 0; i--){
			  String item = list.get(i);
			  if(target.equals(item)){
				  list.remove(item);
			  }
		  }
	  }
    private String prefix = "system/plan";

    @Autowired
    private IYhUserHealthyPlanService yhUserHealthyPlanService;

    @RequiresPermissions("system:plan:view")
    @GetMapping()
    public String plan()
    {
        return prefix + "/plan";
    }

    /**
     * 查询用户健康计划日志列表
     */
    @RequiresPermissions("system:plan:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(YhUserHealthyPlan yhUserHealthyPlan)
    {
        startPage();
        List<YhUserHealthyPlan> list = yhUserHealthyPlanService.selectYhUserHealthyPlanList(yhUserHealthyPlan);
        return getDataTable(list);
    }

    /**
     * 导出用户健康计划日志列表
     */
    @RequiresPermissions("system:plan:export")
    @Log(title = "用户健康计划日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YhUserHealthyPlan yhUserHealthyPlan)
    {
        List<YhUserHealthyPlan> list = yhUserHealthyPlanService.selectYhUserHealthyPlanList(yhUserHealthyPlan);
        ExcelUtil<YhUserHealthyPlan> util = new ExcelUtil<YhUserHealthyPlan>(YhUserHealthyPlan.class);
        return util.exportExcel(list, "plan");
    }

    /**
     * 新增用户健康计划日志
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户健康计划日志
     */
    @RequiresPermissions("system:plan:add")
    @Log(title = "用户健康计划日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(YhUserHealthyPlan yhUserHealthyPlan)
    {
        return toAjax(yhUserHealthyPlanService.insertYhUserHealthyPlan(yhUserHealthyPlan));
    }

    /**
     * 修改用户健康计划日志
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        YhUserHealthyPlan yhUserHealthyPlan = yhUserHealthyPlanService.selectYhUserHealthyPlanById(id);
        mmap.put("yhUserHealthyPlan", yhUserHealthyPlan);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户健康计划日志
     */
    @RequiresPermissions("system:plan:edit")
    @Log(title = "用户健康计划日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(YhUserHealthyPlan yhUserHealthyPlan)
    {
        return toAjax(yhUserHealthyPlanService.updateYhUserHealthyPlan(yhUserHealthyPlan));
    }

    /**
     * 删除用户健康计划日志
     */
    @RequiresPermissions("system:plan:remove")
    @Log(title = "用户健康计划日志", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(yhUserHealthyPlanService.deleteYhUserHealthyPlanByIds(ids));
    }
}
