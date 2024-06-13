package com.yihe.web.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserFolderWeb;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserFolderWebService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserFolderWeb")
public class YhUserFolderWebController {
	@Autowired
	YhUserFolderWebService yhUserFolderWebService;
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	
	
	@ApiOperation(value = "分页查询文件夹")
    @ResponseBody
    @RequestMapping(value="/FolderPage",method=RequestMethod.GET)
    public Map<String,Object> queryYhUserFolderWeb(String userCode,int page,int limit){
       //查询集合
       PageInfo<YhUserFolderWeb> data = yhUserFolderWebService.queryUserByCriteriaWithPage(userCode, page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
	
	/**
	 * 添加文件夹
	 * @param yhUserFolder
	 * @return
	 */
	@RequestMapping(value = "/addYhUserFolder")
	@ResponseBody
	public Result addYhUserFolder(YhUserFolderWeb yhUserFolderWeb) {
		int row = 0;
		yhUserFolderWeb.setFolderCreationDate(new Date());
		if(yhUserFolderWeb.getFolderId()==null || "".equals(yhUserFolderWeb.getFolderId())) {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserFolderWeb.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			row = yhUserFolderWebService.insertSelective(yhUserFolderWeb);
		}else {
			YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
			yhUserEnrolinfo.setEnergyUptime(new Date());
			yhUserEnrolinfo.setUserCode(yhUserFolderWeb.getUserCode());
			yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			row = yhUserFolderWebService.updateByPrimaryKeySelective(yhUserFolderWeb);
		}
		
		return Result.ok(row);
		
	}
	
	@RequestMapping(value = "/selectFolderName")
	@ResponseBody
	public Result selectFolderName(String userCode, String folderName) {
		YhUserFolderWeb  name = yhUserFolderWebService.selectFolderName(userCode, folderName);
		return Result.ok(name);
		
	}
	
	@RequestMapping(value = "/selectFolderNameByparam")
	@ResponseBody
	public Result selectFolderNameByparam(Long folderId) {
		YhUserFolderWeb  name = yhUserFolderWebService.selectByPrimaryKey(folderId);
		return Result.ok(name);
		
	}
	
	@RequestMapping(value = "/deleteFolder")
	@ResponseBody
	public Result deleteFolder(Long folderId) {
		 int deleteByPrimaryKey2 = yhUserFolderWebService.deleteByPrimaryKey(folderId);
		return Result.ok(deleteByPrimaryKey2);
		
	}

}
