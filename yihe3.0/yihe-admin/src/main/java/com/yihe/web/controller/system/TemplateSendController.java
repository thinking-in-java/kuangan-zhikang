/**
 * 
 */
package com.yihe.web.controller.system;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.*;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 模板短信发送
 * @author dong
 *
 */
@Controller
@RequestMapping("/TemplateSendController")
@Api(value = "TemplateSendController控制器", description = "TemplateSendController管理")
public class TemplateSendController {

	
	@Autowired
	 private YhJzSysSmsMapper yhJzSysSmsMapper;
	
	@Autowired
	 private YhJzSysSmsTextMapper yhJzSysSmsTextMapper;
	
	@Autowired
	 private YhManageTaskMapper yhManageTaskMapper;
	
	@Autowired
	YhManageTaskMapper Yhmanagetaskmapper;
	
	
	@Autowired
	YhWxNotifyMapper yhWxNotifyMapper;
	
	@Autowired
	YhUserNotifyMapper yhUserNotifyMapper;
	
	@Autowired
	YhTemplateMessageMapper yhTemplateMessageMapper;
	
	@Autowired
	YhTemplateTypeMapper yhTemplateTypeMapper;
	
	@Autowired
	YhWxInformationMapper yhWxInformationMapper;	
	
	@ApiOperation(value = "查询微信消息模板全部Type")
	   @RequestMapping(value = "/selectByMessage", method = RequestMethod.POST)
		@ResponseBody
	   public Result  selectByMessage() { 
		 List<YhTemplateMessage> book=yhTemplateMessageMapper.selectByMessage();
	       return Result.ok(book);
	   }
	
	
	
	
	@ApiOperation(value = "模板查询")
	   @RequestMapping(value = "/showtemplate", method = RequestMethod.POST)
		@ResponseBody
	   public Result  showtemplate() { 
		YhWxInformation tion=yhWxInformationMapper.selectbyWxType("taskGZH");//去公众号的配置
		 YhWxInformation xiaocx=yhWxInformationMapper.selectbyWxType("task");//小程序配置
		 List<YhJzSysSmsWeb> book=yhJzSysSmsMapper.showSysSmsAll();//短信配置
		 TemplateVojo vojo=new TemplateVojo();
		 if(tion==null) {
			 vojo.setGzh(1);
		 }
		 if(xiaocx==null) {
			 vojo.setXxc(1);
		 }
		 if(book.size()<4) {
			 vojo.setDx(1);
		 }
	       return Result.ok(vojo);
	   }
	
	
	@ApiOperation(value = "根据id查询微信消息模板详情内容")
	   @RequestMapping(value = "/selectWxById", method = RequestMethod.POST)
		@ResponseBody
	   public Result  selectWxById(Integer tType) { 
		YhTemplateType text=yhTemplateTypeMapper.selectByPrimaryKey(tType);
	       return Result.ok(text);
	   }
	
	@ApiOperation(value = "根据id查询微信消息模板内容")
	   @RequestMapping(value = "/selecttType", method = RequestMethod.POST)
		@ResponseBody
	   public Result  selecttType(Integer tType) { 
		List<YhTemplateType> text=yhTemplateTypeMapper.showTTypeAll(tType);
	       return Result.ok(text);
	   }
	
	
	
	/**
	 * 上门回访
	 * @param peihechengdu 配合程度
	 * @param acceptsituation 受理情况
	 * @param checked 
	 * @return
	 * @throws ClientException
	 */
	@ApiOperation(value = "上门回访")
	   @RequestMapping(value = "/visitReturn", method = RequestMethod.POST)
		@ResponseBody
	   public Result  visitReturn(int peihechengdu,String acceptsituation,String checked) throws ClientException { 
		String[] array2 = checked.split(",");//分割服务内容id
		YhServicePage Yhservicepage=new YhServicePage();
		int ret=0;
		for(int i=0;i<array2.length;i++){
			Yhservicepage.setId(Integer.valueOf(array2[i]));
			Yhservicepage.setState(3);
			Yhservicepage.setReturnvisit(4);
			Yhservicepage.setAcceptsituation(acceptsituation);
			Yhservicepage.setSatisfaction(peihechengdu);
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
			Yhservicepage.setEndtime(dateFormat.format(date));
			ret=ret+Yhmanagetaskmapper.updatestate(Yhservicepage);
		 }
	       return Result.ok(ret);
	   }
	

	/**
	 * 电话回访
	 * @param peihechengdu 配合程度
	 * @param acceptsituation 受理情况
	 * @param checked 
	 * @return
	 * @throws ClientException
	 */
	@ApiOperation(value = "电话回访")
	   @RequestMapping(value = "/phoneReturn", method = RequestMethod.POST)
		@ResponseBody
	   public Result  phoneReturn(int peihechengdu,String acceptsituation,String checked) throws ClientException { 
		String[] array2 = checked.split(",");//分割服务内容id
		YhServicePage Yhservicepage=new YhServicePage();
		int ret=0;
		for(int i=0;i<array2.length;i++){
			Yhservicepage.setId(Integer.valueOf(array2[i]));
			Yhservicepage.setState(3);
			Yhservicepage.setReturnvisit(1);
			Yhservicepage.setAcceptsituation(acceptsituation);
			Yhservicepage.setSatisfaction(peihechengdu);
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
			Yhservicepage.setEndtime(dateFormat.format(date));
			ret=ret+Yhmanagetaskmapper.updatestate(Yhservicepage);
		 }
	       return Result.ok(ret);
	   }
	
	/**
	 * 发送小程序消息
	 * @param smsname 短信模板id
	 * @param tText 内容
	 * @param checked 服务id
	 * @return
	 * @throws ClientException
	 */
	@ApiOperation(value = "发送小程序消息")
	   @RequestMapping(value = "/xxcmessage", method = RequestMethod.POST)
		@ResponseBody
	   public Result  xxcmessage(String appletText,String checked) throws ClientException { 
		String[] array2 = checked.split(",");//分割服务内容id
		Set<String> set=new HashSet<String>();
		YhServicePage Yhservicepage=new YhServicePage();
		int ret=0;
		for(int i=0;i<array2.length;i++){
			Yhservicepage.setId(Integer.valueOf(array2[i]));
			Yhservicepage.setState(3);
			Yhservicepage.setReturnvisit(3);
			Yhservicepage.setAcceptsituation("小程序站内信回访:"+appletText);
			Yhservicepage.setSatisfaction(2);
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
			Yhservicepage.setEndtime(dateFormat.format(date));
			ret=ret+Yhmanagetaskmapper.updatestate(Yhservicepage);
		 String userCode=yhManageTaskMapper.selectplanCode(Integer.valueOf(array2[i]));//取user_code
		 set.add(userCode);//去重电话号码
		 }
		int finish=0;
		int fail=0;
		
		for (String string : set) {
			yhWxNotify tify=new yhWxNotify();//站内信
			YhUserNotify notify=new YhUserNotify();//通知通告发送记录
			tify.setWxNotifyId(fail);
			tify.setWxNotifyContent(appletText);
			tify.setWxNotifyBackup("服务通知");
			int wxNotifyId=yhWxNotifyMapper.insertyhnotify(tify);
			
			Date date = new Date();
			notify.setTitle("站内信");
			notify.setContent(appletText);
			notify.setUser_id(string);
			notify.setSend_date(date);
			notify.setType(2);
			notify.setTo_service_id("5");
			notify.setNews_id(tify.getWxNotifyId());
			int i=yhUserNotifyMapper.insertnotify(notify);
			if (i==1) {
				finish++;
			} else{
				fail++;

			}
		}
		
		Servefulfill fill=new Servefulfill();
		fill.setFail(fail);
		fill.setFinish(finish);
	       return Result.ok(fill);
	   }


	/**
	 * 发送小程序消息
	 * @param smsname 短信模板id
	 * @param tText 内容
	 * @param checked 服务id
	 * @return
	 * @throws ClientException
	 */
	@ApiOperation(value = "发送小程序消息")
	@RequestMapping(value = "/xxcmessage3", method = RequestMethod.POST)
	@ResponseBody
	public Result  xxcmessage3(String appletText,String checked) throws ClientException {
		String[] array2 = checked.split(",");//分割服务内容id
		Set<String> set=new HashSet<String>();
		YhServicePage Yhservicepage=new YhServicePage();
		int ret=0;
		for(int i=0;i<array2.length;i++){
			Yhservicepage.setId(Integer.valueOf(array2[i]));
			Yhservicepage.setState(3);
			Yhservicepage.setReturnvisit(3);
			Yhservicepage.setAcceptsituation("小程序站内信回访:"+appletText);
			Yhservicepage.setSatisfaction(2);
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
			Yhservicepage.setEndtime(dateFormat.format(date));
			ret=ret+Yhmanagetaskmapper.updatestate(Yhservicepage);
			String userCode=array2[i];//取user_code
			set.add(userCode);//去重电话号码
		}
		int finish=0;
		int fail=0;

		for (String string : set) {
			yhWxNotify tify=new yhWxNotify();//站内信
			YhUserNotify notify=new YhUserNotify();//通知通告发送记录
			tify.setWxNotifyId(fail);
			tify.setWxNotifyContent(appletText);
			tify.setWxNotifyBackup("服务通知");
			int wxNotifyId=yhWxNotifyMapper.insertyhnotify(tify);

			Date date = new Date();
			notify.setTitle("站内信");
			notify.setContent(appletText);
			notify.setUser_id(string);
			notify.setSend_date(date);
			notify.setType(2);
			notify.setTo_service_id("5");
			notify.setNews_id(tify.getWxNotifyId());
			int i=yhUserNotifyMapper.insertnotify(notify);
			if (i==1) {
				finish++;
			} else{
				fail++;

			}
		}

		Servefulfill fill=new Servefulfill();
		fill.setFail(fail);
		fill.setFinish(finish);
		return Result.ok(fill);
	}
	
	
	@ApiOperation(value = "发送站内信")
	   @RequestMapping(value = "/grzxmessage", method = RequestMethod.POST)
		@ResponseBody
	   public Result  grzxmessage(String appletText,String userCode) throws ClientException {
		
		yhWxNotify tify=new yhWxNotify();//站内信
		YhUserNotify notify=new YhUserNotify();//通知通告发送记录
			tify.setWxNotifyContent(appletText);
			YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
			String userName = yhUserReginfo.getUserName();
			tify.setWxNotifyBackup(userName);
			int wxNotifyId=yhWxNotifyMapper.insertyhnotify(tify);
			
			Date date = new Date();
			notify.setTitle("站内信");
			notify.setContent(appletText);
			notify.setUser_id(userCode);
			notify.setSend_date(date);
			notify.setType(2);
			notify.setTo_service_id("5");
			notify.setNews_id(tify.getWxNotifyId());
			int i=yhUserNotifyMapper.insertnotify(notify);
	
	       return Result.ok(i);
	   }
	
	
	/**
	 * 发送短信服务
	 * @param smsname 短信模板id
	 * @param tText 内容
	 * @param checked 服务id
	 * @return
	 * @throws ClientException
	 */
	@ApiOperation(value = "根据id查询短信模板内容")
	   @RequestMapping(value = "/completeNote", method = RequestMethod.POST)
		@ResponseBody
	   public Result  completeNote(long smsname,String tText,String checked) throws ClientException { 
		 YhJzSysSmsWeb book=yhJzSysSmsMapper.selectByPrimaryKey(smsname);//查询出短信模板
		String[] array2 = checked.split(",");//分割服务内容id
		Set<String> set=new HashSet<String>();
		YhServicePage Yhservicepage=new YhServicePage();
		int ret=0;
		int finish=0;
		int fail=0;
		for(int i=0;i<array2.length;i++){
			Yhservicepage.setId(Integer.valueOf(array2[i]));
			Yhservicepage.setState(3);
			Yhservicepage.setReturnvisit(5);
			Yhservicepage.setAcceptsituation("短信回访"+tText);
			Yhservicepage.setSatisfaction(2);
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
			Yhservicepage.setEndtime(dateFormat.format(date));
			ret=ret+Yhmanagetaskmapper.updatestate(Yhservicepage);
		 	String mobile=yhManageTaskMapper.selectplanusercodeid(Integer.valueOf(array2[i]));//取电话号码
			set.add(mobile);//去重电话号码
		 }
		for (String string : set) {
			SendSmsResponse sendSms=SmsUtils.sendSms(string, tText, book.getAccesskey(), book.getAccesskeysecret(), book.getSignname(), book.getTemplateId(),book.getTemplateType());//发送短信
			 System.out.println("Code=" + sendSms.getCode());
	         System.out.println("Message=" + sendSms.getMessage());
	         System.out.println("RequestId=" + sendSms.getRequestId());
	         System.out.println("BizId=" + sendSms.getBizId());
			if (sendSms.getCode() != null && sendSms.getCode().equals("OK")) {
				finish++;
			} else if (sendSms.getCode().equals("isv.BUSINESS_LIMIT_CONTROL")) {
				fail++;

			} else if (sendSms.getCode().equals("isv.DAY_LIMIT_CONTROL")) {
				fail++;
			} else {
				fail++;
			}
		}
		Servefulfill fill=new Servefulfill();
		fill.setFail(fail);
		fill.setFinish(finish);
	       return Result.ok(fill);
	   }



	/**
	 * 发送短信服务
	 * @param smsname 短信模板id
	 * @param tText 内容
	 * @param checked 服务id
	 * @return
	 * @throws ClientException
	 */
	@ApiOperation(value = "根据id查询短信模板内容")
	@RequestMapping(value = "/completeNote3", method = RequestMethod.POST)
	@ResponseBody
	public Result  completeNote3(long smsname,String tText,String checked) throws ClientException {
		YhJzSysSmsWeb book=yhJzSysSmsMapper.selectByPrimaryKey(smsname);//查询出短信模板
		String[] array2 = checked.split(",");//分割服务内容id
		Set<String> set=new HashSet<String>();
		YhServicePage Yhservicepage=new YhServicePage();
		int ret=0;
		int finish=0;
		int fail=0;
		for(int i=0;i<array2.length;i++){
			Yhservicepage.setId(Integer.valueOf(array2[i]));
			Yhservicepage.setState(3);
			Yhservicepage.setReturnvisit(5);
			Yhservicepage.setAcceptsituation("短信回访"+tText);
			Yhservicepage.setSatisfaction(2);
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
			Yhservicepage.setEndtime(dateFormat.format(date));
			ret=ret+Yhmanagetaskmapper.updatestate(Yhservicepage);
			String mobile=yhManageTaskMapper.selectmobilebyusercode(array2[i]);//取电话号码
			if(StringUtil.isNotEmpty(mobile)){
				set.add(mobile);//去重电话号码
				finish++;
			}

		}

		for (String string : set) {
			SendSmsResponse sendSms=SmsUtils.sendSms(string, tText, book.getAccesskey(), book.getAccesskeysecret(), book.getSignname(), book.getTemplateId(),book.getTemplateType());//发送短信
			System.out.println("Code=" + sendSms.getCode());
			System.out.println("Message=" + sendSms.getMessage());
			System.out.println("RequestId=" + sendSms.getRequestId());
			System.out.println("BizId=" + sendSms.getBizId());
			if (sendSms.getCode() != null && sendSms.getCode().equals("OK")) {
				finish++;
			} else if (sendSms.getCode().equals("isv.BUSINESS_LIMIT_CONTROL")) {
				fail++;

			} else if (sendSms.getCode().equals("isv.DAY_LIMIT_CONTROL")) {
				fail++;
			} else {
				fail++;
			}
		}
		Servefulfill fill=new Servefulfill();
		fill.setFail(fail);
		fill.setFinish(finish);
		return Result.ok(fill);
	}
	
	
	/**
	 * 医生随时发送短信服务
	 * @param smsname 短信模板id
	 * @param tText 内容
	 * @param checked 服务id
	 * @return
	 * @throws ClientException
	 */
	@ApiOperation(value = "根据id查询短信模板内容")
	   @RequestMapping(value = "/doctorcompleteNote", method = RequestMethod.POST)
		@ResponseBody
	   public Result  doctorcompleteNote(long smsname,String tText,String phone) throws ClientException { 
		 YhJzSysSmsWeb book=yhJzSysSmsMapper.selectByPrimaryKey(smsname);//查询出短信模板
			int finish=0;
			int fail=0;
			SendSmsResponse sendSms=SmsUtils.sendSms(phone, tText, book.getAccesskey(), book.getAccesskeysecret(), book.getSignname(), book.getTemplateId(),book.getTemplateType());//发送短信
			 System.out.println("Code=" + sendSms.getCode());
	         System.out.println("Message=" + sendSms.getMessage());
	         System.out.println("RequestId=" + sendSms.getRequestId());
	         System.out.println("BizId=" + sendSms.getBizId());
			if (sendSms.getCode() != null && sendSms.getCode().equals("OK")) {
				finish++;
			} else if (sendSms.getCode().equals("isv.BUSINESS_LIMIT_CONTROL")) {
				fail++;

			} else if (sendSms.getCode().equals("isv.DAY_LIMIT_CONTROL")) {
				fail++;
			} else {
				fail++;
			}
		
		Servefulfill fill=new Servefulfill();
		fill.setFail(fail);
		fill.setFinish(finish);
	       return Result.ok(fill);
	   }
	
	
	 @ApiOperation(value = "根据id查询短信模板内容")
	   @RequestMapping(value = "/SysTextSelectByPrimaryKey", method = RequestMethod.POST)
		@ResponseBody
	   public Result  SysTextSelectByPrimaryKey(Integer smsId) { 
		 List<YhJzSysSmsText> text=yhJzSysSmsTextMapper.selectSmsId(smsId);
	       return Result.ok(text);
	   }
	 
	 
	 @ApiOperation(value = "根据id查询短信模板内容")
	   @RequestMapping(value = "/selectSmsId", method = RequestMethod.POST)
		@ResponseBody
	   public Result  selectSmsId(Integer tId) { 
		 YhJzSysSmsText text=yhJzSysSmsTextMapper.selectByPrimaryKey(tId);
	       return Result.ok(text);
	   }
	
	
	 @ApiOperation(value = "查询短信模板全部")
	   @RequestMapping(value = "/showSysSmsAll", method = RequestMethod.POST)
		@ResponseBody
	   public Result  showSysSmsAll() { 
		 List<YhJzSysSmsWeb> book=yhJzSysSmsMapper.showSysSmsAll();
	       return Result.ok(book);
	   }
	 
	 @ApiOperation(value = "根据id查询短信模板")
	   @RequestMapping(value = "/SysSmsSelectByPrimaryKey", method = RequestMethod.POST)
		@ResponseBody
	   public Result  SysSmsSelectByPrimaryKey(long smsId) { 
		 YhJzSysSmsWeb book=yhJzSysSmsMapper.selectByPrimaryKey(smsId);
	       return Result.ok(book);
	   }
}
