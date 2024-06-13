package com.yihe.system.service.impl;

import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhDivisionsettingMapper;
import com.yihe.system.mapper.YhInterventiongroupingMapper;
import com.yihe.system.service.YhInterventiongroupingService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class YhInterventiongroupingServiceImpl implements YhInterventiongroupingService{
        @Autowired
        YhInterventiongroupingMapper yhInterventiongroupingMapper;
        @Autowired
        YhDivisionsettingMapper yhDivisionsettingMapper;

    @Override
    public List<Interventiongrouping> selectnameandplan(Interventiongrouping interventiongrouping) {
        interventiongrouping.setPage((interventiongrouping.getPage()-1)*interventiongrouping.getLimit());
        List<Interventiongrouping> intertion=yhInterventiongroupingMapper.selectnameandplan(interventiongrouping);
        List<YHDocGroup> selectdoc=yhInterventiongroupingMapper.selectalldoc();
        //循环出分组
        for (int i=0;i<intertion.size();i++){
            if(intertion.get(i).getFlag_Dgroup()!=null){
                String tmeps []=intertion.get(i).getFlag_Dgroup().split(";");
                Boolean isfrist=false;
                String  lastanme="";
                for (int j=0;j<tmeps.length;j++){
                    tmeps[j]=removecahr(tmeps[j],"_");
                    tmeps[j]=removecahr(tmeps[j],"f");
                    for (int k=0;k<selectdoc.size();k++){
                        if((selectdoc.get(k).getuDocgroupId()+"").equals(tmeps[j])){
                            if(isfrist){
                                lastanme= lastanme+";"+selectdoc.get(k).getGname();
                            }else{
                                lastanme=selectdoc.get(k).getGname()+"";
                                isfrist=true;
                            }
                        }
                    }
                }
                intertion.get(i).setFlag_Dgroup(lastanme);
            }
        }
        return intertion;
    }

    @Override
    public List<YhProgramsTemplate> selectserviceplan(YhProgramsTemplate yhProgramsTemplate) {
        //首先获取操作医生的user_code
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        yhProgramsTemplate.setUsercode(yh.getUserCode());
        int count=yhInterventiongroupingMapper.selecthavebegin(yhProgramsTemplate);
        if(yhProgramsTemplate.getTemplatid()!=null&&count==0){


        //清除临时表该医生的数据
        yhInterventiongroupingMapper.deletetempbyusercode(yh.getUserCode());
        //然后把医生操作的数据导入临时表
        yhInterventiongroupingMapper.templlatetemproary(yhProgramsTemplate);
        }
        //查询临时表的数据返回
        yhProgramsTemplate.setStart((yhProgramsTemplate.getPage()-1)*yhProgramsTemplate.getLimit());
        return yhInterventiongroupingMapper.selectserviceplan(yhProgramsTemplate);
    }

    @Override
    public Integer updateischoose(YhProgramsTemplate yhProgramsTemplate) {
        if(yhProgramsTemplate.getTemporary_id()==null){
            //不是全选只改一个
            Subject subject = SecurityUtils.getSubject();
            YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
            yhProgramsTemplate.setUsercode(yh.getUserCode());
            yhProgramsTemplate.setStart((yhProgramsTemplate.getPage()-1)*yhProgramsTemplate.getLimit());
            List<Integer> yhpros=yhInterventiongroupingMapper.selectchangeid(yhProgramsTemplate);
            yhProgramsTemplate.setTemplatid(yhpros);
            return  yhInterventiongroupingMapper.changeischooes(yhProgramsTemplate);
        }else{
            return  yhInterventiongroupingMapper.changeischooesbyid(yhProgramsTemplate);
        }

    }

    @Override
    public int selecttemplatecount(YhProgramsTemplate yhProgramsTemplate) {
        return yhInterventiongroupingMapper.selecttemplatecount(yhProgramsTemplate);
    }

    @Override
    public int insertplans(YhProgramsTemplate yhProgramsTemplate) throws ParseException {
        //查询出当前医生的usercode
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        yhProgramsTemplate.setUsercode(yh.getUserCode());

        //先查出id是该医生操作的计划；
        List<YhProgramsTemplate> protemp=yhInterventiongroupingMapper.selectservicetemplateplan(yhProgramsTemplate);
        List<YhProgramsTemplate> protemp2=new ArrayList<>();
        int count=0;
        //不为空的话把每周换算成时间日期
        if(protemp!=null){
        for (int i=0;i<protemp.size();i++){
            protemp.get(i).setTime(stampToDate(yhProgramsTemplate.getTime(),protemp.get(i).getProgramsWeeks(),protemp.get(i).getProgramsDays()));
            if(protemp.get(i).getLAY_CHECKED()){
                protemp2.add(protemp.get(i));
                count++;
            }
            }
        }


        //循环要查询的ids，查询是否拥有过计划，拥有过的话就修改总计划，为拥有就插入计划，然后循环插入新的计划
        for (int j=0;j<yhProgramsTemplate.getIds().length;j++){
            yhTemplateExecution yhtemplate=yhInterventiongroupingMapper.selectservicetemplateexcution(yhProgramsTemplate.getIds()[j]);
            if(yhtemplate!=null){
                yhtemplate.setPlanCount(count+yhtemplate.getPlanCount());
                //如果最后一天比以前的挽就用最后一天的
                if(yhtemplate.getLastPlanTime()!=null&&!"".equals(yhtemplate.getLastPlanTime())){
                    if(isbig(yhtemplate.getLastPlanTime(),protemp2.get(protemp2.size()-1).getTime())<0){
                        yhtemplate.setLastPlanTime(protemp2.get(protemp2.size()-1).getTime());
                    }
                }else{
                    yhtemplate.setLastPlanTime(protemp.get(protemp.size()-1).getTime());
                }
                //修改
                yhInterventiongroupingMapper.updateexecution(yhtemplate);
            }else{
                yhTemplateExecution yhtemps=new yhTemplateExecution();
                yhtemps.setLastPlanTime(protemp.get(protemp.size()-1).getTime());
                yhtemps.setPlanCount(count);
                yhtemps.setUserCode(yhProgramsTemplate.getIds()[j]);
                yhInterventiongroupingMapper.insertexcution(yhtemps);
            }
            //循环插入分表
            for (int k=0;k<protemp.size();k++){
                if(protemp.get(k).getLAY_CHECKED()==true){
                    protemp.get(k).setUsercode(yhProgramsTemplate.getIds()[j]);
                    yhInterventiongroupingMapper.insertdetail(protemp.get(k));
                }

            }
        }


        //stampToDate(yhProgramsTemplate.getTime().toString());
        //然后再循环插进每个正式表里面；
        return 0;
    }

    @Override
    public int addtemplatetempro(YhProgramsTemplate yhProgramsTemplate) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        yhProgramsTemplate.setUsercode(yh.getUserCode());
        yhProgramsTemplate.setLAY_CHECKE(true+"");
        return yhInterventiongroupingMapper.addtemplatetempro(yhProgramsTemplate);
    }

    @Override
    public int updatetemplatetempro(YhProgramsTemplate yhProgramsTemplate) {
        return yhInterventiongroupingMapper.updatetemplatetempro(yhProgramsTemplate);
    }

    @Override
    public void insertservice() {
        //查询今天的需要添加的任务
        List<YhTemplateDetail> detail=yhInterventiongroupingMapper.detail();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis() ;
        currentTime +=12*60*60*1000;
        Date date=new Date(currentTime);
        if(detail !=null){
            for (int i=0;i<detail.size();i++){
                detail.get(i).setInsert_time(df.format(new Date()));
                detail.get(i).setWatting_time(df.format(date));
                detail.get(i).setService_state(0);
                detail.get(i).setService_type(26);
                detail.get(i).setTotaltype(3);
                //查询服务任务总表里面是否有过数据
                int count= yhInterventiongroupingMapper.selectiscount(detail.get(i));
                //count数减一
                yhInterventiongroupingMapper.deletetemplat(detail.get(i).getUserCode());
                if(count>0){
                    yhInterventiongroupingMapper.insertserviceperson(detail.get(i));
                }else{
                    yhInterventiongroupingMapper.inserttotalperson(detail.get(i));
                    yhInterventiongroupingMapper.insertserviceperson(detail.get(i));
                }

            }
        }





    }

    @Override
    public void wattingservice() {
        List<YhJzServiceSwitch> thjz=yhInterventiongroupingMapper.selectallswich();

        //问卷久未评估
        List<YhJzServiceAssessmentSwitch> ysas=yhInterventiongroupingMapper.selectallysas();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis() ;
        currentTime +=12*60*60*1000;
        Date date=new Date(currentTime);
        for (int i=0;i<ysas.size();i++){
            String times;
            if(ysas.get(i).getLong_time_no_intervaltime()!=null){
                times=ysas.get(i).getLong_time_no_intervaltime().toString();
            }else{
                times="";
            }

            if(ysas.get(i).getName().equals("健康风险评估")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(7).getIsopen()==1) {
                //如果开了的话就查询参与过健康风险评估并且时间是等于等待天数的
                //查询出这个日期
                System.out.println(times);
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectpingguwatting(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(7).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(8);
                    yhJzService.get(j).setServiceContent("该用户"+ getday(yhJzService.get(j).getServiceWaitingTime())+"天未进行健康风险评估;上次评估时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));

                    yhJzService.get(j).setServiceRecord(thjz.get(7).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(7).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setChildnodes(1);
                    yhJzService.get(j).setChildtag(2);
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }else if(ysas.get(i).getName().equals("健康习惯评估")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(7).getIsopen()==1){
                System.out.println(times);
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectpingguhabit(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(7).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(8);

                    yhJzService.get(j).setServiceContent("该用户"+getday(yhJzService.get(j).getServiceWaitingTime())+"天未进行健康习惯评估;上次评估时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceRecord(thjz.get(7).getService_describe());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(7).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setChildnodes(3);
                    yhJzService.get(j).setChildtag(2);
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }else if(ysas.get(i).getName().equals("睡眠质量评估")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(7).getIsopen()==1){
                System.out.println(times);
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectpinggusleep(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(7).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(8);

                    yhJzService.get(j).setServiceContent("该用户"+getday(yhJzService.get(j).getServiceWaitingTime())+"天未进行睡眠质量评估;上次评估时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));
                    yhJzService.get(j).setServiceRecord(thjz.get(7).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(7).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setChildnodes(2);
                    yhJzService.get(j).setChildtag(2);
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }else if(ysas.get(i).getName().equals("A型性格评估")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(7).getIsopen()==1){
                System.out.println(times);
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectpingguaxing(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(7).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(8);

                    yhJzService.get(j).setServiceContent("该用户"+getday(yhJzService.get(j).getServiceWaitingTime())+"天未进行A型性格评估;上次评估时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));
                    yhJzService.get(j).setServiceRecord(thjz.get(7).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(7).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setChildnodes(4);
                    yhJzService.get(j).setChildtag(2);
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }else if(ysas.get(i).getName().equals("PSTRI压力评估")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(7).getIsopen()==1){
                System.out.println(times);
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectpinggupst(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(7).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(8);

                    yhJzService.get(j).setServiceContent("该用户"+getday(yhJzService.get(j).getServiceWaitingTime())+"天未进行PSTRI压力评估;上次评估时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));
                    yhJzService.get(j).setServiceRecord(thjz.get(7).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(7).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setChildnodes(5);
                    yhJzService.get(j).setChildtag(2);
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }else if(ysas.get(i).getName().equals("心理健康评估")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(7).getIsopen()==1){
                System.out.println(times);
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectpingxinli(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(7).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(8);

                    yhJzService.get(j).setServiceContent("该用户"+getday(yhJzService.get(j).getServiceWaitingTime())+"天未进行心理健康评估;上次评估时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));
                    yhJzService.get(j).setServiceRecord(thjz.get(7).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(7).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setChildnodes(6);
                    yhJzService.get(j).setChildtag(2);
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }

            }else if(ysas.get(i).getName().equals("饮食日记")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(9).getIsopen()==1){
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectyinshiriji(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(9).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(10);

                    yhJzService.get(j).setServiceContent("该用户"+getday(yhJzService.get(j).getServiceWaitingTime())+"天未记录饮食日记;上次记录时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));
                    yhJzService.get(j).setServiceRecord(thjz.get(9).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(9).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setServiceSubtitle("饮食日记");
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }else if(ysas.get(i).getName().equals("运动日记")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(9).getIsopen()==1){
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectyundongriji(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(9).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(10);

                    yhJzService.get(j).setServiceContent("该用户"+getday(yhJzService.get(j).getServiceWaitingTime())+"天未记录运动日记;上次记录时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(date));
                    yhJzService.get(j).setServiceRecord(thjz.get(9).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(9).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setServiceSubtitle("运动日记");
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }else if(ysas.get(i).getName().equals("睡眠日记")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(9).getIsopen()==1){
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectshuimian(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(9).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(10);

                    yhJzService.get(j).setServiceContent("该用户"+getday(yhJzService.get(j).getServiceWaitingTime())+"天未记录睡眠日记;上次记录时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));
                    yhJzService.get(j).setServiceRecord(thjz.get(9).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(9).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setServiceSubtitle("睡眠日记");
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }else if(ysas.get(i).getName().equals("习惯日记")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(9).getIsopen()==1){
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectxiguan(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(9).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(10);
                    yhJzService.get(j).setServiceContent("该用户"+getday(yhJzService.get(j).getServiceWaitingTime())+"天未记录习惯日记;上次记录时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));
                    yhJzService.get(j).setServiceRecord(thjz.get(9).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(9).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setServiceSubtitle("习惯日记");
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }else if(ysas.get(i).getName().equals("健康计划日记")&&ysas.get(i).getLong_time_no_switch()==1&&thjz.get(9).getIsopen()==1){
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectjiankangjihua(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(9).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(10);
                    yhJzService.get(j).setServiceContent("该用户"+getday(yhJzService.get(j).getServiceWaitingTime())+"天未记录健康计划日记;上次记录时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));
                    yhJzService.get(j).setServiceRecord(thjz.get(9).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(9).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setServiceSubtitle("健康计划日记");
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }

        }

        //久未检测
        List<YhJzServiceBodydataSwitch> bodyseich=yhDivisionsettingMapper.selectbodyswitch();
        for(int h=0;h<bodyseich.size();h++){
            String times;
            if(bodyseich.get(h).getLong_time_no_intervaltime()!=null){
                times=bodyseich.get(h).getLong_time_no_intervaltime().toString();
            }else{
                times="";
            }
            if(bodyseich.get(h).getName().equals("血压")&&bodyseich.get(h).getLong_time_no_switch()==1&&thjz.get(4).getIsopen()==1) {
                //如果开了的话就查询参与过健康风险评估并且时间是等于等待天数的
                //查询出这个日期
                System.out.println(times);
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectyueya(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(4).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(5);
                    yhJzService.get(j).setServiceContent("该用户"+ getday(yhJzService.get(j).getServiceWaitingTime())+"天未进行血压检测;上次检测时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));

                    yhJzService.get(j).setServiceRecord(thjz.get(4).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(4).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setChildnodes(1);
                    yhJzService.get(j).setChildtag(1);
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }

            if(bodyseich.get(h).getName().equals("血糖")&&bodyseich.get(h).getLong_time_no_switch()==1&&thjz.get(4).getIsopen()==1){
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectxuetang(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(4).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(5);
                    yhJzService.get(j).setServiceContent("该用户"+ getday(yhJzService.get(j).getServiceWaitingTime())+"天未进行血糖检测;上次检测时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));

                    yhJzService.get(j).setServiceRecord(thjz.get(4).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(4).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setChildnodes(2);
                    yhJzService.get(j).setChildtag(1);
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }
            if(bodyseich.get(h).getName().equals("血脂四项")&&bodyseich.get(h).getLong_time_no_switch()==1&&thjz.get(4).getIsopen()==1){
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selectxuezhi(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(4).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(5);
                    yhJzService.get(j).setServiceContent("该用户"+ getday(yhJzService.get(j).getServiceWaitingTime())+"天未进行血脂四项检测;上次检测时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));

                    yhJzService.get(j).setServiceRecord(thjz.get(4).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(4).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setChildnodes(3);
                    yhJzService.get(j).setChildtag(1);
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }

            if(bodyseich.get(h).getName().equals("体重")&&bodyseich.get(h).getLong_time_no_switch()==1&&thjz.get(4).getIsopen()==1){
                List<YhJzServicePerson> yhJzService=yhInterventiongroupingMapper.selecttizhong(times);
                long currentTimes = System.currentTimeMillis() ;
                currentTimes +=thjz.get(4).getService_date()*60*60*1000;
                Date dates=new Date(currentTimes);
                for (int j=0;j<yhJzService.size();j++){
                    yhJzService.get(j).setServiceType(5);
                    yhJzService.get(j).setServiceContent("该用户"+ getday(yhJzService.get(j).getServiceWaitingTime())+"天未进行体重检测;上次检测时间"+yhJzService.get(j).getServiceWaitingTime());
                    yhJzService.get(j).setServiceWaitingTime(df.format(dates));

                    yhJzService.get(j).setServiceRecord(thjz.get(4).getService_describe());
                    yhJzService.get(j).setIsrede(1);
                    yhJzService.get(j).setReturnvisit(thjz.get(4).getReturn_id());
                    yhJzService.get(j).setInsertTime(df.format(new Date()));
                    yhJzService.get(j).setChildnodes(4);
                    yhJzService.get(j).setChildtag(1);
                    yhJzService.get(j).setTotaltype(2);
                    yhInterventiongroupingMapper.insertSelectiveperson(yhJzService.get(j));
                }
            }



        }

    }


    public String removecahr(String str,String chrt) {
        if(str.equals("0") || str.equals("")){
            return "";
        }else{
            if (chrt.equals("_")) {
                int loc = str.indexOf(chrt);
                return str.substring(0, loc);
            } else {
                int loc = str.indexOf(chrt);
                return str.substring(loc+1);
            }
        }
    }

    public Long getday(String dstr){
        SimpleDateFormat sdf=null;
        if(dstr.length()>12){
             sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else{
             sdf=new SimpleDateFormat("yyyy-MM-dd");
        }

        Date date= null;
        try {
            date = sdf.parse(dstr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long s1=date.getTime();//将时间bai转为毫秒du
        long s2=System.currentTimeMillis();//得到当zhi前的毫秒
        Long day=(s2-s1)/1000/60/60/24;
        return day;
    }



    /**
     *
     * @param s 日期开始时间戳
     */
    public  String stampToDate(String s,Integer week,Integer day) throws ParseException {
        String res;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        res=stampToDate(s);
        Date dates = simpleDateFormat.parse(res, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dates);
        calendar.add(Calendar.DATE, (week-1)*7+day-1);
        Date date1 = calendar.getTime();
        String out = simpleDateFormat.format(date1);
        return out;
    }
    public  long dateToStamp(String s) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }

    public  String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    public Long isbig(String lastTime,String nowTime) throws ParseException {
        String differenceFormat = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
        nowTime=nowTime+" 00:00:00";
        Date dateTime1 = sdf.parse(lastTime);
        Date dateTime2 = sdf.parse(nowTime);
        long difference = dateTime1.getTime() - dateTime2.getTime();
        System.out.println("相差毫秒数："+difference);
        return difference;
    }

    public static String plusDay2(int num){
                 Date d = new Date();
                 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 String currdate = format.format(d);
                 System.out.println("现在的日期是：" + currdate);

                 Calendar ca = Calendar.getInstance();
                 ca.add(Calendar.DATE, -num);// num为增加的天数，可以改变的
                 d = ca.getTime();
                 String enddate = format.format(d);
                 System.out.println("增加天数以后的日期：" + enddate);
                 return enddate;
             }
}
