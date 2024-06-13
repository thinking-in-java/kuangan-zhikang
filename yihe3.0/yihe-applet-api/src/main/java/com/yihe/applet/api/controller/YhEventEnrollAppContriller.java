/**
 * 
 */
package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhEventEnrollApp;
import com.yihe.applet.api.mapper.YhEventEnrollAppMapper;
import com.yihe.applet.api.mapper.YhEventRegisterAppMapper;
import com.yihe.applet.api.service.YhOrgTreeAppService;
import com.yihe.system.mapper.YhOrgTreeMapper;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 精彩活动
 * @author dong
 *
 */
@Controller
@RequestMapping("/api/YhEventEnrollAppContriller")
@Api(value = "YhEventEnrollAppContriller控制器", description = "YhEventEnrollAppContriller管理")
public class YhEventEnrollAppContriller {

	@Autowired
	YhEventEnrollAppMapper yhEventEnrollAppMapper;
	
	@Autowired
	YhEventRegisterAppMapper yhEventRegisterAppMapper;

	@Autowired
	private YhOrgTreeMapper yhOrgTreeMapper;

	@Autowired
	YhOrgTreeAppService yhOrgTreeAppService;

	/**
	 * 查询以发布的精彩活动
	 * @param orgCode 机构编号
	 * @return
	 */
	@RequestMapping(value = "/selecyByEnroll")
	@ResponseBody
	public Result selecyByEnroll(String orgCode){
		//自己所属机构及其上所有直系父节点的数据
		Map<String,Object> map1 = new HashMap<String,Object>();
		List<String> strings = yhOrgTreeAppService.allImmediateParentOrg(orgCode,new ArrayList<>());
		if(StringUtil.isNotEmpty(orgCode)&&!"10000".equals(orgCode)){
			map1.put("orgCodes", strings);
		}

		List<YhEventEnrollApp> lisy=yhEventEnrollAppMapper.selecyByEnroll(map1);

		return Result.ok(lisy);
	} 
	
	
	/**
	 * 根据id查询活动详情
	 * @param id
	 * @param userCode
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/enrollByprimary")
	@ResponseBody
	public Result enrollByprimary(Integer id,String userCode) throws ParseException {
		YhEventEnrollApp lisy=yhEventEnrollAppMapper.selectByPrimaryKey(id);
		if(lisy.getStartDate()==null) {
			lisy.setStatus(1);
		}else{
			Date date=new Date();
			int calendar=belongCalendar(date, lisy.getStartDate());//当前时间是否大于开始时间
			if(calendar==2) {
				lisy.setStatus(2);
			}
			
			int calendar1=belongCalendar(date, lisy.getRegisterEndDate());//当前时间是否大于结束时间
			if(calendar1==1) {
				lisy.setStatus(3);
			}
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userCode", userCode);
		map.put("eventId", id);
		int ma=yhEventRegisterAppMapper.showRegisterCount(map);
		if(ma==1) {
			lisy.setStatus(4);
		}
		
		return Result.ok(lisy);
	} 

	/**
	* 判断时间是否在时间段内 开始时间大于结束时间返回true
	* @param beginTime
	* @param endTime
	* @return true
	* Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false； 
	* Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false； 
	* 如果业务数据存在相等的时候，而且相等时也需要做相应的业务判断或处理时，请注意。 
	*/
	public static int belongCalendar(Date beginTime, Date endTime) {

	Calendar begin = Calendar.getInstance();
	begin.setTime(beginTime);

	Calendar end = Calendar.getInstance();
	end.setTime(endTime);
	if (begin.after(end)) {
	return 1;//是
	} else {
	return 2;//否
	}
	}
	
	public static void main(String[] args) throws ParseException {
		Date date=new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = "2020-07-27";
		Date date1 = simpleDateFormat.parse(dateStr);
		System.out.println(belongCalendar(date, date1));
	}
}
