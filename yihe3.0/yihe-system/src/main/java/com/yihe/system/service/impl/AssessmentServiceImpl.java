package com.yihe.system.service.impl;

import com.yihe.system.domain.YhUserPersonageCondition;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.AssessmentMapper;
import com.yihe.system.mapper.YhUserPersonageConditionMapper;
import com.yihe.system.service.AssessmentService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.AsyncTask;
import com.yihe.system.utils.ExcelUtil;
import com.yihe.system.utils.HttpUtils;
import com.yihe.system.utils.StringUtil;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Transactional
@Service
public class AssessmentServiceImpl implements AssessmentService {
    @Autowired
    AssessmentMapper assessmentmapper;
    @Autowired
    AsyncTask task;
    @Autowired
    HttpUtils httpUtils;
    @Autowired
    YhUserReginfoService yhUserReginfoService;
    @Autowired
    YhUserPersonageConditionMapper yhUserPersonageConditionMapper;
    @Override
    public Map<String, Object> ajaxUploadExcel(InputStream in, MultipartFile file, HttpServletRequest request) throws Exception {
       StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append(request.getServerPort() == 80 ? "" : request.getServerPort()).append(request.getContextPath()).append("/applet/api/questionAssessTitleFront/doDMRisk");
       //StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append(request.getServerPort() == 80 ? "" : request.getServerPort()).append(request.getContextPath()).append(":8088/api/questionAssessTitleFront/doDMRisk");
        Map<String,Object> result = new HashMap<String, Object>();
        //获取excel数据

        List<List<Object>> listob = ExcelUtil.getBankListByExcel(in, file.getOriginalFilename());
        for (int k=4;k<listob.size();k++){
           if(!isValidDate(listob.get(k).get(3).toString())){
               result.put("code", "500");
               result.put("msg", "第"+(k+2)+"行,第"+(4)+"列格式不是日期格式");
               return result;
           }
           if(!(listob.get(k).get(1).toString().equals("1")||listob.get(k).get(1).toString().equals("2"))){
               result.put("code", "500");
               result.put("msg", "第"+(k+2)+"行,第"+(2)+"列格式不正确");
               return result;
           }
            for (int h=4;h<129;h++){
                if(h!=113&&h!=109&&h!=108&&h!=107&&h!=112&&h!=111&&h!=38&&h!=37&&h!=35&&h!=34&&h!=32&&h!=31&&h!=30&&h!=29&&h!=70&&h!=69&&h!=68&&
                        h!=116&&h!=115&&h!=114&&h!=123&&h!=124&&h!=129&&h!=66&&h!=65&&h!=64&&h!=93&&h!=94&&h!=95&&h!=62&&
                        h!=63&&h!=110&&h!=5 &&h<119&&!isInt(listob.get(k).get(h).toString())){
                    result.put("code", "500");
                    result.put("msg", "第"+(k+2)+"行,第"+(h+1)+"列格式不正确");
                    return result;
                }
                if((h==5 || h>=119)&&h!=123&&h!=124&&h!=129&&!isInteger(listob.get(k).get(h).toString())){
                    result.put("code", "500");
                    result.put("msg", "第"+(k+2)+"行,第"+(h+1)+"列格式不正确");
                    return result;
                }
                if(k>53){
                    result.put("code", "500");
                    result.put("msg", "一次最多不能超过50条");
                    return result;
                }
            }
        }


       System.out.println(listob);
        int hh=1;
        for (int i=4;i<listob.size();i++){
           String kaitou=" 已成功导入"+(hh-1)+"条数据";
           String jiewei="导入已中止";

            YhUserReginfo YhUserReginfo=new YhUserReginfo();
            YhUserReginfo.setBirthDate(parse(listob.get(i).get(3).toString()));
            YhUserReginfo.setGender(Integer.parseInt( listob.get(i).get(1).toString()));
            if(listob.get(i).get(1).toString().equals("1")){
                YhUserReginfo.setEdBackground("小学及以下");
            }else if(listob.get(i).get(1).toString().equals("2")){
                YhUserReginfo.setEdBackground("初中");
            }else if(listob.get(i).get(1).toString().equals("3")){
                YhUserReginfo.setEdBackground("高中、中专或技校");
            }else if(listob.get(i).get(1).toString().equals("4")){
                YhUserReginfo.setEdBackground("本科");
            }else if(listob.get(i).get(1).toString().equals("5")){
                YhUserReginfo.setEdBackground("本科以上");
            }
            BigDecimal bd1 = new BigDecimal(Double.valueOf(listob.get(i).get(2).toString()));
            YhUserReginfo.setUserCode(assessmentmapper.selectusercode(bd1.toPlainString()));
            YhUserPersonageCondition record=new YhUserPersonageCondition();

            record.setUserCode(assessmentmapper.selectusercode(bd1.toPlainString()));
            record.setStature(listob.get(i).get(4).toString());
            record.setWeight(listob.get(i).get(5).toString());

            HashMap<String,Object> allperson=new HashMap<String,Object>();
            HashMap<String,Object> person=new HashMap<String,Object>();
            //高血压
            HashMap<String,Object> hbp_his=new HashMap<String,Object>();
            //脑卒中
            HashMap<String,Object> stroke_his=new HashMap<String,Object>();
            //冠心病
            HashMap<String,Object> chd_his=new HashMap<String,Object>();
            //心律失常
            HashMap<String,Object> mi_his=new HashMap<String,Object>();
            //绞痛
            HashMap<String,Object> angina_his=new HashMap<String,Object>();

            //心律衰竭
            HashMap<String,Object> ushf_his=new HashMap<String,Object>();
            //左心室肥大
            HashMap<String,Object> lvd_his=new HashMap<String,Object>();
            //您是否患过房颤
            HashMap<String,Object> af_his=new HashMap<String,Object>();
            //急性心脏事件
            HashMap<String,Object> ace_his=new HashMap<String,Object>();
            //糖尿病
            HashMap<String,Object> diab1_his=new HashMap<String,Object>();
            //痛风急性发作期
            HashMap<String,Object> gae_his=new HashMap<String,Object>();
            //非急性发作期
            HashMap<String,Object> gna_his=new HashMap<String,Object>();
            //慢性支气管炎
            HashMap<String,Object> bronch_his=new HashMap<String,Object>();
            //肺气肿
            HashMap<String,Object> emphy_his=new HashMap<String,Object>();
            //肺结核
            HashMap<String,Object> decline_his=new HashMap<String,Object>();
            //肺癌
            HashMap<String,Object> lc_his=new HashMap<String,Object>();
            //骨质疏松症
            HashMap<String,Object> op_his=new HashMap<String,Object>();
            //既往骨折
            HashMap<String,Object> fracture_his=new HashMap<String,Object>();
            //限制运动的疾病
            HashMap<String,Object> sport_his=new HashMap<String,Object>();
            //继发性骨质疏松症的疾病
            HashMap<String,Object> secondary_op_his=new HashMap<String,Object>();
            //乳腺癌
            HashMap<String,Object> bc_his=new HashMap<String,Object>();
            //类风湿关节炎
            HashMap<String,Object> ra_his=new HashMap<String,Object>();
            //吸烟
            HashMap<String,Object> smoke=new HashMap<String,Object>();
            //油烟接触史
            HashMap<String,Object> lampblack_contact=new HashMap<String,Object>();
            //粉尘接触史
            HashMap<String,Object> dust_exposure_his=new HashMap<String,Object>();
            //石棉接触史
            HashMap<String,Object> asbestos_contact_his=new HashMap<String,Object>();

            HashMap<String,Object> diab2_his=new HashMap<String,Object>();

            HashMap<String,Object> hw_check=new HashMap<String,Object>();

            HashMap<String,Object> pc_his=new HashMap<String,Object>();
            HashMap<String,Object> prostate_disease=new HashMap<String,Object>();
            HashMap<String,Object> bf_his=new HashMap<String,Object>();
            HashMap<String,Object> hua_his=new HashMap<String,Object>();
            HashMap<String,Object> fl_his=new HashMap<String,Object>();
            HashMap<String,Object> begind_breast_his=new HashMap<String,Object>();
            HashMap<String,Object> cancer_his=new HashMap<String,Object>();
            HashMap<String,Object> active_his=new HashMap<String,Object>();
            HashMap<String,Object> arrhythmia_his=new HashMap<String,Object>();

            if(assessmentmapper.selectusercode(bd1.toPlainString())==null || assessmentmapper.selectusercode(bd1.toPlainString()) .equals("")){
                result.put("code", "500");
                result.put("msg", kaitou+",第"+(i+2)+"行手机号码不正确,"+jiewei);
                return result;
            }
            if(assessmentmapper.selectgender(bd1.toPlainString())!=Integer.parseInt(listob.get(i).get(1).toString())){
                result.put("code", "500");
                result.put("msg", kaitou+",第"+(i+2)+"行性别信息错误,"+jiewei);
                return result;
            }
            person.put("id",assessmentmapper.selectusercode(bd1.toPlainString()));
            person.put("name",listob.get(i).get(0));
            person.put("sex",listob.get(i).get(1));
            Integer Sex=Integer.parseInt( listob.get(i).get(1).toString());
            person.put("age", getAge(parse(listob.get(i).get(3).toString())) );
            person.put("hipline",listob.get(i).get(7));
            person.put("education_levels",listob.get(i).get(8));
            int jzd=Integer.parseInt(listob.get(i).get(10).toString());
            if(jzd<=2){
                person.put("jzd",1);
                person.put("jzhj",jzd==2? 2:1);
            }else{
                person.put("jzd",2);
                person.put("jzhj",jzd==4? 2:1);
            }
            person.put("living_envir",listob.get(i).get(12));
            hbp_his.put("disFlag",listob.get(i).get(40));
            stroke_his.put("disFlag",listob.get(i).get(41));
            chd_his.put("disFlag",listob.get(i).get(42));
            mi_his.put("disFlag",listob.get(i).get(43));
            ushf_his.put("disFlag",listob.get(i).get(46));
            lvd_his.put("disFlag",listob.get(i).get(47));
            af_his.put("disFlag",listob.get(i).get(48));
            ace_his.put("disFlag",listob.get(i).get(49));
            diab1_his.put("disFlag",listob.get(i).get(50));
            gae_his.put("disFlag",listob.get(i).get(51));
            gna_his.put("disFlag",listob.get(i).get(52));
            bronch_his.put("disFlag",listob.get(i).get(53));
            emphy_his.put("disFlag",listob.get(i).get(54));
            decline_his.put("disFlag",listob.get(i).get(55));
            lc_his.put("disFlag",listob.get(i).get(56));
            arrhythmia_his.put("disFlag",0);
            op_his.put("disFlag",listob.get(i).get(57));
            fracture_his.put("disFlag",listob.get(i).get(58));
            sport_his.put("disFlag",listob.get(i).get(60));
            secondary_op_his.put("disFlag",listob.get(i).get(61));
            bc_his.put("disFlag",listob.get(i).get(67));
            ra_his.put("disFlag",listob.get(i).get(59));
            angina_his.put("disFlag",listob.get(i).get(44));
            allperson.put("weight",listob.get(i).get(5));
            allperson.put("height",listob.get(i).get(4));
            allperson.put("waist",listob.get(i).get(6));
            allperson.put("activity",listob.get(i).get(9));
            allperson.put("pm25",listob.get(i).get(11));
            allperson.put("arterial_status",listob.get(i).get(45));

            System.out.println(listob.get(i).get(67));
            if(Sex==1&&(listob.get(i).get(111)!=""||listob.get(i).get(112)!="" || listob.get(i).get(107)!="" || listob.get(i).get(108)!="" || listob.get(i).get(109)!="" ||
                    listob.get(i).get(113)!=""  || listob.get(i).get(114)!=""  || listob.get(i).get(115)!=""  || listob.get(i).get(116)!=""  || listob.get(i).get(68)!="" ||
                    listob.get(i).get(69)!="" || listob.get(i).get(70)!=""|| listob.get(i).get(110)!="" )){
                result.put("code", "500");
                result.put("msg", kaitou+",请检查第"+(i+2)+"行,第(69-71)(108-117)列为女性选项,男性不能填此项,"+jiewei);
                return result;
            }
            if(Sex==2&&(listob.get(i).get(111)==""||listob.get(i).get(112)==""||listob.get(i).get(107)=="" ||  listob.get(i).get(113)=="" || listob.get(i).get(68)=="" ||
                    listob.get(i).get(69)=="" || listob.get(i).get(70)==""|| listob.get(i).get(110)==""  )){
                result.put("code", "500");
                result.put("msg", kaitou+",请检查第"+(i+2)+"行,第(69,70,71,108,112,113,114,111)列为女性选项,女性为必填项,"+jiewei);
                return result;
            }

            if((listob.get(i).get(107).toString().equals("3"))&&(listob.get(i).get(108)!="" || listob.get(i).get(109)!="" )){
                result.put("code", "500");
                result.put("msg", kaitou+",请检查第"+(i+2)+"行,第108-110列，您还未生育过,"+jiewei);
                return result;
            }
            if((listob.get(i).get(107).toString().equals("1") || (listob.get(i).get(107).toString().equals("2")))&&  ((listob.get(i).get(108)=="" || listob.get(i).get(109)=="" ))){
                result.put("code", "500");
                result.put("msg", kaitou+",请检查第"+(i+2)+"行,第108-110列，您已经生育过,"+jiewei);
                return result;
            }
            if((listob.get(i).get(113).toString().equals("2"))&&(listob.get(i).get(114)!="" || listob.get(i).get(115)!="")){
                result.put("code", "500");
                result.put("msg", kaitou+",请检查第"+(i+2)+"行第(114-116)列，您还未绝经,"+jiewei);
                return result;
            }
            if((listob.get(i).get(113).toString().equals("1"))&&(listob.get(i).get(114)=="" || listob.get(i).get(115)=="")){
                result.put("code", "500");
                result.put("msg", kaitou+",请检查第"+(i+2)+"行第(114-116)列，您已经绝经,"+jiewei);
                return result;
            }

            if(((listob.get(i).get(116).toString()).equals("3"))){
                allperson.put("has_med_ert","2");
            }else if(((listob.get(i).get(116).toString()).equals("1")) || (((listob.get(i).get(116).toString()).equals("2")))){
                allperson.put("has_med_ert","1");
            }else{
                allperson.put("has_med_ert","");
            }


            if(((listob.get(i).get(28).toString()).equals("2"))&&((listob.get(i).get(29)!="")||(listob.get(i).get(30)!="")||(listob.get(i).get(31)!=""))){
                result.put("code", "500");
                result.put("msg", kaitou+",第"+(i+2)+"行,第(30-32)列不用填写，您未吸烟,"+jiewei);
                return result;
            }
            if(((listob.get(i).get(28).toString()).equals("3"))&&((listob.get(i).get(30)!=""))){
                result.put("code", "500");
                result.put("msg", kaitou+",第"+(i+2)+"行,第(31)列不用填写，您已经戒烟,"+jiewei);
                return result;
            }
//            if(((listob.get(i).get(92).toString()).equals("2"))&&((listob.get(i).get(93)=="")|| (listob.get(i).get(95)==""))){
//                result.put("code", "500");
//                result.put("msg", kaitou+",请填写第"+(i+2)+"行,第(94，96)列,"+jiewei);
//                return result;
//            }

            if(((listob.get(i).get(28).toString()).equals("1"))&&((listob.get(i).get(31)!=""))){
                result.put("code", "500");
                result.put("msg", kaitou+",第"+(i+2)+"行,第(32)列不用填写，您还未戒烟,"+jiewei);
                return result;
            }

            if(((listob.get(i).get(28).toString()).equals("1"))&&((listob.get(i).get(29)=="")||(listob.get(i).get(30)==""))){
                result.put("code", "500");
                result.put("msg", kaitou+",第"+(i+2)+"行,第(30-31)列不能为空，因为您正在吸烟,"+jiewei);
                return result;
            }

            if(((listob.get(i).get(33).toString()).equals("2"))&&((listob.get(i).get(34))!="" || (listob.get(i).get(35))!="")){
                result.put("code", "500");
                result.put("msg", kaitou+",请检查第"+(i+2)+"行,第(35-36)列，您选择的是不喝酒,"+jiewei);
                return result;
            }

            if(((listob.get(i).get(33).toString()).equals("1"))&&((listob.get(i).get(34))=="" || (listob.get(i).get(35))=="")){
                result.put("code", "500");
                result.put("msg", kaitou+",请检查第"+(i+2)+"行,第(35-36)列，您选择的是喝酒，请检查是否填写次数,"+jiewei);
                return result;
            }
            if(((listob.get(i).get(36).toString()).equals("2"))&&((listob.get(i).get(37))!="" || (listob.get(i).get(38))!="")){
                result.put("code", "500");
                result.put("msg", kaitou+",请检查第"+(i+2)+"行,第(38-39)列，您没有参加过运动,"+jiewei);
                return result;
            }
            if(((listob.get(i).get(36).toString()).equals("1"))&&((listob.get(i).get(37))=="" || (listob.get(i).get(38))=="")){
                result.put("code", "500");
                result.put("msg", kaitou+",请检查第"+(i+2)+"行,第(38-39)列，您参加过运动，但是没填次数,"+jiewei);
                return result;
            }

            if(Sex==1){
                if((listob.get(i).get(66)=="")||(listob.get(i).get(65)=="")||(listob.get(i).get(64)=="")||(listob.get(i).get(93)=="")||(listob.get(i).get(94)=="")||(listob.get(i).get(95)=="")||(listob.get(i).get(62)=="")||(listob.get(i).get(63)=="")){
                    result.put("code", "500");
                    result.put("msg", kaitou+",请检查第"+(i+2)+"行,第(63-67)(94-96)列，该项为男性必填题,"+jiewei);

                    return result;
                }else{
                    prostate_disease.put("disFlag",listob.get(i).get(62));
                    pc_his.put("disFlag",listob.get(i).get(63));
                }
            }else if(Sex==2){
                if((listob.get(i).get(66)!="")||(listob.get(i).get(65)!="")||(listob.get(i).get(64)!="")||(listob.get(i).get(93)!="")||(listob.get(i).get(94)!="")||(listob.get(i).get(95)!="")||(listob.get(i).get(62)!="")||(listob.get(i).get(63)!="")){
                    result.put("code", "500");
                    result.put("msg", kaitou+",请检查第"+(i+2)+"行,第(63-67)(94-96)列，该项女性不能填写,"+jiewei);
                    return result;
                }
                pc_his.put("disFlag","2");
                prostate_disease.put("disFlag","2");
            }
            allperson.put("female_menstruation",listob.get(i).get(111));
            allperson.put("female_menstruation_regular",listob.get(i).get(112));
            allperson.put("female_birth",listob.get(i).get(107));
            allperson.put("female_tire",listob.get(i).get(108));


            allperson.put("female_breast_time",listob.get(i).get(113));
            if(listob.get(i).get(109).equals("1")||listob.get(i).get(109).equals("2")){
                allperson.put("female_breast",1);
                allperson.put("female_breast_time",listob.get(i).get(109));
            }else  if(listob.get(i).get(109).equals("3")){
                allperson.put("female_breast",2);
                allperson.put("female_breast_time","");
            }else{
                allperson.put("female_breast","");
                allperson.put("female_breast_time","");
            }
            allperson.put("female_menopause",listob.get(i).get(113));
            allperson.put("female_menopause_age",listob.get(i).get(114));
            allperson.put("weight_up",listob.get(i).get(115));
            allperson.put("med_ert",listob.get(i).get(116));
            allperson.put("med_use_pill",listob.get(i).get(68));
            allperson.put("breast_plastic_surgery",listob.get(i).get(69));
            allperson.put("gynecology_operation",listob.get(i).get(70));
            allperson.put("family_diab_f",listob.get(i).get(73));
            allperson.put("family_diab_m",listob.get(i).get(74));
            allperson.put("family_diab_bs",listob.get(i).get(75));
            allperson.put("family_diab_ch",listob.get(i).get(76));
            allperson.put("family_hbp_f",listob.get(i).get(77));
            allperson.put("family_hbp_m",listob.get(i).get(78));
            allperson.put("family_hbp_bs",listob.get(i).get(79));
            allperson.put("family_hbp_ch",listob.get(i).get(80));
            allperson.put("family_chd_f",listob.get(i).get(81));
            allperson.put("family_chd_m",listob.get(i).get(82));
            allperson.put("family_chd_bs",listob.get(i).get(83));
            allperson.put("family_chd_ch",listob.get(i).get(84));
            allperson.put("family_stroke_f",listob.get(i).get(85));
            allperson.put("family_stroke_m",listob.get(i).get(86));
            allperson.put("family_stroke_bs",listob.get(i).get(87));
            allperson.put("family_stroke_ch",listob.get(i).get(88));
            allperson.put("family_hf_f",listob.get(i).get(103));
            allperson.put("family_hf_m",listob.get(i).get(104));
            allperson.put("family_hf_bs",listob.get(i).get(105));
            allperson.put("family_hf_ch",listob.get(i).get(106));
            allperson.put("family_lung_f",listob.get(i).get(89));
            allperson.put("family_lung_m",listob.get(i).get(90));
            allperson.put("family_lung_bs",listob.get(i).get(91));
            allperson.put("family_lung_ch",listob.get(i).get(92));
            allperson.put("family_breast_m",listob.get(i).get(96));
            allperson.put("family_breast_s",listob.get(i).get(97));
            allperson.put("family_breast_d",listob.get(i).get(98));
            allperson.put("family_gout_f",listob.get(i).get(99));
            allperson.put("family_gout_m",listob.get(i).get(100));
            allperson.put("family_gout_bs",listob.get(i).get(101));
            allperson.put("family_gout_ch",listob.get(i).get(102));
            allperson.put("med_hbp",listob.get(i).get(71));
            allperson.put("med_ach",listob.get(i).get(72));
            allperson.put("food_vegetables",listob.get(i).get(18));
            allperson.put("food_fruits",listob.get(i).get(19));
            allperson.put("food_milk",listob.get(i).get(20));
            allperson.put("food_dairy",listob.get(i).get(21));
            allperson.put("food_fired",listob.get(i).get(22));
            allperson.put("food_sweet",listob.get(i).get(23));
            allperson.put("food_sugary",listob.get(i).get(24));
            allperson.put("food_pickled",listob.get(i).get(25));
            allperson.put("seafood_cnt",listob.get(i).get(26));
            allperson.put("drink_tea_cnt",listob.get(i).get(27));
            hw_check.put("xt_exception",0);
            smoke.put("disFlag",listob.get(i).get(28));
            allperson.put("female_gestation",listob.get(i).get(110));

            bf_his.put("disFlag","2");
            hua_his.put("disFlag","2");
            fl_his.put("disFlag","2");
            begind_breast_his.put("disFlag","2");
            cancer_his.put("disFlag","2");
            active_his.put("disFlag","2");
            allperson.put("arrhythmia_his",arrhythmia_his);
            allperson.put("smoke_begin_age",listob.get(i).get(29));
            allperson.put("smoke_amount",listob.get(i).get(30));
            allperson.put("smoke_end_age",listob.get(i).get(31));
            allperson.put("smoke_sencod",listob.get(i).get(32));
            allperson.put("food_drink",listob.get(i).get(33));
            allperson.put("food_drink_times",listob.get(i).get(34));
            allperson.put("food_drink_beer",listob.get(i).get(35));
            allperson.put("sport_in",listob.get(i).get(36));
            allperson.put("sport_freq",listob.get(i).get(37));
            allperson.put("sport_time",listob.get(i).get(38));
            allperson.put("decorate_house",listob.get(i).get(13));
            lampblack_contact.put("disFlag",listob.get(i).get(14));
            allperson.put("chemical_occupation",listob.get(i).get(15));
            dust_exposure_his.put("disFlag",listob.get(i).get(16));
            asbestos_contact_his.put("disFlag",listob.get(i).get(17));
            allperson.put("psy_nervous",listob.get(i).get(118));
            allperson.put("bedtime",listob.get(i).get(39));
            person.put("income",listob.get(i).get(117));
            allperson.put("cholesterol",listob.get(i).get(119));
            allperson.put("tg",listob.get(i).get(120));
            allperson.put("hdlc",listob.get(i).get(121));
            allperson.put("ldlc",listob.get(i).get(122));
            allperson.put("serum_creatinine",listob.get(i).get(123));
            allperson.put("bua",listob.get(i).get(124));
            allperson.put("fbg",listob.get(i).get(125));
            allperson.put("sbp",listob.get(i).get(126));
            allperson.put("dbp",listob.get(i).get(127));
            allperson.put("wbc",listob.get(i).get(128));
            allperson.put("t_score",listob.get(i).get(129));
            allperson.put("hematuresis",listob.get(i).get(66));
            allperson.put("urine_symptom",listob.get(i).get(65));
            allperson.put("urinary_tract_infection",listob.get(i).get(64));
            allperson.put("family_pro_f",listob.get(i).get(93));
            allperson.put("family_pro_b",listob.get(i).get(94));
            allperson.put("family_pro_s",listob.get(i).get(95));
            diab2_his.put("disFlag",2);
            person.put("occupation",0);

            allperson.put("food_drink_wine","1");
            allperson.put("food_drink_spirit","1");
            allperson.put("hazardous_exposure_his","0");
            allperson.put("diseaseid","");
            allperson.put("serialNum","");
            allperson.put("food_heat","1");
            allperson.put("smoke_status",listob.get(i).get(28));
            allperson.put("med_use","");


            allperson.put("hw_check",hw_check);
            allperson.put("pc_his",pc_his);
            allperson.put("prostate_disease",prostate_disease);
            allperson.put("bf_his",bf_his);
            allperson.put("hua_his",hua_his);
            allperson.put("fl_his",fl_his);
            allperson.put("begind_breast_his",begind_breast_his);
            allperson.put("cancer_his",cancer_his);
            allperson.put("active_his",active_his);
            allperson.put("diab2_his",diab2_his);
            allperson.put("person",person);
            allperson.put("smoke",smoke);
            allperson.put("asbestos_contact_his",asbestos_contact_his);
            allperson.put("dust_exposure_his",dust_exposure_his);
            allperson.put("lampblack_contact",lampblack_contact);
            allperson.put("hbp_his",hbp_his);
            allperson.put("stroke_his",stroke_his);
            allperson.put("chd_his",chd_his);
            allperson.put("mi_his",mi_his);
            allperson.put("angina_his",angina_his);
            allperson.put("ushf_his",ushf_his);
            allperson.put("lvd_his",lvd_his);
            allperson.put("af_his",af_his);
            allperson.put("ace_his",ace_his);
            allperson.put("diab1_his",diab1_his);
            allperson.put("gae_his",gae_his);
            allperson.put("gna_his",gna_his);
            allperson.put("bronch_his",bronch_his);
            allperson.put("decline_his",decline_his);
            allperson.put("emphy_his",emphy_his);
            allperson.put("lc_his",lc_his);
            allperson.put("op_his",op_his);
            allperson.put("ra_his",ra_his);
            allperson.put("fracture_his",fracture_his);
            allperson.put("sport_his",sport_his);
            allperson.put("secondary_op_his",secondary_op_his);
            allperson.put("bc_his",bc_his);
            JSONArray a=new JSONArray();
            a.put(allperson);
            System.out.println(a.get(0).toString());
            hh++;
            System.out.println(paths);
            //HttpUtils.postGeneralUrl(paths.toString(),"application/json",a.get(0).toString(),"UTF-8");
            //task.doManyDMRisk(HttpUtils.postGeneralUrl(paths.toString(),"application/json",a.get(0).toString(),"UTF-8"));
            task.doManyDMRisk(HttpUtils.postGeneralUrl(paths.toString(),"application/json",a.get(0).toString(),"UTF-8"));
            //System.out.println(HttpUtils.postGeneralUrl(paths.toString(),"application/json",a.get(0).toString(),"UTF-8"));
            yhUserReginfoService.updateByPrimaryKeySelectives(YhUserReginfo);
            yhUserPersonageConditionMapper.updateByPrimaryKeySelective2(record);
        }

        result.put("code", 200);
        return result;
    }

    /**
     * 获得Java虚拟机可用的处理器个数 + 1
     */
    private static final int THREADS = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * 将一组数据平均分成n组
     *
     * @param source 要分组的数据源
     * @param n      平均分成n组
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    public Map<String, Object> ajaxUploadExcel2(InputStream in, MultipartFile file, HttpServletRequest request) throws Exception {
        StringBuilder paths=new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName()).append(request.getServerPort() == 80 ? "" : request.getServerPort()).append(request.getContextPath()).append("/applet/api/questionAssessTitleFront/doDMRisk");
        //StringBuilder paths=new StringBuilder().append("http://www.yhy.ren").append(request.getServerPort() == 80 ? "" : request.getServerPort()).append(request.getContextPath()).append("/applet/api/questionAssessTitleFront/doDMRisk");
        Map<String, Object> result =new HashMap<>();

        List<List<Object>> listob = ExcelUtil.getBankListByExcel(in, file.getOriginalFilename());
        listob= listob.subList(4,listob.size());
        // 创建一个线程池
        ExecutorService exec = Executors.newFixedThreadPool(THREADS);
        // 定义一个任务集合
        List<Callable<Map<String, List<String>>>> tasks = new ArrayList<Callable<Map<String, List<String>>>>();
        Callable<Map<String, List<String>>> task = null;
        // 确定每条线程的数据
        for (int i = 0; i < THREADS; i++) {
            final List<List<Object>> listStr =  averageAssign(listob, THREADS).get(i);
            task = new Callable<Map<String, List<String>>>() {
                @Override
                public Map<String, List<String>> call() throws Exception {
                    //多线程处理
                    Map<String, List<String>> taskReturn = assess(listStr, paths.toString(),Thread.currentThread().getName()+"线程：");
                    return taskReturn;
                }
            };
            // 这里提交的任务容器列表和返回的Future列表存在顺序对应的关系
            tasks.add(task);
        }
        List<String> sucAll =new ArrayList<>();
        List<String> errAll = new ArrayList<>();
        List<String> pingguAll = new ArrayList<>();
        List<Future<Map<String, List<String>>>> results = exec.invokeAll(tasks);
        for (Future<Map<String, List<String>>> future : results) {
            Map<String, List<String>> stringListMap = future.get();
            sucAll.addAll(stringListMap.get("suc"));
            errAll.addAll(stringListMap.get("err"));
            pingguAll.addAll(stringListMap.get("pinggu"));
        }
        // 关闭线程池
        exec.shutdown();
        System.out.println("线程任务执行结束");
        result.put("suc", sucAll);
        result.put("err", errAll);
        result.put("pinggu", pingguAll);
        return result;
    }

    //操作集合
    public  Map<String, List<String>> assess(List<List<Object>> listob,String paths,String name) throws Exception{

        Map<String,List<String>> result = new HashMap<String, List<String>>();
        List<String> suc=new ArrayList<>();
        List<String> pinggu=new ArrayList<>();
        List<String> err=new ArrayList<>();

        for (int i=0;i<listob.size();i++){
            // 开始时间
            long start = System.currentTimeMillis();
            System.out.println("start...");
            System.err.println("线程:"+name);
            System.err.println((i+1)+"/"+listob.size()+"条数据");
            //手机号
            BigDecimal bd1 = new BigDecimal(Double.valueOf(listob.get(i).get(2).toString()));

            if(!isValidDate(listob.get(i).get(3).toString())){
                err.add("第"+(4)+"列格式不是日期格式"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }
            if(!(listob.get(i).get(1).toString().equals("1")||listob.get(i).get(1).toString().equals("2"))){
                err.add("第"+(2)+"列格式不正确"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }
            for (int h=4;h<129;h++){
                if(h!=113&&h!=109&&h!=108&&h!=107&&h!=112&&h!=111&&h!=38&&h!=37&&h!=35&&h!=34&&h!=32&&h!=31&&h!=30&&h!=29&&h!=70&&h!=69&&h!=68&&
                        h!=116&&h!=115&&h!=114&&h!=123&&h!=124&&h!=129&&h!=66&&h!=65&&h!=64&&h!=93&&h!=94&&h!=95&&h!=62&&
                        h!=63&&h!=110&&h!=5 &&h<119&&!isInt(listob.get(i).get(h).toString())){
                    err.add( "第"+(h+1)+"列格式不正确"+",手机号:"+bd1.toPlainString());
                    System.out.println("errEnd...");
                    continue;
                }
                if((h==5 || h>=119)&&h!=123&&h!=124&&h!=129&&!isInteger(listob.get(i).get(h).toString())){
                    err.add("第"+(h+1)+"列格式不正确"+",手机号:"+bd1.toPlainString());
                    System.out.println("errEnd...");
                    continue;
                }
            }


            String selectusercode = assessmentmapper.selectusercode(bd1.toPlainString());
            if(StringUtil.isEmpty(selectusercode)){
                err.add("根据手机号查询账号不存在"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }
            Integer selectgender = assessmentmapper.selectgender(bd1.toPlainString());
            if(null==selectgender||selectgender!=Integer.parseInt(listob.get(i).get(1).toString())){
                err.add("性别信息错误"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }




            YhUserReginfo YhUserReginfo=new YhUserReginfo();
            YhUserReginfo.setBirthDate(parse(listob.get(i).get(3).toString()));
            YhUserReginfo.setGender(Integer.parseInt( listob.get(i).get(1).toString()));
            if(listob.get(i).get(1).toString().equals("1")){
                YhUserReginfo.setEdBackground("小学及以下");
            }else if(listob.get(i).get(1).toString().equals("2")){
                YhUserReginfo.setEdBackground("初中");
            }else if(listob.get(i).get(1).toString().equals("3")){
                YhUserReginfo.setEdBackground("高中、中专或技校");
            }else if(listob.get(i).get(1).toString().equals("4")){
                YhUserReginfo.setEdBackground("本科");
            }else if(listob.get(i).get(1).toString().equals("5")){
                YhUserReginfo.setEdBackground("本科以上");
            }

            YhUserReginfo.setUserCode(selectusercode);
            YhUserPersonageCondition record=new YhUserPersonageCondition();

            record.setUserCode(selectusercode);
            record.setStature(listob.get(i).get(4).toString());
            record.setWeight(listob.get(i).get(5).toString());

            HashMap<String,Object> allperson=new HashMap<String,Object>();
            HashMap<String,Object> person=new HashMap<String,Object>();
            //高血压
            HashMap<String,Object> hbp_his=new HashMap<String,Object>();
            //脑卒中
            HashMap<String,Object> stroke_his=new HashMap<String,Object>();
            //冠心病
            HashMap<String,Object> chd_his=new HashMap<String,Object>();
            //心律失常
            HashMap<String,Object> mi_his=new HashMap<String,Object>();
            //绞痛
            HashMap<String,Object> angina_his=new HashMap<String,Object>();

            //心律衰竭
            HashMap<String,Object> ushf_his=new HashMap<String,Object>();
            //左心室肥大
            HashMap<String,Object> lvd_his=new HashMap<String,Object>();
            //您是否患过房颤
            HashMap<String,Object> af_his=new HashMap<String,Object>();
            //急性心脏事件
            HashMap<String,Object> ace_his=new HashMap<String,Object>();
            //糖尿病
            HashMap<String,Object> diab1_his=new HashMap<String,Object>();
            //痛风急性发作期
            HashMap<String,Object> gae_his=new HashMap<String,Object>();
            //非急性发作期
            HashMap<String,Object> gna_his=new HashMap<String,Object>();
            //慢性支气管炎
            HashMap<String,Object> bronch_his=new HashMap<String,Object>();
            //肺气肿
            HashMap<String,Object> emphy_his=new HashMap<String,Object>();
            //肺结核
            HashMap<String,Object> decline_his=new HashMap<String,Object>();
            //肺癌
            HashMap<String,Object> lc_his=new HashMap<String,Object>();
            //骨质疏松症
            HashMap<String,Object> op_his=new HashMap<String,Object>();
            //既往骨折
            HashMap<String,Object> fracture_his=new HashMap<String,Object>();
            //限制运动的疾病
            HashMap<String,Object> sport_his=new HashMap<String,Object>();
            //继发性骨质疏松症的疾病
            HashMap<String,Object> secondary_op_his=new HashMap<String,Object>();
            //乳腺癌
            HashMap<String,Object> bc_his=new HashMap<String,Object>();
            //类风湿关节炎
            HashMap<String,Object> ra_his=new HashMap<String,Object>();
            //吸烟
            HashMap<String,Object> smoke=new HashMap<String,Object>();
            //油烟接触史
            HashMap<String,Object> lampblack_contact=new HashMap<String,Object>();
            //粉尘接触史
            HashMap<String,Object> dust_exposure_his=new HashMap<String,Object>();
            //石棉接触史
            HashMap<String,Object> asbestos_contact_his=new HashMap<String,Object>();

            HashMap<String,Object> diab2_his=new HashMap<String,Object>();

            HashMap<String,Object> hw_check=new HashMap<String,Object>();

            HashMap<String,Object> pc_his=new HashMap<String,Object>();
            HashMap<String,Object> prostate_disease=new HashMap<String,Object>();
            HashMap<String,Object> bf_his=new HashMap<String,Object>();
            HashMap<String,Object> hua_his=new HashMap<String,Object>();
            HashMap<String,Object> fl_his=new HashMap<String,Object>();
            HashMap<String,Object> begind_breast_his=new HashMap<String,Object>();
            HashMap<String,Object> cancer_his=new HashMap<String,Object>();
            HashMap<String,Object> active_his=new HashMap<String,Object>();
            HashMap<String,Object> arrhythmia_his=new HashMap<String,Object>();


            person.put("id",selectusercode);
            person.put("name",listob.get(i).get(0));
            person.put("sex",listob.get(i).get(1));
            Integer Sex=Integer.parseInt( listob.get(i).get(1).toString());
            person.put("age", getAge(parse(listob.get(i).get(3).toString())) );
            person.put("hipline",listob.get(i).get(7));
            person.put("education_levels",listob.get(i).get(8));
            int jzd=Integer.parseInt(listob.get(i).get(10).toString());
            if(jzd<=2){
                person.put("jzd",1);
                person.put("jzhj",jzd==2? 2:1);
            }else{
                person.put("jzd",2);
                person.put("jzhj",jzd==4? 2:1);
            }
            person.put("living_envir",listob.get(i).get(12));
            hbp_his.put("disFlag",listob.get(i).get(40));
            stroke_his.put("disFlag",listob.get(i).get(41));
            chd_his.put("disFlag",listob.get(i).get(42));
            mi_his.put("disFlag",listob.get(i).get(43));
            ushf_his.put("disFlag",listob.get(i).get(46));
            lvd_his.put("disFlag",listob.get(i).get(47));
            af_his.put("disFlag",listob.get(i).get(48));
            ace_his.put("disFlag",listob.get(i).get(49));
            diab1_his.put("disFlag",listob.get(i).get(50));
            gae_his.put("disFlag",listob.get(i).get(51));
            gna_his.put("disFlag",listob.get(i).get(52));
            bronch_his.put("disFlag",listob.get(i).get(53));
            emphy_his.put("disFlag",listob.get(i).get(54));
            decline_his.put("disFlag",listob.get(i).get(55));
            lc_his.put("disFlag",listob.get(i).get(56));
            arrhythmia_his.put("disFlag",0);
            op_his.put("disFlag",listob.get(i).get(57));
            fracture_his.put("disFlag",listob.get(i).get(58));
            sport_his.put("disFlag",listob.get(i).get(60));
            secondary_op_his.put("disFlag",listob.get(i).get(61));
            bc_his.put("disFlag",listob.get(i).get(67));
            ra_his.put("disFlag",listob.get(i).get(59));
            angina_his.put("disFlag",listob.get(i).get(44));
            allperson.put("weight",listob.get(i).get(5));
            allperson.put("height",listob.get(i).get(4));
            allperson.put("waist",listob.get(i).get(6));
            allperson.put("activity",listob.get(i).get(9));
            allperson.put("pm25",listob.get(i).get(11));
            allperson.put("arterial_status",listob.get(i).get(45));


            if(Sex==1&&(listob.get(i).get(111)!=""||listob.get(i).get(112)!="" || listob.get(i).get(107)!="" || listob.get(i).get(108)!="" || listob.get(i).get(109)!="" ||
                    listob.get(i).get(113)!=""  || listob.get(i).get(114)!=""  || listob.get(i).get(115)!=""  || listob.get(i).get(116)!=""  || listob.get(i).get(68)!="" ||
                    listob.get(i).get(69)!="" || listob.get(i).get(70)!=""|| listob.get(i).get(110)!="" )){
                err.add("第(69-71)(108-117)列为女性选项,男性不能填此项"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }
            if(Sex==2&&(listob.get(i).get(111)==""||listob.get(i).get(112)==""||listob.get(i).get(107)=="" ||  listob.get(i).get(113)=="" || listob.get(i).get(68)=="" ||
                    listob.get(i).get(69)=="" || listob.get(i).get(70)==""|| listob.get(i).get(110)==""  )){
                err.add("第(69,70,71,108,112,113,114,111)列为女性选项,女性为必填项"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }

            if((listob.get(i).get(107).toString().equals("3"))&&(listob.get(i).get(108)!="" || listob.get(i).get(109)!="" )){
                err.add("第108-110列，您还未生育过"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }
            if((listob.get(i).get(107).toString().equals("1") || (listob.get(i).get(107).toString().equals("2")))&&  ((listob.get(i).get(108)=="" || listob.get(i).get(109)=="" ))){
                err.add("第108-110列，您已经生育过"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }
            if((listob.get(i).get(113).toString().equals("2"))&&(listob.get(i).get(114)!="" || listob.get(i).get(115)!="")){
                err.add("第(114-116)列，您还未绝经"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }
            if((listob.get(i).get(113).toString().equals("1"))&&(listob.get(i).get(114)=="" || listob.get(i).get(115)=="")){
                err.add("第(114-116)列，您已经绝经"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }

            if(((listob.get(i).get(116).toString()).equals("3"))){
                allperson.put("has_med_ert","2");
            }else if(((listob.get(i).get(116).toString()).equals("1")) || (((listob.get(i).get(116).toString()).equals("2")))){
                allperson.put("has_med_ert","1");
            }else{
                allperson.put("has_med_ert","");
            }


            if(((listob.get(i).get(28).toString()).equals("2"))&&((listob.get(i).get(29)!="")||(listob.get(i).get(30)!="")||(listob.get(i).get(31)!=""))){
                err.add("第(30-32)列不用填写，您未吸烟"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }
            if(((listob.get(i).get(28).toString()).equals("3"))&&((listob.get(i).get(30)!=""))){
                err.add("第(31)列不用填写，您已经戒烟"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }

            if(((listob.get(i).get(28).toString()).equals("1"))&&((listob.get(i).get(31)!=""))){
                err.add("第(32)列不用填写，您还未戒烟"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }

            if(((listob.get(i).get(28).toString()).equals("1"))&&((listob.get(i).get(29)=="")||(listob.get(i).get(30)==""))){
                err.add("第(30-31)列不能为空，因为您正在吸烟"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }

            if(((listob.get(i).get(33).toString()).equals("2"))&&((listob.get(i).get(34))!="" || (listob.get(i).get(35))!="")){
                err.add("第(35-36)列，您选择的是不喝酒"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }

            if(((listob.get(i).get(33).toString()).equals("1"))&&((listob.get(i).get(34))=="" || (listob.get(i).get(35))=="")){
                err.add("第(35-36)列，您选择的是喝酒，请检查是否填写次数"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }
            if(((listob.get(i).get(36).toString()).equals("2"))&&((listob.get(i).get(37))!="" || (listob.get(i).get(38))!="")){
                err.add("第(38-39)列，您没有参加过运动"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }
            if(((listob.get(i).get(36).toString()).equals("1"))&&((listob.get(i).get(37))=="" || (listob.get(i).get(38))=="")){
                err.add("第(38-39)列，您参加过运动，但是没填次数"+",手机号:"+bd1.toPlainString());
                System.out.println("errEnd...");
                continue;
            }

            if(Sex==1){
                if((listob.get(i).get(66)=="")||(listob.get(i).get(65)=="")||(listob.get(i).get(64)=="")||(listob.get(i).get(93)=="")||(listob.get(i).get(94)=="")||(listob.get(i).get(95)=="")||(listob.get(i).get(62)=="")||(listob.get(i).get(63)=="")){
                    err.add("第(63-67)(94-96)列，该项为男性必填"+",手机号:"+bd1.toPlainString());
                    System.out.println("errEnd...");
                    continue;
                }else{
                    prostate_disease.put("disFlag",listob.get(i).get(62));
                    pc_his.put("disFlag",listob.get(i).get(63));
                }
            }else if(Sex==2){
                if((listob.get(i).get(66)!="")||(listob.get(i).get(65)!="")||(listob.get(i).get(64)!="")||(listob.get(i).get(93)!="")||(listob.get(i).get(94)!="")||(listob.get(i).get(95)!="")||(listob.get(i).get(62)!="")||(listob.get(i).get(63)!="")){
                    err.add("第(63-67)(94-96)列，该项女性不能填写"+",手机号:"+bd1.toPlainString());
                    System.out.println("errEnd...");
                    continue;
                }
                pc_his.put("disFlag","2");
                prostate_disease.put("disFlag","2");
            }
            allperson.put("female_menstruation",listob.get(i).get(111));
            allperson.put("female_menstruation_regular",listob.get(i).get(112));
            allperson.put("female_birth",listob.get(i).get(107));
            allperson.put("female_tire",listob.get(i).get(108));


            allperson.put("female_breast_time",listob.get(i).get(113));
            if(listob.get(i).get(109).equals("1")||listob.get(i).get(109).equals("2")){
                allperson.put("female_breast",1);
                allperson.put("female_breast_time",listob.get(i).get(109));
            }else  if(listob.get(i).get(109).equals("3")){
                allperson.put("female_breast",2);
                allperson.put("female_breast_time","");
            }else{
                allperson.put("female_breast","");
                allperson.put("female_breast_time","");
            }
            allperson.put("female_menopause",listob.get(i).get(113));
            allperson.put("female_menopause_age",listob.get(i).get(114));
            allperson.put("weight_up",listob.get(i).get(115));
            allperson.put("med_ert",listob.get(i).get(116));
            allperson.put("med_use_pill",listob.get(i).get(68));
            allperson.put("breast_plastic_surgery",listob.get(i).get(69));
            allperson.put("gynecology_operation",listob.get(i).get(70));
            allperson.put("family_diab_f",listob.get(i).get(73));
            allperson.put("family_diab_m",listob.get(i).get(74));
            allperson.put("family_diab_bs",listob.get(i).get(75));
            allperson.put("family_diab_ch",listob.get(i).get(76));
            allperson.put("family_hbp_f",listob.get(i).get(77));
            allperson.put("family_hbp_m",listob.get(i).get(78));
            allperson.put("family_hbp_bs",listob.get(i).get(79));
            allperson.put("family_hbp_ch",listob.get(i).get(80));
            allperson.put("family_chd_f",listob.get(i).get(81));
            allperson.put("family_chd_m",listob.get(i).get(82));
            allperson.put("family_chd_bs",listob.get(i).get(83));
            allperson.put("family_chd_ch",listob.get(i).get(84));
            allperson.put("family_stroke_f",listob.get(i).get(85));
            allperson.put("family_stroke_m",listob.get(i).get(86));
            allperson.put("family_stroke_bs",listob.get(i).get(87));
            allperson.put("family_stroke_ch",listob.get(i).get(88));
            allperson.put("family_hf_f",listob.get(i).get(103));
            allperson.put("family_hf_m",listob.get(i).get(104));
            allperson.put("family_hf_bs",listob.get(i).get(105));
            allperson.put("family_hf_ch",listob.get(i).get(106));
            allperson.put("family_lung_f",listob.get(i).get(89));
            allperson.put("family_lung_m",listob.get(i).get(90));
            allperson.put("family_lung_bs",listob.get(i).get(91));
            allperson.put("family_lung_ch",listob.get(i).get(92));
            allperson.put("family_breast_m",listob.get(i).get(96));
            allperson.put("family_breast_s",listob.get(i).get(97));
            allperson.put("family_breast_d",listob.get(i).get(98));
            allperson.put("family_gout_f",listob.get(i).get(99));
            allperson.put("family_gout_m",listob.get(i).get(100));
            allperson.put("family_gout_bs",listob.get(i).get(101));
            allperson.put("family_gout_ch",listob.get(i).get(102));
            allperson.put("med_hbp",listob.get(i).get(71));
            allperson.put("med_ach",listob.get(i).get(72));
            allperson.put("food_vegetables",listob.get(i).get(18));
            allperson.put("food_fruits",listob.get(i).get(19));
            allperson.put("food_milk",listob.get(i).get(20));
            allperson.put("food_dairy",listob.get(i).get(21));
            allperson.put("food_fired",listob.get(i).get(22));
            allperson.put("food_sweet",listob.get(i).get(23));
            allperson.put("food_sugary",listob.get(i).get(24));
            allperson.put("food_pickled",listob.get(i).get(25));
            allperson.put("seafood_cnt",listob.get(i).get(26));
            allperson.put("drink_tea_cnt",listob.get(i).get(27));
            hw_check.put("xt_exception",0);
            smoke.put("disFlag",listob.get(i).get(28));
            allperson.put("female_gestation",listob.get(i).get(110));

            bf_his.put("disFlag","2");
            hua_his.put("disFlag","2");
            fl_his.put("disFlag","2");
            begind_breast_his.put("disFlag","2");
            cancer_his.put("disFlag","2");
            active_his.put("disFlag","2");
            allperson.put("arrhythmia_his",arrhythmia_his);
            allperson.put("smoke_begin_age",listob.get(i).get(29));
            allperson.put("smoke_amount",listob.get(i).get(30));
            allperson.put("smoke_end_age",listob.get(i).get(31));
            allperson.put("smoke_sencod",listob.get(i).get(32));
            allperson.put("food_drink",listob.get(i).get(33));
            allperson.put("food_drink_times",listob.get(i).get(34));
            allperson.put("food_drink_beer",listob.get(i).get(35));
            allperson.put("sport_in",listob.get(i).get(36));
            allperson.put("sport_freq",listob.get(i).get(37));
            allperson.put("sport_time",listob.get(i).get(38));
            allperson.put("decorate_house",listob.get(i).get(13));
            lampblack_contact.put("disFlag",listob.get(i).get(14));
            allperson.put("chemical_occupation",listob.get(i).get(15));
            dust_exposure_his.put("disFlag",listob.get(i).get(16));
            asbestos_contact_his.put("disFlag",listob.get(i).get(17));
            allperson.put("psy_nervous",listob.get(i).get(118));
            allperson.put("bedtime",listob.get(i).get(39));
            person.put("income",listob.get(i).get(117));
            allperson.put("cholesterol",listob.get(i).get(119));
            allperson.put("tg",listob.get(i).get(120));
            allperson.put("hdlc",listob.get(i).get(121));
            allperson.put("ldlc",listob.get(i).get(122));
            allperson.put("serum_creatinine",listob.get(i).get(123));
            allperson.put("bua",listob.get(i).get(124));
            allperson.put("fbg",listob.get(i).get(125));
            allperson.put("sbp",listob.get(i).get(126));
            allperson.put("dbp",listob.get(i).get(127));
            allperson.put("wbc",listob.get(i).get(128));
            allperson.put("t_score",listob.get(i).get(129));
            allperson.put("hematuresis",listob.get(i).get(66));
            allperson.put("urine_symptom",listob.get(i).get(65));
            allperson.put("urinary_tract_infection",listob.get(i).get(64));
            allperson.put("family_pro_f",listob.get(i).get(93));
            allperson.put("family_pro_b",listob.get(i).get(94));
            allperson.put("family_pro_s",listob.get(i).get(95));
            diab2_his.put("disFlag",2);
            person.put("occupation",0);

            allperson.put("food_drink_wine","1");
            allperson.put("food_drink_spirit","1");
            allperson.put("hazardous_exposure_his","0");
            allperson.put("diseaseid","");
            allperson.put("serialNum","");
            allperson.put("food_heat","1");
            allperson.put("smoke_status",listob.get(i).get(28));
            allperson.put("med_use","");


            allperson.put("hw_check",hw_check);
            allperson.put("pc_his",pc_his);
            allperson.put("prostate_disease",prostate_disease);
            allperson.put("bf_his",bf_his);
            allperson.put("hua_his",hua_his);
            allperson.put("fl_his",fl_his);
            allperson.put("begind_breast_his",begind_breast_his);
            allperson.put("cancer_his",cancer_his);
            allperson.put("active_his",active_his);
            allperson.put("diab2_his",diab2_his);
            allperson.put("person",person);
            allperson.put("smoke",smoke);
            allperson.put("asbestos_contact_his",asbestos_contact_his);
            allperson.put("dust_exposure_his",dust_exposure_his);
            allperson.put("lampblack_contact",lampblack_contact);
            allperson.put("hbp_his",hbp_his);
            allperson.put("stroke_his",stroke_his);
            allperson.put("chd_his",chd_his);
            allperson.put("mi_his",mi_his);
            allperson.put("angina_his",angina_his);
            allperson.put("ushf_his",ushf_his);
            allperson.put("lvd_his",lvd_his);
            allperson.put("af_his",af_his);
            allperson.put("ace_his",ace_his);
            allperson.put("diab1_his",diab1_his);
            allperson.put("gae_his",gae_his);
            allperson.put("gna_his",gna_his);
            allperson.put("bronch_his",bronch_his);
            allperson.put("decline_his",decline_his);
            allperson.put("emphy_his",emphy_his);
            allperson.put("lc_his",lc_his);
            allperson.put("op_his",op_his);
            allperson.put("ra_his",ra_his);
            allperson.put("fracture_his",fracture_his);
            allperson.put("sport_his",sport_his);
            allperson.put("secondary_op_his",secondary_op_his);
            allperson.put("bc_his",bc_his);
            JSONArray a=new JSONArray();
            a.put(allperson);

            System.out.println("整理本条数据用时:"+(System.currentTimeMillis() - start) + "毫秒");
            long start2 = System.currentTimeMillis();
            String retrunStr = httpUtils.postGeneralUrl(paths, "application/json", a.get(0).toString(), "UTF-8");
            System.out.println("调用评估接口数据用时:"+(System.currentTimeMillis() - start2) + "毫秒");
            System.out.println("end...");
            if(!retrunStr.contains("成功")){
                err.add("评估接口评估失败！,手机号:"+bd1.toPlainString());
                continue;
            }
            yhUserReginfoService.updateByPrimaryKeySelectives(YhUserReginfo);
            yhUserPersonageConditionMapper.updateByPrimaryKeySelective2(record);
            suc.add("成功"+",手机号:"+bd1.toPlainString());
        }
        result.put("suc", suc);
        result.put("err", err);
        result.put("pinggu", pinggu);
        return result;
    }

    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }
    public static  int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            }else{
                age--;//当前月份在生日之前，年龄减一
            } } return age; }

    public static   boolean isInteger(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);

    }
    public static boolean isInt(String str) {
        String reg = "^[0-9]+$";
        return str.matches(reg);

    }
    private static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static void main(String[] args) {

            System.out.println(isValidDate("2020/01/29"));

    }


}


