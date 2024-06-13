package com.yihe.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.HealthAssessmentVOJO;
import com.yihe.system.domain.YHDocGroup;
import com.yihe.system.domain.YhDicAssessmentScreen;
import com.yihe.system.domain.YhDiseaseAssessReportVOJO;
import com.yihe.system.domain.YhDiseaseAssessTotalReport;
import com.yihe.system.domain.YhDiseaseAssessTotalReportExample;
import com.yihe.system.mapper.YHDocGroupMapper;
import com.yihe.system.mapper.YhDiseaseAssessTotalReportMapper;
import com.yihe.system.service.YhDiseaseAssessTotalReportService;
import com.yihe.system.utils.StringUtil;

/**
 * YhDiseaseAssessTotalReportService实现 Created by dyb on 2020/3/20.
 */
@Service
public class YhDiseaseAssessTotalReportServiceImpl implements YhDiseaseAssessTotalReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhDiseaseAssessTotalReportServiceImpl.class);

	@Autowired
	YhDiseaseAssessTotalReportMapper yhDiseaseAssessTotalReportMapper;
	@Autowired
	YHDocGroupMapper YHDocGroupMapper;

	@Override
	public List<YhDiseaseAssessTotalReport> selectByPrimaryKeyByAge(YhDicAssessmentScreen selectByPrimaryKey) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.selectByPrimaryKeyByAge(selectByPrimaryKey);
	}

	@Override
	public long countByExample(YhDiseaseAssessTotalReportExample example) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(YhDiseaseAssessTotalReportExample example) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(YhDiseaseAssessTotalReport record) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.insert(record);
	}

	@Override
	public int insertSelective(YhDiseaseAssessTotalReport record) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.insertSelective(record);
	}

	@Override
	public List<YhDiseaseAssessTotalReport> selectByExample(YhDiseaseAssessTotalReportExample example) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.selectByExample(example);
	}

	@Override
	public YhDiseaseAssessTotalReport selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(YhDiseaseAssessTotalReport record, YhDiseaseAssessTotalReportExample example) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(YhDiseaseAssessTotalReport record, YhDiseaseAssessTotalReportExample example) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(YhDiseaseAssessTotalReport record) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhDiseaseAssessTotalReport record) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageInfo<YhDiseaseAssessReportVOJO> selectByPrimaryKeyByusercode(String usercode, String name,
			String username, String strtime, String endtime, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhDiseaseAssessReportVOJO> list = yhDiseaseAssessTotalReportMapper
				.selectByPrimaryKeyByusercode(usercode, name, username, strtime, endtime);
		for (YhDiseaseAssessReportVOJO vojo : list) {
			//高风险疾病
			String bfnames = vojo.getBfnames();
			if(StringUtil.isNotEmpty(bfnames)){
				String[] bfSplit = bfnames.split(";");
				vojo.setHighRiskNum(bfSplit.length);
				String bfinfo="";
				for (String string : bfSplit) {
					if(StringUtil.isNotEmpty(string)){
						String bfName = yhDiseaseAssessTotalReportMapper.findCategoryByCode(string);
						bfinfo=bfinfo+bfName+";";
					}
				}
				vojo.setHighRiskInfo(bfinfo);
			}
			
			
			//患或异常疾病
			String sknames = vojo.getSknames();
			if(StringUtil.isNotEmpty(sknames)){
				String[] skSplit = sknames.split(";");
				vojo.setAbnormalNum(skSplit.length);
				String skinfo="";
				for (String string : skSplit) {
					if(StringUtil.isNotEmpty(string)){
						String skName = yhDiseaseAssessTotalReportMapper.findCategoryByCode(string);
						skinfo=skinfo+skName+";";
					}
				}
				vojo.setAbnormalInfo(skinfo);
			}
			
		  
			
		}
		return new PageInfo<YhDiseaseAssessReportVOJO>(list);
	}

	@Override
	public List<YhDiseaseAssessReportVOJO> selectByPrimaryKeyByusercodeCount(String usercode, String name,
			String username, String strtime, String endtime) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.selectByPrimaryKeyByusercodeCount(usercode, name, username, strtime,
				endtime);
	}

	@Override
	public String selectByPrimaryKeyByusercodeForQtime(String usercode) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.selectByPrimaryKeyByusercodeForQtime(usercode);
	}

	@Override
	public String selectByPrimaryKeyByusercodeForCtime(String usercode) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.selectByPrimaryKeyByusercodeForCtime(usercode);
	}

	@Override
	public String selectByPrimaryKeyByusercodeForSporttime(String usercode) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.selectByPrimaryKeyByusercodeForSporttime(usercode);
	}

	@Override
	public String selectByPrimaryKeyByusercodeForEnrolInfotime(String usercode) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.selectByPrimaryKeyByusercodeForEnrolInfotime(usercode);
	}

	@Override
	public String selectByPrimaryKeyByusercodeForRequestSerialNum(String usercode) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.selectByPrimaryKeyByusercodeForRequestSerialNum(usercode);
	}

	@Override
	public int selectByPrimaryByUsercodeForCount(String usercode) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.selectByPrimaryByUsercodeForCount(usercode);
	}

	@Override
	public PageInfo<HealthAssessmentVOJO> findAssessTotalByUserCode(String  usercode,String strTime,String endTime,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<HealthAssessmentVOJO> list = yhDiseaseAssessTotalReportMapper.findAssessTotalByUserCode(usercode,strTime,endTime);
		
		for (HealthAssessmentVOJO healthAssessmentVOJO : list) {
			//请求序列号
			String requestSerialNum = healthAssessmentVOJO.getRequestSerialNum();
			int gao=0;//高风险
			String gaoInfo="";
			int zhong=0;//中风险
			String zhongInfo="";
			int di=0;//低风险
			String diInfo="";
			int yi=0;//异常或患病
			String yiInfo="";
			List<HealthAssessmentVOJO> findriskLevelByrequestSerialNum = findriskLevelByrequestSerialNum(requestSerialNum);
			for (HealthAssessmentVOJO healthAssessmentVOJO2 : findriskLevelByrequestSerialNum) {
				String riskLevel = healthAssessmentVOJO2.getRiskLevel();
				String diseaseName = healthAssessmentVOJO2.getDiseaseName();
				if(riskLevel.contains("高")){
					if(!riskLevel.equals("高血压")){
						gao++;
						gaoInfo += diseaseName + "; ";
					}
				}
				if(riskLevel.contains("中")){
					zhong++;
					zhongInfo += diseaseName + "; ";
				}
				if(riskLevel.contains("低")){
					di++;
					diInfo += diseaseName + "; ";
				}
				if(riskLevel.contains("患")||riskLevel.contains("异")){
					yi++;
					yiInfo += diseaseName + "; ";
				}
			}
			
			healthAssessmentVOJO.setHighRiskNum(gao);
			healthAssessmentVOJO.setHighRiskInfo(gaoInfo);
			healthAssessmentVOJO.setMediumRiskNum(zhong);
			healthAssessmentVOJO.setMediumRiskInfo(zhongInfo);
			healthAssessmentVOJO.setLowRiskNum(di);
			healthAssessmentVOJO.setLowRiskInfo(diInfo);
			healthAssessmentVOJO.setAbnormalNum(yi);
			healthAssessmentVOJO.setAbnormalInfo(yiInfo);
			
		}
		return new PageInfo<HealthAssessmentVOJO>(list);
	}

	@Override
	public List<HealthAssessmentVOJO> findriskLevelByrequestSerialNum(String requestSerialNum) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.findriskLevelByrequestSerialNum(requestSerialNum);
	}

	@Override
	public int findAssessTotalCountSize(String usercode) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.findAssessTotalCountSize(usercode);
	}

	@Override
	public List<String> findAssessInfoByDocTor2(YhDicAssessmentScreen selectByPrimaryKey){
		List<String> list = yhDiseaseAssessTotalReportMapper.findAssessInfoByDocTor2(selectByPrimaryKey);
		return  list;
	}

	@Override
	public PageInfo<YhDiseaseAssessReportVOJO> findAssessInfoByDocTor(YhDicAssessmentScreen selectByPrimaryKey, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhDiseaseAssessReportVOJO> list = yhDiseaseAssessTotalReportMapper.findAssessInfoByDocTor(selectByPrimaryKey);
		for (YhDiseaseAssessReportVOJO vojo : list) {
			//高风险疾病
			String bfnames = vojo.getBfnames();
			if(StringUtil.isNotEmpty(bfnames)){
				String[] bfSplit = bfnames.split(";");
				vojo.setHighRiskNum(bfSplit.length);
				String bfinfo="";
				for (String string : bfSplit) {
					if(StringUtil.isNotEmpty(string)){
						 
						bfinfo=bfinfo+string+";";
					}
				}
				vojo.setHighRiskInfo(bfinfo);
			}
			
			
			//患或异常疾病
			String sknames = vojo.getSknames();
			if(StringUtil.isNotEmpty(sknames)){
				String[] skSplit = sknames.split(";");
				vojo.setAbnormalNum(skSplit.length);
				String skinfo="";
				for (String string : skSplit) {
					if(StringUtil.isNotEmpty(string)){
						skinfo=skinfo+string+";";
					}
				}
				vojo.setAbnormalInfo(skinfo);
			}
			
		 
			String flagDgroup = vojo.getFlagDgroup();
			String groupInfo="";
			if(StringUtil.isNotEmpty(flagDgroup)){
				
				//f223_g1
				String[] split = flagDgroup.split(";");
				for (String string : split) {
					string=string.replace("f","");
				     String[] split2 = string.split("_");
				     string=split2[0];
				     if(StringUtil.isEmpty(string)){
				    	 continue;
				     }
				     YHDocGroup gInfo = YHDocGroupMapper.selectByPrimaryKey(Integer.valueOf(string));
				     if(null!=gInfo){
				    	 string=gInfo.getGname();
				    	 groupInfo=groupInfo+string+";";
				     }
				     
				}
				
				vojo.setFlagDgroup(groupInfo);
			}
			
		}
		return new PageInfo<YhDiseaseAssessReportVOJO>(list);
	}
	
	public static void main(String[] args) {
		String info="1;1";
		String[] split = info.split(";");
		System.out.println(split.length);
	}

	@Override
	public YhDiseaseAssessReportVOJO findAssessTotalByUsercodeNewInfo(String usercode) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportMapper.findAssessTotalByUsercodeNewInfo(usercode);
	}
	
	  

}