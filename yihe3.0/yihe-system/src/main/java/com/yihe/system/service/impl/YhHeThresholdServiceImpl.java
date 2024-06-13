package com.yihe.system.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;
import com.yihe.system.domain.EmergencyVojo;
import com.yihe.system.domain.YhHeThreshold;
import com.yihe.system.domain.YhHeThreshold2Web;
import com.yihe.system.domain.YhHealthSetting;
import com.yihe.system.domain.YhJzServicePerson;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhAcceptserviceMapper;
import com.yihe.system.mapper.YhHeThreshold2WebMapper;
import com.yihe.system.mapper.YhHeThresholdMapper;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.utils.SendGet;

/**
* YhHeThresholdService实现
* Created by royiwen on 2020/3/27.
*/
@Service
@Transactional

public class YhHeThresholdServiceImpl  implements YhHeThresholdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhHeThresholdServiceImpl.class);

    @Autowired
    YhHeThresholdMapper yhHeThresholdMapper;
    
    
    @Autowired
    YhHeThreshold2WebMapper yhHeThreshold2WebMapper;
    

    @Autowired
    YhAcceptserviceMapper yhAcceptserviceMapper;
    
    @Autowired
	YhUserReginfoMapper yhUserReginfoMapper;
    
	@Override
	public List<YhHeThreshold> selectallheath() {
		// TODO Auto-generated method stub
		return yhHeThresholdMapper.selectallheath();
	}

	@Override
	@Transactional
	public int updatasetting(YhHealthSetting yhhealthsetting) {
		// TODO Auto-generated method stub
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getBMI_price_min(),yhhealthsetting.getBMI_price_max(),yhhealthsetting.getBMI_close()==null?0:1,yhhealthsetting.getBMI_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getDimi_price_min(),yhhealthsetting.getDimi_price_max(),yhhealthsetting.getDimi_close()==null?0:1,yhhealthsetting.getDimi_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getGanyou_price_min(),yhhealthsetting.getGanyou_price_max(),yhhealthsetting.getGanyou_close()==null?0:1,yhhealthsetting.getGanyou_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getGaomi_price_min(),yhhealthsetting.getGaomi_price_max(),yhhealthsetting.getGaomi_close()==null?0:1,yhhealthsetting.getGaomi_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getGubing_price_min(),yhhealthsetting.getGubing_price_max(),yhhealthsetting.getGubing_close()==null?0:1,yhhealthsetting.getGubing_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getGucao_price_min(),yhhealthsetting.getGucao_price_max(),yhhealthsetting.getGucao_close()==null?0:1,yhhealthsetting.getGucao_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getNiaodanbai_price_min(),yhhealthsetting.getNiaodanbai_price_max(),yhhealthsetting.getNiaodanbai_close()==null?0:1,yhhealthsetting.getNiaodanbai_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getNiaojianhong_price_min(),yhhealthsetting.getNiaojianhong_price_max(),yhhealthsetting.getNiaojianhong_close()==null?0:1,yhhealthsetting.getNiaojianhong_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getNiaowei_price_min(),yhhealthsetting.getNiaowei_price_max(),yhhealthsetting.getNiaowei_close()==null?0:1,yhhealthsetting.getNiaowei_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getShousuoya_price_min(),yhhealthsetting.getShousuoya_price_max(),yhhealthsetting.getShousuoya_close()==null?0:1,yhhealthsetting.getShousuoya_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getTanghua_price_min(),yhhealthsetting.getTanghua_price_max(),yhhealthsetting.getTanghua_close()==null?0:1,yhhealthsetting.getTanghua_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getTiwen_price_min(),yhhealthsetting.getTiwen_price_max(),yhhealthsetting.getTiwen_close()==null?0:1,yhhealthsetting.getTiwen_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getTongti_price_min(),yhhealthsetting.getTongti_price_max(),yhhealthsetting.getTongti_close()==null?0:1,yhhealthsetting.getTongti_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getTongxingban_price_min(),yhhealthsetting.getTongxingban_price_max(),yhhealthsetting.getTongxingban_close()==null?0:1,yhhealthsetting.getTongxingban_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXinlv_price_min(),yhhealthsetting.getXinlv_price_max(),yhhealthsetting.getXinlv_close()==null?0:1,yhhealthsetting.getXinlv_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXuejimei_price_min(),yhhealthsetting.getXuejimei_price_max(),yhhealthsetting.getXuejimei_close()==null?0:1,yhhealthsetting.getXuejimei_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXuejimei_price_min2(),yhhealthsetting.getXuejimei_price_max2(),yhhealthsetting.getXuejimei_close2()==null?0:1,yhhealthsetting.getXuejimei_name2());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXueniaosu_price_min(),yhhealthsetting.getXueniaosu_price_max(),yhhealthsetting.getXueniaosu_close()==null?0:1,yhhealthsetting.getXueniaosu_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXueniaosuan_price_min(),yhhealthsetting.getXueniaosuan_price_max(),yhhealthsetting.getXueniaosuan_close()==null?0:1,yhhealthsetting.getXueniaosuan_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXueniaosuan_price_min2(),yhhealthsetting.getXueniaosuan_price_max2(),yhhealthsetting.getXueniaosuan_close2()==null?0:1,yhhealthsetting.getXueniaosuan_name2());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXueqingbai_price_min(),yhhealthsetting.getXueqingbai_price_max(),yhhealthsetting.getXueqingbai_close()==null?0:1,yhhealthsetting.getXueqingbai_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXueqingqiu_price_min(),yhhealthsetting.getXueqingqiu_price_max(),yhhealthsetting.getXueqingqiu_close()==null?0:1,yhhealthsetting.getXueqingqiu_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXuetang_price_min(),yhhealthsetting.getXuetang_price_max(),yhhealthsetting.getXuetang_close()==null?0:1,yhhealthsetting.getXuetang_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXueyang_price_min(),yhhealthsetting.getXueyang_price_max(),yhhealthsetting.getXueyang_close()==null?0:1,yhhealthsetting.getXueyang_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getZongdan_price_min(),yhhealthsetting.getZongdan_price_max(),yhhealthsetting.getZongdan_close()==null?0:1,yhhealthsetting.getZongdan_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getShuzhangya_price_min(),yhhealthsetting.getShuzhangya_price_max(),yhhealthsetting.getShuzhangya_close()==null?0:1,yhhealthsetting.getShuzhangya_name());
		yhHeThresholdMapper.updatasetting(yhhealthsetting.getXuetang_price_min2(),yhhealthsetting.getXuetang_price_max2(),yhhealthsetting.getXuetang_close2()==null?0:1,yhhealthsetting.getXuetang_name2());
		return 1;
	}  

	/**
	 * 数据告警阳雄定制版
	 * @param vojo
	 * @param request
	 * @throws ParseException
	 */
	public void EmergencyAdd(List<EmergencyVojo> vojo, HttpServletRequest request) throws ParseException {
		// TODO Auto-generated method stub
				Date date = new Date();
				SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    String dateStr = format.format(new Date());
			    format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
			    Date date1 = format.parse(dateStr);
			    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			    dateStr = format.format(date1);
			    String doctorCode="";
			    int num=0;
			    
		for (EmergencyVojo emergencyVojo : vojo) {
			//根据Type查询是否开启
			YhHeThreshold hold=yhHeThresholdMapper.selectBythresType(emergencyVojo.getType());
			
			YhUserReginfo info=yhUserReginfoMapper.selectByPrimaryKey(emergencyVojo.getUserCode());//查询用户表
			YhUserReginfo doctor=yhUserReginfoMapper.showdoctorCode(emergencyVojo.getUserCode());//查询医生code
			if(num==0) {
				doctorCode=doctor.getUserCode();
			}
			
			DecimalFormat df = new DecimalFormat("#.##");
			double d =Double.valueOf(emergencyVojo.getValue());
			
			String userCode=emergencyVojo.getUserCode();
			String value=emergencyVojo.getValue();
			String typeName=emergencyVojo.getTypeName();
			String type=emergencyVojo.getType();
			
			if(hold.getISTRUE()==1) {
				YhHeThreshold2Web web=yhHeThreshold2WebMapper.selectUserYhHeThreshold2(userCode);//查询
				if(web==null) {
					if(hold.getHigh()!=null && !hold.getHigh().equals("") && Double.valueOf(value)>Double.valueOf(hold.getHigh())) {
						YhJzServicePerson per=new YhJzServicePerson();
						per.setUserCode(userCode);
						per.setServiceWaitingTime(dateStr);
						per.setGender(info.getGender());
						per.setPhone(info.getMobile());
						per.setAge(getAgeByBirth(info.getBirthDate()));
						per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
						per.setServiceType(4);
						per.setServiceRecord(typeName+"["+df.format(d)+"]"+"高于上限["+hold.getHigh()+"]报警");
						per.setInsertTime(dateFormat.format(new Date()));
						per.setServiceSubtitle(typeName);
						per.setTotaltype(1);
						yhAcceptserviceMapper.insertserviceplan(per);
						int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
						if(acc==0) {
							yhAcceptserviceMapper.inserttotalper(doctorCode, userCode);
						}
						num=num+1;
					}else if(!hold.getLow().equals("") && hold.getLow()!=null && Double.valueOf(value)<Double.valueOf(hold.getLow())) {
						YhJzServicePerson per=new YhJzServicePerson();
						per.setUserCode(userCode);
						per.setServiceWaitingTime(dateStr);
						per.setGender(info.getGender());
						per.setPhone(info.getMobile());
						per.setAge(getAgeByBirth(info.getBirthDate()));
						per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
						per.setServiceType(4);
						per.setServiceRecord(typeName+"["+df.format(d)+"]"+"小于下限["+hold.getLow()+"]报警");
						per.setInsertTime(dateFormat.format(new Date()));
						per.setServiceSubtitle(typeName);
						per.setTotaltype(1);
						yhAcceptserviceMapper.insertserviceplan(per);
						int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
						if(acc==0) {
							yhAcceptserviceMapper.inserttotalper(doctorCode, userCode);
						}
						num=num+1;
					}
				}else {
					
					if(type.equals("blood_sugar2")) {
						if((web.getBloodSugar2Min()!=null && !web.getBloodSugar2Min().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodSugar2Min())) {//餐后2小时血糖
							addLow1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodSugar2Min(),doctorCode,request);
							num=num+1;
						}
						if((web.getBloodSugar2Max()!=null && !web.getBloodSugar2Max().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodSugar2Max())) {//餐后2小时血糖
							addhigh1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodSugar2Max(),doctorCode,request);
							num=num+1;
						}
					}
					
					
					if(type.equals("blood_pressure_sys")) {
						if((web.getBloodPressureSysMin()!=null && !web.getBloodPressureSysMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodPressureSysMin())) {//收缩压小
							addLow1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodPressureSysMin(),doctorCode,request);
							num=num+1;
						}
						if((web.getBloodPressureSysMax()!=null && !web.getBloodPressureSysMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodPressureSysMax())) {//收缩压大
							addhigh1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodPressureSysMax(),doctorCode,request);
							num=num+1;
						}
					}
					
					
					if(type.equals("blood_sugar")) {
						if((web.getBloodSugarMin()!=null && !web.getBloodSugarMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodSugarMin())) {//小血糖
							addLow1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodSugarMin(),doctorCode,request);
							num=num+1;
						}
						if((web.getBloodSugarMax()!=null && !web.getBloodSugarMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodSugarMax())) {//大血糖
							addhigh1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodSugarMax(),doctorCode,request);
							num=num+1;
						}
					}
					if(type.equals("blood_pressure_dia")) {
						if((web.getBloodPressureDiaMin()!=null && !web.getBloodPressureDiaMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodPressureDiaMin())) {//小舒张压
							addLow1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodPressureDiaMin(),doctorCode,request);
							num=num+1;
						}
						if((web.getBloodPressureDiaMax()!=null && !web.getBloodPressureDiaMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodPressureDiaMax())) {//大舒张压
							addhigh1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodPressureDiaMax(),doctorCode,request);
							num=num+1;
						}
					}
					if(type.equals("trig")) {
						if((web.getTrigMin()!=null && !web.getTrigMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getTrigMin())) {//小甘油三酯
							addLow1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTrigMin(),doctorCode,request);
							num=num+1;
						}
						if((web.getTrigMax()!=null && !web.getTrigMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getTrigMax())) {//大甘油三酯
							addhigh1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTrigMax(),doctorCode,request);
							num=num+1;
						}
					}
					if(type.equals("hdl-c")) {
						if((web.getHdlcMin()!=null && !web.getHdlcMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getHdlcMin())) {//小高密度蛋白胆固醇
							addLow1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getHdlcMin(),doctorCode,request);
							num=num+1;
						}
						if((web.getHdlcMax()!=null && !web.getHdlcMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getHdlcMax())) {//大高密度蛋白胆固醇
							addhigh1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getHdlcMax(),doctorCode,request);
							num=num+1;
						}	
					}
					if(type.equals("ldl-c")) {
						if((web.getLdlcMin()!=null && !web.getLdlcMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getLdlcMin())) {//小低密度蛋白胆固醇	
							addLow1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getLdlcMin(),doctorCode,request);
							num=num+1;
						}
						if((web.getLdlcMax()!=null && !web.getLdlcMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getLdlcMax())) {//大低密度蛋白胆固醇	
							addhigh1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getLdlcMax(),doctorCode,request);
							num=num+1;
						}
					}
					
					if(type.equals("BMI")) {
						if((web.getBmiMin()!=null && !web.getBmiMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBmiMin())) {//小BMI
							addLow1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBmiMin(),doctorCode,request);
							num=num+1;
						}
						if((web.getBmiMax()!=null && !web.getBmiMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBmiMax())) {//大BMI
							addhigh1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBmiMax(),doctorCode,request);
							num=num+1;
						}
					}
					
					
					 
					if(type.equals("tc")) {
						if((web.getTcMin()!=null && !web.getTcMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getTcMin())) {//小总胆固醇
							addLow1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTcMin(),doctorCode,request);
							num=num+1;
						}
						if( (web.getTcMax()!=null && !web.getTcMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getTcMax())) {//大总胆固醇
							addhigh1(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTcMax(),doctorCode,request);
							num=num+1;
						}
					}
					
				}
			}
		}
		if(num>0) {
			try {
				String s="新增异常报警"+"["+num+"条]";
			    StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append("").append(request.getContextPath()).append("/messge/send");
			    SendGet.doPost(paths.toString(),"loginUserNum="+doctorCode+"&content="+URLEncoder.encode(s,"utf-8"));
			} catch (UnsupportedEncodingException e) {
			    e.printStackTrace();
			}
		}
	}
	
	

	@Override
	public YhHeThreshold selectBythresType(String type, String userCode,String value,String typeName, HttpServletRequest request) throws ParseException {
		// TODO Auto-generated method stub
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateStr = format.format(new Date());
	    format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	    Date date1 = format.parse(dateStr);
	    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	    dateStr = format.format(date1);
	    
		//根据Type查询是否开启
		YhHeThreshold hold=yhHeThresholdMapper.selectBythresType(type);
		
		YhUserReginfo info=yhUserReginfoMapper.selectByPrimaryKey(userCode);//查询用户表
		YhUserReginfo doctor=yhUserReginfoMapper.showdoctorCode(userCode);//查询医生code
		
		if(type.equals("the_ketone_body")) {//酮体
			if(!value.equals("-")) {
				YhJzServicePerson per=new YhJzServicePerson();
				per.setUserCode(userCode);
				per.setServiceWaitingTime(dateStr);
				per.setGender(info.getGender());
				per.setPhone(info.getMobile());
				per.setAge(getAgeByBirth(info.getBirthDate()));
				per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
				per.setServiceType(4);
				per.setServiceRecord(typeName+"["+value+"]"+"异常报警");
				per.setInsertTime(dateFormat.format(new Date()));
				per.setServiceSubtitle(typeName);
				per.setTotaltype(1);
				yhAcceptserviceMapper.insertserviceplan(per);
				int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
				if(acc==0) {
					yhAcceptserviceMapper.inserttotalper(doctor.getUserCode(), userCode);
				}
				
				try {
					String s="异常报警"+"["+typeName+"]";
				    StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append("").append(request.getContextPath()).append("/messge/send");
				    SendGet.doPost(paths.toString(),"loginUserNum="+doctor.getUserCode()+"&content="+URLEncoder.encode(s,"utf-8"));
				} catch (UnsupportedEncodingException e) {
				    e.printStackTrace();
				}
				
			}
			
			return null;
		}
		
		
		if(type.equals("urinalysis_erythrocyte")) {//尿检白细胞
			if(!value.equals("-")) {
				YhJzServicePerson per=new YhJzServicePerson();
				per.setUserCode(userCode);
				per.setServiceWaitingTime(dateStr);
				per.setGender(info.getGender());
				per.setPhone(info.getMobile());
				per.setAge(getAgeByBirth(info.getBirthDate()));
				per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
				per.setServiceType(4);
				per.setServiceRecord(typeName+"["+value+"]"+"异常报警");
				per.setInsertTime(dateFormat.format(new Date()));
				per.setServiceSubtitle(typeName);
				per.setTotaltype(1);
				yhAcceptserviceMapper.insertserviceplan(per);
				int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
				if(acc==0) {
					yhAcceptserviceMapper.inserttotalper(doctor.getUserCode(), userCode);
				}
				try {
					String s="异常报警"+"["+typeName+"]";
				    StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append("").append(request.getContextPath()).append("/messge/send");
				    SendGet.doPost(paths.toString(),"loginUserNum="+doctor.getUserCode()+"&content="+URLEncoder.encode(s,"utf-8"));
				} catch (UnsupportedEncodingException e) {
				    e.printStackTrace();
				}
			}
			return null;
		}
		
		if(type.equals("urine_protein")) {//尿蛋白
			if(!value.equals("-")) {
				YhJzServicePerson per=new YhJzServicePerson();
				per.setUserCode(userCode);
				per.setServiceWaitingTime(dateStr);
				per.setGender(info.getGender());
				per.setPhone(info.getMobile());
				per.setAge(getAgeByBirth(info.getBirthDate()));
				per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
				per.setServiceType(4);
				per.setServiceRecord(typeName+"["+value+"]"+"异常报警");
				per.setInsertTime(dateFormat.format(new Date()));
				per.setServiceSubtitle(typeName);
				per.setTotaltype(1);
				yhAcceptserviceMapper.insertserviceplan(per);
				int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
				if(acc==0) {
					yhAcceptserviceMapper.inserttotalper(doctor.getUserCode(), userCode);
				}
				try {
					String s="异常报警"+"["+typeName+"]";
				    StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append("").append(request.getContextPath()).append("/messge/send");
				    SendGet.doPost(paths.toString(),"loginUserNum="+doctor.getUserCode()+"&content="+URLEncoder.encode(s,"utf-8"));
				} catch (UnsupportedEncodingException e) {
				    e.printStackTrace();
				}
			}
			return null;
		}
		
		
		
		DecimalFormat df = new DecimalFormat("#.##");
		double d =Double.valueOf(value);
		
		
	    
		
		if(hold.getISTRUE()==1) {
			YhHeThreshold2Web web=yhHeThreshold2WebMapper.selectUserYhHeThreshold2(userCode);//查询
			if(web==null) {
				if(hold.getHigh()!=null && !hold.getHigh().equals("") && Double.valueOf(value)>Double.valueOf(hold.getHigh())) {
					YhJzServicePerson per=new YhJzServicePerson();
					per.setUserCode(userCode);
					per.setServiceWaitingTime(dateStr);
					per.setGender(info.getGender());
					per.setPhone(info.getMobile());
					per.setAge(getAgeByBirth(info.getBirthDate()));
					per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
					per.setServiceType(4);
					per.setServiceRecord(typeName+"["+df.format(d)+"]"+"高于上限["+hold.getHigh()+"]报警");
					per.setInsertTime(dateFormat.format(new Date()));
					per.setServiceSubtitle(typeName);
					per.setTotaltype(1);
					yhAcceptserviceMapper.insertserviceplan(per);
					int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
					if(acc==0) {
						yhAcceptserviceMapper.inserttotalper(doctor.getUserCode(), userCode);
					}
					try {
						String s="异常报警"+"["+typeName+"]";
					    StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append("").append(request.getContextPath()).append("/messge/send");
					    SendGet.doPost(paths.toString(),"loginUserNum="+doctor.getUserCode()+"&content="+URLEncoder.encode(s,"utf-8"));
					} catch (UnsupportedEncodingException e) {
					    e.printStackTrace();
					}
				}else if(!hold.getLow().equals("") && hold.getLow()!=null && Double.valueOf(value)<Double.valueOf(hold.getLow())) {
					YhJzServicePerson per=new YhJzServicePerson();
					per.setUserCode(userCode);
					per.setServiceWaitingTime(dateStr);
					per.setGender(info.getGender());
					per.setPhone(info.getMobile());
					per.setAge(getAgeByBirth(info.getBirthDate()));
					per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
					per.setServiceType(4);
					per.setServiceRecord(typeName+"["+df.format(d)+"]"+"小于下限["+hold.getLow()+"]报警");
					per.setInsertTime(dateFormat.format(new Date()));
					per.setServiceSubtitle(typeName);
					per.setTotaltype(1);
					yhAcceptserviceMapper.insertserviceplan(per);
					int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
					if(acc==0) {
						yhAcceptserviceMapper.inserttotalper(doctor.getUserCode(), userCode);
					}
					try {
						String s="异常报警"+"["+typeName+"]";
					    StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append("").append(request.getContextPath()).append("/messge/send");
					    SendGet.doPost(paths.toString(),"loginUserNum="+doctor.getUserCode()+"&content="+URLEncoder.encode(s,"utf-8"));
					} catch (UnsupportedEncodingException e) {
					    e.printStackTrace();
					}
				}
			}else {
				
				if(type.equals("blood_sugar2")) {
					if((web.getBloodSugar2Min()!=null && !web.getBloodSugar2Min().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodSugar2Min())) {//餐后2小时血糖
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodSugar2Min(),doctor.getUserCode(),request);
					}
					if((web.getBloodSugar2Max()!=null && !web.getBloodSugar2Max().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodSugar2Max())) {//餐后2小时血糖
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodSugar2Max(),doctor.getUserCode(),request);
					}
				}
				
				
				if(type.equals("blood_pressure_sys")) {
					if((web.getBloodPressureSysMin()!=null && !web.getBloodPressureSysMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodPressureSysMin())) {//收缩压小
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodPressureSysMin(),doctor.getUserCode(),request);
					}
					if((web.getBloodPressureSysMax()!=null && !web.getBloodPressureSysMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodPressureSysMax())) {//收缩压大
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodPressureSysMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("temperature")) {
					if((web.getTemperatureMin()!=null && !web.getTemperatureMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getTemperatureMin())) {//小体温
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTemperatureMin(),doctor.getUserCode(),request);
					}
					if((web.getTemperatureMax()!=null && !web.getTemperatureMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getTemperatureMax())) {//大体温
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTemperatureMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("pulse")) {
					if((web.getPulseMin()!=null && !web.getPulseMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getPulseMin())) {//小心率
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getPulseMin(),doctor.getUserCode(),request);
					}
					if((web.getPulseMax()!=null && !web.getPulseMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getPulseMax())) {//大心率
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getPulseMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("blood_sugar")) {
					if((web.getBloodSugarMin()!=null && !web.getBloodSugarMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodSugarMin())) {//小血糖
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodSugarMin(),doctor.getUserCode(),request);
					}
					if((web.getBloodSugarMax()!=null && !web.getBloodSugarMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodSugarMax())) {//大血糖
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodSugarMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("blood_pressure_dia")) {
					if((web.getBloodPressureDiaMin()!=null && !web.getBloodPressureDiaMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodPressureDiaMin())) {//小舒张压
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodPressureDiaMin(),doctor.getUserCode(),request);
					}
					if((web.getBloodPressureDiaMax()!=null && !web.getBloodPressureDiaMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodPressureDiaMax())) {//大舒张压
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodPressureDiaMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("trig")) {
					if((web.getTrigMin()!=null && !web.getTrigMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getTrigMin())) {//小甘油三酯
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTrigMin(),doctor.getUserCode(),request);
					}
					if((web.getTrigMax()!=null && !web.getTrigMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getTrigMax())) {//大甘油三酯
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTrigMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("hdl-c")) {
					if((web.getHdlcMin()!=null && !web.getHdlcMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getHdlcMin())) {//小高密度蛋白胆固醇
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getHdlcMin(),doctor.getUserCode(),request);
					}
					if((web.getHdlcMax()!=null && !web.getHdlcMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getHdlcMax())) {//大高密度蛋白胆固醇
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getHdlcMax(),doctor.getUserCode(),request);
					}	
				}
				if(type.equals("ldl-c")) {
					if((web.getLdlcMin()!=null && !web.getLdlcMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getLdlcMin())) {//小低密度蛋白胆固醇	
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getLdlcMin(),doctor.getUserCode(),request);
					}
					if((web.getLdlcMax()!=null && !web.getLdlcMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getLdlcMax())) {//大低密度蛋白胆固醇	
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getLdlcMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("boold_oxygen")) {
					if((web.getBooldOxygenMin()!=null && !web.getBooldOxygenMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBooldOxygenMin())) {//小血氧
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBooldOxygenMin(),doctor.getUserCode(),request);
					}
					if((web.getBooldOxygenMax()!=null && !web.getBooldOxygenMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBooldOxygenMax())) {//大血氧
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBooldOxygenMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("BMI")) {
					if((web.getBmiMin()!=null && !web.getBmiMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBmiMin())) {//小BMI
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBmiMin(),doctor.getUserCode(),request);
					}
					if((web.getBmiMax()!=null && !web.getBmiMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBmiMax())) {//大BMI
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBmiMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("blood_muscle_anhydride")) {//男
					if(1==info.getGender()) {
						if((web.getBloodMuscleAnhydrideMin()!=null && !web.getBloodMuscleAnhydrideMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodMuscleAnhydrideMin())) {//小血肌酐
							addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName+"(男)",String.valueOf(value),web.getBloodMuscleAnhydrideMin(),doctor.getUserCode(),request);
						}
						if((web.getBloodMuscleAnhydrideMax()!=null && !web.getBloodMuscleAnhydrideMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodMuscleAnhydrideMax())) {//大血肌酐
							addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName+"(女)",String.valueOf(value),web.getBloodMuscleAnhydrideMax(),doctor.getUserCode(),request);
						}
					}else {
						if((web.getBloodMuscleAnhydrideMinGirl()!=null && !web.getBloodMuscleAnhydrideMinGirl().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodMuscleAnhydrideMinGirl())) {//小血肌酐
							addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName+"(男)",String.valueOf(value),web.getBloodMuscleAnhydrideMinGirl(),doctor.getUserCode(),request);
						}
						if((web.getBloodMuscleAnhydrideMaxGirl()!=null && !web.getBloodMuscleAnhydrideMaxGirl().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodMuscleAnhydrideMaxGirl())) {//大血肌酐
							addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName+"(女)",String.valueOf(value),web.getBloodMuscleAnhydrideMaxGirl(),doctor.getUserCode(),request);
						}
					}
					
				}
				
				
				
				if(type.equals("blood_urea_nitrogen")) {
					if((web.getBloodUreaNitrogenMin()!=null && !web.getBloodUreaNitrogenMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodUreaNitrogenMin())) {//小血尿素氮
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodUreaNitrogenMin(),doctor.getUserCode(),request);
					}
					if((web.getBloodUreaNitrogenMax()!=null && !web.getBloodUreaNitrogenMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodUreaNitrogenMax())) {//大血尿素氮
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getBloodUreaNitrogenMax(),doctor.getUserCode(),request);
					}
				}
				
				if(type.equals("blood_uric_acid")) {//男
					
					if(1==info.getGender()) {

						if((web.getBloodUricAcidMin()!=null && !web.getBloodUricAcidMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodUricAcidMin())) {//小血尿酸
							addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName+"(男)",String.valueOf(value),web.getBloodUricAcidMin(),doctor.getUserCode(),request);
						}
						if((web.getBloodUricAcidMax()!=null && !web.getBloodUricAcidMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodUricAcidMax())) {//大血尿酸
							addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName+"(男)",String.valueOf(value),web.getBloodUricAcidMax(),doctor.getUserCode(),request);
						}
					}else {
						if((web.getBloodUreaNitrogenMinGirl()!=null && !web.getBloodUreaNitrogenMinGirl().equals("")) && Double.valueOf(value)<Double.valueOf(web.getBloodUreaNitrogenMinGirl())) {//小血尿酸
							addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName+"(女)",String.valueOf(value),web.getBloodUreaNitrogenMinGirl(),doctor.getUserCode(),request);
						}
						if((web.getBloodUreaNitrogenMaxGirl()!=null && !web.getBloodUreaNitrogenMaxGirl().equals("")) && Double.valueOf(value)>Double.valueOf(web.getBloodUreaNitrogenMaxGirl())) {//大血尿酸
							addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName+"(女)",String.valueOf(value),web.getBloodUreaNitrogenMaxGirl(),doctor.getUserCode(),request);
						}
					}
					
				}
				
				
				 
				
				if(type.equals("alanine_transaminase")) {
					if((web.getAlanineTransaminaseMin()!=null && !web.getAlanineTransaminaseMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getAlanineTransaminaseMin())) {//小谷丙转氨酶
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getAlanineTransaminaseMin(),doctor.getUserCode(),request);
					}
					if((web.getAlanineTransaminaseMax()!=null && !web.getAlanineTransaminaseMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getAlanineTransaminaseMax())) {//大谷丙转氨酶
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getAlanineTransaminaseMax(),doctor.getUserCode(),request);
					}
				}
				
				if(type.equals("transaminase_millet_grass")) {
					if((web.getTransaminaseMilletGrassMin()!=null && !web.getTransaminaseMilletGrassMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getTransaminaseMilletGrassMin())) {//小谷草转氨酶
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTransaminaseMilletGrassMin(),doctor.getUserCode(),request);
					}
					if((web.getTransaminaseMilletGrassMax()!=null && !web.getTransaminaseMilletGrassMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getTransaminaseMilletGrassMax())) {//大谷草转氨酶
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTransaminaseMilletGrassMax(),doctor.getUserCode(),request);
					}
				}
				
				if(type.equals("serum_serum_protein")) {
					if((web.getSerumSerumProteinMin()!=null && !web.getSerumSerumProteinMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getSerumSerumProteinMin())) {//小血清白蛋白
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getSerumSerumProteinMin(),doctor.getUserCode(),request);
					}
					if((web.getSerumSerumProteinMax()!=null && !web.getSerumSerumProteinMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getSerumSerumProteinMax())) {//大血清白蛋白
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getSerumSerumProteinMax(),doctor.getUserCode(),request);
					}
				}
				
				if(type.equals("serum_globulin")) {
					if((web.getSerumGlobulinMin()!=null && !web.getSerumGlobulinMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getSerumGlobulinMin())) {//小血清球蛋白
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getSerumGlobulinMin(),doctor.getUserCode(),request);
					}
					if((web.getSerumGlobulinMax()!=null && !web.getSerumGlobulinMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getSerumGlobulinMax())) {//大血清球蛋白
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getSerumGlobulinMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("urinary_microprotein")) {
					if((web.getUrinaryMicroproteinMin()!=null && !web.getUrinaryMicroproteinMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getUrinaryMicroproteinMin())) {//小尿微量蛋白
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getUrinaryMicroproteinMin(),doctor.getUserCode(),request);
					}
					if((web.getUrinaryMicroproteinMax()!=null && !web.getUrinaryMicroproteinMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getUrinaryMicroproteinMax())) {//大尿微量蛋白
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getUrinaryMicroproteinMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("Hba1c_and_protein")) {
					if((web.getHba1cAndProteinMin()!=null && !web.getHba1cAndProteinMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getHba1cAndProteinMin())) {//小糖化血红蛋白
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getHba1cAndProteinMin(),doctor.getUserCode(),request);
					}
					if((web.getHba1cAndProteinMax()!=null && !web.getHba1cAndProteinMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getHba1cAndProteinMax())) {//大糖化血红蛋白
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getHba1cAndProteinMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("homocysteine")) {
					if((web.getHomocysteineMin()!=null && !web.getHomocysteineMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getHomocysteineMin())) {//小同型半胱氨酸
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getHomocysteineMin(),doctor.getUserCode(),request);
					}
					if((web.getHomocysteineMax()!=null && !web.getHomocysteineMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getHomocysteineMax())) {//大同型半胱氨酸
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getHomocysteineMax(),doctor.getUserCode(),request);
					}
				}
				if(type.equals("tc")) {
					if((web.getTcMin()!=null && !web.getTcMin().equals("")) && Double.valueOf(value)<Double.valueOf(web.getTcMin())) {//小总胆固醇
						addLow(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTcMin(),doctor.getUserCode(),request);
					}
					if((web.getTcMax()!=null && !web.getTcMax().equals("")) && Double.valueOf(value)>Double.valueOf(web.getTcMax())) {//大总胆固醇
						addhigh(userCode,info.getGender(),info.getMobile(),getAgeByBirth(info.getBirthDate()),typeName,String.valueOf(value),web.getTcMax(),doctor.getUserCode(),request);
					}
				}
				
			}
		}
		return null;
	}
	
	
	
	private YhJzServicePerson addhigh(String userCode,int gender,String mobile,int age,String typeName,String value,String high,String doctor, HttpServletRequest request) throws ParseException {
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		DecimalFormat df = new DecimalFormat("#.##");
		double d = Double.valueOf(value);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateStr = format.format(new Date());
	    format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	    Date date1 = format.parse(dateStr);
	    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	    dateStr = format.format(date1);
	    
		YhJzServicePerson per=new YhJzServicePerson();
		per.setUserCode(userCode);
		per.setGender(gender);
		per.setServiceWaitingTime(dateStr);
		per.setPhone(mobile);
		per.setAge(age);
		per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
		per.setServiceType(4);
		per.setServiceRecord(typeName+"["+df.format(d)+"]"+"高于上限["+high+"]报警");
		per.setInsertTime(dateFormat.format(new Date()));
		per.setServiceSubtitle(typeName);
		per.setTotaltype(1);
		yhAcceptserviceMapper.insertserviceplan(per);
		int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
		if(acc==0) {
			yhAcceptserviceMapper.inserttotalper(doctor, userCode);
		}
		try {
			String s="异常报警"+"["+typeName+"]";
		    StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append("").append(request.getContextPath()).append("/messge/send");
		    SendGet.doPost(paths.toString(),"loginUserNum="+doctor+"&content="+URLEncoder.encode(s,"utf-8"));
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		return per;
	}
	
	
	private YhJzServicePerson addLow(String userCode,int gender,String mobile,int age,String typeName,String value,String low,String doctor, HttpServletRequest request) throws ParseException {
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		DecimalFormat df = new DecimalFormat("#.##");
		double d = Double.valueOf(value);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateStr = format.format(new Date());
	    format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	    Date date1 = format.parse(dateStr);
	    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	    dateStr = format.format(date1);
	    
	    
		YhJzServicePerson per=new YhJzServicePerson();
		per.setUserCode(userCode);
		per.setServiceWaitingTime(dateStr);
		per.setGender(gender);
		per.setPhone(mobile);
		per.setAge(age);
		per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
		per.setServiceType(4);
		per.setServiceRecord(typeName+"["+df.format(d)+"]"+"小于下限["+low+"]报警");
		per.setInsertTime(dateFormat.format(new Date()));
		per.setServiceSubtitle(typeName);
		per.setTotaltype(1);
		yhAcceptserviceMapper.insertserviceplan(per);
		int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
		if(acc==0) {
			yhAcceptserviceMapper.inserttotalper(doctor, userCode);
		}
		try {
			String s="异常报警"+"["+typeName+"]";
		    StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append("").append(request.getContextPath()).append("/messge/send");
		    SendGet.doPost(paths.toString(),"loginUserNum="+doctor+"&content="+URLEncoder.encode(s,"utf-8"));
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		return per;
	}
	
	
	
	
	
	private YhJzServicePerson addhigh1(String userCode,int gender,String mobile,int age,String typeName,String value,String high,String doctor, HttpServletRequest request) throws ParseException {
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		DecimalFormat df = new DecimalFormat("#.##");
		double d = Double.valueOf(value);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateStr = format.format(new Date());
	    format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	    Date date1 = format.parse(dateStr);
	    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	    dateStr = format.format(date1);
	    
		YhJzServicePerson per=new YhJzServicePerson();
		per.setUserCode(userCode);
		per.setGender(gender);
		per.setServiceWaitingTime(dateStr);
		per.setPhone(mobile);
		per.setAge(age);
		per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
		per.setServiceType(4);
		per.setServiceRecord(typeName+"["+df.format(d)+"]"+"高于上限["+high+"]报警");
		per.setInsertTime(dateFormat.format(new Date()));
		per.setServiceSubtitle(typeName);
		per.setTotaltype(1);
		yhAcceptserviceMapper.insertserviceplan(per);
		int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
		if(acc==0) {
			yhAcceptserviceMapper.inserttotalper(doctor, userCode);
		}
		
		return per;
	}
	
	
	private YhJzServicePerson addLow1(String userCode,int gender,String mobile,int age,String typeName,String value,String low,String doctor, HttpServletRequest request) throws ParseException {
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		DecimalFormat df = new DecimalFormat("#.##");
		double d = Double.valueOf(value);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateStr = format.format(new Date());
	    format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	    Date date1 = format.parse(dateStr);
	    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	    dateStr = format.format(date1);
	    
	    
		YhJzServicePerson per=new YhJzServicePerson();
		per.setUserCode(userCode);
		per.setServiceWaitingTime(dateStr);
		per.setGender(gender);
		per.setPhone(mobile);
		per.setAge(age);
		per.setServiceContent("询问症状情况，找出产生异常的原因，如有必要，提醒用户尽快到医院就诊");
		per.setServiceType(4);
		per.setServiceRecord(typeName+"["+df.format(d)+"]"+"小于下限["+low+"]报警");
		per.setInsertTime(dateFormat.format(new Date()));
		per.setServiceSubtitle(typeName);
		per.setTotaltype(1);
		yhAcceptserviceMapper.insertserviceplan(per);
		int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
		if(acc==0) {
			yhAcceptserviceMapper.inserttotalper(doctor, userCode);
		}
		return per;
	}
	
	
	
	
	/**
	 * 根据出生算年龄
	 * 
	 * @param birthday
	 * @return
	 */
	private int getAgeByBirth(Date birthday) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间

			Calendar birth = Calendar.getInstance();
			birth.setTime(birthday);

			if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
					age += 1;
				}
			}
			return age;
		} catch (Exception e) {// 兼容性更强,异常后返回数据
			return 0;
		}
	}
	
	public static void main(String[] args) throws ParseException {
		String s="hahahhaha";
		String httpUrl="http://localhost//messge/send?loginUserNum=DA000086&content="+s;
		doGet(httpUrl);
}
	

	  /**
     * Http get请求
     * @param httpUrl 连接
     * @return 响应数据
     */
    public static String doGet(String httpUrl){
        //链接
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer result = new StringBuffer();
        try {
            //创建连接
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setReadTimeout(15000);
            //开始连接
            connection.connect();
            //获取响应数据
            if (connection.getResponseCode() == 200) {
                //获取返回的数据
                is = connection.getInputStream();
                if (null != is) {
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String temp = null;
                    while (null != (temp = br.readLine())) {
                        result.append(temp);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭远程连接
            connection.disconnect();
        }
        return result.toString();
    }

}