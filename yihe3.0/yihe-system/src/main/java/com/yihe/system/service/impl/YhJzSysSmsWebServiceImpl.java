/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhJzSysSmsWeb;
import com.yihe.system.domain.YhJzSysSmsWebVojo;
import com.yihe.system.domain.YhWxInformation;
import com.yihe.system.domain.YhWxInformationVojo;
import com.yihe.system.mapper.YhJzSysSmsMapper;
import com.yihe.system.mapper.YhWxInformationMapper;
import com.yihe.system.service.YhJzSysSmsWebService;

/**
 * @author dong
 *
 */
@Service
@Transactional
public class YhJzSysSmsWebServiceImpl implements YhJzSysSmsWebService{

	@Autowired
	YhJzSysSmsMapper yhJzSysSmsMapper;
	
	@Autowired
	YhWxInformationMapper yhWxInformationMapper;
	
	
	@Override
	public YhWxInformationVojo selectbyWxINfo() {
		// TODO Auto-generated method stub
		List<YhWxInformation> wxinfo=yhWxInformationMapper.selectbyWxINfo();
		YhWxInformationVojo vojo=new YhWxInformationVojo();
		for (YhWxInformation yhWxInformation : wxinfo) {
			vojo.setGzAppid(yhWxInformation.getGzAppid());
			vojo.setGzAppsecret(yhWxInformation.getGzAppsecret());
			vojo.setXcAppid(yhWxInformation.getXcAppid());
			vojo.setXcAppsecret(yhWxInformation.getXcAppsecret());
			if(yhWxInformation.getWxType().equals("task")) {
				vojo.setTaskboardId(yhWxInformation.getBoardId());
				vojo.setTaskvariate1(yhWxInformation.getVariate1());
				vojo.setTaskvariate2(yhWxInformation.getVariate2());
			}else if(yhWxInformation.getWxType().equals("reply")) {
				vojo.setReplyboardId(yhWxInformation.getBoardId());
				vojo.setReplyvariate1(yhWxInformation.getVariate1());
				vojo.setReplyvariate2(yhWxInformation.getVariate2());
				vojo.setReplyvariate3(yhWxInformation.getVariate3());
			}else if(yhWxInformation.getWxType().equals("taskGZH")) {
				vojo.setGzhboardId(yhWxInformation.getBoardId());
				vojo.setGzhvariate1(yhWxInformation.getVariate1());
				vojo.setGzhvariate2(yhWxInformation.getVariate2());
				vojo.setGzhvariate3(yhWxInformation.getVariate3());
				vojo.setGzhvariate4(yhWxInformation.getVariate4());
			}else if(yhWxInformation.getWxType().equals("replyGZH")) {
				vojo.setGzhreplyboardId(yhWxInformation.getBoardId());
				vojo.setGzhreplyvariate1(yhWxInformation.getVariate1());
				vojo.setGzhreplyvariate2(yhWxInformation.getVariate2());
				vojo.setGzhreplyvariate3(yhWxInformation.getVariate3());
				vojo.setGzhreplyvariate4(yhWxInformation.getVariate4());
				vojo.setGzhreplyvariate5(yhWxInformation.getVariate5());
			}
		}
		
		return vojo;
	}
	
	
	@Override
	public int insertSelectiveWxInfo(YhWxInformationVojo record) {
		int i=0;
		// TODO Auto-generated method stub
		if(record.getTaskboardId()!=null) {
			YhWxInformation wx=yhWxInformationMapper.selectbyWxType("task");
			YhWxInformation tion=new YhWxInformation();
			tion.setGzAppid(record.getGzAppid());
			tion.setGzAppsecret(record.getGzAppsecret());
			tion.setXcAppid(record.getXcAppid());
			tion.setXcAppsecret(record.getXcAppsecret());
			tion.setBoardId(record.getTaskboardId());
			tion.setVariate1(record.getTaskvariate1());
			tion.setVariate2(record.getTaskvariate2());
			tion.setInfoType("1");
			tion.setMbName("随访任务提醒");
			tion.setWxType("task");
			if(wx!=null) {
				tion.setWxId(wx.getWxId());
				i=yhWxInformationMapper.updateByPrimaryKeySelective(tion);
			}else {
				i=yhWxInformationMapper.insertSelective(tion);
			}
		}
		
		
		if(record.getReplyboardId()!=null) {
			YhWxInformation wx=yhWxInformationMapper.selectbyWxType("reply");
			YhWxInformation tion=new YhWxInformation();
			tion.setGzAppid(record.getGzAppid());
			tion.setGzAppsecret(record.getGzAppsecret());
			tion.setXcAppid(record.getXcAppid());
			tion.setXcAppsecret(record.getXcAppsecret());
			tion.setBoardId(record.getReplyboardId());
			tion.setVariate1(record.getReplyvariate1());
			tion.setVariate2(record.getReplyvariate2());
			tion.setVariate3(record.getReplyvariate3());
			tion.setMbName("咨询回复通知");
			tion.setInfoType("1");
			tion.setWxType("reply");
			if(wx!=null) {
				tion.setWxId(wx.getWxId());
				i=yhWxInformationMapper.updateByPrimaryKeySelective(tion);
			}else {
				i=yhWxInformationMapper.insertSelective(tion);
			}
		}
		
		
		if(record.getGzhboardId()!=null) {
			YhWxInformation wx=yhWxInformationMapper.selectbyWxType("taskGZH");
			YhWxInformation tion=new YhWxInformation();
			tion.setGzAppid(record.getGzAppid());
			tion.setGzAppsecret(record.getGzAppsecret());
			tion.setXcAppid(record.getXcAppid());
			tion.setXcAppsecret(record.getXcAppsecret());
			tion.setBoardId(record.getGzhboardId());
			tion.setVariate1(record.getGzhvariate1());
			tion.setVariate2(record.getGzhvariate2());
			tion.setVariate3(record.getGzhvariate3());
			tion.setVariate4(record.getGzhvariate4());
			tion.setInfoType("2");
			tion.setMbName("随访任务提醒");
			tion.setWxType("taskGZH");
			if(wx!=null) {
				tion.setWxId(wx.getWxId());
				i=yhWxInformationMapper.updateByPrimaryKeySelective(tion);
			}else {
				i=yhWxInformationMapper.insertSelective(tion);
			}
		}
		
		
		if(record.getGzhboardId()!=null) {
			YhWxInformation wx=yhWxInformationMapper.selectbyWxType("replyGZH");
			YhWxInformation tion=new YhWxInformation();
			tion.setGzAppid(record.getGzAppid());
			tion.setGzAppsecret(record.getGzAppsecret());
			tion.setXcAppid(record.getXcAppid());
			tion.setXcAppsecret(record.getXcAppsecret());
			tion.setBoardId(record.getGzhreplyboardId());
			tion.setVariate1(record.getGzhreplyvariate1());
			tion.setVariate2(record.getGzhreplyvariate2());
			tion.setVariate3(record.getGzhreplyvariate3());
			tion.setVariate4(record.getGzhreplyvariate4());
			tion.setVariate5(record.getGzhreplyvariate5());
			tion.setWxType("replyGZH");
			tion.setMbName("咨询回复通知");
			tion.setInfoType("2");
			if(wx!=null) {
				tion.setWxId(wx.getWxId());
				i=yhWxInformationMapper.updateByPrimaryKeySelective(tion);
			}else {
				i=yhWxInformationMapper.insertSelective(tion);
			}
		}
		
		
		return i;
	}
	
	
	@Override
	public YhJzSysSmsWebVojo selectSmsNull() {
		List<YhJzSysSmsWeb> syssms=yhJzSysSmsMapper.selectSmsNull();
		YhJzSysSmsWebVojo vojo=new YhJzSysSmsWebVojo();
		for (YhJzSysSmsWeb yhJzSysSmsWeb : syssms) {
			vojo.setSignname(yhJzSysSmsWeb.getSignname());
			vojo.setAccesskey(yhJzSysSmsWeb.getAccesskey());
			vojo.setAccesskeysecret(yhJzSysSmsWeb.getAccesskeysecret());
			if(yhJzSysSmsWeb.getTemplateGenre().equals("security")) {
				vojo.setTemplateId(yhJzSysSmsWeb.getTemplateId());
				
			}else if(yhJzSysSmsWeb.getTemplateGenre().equals("task")) {
				
				vojo.setTasktemplateId(yhJzSysSmsWeb.getTemplateId());
				vojo.setTasktemplateType(yhJzSysSmsWeb.getTemplateType());
				vojo.setTasktemplateText(yhJzSysSmsWeb.getTemplateText());
			}else if(yhJzSysSmsWeb.getTemplateGenre().equals("food")) {
				
				vojo.setFoodtemplateId(yhJzSysSmsWeb.getTemplateId());
				vojo.setFoodtemplateType(yhJzSysSmsWeb.getTemplateType());
				vojo.setFoodtemplateText(yhJzSysSmsWeb.getTemplateText());
			}else if(yhJzSysSmsWeb.getTemplateGenre().equals("sports")) {
				
				vojo.setSportstemplateId(yhJzSysSmsWeb.getTemplateId());
				vojo.setSportstemplateType(yhJzSysSmsWeb.getTemplateType());
				vojo.setSportstemplateText(yhJzSysSmsWeb.getTemplateText());
			}else if(yhJzSysSmsWeb.getTemplateGenre().equals("knowledge")) {
				
				vojo.setKnowledgetemplateId(yhJzSysSmsWeb.getTemplateId());
				vojo.setKnowledgetemplateType(yhJzSysSmsWeb.getTemplateType());
				vojo.setKnowledgetemplateText(yhJzSysSmsWeb.getTemplateText());
			}else if(yhJzSysSmsWeb.getTemplateGenre().equals("logistics")) {
				
				vojo.setLogisticsId(yhJzSysSmsWeb.getTemplateId());
				vojo.setLogisticsType(yhJzSysSmsWeb.getTemplateType());
				vojo.setLogisticsText(yhJzSysSmsWeb.getTemplateText());
			}
		}
		return vojo;
	}

	@Override
	public int insertSelective(YhJzSysSmsWebVojo record) {
		
		int i=0;
		if(record.getTemplateId()!=null) {//验证码配置
			YhJzSysSmsWeb sec=yhJzSysSmsMapper.selectCountGenre("security");
			YhJzSysSmsWeb sms=new YhJzSysSmsWeb();
			if(sec!=null) {//修改验证码配置
				sms.setSmsId(sec.getSmsId());
				sms.setTemplateId(record.getTemplateId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setType(1);
				i=yhJzSysSmsMapper.updateByPrimaryKeySelective(sms);
			}else {//增加验证码配置
				sms.setTemplateId(record.getTemplateId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setSmsname("谷宜健康3.0修改密码");
				sms.setSmscodeNum(1);
				sms.setTemplateGenre("security");
				sms.setType(1);
				i=yhJzSysSmsMapper.insertSelective(sms);
			}
		}
		
		
		if(record.getTasktemplateId()!=null) {//计划提醒
			YhJzSysSmsWeb sec=yhJzSysSmsMapper.selectCountGenre("task");
			YhJzSysSmsWeb sms=new YhJzSysSmsWeb();
			if(sec!=null) {//修改计划提醒
				sms.setSmsId(sec.getSmsId());
				sms.setTemplateId(record.getTasktemplateId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setType(2);
				sms.setTemplateType(record.getTasktemplateType());
				sms.setTemplateText(record.getTasktemplateText());
				i=yhJzSysSmsMapper.updateByPrimaryKeySelective(sms);
			}else {//增加计划提醒
				sms.setTemplateId(record.getTasktemplateId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setSmsname("计划任务提醒");
				sms.setTemplateGenre("task");
				sms.setType(2);
				sms.setTemplateType(record.getTasktemplateType());
				sms.setTemplateText(record.getTasktemplateText());
				i=yhJzSysSmsMapper.insertSelective(sms);
			}
		}
		
		
		if(record.getFoodtemplateId()!=null) {//饮食指导
			YhJzSysSmsWeb sec=yhJzSysSmsMapper.selectCountGenre("food");
			YhJzSysSmsWeb sms=new YhJzSysSmsWeb();
			if(sec!=null) {//修改饮食指导
				sms.setSmsId(sec.getSmsId());
				sms.setTemplateId(record.getFoodtemplateId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setType(2);
				sms.setTemplateType(record.getFoodtemplateType());
				sms.setTemplateText(record.getFoodtemplateText());
				i=yhJzSysSmsMapper.updateByPrimaryKeySelective(sms);
			}else {//增加饮食指导
				sms.setTemplateId(record.getFoodtemplateId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setSmsname("饮食指导");
				sms.setTemplateGenre("food");
				sms.setType(2);
				sms.setTemplateType(record.getFoodtemplateType());
				sms.setTemplateText(record.getFoodtemplateText());
				i=yhJzSysSmsMapper.insertSelective(sms);
			}
		}
		
		
		if(record.getLogisticsId()!=null) {//物流发货提醒
			YhJzSysSmsWeb sec=yhJzSysSmsMapper.selectCountGenre("logistics");
			YhJzSysSmsWeb sms=new YhJzSysSmsWeb();
			if(sec!=null) {//修改物流发货提醒
				sms.setSmsId(sec.getSmsId());
				sms.setTemplateId(record.getLogisticsId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setType(3);
				sms.setTemplateType(record.getLogisticsType());
				sms.setTemplateText(record.getLogisticsText());
				i=yhJzSysSmsMapper.updateByPrimaryKeySelective(sms);
			}else {//增加物流发货提醒
				sms.setTemplateId(record.getLogisticsId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setSmsname("发货提醒");
				sms.setTemplateGenre("logistics");
				sms.setType(3);
				sms.setTemplateType(record.getLogisticsType());
				sms.setTemplateText(record.getLogisticsText());
				i=yhJzSysSmsMapper.insertSelective(sms);
			}
		}
		
		if(record.getSportstemplateId()!=null) {//运动指导
			YhJzSysSmsWeb sec=yhJzSysSmsMapper.selectCountGenre("sports");
			YhJzSysSmsWeb sms=new YhJzSysSmsWeb();
			if(sec!=null) {//修改运动指导
				sms.setSmsId(sec.getSmsId());
				sms.setTemplateId(record.getSportstemplateId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setType(2);
				sms.setTemplateType(record.getSportstemplateType());
				sms.setTemplateText(record.getSportstemplateText());
				i=yhJzSysSmsMapper.updateByPrimaryKeySelective(sms);
			}else {//增加运动指导
				sms.setTemplateId(record.getSportstemplateId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setSmsname("运动指导");
				sms.setTemplateGenre("sports");
				sms.setType(2);
				sms.setTemplateType(record.getSportstemplateType());
				sms.setTemplateText(record.getSportstemplateText());
				i=yhJzSysSmsMapper.insertSelective(sms);
			}
		}
		
		if(record.getKnowledgetemplateId()!=null) {//健康知识
			YhJzSysSmsWeb sec=yhJzSysSmsMapper.selectCountGenre("knowledge");
			YhJzSysSmsWeb sms=new YhJzSysSmsWeb();
			if(sec!=null) {//修改健康知识
				sms.setSmsId(sec.getSmsId());
				sms.setTemplateId(record.getKnowledgetemplateId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setType(2);
				sms.setTemplateType(record.getKnowledgetemplateType());
				sms.setTemplateText(record.getKnowledgetemplateText());
				i=yhJzSysSmsMapper.updateByPrimaryKeySelective(sms);
			}else {//增加健康知识
				sms.setTemplateId(record.getKnowledgetemplateId());
				sms.setSignname(record.getSignname());
				sms.setAccesskey(record.getAccesskey());
				sms.setAccesskeysecret(record.getAccesskeysecret());
				sms.setSmsname("健康知识");
				sms.setTemplateGenre("knowledge");
				sms.setType(2);
				sms.setTemplateType(record.getKnowledgetemplateType());
				sms.setTemplateText(record.getKnowledgetemplateText());
				i=yhJzSysSmsMapper.insertSelective(sms);
			}
		}
		
		return i;
	}


	@Override
	public YhJzSysSmsWeb selectYhJzSysSmslogistics() {
		// TODO Auto-generated method stub
		return yhJzSysSmsMapper.selectYhJzSysSmslogistics();
	}





	

	

}
