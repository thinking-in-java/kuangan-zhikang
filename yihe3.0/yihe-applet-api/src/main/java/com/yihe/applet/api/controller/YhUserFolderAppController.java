package com.yihe.applet.api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserFolderApp;
import com.yihe.applet.api.service.YhUserFolderAppService;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;

/**
 * 
 * @author yx
 *	@time  2020年7月23日09:16:39
 */
@Controller
@RequestMapping("/api/YhUserFolderApp")
@Api(value = "YhUserFolderApp控制器", description = "YhUserFolderApp文件夹")
public class YhUserFolderAppController {
	@Autowired
	YhUserFolderAppService yhUserFolderAppService;
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	/**
	 * 添加文件夹
	 * @param yhUserFolderApp
	 * @return
	 */
	@RequestMapping(value = "/addYhUserFolder")
	@ResponseBody
	public Result addYhUserFolder(YhUserFolderApp yhUserFolderApp) {
		int row = 0;
		YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
		yhUserEnrolinfo.setEnergyUptime(new Date());
		yhUserEnrolinfo.setUserCode(yhUserFolderApp.getUserCode());
		yhUserFolderApp.setFolderCreationDate(new Date());
		if(yhUserFolderApp.getFolderId()==null || "".equals(yhUserFolderApp.getFolderId())) {
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			row = yhUserFolderAppService.insertSelective(yhUserFolderApp);
		}else {
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			row = yhUserFolderAppService.updateByPrimaryKeySelective(yhUserFolderApp);
		}
		
		return Result.ok(row);
		
	}
	
	/**
	 * 删除文件夹
	 * @param yhUserFolderApp
	 * @return
	 */
	@RequestMapping(value = "/deleteFolder")
	@ResponseBody
	public Result deleteYhUserFolder(Long folderId) {
	 int deleteByPrimaryKey = yhUserFolderAppService.deleteByPrimaryKey(folderId);
		return Result.ok(deleteByPrimaryKey);
		
	}
	 
	/**
	 * 查询所有文件夹
	 * @param String userCode, Integer page, Integer pageSize
	 * @return
	 */
	@RequestMapping(value = "/selectAllFolder")
	@ResponseBody
	public Result selectAllFolder(String userCode, Integer page, Integer pageSize) {
		List<YhUserFolderApp> list = yhUserFolderAppService.selectYhUserFolderLimitParent(userCode, (page-1)*pageSize, pageSize);
		/*
		 * for (int i = 0; i < list.size(); i++) { String fileName =
		 * list.get(i).getFolderName(); if (null != fileName) { if (fileName.length() >
		 * 20) { list.get(i).setFolderName(fileName.substring(0, 9) + "..."); } } }
		 */
		return Result.ok(list);
		
	}
	
	/** 
	 * 查询所有文件夹
	 * @param String userCode, Integer page, Integer pageSize
	 * @return
	 */
	@RequestMapping(value = "/selectYhUserFolderByparam")
	@ResponseBody
	public Result selectYhUserFolderByparam(String folderName,String userCode, Integer page, Integer pageSize) {
		List<YhUserFolderApp> list = yhUserFolderAppService.selectYhUserFolderByparam(userCode, folderName, (page-1)*pageSize, pageSize);
		System.out.println(list);
		return Result.ok(list);
		
	}
	/**
	 * 修改文件夹名
	 * @param yhUserFolderApp
	 * @return
	 */
	@RequestMapping(value = "/updateName")
	@ResponseBody
	public Result updateName(YhUserFolderApp yhUserFolderApp) {
		
		int insertSelective = yhUserFolderAppService.updateByPrimaryKeySelective(yhUserFolderApp);
		return Result.ok(insertSelective);
		
	}

}
