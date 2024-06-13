package com.yihe.web.controller.system;

import com.yihe.common.utils.poi.ExcelUtilQ;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.ExportBasicUserMapper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ExportBasicUser")
public class ExportBasicUser {
    @Autowired
    ExportBasicUserMapper exportBasicUserMapper;
    @RequestMapping("/exportbychoose")
    public void downloadExcel(BasicUser basicUser, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        String orgCode = yh.getOrgCode();//取到当前用户orgcode
        if(basicUser.getOrgcode()==null || basicUser.getOrgcode()==""){
            basicUser.setOrgcode(orgCode);
        }
        basicUser.setBegantime(basicUser.getTimes().substring(0,11));
        basicUser.setEndtime(basicUser.getTimes().substring(13)+" 23:59:59");
        basicUser.setXaingmu(basicUser.getXiangmu().split(","));
        // 创建标题
        List<List<String>> titles = new ArrayList<List<String>>();
        // 创建sheet名
        List<String> sheetname = new ArrayList<String>();
        //健康风险
        List<AssessmentOfTotalTable> jiankangfengxiandata=new ArrayList<AssessmentOfTotalTable>();
        //changjianzhengzhuang
        List<AssessmentOfTotalTable> changjianzhengzhuang=new ArrayList<AssessmentOfTotalTable>();
        //axing
        List<AssessmentOfTotalTable> axingdata=new ArrayList<AssessmentOfTotalTable>();
        //pstriyali
        List<AssessmentOfTotalTable> pstridata=new ArrayList<AssessmentOfTotalTable>();
        //xinlijiankang
        List<AssessmentOfTotalTable> xinlijiankangdata=new ArrayList<AssessmentOfTotalTable>();
        //shuimian
        List<AssessmentOfTotalTable> shuimiandata=new ArrayList<AssessmentOfTotalTable>();
        //jiankangxiguan
        List<AssessmentOfTotalTable> jiankangxiguandata=new ArrayList<AssessmentOfTotalTable>();
        //汇总表
        List<AssessmentOfTotalTable> totoalassessment=new ArrayList<AssessmentOfTotalTable>();
        //体温
        List<AssessmentOfTotalTable> tiwen=new ArrayList<AssessmentOfTotalTable>();
        //体重
        List<AssessmentOfTotalTable> tizhong=new ArrayList<AssessmentOfTotalTable>();
        //血压
        List<AssessmentOfTotalTable> xueya=new ArrayList<AssessmentOfTotalTable>();
        //血糖
        List<AssessmentOfTotalTable> xuetang=new ArrayList<AssessmentOfTotalTable>();
        //血氧
        List<AssessmentOfTotalTable> xueyang=new ArrayList<AssessmentOfTotalTable>();
        //心率
        List<AssessmentOfTotalTable> xinlv=new ArrayList<AssessmentOfTotalTable>();
        //体脂
        List<AssessmentOfTotalTable> tizhi=new ArrayList<AssessmentOfTotalTable>();
        //血脂四项
        List<AssessmentOfTotalTable> xuezhisixiang=new ArrayList<AssessmentOfTotalTable>();
        //肾功能
        List<AssessmentOfTotalTable> shengongneng=new ArrayList<AssessmentOfTotalTable>();
        //肝功能
        List<AssessmentOfTotalTable> gangongneng=new ArrayList<AssessmentOfTotalTable>();
        //心电图
        List<AssessmentOfTotalTable> xindiantu=new ArrayList<AssessmentOfTotalTable>();
        //尿检
        List<AssessmentOfTotalTable> niaojian=new ArrayList<AssessmentOfTotalTable>();
        //其它指标
        List<AssessmentOfTotalTable> qitazhibiao=new ArrayList<AssessmentOfTotalTable>();
        //饮食日记
        List<AssessmentOfTotalTable> yinshiriji=new ArrayList<AssessmentOfTotalTable>();
        //运动日记
        List<AssessmentOfTotalTable> yundong=new ArrayList<AssessmentOfTotalTable>();
        //睡眠日记
        List<AssessmentOfTotalTable> shuimianriji=new ArrayList<AssessmentOfTotalTable>();
        //健康计划-高血压
        List<AssessmentOfTotalTable> gaoxueyariji=new ArrayList<AssessmentOfTotalTable>();
        //健康计划-高血糖
        List<AssessmentOfTotalTable> gaoxuetangriji=new ArrayList<AssessmentOfTotalTable>();
        //健康计划-高血脂
        List<AssessmentOfTotalTable> gaoxuezhiriji=new ArrayList<AssessmentOfTotalTable>();
        //健康计划-高尿酸
        List<AssessmentOfTotalTable> gaoniansuanriji=new ArrayList<AssessmentOfTotalTable>();
        //减脂
        List<AssessmentOfTotalTable> jianzhiriji=new ArrayList<AssessmentOfTotalTable>();
        //健康习惯
        List<AssessmentOfTotalTable> jiankangxiguanriji=new ArrayList<AssessmentOfTotalTable>();
        List<AssessmentOfTotalTable> jiankangxiguanriji2=new ArrayList<AssessmentOfTotalTable>();

        for(int i=0;i<basicUser.getXaingmu().length;i++){
            if(basicUser.getXaingmu()[i].equals(1+"")){
                List<String> jiankangfengxian=new ArrayList<String>();
                sheetname.add("健康风险评估");
                jiankangfengxian.add("用户编码");
                jiankangfengxian.add("姓名");
                jiankangfengxian.add("性别");
                jiankangfengxian.add("评估次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangfengxian.add("最新评估时间");
                }
                jiankangfengxiandata=exportBasicUserMapper.jiankangfengxiandatanouser(basicUser);
                titles.add(jiankangfengxian);
            }
            if(basicUser.getXaingmu()[i].equals(2+"")){
                List<String> cahgnjian=new ArrayList<String>();
                sheetname.add("常见症状评估与解决方案");
                cahgnjian.add("用户编码");
                cahgnjian.add("姓名");
                cahgnjian.add("性别");
                cahgnjian.add("评估次数");
                if(basicUser.getYonghu().equals(1+"")){
                    cahgnjian.add("最新评估时间");
                }
                changjianzhengzhuang=exportBasicUserMapper.chagjianzhengzhuang(basicUser);
                titles.add(cahgnjian);
            }
            if(basicUser.getXaingmu()[i].equals(3+"")){
                List<String> axing=new ArrayList<String>();
                sheetname.add("A型性格评估");
                axing.add("用户编码");
                axing.add("姓名");
                axing.add("性别");
                axing.add("评估次数");
                if(basicUser.getYonghu().equals(1+"")){
                    axing.add("最新评估时间");
                }
                titles.add(axing);
                axingdata=exportBasicUserMapper.axingdata(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(4+"")){
                List<String> PSTRI=new ArrayList<String>();
                sheetname.add("PSTRI压力评估");
                PSTRI.add("用户编码");
                PSTRI.add("姓名");
                PSTRI.add("性别");
                PSTRI.add("评估次数");
                if(basicUser.getYonghu().equals(1+"")){
                    PSTRI.add("最新评估时间");
                }
                titles.add(PSTRI);
                pstridata=exportBasicUserMapper.pstridata(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(5+"")){
                List<String> xinlijiankang=new ArrayList<String>();
                sheetname.add("心理健康评估");
                xinlijiankang.add("用户编码");
                xinlijiankang.add("姓名");
                xinlijiankang.add("性别");
                xinlijiankang.add("评估次数");
                if(basicUser.getYonghu().equals(1+"")){
                    xinlijiankang.add("最新评估时间");
                }
                titles.add(xinlijiankang);
                xinlijiankangdata=exportBasicUserMapper.xinlijiankangdata(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(6+"")){
                List<String> shuimian=new ArrayList<String>();
                sheetname.add("睡眠质量评估");
                shuimian.add("用户编码");
                shuimian.add("姓名");
                shuimian.add("性别");
                shuimian.add("评估次数");
                if(basicUser.getYonghu().equals(1+"")){
                    shuimian.add("最新评估时间");
                }
                titles.add(shuimian);
                shuimiandata=exportBasicUserMapper.shuimiandata(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(7+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康习惯评估");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("评估次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新评估时间");
                }
                titles.add(jiankangxiguan);
                jiankangxiguandata=exportBasicUserMapper.jiankangxiguandata(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(8+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-体温(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                tiwen=exportBasicUserMapper.tiwen(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(9+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-体重(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                tizhong=exportBasicUserMapper.tizhong(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(10+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-血压(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                xueya=exportBasicUserMapper.xueya(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(11+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-血糖(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                xuetang=exportBasicUserMapper.xuetang(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(12+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-血氧(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                xueyang=exportBasicUserMapper.xueyang(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(13+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-心率(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                xinlv=exportBasicUserMapper.xinlv(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(14+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-体脂(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                tizhi=exportBasicUserMapper.tizhi(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(15+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-血脂四项(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                xuezhisixiang=exportBasicUserMapper.xuezhisixiang(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(16+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-肾功能(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                shengongneng=exportBasicUserMapper.shengongneng(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(17+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-肝功能(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                gangongneng=exportBasicUserMapper.gangongneng(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(18+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-心电图(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                xindiantu=exportBasicUserMapper.xindaintu(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(19+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-尿检(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                niaojian=exportBasicUserMapper.niaojian(basicUser);
            }
            if(basicUser.getXaingmu()[i].equals(20+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康检测-其它指标(次)");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("检测次数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新检测时间");
                }
                titles.add(jiankangxiguan);
                qitazhibiao=exportBasicUserMapper.qitazhibiao(basicUser);
            }


            if(basicUser.getXaingmu()[i].equals(21+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("饮食日记");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");


                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("天数");
                    jiankangxiguan.add("早餐(天)");
                    jiankangxiguan.add("午餐(天)");
                    jiankangxiguan.add("晚餐(天)");
                    jiankangxiguan.add("加餐(天)");
                    jiankangxiguan.add("最新记录日期");
                }
                titles.add(jiankangxiguan);
                List<YhFoodUserDiary> yhFoodUserDiaries=exportBasicUserMapper.yinshiriji(basicUser);
                for (int q=0;q<yhFoodUserDiaries.size();q++){
                    boolean falg=false;
                    for (int w=0;w<yinshiriji.size();w++){
                        if(yhFoodUserDiaries.get(q).getUserCode().equals(yinshiriji.get(w).getUsercode())&&null!=yhFoodUserDiaries.get(q).getfElements()){
                            yinshiriji.get(w).setNewtime(yhFoodUserDiaries.get(q).getcTime());
                            falg=true;
                            String[] fenzu=yhFoodUserDiaries.get(q).getfElements().split(";");
                            yinshiriji.get(w).setZong(yinshiriji.get(w).getZong()+1);
                            Boolean A=true;
                            Boolean B=true;
                            Boolean C=true;
                            Boolean D=true;
                            for (int e=0;e<fenzu.length;e++){
                                System.out.println(fenzu);
                                if(fenzu[e].split("_").length<2){
                                    continue;
                                }
                                String fen=fenzu[e].split("_")[1];

                                if(fen.equals(1+"")&&A){
                                    yinshiriji.get(w).setZao(yinshiriji.get(w).getZao()+1);
                                    A=false;
                                }
                                if(fen.equals(2+"")&&B){
                                    yinshiriji.get(w).setZhong(yinshiriji.get(w).getZhong()+1);
                                    B=false;
                                }
                                if(fen.equals(3+"")&&C){
                                    yinshiriji.get(w).setWan(yinshiriji.get(w).getWan()+1);
                                    C=false;
                                }
                                if(fen.equals(4+"")&&D){
                                    yinshiriji.get(w).setJia(yinshiriji.get(w).getJia()+1);
                                    D=false;
                                }
                            }
                        }
                    }
                    if(!falg){
                        AssessmentOfTotalTable yhFoodUserDiary=new AssessmentOfTotalTable();
                        yhFoodUserDiary.setUsercode(yhFoodUserDiaries.get(q).getUserCode());
                        yhFoodUserDiary.setSex(yhFoodUserDiaries.get(q).getGender()+"");
                        yhFoodUserDiary.setName(yhFoodUserDiaries.get(q).getUserName());
                        yhFoodUserDiary.setNewtime(yhFoodUserDiaries.get(q).getcTime());
                        yhFoodUserDiary.setZong(1);
                        yhFoodUserDiary.setZhong(0);
                        yhFoodUserDiary.setWan(0);
                        yhFoodUserDiary.setJia(0);
                        yhFoodUserDiary.setZao(0);
                        Boolean A=true;
                        Boolean B=true;
                        Boolean C=true;
                        Boolean D=true;


                        if(yhFoodUserDiaries.get(q).getfElements()!=null){
                            String[] fenzu=yhFoodUserDiaries.get(q).getfElements().split(";");
                            if(!fenzu[0].equals("")){
                                for (int e=0;e<fenzu.length;e++){
                                    String fen=fenzu[e].split("_")[1];
                                    System.out.println(e);
                                    if(fen.equals(1+"")&&A){
                                        yhFoodUserDiary.setZao(yhFoodUserDiary.getZao()+1);
                                    }
                                    if(fen.equals(2+"")&&B){
                                        yhFoodUserDiary.setZhong(yhFoodUserDiary.getZhong()+1);
                                    }
                                    if(fen.equals(3+"")&&C){
                                        yhFoodUserDiary.setWan(yhFoodUserDiary.getWan()+1);
                                    }
                                    if(fen.equals(4+"")&&D){
                                        yhFoodUserDiary.setJia(yhFoodUserDiary.getJia()+1);
                                    }
                                }
                            }

                        }

                        yinshiriji.add(yhFoodUserDiary);
                    }
                }
            }

            if(basicUser.getXaingmu()[i].equals(22+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("运动日记");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");

                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("天数");
                    jiankangxiguan.add("步数(天)");
                    jiankangxiguan.add("推荐运动(天)");
                    jiankangxiguan.add("课程训练(天)");
                    jiankangxiguan.add("其它运动(天)");
                    jiankangxiguan.add("最新记录日期");
                }
                titles.add(jiankangxiguan);

                List<YhSportDiary> yhSportDiaries=exportBasicUserMapper.selectYhpory(basicUser);
                for (int k=0;k<yhSportDiaries.size();k++){
                    boolean flag=false;
                    for(int a=0;a<yundong.size();a++){
                        if(yhSportDiaries.get(k).getUserCode().equals(yundong.get(a).getUsercode())){
                            yundong.get(a).setNewtime(yhSportDiaries.get(k).getEntryTime());
                            flag=true;
                            yundong.get(a).setZong( yundong.get(a).getZong()+1);
                            if(yhSportDiaries.get(k).getNumber()!=null){
                                yundong.get(a).setZao(yundong.get(a).getZao()+1);
                            }
                            if(yhSportDiaries.get(k).getDiaryRecommend()!=null){
                                yundong.get(a).setZhong(yundong.get(a).getZhong()+1);
                            }
                            if(yhSportDiaries.get(k).getDiaryBoutique()!=null){
                                yundong.get(a).setWan(yundong.get(a).getWan()+1);
                            }
                            if(yhSportDiaries.get(k).getDiaryDetails()!=null){
                                yundong.get(a).setJia(yundong.get(a).getJia()+1);
                            }
                        }
                    }
                    if(!flag){
                        AssessmentOfTotalTable yhFoodUserDiary=new AssessmentOfTotalTable();
                        yhFoodUserDiary.setUsercode(yhSportDiaries.get(k).getUserCode());
                        yhFoodUserDiary.setName(yhSportDiaries.get(k).getUserName());
                        yhFoodUserDiary.setSex(yhSportDiaries.get(k).getGender());
                        yhFoodUserDiary.setNewtime(yhSportDiaries.get(k).getEntryTime());
                        yhFoodUserDiary.setZong(1);
                        if(yhSportDiaries.get(k).getNumber()!=null){
                            yhFoodUserDiary.setZao(1);
                        }else{
                            yhFoodUserDiary.setZao(0);
                        }
                        if(yhSportDiaries.get(k).getDiaryRecommend()!=null){
                            yhFoodUserDiary.setZhong(1);
                        }else{
                            yhFoodUserDiary.setZhong(0);
                        }
                        if(yhSportDiaries.get(k).getDiaryBoutique()!=null){
                            yhFoodUserDiary.setWan(1);
                        }else{
                            yhFoodUserDiary.setWan(0);
                        }
                        if(yhSportDiaries.get(k).getDiaryDetails()!=null){
                            yhFoodUserDiary.setJia(1);
                        }else{
                            yhFoodUserDiary.setJia(0);
                        }

                        yundong.add(yhFoodUserDiary);
                    }
                }
            }

            if(basicUser.getXaingmu()[i].equals(23+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("睡眠日记");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("天数");
                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("最新记录日期");
                }
                titles.add(jiankangxiguan);
                shuimianriji=exportBasicUserMapper.shuimian(basicUser);
            }

            if(basicUser.getXaingmu()[i].equals(24+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康计划-高血压");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("健康计划-第1天");

                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("执行天数");
                    jiankangxiguan.add("最新执行时间");
                    jiankangxiguan.add("当前状态");
                }
                titles.add(jiankangxiguan);
                gaoxueyariji=exportBasicUserMapper.gaoxueyariji(basicUser);
            }

            if(basicUser.getXaingmu()[i].equals(25+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康计划-高血糖");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("健康计划-第1天");

                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("执行天数");
                    jiankangxiguan.add("最新执行时间");
                    jiankangxiguan.add("当前状态");
                }
                titles.add(jiankangxiguan);
                gaoxuetangriji=exportBasicUserMapper.gaoxuetangriji(basicUser);
            }

            if(basicUser.getXaingmu()[i].equals(26+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康计划-高血脂");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("健康计划-第1天");

                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("执行天数");
                    jiankangxiguan.add("最新执行时间");
                    jiankangxiguan.add("当前状态");
                }
                titles.add(jiankangxiguan);
                gaoxuezhiriji=exportBasicUserMapper.gaoxuezhiriji(basicUser);
            }

            if(basicUser.getXaingmu()[i].equals(27+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康计划-高尿酸");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("健康计划-第1天");

                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("执行天数");
                    jiankangxiguan.add("最新执行时间");
                    jiankangxiguan.add("当前状态");
                }
                titles.add(jiankangxiguan);
                gaoniansuanriji=exportBasicUserMapper.gaoniansuanriji(basicUser);
            }

            if(basicUser.getXaingmu()[i].equals(28+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康计划-减脂");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");
                jiankangxiguan.add("健康计划-第1天");

                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("执行天数");
                    jiankangxiguan.add("最新执行时间");
                    jiankangxiguan.add("当前状态");
                }
                titles.add(jiankangxiguan);
                jianzhiriji=exportBasicUserMapper.jianzhiriji(basicUser);
            }

            if(basicUser.getXaingmu()[i].equals(29+"")){
                List<String> jiankangxiguan=new ArrayList<String>();
                sheetname.add("健康习惯日记");
                jiankangxiguan.add("用户编码");
                jiankangxiguan.add("姓名");
                jiankangxiguan.add("性别");


                if(basicUser.getYonghu().equals(1+"")){
                    jiankangxiguan.add("天数");
                    jiankangxiguan.add("最新记录日期");
                }
                titles.add(jiankangxiguan);
                jiankangxiguanriji=exportBasicUserMapper.jiankangxiguanriji(basicUser);
                for (int a=0;a<jiankangxiguanriji.size();a++){
                    boolean fals=true;
                    for(int b=0;b<jiankangxiguanriji2.size();b++){
                        if(jiankangxiguanriji.get(a).getUsercode().equals(jiankangxiguanriji2.get(b).getUsercode())){
                            fals=false;
                            jiankangxiguanriji2.get(b).setZong(jiankangxiguanriji2.get(b).getZong()+1);
                            jiankangxiguanriji2.get(b).setNewtime(jiankangxiguanriji.get(a).getNewtime());
                        }
                    }
                    if(fals){
                        AssessmentOfTotalTable assessmentOfTotalTable=new AssessmentOfTotalTable();
                        assessmentOfTotalTable.setName(jiankangxiguanriji.get(a).getName());
                        assessmentOfTotalTable.setUsercode(jiankangxiguanriji.get(a).getUsercode());
                        assessmentOfTotalTable.setSex(jiankangxiguanriji.get(a).getSex());
                        assessmentOfTotalTable.setZong(1);
                        assessmentOfTotalTable.setNewtime(jiankangxiguanriji.get(a).getNewtime());
                        jiankangxiguanriji2.add(assessmentOfTotalTable);
                    }
                }
            }
        }
            if(basicUser.getTable()==1){
                if(basicUser.getXaingmu().length>2){
                    List<String> zongbiao=new ArrayList<String>();
                    sheetname.add("合计(评估)");
                    zongbiao.add("用户编码");
                    zongbiao.add("姓名");
                    zongbiao.add("性别");
                    for (int i=0;i<basicUser.getXaingmu().length;i++){
                        if(basicUser.getXaingmu()[i].equals(1+"")){
                            zongbiao.add("健康风险评估(次)");
                            for (int j=0;j<jiankangfengxiandata.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(jiankangfengxiandata.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setHealthAssessment(jiankangfengxiandata.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(jiankangfengxiandata.get(j).getUsercode());
                                    totalTable.setName(jiankangfengxiandata.get(j).getName());
                                    totalTable.setSex(jiankangfengxiandata.get(j).getSex());
                                    totalTable.setHealthAssessment(jiankangfengxiandata.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(2+"")){
                            zongbiao.add("常见症状评估与解决方案(次)");
                            for (int j=0;j<changjianzhengzhuang.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(changjianzhengzhuang.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setCommonAssessment(changjianzhengzhuang.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(changjianzhengzhuang.get(j).getUsercode());
                                    totalTable.setName(changjianzhengzhuang.get(j).getName());
                                    totalTable.setSex(changjianzhengzhuang.get(j).getSex());
                                    totalTable.setCommonAssessment(changjianzhengzhuang.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(3+"")){
                            zongbiao.add("A型性格评估(次)");
                            for (int j=0;j<axingdata.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(axingdata.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setAxingAssessment(axingdata.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(axingdata.get(j).getUsercode());
                                    totalTable.setName(axingdata.get(j).getName());
                                    totalTable.setSex(axingdata.get(j).getSex());
                                    totalTable.setAxingAssessment(axingdata.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(4+"")){
                            zongbiao.add("PSTRI压力评估(次)");
                            for (int j=0;j<pstridata.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(pstridata.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setPstriAssessment(pstridata.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(pstridata.get(j).getUsercode());
                                    totalTable.setName(pstridata.get(j).getName());
                                    totalTable.setSex(pstridata.get(j).getSex());
                                    totalTable.setPstriAssessment(pstridata.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(5+"")){
                            zongbiao.add("心理健康评估(次)");
                            for (int j=0;j<xinlijiankangdata.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(xinlijiankangdata.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setHeartAssessment(xinlijiankangdata.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(xinlijiankangdata.get(j).getUsercode());
                                    totalTable.setName(xinlijiankangdata.get(j).getName());
                                    totalTable.setSex(xinlijiankangdata.get(j).getSex());
                                    totalTable.setHeartAssessment(xinlijiankangdata.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(6+"")){
                            zongbiao.add("睡眠质量评估(次)");
                            for (int j=0;j<shuimiandata.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(shuimiandata.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setSleepassessment(shuimiandata.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(shuimiandata.get(j).getUsercode());
                                    totalTable.setSleepassessment(shuimiandata.get(j).getHealthAssessment());
                                    totalTable.setName(shuimiandata.get(j).getName());
                                    totalTable.setSex(shuimiandata.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(7+"")){
                            zongbiao.add("健康习惯评估(次)");
                            for (int j=0;j<jiankangxiguandata.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(jiankangxiguandata.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setAssessmenttimes(jiankangxiguandata.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(jiankangxiguandata.get(j).getUsercode());
                                    totalTable.setAssessmenttimes(jiankangxiguandata.get(j).getHealthAssessment());
                                    totalTable.setName(jiankangxiguandata.get(j).getName());
                                    totalTable.setSex(jiankangxiguandata.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }

                    }
                    titles.add(zongbiao);
                }


            }else if(basicUser.getTable()==2){
                if(basicUser.getXaingmu().length>2){
                    List<String> zongbiao=new ArrayList<String>();
                    sheetname.add("合计(检测)");
                    zongbiao.add("用户编码");
                    zongbiao.add("姓名");
                    zongbiao.add("性别");
                    for (int i=0;i<basicUser.getXaingmu().length;i++){
                        if(basicUser.getXaingmu()[i].equals(8+"")){
                            zongbiao.add("健康检测-体温(次)");
                            for (int j=0;j<tiwen.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(tiwen.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setTiwen(tiwen.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(tiwen.get(j).getUsercode());
                                    totalTable.setName(tiwen.get(j).getName());
                                    totalTable.setSex(tiwen.get(j).getSex());
                                    totalTable.setTiwen(tiwen.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(9+"")){
                            zongbiao.add("健康检测-体重(次)");
                            for (int j=0;j<tizhong.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(tizhong.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setTizhong(tizhong.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(tizhong.get(j).getUsercode());
                                    totalTable.setName(tizhong.get(j).getName());
                                    totalTable.setSex(tizhong.get(j).getSex());
                                    totalTable.setTizhong(tizhong.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(10+"")){
                            zongbiao.add("健康检测-血压(次)");
                            for (int j=0;j<xueya.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(xueya.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setXueya(xueya.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(xueya.get(j).getUsercode());
                                    totalTable.setName(xueya.get(j).getName());
                                    totalTable.setSex(xueya.get(j).getSex());
                                    totalTable.setXueya(xueya.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(11+"")){
                            zongbiao.add("健康检测-血糖(次)");
                            for (int j=0;j<xuetang.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(xuetang.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setXuetang(xuetang.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(xuetang.get(j).getUsercode());
                                    totalTable.setName(xuetang.get(j).getName());
                                    totalTable.setSex(xuetang.get(j).getSex());
                                    totalTable.setXuetang(xuetang.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(12+"")){
                            zongbiao.add("健康检测-血氧(次)");
                            for (int j=0;j<xueyang.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(xueyang.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setXueyang(xueyang.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(xueyang.get(j).getUsercode());
                                    totalTable.setName(xueyang.get(j).getName());
                                    totalTable.setSex(xueyang.get(j).getSex());
                                    totalTable.setXueyang(xueyang.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(13+"")){
                            zongbiao.add("健康检测-心率(次)");
                            for (int j=0;j<xinlv.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(xinlv.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setXinlv(xinlv.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(xinlv.get(j).getUsercode());
                                    totalTable.setXinlv(xinlv.get(j).getHealthAssessment());
                                    totalTable.setName(xinlv.get(j).getName());
                                    totalTable.setSex(xinlv.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(14+"")){
                            zongbiao.add("健康检测-体脂(次)");
                            for (int j=0;j<tizhi.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(tizhi.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setTizhi(tizhi.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(tizhi.get(j).getUsercode());
                                    totalTable.setTizhi(tizhi.get(j).getHealthAssessment());
                                    totalTable.setName(tizhi.get(j).getName());
                                    totalTable.setSex(tizhi.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(15+"")){
                            zongbiao.add("健康检测-血脂四项(次)");
                            for (int j=0;j<xuezhisixiang.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(xuezhisixiang.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setXuezhisixiang(xuezhisixiang.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(xuezhisixiang.get(j).getUsercode());
                                    totalTable.setXuezhisixiang(xuezhisixiang.get(j).getHealthAssessment());
                                    totalTable.setName(xuezhisixiang.get(j).getName());
                                    totalTable.setSex(xuezhisixiang.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(16+"")){
                            zongbiao.add("健康检测-肾功能(次)");
                            for (int j=0;j<shengongneng.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(shengongneng.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setShengongneng(shengongneng.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(shengongneng.get(j).getUsercode());
                                    totalTable.setShengongneng(shengongneng.get(j).getHealthAssessment());
                                    totalTable.setName(shengongneng.get(j).getName());
                                    totalTable.setSex(shengongneng.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(17+"")){
                            zongbiao.add("健康检测-肝功能(次)");
                            for (int j=0;j<gangongneng.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(gangongneng.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setGangongneng(gangongneng.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(gangongneng.get(j).getUsercode());
                                    totalTable.setGangongneng(gangongneng.get(j).getHealthAssessment());
                                    totalTable.setName(gangongneng.get(j).getName());
                                    totalTable.setSex(gangongneng.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(18+"")){
                            zongbiao.add("健康检测-心电图(次)");
                            for (int j=0;j<xindiantu.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(xindiantu.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setXindaintu(xindiantu.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(xindiantu.get(j).getUsercode());
                                    totalTable.setXindaintu(xindiantu.get(j).getHealthAssessment());
                                    totalTable.setName(xindiantu.get(j).getName());
                                    totalTable.setSex(xindiantu.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(19+"")){
                            zongbiao.add("健康检测-尿检(次)");
                            for (int j=0;j<niaojian.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(niaojian.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setNiaojian(niaojian.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(niaojian.get(j).getUsercode());
                                    totalTable.setNiaojian(niaojian.get(j).getHealthAssessment());
                                    totalTable.setName(niaojian.get(j).getName());
                                    totalTable.setSex(niaojian.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(20+"")){
                            zongbiao.add("健康检测-其它指标(次)");
                            for (int j=0;j<qitazhibiao.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(qitazhibiao.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setQitazhibiao(qitazhibiao.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(qitazhibiao.get(j).getUsercode());
                                    totalTable.setQitazhibiao(qitazhibiao.get(j).getHealthAssessment());
                                    totalTable.setName(qitazhibiao.get(j).getName());
                                    totalTable.setSex(qitazhibiao.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }

                    }
                    titles.add(zongbiao);
                }
            }else if(basicUser.getTable()==3){
                if(basicUser.getXaingmu().length>2){
                    List<String> zongbiao=new ArrayList<String>();
                    sheetname.add("合计（日记)");
                    zongbiao.add("用户编码");
                    zongbiao.add("姓名");
                    zongbiao.add("性别");
                    for (int i=0;i<basicUser.getXaingmu().length;i++){
                        if(basicUser.getXaingmu()[i].equals(21+"")){
                            zongbiao.add("饮食日记(天)");
                            for (int j=0;j<yinshiriji.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(yinshiriji.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setHealthAssessment(yinshiriji.get(j).getZong());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(yinshiriji.get(j).getUsercode());
                                    totalTable.setName(yinshiriji.get(j).getName());
                                    totalTable.setSex(yinshiriji.get(j).getSex());
                                    totalTable.setHealthAssessment(yinshiriji.get(j).getZong());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(22+"")){
                            zongbiao.add("运动日记(天)");
                            for (int j=0;j<yundong.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(yundong.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setCommonAssessment(yundong.get(j).getZong());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(yundong.get(j).getUsercode());
                                    totalTable.setName(yundong.get(j).getName());
                                    totalTable.setSex(yundong.get(j).getSex());
                                    totalTable.setCommonAssessment(yundong.get(j).getZong());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(23+"")){
                            zongbiao.add("睡眠日记(天)");
                            for (int j=0;j<shuimianriji.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(shuimianriji.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setAxingAssessment(shuimianriji.get(j).getHealthAssessment());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(shuimianriji.get(j).getUsercode());
                                    totalTable.setName(shuimianriji.get(j).getName());
                                    totalTable.setSex(shuimianriji.get(j).getSex());
                                    totalTable.setAxingAssessment(shuimianriji.get(j).getHealthAssessment());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(24+"")){
                            zongbiao.add("健康计划-高血压(天)");
                            for (int j=0;j<gaoxueyariji.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(gaoxueyariji.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setPstriAssessment(gaoxueyariji.get(j).getZong());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(gaoxueyariji.get(j).getUsercode());
                                    totalTable.setName(gaoxueyariji.get(j).getName());
                                    totalTable.setSex(gaoxueyariji.get(j).getSex());
                                    totalTable.setPstriAssessment(gaoxueyariji.get(j).getZong());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(25+"")){
                            zongbiao.add("健康计划-高血糖(天)");
                            for (int j=0;j<gaoxuetangriji.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(gaoxuetangriji.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setHeartAssessment(gaoxuetangriji.get(j).getZong());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(gaoxuetangriji.get(j).getUsercode());
                                    totalTable.setName(gaoxuetangriji.get(j).getName());
                                    totalTable.setSex(gaoxuetangriji.get(j).getSex());
                                    totalTable.setHeartAssessment(gaoxuetangriji.get(j).getZong());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(26+"")){
                            zongbiao.add("健康计划-高血脂(天)");
                            for (int j=0;j<gaoxuezhiriji.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(gaoxuezhiriji.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setSleepassessment(gaoxuezhiriji.get(j).getZong());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(gaoxuezhiriji.get(j).getUsercode());
                                    totalTable.setSleepassessment(gaoxuezhiriji.get(j).getZong());
                                    totalTable.setName(gaoxuezhiriji.get(j).getName());
                                    totalTable.setSex(gaoxuezhiriji.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(27+"")){
                            zongbiao.add("健康计划-高尿酸(天)");
                            for (int j=0;j<gaoniansuanriji.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(gaoniansuanriji.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setAssessmenttimes(gaoniansuanriji.get(j).getZong());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(gaoniansuanriji.get(j).getUsercode());
                                    totalTable.setAssessmenttimes(gaoniansuanriji.get(j).getZong());
                                    totalTable.setName(gaoniansuanriji.get(j).getName());
                                    totalTable.setSex(gaoniansuanriji.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(28+"")){
                            zongbiao.add("健康计划-减脂(天)");
                            for (int j=0;j<jianzhiriji.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(jianzhiriji.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setZao(jianzhiriji.get(j).getZong());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(jianzhiriji.get(j).getUsercode());
                                    totalTable.setZao(jianzhiriji.get(j).getZong());
                                    totalTable.setName(jianzhiriji.get(j).getName());
                                    totalTable.setSex(jianzhiriji.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }else if(basicUser.getXaingmu()[i].equals(29+"")){
                            zongbiao.add("健康习惯日记(天)");
                            for (int j=0;j<jiankangxiguanriji2.size();j++){
                                boolean flag=false;
                                for(int k=0;k<totoalassessment.size();k++){
                                    if(totoalassessment.get(k).getUsercode().equals(jiankangxiguanriji2.get(j).getUsercode())){
                                        flag=true;
                                        totoalassessment.get(k).setZhong(jiankangxiguanriji2.get(j).getZong());
                                    }
                                }
                                if(!flag){
                                    AssessmentOfTotalTable totalTable=new AssessmentOfTotalTable();
                                    totalTable.setUsercode(jiankangxiguanriji2.get(j).getUsercode());
                                    totalTable.setZhong(jiankangxiguanriji2.get(j).getZong());
                                    totalTable.setName(jiankangxiguanriji2.get(j).getName());
                                    totalTable.setSex(jiankangxiguanriji2.get(j).getSex());
                                    totoalassessment.add(totalTable);
                                }
                            }
                        }

                    }
                    titles.add(zongbiao);
                }
            }

        for (int i = 0; i < sheetname.size(); i++) {
            // 创建sheet
            HSSFSheet sheet = workbook.createSheet(sheetname.get(i));
            // 循环标题（多页结构）
            if(sheetname.get(i).equals("检测数据汇总表")){
                ExcelUtilQ.createTitles(workbook, sheet, titles.get(i),i);
            }else{
                ExcelUtilQ.createTitle(workbook, sheet, titles.get(i),i);
            }
            if (sheetname.get(i).equals("合计(评估)")) {
                // 循环表数据
                for (int j = 0; j <totoalassessment.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(totoalassessment.get(j).getUsercode());
                    row.createCell(1).setCellValue(totoalassessment.get(j).getName());
                    row.createCell(2).setCellValue(totoalassessment.get(j).getSex() .equals("1") ? "男" : "女");
                    int p=2;
                    for(int k=0;k<basicUser.getXaingmu().length;k++){
                        if(basicUser.getXaingmu()[k].equals(1+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getHealthAssessment()==null?"":totoalassessment.get(j).getHealthAssessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(2+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getCommonAssessment()==null?"":totoalassessment.get(j).getCommonAssessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(3+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getAxingAssessment()==null?"":totoalassessment.get(j).getAxingAssessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(4+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getPstriAssessment()==null?"":totoalassessment.get(j).getPstriAssessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(5+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getHeartAssessment()==null?"":totoalassessment.get(j).getHeartAssessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(6+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getSleepassessment()==null?"":totoalassessment.get(j).getSleepassessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(7+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getAssessmenttimes()==null?"":totoalassessment.get(j).getAssessmenttimes().toString());
                            p++;
                        }
                    }

                }
            }
            if (sheetname.get(i).equals("健康风险评估")) {
                // 循环表数据
                for (int j = 0; j <jiankangfengxiandata.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(jiankangfengxiandata.get(j).getUsercode());
                    row.createCell(1).setCellValue(jiankangfengxiandata.get(j).getName());
                    row.createCell(2).setCellValue(jiankangfengxiandata.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(jiankangfengxiandata.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(jiankangfengxiandata.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("常见症状评估与解决方案")) {
                // 循环表数据
                for (int j = 0; j <changjianzhengzhuang.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(changjianzhengzhuang.get(j).getUsercode());
                    row.createCell(1).setCellValue(changjianzhengzhuang.get(j).getName());
                    row.createCell(2).setCellValue(changjianzhengzhuang.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(changjianzhengzhuang.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(changjianzhengzhuang.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("A型性格评估")) {
                // 循环表数据
                for (int j = 0; j <axingdata.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(axingdata.get(j).getUsercode());
                    row.createCell(1).setCellValue(axingdata.get(j).getName());
                    row.createCell(2).setCellValue(axingdata.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(axingdata.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(axingdata.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("PSTRI压力评估")) {
                // 循环表数据
                for (int j = 0; j <pstridata.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(pstridata.get(j).getUsercode());
                    row.createCell(1).setCellValue(pstridata.get(j).getName());
                    row.createCell(2).setCellValue(pstridata.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(pstridata.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(pstridata.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("心理健康评估")) {
                // 循环表数据
                for (int j = 0; j <xinlijiankangdata.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(xinlijiankangdata.get(j).getUsercode());
                    row.createCell(1).setCellValue(xinlijiankangdata.get(j).getName());
                    row.createCell(2).setCellValue(xinlijiankangdata.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(xinlijiankangdata.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(xinlijiankangdata.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("睡眠质量评估")) {
                // 循环表数据
                for (int j = 0; j <shuimiandata.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(shuimiandata.get(j).getUsercode());
                    row.createCell(1).setCellValue(shuimiandata.get(j).getName());
                    row.createCell(2).setCellValue(shuimiandata.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(shuimiandata.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(shuimiandata.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康习惯评估")) {
                // 循环表数据
                for (int j = 0; j <jiankangxiguandata.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(jiankangxiguandata.get(j).getUsercode());
                    row.createCell(1).setCellValue(jiankangxiguandata.get(j).getName());
                    row.createCell(2).setCellValue(jiankangxiguandata.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(jiankangxiguandata.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(jiankangxiguandata.get(j).getNewtime());
                    }
                }
            }
            if (sheetname.get(i).equals("合计(检测)")) {
                // 循环表数据
                for (int j = 0; j <totoalassessment.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(totoalassessment.get(j).getUsercode());
                    row.createCell(1).setCellValue(totoalassessment.get(j).getName());
                    row.createCell(2).setCellValue(totoalassessment.get(j).getSex() .equals("1") ? "男" : "女");
                    int p=2;
                    for(int k=0;k<basicUser.getXaingmu().length;k++){
                        if(basicUser.getXaingmu()[k].equals(8+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getTiwen()==null?"":totoalassessment.get(j).getTiwen().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(9+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getTizhong()==null?"":totoalassessment.get(j).getTizhong().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(10+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getXueya()==null?"":totoalassessment.get(j).getXueya().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(11+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getXuetang()==null?"":totoalassessment.get(j).getXuetang().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(12+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getXueyang()==null?"":totoalassessment.get(j).getXueyang().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(13+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getXinlv()==null?"":totoalassessment.get(j).getXinlv().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(14+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getTizhi()==null?"":totoalassessment.get(j).getTizhi().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(15+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getXuezhisixiang()==null?"":totoalassessment.get(j).getXuezhisixiang().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(16+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getShengongneng()==null?"":totoalassessment.get(j).getShengongneng().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(17+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getGangongneng()==null?"":totoalassessment.get(j).getGangongneng().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(18+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getXindaintu()==null?"":totoalassessment.get(j).getXindaintu().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(19+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getNiaojian()==null?"":totoalassessment.get(j).getNiaojian().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(20+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getQitazhibiao()==null?"":totoalassessment.get(j).getQitazhibiao().toString());
                            p++;
                        }
                    }

                }
            }

            if (sheetname.get(i).equals("健康检测-体温(次)")) {
                // 循环表数据
                for (int j = 0; j <tiwen.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(tiwen.get(j).getUsercode());
                    row.createCell(1).setCellValue(tiwen.get(j).getName());
                    row.createCell(2).setCellValue(tiwen.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(tiwen.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(tiwen.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-体重(次)")) {
                // 循环表数据
                for (int j = 0; j <tizhong.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(tizhong.get(j).getUsercode());
                    row.createCell(1).setCellValue(tizhong.get(j).getName());
                    row.createCell(2).setCellValue(tizhong.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(tizhong.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(tizhong.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-血压(次)")) {
                // 循环表数据
                for (int j = 0; j <xueya.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(xueya.get(j).getUsercode());
                    row.createCell(1).setCellValue(xueya.get(j).getName());
                    row.createCell(2).setCellValue(xueya.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(xueya.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(xueya.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-血糖(次)")) {
                // 循环表数据
                for (int j = 0; j <xuetang.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(xuetang.get(j).getUsercode());
                    row.createCell(1).setCellValue(xuetang.get(j).getName());
                    row.createCell(2).setCellValue(xuetang.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(xuetang.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(xuetang.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-血氧(次)")) {
                // 循环表数据
                for (int j = 0; j <xueyang.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(xueyang.get(j).getUsercode());
                    row.createCell(1).setCellValue(xueyang.get(j).getName());
                    row.createCell(2).setCellValue(xueyang.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(xueyang.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(xueyang.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-心率(次)")) {
                // 循环表数据
                for (int j = 0; j <xinlv.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(xinlv.get(j).getUsercode());
                    row.createCell(1).setCellValue(xinlv.get(j).getName());
                    row.createCell(2).setCellValue(xinlv.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(xinlv.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(xinlv.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-体脂(次)")) {
                // 循环表数据
                for (int j = 0; j <tizhi.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(tizhi.get(j).getUsercode());
                    row.createCell(1).setCellValue(tizhi.get(j).getName());
                    row.createCell(2).setCellValue(tizhi.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(tizhi.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(tizhi.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-血脂四项(次)")) {
                // 循环表数据
                for (int j = 0; j <xuezhisixiang.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(xuezhisixiang.get(j).getUsercode());
                    row.createCell(1).setCellValue(xuezhisixiang.get(j).getName());
                    row.createCell(2).setCellValue(xuezhisixiang.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(xuezhisixiang.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(xuezhisixiang.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-肾功能(次)")) {
                // 循环表数据
                for (int j = 0; j <shengongneng.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(shengongneng.get(j).getUsercode());
                    row.createCell(1).setCellValue(shengongneng.get(j).getName());
                    row.createCell(2).setCellValue(shengongneng.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(shengongneng.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(shengongneng.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-肝功能(次)")) {
                // 循环表数据
                for (int j = 0; j <gangongneng.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(gangongneng.get(j).getUsercode());
                    row.createCell(1).setCellValue(gangongneng.get(j).getName());
                    row.createCell(2).setCellValue(gangongneng.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(gangongneng.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(gangongneng.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-心电图(次)")) {
                // 循环表数据
                for (int j = 0; j <xindiantu.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(xindiantu.get(j).getUsercode());
                    row.createCell(1).setCellValue(xindiantu.get(j).getName());
                    row.createCell(2).setCellValue(xindiantu.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(xindiantu.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(xindiantu.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-尿检(次)")) {
                // 循环表数据
                for (int j = 0; j <niaojian.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(niaojian.get(j).getUsercode());
                    row.createCell(1).setCellValue(niaojian.get(j).getName());
                    row.createCell(2).setCellValue(niaojian.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(niaojian.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(niaojian.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("健康检测-其它指标(次)")) {
                // 循环表数据
                for (int j = 0; j <qitazhibiao.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(qitazhibiao.get(j).getUsercode());
                    row.createCell(1).setCellValue(qitazhibiao.get(j).getName());
                    row.createCell(2).setCellValue(qitazhibiao.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(qitazhibiao.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(qitazhibiao.get(j).getNewtime());
                    }
                }
            }

            if (sheetname.get(i).equals("合计（日记)")) {
                // 循环表数据
                for (int j = 0; j <totoalassessment.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(totoalassessment.get(j).getUsercode());
                    row.createCell(1).setCellValue(totoalassessment.get(j).getName());
                    row.createCell(2).setCellValue(totoalassessment.get(j).getSex() .equals("1") ? "男" : "女");
                    int p=2;
                    if(basicUser.getYonghu().equals(1+"")){
                    for(int k=0;k<basicUser.getXaingmu().length;k++){
                        if(basicUser.getXaingmu()[k].equals(21+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getHealthAssessment()==null?"":totoalassessment.get(j).getHealthAssessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(22+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getCommonAssessment()==null?"":totoalassessment.get(j).getCommonAssessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(23+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getAxingAssessment()==null?"":totoalassessment.get(j).getAxingAssessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(24+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getPstriAssessment()==null?"":totoalassessment.get(j).getPstriAssessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(25+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getHeartAssessment()==null?"":totoalassessment.get(j).getHeartAssessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(26+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getSleepassessment()==null?"":totoalassessment.get(j).getSleepassessment().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(27+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getAssessmenttimes()==null?"":totoalassessment.get(j).getAssessmenttimes().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(28+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getZao()==null?"":totoalassessment.get(j).getZao().toString());
                            p++;
                        }
                        if(basicUser.getXaingmu()[k].equals(29+"")){
                            row.createCell(p+1).setCellValue(totoalassessment.get(j).getZhong()==null?"":totoalassessment.get(j).getZhong().toString());
                            p++;
                        }

                    }

                    }

                }
                }


            if (sheetname.get(i).equals("饮食日记")) {
                // 循环表数据
                for (int j = 0; j <yinshiriji.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(yinshiriji.get(j).getUsercode());
                    row.createCell(1).setCellValue(yinshiriji.get(j).getName());
                    row.createCell(2).setCellValue(yinshiriji.get(j).getSex() .equals("1") ? "男" : "女");

                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(3).setCellValue(yinshiriji.get(j).getZong());
                        row.createCell(4).setCellValue(yinshiriji.get(j).getZao());
                        row.createCell(5).setCellValue(yinshiriji.get(j).getZhong());
                        row.createCell(6).setCellValue(yinshiriji.get(j).getWan());
                        row.createCell(7).setCellValue(yinshiriji.get(j).getJia());
                        row.createCell(8).setCellValue(yinshiriji.get(j).getNewtime());
                    }
                }
            }
            if (sheetname.get(i).equals("运动日记")) {
                // 循环表数据
                for (int j = 0; j <yundong.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(yundong.get(j).getUsercode());
                    row.createCell(1).setCellValue(yundong.get(j).getName());
                    row.createCell(2).setCellValue(yundong.get(j).getSex() .equals("1") ? "男" : "女");

                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(3).setCellValue(yundong.get(j).getZong());
                        row.createCell(4).setCellValue(yundong.get(j).getZao());
                        row.createCell(5).setCellValue(yundong.get(j).getZhong());
                        row.createCell(6).setCellValue(yundong.get(j).getWan());
                        row.createCell(7).setCellValue(yundong.get(j).getJia());
                        row.createCell(8).setCellValue(yundong.get(j).getNewtime());
                    }
                }
            }

//            if (sheetname.get(i).equals("运动日记")) {
//                // 循环表数据
//                for (int j = 0; j <yundong.size(); j++) {
//                    HSSFRow row = sheet.createRow(j + 1);
//                    row.setHeightInPoints(18);
//                    row.createCell(0).setCellValue(yundong.get(j).getUsercode());
//                    row.createCell(1).setCellValue(yundong.get(j).getName());
//                    row.createCell(2).setCellValue(yundong.get(j).getSex() .equals("1") ? "男" : "女");
//                    row.createCell(3).setCellValue(yundong.get(j).getZong());
//                    if(basicUser.getYonghu().equals(1+"")){
//                        row.createCell(4).setCellValue(yundong.get(j).getZao());
//                        row.createCell(5).setCellValue(yundong.get(j).getZhong());
//                        row.createCell(6).setCellValue(yundong.get(j).getWan());
//                        row.createCell(7).setCellValue(yundong.get(j).getJia());
//                        row.createCell(8).setCellValue(yundong.get(j).getNewtime());
//                    }
//                }
//            }

            if (sheetname.get(i).equals("睡眠日记")) {
                // 循环表数据
                for (int j = 0; j <shuimianriji.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(shuimianriji.get(j).getUsercode());
                    row.createCell(1).setCellValue(shuimianriji.get(j).getName());
                    row.createCell(2).setCellValue(shuimianriji.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(shuimianriji.get(j).getHealthAssessment());
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(shuimianriji.get(j).getNewtime());

                    }
                }
            }

            if (sheetname.get(i).equals("健康计划-高血压")) {
                // 循环表数据
                for (int j = 0; j <gaoxueyariji.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(gaoxueyariji.get(j).getUsercode());
                    row.createCell(1).setCellValue(gaoxueyariji.get(j).getName());
                    row.createCell(2).setCellValue(gaoxueyariji.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(exportBasicUserMapper.chazhaoshijian(gaoxueyariji.get(j).getHealthAssessment()));
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(gaoxueyariji.get(j).getZong());
                        row.createCell(5).setCellValue(gaoxueyariji.get(j).getNewtime());
                        row.createCell(6).setCellValue(gaoxueyariji.get(j).getZao()==0?"未开始":gaoxueyariji.get(j).getZao()==1?"正在执行":gaoxueyariji.get(j).getZao()==2?"已结束":gaoxueyariji.get(j).getZao()==3?"已取消":"已结束");
                    }
                }
            }

            if (sheetname.get(i).equals("健康计划-高血糖")) {
                // 循环表数据
                for (int j = 0; j <gaoxuetangriji.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(gaoxuetangriji.get(j).getUsercode());
                    row.createCell(1).setCellValue(gaoxuetangriji.get(j).getName());
                    row.createCell(2).setCellValue(gaoxuetangriji.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(exportBasicUserMapper.chazhaoshijian(gaoxuetangriji.get(j).getHealthAssessment()));
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(gaoxuetangriji.get(j).getZong());
                        row.createCell(5).setCellValue(gaoxuetangriji.get(j).getNewtime());
                        row.createCell(6).setCellValue(gaoxuetangriji.get(j).getZao()==0?"未开始":gaoxuetangriji.get(j).getZao()==1?"正在执行":gaoxuetangriji.get(j).getZao()==2?"已结束":gaoxuetangriji.get(j).getZao()==3?"已取消":"已结束");
                    }
                }
            }

            if (sheetname.get(i).equals("健康计划-高血脂")) {
                // 循环表数据
                for (int j = 0; j <gaoxuezhiriji.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(gaoxuezhiriji.get(j).getUsercode());
                    row.createCell(1).setCellValue(gaoxuezhiriji.get(j).getName());
                    row.createCell(2).setCellValue(gaoxuezhiriji.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(exportBasicUserMapper.chazhaoshijian(gaoxuezhiriji.get(j).getHealthAssessment()));
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(gaoxuezhiriji.get(j).getZong());
                        row.createCell(5).setCellValue(gaoxuezhiriji.get(j).getNewtime());
                        row.createCell(6).setCellValue(gaoxuezhiriji.get(j).getZao()==0?"未开始":gaoxuezhiriji.get(j).getZao()==1?"正在执行":gaoxuezhiriji.get(j).getZao()==2?"已结束":gaoxuezhiriji.get(j).getZao()==3?"已取消":"已结束");
                    }
                }
            }

            if (sheetname.get(i).equals("健康计划-高尿酸")) {
                // 循环表数据
                for (int j = 0; j <gaoniansuanriji.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(gaoniansuanriji.get(j).getUsercode());
                    row.createCell(1).setCellValue(gaoniansuanriji.get(j).getName());
                    row.createCell(2).setCellValue(gaoniansuanriji.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(exportBasicUserMapper.chazhaoshijian(gaoniansuanriji.get(j).getHealthAssessment()));
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(gaoniansuanriji.get(j).getZong());
                        row.createCell(5).setCellValue(gaoniansuanriji.get(j).getNewtime());
                        row.createCell(6).setCellValue(gaoniansuanriji.get(j).getZao()==0?"未开始":gaoniansuanriji.get(j).getZao()==1?"正在执行":gaoniansuanriji.get(j).getZao()==2?"已结束":gaoniansuanriji.get(j).getZao()==3?"已取消":"已结束");
                    }
                }
            }

            if (sheetname.get(i).equals("健康计划-减脂")) {
                // 循环表数据
                for (int j = 0; j <jianzhiriji.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(jianzhiriji.get(j).getUsercode());
                    row.createCell(1).setCellValue(jianzhiriji.get(j).getName());
                    row.createCell(2).setCellValue(jianzhiriji.get(j).getSex() .equals("1") ? "男" : "女");
                    row.createCell(3).setCellValue(exportBasicUserMapper.chazhaoshijian(jianzhiriji.get(j).getHealthAssessment()));
                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(4).setCellValue(jianzhiriji.get(j).getZong());
                        row.createCell(5).setCellValue(jianzhiriji.get(j).getNewtime());
                        row.createCell(6).setCellValue(jianzhiriji.get(j).getZao()==0?"未开始":jianzhiriji.get(j).getZao()==1?"正在执行":jianzhiriji.get(j).getZao()==2?"已结束":jianzhiriji.get(j).getZao()==3?"已取消":"已结束");
                    }
                }
            }



            if (sheetname.get(i).equals("健康习惯日记")) {
                // 循环表数据
                for (int j = 0; j <jiankangxiguanriji2.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(jiankangxiguanriji2.get(j).getUsercode());
                    row.createCell(1).setCellValue(jiankangxiguanriji2.get(j).getName());
                    row.createCell(2).setCellValue(jiankangxiguanriji2.get(j).getSex() .equals("1") ? "男" : "女");

                    if(basicUser.getYonghu().equals(1+"")){
                        row.createCell(3).setCellValue(jiankangxiguanriji2.get(j).getZong());
                        row.createCell(4).setCellValue(jiankangxiguanriji2.get(j).getNewtime());

                    }
                }
            }



        }

        ExcelUtilQ.exportExcel(response, workbook);
    }
}
