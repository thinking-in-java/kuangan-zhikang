package com.yihe.applet.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yihe.applet.api.domain.YhQuestionCheckJsonApp;
import com.yihe.applet.api.mapper.YhQuestionCheckJsonAppMapper;
import com.yihe.applet.api.util.common.util.Result;
import com.yihe.system.domain.YhSportRecommendRecord;
import com.yihe.system.domain.YhSportRecommendRecordVojo;
import com.yihe.system.domain.YhSportSetmeal;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhDiseaseAssessTotalReport2Mapper;
import com.yihe.system.mapper.YhSportSetmealMapper;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.service.YhSportRecommendRecordService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * YhSportRecommendRecordcontroller
 * Created by dong on 2020/3/9.
 */
@Controller
@RequestMapping("/api/YhSportRecommendRecord")
@Api(value = "YhSportRecommendRecord控制器", description = "YhSportRecommendRecord管理")
public class YhSportRecommendRecordController{

    private static final Logger LOGGER = LoggerFactory.getLogger(YhSportRecommendRecordController.class);
	
    @Autowired
    YhSportRecommendRecordService yhSportRecommendRecordService;

    @Autowired
    YhDiseaseAssessTotalReport2Mapper yhDiseaseAssessTotalReport2Mapper;
    
    @Autowired
    YhSportSetmealMapper yhSportSetmealMapper;
    
    @Autowired
    YhQuestionCheckJsonAppMapper yhQuestionCheckJsonAppMapper;
    
    @Autowired
   	YhUserReginfoMapper yhUserReginfoMapper;
    /**
     * 根据code查询运动推荐类型
     * @param userCode
     * @return
     */
	@RequestMapping(value = "/showSportRecommended")
	@ResponseBody
	public Result showSportRecommended(String userCode) {
		String illness="患有:";
		int hy=0;
		String notillness="";
		int jb=0;
		int count=yhDiseaseAssessTotalReport2Mapper.selectByreport(userCode);
		if(count==0) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("setmealMame", "健康健身");
			 List<YhSportSetmeal> list=yhSportSetmealMapper.selectSetmealshow(map);
			 for (YhSportSetmeal yhSportSetmeal : list) {
				 YhSportRecommendRecord reco=new YhSportRecommendRecord();
				 reco.setUserCode(userCode);
				 reco.setDiseaseid(String.valueOf(yhSportSetmeal.getDiseaseCode()));
				 reco.setSetmealid(String.valueOf(yhSportSetmeal.getId()));
				 reco.setRecordMode("系统推荐");
				 reco.setStandby("12");
				 reco.setSportName(yhSportSetmeal.getSetmealMame());
				 yhSportRecommendRecordService.insertSelective(reco);
			}
		}else {
			YhQuestionCheckJsonApp jsonapp=yhQuestionCheckJsonAppMapper.selectYhQuestionCheckJsonByUserCode(userCode, 1);
			  // 1.将JSON转成JSONObject对象
	         JSONObject createJsonObject = JSON.parseObject(jsonapp.getUserCheckAnswer().toString());
	         // 2.获取mydatas内容
	         String stroke_his = createJsonObject.getString("stroke_his");//脑卒中
	         // 3.将子集也转成JSONObject对象
	         JSONObject product = JSON.parseObject(stroke_his);
	         // 4.获取子集内容
	         String price = product.getString("disFlag");
	         if(price!=null && price.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="脑卒中,";
	         }
	         
	         String chd_his = createJsonObject.getString("chd_his");//冠心病
	         JSONObject product1 = JSON.parseObject(chd_his);
	         String price1 = product1.getString("disFlag");
	         if(price1!=null &&  price1.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="冠心病,";
	         }
	         
	         
	         String mi_his = createJsonObject.getString("mi_his");//严重心律失常
	         JSONObject product2 = JSON.parseObject(mi_his);
	         String price2 = product2.getString("disFlag");
	         if(price2!=null &&  price2.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="严重心律失常,";
	         }
	         
	         String angina_his = createJsonObject.getString("angina_his");//不稳定心绞痛
	         JSONObject product3 = JSON.parseObject(angina_his);
	         String price3 = product3.getString("disFlag");
	         if(price3!=null && price3.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="不稳定心绞痛,";
	         }
	         
	         String gae_his = createJsonObject.getString("gae_his");//痛风急性发作期
	         JSONObject product4 = JSON.parseObject(gae_his);
	         String price4 = product4.getString("disFlag");
	         if(price4!=null &&  price4.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="痛风急性发作期,";
	         }
	         
	         String arterial_status = createJsonObject.getString("arterial_status");//动脉硬化、狭窄、闭塞
	         if(arterial_status!=null && arterial_status.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="动脉硬化、狭窄、闭塞,";
	         }
	         
	         
	         String ace_his = createJsonObject.getString("ace_his");//其他急性心脏事件
	         JSONObject product5 = JSON.parseObject(ace_his);
	         String price5 = product5.getString("disFlag");
	         if(price5!=null && price5.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="其他急性心脏事件,";
	         }
	         
	         String af_his = createJsonObject.getString("af_his");//房颤
	         JSONObject product6 = JSON.parseObject(af_his);
	         String price6 = product6.getString("disFlag");
	         if(price6!=null && price6.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="房颤,";
	         }
	         
	         String lc_his = createJsonObject.getString("lc_his");//肺癌
	         JSONObject product7 = JSON.parseObject(lc_his);
	         String price7 = product7.getString("disFlag");
	         if(price7!=null && price7.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="肺癌,";
	         }
	         
	         
	         String emphy_his = createJsonObject.getString("emphy_his");//肺气肿
	         JSONObject product8 = JSON.parseObject(emphy_his);
	         String price8 = product8.getString("disFlag");
	         if(price8!=null && price8.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="肺气肿,";
	         }
	         
	         String decline_his = createJsonObject.getString("decline_his");//肺结核
	         JSONObject product9 = JSON.parseObject(decline_his);
	         String price9 = product9.getString("disFlag");
	         if(price9!=null && price9.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="肺结核,";
	         }
	         
	         String sport_his = createJsonObject.getString("sport_his");//其他限制运动的疾病（如关节炎、足部溃疡、肌肉韧带损伤等）
	         JSONObject product10 = JSON.parseObject(sport_his);
	         String price10 = product10.getString("disFlag");
	         if(price10!=null && price10.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="其他限制运动的疾病（如关节炎、足部溃疡、肌肉韧带损伤等）,";
	         }
	         
	         String secondary_op_his = createJsonObject.getString("secondary_op_his");//引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、甲状旁腺功能亢进症、库欣综合征、慢性营养不良等）
	         JSONObject product11 = JSON.parseObject(secondary_op_his);
	         String price11 = product11.getString("disFlag");
	         if(price11!=null && price11.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、甲状旁腺功能亢进症、库欣综合征、慢性营养不良等）,";
	         }
	         
	         String ushf_his = createJsonObject.getString("ushf_his");//未控制的有症状的心率衰竭
	         JSONObject product12 = JSON.parseObject(ushf_his);
	         String price12 = product12.getString("disFlag");
	         if(price12!=null && price12.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="未控制的有症状的心率衰竭,";
	         }
	         
	         String fracture_his = createJsonObject.getString("fracture_his");//既往骨折
	         JSONObject product13 = JSON.parseObject(fracture_his);
	         String price13 = product13.getString("disFlag");
	         
	         String op_his = createJsonObject.getString("op_his");//骨质疏松症
	         JSONObject product14 = JSON.parseObject(op_his);
	         String price14 = product14.getString("disFlag");
	         
	         
	         if(price13!=null && price13.equals("1") || price14!=null && price14.equals("1")) {
	        	 jb+=1;
	        	 hy+=1;
	        	 illness+="骨质疏松加既往骨折,";
	         }
	         
	         
	         YhUserReginfo info=yhUserReginfoMapper.selectByPrimaryKey(userCode);//查询用户表
	         
	         if(1==info.getGender()) {//男
	        	 String serum_creatinine = createJsonObject.getString("serum_creatinine");//血尿酸
		         if(serum_creatinine!=null && !serum_creatinine.equals("")  && Double.valueOf(serum_creatinine) >=600) {
		        	 jb+=1;
		        	 notillness+="血尿酸大于等于600μmol/L,";
		         }
	        	 
	        	 
				}else {//女
					
					String serum_creatinine = createJsonObject.getString("serum_creatinine");//血尿酸
			         if(serum_creatinine!=null && !serum_creatinine.equals("")  && Double.valueOf(serum_creatinine) >=540) {
			        	 jb+=1;
			        	 notillness+="血尿酸大于等于540μmol/L,";
			         }
			         
			         
			         String female_birth = createJsonObject.getString("female_birth");//产后三个月内
			         if(female_birth!=null && !female_birth.equals("") && female_birth.equals("1")) {
			        	 jb+=1;
			        	 notillness+="正在产后三个月内,";
			         }
			         
			         
					 String female_gestation = createJsonObject.getString("female_gestation");//您是否处于妊娠期？
			         if(female_gestation!=null && !female_gestation.equals("") && female_gestation.equals("1")) {
			        	 jb+=1;
			        	 notillness+="正在妊娠期,";
			         }
				}
				
	         
	         String serum_creatinine = createJsonObject.getString("serum_creatinine");//血肌酐
	         if(serum_creatinine!=null && !serum_creatinine.equals("") && Double.valueOf(serum_creatinine) >=177) {
	        	 jb+=1;
	        	 notillness+="血肌酐大于等于177 μmol/L,";
	         }
	         
	         
	         String fbg = createJsonObject.getString("fbg");//空腹血糖
	         if(fbg!=null && !fbg.equals("") && Double.valueOf(fbg) >=14) {
	        	 jb+=1;
	        	 notillness+="空腹血糖大于等于14mmol/L,";
	         }
	         
	         String sbp = createJsonObject.getString("sbp");//收缩压
	         if(sbp!=null && !sbp.equals("") && Integer.valueOf(sbp) >=180) {
	        	 jb+=1;
	        	 notillness+="收缩压大于等于180mmHg,";
	         }
	         
	         String dbp = createJsonObject.getString("dbp");//舒张压
	         if(dbp!=null && !dbp.equals("") && Integer.valueOf(dbp) >=120) {
	        	 jb+=1;
	        	 notillness+="舒张压大于等于120mmHg,";
	         }
	         
	         
	         
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		List<YhSportRecommendRecordVojo> list=null;
		map.put("jb", jb);
		if(jb==0) {
			list=yhSportRecommendRecordService.showSportRecommended(userCode);
			if(hy>0) {
				map.put("illness", notillness+illness);
			}else {
				map.put("illness", notillness);
			}
			
			map.put("list", list);
		}else {
			map.put("list", list);
			if(hy>0) {
				map.put("illness", notillness+illness);
			}else {
				map.put("illness", notillness);
			}
		}
		
		return Result.ok(map,Long.valueOf(count));
	}

    public static void main(String[] args) {
    	 String createData="{\"mydatas\":{\"price\":10,\"pname\":\"f1\",\"weight\":20,66:88}}";
         System.err.println("getJsonKey:"+createData);
         // 1.将JSON转成JSONObject对象
         JSONObject createJsonObject = JSON.parseObject(createData.toString());
         // 2.获取mydatas内容
         String mydatas = createJsonObject.getString("mydatas");
         System.out.println("JSON:"+mydatas);

         // 3.将子集也转成JSONObject对象
         JSONObject product = JSON.parseObject(mydatas);
         // 4.获取子集内容
         String price = product.getString("price");
         String pname = product.getString("pname");
         String weight = product.getString("weight");
         String m66 = product.getString("66");
         System.out.print("price:"+price);
         System.out.print("    pname:"+pname);
         System.out.print("    weight:"+weight);
         System.out.println("    66:"+m66);
	}

}