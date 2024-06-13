package com.yihe.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhChineseAnwserr;
import com.yihe.system.mapper.YhcheidentificationMapper;
import com.yihe.system.service.YhcheidentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class YhcheidentificationServiceImpl implements YhcheidentificationService {
    @Autowired
    YhcheidentificationMapper yhcheidentificationMapper;

    @Override
    public YhChineseAnwserr selectByPrimaryKey(Integer reportId) {
        YhChineseAnwserr yhChineseAnwserr= yhcheidentificationMapper.selectByPrimaryKey(reportId);
        yhChineseAnwserr.setTimes(yearDateDiff(yhChineseAnwserr.getBirth_date(),yhChineseAnwserr.getTime())+"");
        return yhChineseAnwserr;
    }

    public static void main(String[] args) {
        System.out.println(yearDateDiff("2027-01-02","2021-01-03"));
    }
    public static int yearDateDiff(String startDate,String endDate){
        Calendar calBegin = Calendar.getInstance(); //获取日历实例
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(stringTodate(startDate,"yyyy")); //字符串按照指定格式转化为日期
        calEnd.setTime(stringTodate(endDate,"yyyy"));
        return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
    }

    public static Date stringTodate(String dateStr, String formatStr) {
        // 如果时间为空则默认当前时间
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        if (dateStr != null && !dateStr.equals("")) {
            String time = "";
            try {
                Date dateTwo = format.parse(dateStr);
                time = format.format(dateTwo);
                date = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            String timeTwo = format.format(new Date());
            try {
                date = format.parse(timeTwo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }





    @Override
    public PageInfo<YhChineseAnwserr> queryUserByCriteriaWithPage(String userCode, String qtstrTime, String qtendTime, int page, int limit) {
        //设置分页参数
        PageHelper.startPage(page,limit);
        // TODO Auto-generated method stub
        List<YhChineseAnwserr> list = yhcheidentificationMapper.selectUserYhPainTotalReportParam(userCode, qtstrTime,qtendTime);
        for (int i=0;i<list.size();i++){
            if(list.get(i).getaIstype()==1){
                list.get(i).setIspinghe("是");
            }else if(list.get(i).getaIstype()==2){
                list.get(i).setIspinghe("基本是");
            }else{
                list.get(i).setIspinghe("不是");
            }
            int times=0;
            int times2=0;
            if(list.get(i).getbIstype()==1){
                list.get(i).setIspianposhi("气虚质");
                times++;
            }else if(list.get(i).getbIstype()==2){
                list.get(i).setIspianpopianxiangshi("气虚质");
                times2++;
            }

            if(list.get(i).getcIstype()==1){
                if(times==0){
                    list.get(i).setIspianposhi("阳虚质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";阳虚质");
                }
                times++;
            }else if(list.get(i).getcIstype()==2){
                if(times2==0){
                    list.get(i).setIspianpopianxiangshi("阳虚质");
                }else{
                    list.get(i).setIspianpopianxiangshi(list.get(i).getIspianpopianxiangshi()+";阳虚质");
                }
                times2++;
            }

            if(list.get(i).getdIstype()==1){
                if(times==0){
                    list.get(i).setIspianposhi("阴虚质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";阴虚质");
                }
                times++;
            }else if(list.get(i).getdIstype()==2){
                if(times2==0){
                    list.get(i).setIspianpopianxiangshi("阴虚质");
                }else{
                    list.get(i).setIspianpopianxiangshi(list.get(i).getIspianpopianxiangshi()+";阴虚质");
                }
                times2++;
            }

            if(list.get(i).geteIstype()==1){
                if(times==0){
                    list.get(i).setIspianposhi("痰湿质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";痰湿质");
                }
                times++;
            }else if(list.get(i).geteIstype()==2){
                if(times2==0){
                    list.get(i).setIspianpopianxiangshi("痰湿质");
                }else{
                    list.get(i).setIspianpopianxiangshi(list.get(i).getIspianpopianxiangshi()+";痰湿质");
                }
                times2++;
            }

            if(list.get(i).getfIstype()==1){
                if(times==0){
                    list.get(i).setIspianposhi("湿热质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";湿热质");
                }
                times++;
            }else if(list.get(i).getfIstype()==2){
                if(times2==0){
                    list.get(i).setIspianpopianxiangshi("湿热质");
                }else{
                    list.get(i).setIspianpopianxiangshi(list.get(i).getIspianpopianxiangshi()+";湿热质");
                }
                times2++;
            }


            if(list.get(i).getgIstype()==1){
                if(times==0){
                    list.get(i).setIspianposhi("血瘀质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";血瘀质");
                }
                times++;
            }else if(list.get(i).getgIstype()==2){
                if(times2==0){
                    list.get(i).setIspianpopianxiangshi("血瘀质");
                }else{
                    list.get(i).setIspianpopianxiangshi(list.get(i).getIspianpopianxiangshi()+";血瘀质");
                }
                times2++;
            }

            if(list.get(i).gethIstype()==1){
                if(times==0){
                    list.get(i).setIspianposhi("气郁质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";气郁质");
                }
                times++;
            }else if(list.get(i).gethIstype()==2){
                if(times2==0){
                    list.get(i).setIspianpopianxiangshi("气郁质");
                }else{
                    list.get(i).setIspianpopianxiangshi(list.get(i).getIspianpopianxiangshi()+";气郁质");
                }
                times2++;
            }

            if(list.get(i).getiIstype()==1){
                if(times==0){
                    list.get(i).setIspianposhi("特禀质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";特禀质");
                }
                times++;
            }else if(list.get(i).getiIstype()==2){
                if(times2==0){
                    list.get(i).setIspianpopianxiangshi("特禀质");
                }else{
                    list.get(i).setIspianpopianxiangshi(list.get(i).getIspianpopianxiangshi()+";特禀质");
                }
                times2++;
            }

            if( list.get(i).getIspianposhi()==null){
                list.get(i).setIspianposhi("--");
            }
            if( list.get(i).getIspianpopianxiangshi()==null){
                list.get(i).setIspianpopianxiangshi("--");
            }

        }
        return new PageInfo<YhChineseAnwserr>(list);
    }

    @Override
    public PageInfo<YhChineseAnwserr> queryAllUserByCriteriaWithPage(String symptom, String orgCode, String name, String userCode, String qtstrTime, String qtendTime, int page, int limit) {
        //设置分页参数
        PageHelper.startPage(page,limit);
        List<YhChineseAnwserr> list = yhcheidentificationMapper.selectUserAllYhPainTotalReportParam(symptom,orgCode,name,userCode, qtstrTime, qtendTime);
        for (int i=0;i<list.size();i++){
            if(list.get(i).getaIstype()==1){
                list.get(i).setIspinghe("是");
            }else if(list.get(i).getaIstype()==2){
                list.get(i).setIspinghe("基本是");
            }else{
                list.get(i).setIspinghe("不是");
            }
            int times=0;
            int times2=0;
            if(list.get(i).getbIstype()==1){
                times++;
                list.get(i).setIspianposhi("气虚质");
            }else if(list.get(i).getbIstype()==2){
                times2++;
                list.get(i).setIspianpopianxiangshi("气虚质");
            }

            if(list.get(i).getcIstype()==1){
                times++;
                if(times==0){
                    list.get(i).setIspianposhi("阳虚质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";阳虚质");
                }
            }else if(list.get(i).getcIstype()==2){
                times2++;
                if(times2==0){
                    list.get(i).setIspianposhi("阳虚质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianpopianxiangshi()+";阳虚质");
                }
            }

            if(list.get(i).getdIstype()==1){
                times++;
                if(times==0){
                    list.get(i).setIspianposhi("阴虚质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";阴虚质");
                }
            }else if(list.get(i).getdIstype()==2){
                times2++;
                if(times2==0){
                    list.get(i).setIspianposhi("阴虚质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianpopianxiangshi()+";阴虚质");
                }
            }

            if(list.get(i).geteIstype()==1){
                times++;
                if(times==0){
                    list.get(i).setIspianposhi("痰湿质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";痰湿质");
                }
            }else if(list.get(i).geteIstype()==2){
                times2++;
                if(times2==0){
                    list.get(i).setIspianposhi("痰湿质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianpopianxiangshi()+";痰湿质");
                }
            }

            if(list.get(i).getfIstype()==1){
                times++;
                if(times==0){
                    list.get(i).setIspianposhi("湿热质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";湿热质");
                }
            }else if(list.get(i).getfIstype()==2){
                times2++;
                if(times2==0){
                    list.get(i).setIspianposhi("湿热质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianpopianxiangshi()+";湿热质");
                }
            }


            if(list.get(i).getgIstype()==1){
                times++;
                if(times==0){
                    list.get(i).setIspianposhi("血瘀质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";血瘀质");
                }
            }else if(list.get(i).getgIstype()==2){
                times2++;
                if(times2==0){
                    list.get(i).setIspianposhi("血瘀质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianpopianxiangshi()+";血瘀质");
                }
            }

            if(list.get(i).gethIstype()==1){
                times++;
                if(times==0){
                    list.get(i).setIspianposhi("气郁质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";气郁质");
                }
            }else if(list.get(i).gethIstype()==2){
                times2++;
                if(times2==0){
                    list.get(i).setIspianposhi("气郁质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianpopianxiangshi()+";气郁质");
                }
            }

            if(list.get(i).getiIstype()==1){
                times++;
                if(times==0){
                    list.get(i).setIspianposhi("特禀质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianposhi()+";特禀质");
                }
            }else if(list.get(i).getiIstype()==2){
                times2++;
                if(times2==0){
                    list.get(i).setIspianposhi("特禀质");
                }else{
                    list.get(i).setIspianposhi(list.get(i).getIspianpopianxiangshi()+";特禀质");
                }
            }

            if( list.get(i).getIspianposhi()==null){
                list.get(i).setIspianposhi("--");
            }
            if( list.get(i).getIspianpopianxiangshi()==null){
                list.get(i).setIspianpopianxiangshi("--");
            }

        }
        return new PageInfo<YhChineseAnwserr>(list);
    }
}
