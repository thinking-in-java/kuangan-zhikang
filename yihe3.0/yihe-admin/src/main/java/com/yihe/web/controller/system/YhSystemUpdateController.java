package com.yihe.web.controller.system;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhSystemUpdate;
import com.yihe.system.service.YhSystemUpdateService;
import com.yihe.system.utils.Result;



@Controller
@RequestMapping("/YhSystemUpdateController")
public class YhSystemUpdateController {
	@Autowired
	YhSystemUpdateService yhsysdateservice ;
	
	//查询当前时间的草稿，以及发布时间未到的内容
	@RequestMapping("/selectsystemupdate")
	@ResponseBody
	public Result selectsystemupdate(){
			YhSystemUpdate yhsystemupdate=yhsysdateservice.selectsystemupdate();
			return Result.ok(yhsystemupdate);

		
	}
	
		//修改或添加草稿
		@RequestMapping("/updatedate")
		@ResponseBody
		public Result updatedate(YhSystemUpdate yhsystemupdate){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			yhsystemupdate.setBrgin(sdf.format(yhsystemupdate.getUpdate_begintime()));
			yhsystemupdate.setEnd(sdf.format(yhsystemupdate.getUpdate_endtime()));
			if(yhsysdateservice.updatedate(yhsystemupdate)!=0) {
				return Result.ok();
			}else {
				return Result.error();
			}
			
		}
		
		//撤销删除草稿
				@RequestMapping("/updatechexiao")
				@ResponseBody
				public Result updatechexiao(YhSystemUpdate yhsystemupdate){
					
					return Result.ok(yhsysdateservice.updatechexiao(yhsystemupdate));
			}
		//发布
		@RequestMapping("/formSubmitBasfabu")
		@ResponseBody
			public Result formSubmitBasfabu(YhSystemUpdate yhsystemupdate){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			yhsystemupdate.setBrgin(sdf.format(yhsystemupdate.getUpdate_begintime()));
			yhsystemupdate.setEnd(sdf.format(yhsystemupdate.getUpdate_endtime()));
					return Result.ok(yhsysdateservice.updatefabu(yhsystemupdate));
			}
}
