package com.yihe.applet.api.service.impl;


import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.mapper.YhstopSmokingMapper;
import com.yihe.applet.api.service.YhstopSmokingService;
import com.yihe.system.domain.YhTemplateDetail;
import com.yihe.system.domain.YhUserHealthyPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class YhstopSmokingServiceImpl implements YhstopSmokingService {
    @Autowired
    YhstopSmokingMapper yhstopSmokingMapper;
    @Override
    public List<YhQuestionSmoking> selectallquetion() {
        return yhstopSmokingMapper.selectallquetion();
    }

    @Override
    public int insertqustionansers(SmokeAnswers smokeAnswers) {
        return yhstopSmokingMapper.insertqustionansers(smokeAnswers);
    }

    @Override
    public SmokeAnswers selectanswers(SmokeAnswers smokeAnswers) {
        return yhstopSmokingMapper.selectanswers(smokeAnswers);
    }

    @Override
    public Integer selecttoonce(String usercode) {
        String isnull=yhstopSmokingMapper.selecttoonce(usercode);
        if(isnull==null){
            yhstopSmokingMapper.updateonce(usercode);
            return 0;
        }else{
            return 1;
        }

    }

    @Override
    public SmokeAnswers selectanwser(SmokeAnswers smokeAnswers) {
        return yhstopSmokingMapper.selectanwser(smokeAnswers);
    }

    @Override
    public Integer insertaddsmoking(YhUserSmoking yhUserSmoking) {
        return yhstopSmokingMapper.insertaddsmoking(yhUserSmoking);
    }

    @Override
    public Integer chanplan() {
        //查询今天的需要添加的任务
        List<YhTemplateDetail> detail=yhstopSmokingMapper.detail();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis() ;
        currentTime +=12*60*60*1000;
        Date date=new Date(currentTime);


        List<YhUserHealthyPlan> yhUserHealthyPlans=yhstopSmokingMapper.selectnewplan();
        for (int k=1;k<27;k++){
            for (int j=0;j<yhUserHealthyPlans.size();j++){
                String plannum=yhstopSmokingMapper.getplanmun(yhUserHealthyPlans.get(j).getPlanDay().toString(),yhUserHealthyPlans.get(j).getPlanId().toString());
                YhUserHealthyPlan yhUserHealthyPlan=new YhUserHealthyPlan();
                yhUserHealthyPlan.setUserCode(yhUserHealthyPlans.get(j).getUserCode());
                yhUserHealthyPlan.setCourseseq(plannum.split(";").length);
                yhUserHealthyPlan.setPlanDay(yhUserHealthyPlans.get(j).getPlanDay()+1+k);
                yhUserHealthyPlan.setPlanUnique(yhUserHealthyPlans.get(j).getPlanUnique());
                yhUserHealthyPlan.setPlanId(yhUserHealthyPlans.get(j).getPlanId());
                if(yhUserHealthyPlans.get(j).getPlanDay()+1+k==28){
                    try {
                        yhUserHealthyPlans.get(j).setPlanEnddate(converdate(yhUserHealthyPlans.get(j).getPlanStartdate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                yhUserHealthyPlan.setPlandetailId(plannum);
                try {
                    yhUserHealthyPlan.setPlanStartdate(converdate(yhUserHealthyPlans.get(j).getPlanStartdate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                yhUserHealthyPlan.setPlanState(2);
                yhUserHealthyPlan.setPlanUnique(yhUserHealthyPlan.getPlanUnique());
                yhstopSmokingMapper.insertplansbyuser(yhUserHealthyPlan);
            }
        }
        return 1;
    }

    @Override
    public YhUserSmoking selecthavesmoke(String usercode) {
        if(yhstopSmokingMapper.selecthavesmoke(usercode)==null){
            return null;
        }else{
            return yhstopSmokingMapper.selecthavesmoke(usercode);
        }

    }

    @Override
    public List<YhGiveupSmokingDirectory> selectdirector() {
        return yhstopSmokingMapper.selectdirector();
    }

    @Override
    public Integer changejindu(Integer alterid, Integer parentid, String usercode) {
        //增加观看人数
        yhstopSmokingMapper.updatewatch(alterid);
        //查询是否有记录
        int count=yhstopSmokingMapper.selectcount(alterid,usercode);
        if(count==0){
            yhstopSmokingMapper.insertjilu(alterid,parentid,usercode);
        }
        return count;
    }

    @Override
    public List<Integer> selectprogrund(String id,String usercode) {
        int count=0;
        List<YhGiveupSmokingDirectory> yhGiveupSmokingDirectories=yhstopSmokingMapper.selectsmokingcount(id);
        List<Integer> list=new ArrayList<>();
        for (int i=0;i<yhGiveupSmokingDirectories.size();i++){
            Integer fofo=yhstopSmokingMapper.selectjindu(yhGiveupSmokingDirectories.get(i).getSecret_Id(),usercode);
            if(null==fofo){
                list.add(0);
            }else{
                list.add(fofo);
            }
        }
        return list;
    }

    @Override
    public YhGiveupSmokingInfo selectarticle(String id) {
        return yhstopSmokingMapper.selectarticle(id);
    }

    @Override
    public List<YhHealthLectureCommentApp> findCommentBylId(Integer lid) {
        return yhstopSmokingMapper.findCommentBylId(lid);
    }

    @Override
    public List<YhHealthLectureCommentApp> findcommmbypage(Integer lid, Integer page, String usercode) {
        return yhstopSmokingMapper.findcommmbypage(lid,page,usercode);
    }
    @Override
    public List<YhHealthLectureCommentApp> findcommmbypage2(Integer lid, Integer page, String usercode) {
        return yhstopSmokingMapper.findcommmbypage2(lid,page,usercode);
    }

    @Override
    public List<YhHealthLectureCommentApp> findCommentByPid(Integer pid) {
        return yhstopSmokingMapper.findCommentByPid(pid);
    }

    @Override
    public int insertSelective(YhHealthLectureCommentApp record) {
        return yhstopSmokingMapper.insertSelective(record);
    }

    @Override
    public YhHealthLectureCommentApp findCommentPuserCodeByParam(Integer pid) {
        return yhstopSmokingMapper.findCommentPuserCodeByParam(pid);
    }

    @Override
    public int adddianzan(String commentid, String usercode) {
        yhstopSmokingMapper.addall(commentid);
        return yhstopSmokingMapper.adddianzan(commentid,usercode);
    }

    @Override
    public int quxiaodianzan(String commentid, String usercode) {
        yhstopSmokingMapper.quxiaodianzanall(commentid);
        return yhstopSmokingMapper.quxiaodianzan(commentid,usercode);
    }

    @Override
    public String selectbeginday(String usercode) {
        return yhstopSmokingMapper.selectbeginday(usercode);
    }

    @Override
    public int stopsmokings(String usercode) {
        return yhstopSmokingMapper.stopsmokings(usercode);
    }

    @Override
    public YhSmokingReport selectyilai(String usercode) {
        return yhstopSmokingMapper.selectyilai(usercode);
    }

    @Override
    public YhSmokeCraving selectyanying(String usercode) {
        return yhstopSmokingMapper.selectyanying(usercode);
    }

    @Override
    public Integer addyanying(String usercode) {
        return yhstopSmokingMapper.addyanying(usercode);
    }

    @Override
    public Integer[] selectyanyingDuary(String usercode) {
        List<Integer> sevenyanying=yhstopSmokingMapper.selectsevenyanying(usercode);
        Integer[] qitian=new Integer[7];
        if(null!=sevenyanying){
            for (int i=0;i<qitian.length;i++){
                if(i>sevenyanying.size()-1){
                    qitian[i]=0;
                }else{
                    qitian[i]=sevenyanying.get(i);
                }
            }
        }
        return qitian;
    }

    @Override
    public Integer[] selectyanyingDuarybymothh(String usercode) {
         List<Integer> sevenyanying=yhstopSmokingMapper.selectyanyingDuarybymothh(usercode);
        Integer[] qitian=new Integer[12];
        if(null!=sevenyanying){
            for (int i=0;i<qitian.length;i++){
                if(i>sevenyanying.size()-1){
                    qitian[i]=0;
                }else{
                    qitian[i]=sevenyanying.get(i);
                }
            }
        }
        return qitian;
    }

    @Override
    public SmokeRecord selectSmokeRecord7(String usercode) {
        return yhstopSmokingMapper.selectSmokeRecord7(usercode);
    }
    @Override
    public SmokeRecord selectSmokeRecord12(String usercode) {
        return yhstopSmokingMapper.selectSmokeRecord12(usercode);
    }

    @Override
    public List<YhUserSmoking> selecytstopsmokinglog(String usercode) {
        return yhstopSmokingMapper.selecytstopsmokinglog(usercode);
    }

    @Override
    public List<YhSmokingReport> selectsmokingreport(String usercode) {
        return yhstopSmokingMapper.selectsmokingreport(usercode);
    }

    @Override
    public int findSmokingCount() {
        return yhstopSmokingMapper.findSmokingCount();
    }

    public static String converdate(String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sDate = sdf.parse(endDate);
        //System.out.println("String类型转Date类型 "+sDate);

        Format f = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Date结束日期:" + f.format(sDate));

        Calendar c = Calendar.getInstance();
        c.setTime(sDate);
        c.add(Calendar.DAY_OF_MONTH, 1);

        sDate = c.getTime();
        //System.out.println("Date结束日期+1 " +f.format(sDate));

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return endDate = sdf1.format(sDate);
    }

    public static void main(String[] args) {
        try {
            System.out.println(converdate("2020-02-30 02:03:55"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
