/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ibm.icu.util.Calendar;
import com.yihe.system.domain.InterventionAdjustment;
import com.yihe.system.domain.SportInterveneVojo;
import com.yihe.system.domain.YHDocGroup;
import com.yihe.system.domain.YhSportRecommendRecord;
import com.yihe.system.domain.YhSportRecommendRecordVojo;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YHDocGroupMapper;
import com.yihe.system.service.YhSportRecommendRecordService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 运动干预
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhSportMeddleController")
public class YhSportMeddleController {
	
	@Autowired
	YHDocGroupMapper YHDocGroupMapper;
	
	@Autowired
	YhSportRecommendRecordService yhSportRecommendRecordService;
	
	@Autowired
	YhUserReginfoService yhUserReginfoService;
	
	@ApiOperation(value = "干预调整")
	@RequestMapping(value = "/SportInterveneInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result adjustment(String usercode, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit, @RequestParam(required = false) String orgid,
			@RequestParam(required = false) String groupid, @RequestParam(required = false) String mode,
			@RequestParam(required = false) String sex, @RequestParam(required = false) String strength,
			@RequestParam(required = false) String ageStr, @RequestParam(required = false) String ageEnd,
			@RequestParam(required = false) String heightStr, @RequestParam(required = false) String heightEnd,
			@RequestParam(required = false) String weightStr, @RequestParam(required = false) String Bimweight,
			@RequestParam(required = false) String kayStr,
			@RequestParam(required = false) String recordname, @RequestParam(required = false) String username

	) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("code", 0);
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgid", orgid);
		if (StringUtil.isNotEmpty(groupid)) {
			groupid = "f" + groupid + "_";
		}
		map.put("groupid", groupid);
		map.put("mode", mode);
		map.put("sex", sex);
		map.put("strength", strength);
		map.put("ageStr", ageStr);
		map.put("ageEnd", ageEnd);
		map.put("heightStr", heightStr);
		map.put("heightEnd", heightEnd);
		map.put("weightStr", weightStr);
		map.put("kayStr", kayStr);
		map.put("recordname", recordname);
		map.put("username", username);
		map.put("Bimweight", Bimweight);
		map.put("usercode", yh.getUserCode());
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println("key=" + entry.getKey() + " value=" + entry.getValue());
		}

		PageInfo<SportInterveneVojo> list = yhSportRecommendRecordService.SportInterveneInfo(map, page,
				limit);
		List<SportInterveneVojo> list2 = list.getList();
		if (null != list2 && list2.size() > 0) {
			for (int i = 0; i < list2.size(); i++) {
				SportInterveneVojo interventionAdjustment = list2.get(i);
				String flag_Dgroup = interventionAdjustment.getFlag_Dgroup();
				String forGroup = forGroup(flag_Dgroup);
				interventionAdjustment.setFlag_Dgroup(forGroup);
				if(interventionAdjustment.getAge()==null) {//如果年龄等于空
					
//					YhUserReginfo reg=yhUserReginfoService.selectByPrimaryKey(interventionAdjustment.getUsercode());
//					Date date=new Date();
//					interventionAdjustment.setAge(dayCompare(reg.getBirthDate(), date));
					interventionAdjustment.setAge("--");
				}
				String sportName="";
				List<YhSportRecommendRecord> recolist= yhSportRecommendRecordService.seelctCode(interventionAdjustment.getUsercode());
				if(recolist.size()>0) {
					for (YhSportRecommendRecord yhSportRecommendRecord : recolist) {
						sportName+=yhSportRecommendRecord.getSportName()+";";
					}
					interventionAdjustment.setPlanName(sportName);
				}else {
					interventionAdjustment.setPlanName("--");
				}
				
				
			}
		}

		rsMap.put("count", list.getTotal());

		rsMap.put("msg", "ok");
		rsMap.put("data", list2);
		return Result.ok(rsMap);
	}

	public String forGroup(String str) {
		if (StringUtil.isEmpty(str) || "0".equals(str)) {
			return "未分组";
		}
		String groupInfo = "";
		// f224_g1;f226_g2;f221_g5;
		String[] split = str.split(";");
		for (String string : split) {
			// f224_g1
			if(string!=null && !string.equals("")) {
				
			String[] split2 = string.split("_");
			String string2 = split2[0];
				String substring = string2.substring(1);
				YHDocGroup gInfo = YHDocGroupMapper.selectByPrimaryKey(Integer.valueOf(substring));
				if (null != gInfo) {
					groupInfo = groupInfo + gInfo.getGname() + ";";
				}
				
			}
			
		}
		return groupInfo;
	}

	
	/**
	 * 计算2个日期之间相差的  以年、月、日为单位，各自计算结果是多少
	 * 比如：2011-02-02 到  2017-03-02
	 *                                以年为单位相差为：6年
	 *                                以月为单位相差为：73个月
	 *                                以日为单位相差为：2220天
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static String dayCompare(Date fromDate,Date toDate){
	    Calendar  from  =  Calendar.getInstance();
	    from.setTime(fromDate);
	    Calendar  to  =  Calendar.getInstance();
	    to.setTime(toDate);
	    //只要年月
	    int fromYear = from.get(Calendar.YEAR);
	    int fromMonth = from.get(Calendar.MONTH);

	    int toYear = to.get(Calendar.YEAR);
	    int toMonth = to.get(Calendar.MONTH);

	    int year = toYear  -  fromYear;
	    int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
	    int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
	    return String.valueOf(year);
	}
	
	
}
