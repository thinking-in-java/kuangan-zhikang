package com.yihe.web.controller.system;

import com.yihe.system.domain.YhHeThreshold;
import com.yihe.system.domain.YhHeThreshold2Web;
import com.yihe.system.domain.YhUserDevice;
import com.yihe.system.service.IYhUserDeviceService;
import com.yihe.system.service.YhHeThreshold2WebService;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 阳雄
 * @Date 2020年9月23日15:37:35
 */

@Controller
@RequestMapping("/YhHeThreshold2Web")
public class YhHeThreshold2WebController {
	@Autowired
	YhHeThreshold2WebService yhHeThreshold2WebService;
	@Autowired 
	YhHeThresholdService yhHeThresholdService;
	@Autowired
	IYhUserDeviceService yhUserDeviceService;
	
	@RequestMapping(value = "/selectUserAll", method = RequestMethod.POST)
	@ResponseBody
	public List<YhUserDevice> selectUserAll(String userCode) {
		List<YhUserDevice> list = yhUserDeviceService.selectUserAll(userCode);
		return list;
	}
	
	@RequestMapping(value = "/deleteData", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteData(Integer tid) {
		int deleteYhUserDevice = yhUserDeviceService.deleteYhUserDevice(tid);
		return Result.ok(deleteYhUserDevice);
	}
	
	@RequestMapping(value = "/selectTerminalId", method = RequestMethod.GET)
	@ResponseBody
	public Result selectTerminalId(String terminalId) {
	YhUserDevice device = yhUserDeviceService.selectTerminalId(terminalId);
		return Result.ok(device);
	}
	
	@RequestMapping(value = "/addYhUserDevice", method = RequestMethod.GET)
	@ResponseBody
	public Result addYhUserDevice(YhUserDevice yhUserDevice) {
		int row =0;
		Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    //获取当前时间String类型
	    String time_string = sdf.format(date);
		if(yhUserDevice.getTid()==null || "".equals(yhUserDevice.getTid())) {
			yhUserDevice.setBindingtime(time_string);
			row = yhUserDeviceService.insertYhUserDevice(yhUserDevice);
			Integer tid = yhUserDevice.getTid();
			return Result.ok(tid);
		}else {
			row = yhUserDeviceService.updateYhUserDevice(yhUserDevice);
		}
		
		return Result.ok(row);
	}
	
	private boolean IsEmpty(int tid) {
		// TODO Auto-generated method stub
		return false;
	}


	@RequestMapping(value = "/addYhHeThreshold2Web", method = RequestMethod.POST)
	@ResponseBody
	public Result addYhHeThreshold2Web(YhHeThreshold2Web record) {
		int row =0;
		if(null==record|| StringUtil.isEmpty(record.getUserCode())){
			return Result.ok(row);
		}
		if(record.getId()==null || "".equals(record.getId())) {
			YhHeThreshold2Web threshold2 = yhHeThreshold2WebService.selectUserYhHeThreshold2(record.getUserCode());
			if(null!=threshold2){
				record.setId(threshold2.getId());
				row = yhHeThreshold2WebService.updateByPrimaryKeySelective(record);
			}else{
				row = yhHeThreshold2WebService.insertSelective(record);
			}
		}else {
			row = yhHeThreshold2WebService.updateByPrimaryKeySelective(record);
		}
		
		return Result.ok(row);
	}
	@RequestMapping(value = "/selectYhHeThreshold2Web", method = RequestMethod.POST)
	@ResponseBody
	public Result selectYhHeThreshold2Web(String  userCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<YhHeThreshold> list = yhHeThresholdService.selectallheath();
		YhHeThreshold2Web threshold2 = yhHeThreshold2WebService.selectUserYhHeThreshold2(userCode);
		map.put("list", list);
		map.put("threshold2", threshold2);
		return Result.ok(map);
	}
	
	@RequestMapping(value = "/deleteYhHeThreshold2Web", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteYhHeThreshold2Web(String userCode) {
		int deleteByPrimaryKey = yhHeThreshold2WebService.deleteByPrimaryKey(userCode);
		return Result.ok(deleteByPrimaryKey);
	}
	
}
