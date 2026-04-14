package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.applet.api.service.YhstopSmokingService;
import com.yihe.common.utils.http.HttpUtils;
import com.yihe.system.mapper.YhInterventiongroupingMapper;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/YhstopSmokingController")
@Api(value = "YhstopSmokingController", description = "YhstopSmokingController")
public class YhstopSmokingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YhstopSmokingController.class);
    @Autowired
    YhstopSmokingService yhstopSmokingService;
    @Autowired
    YhInterventiongroupingMapper yhInterventiongroupingMapper;
    @Autowired
    com.yihe.applet.api.service.YhUserReginfoAppService YhUserReginfoAppService;
    @Autowired
    com.yihe.applet.api.service.YhWxSettingService YhWxSettingService;
    @Autowired
    YhUserReginfoAppService YhUserReginfoService;
    /***
     * 查询戒烟的问卷
     * @return
     */
    @RequestMapping(value="/selectsmokingquestion",method= RequestMethod.POST)
    @ResponseBody
    public List<Smokequestion> showquestionnaire(){
        List<Smokequestion> question=new ArrayList<Smokequestion>();
        List<YhQuestionSmoking> yhQuestionSmokings=yhstopSmokingService.selectallquetion();
        for (int i=0;i<yhQuestionSmokings.size();i++){
            List<YhQuestionSmokingSon> top=new ArrayList<YhQuestionSmokingSon>();
            Smokequestion smokequestion=new Smokequestion();
            smokequestion.setId(yhQuestionSmokings.get(i).getQuestion_id());
            smokequestion.setTitle(yhQuestionSmokings.get(i).getQuestion_title());
            smokequestion.setzIndex(100-i);
            if(yhQuestionSmokings.get(i).getChoose1()!=null &&!yhQuestionSmokings.get(i).getChoose1().equals("")){
                YhQuestionSmokingSon son=new YhQuestionSmokingSon();
                son.setSelect(false);
                son.setScoreid(yhQuestionSmokings.get(i).getQuestion_id()+1);
                son.setScore(yhQuestionSmokings.get(i).getChoose1_value());
                son.setTopic(yhQuestionSmokings.get(i).getChoose1());
                top.add(son);
            }

            if(yhQuestionSmokings.get(i).getChoose2()!=null &&!yhQuestionSmokings.get(i).getChoose2().equals("")){
                YhQuestionSmokingSon son=new YhQuestionSmokingSon();
                son.setSelect(false);
                son.setScoreid(yhQuestionSmokings.get(i).getQuestion_id()+2);
                son.setScore(yhQuestionSmokings.get(i).getChoose2_value());
                son.setTopic(yhQuestionSmokings.get(i).getChoose2());
                top.add(son);
            }


            if(yhQuestionSmokings.get(i).getChoose3()!=null &&!yhQuestionSmokings.get(i).getChoose3().equals("")){
                YhQuestionSmokingSon son=new YhQuestionSmokingSon();
                son.setSelect(false);
                son.setScoreid(yhQuestionSmokings.get(i).getQuestion_id()+3);
                son.setScore(yhQuestionSmokings.get(i).getChoose3_value());
                son.setTopic(yhQuestionSmokings.get(i).getChoose3());
                top.add(son);
            }

            if(yhQuestionSmokings.get(i).getChoose4()!=null &&!yhQuestionSmokings.get(i).getChoose4().equals("")){
                YhQuestionSmokingSon son=new YhQuestionSmokingSon();
                son.setSelect(false);
                son.setScoreid(yhQuestionSmokings.get(i).getQuestion_id()+4);
                son.setScore(yhQuestionSmokings.get(i).getChoose4_value());
                son.setTopic(yhQuestionSmokings.get(i).getChoose4());
                top.add(son);
            }
            smokequestion.setSonquestion(top);
            question.add(smokequestion);
        }

        return question;
    }

    /***
     * 添加尼古丁依赖的评估结果
     * @param smokeAnswers
     * @return
     */
    @RequestMapping(value="/addsmokequetion",method= RequestMethod.POST)
    @ResponseBody
    public int addsmokequetion(SmokeAnswers smokeAnswers){
        int allscore=smokeAnswers.getAnswers1()+smokeAnswers.getAnswers2()+smokeAnswers.getAnswers3()+smokeAnswers.getAnswers4()+smokeAnswers.getAnswers5()+smokeAnswers.getAnswers6();
        if(allscore<=3){
            smokeAnswers.setSmoking_report_desc("尽管已经染上烟瘾，自己却没有意识到。这并不值得羡慕，很有可能成为重度吸烟者。记住，所有酒鬼都是从偶尔贪杯开始的，烟鬼也是一样。;\n" +
                    "曾经是重度吸烟者，经过减量法戒烟变成轻度吸烟者，这样的吸烟者最为可怜。;\n" +
                    "吸烟是一种连锁反应，如果你不主动戒烟的话，它会终生纠缠着你。;\n" +
                    "戒烟不仅不会导致生活质量下降，还能让身体更健康，精力更充沛，生活更充实、更有情趣。\n");
            smokeAnswers.setSmoking_report_level("轻度依赖");
        }else if(allscore<=6){
            smokeAnswers.setSmoking_report_desc("是时候考虑一辈子的吸烟费用了，你不妨按年算算花费。您也许会说出得起这笔钱，但您真的不在乎花大笔的金钱破坏自己的生活吗。;\n" +
                    "从现在开始戒烟，在烟瘾来袭的时候在系统上学习克制烟瘾的方法，以正确轻松的科学方法实施戒烟。;\n" +
                    "不要对戒断期的痛苦谈之色变，事实上，戒断期的痛苦并不是生理层面的疼痛，而是精神层面的自我折磨。\n");
            smokeAnswers.setSmoking_report_level("中度依赖");
        }else if(allscore>=7){
            smokeAnswers.setSmoking_report_desc("您的生命健康已受侵害，为了自己及关心您的人，务必要戒烟。;\n" +
                    "每天20支烟的吸烟者，一辈子至少要花掉10万块钱。把钱扔进垃圾箱，或者点火烧掉，也比花钱毒害自己的身体来得好。;\n" +
                    "不要再做烟瘾的奴隶，任由自己摧毁自己的健康和生命。\n");
            smokeAnswers.setSmoking_report_level("重度依赖");
        }
        smokeAnswers.setSmoking_report_queselect(smokeAnswers.getAnswers1()+";"+smokeAnswers.getAnswers2()+";"+smokeAnswers.getAnswers3()+";"+smokeAnswers.getAnswers4()+";"+smokeAnswers.getAnswers5()+";"+smokeAnswers.getAnswers6());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        smokeAnswers.setQtime(df.format(new Date()));
        smokeAnswers.setSmoking_report_score(allscore);
        return yhstopSmokingService.insertqustionansers(smokeAnswers);
    }


    /***
     * 查询评估结果
     * @param smokeAnswers
     * @return
     */
    @RequestMapping(value="/selectnowanser",method= RequestMethod.POST)
    @ResponseBody
    private SmokeAnswers selectnowanser(SmokeAnswers smokeAnswers){

        return yhstopSmokingService.selectanswers(smokeAnswers);
    }

    /***
     * 是否第一次进秘籍
     * @param usercode
     * @return
     */
    @RequestMapping(value="/selecttoonce",method= RequestMethod.POST)
    @ResponseBody
    private Integer selecttoonce(String usercode){
        Integer istrue=  yhstopSmokingService.selecttoonce(usercode);
        return istrue;
    }

    /***
     * 查询评估结果byid
     * @param smokeAnswers
     * @return
     */
    @RequestMapping(value="/selectanwser",method= RequestMethod.POST)
    @ResponseBody
    private SmokeAnswers selectanwser(SmokeAnswers smokeAnswers){

        return yhstopSmokingService.selectanwser(smokeAnswers);
    }

    /***
     * 添加戒烟计时数据
     * @param yhUserSmoking
     * @return
     */
    @RequestMapping(value="/insertaddsmoking",method= RequestMethod.POST)
    @ResponseBody
    private Integer insertaddsmoking(YhUserSmoking yhUserSmoking){

        return yhstopSmokingService.insertaddsmoking(yhUserSmoking);
    }
    //改变干预计划
    @RequestMapping(value="/chanplan",method= RequestMethod.GET)
    @ResponseBody
    private Integer chanplan(){
        return yhstopSmokingService.chanplan();
    }

    //改变干预计划
    @RequestMapping(value="/selecthavesmoke")
    @ResponseBody
    private YhUserSmoking selecthavesmoke(String user_code){
        return yhstopSmokingService.selecthavesmoke(user_code);
    }


    //查询戒烟秘籍的目录
    @RequestMapping(value="/selectdirector")
    @ResponseBody
    private List<YhGiveupSmokingDirectory> selectdirector(){
        return yhstopSmokingService.selectdirector();
    }


    @RequestMapping(value="/changejindu")
    @ResponseBody
    private Integer changejindu(Integer alterid,Integer parentid,String usercode){
        return yhstopSmokingService.changejindu(alterid,parentid,usercode);
    }
    //查询戒烟秘籍的进度
    @RequestMapping(value="/selectprogrund")
    @ResponseBody
    private List<Integer> selectprogrund(String id,String usercode){
        return yhstopSmokingService.selectprogrund(id,usercode);
    }

    //根据文章的id查询文章
    @RequestMapping(value="/selectarticle")
    @ResponseBody
    private YhGiveupSmokingInfo selectarticle(String id){
        return yhstopSmokingService.selectarticle(id);
    }

    //根据文章的id查询文章评论
    @RequestMapping(value = "/findsmokingInfofindInfoAllComById")
    @ResponseBody
    public Result findsmokingInfofindInfoAllComById(Integer categoryId) throws ParseException {
        if (null == categoryId) {
            return Result.error();
        }
        // 查询评论

        List<YhHealthLectureCommentApp> selectByExample = yhstopSmokingService.findCommentBylId(categoryId);
        if (null != selectByExample && selectByExample.size() > 0) {
            for (int i = 0; i < selectByExample.size(); i++) {
                // 查询评论楼中楼

                List<YhHealthLectureCommentApp> selectByExample2 = yhstopSmokingService.findCommentByPid(selectByExample.get(i).getCommentid());
                if (null != selectByExample2 && selectByExample2.size() > 3) {
                    selectByExample.get(i).setIsExceed(false);
                } else {
                    selectByExample.get(i).setIsExceed(true);
                }

                selectByExample.get(i).setComsList(selectByExample2);
                String userCode = selectByExample.get(i).getUsercode();
                YhUserReginfoApp findYhUserReginfo = YhUserReginfoAppService.findYhUserReginfo(userCode);
                if(findYhUserReginfo.getGender()==1) {
                    selectByExample.get(i).setDefaultImg("https://qny.guyijk.com/xiaochengxu/toxiangnan.png");
                }

                if(findYhUserReginfo.getGender()==2) {
                    selectByExample.get(i).setDefaultImg("https://qny.guyijk.com/xiaochengxu/toxiangnv.png");
                }

            }
        }

        return Result.ok(selectByExample);
    }

    //根据文章的id查询文章评论
    @RequestMapping(value = "/findcommmbypage")
    @ResponseBody
    public Result findcommmbypage(Integer categoryId,Integer page,String usercode) throws ParseException {
        if (null == categoryId) {
            return Result.error();
        }
        // 查询评论

        List<YhHealthLectureCommentApp> selectByExample = yhstopSmokingService.findcommmbypage(categoryId,page,usercode);
        List<YhHealthLectureCommentApp> selectByExample4 = yhstopSmokingService.findcommmbypage2(categoryId,page,usercode);
        for (int i=0;i<selectByExample.size();i++){
            for (int j=0;j<selectByExample4.size();j++){
                if(selectByExample.get(i).getCommentid()==selectByExample4.get(j).getCommentid()){
                    selectByExample.set(i,selectByExample4.get(j));
                }
            }
        }
        if (null != selectByExample && selectByExample.size() > 0) {
            for (int i = 0; i < selectByExample.size(); i++) {
                // 查询评论楼中楼

                List<YhHealthLectureCommentApp> selectByExample2 = yhstopSmokingService.findCommentByPid(selectByExample.get(i).getCommentid());
                if (null != selectByExample2 && selectByExample2.size() > 3) {
                    selectByExample.get(i).setIsExceed(false);
                } else {
                    selectByExample.get(i).setIsExceed(true);
                }

                selectByExample.get(i).setComsList(selectByExample2);
                String userCode = selectByExample.get(i).getUsercode();
                YhUserReginfoApp findYhUserReginfo = YhUserReginfoAppService.findYhUserReginfo(userCode);
                if(findYhUserReginfo.getGender()==1) {
                    selectByExample.get(i).setDefaultImg("https://qny.guyijk.com/xiaochengxu/toxiangnan.png");
                }

                if(findYhUserReginfo.getGender()==2) {
                    selectByExample.get(i).setDefaultImg("https://qny.guyijk.com/xiaochengxu/toxiangnv.png");
                }

            }
        }

        return Result.ok(selectByExample);
    }

    //根据文章的id添加文章评论
    @RequestMapping(value = "/addCommentbysmoking")
    @ResponseBody
    public Result addComment(YhHealthLectureCommentApp record) {
        if (null == record) {
            return Result.error();
        }
        record.setCommenttime(NowString());
        if (!checkContent(record.getCommentinfo())) {
            return Result.error("文字内容违规");
        }
        int insertSelective = yhstopSmokingService.insertSelective(record);

        YhHealthLectureCommentApp commentApp = yhstopSmokingService.findCommentPuserCodeByParam(record.getPid());
//        YhUserNotifyApp yhUserNotifyApp = new YhUserNotifyApp();
//        yhUserNotifyApp.setUserId(commentApp.getUsercode());
//        yhUserNotifyApp.setNewsId(record.getPid());
//        yhUserNotifyApp.setLectureinfoid(record.getCommentid());
//        yhUserNotifyApp.setTitle("课堂评论");
//        yhUserNotifyApp.setContent("回复了你的课堂评论，立即查看详情>>");
//        yhUserNotifyApp.setToServiceId(6);
//        yhUserNotifyApp.setTyepe(2);
//        yhUserNotifyApp.setIsRead(false);
//        yhUserNotifyApp.setSendDate(new Date());
//        yhUserNotifyAppService.insert(yhUserNotifyApp);
        YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(record.getUsercode());
        record.setName(findYhUserReginfo.getName());
        return Result.ok(record);
    }

    //点赞
    @RequestMapping(value="/adddianzan")
    @ResponseBody
    private int adddianzan(String commentid,String usercode){
        return yhstopSmokingService.adddianzan(commentid,usercode);
    }

    //点赞
    @RequestMapping(value="/quxiaodianzan")
    @ResponseBody
    private int quxiaodianzan(String commentid,String usercode){
        return yhstopSmokingService.quxiaodianzan(commentid,usercode);
    }

    //点赞
    @RequestMapping(value="/selectbeginday")
    @ResponseBody
    private Result selectbeginday(String usercode){

        return   Result.ok(yhstopSmokingService.selectbeginday(usercode));
    }


    //重置吸烟
    @RequestMapping(value="/stopsmokings")
    @ResponseBody
    private Result stopsmokings(String usercode){

        return   Result.ok(yhstopSmokingService.stopsmokings(usercode));
    }

    //查询依赖
    @RequestMapping(value="/selectyilai")
    @ResponseBody
    private Result selectyilai(String usercode){

        return   Result.ok(yhstopSmokingService.selectyilai(usercode));
    }

    //查询评估人次
    @RequestMapping(value="/findSmokingCount")
    @ResponseBody
    private int findSmokingCount(){
        return yhstopSmokingService.findSmokingCount();
    }
    //查询评估报告
    @RequestMapping(value="/selectsmokingreport")
    @ResponseBody
    private List<YhSmokingReport> selectsmokingreport(String usercode){

        return   yhstopSmokingService.selectsmokingreport(usercode);
    }

    //查询烟瘾来袭日期
    @RequestMapping(value="/selectyanying")
    @ResponseBody
    private YhSmokeCraving selectyanying(String usercode){

        return   yhstopSmokingService.selectyanying(usercode);
    }

    //查询烟瘾来袭日期
    @RequestMapping(value="/addyanying")
    @ResponseBody
    private Integer addyanying(String usercode){

        return   yhstopSmokingService.addyanying(usercode);
    }

    //烟瘾来袭表统计
    @RequestMapping(value="/selectyanyingsave")
    @ResponseBody
    private Result selectyanyingsave(String usercode, String type){
        if (StringUtil.isEmpty(usercode) || StringUtil.isEmpty(type)) {
            return Result.error("参数异常");
        }
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        Map<String, Object> map = new HashMap<String, Object>();
        if (type.equals("最近七个戒烟日")) {

            //前七天的烟瘾次数
            Integer[] selectyanyingDuary = yhstopSmokingService.selectyanyingDuary(usercode);
            List<String> xingqi=pastDay(dateNowStr);
            Double pingjun=0.00;
            for (int k=0;k<selectyanyingDuary.length;k++){
                pingjun=pingjun+selectyanyingDuary[k];
            }
            for (int j=0;j<xingqi.size();j++){
                xingqi.set(j,xingqi.get(j).substring(5));
            }
            SmokeRecord recode=yhstopSmokingService.selectSmokeRecord7(usercode);

            map.put("infoList", selectyanyingDuary);
            map.put("timeList", xingqi);
            map.put("recode", recode);
            map.put("averageHeat", divide(pingjun,7,2));
        }else{
            Double pingjun=0.00;
            //前12个月的烟瘾次数
            Integer[] selectyanyingDuary = yhstopSmokingService.selectyanyingDuarybymothh(usercode);
            for (int k=0;k<selectyanyingDuary.length;k++){
                pingjun=pingjun+selectyanyingDuary[k];
            }
            String[] thismouth= getLast12Months();
            SmokeRecord recode=yhstopSmokingService.selectSmokeRecord12(usercode);
            map.put("infoList", selectyanyingDuary);
            map.put("timeList", thismouth);
            map.put("recode", recode);
            map.put("averageHeat", divide(pingjun,12,2));
        }
        return   Result.ok(map)    ;
    }


    //查询戒烟记录
    @RequestMapping(value="/selecytstopsmokinglog")
    @ResponseBody
    private List<YhUserSmoking> selecytstopsmokinglog(String usercode){

        return   yhstopSmokingService.selecytstopsmokinglog(usercode);
    }
    public String NowString() {
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");// 璁剧疆鏃ユ湡鏍煎紡
        return df.format(new Date());// new Date()涓鸿幏鍙栧綋鍓嶇郴缁熸椂闂�
    }

    /**
     * 验证文字是否违规
     * @param content
     * @return
     */
    public  Boolean checkContent(String content) {
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            CloseableHttpResponse response = null;

            HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + getAccessToken());
            request.addHeader("Content-Type", "application/json");
            Map<String, String> map = new HashMap<>();
            map.put("content",content);
            String body = com.alibaba.fastjson.JSONObject.toJSONString(map);
            request.setEntity((HttpEntity) new StringEntity(body, ContentType.create("text/json", "UTF-8")));
            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
            JSONObject jso = JSONObject.fromObject(result);
            return getResult(jso);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("----------------调用腾讯内容过滤系统出错------------------");
            return true;
        }
    }
    public String getAccessToken()
    {
        YhWxSetting selectByPrimaryKey = YhWxSettingService.selectByPrimaryKey(1);
        String accessToken="https://api.weixin.qq.com/cgi-bin/token";
        String turl = String.format(
                "grant_type=client_credential&appid=%s&secret=%s",
                selectByPrimaryKey.getAppid(),
                selectByPrimaryKey.getAppsecret());
        String result = HttpUtils.sendPost(accessToken, turl);
        JSONObject res = JSONObject.fromObject(result);
        return res.getString("access_token");
    }

    private static Boolean  getResult(JSONObject jso){
        Object errcode = jso.get("errcode");
        int errCode = (int) errcode;
        if (errCode == 0) {
            LOGGER.info("文字内容正常-----------");
            return true;
        } else if (errCode == 87014) {
            LOGGER.info("文字内容违规-----------");
            return false;
        }
        return true;
    }

    public static ArrayList<String> pastDay(String time){
        ArrayList<String> pastDaysList = new ArrayList<>();
        try {
            //我这里传来的时间是个string类型的，所以要先转为date类型的。
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(time);
            for (int i = 6; i >= 0; i--) {
                pastDaysList.add(getPastDate(i,date));
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return pastDaysList;
    }
    public static String getPastDate(int past,Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = sdf.format(today);
        return result;
    }

    public static double divide(double a, double b, int scale){
                 BigDecimal bd1 = new BigDecimal(Double.toString(a));
                 BigDecimal bd2 = new BigDecimal(Double.toString(b));
                 return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
             }

    public static String[] getLast12Months(){

        String[] last12Months = new String[12];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //要先+1,才能把本月的算进去
        // 加此行,否则3月重复
        cal.set(Calendar.DATE,1);

        for(int i=0; i<12; i++){
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1); //逐次往前推1个月
            if((cal.get(Calendar.MONTH)+1)<10){
                last12Months[11-i] = cal.get(Calendar.YEAR)+ "-0" +  (cal.get(Calendar.MONTH)+1);
            }else{
                last12Months[11-i] = cal.get(Calendar.YEAR)+ "-" +  (cal.get(Calendar.MONTH)+1);
            }

        }

        return last12Months;
    }


}
