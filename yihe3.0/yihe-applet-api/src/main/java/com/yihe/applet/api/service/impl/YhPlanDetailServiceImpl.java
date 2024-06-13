package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhplanDetail;
import com.yihe.applet.api.mapper.YhPlanDetailMapper;
import com.yihe.applet.api.service.YhPlanDetailService;
import com.yihe.system.domain.YhFoodSrecipe;
import com.yihe.system.domain.YhFoodSrecipeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class YhPlanDetailServiceImpl implements YhPlanDetailService {

    @Autowired
    YhPlanDetailMapper yhplandetailmapper;

    @Override
    public int selectfoodcount(String usercode) {
        return yhplandetailmapper.selectfoodcount(usercode);
    }

    @Override
    public int selectsportcount(String usercode) {
        return yhplandetailmapper.selectsportcount(usercode);
    }
    
    @Override
    public int changefoodse(YhplanDetail yhplanDetail) {
        //首先根据疾病id与oneself查询出套餐
        List<YhFoodSrecipe> yhFoodSrecipes=yhplandetailmapper.changhFoodSrecipes(yhplanDetail);
        Double[] count= new Double[yhFoodSrecipes.size()];
        Integer[] id=new Integer[yhFoodSrecipes.size()];
        for (int i=0;i<yhFoodSrecipes.size();i++){
            count[i]=yhFoodSrecipes.get(i).geteLevel()-yhplanDetail.getEnnage();
            if(count[i]<0){
                count[i]=-count[i];
            }
            id[i]=yhFoodSrecipes.get(i).getId();
        }
        double min=count[0];
        int minid=0;
        for (int j=0;j<count.length;j++){
            if(min>=count[j]){
                min=count[j];
                minid=j;
            }
        }
        //修改reg表的出生年月日
            yhplandetailmapper.updatereg(yhplanDetail);
        //查询是否拥有该usercode的数据
        if(yhplandetailmapper.selectperson(yhplanDetail)>=1){
            //拥有就修改usercode的数据，否则的话添加
            yhplandetailmapper.updateperson(yhplanDetail);
        }else{
            yhplandetailmapper.insertperson(yhplanDetail);
        }
        //创建新的食物对象
        YhFoodSrecipeRecord yhFoodSrecipeRecord=new YhFoodSrecipeRecord();
        yhFoodSrecipeRecord.setRecordData(new Date());
        yhFoodSrecipeRecord.setRecordName("健康计划推荐套餐");
        yhFoodSrecipeRecord.setRecordKay(yhFoodSrecipes.get(minid).geteLevel());
        yhFoodSrecipeRecord.setRecordType(1);
        yhFoodSrecipeRecord.setRecordMode("健康计划");
        yhFoodSrecipeRecord.setUserCode(yhplanDetail.getUsercode());
        yhFoodSrecipeRecord.setMealId(yhFoodSrecipes.get(minid).getId().toString());
        yhFoodSrecipeRecord.setIllnessId(yhplanDetail.getDiseassid().toString());
        //删除正在执行的健康计划食物推荐
        yhplandetailmapper.upatefoodlogispaln(yhplanDetail.getUsercode());
        //挂起非健康计划的食物推荐
        yhplandetailmapper.upatefoodlogisnotpaln(yhplanDetail.getUsercode());

        //插入正在执行的食物
        try {
            yhFoodSrecipeRecord.setAge(getAge(parses(yhplanDetail.getBrethday()))+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        yhFoodSrecipeRecord.setHeight(yhplanDetail.getHeight());
        yhFoodSrecipeRecord.setWeight(yhplanDetail.getWeight());
        yhFoodSrecipeRecord.setStrength(yhplanDetail.getLaodong());
        yhFoodSrecipeRecord.setSex(yhplanDetail.getSex());
        return  yhplandetailmapper.insertyhfoodlog(yhFoodSrecipeRecord);
    }
    public static  Date parses(String strDate) throws ParseException {
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
}
