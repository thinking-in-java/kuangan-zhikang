/**
 * 
 */
package com.yihe.web.controller.system;

import com.yihe.system.domain.YhEventEnroll;
import com.yihe.system.domain.YhHealthHeadline;
import com.yihe.system.domain.YhHealthLectureInfo;
import com.yihe.system.domain.YhHomeinfoConfig;
import com.yihe.system.service.YhEventEnrollService;
import com.yihe.system.service.YhHealthHeadlineService;
import com.yihe.system.service.YhHealthLectureInfoService;
import com.yihe.system.service.YhHomeinfoConfigService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhHomeinfoConfigControoler")
@Api(value = "YhHomeinfoConfigControoler控制器", description = "YhHomeinfoConfigControoler管理")
public class YhHomeinfoConfigControoler {

	@Autowired
	YhHomeinfoConfigService yhHomeinfoConfigService;
	
	 @Autowired
	 private YhHealthLectureInfoService yhHealthLectureInfoService;
	 
	   @Autowired
		 private YhEventEnrollService yhEventEnrollService;
	   
	   @Autowired
		 private YhHealthHeadlineService yhHealthHeadlineService;
	 
	   
	   
	   @ApiOperation(value = "修改上移与下移")
	    @RequestMapping(value = "/amendinfotip", method = RequestMethod.POST)
		@ResponseBody
	    public Result  amendinfotip(int id,int infotip,int type) {
		   int serv=0;
		   if(type==1) {
			   YhHomeinfoConfig list= yhHomeinfoConfigService.amendinfotip(infotip+1);
			   list.setInfoTip(list.getInfoTip()-1);
			   yhHomeinfoConfigService.updateByPrimaryKeySelective(list);
			   
			   YhHomeinfoConfig config=new YhHomeinfoConfig();
			   config.setId(id);
			   config.setInfoTip(infotip+1);
			   serv=yhHomeinfoConfigService.updateByPrimaryKeySelective(config);
		   }else {
			   YhHomeinfoConfig list= yhHomeinfoConfigService.amendinfotip(infotip-1);
			   list.setInfoTip(list.getInfoTip()+1);
			   yhHomeinfoConfigService.updateByPrimaryKeySelective(list);
			   
			   YhHomeinfoConfig config=new YhHomeinfoConfig();
			   config.setId(id);
			   config.setInfoTip(infotip-1);
			   serv=yhHomeinfoConfigService.updateByPrimaryKeySelective(config);
		   }
		  
	        return Result.ok(serv);
	    }
	   
	   
	   
	   @ApiOperation(value = "增加/修改头条")
	    @RequestMapping(value = "/insertHealthHeadline", method = RequestMethod.POST)
		@ResponseBody
	    public Result  insertHealthHeadline(YhHomeinfoConfig config) {
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式  
		  config.setInfoTime(df.format(new Date()));
		  YhHealthHeadline linte=yhHealthHeadlineService.selectByPrimaryKey(config.getInfoContent());
		  config.setInfoImg(linte.getHeImg());
		  config.setInfoTitle(linte.getHeTitle());
		  config.setInfoStatus(1);
		  int i;
		  if(config.getId()==0 || config.getId()==null) {
			   i=yhHomeinfoConfigService.insertSelective(config);
		  }else {
			  i=yhHomeinfoConfigService.updateByPrimaryKeySelective(config);
		  }
	        return Result.ok(i);
	    }
	   
	   @ApiOperation(value = "查找健康头条所有")
	    @RequestMapping(value = "/showheadline", method = RequestMethod.POST)
		@ResponseBody
	    public Result  showheadline() {
	    	List<YhHealthHeadline> list=yhHealthHeadlineService.selectByExample();
	        return Result.ok(list);
	    }
	   
	  @ApiOperation(value = "查找健康课堂所有")
	    @RequestMapping(value = "/selectByExample", method = RequestMethod.POST)
		@ResponseBody
	    public Result  selectByslidehow() {
	    	List<YhHealthLectureInfo> list=yhHealthLectureInfoService.selectByExample();
	        return Result.ok(list);
	    }
	  
	  @ApiOperation(value = "查找精彩活动所有")
	    @RequestMapping(value = "/selectByEnrooll", method = RequestMethod.POST)
		@ResponseBody
	    public Result  selectByEnrooll() {
	    	List<YhEventEnroll> list=yhEventEnrollService.selectByExample();
	        return Result.ok(list);
	    }
	  
	  @ApiOperation(value = "删除内容")
	    @RequestMapping(value = "/deleteEnrooll", method = RequestMethod.POST)
		@ResponseBody
	    public Result  deleteByPrimaryKey(int id) {
	    	int  i=yhHomeinfoConfigService.deleteByPrimaryKey(id);
	    	//删除时候修改
	    	List<YhHomeinfoConfig> list=yhHomeinfoConfigService.selectByOrder();
	    	int e=0;
	    	for (YhHomeinfoConfig yhHomeinfoConfig : list) {
				e++;
				yhHomeinfoConfig.setInfoTip(e);
				yhHomeinfoConfigService.updateByPrimaryKeySelective(yhHomeinfoConfig);
			}
	    	
	        return Result.ok(i);
	    }
	  
	  @ApiOperation(value = "增加/修改轮播图")
	    @RequestMapping(value = "/insertSelective", method = RequestMethod.POST)
		@ResponseBody
	    public Result  insertSelective(YhHomeinfoConfig config) {
		  
		  String infotip=yhHomeinfoConfigService.InfoTipMax();
		  if(infotip==null || infotip.equals("")) {
			  infotip="0";
		  }
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式  
		  config.setInfoTime(df.format(new Date()));
		  if(config.getInfoSource()==1) {
			YhHealthLectureInfo info=yhHealthLectureInfoService.selectByPrimaryKey(config.getInfoContent());
			config.setInfoTitle(info.getTitle());
		  }else if(config.getInfoSource()==2) {
			  YhEventEnroll i=yhEventEnrollService.selectByPrimaryKey(config.getInfoContent());
			  config.setInfoTitle(i.getEventName());
		  }
		  int i;
		  if(config.getId()==0 || config.getId()==null) {
			  //增加时候修改
		    	List<YhHomeinfoConfig> list=yhHomeinfoConfigService.selectByOrder();
		    	int e=1;
		    	for (YhHomeinfoConfig yhHomeinfoConfig : list) {
					e++;
					yhHomeinfoConfig.setInfoTip(e);
					yhHomeinfoConfigService.updateByPrimaryKeySelective(yhHomeinfoConfig);
				}
		    	
			  config.setInfoTip(1);
			  config.setInfoStatus(1);
			   i=yhHomeinfoConfigService.insertSelective(config);
		  }else {
		  	  if(config.getInfoSource()==3 && config.getInfoContent() == null){
				  config.setInfoTitle("0");
			  }
			  if(config.getInfoSource()==0) {
				  config.setInfoTitle("0");
			  }
			  i=yhHomeinfoConfigService.updateByPrimaryKeySelective(config);
		  }
	        return Result.ok(i);
	    }
	
	
	  @ApiOperation(value = "查找轮播图")
	    @RequestMapping(value = "/selectByslideshow", method = RequestMethod.POST)
		@ResponseBody
	    public Result  selectByslideshow(Model model) {
	    	List<YhHomeinfoConfig> list=yhHomeinfoConfigService.selectByslideshow();
	    	Map<String,Object> rsMap = new HashMap<String,Object>();
	        rsMap.put("code", 20000);
	        rsMap.put("msg", "ok");
	        rsMap.put("data", list);
	        return Result.ok(rsMap);
	    }
	  
	  @ApiOperation(value = "查找健康头条")
	    @RequestMapping(value = "/selectByheadline", method = RequestMethod.POST)
		@ResponseBody
	    public Result  selectByheadline(Model model) {
	    	List<YhHomeinfoConfig> list=yhHomeinfoConfigService.selectByheadline();
	    	int number=yhHomeinfoConfigService.selectByheadlinenumber();
	    	model.addAttribute("number", number);
	    	Map<String,Object> rsMap = new HashMap<String,Object>();
	        rsMap.put("code", 20000);
	        rsMap.put("msg", "ok");
	        rsMap.put("data", list);
	        return Result.ok(rsMap);
	    }
}
