package com.yihe.web.controller.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yihe.common.annotation.Log;
import com.yihe.common.enums.BusinessType;
import com.yihe.system.domain.EquipmentInfoVOJO;
import com.yihe.system.domain.YhDicAssessmentScreen;
import com.yihe.system.domain.YhDiseaseAssessReportVOJO;
import com.yihe.system.domain.YhUserDevice;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.IYhUserDeviceService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import com.yihe.common.core.controller.BaseController;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.common.utils.poi.ExcelUtil;
import com.yihe.common.core.page.TableDataInfo;

/**
 * 鐢ㄦ埛缁戝畾璁惧Controller
 * 
 * @author yihe
 * @date 2020-04-20
 */
@Controller
@RequestMapping("/system/device")
public class YhUserDeviceController extends BaseController {
	private String prefix = "system/device";

	@Autowired
	private IYhUserDeviceService yhUserDeviceService;

	@RequestMapping(value = "/addDiaryInfo", method = RequestMethod.GET)
	public String addDiaryInfo(Model model) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo username = (YhUserReginfo) subject.getPrincipal();
		String usercode = username.getUserCode();
		List<EquipmentInfoVOJO> findregInfoBYUsercodeForName = yhUserDeviceService
				.findregInfoBYUsercodeForName(usercode);
		model.addAttribute("namelist", findregInfoBYUsercodeForName);
		return "devicesSettingUp/addDevice";
	}

	@RequestMapping(value = "/addInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result addInfo(@RequestBody YhUserDevice yhUserDevice) {

		// 鏌ヨ鐢ㄦ埛淇℃伅
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 璁剧疆鏃ユ湡鏍煎紡
		yhUserDevice.setBindingTime(df.format(new Date()));
		String selectNewDeviceUserCode = yhUserDeviceService.selectNewDeviceUserCode(yhUserDevice.getTerminalid());
		if (StringUtil.isNotEmpty(selectNewDeviceUserCode)) {
			return Result.error();
		}
		yhUserDeviceService.insertYhUserDevice(yhUserDevice);
		return Result.ok();
	}

	@RequestMapping(value = "/editInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result editInfo(@RequestBody YhUserDevice yhUserDevice) {
		String selectNewDeviceUserCode = yhUserDeviceService.selectNewDeviceUserCode(yhUserDevice.getTerminalid());
		if (StringUtil.isNotEmpty(selectNewDeviceUserCode)) {
			return Result.error();
		}
		yhUserDeviceService.updateYhUserDevice(yhUserDevice);

		return Result.ok();
	}

	@RequestMapping(value = "/delInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result delInfo(String ids) {

		return Result.ok(yhUserDeviceService.deleteYhUserDeviceByIds(ids));
	}

	@RequestMapping(value = "/findByTid", method = RequestMethod.GET)
	public String findByTid(int tid, Model model) {
		YhUserDevice yhUserDevice = yhUserDeviceService.selectYhUserDeviceById(tid);
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo username = (YhUserReginfo) subject.getPrincipal();
		String usercode = username.getUserCode();
		List<EquipmentInfoVOJO> findregInfoBYUsercodeForName = yhUserDeviceService
				.findregInfoBYUsercodeForName(usercode);
		model.addAttribute("namelist", findregInfoBYUsercodeForName);
		model.addAttribute("yhUserDevice", yhUserDevice);
		return "devicesSettingUp/editDevice";
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("aa");
		list.add("aa");
		list.add("aa");
		list.add("bb");
		list.add("bb");
		list.add("cc");
		System.out.println(getDuplicateElements(list).toString());
	}

	public static List<String> getDuplicateElements(List<String> list) {
		return list.stream().collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b)).entrySet().stream()
				.filter(entry -> entry.getValue() > 1).map(entry -> entry.getKey()).collect(Collectors.toList());
	}

	@RequestMapping(value = "/yhUserDevicepage", method = RequestMethod.GET)
	@ResponseBody
	public Result queryUserYhUserDevicePage(@RequestParam(required = false) String name,
			@RequestParam(required = false) String terminalid,int page, int limit) {

		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		PageInfo<YhUserDevice> data = yhUserDeviceService.queryUserYhUserDevicePage(yhUserReginfo.getUserCode(), page,
				limit,name,terminalid);
		// 定义一个List接收每个用户的list；
		List<YhUserDevice> list2 = new ArrayList<YhUserDevice>();
		List<YhUserDevice> list = data.getList();

		for (int i = 0; i < list.size(); i++) {
			boolean falg = false;
			int result = -1;
			for (int j = 0; j < list2.size(); j++) {
				System.out.println(list.get(i).getUsercode().equals(list2.get(j).getUsercode()));
				if (list.get(i).getUsercode().equals(list2.get(j).getUsercode())) {
					falg = true;
					result = j;
				}
			}
			if (falg) {
				if (result != -1) {
					if(list.get(i).getTerminaltype()!=null) {
						if (list.get(i).getTerminaltype().equals("1")) {
							list2.get(result).setXueya(list.get(i).getTerminalid() + ";" + list2.get(result).getXueya());
						} else if (list.get(i).getTerminaltype().equals("2")) {
							list2.get(result).setXuetan(list.get(i).getTerminalid() + ";" + list2.get(result).getXuetan());
						} else if (list.get(i).getTerminaltype().equals("3")) {
							list2.get(result).setXueyaxuetan(list.get(i).getTerminalid() + ";" + list2.get(result).getXueyaxuetan());
						}
					}
					

				}
			} else {
				System.out.println(list.get(i).getTerminaltype());
				if(list.get(i).getTerminaltype()!=null) {
					if (list.get(i).getTerminaltype().equals("1")) {
						list.get(i).setXueya(list.get(i).getTerminalid());
					}
					if (list.get(i).getTerminaltype().equals("2")) {
						list.get(i).setXuetan(list.get(i).getTerminalid());
					}
					if (list.get(i).getTerminaltype().equals("3")) {
						list.get(i).setXueyaxuetan(list.get(i).getTerminalid());
					}
				}
					
				
					
					list2.add(list.get(i));
				
			}

		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", list2);
		result.put("count", data.getTotal());
		result.put("msg", "");
		result.put("code", 0);
		return Result.ok(result);
	}

	@RequestMapping(value = "/findDeviceList", method = RequestMethod.GET)
	@ResponseBody
	public Result findDeviceList(@RequestParam(required = false) String nameLike,
			@RequestParam(required = false) String tid, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {
		System.out.println("nameLike---" + nameLike);
		System.out.println("page---" + page);
		System.out.println("tid---" + tid);
		System.out.println("limit---" + limit);
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo upmsUser = (YhUserReginfo) subject.getPrincipal();

		if (null == upmsUser) {
			return Result.error();
		}
		String usercode = upmsUser.getUserCode();

		PageInfo<EquipmentInfoVOJO> listpage = yhUserDeviceService.findTerMinalByDoctorAndNameAndTid(usercode, nameLike,
				tid, page, limit);
		List<EquipmentInfoVOJO> list = listpage.getList();
		// 定义一个List接收每个用户的list；
		List<Object> list2 = new ArrayList<Object>();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String userCode = list.get(i).getUsercode();
				List<EquipmentInfoVOJO> type = yhUserDeviceService.selectFullOrgName(userCode);
				list2.add(type);
			}
		}

		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", listpage.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list2);
		return Result.ok(rsMap);

	}

	@RequiresPermissions("system:device:view")
	@GetMapping()
	public String device() {
		return prefix + "/device";
	}

	/**
	 * 鏌ヨ鐢ㄦ埛缁戝畾璁惧鍒楄〃
	 */
	@RequiresPermissions("system:device:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(YhUserDevice yhUserDevice) {
		startPage();
		List<YhUserDevice> list = yhUserDeviceService.selectYhUserDeviceList(yhUserDevice);
		return getDataTable(list);
	}

	/**
	 * 瀵煎嚭鐢ㄦ埛缁戝畾璁惧鍒楄〃
	 */
	@RequiresPermissions("system:device:export")
	@Log(title = "鐢ㄦ埛缁戝畾璁惧", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(YhUserDevice yhUserDevice) {
		List<YhUserDevice> list = yhUserDeviceService.selectYhUserDeviceList(yhUserDevice);
		ExcelUtil<YhUserDevice> util = new ExcelUtil<YhUserDevice>(YhUserDevice.class);
		return util.exportExcel(list, "device");
	}

	/**
	 * 鏂板鐢ㄦ埛缁戝畾璁惧
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 鏂板淇濆瓨鐢ㄦ埛缁戝畾璁惧
	 */
	@RequiresPermissions("system:device:add")
	@Log(title = "鐢ㄦ埛缁戝畾璁惧", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(YhUserDevice yhUserDevice) {
		return toAjax(yhUserDeviceService.insertYhUserDevice(yhUserDevice));
	}

	/**
	 * 淇敼鐢ㄦ埛缁戝畾璁惧
	 */
	@GetMapping("/edit/{terminalid}")
	public String edit(@PathVariable("tid") int tid, ModelMap mmap) {
		YhUserDevice yhUserDevice = yhUserDeviceService.selectYhUserDeviceById(tid);
		mmap.put("yhUserDevice", yhUserDevice);
		return prefix + "/edit";
	}

	/**
	 * 淇敼淇濆瓨鐢ㄦ埛缁戝畾璁惧
	 */
	@RequiresPermissions("system:device:edit")
	@Log(title = "鐢ㄦ埛缁戝畾璁惧", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(YhUserDevice yhUserDevice) {
		return toAjax(yhUserDeviceService.updateYhUserDevice(yhUserDevice));
	}

	/**
	 * 鍒犻櫎鐢ㄦ埛缁戝畾璁惧
	 */
	@RequiresPermissions("system:device:remove")
	@Log(title = "鐢ㄦ埛缁戝畾璁惧", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(yhUserDeviceService.deleteYhUserDeviceByIds(ids));
	}
}
