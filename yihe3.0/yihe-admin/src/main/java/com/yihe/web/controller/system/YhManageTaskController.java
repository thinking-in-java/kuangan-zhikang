package com.yihe.web.controller.system;

import com.yihe.system.domain.YhManageTotalTask;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhServicePage;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhManageTaskService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Controller
@RequestMapping("/yhmanagetaskcontroller")
@Api(value = "YhManageTaskController控制器", description = "YhManageTaskController管理")
public class YhManageTaskController {
		@Autowired
		YhManageTaskService yhmanagetaskservice;
		
		
		
		
		

		@ApiOperation(value = "全选关闭服务")
	    @RequestMapping(value = "/closeUpdatestate", method = RequestMethod.POST)
		@ResponseBody
		public Result closeUpdatestate(String[] serviceid,String acceptsituation) {
			System.out.println(acceptsituation);
			return Result.ok(yhmanagetaskservice.closeUpdatestate(serviceid,acceptsituation));
		}
		
		@ApiOperation(value = "查询等待服务的人")
	    @RequestMapping(value = "/selectcount", method = RequestMethod.POST)
		@ResponseBody
		public Result selectcount(YhServicePage Yhservicepage) {
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
			Yhservicepage.setUsercode(reginfo.getUserCode());
			if(hasChineseByRange(Yhservicepage.getTypeson())){
				Yhservicepage.setPlanname(Yhservicepage.getTypeson());
				Yhservicepage.setTypeson(null);
			}
			List<YhManageTotalTask> list=yhmanagetaskservice.selectlist(Yhservicepage);
			return Result.ok(list);
		}


	@ApiOperation(value = "查询等待服务的人")
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST)
	@ResponseBody
	public Result selectAll(YhServicePage Yhservicepage) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
		Yhservicepage.setUsercode(reginfo.getUserCode());
		if(hasChineseByRange(Yhservicepage.getTypeson())){
			Yhservicepage.setPlanname(Yhservicepage.getTypeson());
			Yhservicepage.setTypeson(null);
		}
		List<Integer> list=yhmanagetaskservice.selectAll(Yhservicepage);
		return Result.ok(list);
	}


		
		@ApiOperation(value = "分页查询个数")
	    @RequestMapping(value = "/selectpagecount", method = RequestMethod.POST)
		@ResponseBody
		public Result selectpagecount(YhServicePage Yhservicepage) {
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
			if(hasChineseByRange(Yhservicepage.getTypeson())){
				Yhservicepage.setPlanname(Yhservicepage.getTypeson());
				Yhservicepage.setTypeson(null);
			}
			Yhservicepage.setUsercode(reginfo.getUserCode());
			return Result.ok(yhmanagetaskservice.selectcountpage(Yhservicepage));
		}
		
		@ApiOperation(value = "查询各类服务的个数")
	    @RequestMapping(value = "/selectservicecount", method = RequestMethod.POST)
		@ResponseBody
		public Result selectservicecount(YhServicePage Yhservicepage) {
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
			if(hasChineseByRange(Yhservicepage.getTypeson())){
				Yhservicepage.setPlanname(Yhservicepage.getTypeson());
				Yhservicepage.setTypeson(null);
			}
			Yhservicepage.setUsercode(reginfo.getUserCode());
			YhManageTotalTask totalTask=yhmanagetaskservice.selectservicecount(Yhservicepage);
			return Result.ok(totalTask);
		}
		
		
		
		@ApiOperation(value = "查询服务的详情")
	    @RequestMapping(value = "/selectdetalserviceplan", method = RequestMethod.POST)
		@ResponseBody
		public Result selectdetalserviceplan(YhServicePage Yhservicepage) {
			return Result.ok(yhmanagetaskservice.selectditailbyid(Yhservicepage));
		}
		
		@ApiOperation(value = "查询服务医生的详情")
	    @RequestMapping(value = "/selectdetaldoctor", method = RequestMethod.POST)
		@ResponseBody
		public Result selectdetaldoctor(YhServicePage Yhservicepage) {
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
			Yhservicepage.setUsercode(reginfo.getUserCode());
			return Result.ok(yhmanagetaskservice.selectdoctorname(Yhservicepage));
		}
		
		
		@ApiOperation(value = "修改状态")
	    @RequestMapping(value = "/updatestate", method = RequestMethod.POST)
		@ResponseBody
		public Result updatestate(YhServicePage Yhservicepage) {
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
			Yhservicepage.setUsercode(reginfo.getUserCode());
			System.out.println(reginfo.getUserCode());
			return Result.ok(yhmanagetaskservice.updatestate(Yhservicepage));
		}
		
		
		@ApiOperation(value = "查询未读的信息")
	    @RequestMapping(value = "/selectunread", method = RequestMethod.POST)
		@ResponseBody
		public Result selectunread(YhServicePage Yhservicepage) {
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
			Yhservicepage.setUsercode(reginfo.getUserCode());
			return Result.ok(yhmanagetaskservice.selectunread(Yhservicepage));
		}
		
		
		
		
		@ApiOperation(value = "修改成已读的信息")
	    @RequestMapping(value = "/updateisrede", method = RequestMethod.POST)
		@ResponseBody
		public Result updateisrede(YhServicePage Yhservicepage) {
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
			Yhservicepage.setUsercode(reginfo.getUserCode());
			return Result.ok(yhmanagetaskservice.updateisrede(Yhservicepage));
		}

	public static boolean hasChineseByRange(String str) {
		         if (str == null) {
		             return false;
		       }
		        char[] ch = str.toCharArray();
		        for (char c : ch) {
			            if (c >= 0x4E00 && c <= 0x9FBF) {
			                return true;
				    }
		        }
		         return false;
		     }

}
