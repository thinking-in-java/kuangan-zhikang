package com.yihe.system.service.impl;


import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.yihe.system.domain.PeriodComparison;
import com.yihe.system.domain.YhDiseaseAssessTotalReport3web;
import com.yihe.system.domain.YhExportCheck;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhPeriodComparisonMapper;
import com.yihe.system.service.YhPeriodComparisonService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class YhPeriodComparisonServiceImpl implements YhPeriodComparisonService {
    @Autowired
    private YhPeriodComparisonMapper userDao;
    @Override
    public Map<String, Object> get(PeriodComparison model) {
        // 腰围过大 蔬菜水果摄入不足  肥胖超重  血脂异常 血压高  肉类摄入过多 谷类摄入过多 血糖高 缺乏运动 吸烟 过量饮酒
        if(model.getSex()==0){
            model.setSex(null);
        }
        Long ismin= Long.valueOf(Integer.MAX_VALUE);
        String[] arr1=model.getSelect1().split(",");
        List<YhDiseaseAssessTotalReport3web> check = new ArrayList<YhDiseaseAssessTotalReport3web>();
        List<YhDiseaseAssessTotalReport3web> addcheck = new ArrayList<YhDiseaseAssessTotalReport3web>();
        List<YhDiseaseAssessTotalReport3web> check2 = new ArrayList<YhDiseaseAssessTotalReport3web>();
        List<YhDiseaseAssessTotalReport3web> addcheck2 = new ArrayList<YhDiseaseAssessTotalReport3web>();


        model.setBegorberthday(Integer.valueOf(getSysYear())-model.getAgeStart()+"-01-01 00:00:00");
        model.setEndberthday(Integer.valueOf(getSysYear())-model.getAgeEnd()+"-12-31 11:59:59");
        Integer[] yhArr = {0,0,0,0,0,0,0,0,0,0};
        if(model.getSelect1()!=null) {
            for (int i = 0; i < arr1.length; i++) {
                if(ismin>Long.valueOf(arr1[i])) {
                    ismin= Long.valueOf(arr1[i]);
                }
            }
            if((ismin+"").equals(model.getNoworgcode())) {
                model.setOrgCode(ismin+"");
                if(model.getBefortime()!=null) {
                    model.setStartTime(model.getBefortime().substring(0, 10)+" 00:00:00");
                    model.setEndTime(model.getBefortime().substring(13)+" 23:59:59");
                }
                addcheck=(ArrayList<YhDiseaseAssessTotalReport3web>) userDao.get(model);
                if(model.getBefortime()!=null) {
                    model.setStartTime(model.getEndtime().substring(0, 10)+" 00:00:00");
                    model.setEndTime(model.getEndtime().substring(13)+" 23:59:59");
                }
                addcheck2=(ArrayList<YhDiseaseAssessTotalReport3web>) userDao.getMeddle(model);
                check.addAll(addcheck);
                check2.addAll(addcheck2);
            }else {
                for (int i = 0; i < arr1.length; i++) {
                    model.setOrgCode(arr1[i]);
                    if(model.getBefortime()!=null) {
                        model.setStartTime(model.getBefortime().substring(0, 10)+" 00:00:00");
                        model.setEndTime(model.getBefortime().substring(13)+" 23:59:59");
                    }
                    addcheck=(ArrayList<YhDiseaseAssessTotalReport3web>) userDao.get(model);
                    if(model.getBefortime()!=null) {
                        model.setStartTime(model.getEndtime().substring(0, 10)+" 00:00:00");
                        model.setEndTime(model.getEndtime().substring(13)+" 23:59:59");
                    }
                    addcheck2=(ArrayList<YhDiseaseAssessTotalReport3web>) userDao.getMeddle(model);
                    check.addAll(addcheck);
                    check2.addAll(addcheck2);

                }
            }
        }
        List<YhDiseaseAssessTotalReport3web> getonelist =getonelist(check,check2);
        //干预后
        List<YhDiseaseAssessTotalReport3web> meddleList = userDao.getMeddle(model);
        check=getonelist(check,getonelist);
        check2=getonelist(check2,getonelist);
        int mancount=0;
        int womancount=0;
        for (int i=0;i<check.size();i++){
            if(check.get(i).getGender()==1){
                mancount++;
            }else{
                womancount++;
            }
        }
        System.out.println(111);
        List<Integer[]> befor= getchange(check);
        List<Integer[]> end=getchange(check2);
        Map<String,Object> map=new HashMap<>();
        List<Integer[]> list=new ArrayList<>();
        list.add(befor.get(0));
        list.add(befor.get(1));
        list.add(befor.get(2));
        list.add(end.get(0));
        list.add(end.get(1));
        list.add(end.get(2));
        List<Integer[]> disbefor=getdiss(check);
        List<Integer[]> disend=getdiss(check2);
        list.add(disbefor.get(0));
        list.add(disbefor.get(1));
        list.add(disbefor.get(2));
        list.add(disend.get(0));
        list.add(disend.get(1));
        list.add(disend.get(2));
        map.put("list",list);
        map.put("allpeople",check.size());
        map.put("mancount",mancount);
        map.put("womancount",womancount);
        return map;
    }

    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }


    @Override
    public List<YhDiseaseAssessTotalReport3web> getMeddle(PeriodComparison model) {
        return userDao.getMeddle(model);
    }

    @Override
    public String getrepotimg() {
        return userDao.getrepotimg();
    }

    //先找出相同的list
    public List<YhDiseaseAssessTotalReport3web> getonelist(List<YhDiseaseAssessTotalReport3web> one ,List<YhDiseaseAssessTotalReport3web> two){
        List<YhDiseaseAssessTotalReport3web> retu=new ArrayList<>();
        for (int i=0;i<one.size();i++){
            for (int k=0;k<two.size();k++){
                if(one.get(i).getUserCode().equals(two.get(k).getUserCode())){
                    retu.add(one.get(i));
                }
            }
        }
        return  retu;
    }

    public List<Integer[]> getchange(List<YhDiseaseAssessTotalReport3web> list){
        List<Integer[]>  lists=new ArrayList<>();
        Integer[] yhArr = {0,0,0,0,0,0,0,0,0,0};
        Integer[] yhArrman = {0,0,0,0,0,0,0,0,0,0};
        Integer[] yhArrwoman = {0,0,0,0,0,0,0,0,0,0};
        //饮酒人数
        int yinjiu=0;
        int womanyinjiu=0;
        int manyinjiu=0;
        //吸烟人数
        int xiyan=0;
        int womanxiyan=0;
        int manxiyan=0;
        //缺乏运动人数
        int quefa=0;
        int womanquefa=0;
        int manquefa=0;
        //血糖高人数
        int xuetanggao=0;
        int womanxuetanggao=0;
        int manxuetanggao=0;
        //水果
        int shuiguo=0;
        int womanshuiguo=0;
        int manshuiguo=0;
        //血压高
        int xueya=0;
        int womanxueya=0;
        int manxueya=0;
        //血脂高
        int xuezhi=0;
        int womanxuezhi=0;
        int manxuezhi=0;
        //肥胖
        int feipang=0;
        int womanfeipang=0;
        int manfeipang=0;
        //蔬菜少
        int shucai=0;
        int womanshucai=0;
        int manshucai=0;
        //腰围过大
        int yaowei=0;
        int womanyaowei=0;
        int manyaowei=0;
        for (int i=0;i<list.size();i++) {
            if (list.get(i).getUserCheckAnswer() != null) {
                Object resultDocument = Configuration.defaultConfiguration().jsonProvider().parse(list.get(i).getUserCheckAnswer());
                String isYingiu = "";
                String isYingtime = "";
                String isxiyan = "";
                String isquefa = "";
                Double isxuetanggao = 0.0;
                String isgulei = "";
                String isroulei = "";
                String isfeipang = "";
                String isshucai = "";
                String isyaowei = "";
                if (((JsonPath.read(resultDocument, "$.food_drink_times") + "").trim().length() > 0)) {
                    if (list.get(i).getUserCheckAnswer().indexOf("food_drink_times") != -1) {
                        isYingiu = JsonPath.read(resultDocument, "$.food_drink_times") + "";
                    }
                }
                if (((JsonPath.read(resultDocument, "$.food_drink_beer") + "").trim().length() > 0)) {
                    if (list.get(i).getUserCheckAnswer().indexOf("food_drink_beer") != -1) {
                        isYingtime = JsonPath.read(resultDocument, "$.food_drink_beer") + "";
                    }
                }
                //吸烟状态
                if (((JsonPath.read(resultDocument, "$.smoke_status") + "").trim().length() > 0)) {
                    if (list.get(i).getUserCheckAnswer().indexOf("smoke_status") != -1) {
                        isxiyan = JsonPath.read(resultDocument, "$.smoke_status") + "";
                    }
                }
                //血糖
                if (((JsonPath.read(resultDocument, "$.fbg") + "").trim().length() > 0)) {
                    if (list.get(i).getUserCheckAnswer().indexOf("fbg") != -1) {
                        isxuetanggao = Double.valueOf((JsonPath.read(resultDocument, "$.fbg") + ""));
                    }
                }

                //血压


                if (("2".equals(isYingiu) || "3".equals(isYingiu)) && ("3".equals(isYingtime) || "4".equals(isYingtime))) {
                    yinjiu++;
                    if(list.get(i).getGender()==1){
                        manyinjiu++;
                    }else{
                        womanyinjiu++;
                    }
                }
                if ("1".equals(isxiyan)) {
                    xiyan++;
                    if(list.get(i).getGender()==1){
                        manxiyan++;
                    }else{
                        womanxiyan++;
                    }
                }
                if (isxuetanggao >= 6.1) {
                    xuetanggao++;
                    if(list.get(i).getGender()==1){
                        manxuetanggao++;
                    }else{
                        womanxuetanggao++;
                    }
                }
                if (((JsonPath.read(resultDocument, "$.dbp") + "").trim().length() > 0) && ((JsonPath.read(resultDocument, "$.dbp") + "").trim().length() > 0)) {
                    if (Double.valueOf((JsonPath.read(resultDocument, "$.dbp") + "").trim()) > 80.0 || Double.valueOf((JsonPath.read(resultDocument, "$.sbp") + "").trim()) > 120.0) {
                        xueya++;
                        if(list.get(i).getGender()==1){
                            manxueya++;
                        }else{
                            womanxueya++;
                        }
                    }
                }
                if (((JsonPath.read(resultDocument, "$.sport_freq") + "").trim().length() > 0)) {
                    if ((JsonPath.read(resultDocument, "$.sport_freq") + "").equals("4") ||(JsonPath.read(resultDocument, "$.sport_freq") + "").equals("5")) {

                    }else{
                        quefa++;
                        if(list.get(i).getGender()==1){
                            manquefa++;
                        }else{
                            womanquefa++;
                        }
                    }
                }else{
                    quefa++;
                    if(list.get(i).getGender()==1){
                        manquefa++;
                    }else{
                        womanquefa++;
                    }
                }
                if (((JsonPath.read(resultDocument, "$.cholesterol") + "").trim().length() > 0) && ((JsonPath.read(resultDocument, "$.tg") + "").trim().length() > 0) && ((JsonPath.read(resultDocument, "$.hdlc") + "").trim().length() > 0) && ((JsonPath.read(resultDocument, "$.ldlc") + "").trim().length() > 0)) {
                    if (Double.valueOf(JsonPath.read(resultDocument, "$.cholesterol") + "") >= 5.18 || Double.valueOf(JsonPath.read(resultDocument, "$.tg") + "") >= 1.7 || Double.valueOf(JsonPath.read(resultDocument, "$.hdlc") + "") < 1.04 || Double.valueOf(JsonPath.read(resultDocument, "$.ldlc") + "") >= 3.37) {
                        xuezhi++;
                        if(list.get(i).getGender()==1){
                            manxuezhi++;
                        }else{
                            womanxuezhi++;
                        }
                    }
                }
                String StringBmi = userDao.selectbmi(list.get(i).getRequestSerialnum());
                if(StringBmi==null){
                    StringBmi="{'now_value':'19.89','isRadar':1,'last_value':'19.89','Ideal_value':'＜24','change_value':'健康'}";
                }
                Object resultbmi = Configuration.defaultConfiguration().jsonProvider().parse(StringBmi);
                if (Double.valueOf(JsonPath.read(resultbmi, "$.now_value") + "") >= 24.0) {
                    feipang++;
                    if(list.get(i).getGender()==1){
                        manfeipang++;
                    }else{
                        womanfeipang++;
                    }
                }
                if (!(JsonPath.read(resultDocument, "$.food_vegetables") .equals("5"))) {
                    shucai++;
                    if(list.get(i).getGender()==1){
                        manshucai++;
                    }else{
                        womanshucai++;
                    }
                }
                if (!(JsonPath.read(resultDocument, "$.food_fruits") .equals("5"))) {
                    shuiguo++;
                    if(list.get(i).getGender()==1){
                        manshuiguo++;
                    }else{
                        womanshuiguo++;
                    }
                }
                if (((JsonPath.read(resultDocument, "$.waist") + "").trim().length() > 0)) {
                    if ((list.get(i).getGender() == 1 && Double.valueOf((JsonPath.read(resultDocument, "$.waist") + "").trim()) > 85.0) || (list.get(i).getGender() == 2 && Double.valueOf((JsonPath.read(resultDocument, "$.waist") + "").trim()) > 80.0)) {
                        yaowei++;
                        if(list.get(i).getGender()==1){
                            manyaowei++;
                        }else{
                            womanyaowei++;
                        }
                    }
                }
            }
        }
        yhArr[0]=yinjiu;
        yhArr[1]=xiyan;
        yhArr[2]=quefa;
        yhArr[3]=xuetanggao;
        yhArr[4]=shucai;
        yhArr[5]=shuiguo;
        yhArr[6]=xueya;
        yhArr[7]=xuezhi;
        yhArr[8]=feipang;
        yhArr[9]=yaowei;
        yhArrman[0]=manyinjiu;
        yhArrman[1]=manxiyan;
        yhArrman[2]=manquefa;
        yhArrman[3]=manxuetanggao;
        yhArrman[4]=manshucai;
        yhArrman[5]=manshuiguo;
        yhArrman[6]=manxueya;
        yhArrman[7]=manxuezhi;
        yhArrman[8]=manfeipang;
        yhArrman[9]=manyaowei;
        yhArrwoman[0]=womanyinjiu;
        yhArrwoman[1]=womanxiyan;
        yhArrwoman[2]=womanquefa;
        yhArrwoman[3]=womanxuetanggao;
        yhArrwoman[4]=womanshucai;
        yhArrwoman[5]=womanshuiguo;
        yhArrwoman[6]=womanxueya;
        yhArrwoman[7]=womanxuezhi;
        yhArrwoman[8]=womanfeipang;
        yhArrwoman[9]=womanyaowei;
        lists.add(yhArr);
        lists.add(yhArrman);
        lists.add(yhArrwoman);
        return lists;
    }

    public List<Integer[]> getdiss(List<YhDiseaseAssessTotalReport3web> list){
        List<Integer[]>  lists=new ArrayList<>();
        Integer[] yhArr = {0,0,0,0,0,0,0,0,0};
        Integer[] yhArrman = {0,0,0,0,0,0,0,0,0};
        Integer[] yhArrwoman = {0,0,0,0,0,0,0,0,0};
        int xuezhi=0;
        int guzhi=0;
        int ruxian=0;
        int qianliexian=0;
        int feiai=0;
        int naocuzhong=0;
        int guanxin=0;
        int gaoxueya=0;
        int tangniao=0;
        int manxuezhi=0;
        int manguzhi=0;
        int manruxian=0;
        int manqianliexian=0;
        int manfeiai=0;
        int mannaocuzhong=0;
        int manguanxin=0;
        int mangaoxueya=0;
        int mantangniao=0;
        int womanxuezhi=0;
        int womanguzhi=0;
        int womanruxian=0;
        int womanqianliexian=0;
        int womanfeiai=0;
        int womannaocuzhong=0;
        int womanguanxin=0;
        int womangaoxueya=0;
        int womantangniao=0;
        for (int i=0;i<list.size();i++){
            Object resultDocument = Configuration.defaultConfiguration().jsonProvider().parse(list.get(i).getUserCheckAnswer());
                    if(list.get(i).getBfHis()==1){
                        xuezhi++;
                        if(list.get(i).getGender()==1){
                            manxuezhi++;
                        }else{
                            womanxuezhi++;
                        }
                    }
                    if(list.get(i).getOpHis()==1){
                        guzhi++;
                        if(list.get(i).getGender()==1){
                            manguzhi++;
                        }else{
                            womanguzhi++;
                        }
                    }
                    if(list.get(i).getBcHis()==1){
                        ruxian++;
                        if(list.get(i).getGender()==1){
                            manruxian++;
                        }else{
                            womanruxian++;
                        }
                    }
                    if(list.get(i).getPcHis()==1){
                        qianliexian++;
                        if(list.get(i).getGender()==1){
                            manqianliexian++;
                        }else{
                            womanqianliexian++;
                        }
                    }
                    if(list.get(i).getLcHis()==1){
                        feiai++;
                        if(list.get(i).getGender()==1){
                            manfeiai++;
                        }else{
                            womanfeiai++;
                        }
                    }
                    if(list.get(i).getStrokeHis()==1){
                        naocuzhong++;
                        if(list.get(i).getGender()==1){
                            mannaocuzhong++;
                        }else{
                            womannaocuzhong++;
                        }
                    }
                    if(list.get(i).getChdHis()==1){
                        guanxin++;
                        if(list.get(i).getGender()==1){
                            manguanxin++;
                        }else{
                            womanguanxin++;
                        }
                    }
                    if(null!=list.get(i).getHbpHis() && list.get(i).getHbpHis()==1){
                        gaoxueya++;
                        if(list.get(i).getGender()==1){
                            mangaoxueya++;
                        }else{
                            womangaoxueya++;
                        }
                    }
                    if((JsonPath.read(resultDocument, "$.diab1_his.disFlag") .equals("1"))){
                        tangniao++;
                        if(list.get(i).getGender()==1){
                            mantangniao++;
                        }else{
                            womantangniao++;
                        }
                    }

        }
        yhArr[0]=xuezhi;
        yhArr[1]=guzhi;
        yhArr[2]=ruxian;
        yhArr[3]=qianliexian;
        yhArr[4]=feiai;
        yhArr[5]=naocuzhong;
        yhArr[6]=guanxin;
        yhArr[7]=gaoxueya;
        yhArr[8]=tangniao;
        yhArrman[0]=manxuezhi;
        yhArrman[1]=manguzhi;
        yhArrman[2]=manruxian;
        yhArrman[3]=manqianliexian;
        yhArrman[4]=manfeiai;
        yhArrman[5]=mannaocuzhong;
        yhArrman[6]=manguanxin;
        yhArrman[7]=mangaoxueya;
        yhArrman[8]=mantangniao;
        yhArrwoman[0]=womanxuezhi;
        yhArrwoman[1]=womanguzhi;
        yhArrwoman[2]=womanruxian;
        yhArrwoman[3]=womanqianliexian;
        yhArrwoman[4]=womanfeiai;
        yhArrwoman[5]=womannaocuzhong;
        yhArrwoman[6]=womanguanxin;
        yhArrwoman[7]=womanxuezhi;
        yhArrwoman[8]=womantangniao;
        lists.add(yhArr);
        lists.add(yhArrman);
        lists.add(yhArrwoman);
        return  lists;
    }


}
