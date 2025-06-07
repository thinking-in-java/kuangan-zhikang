package com.yihe.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.yihe.common.utils.ReflectionUtil;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.*;
import com.yihe.system.service.YhDiseaseAssessReportService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * YhDiseaseAssessReportService实现 Created by dyb on 2020/3/18.
 */
@Service
public class YhDiseaseAssessReportServiceImpl implements YhDiseaseAssessReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhDiseaseAssessReportServiceImpl.class);

    @Autowired
    YhDiseaseAssessReportMapper yhDiseaseAssessReportMapper;
    @Autowired
    YhFoodSrecipeRecordMapper YhFoodSrecipeRecordMapper;
    @Autowired
    YhFoodUserScrecipeWebNewServiceImpl YhFoodUserScrecipeWebNewServiceImpl;
    @Autowired
    YhFoodCookBookMapper YhFoodCookBookMapper;
    @Autowired
    YhUserReginfoService yhUserReginfoService;
    @Autowired
    private YhDiseaseRiskResultMapper YhDiseaseRiskResultMapper;
    @Autowired
    private YhDiseaseAssessTotalReport2Mapper YhDiseaseAssessTotalReport2Mapper;
    @Autowired
    private YhQuestionCheckJson2WebMapper YhQuestionCheckJson2WebMapper;
    @Autowired
    private YhDiseaseRiskResult2WebMapper YhDiseaseRiskResult2WebMapper;

    public static String toCamelCase(String s) {
        /** 下划线 */
        final char SEPARATOR = '_';
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        double pow = Math.pow(78, 1.0026);
        double pow2 = pow * 0.9686;
        System.out.println(pow2);
    }

    @Override
    public int selectByPrimaryKeyBycount(String usercode) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.selectByPrimaryKeyBycount(usercode);
    }

    @Override
    public List<YhDiseaseAssessReport> selectByPrimaryKeyByrequestserialNum(YhDicAssessmentScreen selectByPrimaryKey) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.selectByPrimaryKeyByrequestserialNum(selectByPrimaryKey);
    }

    @Override
    public long countByExample(YhDiseaseAssessReportExample example) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(YhDiseaseAssessReportExample example) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(YhDiseaseAssessReport record) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.insert(record);
    }

    @Override
    public int insertSelective(YhDiseaseAssessReport record) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.insertSelective(record);
    }

    @Override
    public List<YhDiseaseAssessReport> selectByExample(YhDiseaseAssessReportExample example) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.selectByExample(example);
    }

    @Override
    public YhDiseaseAssessReport selectByPrimaryKey(Integer id) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(YhDiseaseAssessReport record, YhDiseaseAssessReportExample example) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(YhDiseaseAssessReport record, YhDiseaseAssessReportExample example) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(YhDiseaseAssessReport record) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(YhDiseaseAssessReport record) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.updateByPrimaryKey(record);
    }

    @Override
    public String findYhDiseaseAssessReportByAbnormal(String serialNum) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.findYhDiseaseAssessReportByAbnormal(serialNum);
    }

    @Override
    public List<Map<String, String>> findxls() {
        return yhDiseaseAssessReportMapper.findxls();
    }

    @Override
    public List<YhDiseaseAssessReport> findAssessReportInfoList(String usercode) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.findAssessReportInfoList(usercode);
    }

    @Override
    public YhDiseaseAssessReport findUserAssessReportInfoByTimeDesc(String usercode) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.findUserAssessReportInfoByTimeDesc(usercode);
    }

    @Override
    public List<YhDiseaseAssessReport> findAssessReportByRs(String rs) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.findAssessReportByRs(rs);
    }

    public String sportInfo(Object document) {
        String sport_in = JsonPath.read(document, "$.sport_in") + "";
        String sport_freq = JsonPath.read(document, "$.sport_freq") + "";
        if (sport_in.equals("1")) {
            // 参加过
            if (sport_freq.equals("1") || sport_freq.equals("2") || sport_freq.equals("3")) {
                return "您的体力活动水平不足，建议您按照初期体育健身活动方案持续2~4周，然后根据自身适应情况，过渡到中期体育健身活动方案持续2~4周，最后使用长期体育健身活动方案";
            } else if (sport_freq.equals("4")) {
                return "您的体力活动水平不足，建议您按照中期体育健身活动方案持续2~4周，然后根据自身适应情况，过渡到长期体育健身活动方案，";
            } else {
                return "您的体力活动水平充足，建议您按照长期体育健身活动方案来达到运动多样化的目的，";
            }
        } else {
            // 没参加过
            return "您的体力活动水平不足，建议您按照初期体育健身活动方案持续2~4周，然后根据自身适应情况，过渡到中期体育健身活动方案持续2~4周，最后使用长期体育健身活动方案";
        }

    }

    /**
     * 报告中饮食推荐模块
     */
    public List<List<RiskFactorsVOJO>> foodInfo(String rs) {
        String findFoodSrecipeRecordByRsInfo = YhFoodSrecipeRecordMapper.findFoodSrecipeRecordByRsInfo(rs);
        List<List<RiskFactorsVOJO>> vlists = new ArrayList<>();
        if (com.yihe.system.utils.StringUtil.isNotEmpty(findFoodSrecipeRecordByRsInfo)) {
            String[] split = findFoodSrecipeRecordByRsInfo.split(";");
            for (int i = 0; i < 3; i++) {
                List<RiskFactorsVOJO> vlist = new ArrayList<>();

                String zaoInfo = "";
                String wuInfo = "";
                String wanInfo = "";
                String jiaInfo = "";
                float zaoKar = 0f;
                float wuKar = 0f;
                float wanKar = 0f;
                float jiaKar = 0f;
                YhFoodUserScrecipeWebNew selectByPrimaryKey = YhFoodUserScrecipeWebNewServiceImpl
                        .selectByPrimaryKey(Integer.valueOf(split[i]));
                if (null != selectByPrimaryKey) {
                    String getfElements = selectByPrimaryKey.getfElements();
                    if (StringUtils.isNotEmpty(getfElements)) {
                        String[] split2 = getfElements.split(";");
                        for (int j = 0; j < split2.length; j++) {
                            // 1_82589_100
                            String string = split2[j];
                            String[] split3 = string.split("_");
                            String type = split3[0];
                            String foodid = split3[1];
                            String keshu = split3[2];
                            YhFoodCookBook foodInfo = YhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(foodid));
                            if (null == foodInfo) {
                                continue;
                            }
                            switch (type) {
                                case "1":
                                    zaoInfo = zaoInfo + foodInfo.getfCName() + " " + keshu + "克;";
                                    float zaonum = foodInfo.getEnergyKcal() / 100 * Float.valueOf(keshu);
                                    zaoKar = zaoKar + zaonum;
                                    break;
                                case "2":
                                    wuInfo = wuInfo + foodInfo.getfCName() + " " + keshu + "克;";
                                    float wunum = foodInfo.getEnergyKcal() / 100 * Float.valueOf(keshu);
                                    wuKar = wuKar + wunum;
                                    break;
                                case "3":
                                    wanInfo = wanInfo + foodInfo.getfCName() + " " + keshu + "克;";
                                    float wannum = foodInfo.getEnergyKcal() / 100 * Float.valueOf(keshu);
                                    wanKar = wanKar + wannum;
                                    break;
                                case "4":
                                    jiaInfo = jiaInfo + foodInfo.getfCName() + " " + keshu + "克;";
                                    float jianum = foodInfo.getEnergyKcal() / 100 * Float.valueOf(keshu);
                                    jiaKar = jiaKar + jianum;
                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                }
                RiskFactorsVOJO vojo = new RiskFactorsVOJO();
                vojo.setRiskFactors("早餐");
                vojo.setThisTime(zaoInfo);
                float zaoBfb = zaoKar / (zaoKar + wuKar + wanKar + jiaKar) * 100;
                float zaoBfb2 = (float) (Math.round(zaoBfb * 10)) / 10;
                vojo.setIdealLevel(String.valueOf(zaoBfb2) + "%");
                vojo.setLastTime(String.valueOf((float) (Math.round(zaoKar * 100)) / 100));
                vlist.add(vojo);

                RiskFactorsVOJO vojo1 = new RiskFactorsVOJO();
                vojo1.setRiskFactors("午餐");
                vojo1.setThisTime(wuInfo);
                float wuBfb = wuKar / (zaoKar + wuKar + wanKar + jiaKar) * 100;
                float wuBfb2 = (float) (Math.round(wuBfb * 10)) / 10;
                vojo1.setIdealLevel(String.valueOf(wuBfb2) + "%");
                vojo1.setLastTime(String.valueOf((float) (Math.round(wuKar * 100)) / 100));
                vlist.add(vojo1);

                RiskFactorsVOJO vojo11 = new RiskFactorsVOJO();
                vojo11.setRiskFactors("晚餐");
                vojo11.setThisTime(wanInfo);
                float wanBfb = wanKar / (zaoKar + wuKar + wanKar + jiaKar) * 100;
                float wanBfb2 = (float) (Math.round(wanBfb * 10)) / 10;
                vojo11.setIdealLevel(String.valueOf(wanBfb2) + "%");
                vojo11.setLastTime(String.valueOf((float) (Math.round(wanKar * 100)) / 100));
                vlist.add(vojo11);

                RiskFactorsVOJO vojo111 = new RiskFactorsVOJO();
                vojo111.setRiskFactors("加餐");
                vojo111.setThisTime(jiaInfo);
                float jiaBfb = jiaKar / (zaoKar + wuKar + wanKar + jiaKar) * 100;
                float jiaBfb2 = (float) (Math.round(jiaBfb * 10)) / 10;
                vojo111.setIdealLevel(String.valueOf(jiaBfb2) + "%");
                vojo111.setLastTime(String.valueOf((float) (Math.round(jiaKar * 100)) / 100));
                vlist.add(vojo111);

                vlists.add(vlist);
            }
        }
        return vlists;
    }

    /**
     * 风险评估报告
     */
    @Override
    public List<YhReportDetailsVOJO> findDiseaseNameAndRiskLevelByRs(String rs, String sex, Model model, Object document, Object lastdocument) {
        if (StringUtils.isEmpty(rs) || StringUtils.isEmpty(sex)) {
            return null;
        }
        //危险因素控制目标及改善途径
        Map<String, String> mapResolve = new HashMap<>();

        //危险因素提示：您做好的方面
        Set<String> goodList = new LinkedHashSet();
        //危险因素提示：您做得不够好的方面
        Set<String> nogoodList = new LinkedHashSet();


        String huanb = "";
        String gaofx = "";
        String jiaogaofx = "";
        String zhongdengfx = "";
        String difx = "";

        //查询本次评估每个疾病信息
        List<YhReportDetailsVOJO> list = yhDiseaseAssessReportMapper.findDiseaseNameAndRiskLevelByRsNew(rs);
        //遍历每个疾病信息
        for (YhReportDetailsVOJO yhDiseaseAssessReport : list) {
            // 疾病id
            Integer diseasid = yhDiseaseAssessReport.getDiseasid();
            System.out.println("diseasid----------" + diseasid);

            String riskLevel = yhDiseaseAssessReport.getRiskLevel();


            if (riskLevel.contains("患") || riskLevel.contains("疑")) {
                huanb = huanb + yhDiseaseAssessReport.getDiseaseName() + "、";
                yhDiseaseAssessReport.setSign(-450);
            } else if (riskLevel.contains("高") && !riskLevel.contains("高血压") && !riskLevel.contains("较高")) {
                gaofx = gaofx + yhDiseaseAssessReport.getDiseaseName() + "、";
                yhDiseaseAssessReport.setSign(-270);
            } else if (riskLevel.contains("较高")) {
                jiaogaofx = jiaogaofx + yhDiseaseAssessReport.getDiseaseName() + "、";
                yhDiseaseAssessReport.setSign(-90);
            } else if (riskLevel.contains("中等")) {
                zhongdengfx = zhongdengfx + yhDiseaseAssessReport.getDiseaseName() + "、";
                yhDiseaseAssessReport.setSign(90);
            } else if (riskLevel.contains("低") || riskLevel.contains("较低")) {
                if (riskLevel.contains("较低")) {
                    yhDiseaseAssessReport.setSign(280);
                } else {
                    yhDiseaseAssessReport.setSign(455);
                }
                difx = difx + yhDiseaseAssessReport.getDiseaseName() + "、";
            }


            List<YhReportResolveVOJO> glist = yhDiseaseAssessReport.getGlist();
            if (null != glist) {
                for (YhReportResolveVOJO yhDiseaseAssessResolve : glist) {
                    if (!mapResolve.containsKey(yhDiseaseAssessResolve.getType())) {
                        mapResolve.put(yhDiseaseAssessResolve.getType(), yhDiseaseAssessResolve.getName());
                    }
                }
            }


            //查询每个疾病雷达图
            List<YHDiseaseRiskOptimal> findYhDiseaseRiskOptimal = yhDiseaseAssessReportMapper.findYhDiseaseRiskOptimal(String.valueOf(diseasid), 1, sex);
            //查询每个疾病相关危险因素
            List<YHDiseaseRiskOptimal> yhDiseaseRiskOptimalMoreList = yhDiseaseAssessReportMapper.findYhDiseaseRiskOptimal(String.valueOf(diseasid), 3, sex);

            //查询每个疾病雷达图结果大小
            int yhDiseaseRiskOptimalSize = findYhDiseaseRiskOptimal.size();
            //查询每个疾病相关危险因素结果大小
            int yhDiseaseRiskOptimalMoreSize = yhDiseaseRiskOptimalMoreList.size();

            //每个相关危险因素
            List<RiskFactorsVOJO> rlist = new ArrayList<RiskFactorsVOJO>();

            //创建危险因素数组
            YhDiseaseRiskFactorGroups[] yhDiseaseRiskFactorGroups = new YhDiseaseRiskFactorGroups[yhDiseaseRiskOptimalMoreSize];

            YhDiseaseRiskFactorGroups yhDiseaseRiskFactors = null;


            if (null != lastdocument) {
                List<RiskFactorsVOJO> rlistInfo = getRlistInfo(sex, diseasid, lastdocument, document);

                rlist.addAll(rlistInfo);

            } else {
                List<RiskFactorsVOJO> rlistInfo = getRlistInfo(sex, diseasid, null, document);
                rlist.addAll(rlistInfo);
            }

            yhDiseaseAssessReport.setRlist(rlist);

            yhDiseaseAssessReport.setFxlist(yhDiseaseRiskFactorGroups);


            //评估返回数据
            YhDiseaseRiskResult2 yhDiseaseRiskResult = YhDiseaseRiskResultMapper.selectRiskResulByRequestNum(rs);
            Thread leida = new Thread(() -> {
                //设置雷达图
                setYhReportDetailsVOJO(yhDiseaseRiskResult, yhDiseaseAssessReport, findYhDiseaseRiskOptimal, yhDiseaseRiskOptimalSize);
            }, "leida");
            leida.start();


            if (yhDiseaseRiskOptimalMoreSize > 0) {
                // 查询评估相关危险因素
                for (int j = 0; j < yhDiseaseRiskOptimalMoreSize; j++) {
                    YHDiseaseRiskOptimal yhDiseaseRiskOptimal = yhDiseaseRiskOptimalMoreList.get(j);
                    yhDiseaseRiskFactors = new YhDiseaseRiskFactorGroups();
                    yhDiseaseRiskFactors.setDiseaseRiskFactorName(yhDiseaseRiskOptimal.getQname());
                    // 调用计算雷达图以及风险因素
                    doDiseaseRiskResult2(yhDiseaseRiskOptimal, yhDiseaseRiskResult, yhDiseaseRiskFactors);
                    yhDiseaseRiskFactorGroups[j] = yhDiseaseRiskFactors;
                    String xpath = yhDiseaseRiskOptimal.getXpath();
                    String qname = yhDiseaseRiskOptimal.getQname();
                    if (null != yhDiseaseRiskFactors.getDiseaseRiskFactorBadge()
                            && null != yhDiseaseRiskFactors.getDiseaseRiskFactorSegment()) {
                        if (yhDiseaseRiskFactors.getDiseaseRiskFactorBadge() <= yhDiseaseRiskFactors
                                .getDiseaseRiskFactorSegment() && !"1".equals(yhDiseaseRiskOptimal.getRadarRangePoint())) {
                            goodList.add(yhDiseaseRiskOptimal.getQname() + ":" + yhDiseaseRiskOptimal.getOptimal());
                        } else if (!"1".equals(yhDiseaseRiskOptimal.getRadarRangePoint())) {
                            nogoodList.add(yhDiseaseRiskOptimal.getQname());
                        }
                    }
                    String returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(new YhDiseaseRiskResultAppVOJO(xpath, String.valueOf(0), rs));
                    //相关因素
                    if (StringUtils.isNotEmpty(returnStr)) {
                        JSONObject jsonObject = JSONObject.parseObject(returnStr);
                        RiskFactorsVOJO rv = new RiskFactorsVOJO(qname, jsonObject.getString("now_value"), exceedingTheStandard(xpath, jsonObject.getString("now_value"), sex,
                                diseasid.toString()), jsonObject.getString("last_value"), exceedingTheStandard(xpath, jsonObject.getString("last_value"), sex,
                                diseasid.toString()), jsonObject.getString("change_value"), jsonObject.getString("Ideal_value"));
                        rlist.add(getRiskFactorsVOJO(rv, qname, document, lastdocument, diseasid, jsonObject));
                    }
                }
            }

            try {
                leida.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            yhDiseaseAssessReport.setFlist(yhDiseaseRiskFactorGroups);

        }

        model.addAttribute("huanb", huanb.equals("") ? "无" : huanb.substring(0, huanb.length() - 1));
        model.addAttribute("gaofx", gaofx.equals("") ? "无" : gaofx.substring(0, gaofx.length() - 1));
        model.addAttribute("jiaogaofx", jiaogaofx.equals("") ? "无" : jiaogaofx.substring(0, jiaogaofx.length() - 1));
        model.addAttribute("zhongdengfx", zhongdengfx.equals("") ? "无" : zhongdengfx.substring(0, zhongdengfx.length() - 1));
        model.addAttribute("difx", difx.equals("") ? "无" : difx.substring(0, difx.length() - 1));
        model.addAttribute("mapResolve", mapResolve);
        model.addAttribute("goodList", goodList);
        return list;
    }



    @Override
    public List<YhDiseaseAssessReport> findDiseaseNameAndRiskLevelByRs(String rs, String sex)
            throws InvocationTargetException {
        // TODO Auto-generated method stub
        if (StringUtils.isEmpty(rs) || StringUtils.isEmpty(sex)) {
            return null;
        }
        //查询本次评估每个疾病信息
        List<YhDiseaseAssessReport> list = yhDiseaseAssessReportMapper.findDiseaseNameAndRiskLevelByRs(rs);
        //遍历每个疾病信息
        for (YhDiseaseAssessReport CSDiseaseAssessReport : list) {
            // 疾病id
            Integer diseasid = CSDiseaseAssessReport.getDiseasid();
            //查询每个疾病改善指南
            List<YHDiseaseAssessResolve> findAssessResolveInfoByrs = yhDiseaseAssessReportMapper
                    .findAssessResolveInfoByrs(rs, diseasid);
            // 改善指南
            CSDiseaseAssessReport.setGlist(findAssessResolveInfoByrs);
            if (diseasid == 11) {
                continue;
            }
            List<YHDiseaseRiskOptimal> goodList = new ArrayList<>();

            List<YHDiseaseRiskOptimal> nogoodList = new ArrayList<>();

            //查询每个疾病雷达图
            List<YHDiseaseRiskOptimal> findCSDiseaseRiskOptimal = yhDiseaseAssessReportMapper
                    .findCSDiseaseRiskOptimal(String.valueOf(diseasid), 1, sex);
            //查询每个疾病相关危险因素
            List<YHDiseaseRiskOptimal> CSDiseaseRiskOptimalMoreList = yhDiseaseAssessReportMapper
                    .findCSDiseaseRiskOptimal(String.valueOf(diseasid), 3, sex);
            //查询每个疾病雷达图结果大小
            int YiBoDiseaseRiskOptimalSize = findCSDiseaseRiskOptimal.size();
            //查询每个疾病相关危险因素结果大小
            int YiBoDiseaseRiskOptimalMoreSize = CSDiseaseRiskOptimalMoreList.size();

            //每个相关危险因素
            List<RiskFactorsVOJO> rlist = new ArrayList<RiskFactorsVOJO>();

            //创建雷达图数组
            YhDiseaseRiskFactorGroupsVOJO[] CSDiseaseRiskFactorGroupsVOJOlist = new YhDiseaseRiskFactorGroupsVOJO[YiBoDiseaseRiskOptimalSize];
            //创建危险因素数组
            YhDiseaseRiskFactorGroups[] CSDiseaseRiskFactorGroups = new YhDiseaseRiskFactorGroups[YiBoDiseaseRiskOptimalMoreSize];

            YhDiseaseRiskFactorGroups YiBoDiseaseRiskOptimalRiskFactors = null;

            //理想风险比值数组
            Integer[] lixiang = new Integer[YiBoDiseaseRiskOptimalSize];
            //当前风险比值数组
            Integer[] dangqian = new Integer[YiBoDiseaseRiskOptimalSize];

            //查询答案json
            String findQuestionCheckForCheckAnswerByrs = findQuestionCheckForCheckAnswerByrs(rs);
            if (StringUtils.isEmpty(findQuestionCheckForCheckAnswerByrs)) {
                continue;
            }

            // 将json字符串解析成document
            Object document = null;
            document = Configuration.defaultConfiguration().jsonProvider().parse(findQuestionCheckForCheckAnswerByrs);

            YhQuestionCheckJson2Web selectByRsLimit1 = YhQuestionCheckJson2WebMapper.selectByRsLimit1(rs);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取String类型的时间
            String timeInfo = sdf.format(selectByRsLimit1.getUserCheckTime());
            //健康指标具体变化 查询上次
            YhDiseaseAssessTotalReport2web lastRsInfo = YhDiseaseAssessTotalReport2Mapper
                    .findAssessTotalReportLastInfo(selectByRsLimit1.getUserCode(), timeInfo);
            Object lastdocument = null;
            if (null != lastRsInfo) {
                YhQuestionCheckJson2Web selectByRsLimit2 = YhQuestionCheckJson2WebMapper.selectByRsLimit1(lastRsInfo.getRequestSerialnum());
                String findQuestionCheckForCheckAnswerByrs2 = selectByRsLimit2.getUserCheckAnswer();

                lastdocument = Configuration.defaultConfiguration().jsonProvider()
                        .parse(findQuestionCheckForCheckAnswerByrs2);
                List<RiskFactorsVOJO> rlistInfo = getRlistInfo(sex, diseasid, lastdocument, document);
                rlist.addAll(rlistInfo);

            } else {
                List<RiskFactorsVOJO> rlistInfo = getRlistInfo(sex, diseasid, null, document);
                rlist.addAll(rlistInfo);
            }

            CSDiseaseAssessReport.setRlist(rlist);

            CSDiseaseAssessReport.setFxlist(CSDiseaseRiskFactorGroups);


            //评估返回数据
            YhDiseaseRiskResult2 YiBoDiseaseRiskOptimalRiskResult = YhDiseaseRiskResultMapper.selectRiskResulByRequestNum(rs);
            if (YiBoDiseaseRiskOptimalMoreSize > 0) {
                // 查询评估相关危险因素

                for (int j = 0; j < YiBoDiseaseRiskOptimalMoreSize; j++) {
                    YHDiseaseRiskOptimal CSDiseaseRiskOptimal = CSDiseaseRiskOptimalMoreList.get(j);
                    YiBoDiseaseRiskOptimalRiskFactors = new YhDiseaseRiskFactorGroups();
                    YiBoDiseaseRiskOptimalRiskFactors.setDiseaseRiskFactorName(CSDiseaseRiskOptimal.getQname());
                    // 调用计算雷达图以及风险因素
                    doDiseaseRiskResult2(CSDiseaseRiskOptimal, YiBoDiseaseRiskOptimalRiskResult, YiBoDiseaseRiskOptimalRiskFactors);
                    CSDiseaseRiskFactorGroups[j] = YiBoDiseaseRiskOptimalRiskFactors;
                    String xpath = CSDiseaseRiskOptimal.getXpath();
                    String qname = CSDiseaseRiskOptimal.getQname();


                    if (null != YiBoDiseaseRiskOptimalRiskFactors.getDiseaseRiskFactorBadge()
                            && null != YiBoDiseaseRiskOptimalRiskFactors.getDiseaseRiskFactorSegment()) {
                        if (YiBoDiseaseRiskOptimalRiskFactors.getDiseaseRiskFactorBadge() <= YiBoDiseaseRiskOptimalRiskFactors
                                .getDiseaseRiskFactorSegment()) {
                            goodList.add(CSDiseaseRiskOptimal);
                        } else {
                            nogoodList.add(CSDiseaseRiskOptimal);
                        }
                    }

                    YhDiseaseRiskResultAppVOJO vojo = new YhDiseaseRiskResultAppVOJO();
                    vojo.setColumnName(xpath);
                    vojo.setDid(String.valueOf(0));
                    vojo.setRs(rs);
                    String returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);

                    if (StringUtils.isNotEmpty(returnStr)) {
                        RiskFactorsVOJO rv = new RiskFactorsVOJO();
                        JSONObject jsonObject = JSONObject.parseObject(returnStr);
                        rv.setRiskFactors(qname);
                        rv.setThisTimeColor(exceedingTheStandard(xpath, jsonObject.getString("now_value"), sex,
                                diseasid.toString()));
                        rv.setLastTimeColor(exceedingTheStandard(xpath, jsonObject.getString("last_value"), sex,
                                diseasid.toString()));
                        rv.setThisTime(jsonObject.getString("now_value"));
                        rv.setLastTime(jsonObject.getString("last_value"));
                        rv.setChanges(jsonObject.getString("change_value"));

                        rv.setIdealLevel(jsonObject.getString("Ideal_value"));
                        if ("高密度脂蛋白胆固醇".equals(qname)) {
                            rv.setIdealLevel("≥1.04 mmol/L");
                        }
                        if ("血尿酸".equals(qname)) {
                            //血尿酸
                            String bua = JsonPath.read(document, "$.bua") + "";
                            if (StringUtils.isEmpty(bua)) {
                                rv.setThisTime("--");

                            }
                            if (null != lastRsInfo) {
                                String bualast = JsonPath.read(lastdocument, "$.bua") + "";
                                if (StringUtils.isEmpty(bualast)) {
                                    rv.setLastTime("--");

                                }
                            }
                        }

                        if ("收缩压".equals(qname)) {
                            if (4 != diseasid) {
                                rv.setIdealLevel("＜120 mmHg");
                            } else {
                                rv.setIdealLevel("＜140 mmHg");
                            }
                        }
                        if ("舒张压".equals(qname)) {
                            if (4 != diseasid) {
                                rv.setIdealLevel("＜80 mmHg");
                            } else {
                                rv.setIdealLevel("＜90 mmHg");
                            }
                        }

                        if ("左心室肥大".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
                            }
                        }
                        if ("饮茶情况".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "几乎不饮茶" : "饮茶");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "几乎不饮茶" : "饮茶");
                            }
                        }
                        if ("反式脂肪酸".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "适量" : "过量");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "适量" : "过量");
                            }
                        }
                        if ("油烟接触史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("2") ? "无" : "有");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
                            }
                        }
                        if ("慢性支气管炎病史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
                            }
                        }
                        if ("精神压力".equals(qname)) {
                            /**
                             * 1,没有
                             * 2,偶尔
                             * 3,经常
                             */
                            if (jsonObject.getString("now_value").equals("1")) {
                                rv.setThisTime("没有");
                            } else if (jsonObject.getString("now_value").equals("2")) {
                                rv.setThisTime("偶尔");
                            } else if (jsonObject.getString("now_value").equals("3")) {
                                rv.setThisTime("经常");
                            }


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {

                                if (jsonObject.getString("last_value").equals("1")) {
                                    rv.setLastTime("没有");
                                } else if (jsonObject.getString("last_value").equals("2")) {
                                    rv.setLastTime("偶尔");
                                } else if (jsonObject.getString("last_value").equals("3")) {
                                    rv.setLastTime("经常");
                                }
                            }
                        }
                        if ("肺气肿病史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
                            }
                        }
                        if ("近两年是否新房装修".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("2") ? "否" : "是");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "否" : "是");
                            }
                        }
                        if ("初产年龄".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "≥30岁" : "＜30岁");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "≥30岁" : "＜30岁");
                            }
                        }
                        if ("乳腺癌家族史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
                            }
                        }
                        if ("痛风家族史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
                            }
                        }
                        if ("长期服用肾上腺皮质激素".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
                            }
                        }
                        if ("冠心病史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
                            }
                        }
                        if ("高血压病史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
                            }
                        }
                        if ("糖尿病家族史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
                            }
                        }
                        if ("冠心病家族史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
                            }
                        }
                        if ("高脂饮食".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "避免高脂饮食" : "高脂饮食");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "避免高脂饮食" : "高脂饮食");
                            }
                        }
                        if ("吸烟情况".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "吸烟" : "不吸烟");


                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "吸烟" : "不吸烟");
                            }
                        }
                        if ("被动吸烟".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "是" : "否");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "是" : "否");
                            }

                        }
                        if ("尿路感染".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "是" : "否");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "是" : "否");
                            }

                        }
                        if ("粉尘接触史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
                            }

                        }
                        if ("蔬菜水果摄入".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "不足" : "充足");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "不足" : "充足");
                            }

                        }
                        if ("蔬菜水果".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "不足" : "充足");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "不足" : "充足");
                            }


                        }
                        if ("居住环境".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "不寒冷" : "寒冷");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "不寒冷" : "寒冷");
                            }
                        }
                        if ("牛奶摄入".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "充足" : "不足");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "充足" : "不足");
                            }
                        }
                        if ("初潮年龄".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "不良" : "正常");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "不良" : "正常");
                            }

                        }
                        if ("哺乳喂养时间".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "不良" : "正常");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "不良" : "正常");
                            }

                        }
                        if ("饮酒情况".equals(qname)) {
                            if (jsonObject.getString("now_value").equals("0")) {
                                rv.setThisTime("不喝");
                            } else if (jsonObject.getString("now_value").equals("1")) {
                                rv.setThisTime("饮酒");
                            }
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                if (jsonObject.getString("last_value").equals("0")) {
                                    rv.setLastTime("不喝");
                                } else if (jsonObject.getString("last_value").equals("1")) {
                                    rv.setLastTime("饮酒");
                                }
                            }
                            if (7 != diseasid && 10 != diseasid) {
                                rv.setIdealLevel("不喝酒");
                            }


                        }
                        if ("啤酒摄入".equals(qname)) {
                            if (jsonObject.getString("now_value").equals("2")) {
                                rv.setThisTime("过量");
                            }
                            if (jsonObject.getString("now_value").equals("1")) {
                                rv.setThisTime("适量");
                            }
                            if (jsonObject.getString("now_value").equals("0")) {
                                rv.setThisTime("几乎不喝");
                            }
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                if (jsonObject.getString("last_value").equals("2")) {
                                    rv.setLastTime("过量");
                                }
                                if (jsonObject.getString("last_value").equals("1")) {
                                    rv.setLastTime("适量");
                                }
                                if (jsonObject.getString("last_value").equals("0")) {
                                    rv.setLastTime("几乎不喝");
                                }
                            }

                        }

                        if ("高血脂".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "异常" : "正常");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "异常" : "正常");
                            }

                        }
                        if ("血脂异常".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "异常" : "正常");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "异常" : "正常");
                            }

                        }
                        if ("糖尿病史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
                            }
                        }
                        if ("脑卒中家族史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
                            }
                        }
                        if ("心血管病史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
                            }
                        }
                        if ("高血压家族史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
                            }
                        }
                        if ("体力活动水平".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "充足" : "不足");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "充足" : "不足");
                            }
                        }
                        if ("体力活动".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "充足" : "不足");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "充足" : "不足");
                            }
                        }
                        if ("血脂".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "正常" : "异常");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "正常" : "异常");
                            }
                        }
                        if ("高嘌呤饮食".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "是" : "否");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "是" : "否");
                            }
                        }
                        if ("个人既往骨折史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
                            }
                        }
                        if ("父母髋部骨折史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
                            }
                        }

                        if ("肺癌家族史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
                            }
                        }
                        if ("前列腺癌家族史".equals(qname)) {
                            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
                            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                                rv.setLastTime("-");
                            } else {
                                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
                            }
                        }
                        if (diseasid == 6 && "甘油三酯".equals(qname)) {

                            rv.setIdealLevel("<1.70mmol/L");
                        }
                        rlist.add(rv);
                    }
                }
            }

            //雷达图
            if (null != findCSDiseaseRiskOptimal && YiBoDiseaseRiskOptimalSize != 0) {
                // 查询评估相关危险因素selectRiskResulByRequestNum
                int j = 0;
                for (YHDiseaseRiskOptimal CSDiseaseRiskOptimal : findCSDiseaseRiskOptimal) {

                    YiBoDiseaseRiskOptimalRiskFactors = new YhDiseaseRiskFactorGroups();
                    YiBoDiseaseRiskOptimalRiskFactors.setDiseaseRiskFactorName(CSDiseaseRiskOptimal.getQname());
                    // 调用计算雷达图以及风险因素
                    doDiseaseRiskResult2(CSDiseaseRiskOptimal, YiBoDiseaseRiskOptimalRiskResult, YiBoDiseaseRiskOptimalRiskFactors);

                    YhDiseaseRiskFactorGroupsVOJO GroupsVOJO = new YhDiseaseRiskFactorGroupsVOJO();

                    String qname = CSDiseaseRiskOptimal.getQname();
                    GroupsVOJO.setName(qname);
                    GroupsVOJO.setMax(100);
                    CSDiseaseRiskFactorGroupsVOJOlist[j] = GroupsVOJO;

                    // 题目层级
                    YiBoDiseaseRiskOptimalRiskFactors.setDiseaseRiskFactorName(YiBoDiseaseRiskOptimalRiskFactors.getName());
                    //理想风险比值
                    lixiang[j] = YiBoDiseaseRiskOptimalRiskFactors.getDiseaseRiskFactorSegment();
                    //当前风险比值
                    dangqian[j] = YiBoDiseaseRiskOptimalRiskFactors.getDiseaseRiskFactorBadge();

                    j++;
                }
                CSDiseaseAssessReport.setGvojolist(CSDiseaseRiskFactorGroupsVOJOlist);
                CSDiseaseAssessReport.setLixiangList(lixiang);
                CSDiseaseAssessReport.setDangqianList(dangqian);
                CSDiseaseAssessReport.setGoodList(goodList);
                CSDiseaseAssessReport.setNogoodList(nogoodList);
                CSDiseaseAssessReport.setFlist(CSDiseaseRiskFactorGroups);
            }

        }
        return list;
    }

    //雷达图
    private YhReportDetailsVOJO setYhReportDetailsVOJO(YhDiseaseRiskResult2 yhDiseaseRiskResult, YhReportDetailsVOJO yhDiseaseAssessReport, List<YHDiseaseRiskOptimal> findYhDiseaseRiskOptimal, int yhDiseaseRiskOptimalSize) {
        YhDiseaseRiskFactorGroups yhDiseaseRiskFactors = null;
        //创建雷达图数组
        YhDiseaseRiskFactorGroupsVOJO[] YhDiseaseRiskFactorGroupsVOJOlist = new YhDiseaseRiskFactorGroupsVOJO[yhDiseaseRiskOptimalSize];
        //理想风险比值数组
        Integer[] lixiang = new Integer[yhDiseaseRiskOptimalSize];
        //当前风险比值数组
        Integer[] dangqian = new Integer[yhDiseaseRiskOptimalSize];
        //雷达图
        if (null != findYhDiseaseRiskOptimal && yhDiseaseRiskOptimalSize != 0) {
            // 查询评估相关危险因素selectRiskResulByRequestNum
            int j = 0;
            for (YHDiseaseRiskOptimal yhDiseaseRiskOptimal : findYhDiseaseRiskOptimal) {

                yhDiseaseRiskFactors = new YhDiseaseRiskFactorGroups();
                yhDiseaseRiskFactors.setDiseaseRiskFactorName(yhDiseaseRiskOptimal.getQname());
                // 调用计算雷达图以及风险因素
                doDiseaseRiskResult2(yhDiseaseRiskOptimal, yhDiseaseRiskResult, yhDiseaseRiskFactors);

                YhDiseaseRiskFactorGroupsVOJO GroupsVOJO = new YhDiseaseRiskFactorGroupsVOJO();

                String qname = yhDiseaseRiskOptimal.getQname();
                GroupsVOJO.setName(qname);
                GroupsVOJO.setMax(100);
                YhDiseaseRiskFactorGroupsVOJOlist[j] = GroupsVOJO;
                // 题目层级
                yhDiseaseRiskFactors.setDiseaseRiskFactorName(yhDiseaseRiskFactors.getName());
                // 调用计算雷达图以及风险因素
                //理想风险比值
                lixiang[j] = yhDiseaseRiskFactors.getDiseaseRiskFactorSegment();
                //当前风险比值
                dangqian[j] = yhDiseaseRiskFactors.getDiseaseRiskFactorBadge();
                j++;
            }
            yhDiseaseAssessReport.setGvojolist(YhDiseaseRiskFactorGroupsVOJOlist);
            yhDiseaseAssessReport.setLixiangList(lixiang);
            yhDiseaseAssessReport.setDangqianList(dangqian);
        }
        return yhDiseaseAssessReport;
    }

    //个性化处理
    private RiskFactorsVOJO getRiskFactorsVOJO(RiskFactorsVOJO rv, String qname, Object document, Object lastdocument, Integer diseasid, JSONObject jsonObject) {
        if ("左心室肥大".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("2") ? "无" : "有");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("2") ? "无" : "有");
            }
        }
        if ("饮茶情况".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "几乎不饮茶" : "饮茶");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "几乎不饮茶" : "饮茶");
            }
        }
        if ("高密度脂蛋白胆固醇".equals(qname)) {
            rv.setIdealLevel("≥1.04 mmol/L");
        }
        if ("血尿酸".equals(qname)) {
            String bua = JsonPath.read(document, "$.bua") + "";
            if (StringUtils.isEmpty(bua)) {
                rv.setThisTime("--");
            }
            if (null != lastdocument) {
                String bualast = JsonPath.read(lastdocument, "$.bua") + "";
                if (StringUtils.isEmpty(bualast)) {
                    rv.setLastTime("--");
                }
            }
        }
        if ("收缩压".equals(qname)) {
            if (4 != diseasid) {
                rv.setIdealLevel("＜120 mmHg");
            } else {
                rv.setIdealLevel("＜140 mmHg");
            }
        }
        if ("舒张压".equals(qname)) {
            if (4 != diseasid) {
                rv.setIdealLevel("＜80 mmHg");
            } else {
                rv.setIdealLevel("＜90 mmHg");
            }
        }

        if ("反式脂肪酸".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "适量" : "过量");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "适量" : "过量");
            }
        }
        if ("油烟接触史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("2") ? "无" : "有");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }
        }
        if ("慢性支气管炎病史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("2") ? "无" : "有");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }
        }
        if ("精神压力".equals(qname)) {
            /**
             * 1,没有
             * 2,偶尔
             * 3,经常
             */
            if (jsonObject.getString("now_value").equals("1")) {
                rv.setThisTime("没有");
            } else if (jsonObject.getString("now_value").equals("2")) {
                rv.setThisTime("偶尔");
            } else if (jsonObject.getString("now_value").equals("3")) {
                rv.setThisTime("经常");
            }


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {

                if (jsonObject.getString("last_value").equals("1")) {
                    rv.setLastTime("没有");
                } else if (jsonObject.getString("last_value").equals("2")) {
                    rv.setLastTime("偶尔");
                } else if (jsonObject.getString("last_value").equals("3")) {
                    rv.setLastTime("经常");
                }
            }
        }
        if ("肺气肿病史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }
        }
        if ("近两年是否新房装修".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("2") ? "否" : "是");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "否" : "是");
            }
        }
        if ("初产年龄".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "≥30岁" : "＜30岁");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "≥30岁" : "＜30岁");
            }
        }
        if ("乳腺癌家族史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }
        }
        if ("痛风家族史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }
        }
        if ("长期服用肾上腺皮质激素".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }
        }
        if ("冠心病史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }
        }
        if ("高血压病史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }
        }
        if ("糖尿病家族史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }
        }
        if ("冠心病家族史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }
        }


        if ("高脂饮食".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "避免高脂饮食" : "高脂饮食");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "避免高脂饮食" : "高脂饮食");
            }
        }
        if ("吸烟情况".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "吸烟" : "不吸烟");


            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "吸烟" : "不吸烟");
            }
        }
        if ("被动吸烟".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "是" : "否");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "是" : "否");
            }

        }
        if ("尿路感染".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "是" : "否");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "是" : "否");
            }

        }
        if ("粉尘接触史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "无" : "有");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "无" : "有");
            }

        }
        if ("蔬菜水果摄入".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "不足" : "充足");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "不足" : "充足");
            }

        }
        if ("蔬菜水果".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "不足" : "充足");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "不足" : "充足");
            }


        }
        if ("居住环境".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "不寒冷" : "寒冷");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "不寒冷" : "寒冷");
            }
        }
        if ("牛奶摄入".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "充足" : "不足");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "充足" : "不足");
            }
        }
        if ("初潮年龄".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "不良" : "正常");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "不良" : "正常");
            }

        }
        if ("哺乳喂养时间".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "不良" : "正常");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "不良" : "正常");
            }

        }
        if ("饮酒情况".equals(qname)) {
            if (jsonObject.getString("now_value").equals("0")) {
                rv.setThisTime("不喝");
            } else if (jsonObject.getString("now_value").equals("1")) {
                rv.setThisTime("饮酒");
            }
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                if (jsonObject.getString("last_value").equals("0")) {
                    rv.setLastTime("不喝");
                } else if (jsonObject.getString("last_value").equals("1")) {
                    rv.setLastTime("饮酒");
                }
            }
            if (7 != diseasid && 10 != diseasid) {
                rv.setIdealLevel("不喝酒");
            }


        }
        if ("啤酒摄入".equals(qname)) {
            if (jsonObject.getString("now_value").equals("2")) {
                rv.setThisTime("过量");
            }
            if (jsonObject.getString("now_value").equals("1")) {
                rv.setThisTime("适量");
            }
            if (jsonObject.getString("now_value").equals("0")) {
                rv.setThisTime("几乎不喝");
            }
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                if (jsonObject.getString("last_value").equals("2")) {
                    rv.setLastTime("过量");
                }
                if (jsonObject.getString("last_value").equals("1")) {
                    rv.setLastTime("适量");
                }
                if (jsonObject.getString("last_value").equals("0")) {
                    rv.setLastTime("几乎不喝");
                }
            }

        }

        if ("高血脂".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "异常" : "正常");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "异常" : "正常");
            }

        }
        if ("血脂异常".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "异常" : "正常");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "异常" : "正常");
            }

        }
        if ("糖尿病史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
            }
        }
        if ("脑卒中家族史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
            }
        }
        if ("心血管病史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
            }
        }
        if ("高血压家族史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
            }
        }
        if ("体力活动水平".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "充足" : "不足");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "充足" : "不足");
            }
        }
        if ("体力活动".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "充足" : "不足");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "充足" : "不足");
            }
        }
        if ("血脂".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("0") ? "正常" : "异常");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("0") ? "正常" : "异常");
            }
        }
        if ("高嘌呤饮食".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "是" : "否");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "是" : "否");
            }
        }
        if ("个人既往骨折史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
            }
        }
        if ("父母髋部骨折史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
            }
        }

        if ("肺癌家族史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
            }
        }
        if ("前列腺癌家族史".equals(qname)) {
            rv.setThisTime(jsonObject.getString("now_value").equals("1") ? "有" : "无");
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
            } else {
                rv.setLastTime(jsonObject.getString("last_value").equals("1") ? "有" : "无");
            }
        }
        if (diseasid == 6 && "甘油三酯".equals(qname)) {

            rv.setIdealLevel("<1.70mmol/L");
        }

        return rv;
    }

    // 代谢综合征发病相关
    public List<RiskFactorsVOJO> metabolize(String rs, String sex) {

        List<RiskFactorsVOJO> rlist = new ArrayList<>();
        rlist.add(getMetabolizeInfo("BMI", rs, "BMI", sex));
        rlist.add(getMetabolizeInfo("diab_his", rs, "糖尿病病史", sex));
        rlist.add(getMetabolizeInfo("FBG", rs, "空腹血糖", sex));
        rlist.add(getMetabolizeInfo("hbp_his", rs, "高血压病史", sex));
        rlist.add(getMetabolizeInfo("sbp", rs, "收缩压", sex));
        rlist.add(getMetabolizeInfo("dbp", rs, "舒张压", sex));
        rlist.add(getMetabolizeInfo("TG", rs, "甘油三脂", sex));
        rlist.add(getMetabolizeInfo("hdlc", rs, "高密度脂蛋白胆固醇", sex));
        return rlist;
    }

    public List<RiskFactorsVOJO> getRlistInfo(String sex, Integer diseasid, Object lastdocument, Object document) {
        List<RiskFactorsVOJO> rlist = new ArrayList<>();
        //冠心病
        if (1 == diseasid) {
            RiskFactorsVOJO rlistadd = new RiskFactorsVOJO();
            String food_pickled = JsonPath.read(document, "$.food_pickled") + "";

            rlistadd.setRiskFactors("咸菜腌渍食品摄入过量");
            if (Integer.valueOf(food_pickled) >= 2) {
                rlistadd.setThisTimeColor("1");
                rlistadd.setThisTime("有");
            } else {
                rlistadd.setThisTimeColor("0");
                rlistadd.setThisTime("无");
            }
            rlistadd.setIdealLevel("无");

            if (null != lastdocument) {
                String food_pickledlast = JsonPath.read(lastdocument, "$.food_pickled") + "";
                if (Integer.valueOf(food_pickledlast) >= 2) {
                    rlistadd.setLastTimeColor("1");
                    rlistadd.setLastTime("有");
                } else {
                    rlistadd.setLastTimeColor("0");
                    rlistadd.setLastTime("无");
                }
                if ("--".equals(rlistadd.getLastTime())) {
                    rlistadd.setChanges("--");
                }
                if (rlistadd.getThisTime().equals(rlistadd.getLastTime())) {
                    rlistadd.setChanges("保持");
                }
                if ("无".equals(rlistadd.getLastTime()) && "有".equals(rlistadd.getThisTime())) {
                    rlistadd.setChanges("恶化");
                }
                if ("有".equals(rlistadd.getLastTime()) && "无".equals(rlistadd.getThisTime())) {
                    rlistadd.setChanges("好转");
                }
            } else {
                rlistadd.setChanges("-");
                rlistadd.setLastTime("--");
            }


            rlist.add(rlistadd);

            RiskFactorsVOJO rlistadd2 = new RiskFactorsVOJO();
            rlistadd2.setRiskFactors("个人患病史");
            rlistadd2.setIdealLevel("无:高血压/糖尿病（1型或2型）/冠心病/不稳定心绞痛");
            //高血压
            String hbp_his_disFlag = JsonPath.read(document, "$.hbp_his.disFlag") + "";
            //糖尿病（1或2型）
            String diab2_his_disFlag = JsonPath.read(document, "$.diab2_his.disFlag") + "";
            //冠心病
            String chd_his_disFlag = JsonPath.read(document, "$.chd_his.disFlag") + "";
            //不稳定心绞痛
            String angina_his_disFlag = JsonPath.read(document, "$.angina_his.disFlag") + "";
            if ("1".equals(hbp_his_disFlag) || "1".equals(diab2_his_disFlag) || "1".equals(chd_his_disFlag) || "1".equals(angina_his_disFlag)) {
                rlistadd2.setThisTimeColor("1");
                rlistadd2.setThisTime("有");
            } else {
                rlistadd2.setThisTimeColor("0");
                rlistadd2.setThisTime("无");
            }
            if (null != lastdocument) {
                //高血压
                String hbp_his_disFlaglast = JsonPath.read(lastdocument, "$.hbp_his.disFlag") + "";
                //糖尿病（1或2型）
                String diab2_his_disFlaglast = JsonPath.read(lastdocument, "$.diab2_his.disFlag") + "";
                //冠心病
                String chd_his_disFlaglast = JsonPath.read(lastdocument, "$.chd_his.disFlag") + "";
                //不稳定心绞痛
                String angina_his_disFlaglast = JsonPath.read(lastdocument, "$.angina_his.disFlag") + "";
                if ("1".equals(hbp_his_disFlaglast) || "1".equals(diab2_his_disFlaglast) || "1".equals(chd_his_disFlaglast) || "1".equals(angina_his_disFlaglast)) {
                    rlistadd2.setLastTimeColor("1");
                    rlistadd2.setLastTime("有");
                } else {
                    rlistadd2.setLastTimeColor("0");
                    rlistadd2.setLastTime("无");
                }
                if ("--".equals(rlistadd2.getLastTime())) {
                    rlistadd2.setChanges("--");
                }
                if (rlistadd2.getThisTime().equals(rlistadd2.getLastTime())) {
                    rlistadd2.setChanges("保持");
                }
                if ("无".equals(rlistadd2.getLastTime()) && "有".equals(rlistadd2.getThisTime())) {
                    rlistadd2.setChanges("恶化");
                }
                if ("有".equals(rlistadd2.getLastTime()) && "无".equals(rlistadd2.getThisTime())) {
                    rlistadd2.setChanges("好转");
                }
            } else {
                rlistadd2.setChanges("-");
                rlistadd2.setLastTime("--");
            }


            rlist.add(rlistadd2);


        }
        //脑卒中
        if (2 == diseasid) {
            RiskFactorsVOJO rlistadd = new RiskFactorsVOJO();
            rlistadd.setRiskFactors("血肌酐");
            rlistadd.setIdealLevel("男54~106;女44~97;");

            String serum_creatinine = JsonPath.read(document, "$.serum_creatinine") + "";
            if (StringUtils.isNotEmpty(serum_creatinine)) {
                rlistadd.setThisTime(serum_creatinine);
                if ("1".equals(sex)) {
                    if (Double.valueOf(serum_creatinine) < 54 || Double.valueOf(serum_creatinine) > 106) {
                        rlistadd.setThisTimeColor("1");
                    }
                }
                if ("2".equals(sex)) {
                    if (Double.valueOf(serum_creatinine) < 44 || Double.valueOf(serum_creatinine) > 97) {
                        rlistadd.setThisTimeColor("1");
                    }
                }

                if (null != lastdocument) {
                    String serum_creatininelast = JsonPath.read(lastdocument, "$.serum_creatinine") + "";
                    if (StringUtils.isNotEmpty(serum_creatininelast)) {
                        rlistadd.setLastTime(serum_creatininelast);
                        if ("1".equals(sex)) {
                            if (Double.valueOf(serum_creatininelast) < 54 || Double.valueOf(serum_creatininelast) < 106) {
                                rlistadd.setLastTimeColor("1");
                            }
                        }
                        if ("2".equals(sex)) {
                            if (Double.valueOf(serum_creatininelast) < 44 || Double.valueOf(serum_creatininelast) > 97) {
                                rlistadd.setLastTimeColor("1");
                            }
                        }
                    }


                } else {
                    rlistadd.setLastTime("-");
                }

                rlist.add(rlistadd);
            }

        }
        //高血压
        if (3 == diseasid) {
            RiskFactorsVOJO rlistadd = new RiskFactorsVOJO();
            String food_pickled = JsonPath.read(document, "$.food_pickled") + "";

            rlistadd.setRiskFactors("咸菜腌渍食品摄入过量");
            if (Integer.valueOf(food_pickled) >= 2) {
                rlistadd.setThisTimeColor("1");
                rlistadd.setThisTime("有");
            } else {
                rlistadd.setThisTimeColor("0");
                rlistadd.setThisTime("无");
            }
            rlistadd.setIdealLevel("无");

            if (null != lastdocument) {
                String food_pickledlast = JsonPath.read(lastdocument, "$.food_pickled") + "";
                if (Integer.valueOf(food_pickledlast) >= 2) {
                    rlistadd.setLastTimeColor("1");
                    rlistadd.setLastTime("有");
                } else {
                    rlistadd.setLastTimeColor("0");
                    rlistadd.setLastTime("无");
                }
                if ("--".equals(rlistadd.getLastTime())) {
                    rlistadd.setChanges("--");
                }
                if (rlistadd.getThisTime().equals(rlistadd.getLastTime())) {
                    rlistadd.setChanges("保持");
                }
                if ("无".equals(rlistadd.getLastTime()) && "有".equals(rlistadd.getThisTime())) {
                    rlistadd.setChanges("恶化");
                }
                if ("有".equals(rlistadd.getLastTime()) && "无".equals(rlistadd.getThisTime())) {
                    rlistadd.setChanges("好转");
                }
            } else {
                rlistadd.setChanges("-");
                rlistadd.setLastTime("--");
            }


            rlist.add(rlistadd);
        }

        //痛风
        if (5 == diseasid) {
            RiskFactorsVOJO rlistadd = new RiskFactorsVOJO();
            rlistadd.setRiskFactors("个人患病史");
            rlistadd.setIdealLevel("无:痛风急性发作期/痛风非急性期/冠心病患病史");
            //痛风急性发作期
            String gae_his_disFlag = JsonPath.read(document, "$.gae_his.disFlag") + "";
            //痛风非急性发作期
            String gna_his_disFlag = JsonPath.read(document, "$.gna_his.disFlag") + "";
            //冠心病
            String chd_his_disFlag = JsonPath.read(document, "$.chd_his.disFlag") + "";
            if ("1".equals(gae_his_disFlag) || "1".equals(gna_his_disFlag) || "1".equals(chd_his_disFlag)) {
                rlistadd.setThisTimeColor("1");
                rlistadd.setThisTime("有");
            } else {
                rlistadd.setThisTimeColor("0");
                rlistadd.setThisTime("无");
            }
            if (null != lastdocument) {
                //痛风急性发作期
                String gae_his_disFlaglast = JsonPath.read(lastdocument, "$.gae_his.disFlag") + "";
                //痛风非急性发作期
                String gna_his_disFlaglast = JsonPath.read(lastdocument, "$.gna_his.disFlag") + "";
                //冠心病
                String chd_his_disFlaglast = JsonPath.read(lastdocument, "$.chd_his.disFlag") + "";
                if ("1".equals(chd_his_disFlaglast) || "1".equals(gna_his_disFlaglast) || "1".equals(gae_his_disFlaglast)) {
                    rlistadd.setLastTimeColor("1");
                    rlistadd.setLastTime("有");
                } else {
                    rlistadd.setLastTimeColor("0");
                    rlistadd.setLastTime("无");
                }
                if ("--".equals(rlistadd.getLastTime())) {
                    rlistadd.setChanges("--");
                }
                if (rlistadd.getThisTime().equals(rlistadd.getLastTime())) {
                    rlistadd.setChanges("保持");
                }
                if ("无".equals(rlistadd.getLastTime()) && "有".equals(rlistadd.getThisTime())) {
                    rlistadd.setChanges("恶化");
                }
                if ("有".equals(rlistadd.getLastTime()) && "无".equals(rlistadd.getThisTime())) {
                    rlistadd.setChanges("好转");
                }
            } else {
                rlistadd.setChanges("-");
                rlistadd.setLastTime("--");
            }


            rlist.add(rlistadd);


            RiskFactorsVOJO rlistadd2 = new RiskFactorsVOJO();
            rlistadd2.setRiskFactors("海鲜摄入");
            rlistadd2.setIdealLevel("无");
            String seafood_cnt = JsonPath.read(document, "$.seafood_cnt") + "";

            if (!"1".equals(seafood_cnt)) {
                rlistadd2.setThisTimeColor("1");
                rlistadd2.setThisTime("有");
            } else {
                rlistadd2.setThisTimeColor("0");
                rlistadd2.setThisTime("无");
            }
            rlistadd2.setIdealLevel("无");

            if (null != lastdocument) {
                String seafood_cntlast = JsonPath.read(lastdocument, "$.seafood_cnt") + "";
                if (!"1".equals(seafood_cntlast)) {
                    rlistadd2.setLastTimeColor("1");
                    rlistadd2.setLastTime("有");
                } else {
                    rlistadd2.setLastTimeColor("0");
                    rlistadd2.setLastTime("无");
                }
                if ("--".equals(rlistadd2.getLastTime())) {
                    rlistadd2.setChanges("--");
                }
                if (rlistadd2.getThisTime().equals(rlistadd2.getLastTime())) {
                    rlistadd2.setChanges("保持");
                }
                if ("无".equals(rlistadd2.getLastTime()) && "有".equals(rlistadd2.getThisTime())) {
                    rlistadd2.setChanges("恶化");
                }
                if ("有".equals(rlistadd2.getLastTime()) && "无".equals(rlistadd2.getThisTime())) {
                    rlistadd2.setChanges("好转");
                }
            } else {
                rlistadd2.setChanges("-");
                rlistadd2.setLastTime("--");
            }


            rlist.add(rlistadd2);


        }

        //骨质疏松 7
        if (7 == diseasid) {
            RiskFactorsVOJO rlistadd = new RiskFactorsVOJO();
            rlistadd.setRiskFactors("类风湿性关节炎");
            rlistadd.setIdealLevel("无");
            //类风湿性关节炎
            String ra_his_disFlag = JsonPath.read(document, "$.ra_his.disFlag") + "";

            if ("1".equals(ra_his_disFlag)) {
                rlistadd.setThisTimeColor("1");
                rlistadd.setThisTime("有");
            } else {
                rlistadd.setThisTimeColor("0");
                rlistadd.setThisTime("无");
            }
            if (null != lastdocument) {
                //类风湿性关节炎
                String ra_his_disFlaglast = JsonPath.read(lastdocument, "$.ra_his.disFlag") + "";

                if ("1".equals(ra_his_disFlaglast)) {
                    rlistadd.setLastTimeColor("1");
                    rlistadd.setLastTime("有");
                } else {
                    rlistadd.setLastTimeColor("0");
                    rlistadd.setLastTime("无");
                }
                if ("--".equals(rlistadd.getLastTime())) {
                    rlistadd.setChanges("--");
                }
                if (rlistadd.getThisTime().equals(rlistadd.getLastTime())) {
                    rlistadd.setChanges("保持");
                }
                if ("无".equals(rlistadd.getLastTime()) && "有".equals(rlistadd.getThisTime())) {
                    rlistadd.setChanges("恶化");
                }
                if ("有".equals(rlistadd.getLastTime()) && "无".equals(rlistadd.getThisTime())) {
                    rlistadd.setChanges("好转");
                }
            } else {
                rlistadd.setChanges("-");
                rlistadd.setLastTime("--");
            }


            rlist.add(rlistadd);

            RiskFactorsVOJO rlistadd2 = new RiskFactorsVOJO();
            rlistadd2.setRiskFactors("继发性骨质疏松症");
            rlistadd2.setIdealLevel("无");
            //类风湿性关节炎
            String secondary_op_his_disFlag = JsonPath.read(document, "$.secondary_op_his.disFlag") + "";

            if ("1".equals(secondary_op_his_disFlag)) {
                rlistadd2.setThisTimeColor("1");
                rlistadd2.setThisTime("有");
            } else {
                rlistadd2.setThisTimeColor("0");
                rlistadd2.setThisTime("无");
            }
            if (null != lastdocument) {
                //类风湿性关节炎
                String secondary_op_his_disFlaglast = JsonPath.read(lastdocument, "$.secondary_op_his.disFlag") + "";

                if ("1".equals(secondary_op_his_disFlaglast)) {
                    rlistadd2.setLastTimeColor("1");
                    rlistadd2.setLastTime("有");
                } else {
                    rlistadd2.setLastTimeColor("0");
                    rlistadd2.setLastTime("无");
                }
                if ("--".equals(rlistadd2.getLastTime())) {
                    rlistadd2.setChanges("--");
                }
                if (rlistadd2.getThisTime().equals(rlistadd2.getLastTime())) {
                    rlistadd2.setChanges("保持");
                }
                if ("无".equals(rlistadd2.getLastTime()) && "有".equals(rlistadd2.getThisTime())) {
                    rlistadd2.setChanges("恶化");
                }
                if ("有".equals(rlistadd2.getLastTime()) && "无".equals(rlistadd2.getThisTime())) {
                    rlistadd2.setChanges("好转");
                }
            } else {
                rlistadd2.setChanges("-");
                rlistadd2.setLastTime("--");
            }


            rlist.add(rlistadd2);

            RiskFactorsVOJO rlistadd3 = new RiskFactorsVOJO();
            rlistadd3.setRiskFactors("注射肾上腺皮质激素");
            rlistadd3.setIdealLevel("无");
            //类风湿性关节炎
            String med_ach = JsonPath.read(document, "$.med_ach") + "";

            if ("1".equals(med_ach)) {
                rlistadd3.setThisTimeColor("1");
                rlistadd3.setThisTime("有");
            } else {
                rlistadd3.setThisTimeColor("0");
                rlistadd3.setThisTime("无");
            }
            if (null != lastdocument) {
                //类风湿性关节炎
                String med_achlast = JsonPath.read(lastdocument, "$.med_ach") + "";

                if ("1".equals(med_achlast)) {
                    rlistadd3.setLastTimeColor("1");
                    rlistadd3.setLastTime("有");
                } else {
                    rlistadd3.setLastTimeColor("0");
                    rlistadd3.setLastTime("无");
                }

                if ("--".equals(rlistadd3.getLastTime())) {
                    rlistadd3.setChanges("--");
                }
                if (rlistadd3.getThisTime().equals(rlistadd3.getLastTime())) {
                    rlistadd3.setChanges("保持");
                }
                if ("无".equals(rlistadd3.getLastTime()) && "有".equals(rlistadd3.getThisTime())) {
                    rlistadd3.setChanges("恶化");
                }
                if ("有".equals(rlistadd3.getLastTime()) && "无".equals(rlistadd3.getThisTime())) {
                    rlistadd3.setChanges("好转");
                }
            } else {
                rlistadd3.setChanges("-");
                rlistadd3.setLastTime("--");
            }


            rlist.add(rlistadd3);

            if ("2".equals(sex)) {
                //是否绝经
                RiskFactorsVOJO rlistadd4 = new RiskFactorsVOJO();
                rlistadd4.setRiskFactors("是否绝经");
                rlistadd4.setIdealLevel("绝经期迟（≥55岁）发生风险升高");
                //是否绝经
                String female_menopause = JsonPath.read(document, "$.female_menopause") + "";

                if ("1".equals(female_menopause)) {
                    rlistadd4.setThisTimeColor("1");
                    rlistadd4.setThisTime("是");
                } else {
                    rlistadd4.setThisTimeColor("0");
                    rlistadd4.setThisTime("否");
                }
                if (null != lastdocument) {
                    //类风湿性关节炎
                    String female_menopauselast = JsonPath.read(lastdocument, "$.female_menopause") + "";

                    if ("1".equals(female_menopauselast)) {
                        rlistadd4.setLastTimeColor("1");
                        rlistadd4.setLastTime("是");
                    } else {
                        rlistadd4.setLastTimeColor("0");
                        rlistadd4.setLastTime("否");
                    }

                    if ("--".equals(rlistadd4.getLastTime())) {
                        rlistadd4.setChanges("--");
                    }
                    if (rlistadd4.getThisTime().equals(rlistadd4.getLastTime())) {
                        rlistadd4.setChanges("保持");
                    }
                    if ("否".equals(rlistadd4.getLastTime()) && "是".equals(rlistadd4.getThisTime())) {
                        rlistadd4.setChanges("恶化");
                    }
                    if ("是".equals(rlistadd4.getLastTime()) && "否".equals(rlistadd4.getThisTime())) {
                        rlistadd4.setChanges("好转");
                    }
                } else {
                    rlistadd4.setChanges("-");
                    rlistadd4.setLastTime("--");
                }


                rlist.add(rlistadd4);
                //是否绝经
                if ("1".equals(female_menopause)) {

                    RiskFactorsVOJO rlistadd5 = new RiskFactorsVOJO();
                    rlistadd5.setRiskFactors("绝经年龄");
                    rlistadd5.setIdealLevel("<55岁");
                    //是否绝经
                    String female_menopause_age = JsonPath.read(document, "$.female_menopause_age") + "";

                    if ("1".equals(female_menopause_age)) {
                        rlistadd5.setThisTimeColor("0");
                        rlistadd5.setThisTime("<55岁");
                    } else {
                        rlistadd5.setThisTimeColor("1");
                        rlistadd5.setThisTime("≥55岁");
                    }
                    if (null != lastdocument) {
                        //类风湿性关节炎
                        String female_menopause_agelast = JsonPath.read(lastdocument, "$.female_menopause_age") + "";

                        if ("1".equals(female_menopause_agelast)) {
                            rlistadd5.setLastTimeColor("0");
                            rlistadd5.setLastTime("<55岁");
                        } else {
                            rlistadd5.setLastTimeColor("1");
                            rlistadd5.setLastTime("≥55岁");
                        }
                        if ("--".equals(rlistadd5.getLastTime())) {
                            rlistadd5.setChanges("--");
                        }
                        if (rlistadd5.getLastTimeColor().equals(rlistadd5.getThisTimeColor())) {
                            rlistadd5.setChanges("保持");
                        }
                        if (Double.valueOf(rlistadd5.getLastTimeColor()) < Double.valueOf(rlistadd5.getThisTimeColor())) {
                            rlistadd5.setChanges("好转");
                        }
                        if (Double.valueOf(rlistadd5.getLastTimeColor()) > Double.valueOf(rlistadd5.getThisTimeColor())) {
                            rlistadd5.setChanges("恶化");
                        }
                    } else {
                        rlistadd5.setChanges("-");
                        rlistadd5.setLastTime("--");
                    }


                    rlist.add(rlistadd5);

                }


            }


        }

        //肺癌
        if (8 == diseasid) {
            //lc_his.disFlag
            RiskFactorsVOJO rlistadd2 = new RiskFactorsVOJO();
            rlistadd2.setRiskFactors("个人患病史");
            rlistadd2.setIdealLevel("无:肺癌/肺结核史");
            //肺癌
            String lc_his_disFlag = JsonPath.read(document, "$.lc_his.disFlag") + "";
            //肺结核
            String decline_his_disFlag = JsonPath.read(document, "$.decline_his.disFlag") + "";
            if ("1".equals(lc_his_disFlag) || "1".equals(decline_his_disFlag)) {
                rlistadd2.setThisTimeColor("1");
                rlistadd2.setThisTime("有");
            } else {
                rlistadd2.setThisTimeColor("0");
                rlistadd2.setThisTime("无");
            }
            if (null != lastdocument) {
                //肺癌
                String lc_his_disFlaglast = JsonPath.read(lastdocument, "$.lc_his.disFlag") + "";
                //肺结核
                String decline_his_disFlaglast = JsonPath.read(lastdocument, "$.decline_his.disFlag") + "";

                if ("1".equals(lc_his_disFlaglast) || "1".equals(decline_his_disFlaglast)) {
                    rlistadd2.setLastTimeColor("1");
                    rlistadd2.setLastTime("有");
                } else {
                    rlistadd2.setLastTimeColor("0");
                    rlistadd2.setLastTime("无");
                }
                if ("--".equals(rlistadd2.getLastTime())) {
                    rlistadd2.setChanges("--");
                }
                if (rlistadd2.getThisTime().equals(rlistadd2.getLastTime())) {
                    rlistadd2.setChanges("保持");
                }
                if ("无".equals(rlistadd2.getLastTime()) && "有".equals(rlistadd2.getThisTime())) {
                    rlistadd2.setChanges("恶化");
                }
                if ("有".equals(rlistadd2.getLastTime()) && "无".equals(rlistadd2.getThisTime())) {
                    rlistadd2.setChanges("好转");
                }
            } else {
                rlistadd2.setChanges("-");
                rlistadd2.setLastTime("--");
            }


            rlist.add(rlistadd2);


            RiskFactorsVOJO rlistadd3 = new RiskFactorsVOJO();
            rlistadd3.setRiskFactors("空气质量");
            rlistadd3.setIdealLevel("优良");
            //肺癌
            String pm25 = JsonPath.read(document, "$.pm25") + "";

            if ("1".equals(pm25)) {
                rlistadd3.setThisTimeColor("0");
                rlistadd3.setThisTime("优良");
            } else {
                rlistadd3.setThisTimeColor("1");
                rlistadd3.setThisTime("差");
            }
            if (null != lastdocument) {
                //肺癌
                String pm25last = JsonPath.read(lastdocument, "$.pm25") + "";

                if ("1".equals(pm25last)) {
                    rlistadd3.setLastTimeColor("0");
                    rlistadd3.setLastTime("优良");
                } else {
                    rlistadd3.setLastTimeColor("1");
                    rlistadd3.setLastTime("差");
                }
                if ("--".equals(rlistadd3.getLastTime())) {
                    rlistadd3.setChanges("--");
                }
                if (rlistadd3.getThisTime().equals(rlistadd3.getLastTime())) {
                    rlistadd3.setChanges("保持");
                }
                if ("优良".equals(rlistadd3.getLastTime()) && "差".equals(rlistadd3.getThisTime())) {
                    rlistadd3.setChanges("恶化");
                }
                if ("差".equals(rlistadd3.getLastTime()) && "优良".equals(rlistadd3.getThisTime())) {
                    rlistadd3.setChanges("好转");
                }
            } else {
                rlistadd3.setChanges("-");
                rlistadd3.setLastTime("--");
            }


            rlist.add(rlistadd3);


            RiskFactorsVOJO rlistadd4 = new RiskFactorsVOJO();
            rlistadd4.setRiskFactors("有害物质职业接触");
            rlistadd4.setIdealLevel("无粉尘接触史、油烟接触史、石棉接触史");
            //粉尘接触史
            String dust_exposure_his_disFlag = JsonPath.read(document, "$.dust_exposure_his.disFlag") + "";
            //油烟接触史
            String lampblack_contact_disFlag = JsonPath.read(document, "$.lampblack_contact.disFlag") + "";
            //石棉接触史
            String asbestos_contact_his_disFlag = JsonPath.read(document, "$.asbestos_contact_his.disFlag") + "";


            if ("1".equals(dust_exposure_his_disFlag) || "1".equals(lampblack_contact_disFlag) || "1".equals(asbestos_contact_his_disFlag)) {
                rlistadd4.setThisTimeColor("1");
                rlistadd4.setThisTime("有");
            } else {
                rlistadd4.setThisTimeColor("0");
                rlistadd4.setThisTime("无");
            }
            if (null != lastdocument) {
                //粉尘接触史
                String dust_exposure_his_disFlaglast = JsonPath.read(lastdocument, "$.dust_exposure_his.disFlag") + "";
                //油烟接触史
                String lampblack_contact_disFlaglast = JsonPath.read(lastdocument, "$.lampblack_contact.disFlag") + "";
                //石棉接触史
                String asbestos_contact_his_disFlaglastW = JsonPath.read(lastdocument, "$.asbestos_contact_his.disFlag") + "";

                if ("1".equals(dust_exposure_his_disFlaglast) || "1".equals(lampblack_contact_disFlaglast) || "1".equals(asbestos_contact_his_disFlaglastW)) {
                    rlistadd4.setLastTimeColor("1");
                    rlistadd4.setLastTime("有");
                } else {
                    rlistadd4.setLastTimeColor("0");
                    rlistadd4.setLastTime("无");
                }
                if ("--".equals(rlistadd4.getLastTime())) {
                    rlistadd4.setChanges("--");
                }
                if (rlistadd4.getThisTime().equals(rlistadd4.getLastTime())) {
                    rlistadd4.setChanges("保持");
                }
                if ("无".equals(rlistadd4.getLastTime()) && "有".equals(rlistadd4.getThisTime())) {
                    rlistadd4.setChanges("恶化");
                }
                if ("有".equals(rlistadd4.getLastTime()) && "无".equals(rlistadd4.getThisTime())) {
                    rlistadd4.setChanges("好转");
                }
            } else {
                rlistadd4.setChanges("-");
                rlistadd4.setLastTime("--");
            }


            rlist.add(rlistadd4);


        }

        //前列腺癌
        if (9 == diseasid) {
            RiskFactorsVOJO rlistadd4 = new RiskFactorsVOJO();
            rlistadd4.setRiskFactors("前列腺疾病史");
            rlistadd4.setIdealLevel("无尿路感染、尿频、尿痛、尿不尽、排尿困难等症状、尿血或尿路结石");
            //尿路感染
            String urinary_tract_infection = JsonPath.read(document, "$.urinary_tract_infection") + "";
            //尿频、尿痛、尿不尽、排尿困难等症状
            String urine_symptom = JsonPath.read(document, "$.urine_symptom") + "";
            //尿血或尿路结石
            String hematuresis = JsonPath.read(document, "$.hematuresis") + "";


            if ("1".equals(urinary_tract_infection) || "1".equals(urine_symptom) || "1".equals(hematuresis)) {
                rlistadd4.setThisTimeColor("1");
                rlistadd4.setThisTime("有");
            } else {
                rlistadd4.setThisTimeColor("0");
                rlistadd4.setThisTime("无");
            }
            if (null != lastdocument) {
                //粉尘接触史
                String urinary_tract_infectionlast = JsonPath.read(lastdocument, "$.urinary_tract_infection") + "";
                //油烟接触史
                String urine_symptomlast = JsonPath.read(lastdocument, "$.urine_symptom") + "";
                //石棉接触史
                String hematuresislast = JsonPath.read(lastdocument, "$.hematuresis") + "";

                if ("1".equals(urinary_tract_infectionlast) || "1".equals(urine_symptomlast) || "1".equals(hematuresislast)) {
                    rlistadd4.setLastTimeColor("1");
                    rlistadd4.setLastTime("有");
                } else {
                    rlistadd4.setLastTimeColor("0");
                    rlistadd4.setLastTime("无");
                }
                if ("--".equals(rlistadd4.getLastTime())) {
                    rlistadd4.setChanges("--");
                }
                if (rlistadd4.getThisTime().equals(rlistadd4.getLastTime())) {
                    rlistadd4.setChanges("保持");
                }
                if ("无".equals(rlistadd4.getLastTime()) && "有".equals(rlistadd4.getThisTime())) {
                    rlistadd4.setChanges("恶化");
                }
                if ("有".equals(rlistadd4.getLastTime()) && "无".equals(rlistadd4.getThisTime())) {
                    rlistadd4.setChanges("好转");
                }
            } else {
                rlistadd4.setChanges("-");
                rlistadd4.setLastTime("--");
            }


            rlist.add(rlistadd4);

            RiskFactorsVOJO rlistadd5 = new RiskFactorsVOJO();
            rlistadd5.setRiskFactors("有害物质直接接触");
            rlistadd5.setIdealLevel("无长期从事化工、染料、橡胶及印刷等职业");
            //尿路感染
            String chemical_occupation = JsonPath.read(document, "$.chemical_occupation") + "";


            if ("1".equals(chemical_occupation)) {
                rlistadd5.setThisTimeColor("1");
                rlistadd5.setThisTime("有");
            } else {
                rlistadd5.setThisTimeColor("0");
                rlistadd5.setThisTime("无");
            }
            if (null != lastdocument) {
                //粉尘接触史
                String chemical_occupationlast = JsonPath.read(lastdocument, "$.chemical_occupation") + "";


                if ("1".equals(chemical_occupationlast)) {
                    rlistadd5.setLastTimeColor("1");
                    rlistadd5.setLastTime("有");
                } else {
                    rlistadd5.setLastTimeColor("0");
                    rlistadd5.setLastTime("无");
                }
                if ("--".equals(rlistadd5.getLastTime())) {
                    rlistadd5.setChanges("--");
                }
                if (rlistadd5.getThisTime().equals(rlistadd5.getLastTime())) {
                    rlistadd5.setChanges("保持");
                }
                if ("无".equals(rlistadd5.getLastTime()) && "有".equals(rlistadd5.getThisTime())) {
                    rlistadd5.setChanges("恶化");
                }
                if ("有".equals(rlistadd5.getLastTime()) && "无".equals(rlistadd5.getThisTime())) {
                    rlistadd5.setChanges("好转");
                }
            } else {
                rlistadd5.setChanges("-");
                rlistadd5.setLastTime("--");
            }


            rlist.add(rlistadd5);

        }
        //乳腺癌
        if (10 == diseasid) {


            //是否绝经
            RiskFactorsVOJO rlistadd4 = new RiskFactorsVOJO();
            rlistadd4.setRiskFactors("是否绝经");
            rlistadd4.setIdealLevel("随着绝经的发生风险增高");
            //是否绝经
            String female_menopause = JsonPath.read(document, "$.female_menopause") + "";

            if ("1".equals(female_menopause)) {
                rlistadd4.setThisTimeColor("1");
                rlistadd4.setThisTime("是");
            } else {
                rlistadd4.setThisTimeColor("0");
                rlistadd4.setThisTime("否");
            }
            if (null != lastdocument) {
                //类风湿性关节炎
                String female_menopauselast = JsonPath.read(lastdocument, "$.female_menopause") + "";

                if ("1".equals(female_menopauselast)) {
                    rlistadd4.setLastTimeColor("1");
                    rlistadd4.setLastTime("是");
                } else {
                    rlistadd4.setLastTimeColor("0");
                    rlistadd4.setLastTime("否");
                }
                if ("--".equals(rlistadd4.getLastTime())) {
                    rlistadd4.setChanges("--");
                }
                if (rlistadd4.getThisTime().equals(rlistadd4.getLastTime())) {
                    rlistadd4.setChanges("保持");
                }
                if ("否".equals(rlistadd4.getLastTime()) && "是".equals(rlistadd4.getThisTime())) {
                    rlistadd4.setChanges("恶化");
                }
                if ("是".equals(rlistadd4.getLastTime()) && "否".equals(rlistadd4.getThisTime())) {
                    rlistadd4.setChanges("好转");
                }
            } else {
                rlistadd4.setChanges("-");
                rlistadd4.setLastTime("--");
            }


            rlist.add(rlistadd4);


            //本次是否生育
            String female_birth = JsonPath.read(document, "$.female_birth") + "";


            //生育过才显示
            if ("1".equals(female_birth) || "2".equals(female_birth)) {

                RiskFactorsVOJO rlistadd71 = new RiskFactorsVOJO();
                rlistadd71.setRiskFactors("是否生育");
                rlistadd71.setIdealLevel("已生育发生风险降低");
                rlistadd71.setThisTime("已生育");

                //初产年龄
                RiskFactorsVOJO rlistadd7 = new RiskFactorsVOJO();
                rlistadd7.setRiskFactors("初产年龄");
                rlistadd7.setIdealLevel("初产年龄大(＜30岁)发生风险降低");

                //初产年龄
                String female_tire = JsonPath.read(document, "$.female_tire") + "";

                if ("1".equals(female_tire)) {
                    rlistadd7.setThisTime("<30岁");
                } else if ("2".equals(female_tire)) {
                    rlistadd7.setThisTimeColor("1");
                    rlistadd7.setThisTime("≥30岁");
                }


                if (null != lastdocument) {
                    //上次是否生育
                    String female_birthlast = JsonPath.read(lastdocument, "$.female_birth") + "";
                    if ("1".equals(female_birthlast) && "2".equals(female_birthlast)) {
                        rlistadd71.setLastTime("已生育");
                        //初产年龄
                        String female_tirelast = JsonPath.read(lastdocument, "$.female_tire") + "";

                        if ("1".equals(female_tirelast)) {
                            rlistadd7.setLastTime("<30岁");
                        } else if ("2".equals(female_tirelast)) {
                            rlistadd7.setLastTimeColor("1");
                            rlistadd7.setLastTime("≥30岁");
                        }

                    } else {
                        rlistadd71.setLastTime("未生育");
                    }


                } else {
                    rlistadd7.setLastTime("--");
                    rlistadd71.setLastTime("--");
                }


                rlistadd7.setChanges("-");
                rlist.add(rlistadd7);
                rlistadd71.setChanges("-");
                rlist.add(rlistadd71);


                //哺乳喂养时间
                RiskFactorsVOJO rlistadd8 = new RiskFactorsVOJO();
                rlistadd8.setRiskFactors("哺乳喂养时间");
                rlistadd8.setIdealLevel("哺乳时间过长(≥12月)发生风险升高");


                //哺乳喂养时间
                String female_breast_time = JsonPath.read(document, "$.female_breast_time") + "";
                //是否母乳喂养
                String female_breast = JsonPath.read(document, "$.female_breast") + "";
                if ("1".equals(female_breast_time)) {
                    rlistadd8.setThisTime("<12个月");
                    rlistadd8.setThisTimeColor("1");
                } else if ("2".equals(female_breast_time)) {
                    rlistadd8.setThisTime("≥12个月");
                } else if ("2".equals(female_breast)) {
                    rlistadd8.setThisTimeColor("1");
                    rlistadd8.setThisTime("不是母乳喂养");
                }


                if (null != lastdocument) {
                    //哺乳喂养时间
                    String female_breast_timelast = JsonPath.read(lastdocument, "$.female_breast_time") + "";
                    //是否母乳喂养
                    String female_breastlast = JsonPath.read(document, "$.female_breast") + "";

                    if ("1".equals(female_breast_timelast)) {
                        rlistadd8.setLastTime("<12个月");
                        rlistadd8.setLastTimeColor("1");
                    } else if ("2".equals(female_breast_timelast)) {
                        rlistadd8.setLastTime("≥12个月");
                    } else if ("2".equals(female_breastlast)) {
                        rlistadd8.setLastTimeColor("1");
                        rlistadd8.setLastTime("不是母乳喂养");
                    }
                } else {
                    rlistadd8.setLastTime("--");
                }


                rlistadd8.setChanges("-");
                rlist.add(rlistadd8);


            } else {
                RiskFactorsVOJO rlistadd71 = new RiskFactorsVOJO();
                rlistadd71.setRiskFactors("是否生育");
                rlistadd71.setIdealLevel("已生育发生风险降低");
                rlistadd71.setThisTime("未生育");
                rlistadd71.setThisTimeColor("1");
                rlistadd71.setChanges("-");


                if (null != lastdocument) {
                    //上次是否生育
                    String female_birthlast = JsonPath.read(lastdocument, "$.female_birth") + "";
                    if ("1".equals(female_birthlast) && "2".equals(female_birthlast)) {
                        rlistadd71.setLastTime("已生育");
                    } else {
                        rlistadd71.setLastTime("未生育");
                        rlistadd71.setLastTimeColor("1");
                    }

                } else {
                    rlistadd71.setLastTime("--");
                }
                rlist.add(rlistadd71);
            }
            //相关用药或手术

            RiskFactorsVOJO rlistadd9 = new RiskFactorsVOJO();
            rlistadd9.setRiskFactors("相关用药或手术");
            rlistadd9.setIdealLevel("无长期口服避孕药、长期使用丰乳产品或做过乳房整形手术、停经后采用雌激素替代疗法");
            //长期使用丰乳产品或做过乳房整形手术
            String breast_plastic_surgery = JsonPath.read(document, "$.breast_plastic_surgery") + "";
            //长期口服避孕药
            String med_use_pill = JsonPath.read(document, "$.med_use_pill") + "";
            //停经后采用雌激素替代疗法 1：<5年 2>=5年
            String med_ert = JsonPath.read(document, "$.med_ert") + "";


            if ("1".equals(breast_plastic_surgery) || "1".equals(med_use_pill) || "1".equals(med_ert) || "2".equals(med_ert)) {
                rlistadd9.setThisTimeColor("1");
                rlistadd9.setThisTime("有");
            } else {
                rlistadd9.setThisTimeColor("0");
                rlistadd9.setThisTime("无");
            }
            if (null != lastdocument) {
                //长期使用丰乳产品或做过乳房整形手术
                String breast_plastic_surgerylast = JsonPath.read(lastdocument, "$.breast_plastic_surgery") + "";
                //长期口服避孕药
                String med_use_pilllast = JsonPath.read(lastdocument, "$.med_use_pill") + "";
                //停经后采用雌激素替代疗法
                String med_ertlast = JsonPath.read(lastdocument, "$.med_ert") + "";

                if ("1".equals(breast_plastic_surgerylast) || "1".equals(med_use_pilllast) || "1".equals(med_ertlast) || "2".equals(med_ertlast)) {
                    rlistadd9.setLastTimeColor("1");
                    rlistadd9.setLastTime("有");
                } else {
                    rlistadd9.setLastTimeColor("0");
                    rlistadd9.setLastTime("无");
                }
                if ("--".equals(rlistadd9.getLastTime())) {
                    rlistadd9.setChanges("--");
                }
                if (rlistadd9.getThisTime().equals(rlistadd9.getLastTime())) {
                    rlistadd9.setChanges("保持");
                }
                if ("无".equals(rlistadd9.getLastTime()) && "有".equals(rlistadd9.getThisTime())) {
                    rlistadd9.setChanges("恶化");
                }
                if ("有".equals(rlistadd9.getLastTime()) && "无".equals(rlistadd9.getThisTime())) {
                    rlistadd9.setChanges("好转");
                }
            } else {
                rlistadd9.setChanges("-");
                rlistadd9.setLastTime("--");
            }


            rlist.add(rlistadd9);

        }

        return rlist;
    }

    public String exceedingTheStandard(String xpath, String value, String sex, String diseaseid) {
        if (StringUtils.isEmpty(value) || "-".equals(value)) {
            return "";
        }
        // 当前分数
        Integer badge = 0;
        // 理想分数
        Integer Segment = 50;
        String nowValue = value;
        YHDiseaseRiskOptimal yhDiseaseRiskOptimal = yhDiseaseAssessReportMapper
                .findYhDiseaseRiskOptimalByXpath(diseaseid, 3, sex, xpath);
        String qtype = yhDiseaseRiskOptimal.getQtype();
        String Remark = yhDiseaseRiskOptimal.getRemark();
        String qvalue = yhDiseaseRiskOptimal.getValue();
        if ("T".equals(qtype)) {
            Segment = 50;
            boolean contains = Remark.contains("/");
            if (contains) {
                if (StringUtils.isNotEmpty(nowValue)) {
                    if (qvalue.equals(nowValue)) {
                        badge = 50;
                    } else {
                        badge = 100;
                    }
                } else {
                    badge = 50;
                }
            } else {

                if (StringUtils.isNotEmpty(nowValue)) {
                    if (qvalue.equals(nowValue)) {
                        badge = 50;
                    } else {
                        badge = 100;
                    }
                } else {
                    badge = 50;
                }
            }

        } else {
            Double reqvalue = 0.0;
            if (StringUtil.isNotEmpty(nowValue)) {
                reqvalue = Double.valueOf(nowValue);
            }
            Integer rangePoint = Integer.parseInt(yhDiseaseRiskOptimal.getRadarRangePoint());
            int s = 100 / rangePoint;
            Segment = s;
            // 获取区间值
            Double minValue = yhDiseaseRiskOptimal.getMinv();
            Double maxvalue = yhDiseaseRiskOptimal.getMaxv();
            if (rangePoint > 2) {
                if (minValue <= reqvalue && reqvalue < maxvalue) {
                    badge = s;
                } else {
                    badge = 100;
                }

            } else {
                if (reqvalue <= maxvalue) {
                    badge = 50;
                } else {
                    badge = 100;
                }
            }
        }

        return badge == Segment ? "0" : "1";
    }

    /**
     * @param @param yhDiseaseRiskOptimal
     * @param @param document
     * @param @param yhDiseaseRiskFactors 设定文件
     * @return void 返回类型
     * @throws @Title: doDiseaseRiskResult2
     * @Description: 根据每个疾病的雷达图相关危险因素划分区间值，并计算理想与当前风险在区间的位置
     */
    public void doDiseaseRiskResult2(YHDiseaseRiskOptimal yhDiseaseRiskOptimal,
                                     YhDiseaseRiskResult2 yhDiseaseRiskResult, YhDiseaseRiskFactorGroups yhDiseaseRiskFactors) {
        //当前分数
        Integer badge = 0;
        //理想分数
        Integer Segment = 50;
        String nowValue = null;
        String qcode = toCamelCase(yhDiseaseRiskOptimal.getXpath());
        String qtype = yhDiseaseRiskOptimal.getQtype();
        String Remark = yhDiseaseRiskOptimal.getRemark();
        String qvalue = yhDiseaseRiskOptimal.getValue();
        Collection<String> yhDiseaseRiskResultReflect;
        try {
            yhDiseaseRiskResultReflect = ReflectionUtil.getReflect(yhDiseaseRiskResult);
            for (String string : yhDiseaseRiskResultReflect) {
                if (string.equals(qcode)) {
                    Object document = Configuration.defaultConfiguration().jsonProvider().parse(ReflectionUtil.getFieldValue(yhDiseaseRiskResult, string).toString());
                    nowValue = JsonPath.read(document, "$.now_value").toString();
                    //判断是否是数值
                    if ("T".equals(qtype)) {
                        Segment = 50;
                        boolean contains = Remark.contains("/");
                        if (contains) {
                            if (StringUtils.isNotEmpty(nowValue)) {
                                if (qvalue.equals(nowValue)) {
                                    badge = 50;
                                    yhDiseaseRiskFactors.setDiseaseRiskFactorColor(Remark.split("/")[0]);
                                } else {
                                    badge = 100;
                                    yhDiseaseRiskFactors.setDiseaseRiskFactorColor(Remark.split("/")[1]);
                                }
                            } else {
                                badge = 50;
                                yhDiseaseRiskFactors.setDiseaseRiskFactorColor(Remark.split("/")[0]);
                            }
                        } else {

                            if (StringUtils.isNotEmpty(nowValue)) {
                                if (qvalue.equals(nowValue)) {
                                    badge = 50;
                                } else {
                                    badge = 100;
                                }
                            } else {
                                badge = 50;
                            }
                        }


                    } else {
                        Double reqvalue = 0.0;
                        if (StringUtil.isNotEmpty(nowValue)) {
                            reqvalue = Double.valueOf(nowValue);
                        }
                        Integer rangePoint = Integer.parseInt(yhDiseaseRiskOptimal.getRadarRangePoint());
                        int s = 100 / rangePoint;
                        Segment = s;
                        // 获取区间值
                        Double minValue = yhDiseaseRiskOptimal.getMinv();
                        Double maxvalue = yhDiseaseRiskOptimal.getMaxv();
                        if (rangePoint > 2) {
                            if (minValue <= reqvalue && reqvalue < maxvalue) {
                                badge = s;
                            } else {
                                badge = 100;
                            }

                        } else {
                            if (reqvalue <= maxvalue) {
                                badge = 50;
                            } else {
                                badge = 100;
                            }
                        }
                        yhDiseaseRiskFactors.setDiseaseRiskFactorColor(nowValue);
                    }
                    yhDiseaseRiskFactors.setDiseaseRiskFactorBadge(badge);
                    yhDiseaseRiskFactors.setDiseaseRiskFactorSegment(Segment);
                    yhDiseaseRiskFactors.setName(yhDiseaseRiskOptimal.getOptimal());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getValue(YhDiseaseRiskResultAppVOJO findDiseaseRiskResultByRsForRs) throws InvocationTargetException {
        Field[] field = findDiseaseRiskResultByRsForRs.getClass().getDeclaredFields();
        for (int j = 0; j < field.length; j++) {
            String name = field[j].getName();

            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            String type = field[j].getGenericType().toString();
            if (type.equals("class java.lang.String")) {
                Method m;
                String value;
                try {
                    m = findDiseaseRiskResultByRsForRs.getClass().getMethod("get" + name);
                    value = (String) m.invoke(findDiseaseRiskResultByRsForRs);
                    if (value != null && !"".equals(value)) {
                        System.out.println(name);
                        System.out.println(value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public String findQuestionCheckForCheckAnswerByrs(String rs) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.findQuestionCheckForCheckAnswerByrs(rs);
    }

    @Override
    public YhDiseaseRiskResultAppVOJO findAssessResultInfo(String rs) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.findAssessResultInfo(rs);
    }

    @Override
    public YhDiseaseRiskResultAppVOJO findDiseaseRiskResultByRsFor3(String rs) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsFor3(rs);
    }

    /**
     * @Title: doDiseaseRiskResult @Description:
     * 计算在雷达图位置值并回显危险因素的区间值 @param @param radarRangePoint @param @param
     * radarRangeName @param @param value @param @return 设定文件 @return
     * String 返回类型 @throws
     */
    private void doDiseaseRiskResult(String radarRangePoint, String radarRangeName, Object document,
                                     YhDiseaseRiskFactorGroups yhDiseaseRiskFactors) {
        List<QuestionNames> questionNamesList = new ArrayList<QuestionNames>();
        QuestionNames questionName = null;
        String color = null;
        Integer badge = null;
        if (StringUtils.isEmpty(radarRangeName)) {
            return;
        }
        String[] splitRangeName = radarRangeName.split(":");
        int parseInt = Integer.parseInt(radarRangePoint);
        Object read = null;
        if (splitRangeName.length > 0) {
            String qCode = splitRangeName[0];
            String qSplit = splitRangeName[1];
            // jsonpath解析获取字段的值
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append("$.").append(qCode);
            // 特殊bmi获取值
            if (!"BMI".equals(qCode)) {
                read = JsonPath.read(document, sBuffer.toString());
            } else {
                String height = JsonPath.read(document, "$.height") + "";
                String weight = JsonPath.read(document, "$.weight") + "";
                if (!"".equals(height) && !"".equals(weight)) {
                    // 计算bmi值并保存两位小数
                    Double bf = (Double.parseDouble(weight)
                            / ((Double.parseDouble(height) / 100) * (Double.parseDouble(height)) / 100));
                    BigDecimal b = new BigDecimal(bf);
                    read = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
            yhDiseaseRiskFactors.setDiseaseRiskFactorGroup(qCode);
            // 获取区间值
            String[] splitcode = qSplit.split(",");
            boolean flag = true;
            for (int i = 0; i < splitcode.length; i++) {
                String index = String.valueOf(i + 1);
                questionName = new QuestionNames();
                String splitnumf = splitcode[i];
                questionName.setAnswerid(index);
                questionName.setAnswersName(splitnumf);
                if (CommonUtils.isChinese(splitnumf)) {
                    if ("tg".equals(qCode)) {
                        // 获取评估的值
                        Double checkValue = Double.valueOf(read + "");
                        if (flag) {
                            if (0 < checkValue && 1.7 > checkValue) {
                                badge = 20;
                                color = "mauve";
                                questionName.setSelect(true);
                                flag = false;
                            } else {
                                badge = 100;
                                color = "red";
                                questionName.setSelect(true);
                                flag = false;
                            }
                        } else {
                            flag = true;
                            questionName.setSelect(false);
                        }
                    } else {
                        if (flag) {
                            if (index.equals(read)) {
                                questionName.setSelect(true);
                                badge = 100;
                                color = "red";
                                flag = false;
                            } else {
                                questionName.setSelect(true);
                                badge = 20;
                                color = "mauve";
                                flag = false;
                            }
                        } else {
                            questionName.setSelect(false);
                        }
                    }
                    questionNamesList.add(questionName);
                } else {
                    int s = 100 / parseInt;
                    if (splitnumf.contains("~")) {
                        // 获取评估的值
                        Double checkValue = Double.valueOf(read + "");
                        String[] nums = splitnumf.split("~");
                        // 判断是否是最后一位
                        if (nums.length > 1) {
                            Double minNum = Double.parseDouble(nums[0]);
                            Double maxNum = Double.parseDouble(nums[1]);
                            if (minNum <= checkValue && checkValue <= maxNum) {
                                badge = s * (i + 1);
                                if (0 < badge && badge < 33) {
                                    color = "mauve";
                                } else if (33 < badge && badge < 66) {
                                    color = "yellow";
                                } else {
                                    color = "red";
                                }
                                questionName.setSelect(true);
                            } else {
                                questionName.setSelect(false);
                            }

                        } else {
                            Double minNum = Double.parseDouble(nums[0]);
                            if (checkValue > minNum) {
                                badge = 100;
                                color = "red";
                                questionName.setSelect(true);
                            } else {
                                questionName.setSelect(false);
                            }
                        }
                        questionNamesList.add(questionName);
                    }
                }
            }
        }

        yhDiseaseRiskFactors.setDiseaseRiskFactorColor(color);
        yhDiseaseRiskFactors.setDiseaseRiskFactorBadge(null == badge ? 0 : badge);
        // 定义一个数组出入集合数据
        QuestionNames[] QuestionNa = null;
        if (questionNamesList.size() > 0) {
            int n = questionNamesList.size();
            QuestionNa = new QuestionNames[n];
            for (int j = 0; j < n; j++) {
                QuestionNa[j] = questionNamesList.get(j);
            }
        }
        yhDiseaseRiskFactors.setQuestionNames(QuestionNa);
    }

    @Override
    public List<YHDiseaseAssessResolve> findAssessResolveInfoByrs(String rs, Integer did) {
        // TODO Auto-generated method stub
        return yhDiseaseAssessReportMapper.findAssessResolveInfoByrs(rs, did);
    }

    @Override
    public String findDiseaseNameInfo(String code) {
        // TODO 自动生成的方法存根
        return yhDiseaseAssessReportMapper.findDiseaseNameInfo(code);
    }

    public String familyfInfo(Object document) {
        String fInfo = "";
        String family_diab_f = JsonPath.read(document, "$.family_diab_f") + "";
        if (family_diab_f.equals("1")) {
            fInfo = fInfo + "糖尿病;";
        }
        String family_hbp_f = JsonPath.read(document, "$.family_hbp_f") + "";
        if (family_hbp_f.equals("1")) {
            fInfo = fInfo + "高血压";
        }
        String family_chd_f = JsonPath.read(document, "$.family_chd_f") + "";
        if (family_chd_f.equals("1")) {
            fInfo = fInfo + "冠心病";
        }
        String family_stroke_f = JsonPath.read(document, "$.family_stroke_f") + "";
        if (family_stroke_f.equals("1")) {
            fInfo = fInfo + "脑卒中";
        }
        String family_hf_f = JsonPath.read(document, "$.family_hf_f") + "";
        if (family_hf_f.equals("1")) {
            fInfo = fInfo + "脑卒中";
        }
        String family_lung_f = JsonPath.read(document, "$.family_lung_f") + "";
        if (family_lung_f.equals("1")) {
            fInfo = fInfo + "肺癌";
        }
        String family_pro_f = JsonPath.read(document, "$.family_pro_f") + "";
        if (family_pro_f.equals("1")) {
            fInfo = fInfo + "前列腺癌";
        }
        String family_gout_f = JsonPath.read(document, "$.family_gout_f") + "";
        if (family_gout_f.equals("1")) {
            fInfo = fInfo + "痛风";
        }

        return fInfo;
    }

    public String familymInfo(Object document) {
        String fInfo = "";
        String family_diab_f = JsonPath.read(document, "$.family_diab_m") + "";
        if (family_diab_f.equals("1")) {
            fInfo = fInfo + "糖尿病;";
        }
        String family_hbp_f = JsonPath.read(document, "$.family_hbp_m") + "";
        if (family_hbp_f.equals("1")) {
            fInfo = fInfo + "高血压";
        }
        String family_chd_f = JsonPath.read(document, "$.family_chd_m") + "";
        if (family_chd_f.equals("1")) {
            fInfo = fInfo + "冠心病";
        }
        String family_stroke_f = JsonPath.read(document, "$.family_stroke_m") + "";
        if (family_stroke_f.equals("1")) {
            fInfo = fInfo + "脑卒中";
        }
        String family_hf_f = JsonPath.read(document, "$.family_hf_m") + "";
        if (family_hf_f.equals("1")) {
            fInfo = fInfo + "髋部骨折";
        }
        String family_lung_f = JsonPath.read(document, "$.family_lung_m") + "";
        if (family_lung_f.equals("1")) {
            fInfo = fInfo + "肺癌";
        }
        String family_pro_f = JsonPath.read(document, "$.family_breast_m") + "";
        if (family_pro_f.equals("1")) {
            fInfo = fInfo + "乳腺癌";
        }
        String family_gout_f = JsonPath.read(document, "$.family_gout_m") + "";
        if (family_gout_f.equals("1")) {
            fInfo = fInfo + "痛风";
        }

        return fInfo;
    }

    public String familybsInfo(Object document) {
        String fInfo = "";
        String family_diab_f = JsonPath.read(document, "$.family_diab_bs") + "";
        if (family_diab_f.equals("1")) {
            fInfo = fInfo + "糖尿病;";
        }
        String family_hbp_f = JsonPath.read(document, "$.family_hbp_bs") + "";
        if (family_hbp_f.equals("1")) {
            fInfo = fInfo + "高血压";
        }
        String family_chd_f = JsonPath.read(document, "$.family_chd_bs") + "";
        if (family_chd_f.equals("1")) {
            fInfo = fInfo + "冠心病";
        }
        String family_stroke_f = JsonPath.read(document, "$.family_stroke_bs") + "";
        if (family_stroke_f.equals("1")) {
            fInfo = fInfo + "脑卒中";
        }
        String family_hf_f = JsonPath.read(document, "$.family_hf_bs") + "";
        if (family_hf_f.equals("1")) {
            fInfo = fInfo + "髋部骨折";
        }
        String family_lung_f = JsonPath.read(document, "$.family_lung_bs") + "";
        if (family_lung_f.equals("1")) {
            fInfo = fInfo + "肺癌";
        }
        String family_pro_s = JsonPath.read(document, "$.family_pro_b") + "";
        if (family_pro_s.equals("1")) {
            fInfo = fInfo + "前列腺癌";
        }
        String family_pro_f = JsonPath.read(document, "$.family_breast_s") + "";
        if (family_pro_f.equals("1")) {
            fInfo = fInfo + "乳腺癌";
        }
        String family_gout_f = JsonPath.read(document, "$.family_gout_bs") + "";
        if (family_gout_f.equals("1")) {
            fInfo = fInfo + "痛风";
        }

        return fInfo;
    }

    public String familychInfo(Object document) {
        String fInfo = "";
        String family_diab_f = JsonPath.read(document, "$.family_diab_ch") + "";
        if (family_diab_f.equals("1")) {
            fInfo = fInfo + "糖尿病;";
        }
        String family_hbp_f = JsonPath.read(document, "$.family_hbp_ch") + "";
        if (family_hbp_f.equals("1")) {
            fInfo = fInfo + "高血压";
        }
        String family_chd_f = JsonPath.read(document, "$.family_chd_ch") + "";
        if (family_chd_f.equals("1")) {
            fInfo = fInfo + "冠心病";
        }
        String family_stroke_f = JsonPath.read(document, "$.family_stroke_ch") + "";
        if (family_stroke_f.equals("1")) {
            fInfo = fInfo + "脑卒中";
        }
        String family_hf_f = JsonPath.read(document, "$.family_hf_ch") + "";
        if (family_hf_f.equals("1")) {
            fInfo = fInfo + "髋部骨折";
        }
        String family_lung_f = JsonPath.read(document, "$.family_lung_ch") + "";
        if (family_lung_f.equals("1")) {
            fInfo = fInfo + "肺癌";
        }
        String family_pro_s = JsonPath.read(document, "$.family_pro_s") + "";
        if (family_pro_s.equals("1")) {
            fInfo = fInfo + "前列腺癌";
        }
        String family_pro_f = JsonPath.read(document, "$.family_breast_d") + "";
        if (family_pro_f.equals("1")) {
            fInfo = fInfo + "乳腺癌";
        }
        String family_gout_f = JsonPath.read(document, "$.family_gout_ch") + "";
        if (family_gout_f.equals("1")) {
            fInfo = fInfo + "痛风";
        }

        return fInfo;
    }

    public String personalPastHistory(Object document) {
        String fInfo = "";
        String hbp_his_disFlag = JsonPath.read(document, "$.hbp_his.disFlag") + "";
        if ("1".equals(hbp_his_disFlag)) {
            fInfo = fInfo + "高血压;";
        }
        String stroke_his_disFlag = JsonPath.read(document, "$.stroke_his.disFlag") + "";
        if ("1".equals(stroke_his_disFlag)) {
            fInfo = fInfo + "脑卒中;";
        }
        String chd_his_disFlag = JsonPath.read(document, "$.chd_his.disFlag") + "";
        if ("1".equals(chd_his_disFlag)) {
            fInfo = fInfo + "冠心病;";
        }

        String mi_his_disFlag = JsonPath.read(document, "$.mi_his.disFlag") + "";
        if ("1".equals(mi_his_disFlag)) {
            fInfo = fInfo + "严重心律失常;";
        }
        String angina_his_disFlag = JsonPath.read(document, "$.angina_his.disFlag") + "";
        if ("1".equals(angina_his_disFlag)) {
            fInfo = fInfo + "不稳定心绞痛;";
        }
        String arterial_status = JsonPath.read(document, "$.arterial_status") + "";
        if ("1".equals(arterial_status)) {
            fInfo = fInfo + "动脉硬化/狭窄/闭塞;";
        }
        String ushf_his_disFlag = JsonPath.read(document, "$.ushf_his.disFlag") + "";
        if ("1".equals(ushf_his_disFlag)) {
            fInfo = fInfo + "未控制的有症状的心律衰竭;";
        }
        String lvd_his_disFlag = JsonPath.read(document, "$.lvd_his.disFlag") + "";
        if ("1".equals(lvd_his_disFlag)) {
            fInfo = fInfo + "左心室肥大;";
        }
        String af_his_disFlag = JsonPath.read(document, "$.af_his.disFlag") + "";
        if ("1".equals(af_his_disFlag)) {
            fInfo = fInfo + "房颤;";
        }

        String ace_his_disFlag = JsonPath.read(document, "$.ace_his.disFlag") + "";
        if ("1".equals(ace_his_disFlag)) {
            fInfo = fInfo + "其他急性心脏事件;";
        }
        String diab2_his_disFlag = JsonPath.read(document, "$.diab2_his.disFlag") + "";
        if ("1".equals(diab2_his_disFlag)) {
            fInfo = fInfo + "糖尿病（1或2型）;";
        }

        String gae_his_disFlag = JsonPath.read(document, "$.gae_his.disFlag") + "";
        if ("1".equals(gae_his_disFlag)) {
            fInfo = fInfo + "痛风急性发作期;";
        }

        String gna_his_disFlag = JsonPath.read(document, "$.gna_his.disFlag") + "";
        if ("1".equals(gna_his_disFlag)) {
            fInfo = fInfo + "痛风非急性发作期;";
        }

        String bronch_his_disFlag = JsonPath.read(document, "$.bronch_his.disFlag") + "";
        if ("1".equals(bronch_his_disFlag)) {
            fInfo = fInfo + "慢性支气管炎;";
        }

        String emphy_his_disFlag = JsonPath.read(document, "$.emphy_his.disFlag") + "";
        if ("1".equals(emphy_his_disFlag)) {
            fInfo = fInfo + "肺气肿;";
        }

        String decline_his_disFlag = JsonPath.read(document, "$.decline_his.disFlag") + "";
        if ("1".equals(decline_his_disFlag)) {
            fInfo = fInfo + "肺结核;";
        }

        String lc_his_disFlag = JsonPath.read(document, "$.lc_his.disFlag") + "";
        if ("1".equals(lc_his_disFlag)) {
            fInfo = fInfo + "肺癌;";
        }

        String op_his_disFlag = JsonPath.read(document, "$.op_his.disFlag") + "";
        if ("1".equals(op_his_disFlag)) {
            fInfo = fInfo + "骨质疏松症;";
        }

        String fracture_his_disFlag = JsonPath.read(document, "$.fracture_his.disFlag") + "";
        if ("1".equals(fracture_his_disFlag)) {
            fInfo = fInfo + "既往骨折;";
        }

        String sport_his_disFlag = JsonPath.read(document, "$.sport_his.disFlag") + "";
        if ("1".equals(sport_his_disFlag)) {
            fInfo = fInfo + "其他限制运动的疾病（如关节炎、足部溃疡、肌肉韧带损伤等）;";
        }
        String secondary_op_his_disFlag = JsonPath.read(document, "$.secondary_op_his.disFlag") + "";
        if ("1".equals(secondary_op_his_disFlag)) {
            fInfo = fInfo + "引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、慢性营养不良等）;";
        }

        String prostate_disease_disFlag = JsonPath.read(document, "$.prostate_disease.disFlag") + "";
        if ("1".equals(prostate_disease_disFlag)) {
            fInfo = fInfo + "前列腺炎或肥大;";
        }
        String pc_his_disFlag = JsonPath.read(document, "$.pc_his.disFlag") + "";
        if ("1".equals(pc_his_disFlag)) {
            fInfo = fInfo + "前列腺癌;";
        }

        String bc_his_disFlag = JsonPath.read(document, "$.bc_his.disFlag") + "";
        if ("1".equals(bc_his_disFlag)) {
            fInfo = fInfo + "乳腺癌（男性也有患病几率）;";
        }

        return fInfo;
    }

    public String pastContactHistory(Object document) {
        String fInfo = "";
        // 有油烟接触史（长期厨房做菜或为厨师职业）lampblack_contact.disFlag:1
        // 长期从事化工、染料、橡胶及印刷等职业 chemical_occupation:1
        // 粉尘接触史（如：矿工、环卫工人等或长期接触过汽油等有害物质） dust_exposure_his.disFlag:1
        // 石棉接触史（长期接触过石棉） asbestos_contact_his.disFlag:1
        String lampblack_contact = JsonPath.read(document, "$.lampblack_contact.disFlag") + "";
        if ("1".equals(lampblack_contact)) {
            fInfo = fInfo + "有油烟接触史;";
        }

        String chemical_occupation = JsonPath.read(document, "$.chemical_occupation") + "";
        if ("1".equals(chemical_occupation)) {
            fInfo = fInfo + "长期从事化工、染料、橡胶及印刷等职业;";
        }

        String dust_exposure_his = JsonPath.read(document, "$.dust_exposure_his.disFlag") + "";
        if ("1".equals(dust_exposure_his)) {
            fInfo = fInfo + "粉尘接触史;";
        }

        String asbestos_contact_his = JsonPath.read(document, "$.asbestos_contact_his.disFlag") + "";
        if ("1".equals(asbestos_contact_his)) {
            fInfo = fInfo + "石棉接触史;";
        }

        return fInfo;
    }

    public YhUserAssessmentReportVOJO evaluateAge(int thisNum, Integer age, int sex) {
        if (sex == 1) {
            YhUserAssessmentReportVOJO v = new YhUserAssessmentReportVOJO();
            // 0.001*生理年龄*生理年龄 + 0.8512*生理年龄 + 0.7723
            v.setAge(String.valueOf(age));
            v.setIdealAge((double) Math.round((0.001 * age * age + 0.8512 * age + 0.7723) * 100) / 100);
            v.setIdealStr("如果您将以上不良的生活习惯改善至理想状态，您将达到健康年龄，开始行动吧，您将变得更年轻。");
            if (thisNum <= 20) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄大于生理年龄，说明您目前的生活习惯影响了您的健康状况，减少了寿命。");
                v.setEvaluateAge((double) Math.round((1.037 * age + 1.0862) * 10) / 10);
            } else if (thisNum > 20 && thisNum <= 30) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄大于生理年龄，说明您目前的生活习惯影响了您的健康状况，减少了寿命。");
                v.setEvaluateAge((double) Math.round((1.0279 * age + 0.7836) * 10) / 10);
            } else if (thisNum > 30 && thisNum <= 40) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄大于生理年龄，说明您目前的生活习惯影响了您的健康状况，减少了寿命。");
                v.setEvaluateAge((double) Math.round((1.0182 * age + 0.5419) * 10) / 10);
            } else if (thisNum > 40 && thisNum <= 50) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄大于生理年龄，说明您目前的生活习惯影响了您的健康状况，减少了寿命。");
                v.setEvaluateAge((double) Math.round((1.0084 * age + 0.2989) * 10) / 10);
            } else if (thisNum > 50 && thisNum <= 67) {
                v.setEvaluateStr("根据您目前的生活习惯情况，评价出的反映您目前综合健康状况的年龄。您的评价年龄等于生理年龄，请养成并持续保持良好的生活习惯，以延长寿命。");
                v.setEvaluateAge(age);
            } else if (thisNum > 67 && thisNum <= 75) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄小于生理年龄，说明您目前的生活习惯改善了您的健康状况，延长了寿命。");
                v.setEvaluateAge((double) Math.round((0.0013 * age * age + 0.8453 * age + 2.727) * 10) / 10);
            } else if (thisNum > 75 && thisNum < 85) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄小于生理年龄，说明您目前的生活习惯改善了您的健康状况，延长了寿命。");
                v.setEvaluateAge((double) Math.round((0.0012 * age * age + 0.853 * age + 2.3802) * 10) / 10);
            } else if (thisNum >= 85 && thisNum < 95) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄小于生理年龄，说明您目前的生活习惯改善了您的健康状况，延长了寿命。");
                v.setEvaluateAge((double) Math.round((age * 0.9615) * 10) / 10);
            } else if (thisNum >= 95 && thisNum < 100) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄小于生理年龄，说明您目前的生活习惯改善了您的健康状况，延长了寿命。");
                v.setEvaluateAge((double) Math.round((age - 0.5) * 10) / 10);
            }
            v.setExtendAge((double) Math.round((v.getEvaluateAge() - v.getIdealAge()) * 100) / 100);
            v.setExtend("您目前评价年龄与理想健康年龄的差距,也是养成健康生活方式将为您带来的寿命延长空间。");
            v.setEvaluatepxNum(v.getEvaluateAge() * 4.5 + 30);
            v.setIdealpxNum(v.getIdealAge() * 4.5 + 30);
            return v;
        } else {
            YhUserAssessmentReportVOJO v = new YhUserAssessmentReportVOJO();
            // 0.001*生理年龄*生理年龄 + 0.8512*生理年龄 + 0.7723
            v.setAge(String.valueOf(age));
            v.setIdealAge((double) Math.round((0.9561 * age - 1.8378) * 100) / 100);
            v.setIdealStr("如果您将以上不良的生活习惯改善至理想状态，您将达到健康年龄，开始行动吧，您将变得更年轻。");
            if (thisNum <= 20) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄大于生理年龄，说明您目前的生活习惯影响了您的健康状况，减少了寿命。");
                v.setEvaluateAge((double) Math.round((1.0431 * age + 1.0651) * 10) / 10);
            } else if (thisNum > 20 && thisNum <= 30) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄大于生理年龄，说明您目前的生活习惯影响了您的健康状况，减少了寿命。");
                v.setEvaluateAge((double) Math.round((1.1453 * (Math.pow(age, 0.9769))) * 10) / 10);
            } else if (thisNum > 30 && thisNum <= 40) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄大于生理年龄，说明您目前的生活习惯影响了您的健康状况，减少了寿命。");
                v.setEvaluateAge((double) Math.round((1.0176 * age + 0.5747) * 10) / 10);
            } else if (thisNum > 40 && thisNum <= 50) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄大于生理年龄，说明您目前的生活习惯影响了您的健康状况，减少了寿命。");
                v.setEvaluateAge((double) Math.round((1.0079 * age + 0.3177) * 10) / 10);
            } else if (thisNum > 50 && thisNum <= 67) {
                v.setEvaluateStr("根据您目前的生活习惯情况，评价出的反映您目前综合健康状况的年龄。您的评价年龄等于生理年龄，请养成并持续保持良好的生活习惯，以延长寿命。");
                v.setEvaluateAge(age);
            } else if (thisNum > 67 && thisNum <= 75) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄小于生理年龄，说明您目前的生活习惯改善了您的健康状况，延长了寿命。");
                v.setEvaluateAge((double) Math.round((0.9922 * age - 0.2999) * 10) / 10);
            } else if (thisNum > 75 && thisNum < 85) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄小于生理年龄，说明您目前的生活习惯改善了您的健康状况，延长了寿命。");
                v.setEvaluateAge((double) Math.round((0.9802 * age - 0.5105) * 10) / 10);
            } else if (thisNum >= 85 && thisNum < 95) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄小于生理年龄，说明您目前的生活习惯改善了您的健康状况，延长了寿命。");
                v.setEvaluateAge((double) Math.round((0.9718 * age - 0.7382) * 10) / 10);
            } else if (thisNum >= 95 && thisNum < 100) {
                v.setEvaluateStr("根据您目前的生活习惯状况，评价出的反映您目前综合健康状况的年龄。您的评价年龄小于生理年龄，说明您目前的生活习惯改善了您的健康状况，延长了寿命。");
                v.setEvaluateAge((double) Math.round((age - 0.5) * 10) / 10);
            }
            v.setExtendAge((double) Math.round((v.getEvaluateAge() - v.getIdealAge()) * 100) / 100);
            v.setExtend("您目前评价年龄与理想健康年龄的差距,也是养成健康生活方式将为您带来的寿命延长空间。");
            v.setEvaluatepxNum(v.getEvaluateAge() * 4.5 + 30);
            v.setIdealpxNum(v.getIdealAge() * 4.5 + 30);
            return v;
        }

    }

    public List<YhUserAssessmentReportVOJO> mainRiskFactors(String rs, Object document) {
        List<YhUserAssessmentReportVOJO> vlist = new ArrayList<>();
        YhUserAssessmentReportVOJO vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("家族史");
        String jiazu = "";
        // 父亲家族史
        String fInfo = familyfInfo(document);
        if (com.yihe.system.utils.StringUtil.isNotEmpty(fInfo)) {
            fInfo = fInfo + "<br>";
        }
        // 母亲家族史
        String mInfo = familymInfo(document);
        if (com.yihe.system.utils.StringUtil.isNotEmpty(mInfo)) {
            mInfo = mInfo + "<br>";
        }
        // 兄弟姐妹家族史
        String gsInfo = familybsInfo(document);
        if (com.yihe.system.utils.StringUtil.isNotEmpty(gsInfo)) {
            gsInfo = gsInfo + "<br>";
        }
        // 子女家族史
        String chInfo = familychInfo(document);
        if (com.yihe.system.utils.StringUtil.isNotEmpty(chInfo)) {
            chInfo = chInfo + "<br>";
        }

        jiazu = jiazu + familyfInfo(document);
        vojo.setRiskFactors(fInfo + mInfo + gsInfo + chInfo);
        vojo.setProposal("注意相关疾病的防患");
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("个人疾病史");
        vojo.setRiskFactors(personalPastHistory(document));
        vojo.setProposal("谨遵医嘱进行治疗和康复");
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("既往接触史");
        vojo.setRiskFactors(pastContactHistory(document));
        vojo.setProposal("做好防范措施，远离有害物质");
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("饮食");
        YhDiseaseRiskResult2Web selectByPrimaryByRs = YhDiseaseRiskResult2WebMapper.selectByPrimaryByRs(rs);
        if (null == selectByPrimaryByRs) {
            return null;
        }

        String food_vegetables = JsonPath.read(document, "$.food_vegetables") + "";
        if (!food_vegetables.equals("5")) {
            vojo.setRiskFactors("蔬菜摄入不足");
        }
        vojo.setProposal("适量增加新鲜蔬菜摄入量，300克/天。");
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("饮食");
        String food_fruits = JsonPath.read(document, "$.food_fruits") + "";
        if (!food_fruits.equals("5")) {
            vojo.setRiskFactors("水果摄入不足");
        }
        vojo.setProposal("适量增加新鲜水果摄入量，200克/天");
        vlist.add(vojo);


        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("饮食");
        String b4 = selectByPrimaryByRs.getB4();
        JSONObject b4Json = JSONObject.parseObject(b4);
        if (!b4Json.getString("now_value").equals("0")) {
            vojo.setRiskFactors("存在高脂饮食");
        }
        vojo.setProposal("少吃高脂肪含量的食物，控制体重并警惕血脂增高");
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("饮食");
        String tfa = selectByPrimaryByRs.getTfa();
        JSONObject tfaJson = JSONObject.parseObject(tfa);
        if (!tfaJson.getString("now_value").equals("0")) {
            vojo.setRiskFactors("反式脂肪酸摄入过量");
        }
        vojo.setProposal("少吃反式脂肪酸含量高的食物，远离肥胖警惕血液胆固醇增高");
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("饮食");
        String food_pickled = JsonPath.read(document, "$.food_pickled") + "";
        if (Integer.valueOf(food_pickled) >= 2) {
            vojo.setRiskFactors("咸菜腌渍食品摄入过量");
        }
        vojo.setProposal("少吃腌渍类食物，减少致癌物摄入");
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("饮食");
        String milk = selectByPrimaryByRs.getMilk();
        JSONObject milkJson = JSONObject.parseObject(milk);
        if (!milkJson.getString("now_value").equals("1")) {
            vojo.setRiskFactors("乳制品摄入不足");
        }
        vojo.setProposal("适量增加乳制品摄入量，300克/天");
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("饮食");
        String foodPurine = selectByPrimaryByRs.getFoodPurine();
        JSONObject foodPurineJson = JSONObject.parseObject(foodPurine);
        if (!foodPurineJson.getString("now_value").equals("0")) {
            vojo.setRiskFactors("存在高嘌呤饮食");
        }
        vojo.setProposal("少吃嘌呤含量高的食物，关注尿酸变化警惕痛风");
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("运动");
        String sport = selectByPrimaryByRs.getSport();
        JSONObject sportJson = JSONObject.parseObject(sport);
        String sportStr = sportJson.getString("now_value");
        vojo.setRiskFactors(sportStr.equals("1") ? "" : "体力活动水平不足");
        vojo.setProposal("循序渐进的增加运动量和时间，劳逸结合更健康");
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setType("吸烟");
        String smoke = selectByPrimaryByRs.getSmoke();
        JSONObject smokeJson = JSONObject.parseObject(smoke);
        String smoke_status = smokeJson.getString("now_value");
        if (StringUtil.isNotEmpty(smoke_status)) {
            if (smoke_status.equals("1")) {
                vojo.setRiskFactors("吸烟");
            }
        }

        vojo.setProposal("控烟，尽量戒烟");
        vlist.add(vojo);

        return vlist;
    }

    // 肥胖症发病相关
    public List<RiskFactorsVOJO> obesityRelated(String rs) {

        List<RiskFactorsVOJO> rlist = new ArrayList<>();
        rlist.add(getRiskInfo("drink", rs, "饮酒"));
        rlist.add(getRiskInfo("b4", rs, "高脂饮食"));
        rlist.add(getRiskInfo("tfa", rs, "反式脂肪酸摄入"));
        rlist.add(getRiskInfo("food", rs, "蔬菜水果摄入"));
        rlist.add(getRiskInfo("sport", rs, "体力活动水平"));
        return rlist;
    }

    public List<RiskFactorsVOJO> getLifeRiskInfo(String rs, Object document,
                                                 YhDiseaseAssessTotalReport2web findAssessTotalReportLastInfo, Model model, Integer sex) {
        String age = JsonPath.read(document, "$.person.age") + "";

        if (null != findAssessTotalReportLastInfo) {

            int thisNum = 0;
            int lastNum = 0;
            String lastRs = findAssessTotalReportLastInfo.getRequestSerialnum();
            YhQuestionCheckJson2Web selectByRsLimit1 = YhQuestionCheckJson2WebMapper.selectByRsLimit1(lastRs);
            String findQuestionCheckForCheckAnswerByrs = selectByRsLimit1.getUserCheckAnswer();
            Object lastdocument = null;
            lastdocument = Configuration.defaultConfiguration().jsonProvider()
                    .parse(findQuestionCheckForCheckAnswerByrs);
            List<RiskFactorsVOJO> rlist = new ArrayList<RiskFactorsVOJO>();
            YhDiseaseRiskResultAppVOJO vojo = new YhDiseaseRiskResultAppVOJO();
            vojo.setColumnName("BMI");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            String returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            RiskFactorsVOJO rv = new RiskFactorsVOJO();
            JSONObject jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("体重指数(BMI)");
            rv.setChanges(jsonObject.getString("change_value"));
            rv.setIdealLevel("18.5≤BMI<24");
            String now_value = jsonObject.getString("now_value");
            rv.setThisTime(now_value);
            // 颜色表示
            if (!(Double.valueOf(now_value) >= 18.5 && Double.valueOf(now_value) < 24)) {
                rv.setThisTimeColor("1");
            }
            // 本次得分
            if (Double.valueOf(now_value) < 18.5) {
                rv.setThisScore(5);
            } else if (Double.valueOf(now_value) >= 18.5 && Double.valueOf(now_value) < 24) {
                rv.setThisScore(10);
            } else if (Double.valueOf(now_value) >= 24 && Double.valueOf(now_value) < 28) {
                rv.setThisScore(3);
            } else {
                rv.setThisScore(0);
            }
            String last_value = jsonObject.getString("last_value");
            if (StringUtils.isEmpty(last_value) || "-".equals(last_value)) {
                rv.setLastScore(0);
            } else {
                if (Double.valueOf(last_value) < 18.5) {
                    rv.setLastScore(5);
                } else if (Double.valueOf(last_value) >= 18.5 && Double.valueOf(last_value) < 24) {
                    rv.setLastScore(10);
                } else if (Double.valueOf(last_value) >= 24 && Double.valueOf(last_value) < 28) {
                    rv.setLastScore(3);
                } else {
                    rv.setLastScore(0);
                }
            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("sport");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("体力活动水平");
            rv.setChanges(jsonObject.getString("change_value"));

            String sport_in = JsonPath.read(document, "$.sport_in") + "";
            String sport_inlast = JsonPath.read(lastdocument, "$.sport_in") + "";
            if (sport_in.equals("1")) {
                // 参加过
                String sport_freq = JsonPath.read(document, "$.sport_freq") + "";
                if (sport_freq.equals("1") || sport_freq.equals("2")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(0);
                    rv.setThisTimeColor("1");
                }

                if (sport_freq.equals("3")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(5);
                    rv.setThisTimeColor("1");
                }
                if (sport_freq.equals("4")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(10);
                    rv.setThisTimeColor("1");
                }
                if (sport_freq.equals("5")) {
                    rv.setThisTime("充足");
                    rv.setThisScore(20);
                }
            } else {
                // 没参加过
                rv.setThisTime("不足");
                rv.setThisScore(0);
                rv.setThisTimeColor("1");
            }

            if (sport_inlast.equals("1")) {
                // 参加过
                String sport_freq = JsonPath.read(lastdocument, "$.sport_freq") + "";
                if (sport_freq.equals("1") || sport_freq.equals("2")) {
                    rv.setLastScore(0);
                }

                if (sport_freq.equals("3")) {
                    rv.setLastScore(5);
                }
                if (sport_freq.equals("4")) {
                    rv.setLastScore(10);
                }
                if (sport_freq.equals("5")) {
                    rv.setLastScore(20);
                }
            } else {
                // 没参加过
                rv.setLastScore(0);
            }
            rv.setIdealLevel("每周5次以上运动，每次不少于30分钟");

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("smoke");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("吸烟状况");
            rv.setChanges(jsonObject.getString("change_value"));

            rv.setIdealLevel("不吸烟/戒烟");
            String smoke_status = JsonPath.read(document, "$.smoke_status") + "";
            if (smoke_status.equals("1")) {
                // 吸烟
                rv.setThisTime("吸烟");
                rv.setThisTimeColor("1");
                String smoke_amount = JsonPath.read(document, "$.smoke_amount") + "";
                if (Integer.valueOf(smoke_amount) < 10) {
                    rv.setThisScore(3);
                }
                if (Integer.valueOf(smoke_amount) >= 10 && Integer.valueOf(smoke_amount) <= 40) {
                    rv.setThisScore(1);
                }
                if (Integer.valueOf(smoke_amount) > 40) {
                    rv.setThisScore(0);
                }

            } else if (smoke_status.equals("2")) {
                // 不吸烟
                rv.setThisTime("您不吸烟");
                rv.setThisScore(10);
            } else {
                rv.setThisTime("已戒烟");
                String smoke_end_age = JsonPath.read(document, "$.smoke_end_age") + "";
                String age2 = JsonPath.read(document, "$.person.age") + "";
                if (StringUtils.isNotEmpty(smoke_end_age)) {
                    if (Integer.valueOf(age2) - Integer.valueOf(smoke_end_age) > 1) {
                        rv.setThisScore(10);
                    } else {
                        rv.setThisScore(3);
                    }
                } else {
                    rv.setThisScore(10);
                }

            }

            String smoke_statuslast = JsonPath.read(lastdocument, "$.smoke_status") + "";
            if (smoke_statuslast.equals("1")) {
                // 吸烟
                rv.setLastTime("吸烟");
                String smoke_amount = JsonPath.read(lastdocument, "$.smoke_amount") + "";
                if (Integer.valueOf(smoke_amount) < 10) {
                    rv.setLastScore(3);
                }
                if (Integer.valueOf(smoke_amount) >= 10 && Integer.valueOf(smoke_amount) <= 40) {
                    rv.setLastScore(1);
                }
                if (Integer.valueOf(smoke_amount) > 40) {
                    rv.setLastScore(0);
                }

            } else if (smoke_statuslast.equals("2")) {
                // 不吸烟
                rv.setLastTime("您不吸烟");
                rv.setLastScore(10);
            } else if (smoke_statuslast.equals("3")) {
                rv.setLastTime("您已戒烟");
                String smoke_end_age = JsonPath.read(lastdocument, "$.smoke_end_age") + "";
                String age2 = JsonPath.read(lastdocument, "$.person.age") + "";
                if (StringUtils.isNotEmpty(smoke_end_age)) {
                    if (Integer.valueOf(age2) - Integer.valueOf(smoke_end_age) > 1) {
                        rv.setLastScore(10);
                    } else {
                        rv.setLastScore(3);
                    }
                } else {
                    rv.setLastScore(10);
                }


            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("drink");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("饮酒状况");
            rv.setChanges(jsonObject.getString("change_value"));

            String food_drink = JsonPath.read(document, "$.food_drink") + "";
            rv.setIdealLevel("不饮酒");
            //1喝2不喝
            if (food_drink.equals("1")) {
                // 喝酒

                String food_drink_times = JsonPath.read(document, "$.food_drink_times") + "";
                String food_drink_beer = JsonPath.read(document, "$.food_drink_beer") + "";
                if (food_drink_times.equals("1") && (food_drink_beer.equals("1") || food_drink_beer.equals("2"))) {
                    rv.setThisTime("您饮酒适量");
                    rv.setThisScore(2);
                } else {
                    rv.setThisTime("您饮酒过量");
                    rv.setThisScore(0);
                    rv.setThisTimeColor("1");
                }
            } else {
                // 不喝酒
                rv.setThisTime("您不喝酒");
                rv.setThisScore(5);
            }

            String food_drinklast = JsonPath.read(lastdocument, "$.food_drink") + "";
            rv.setIdealLevel("不饮酒");
            if (food_drinklast.equals("1")) {
                // 喝酒
                rv.setLastTime("您不喝酒");
                rv.setLastScore(5);
            } else {
                // 不喝酒
                String food_drink_times = JsonPath.read(lastdocument, "$.food_drink_times") + "";
                String food_drink_beer = JsonPath.read(lastdocument, "$.food_drink_beer") + "";
                if (food_drink_times.equals("1") && (food_drink_beer.equals("1") || food_drink_beer.equals("2"))) {
                    rv.setLastTime("您饮酒适量");
                    rv.setLastScore(2);
                } else {
                    rv.setLastTime("您饮酒过量");
                    rv.setLastScore(0);
                }
            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("蔬菜摄入状况");
            rv.setIdealLevel("适量(300～500克/天)");
            String food_vegetables = JsonPath.read(document, "$.food_vegetables") + "";
            if ("1".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(2);
            } else if ("4".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(5);
            } else if ("5".equals(food_vegetables)) {
                rv.setThisTime("充足");

                rv.setThisScore(10);
            }

            String food_vegetableslast = JsonPath.read(lastdocument, "$.food_vegetables") + "";
            if ("1".equals(food_vegetableslast)) {
                rv.setLastTime("不足");

                rv.setLastScore(0);
            } else if ("2".equals(food_vegetableslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(0);
            } else if ("3".equals(food_vegetableslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(2);
            } else if ("4".equals(food_vegetableslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(5);
            } else if ("5".equals(food_vegetableslast)) {
                rv.setLastTime("充足");
                rv.setLastScore(10);
            }
            if (Integer.valueOf(food_vegetables) == Integer.valueOf(food_vegetableslast)) {
                rv.setChanges("无改善");
            } else if (Integer.valueOf(food_vegetables) > Integer.valueOf(food_vegetableslast)) {
                rv.setChanges("好转");
            } else {
                rv.setChanges("恶化");
            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("水果摄入状况");
            rv.setIdealLevel("适量(200～350克/天)");
            String food_fruits = JsonPath.read(document, "$.food_fruits") + "";
            if ("1".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(2);
            } else if ("4".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(5);
            } else if ("5".equals(food_fruits)) {
                rv.setThisTime("充足");
                rv.setThisScore(10);
            }

            String food_fruitslast = JsonPath.read(lastdocument, "$.food_fruits") + "";
            if ("1".equals(food_fruitslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(0);
            } else if ("2".equals(food_fruitslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(0);
            } else if ("3".equals(food_fruitslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(2);
            } else if ("4".equals(food_fruitslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(5);
            } else if ("5".equals(food_fruitslast)) {
                rv.setLastTime("充足");
                rv.setLastScore(10);
            }

            if (Integer.valueOf(food_fruits) == Integer.valueOf(food_fruitslast)) {
                rv.setChanges("无改善");
            } else if (Integer.valueOf(food_fruits) > Integer.valueOf(food_fruitslast)) {
                rv.setChanges("好转");
            } else {
                rv.setChanges("恶化");
            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("milk");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("乳制品摄入状况");
            rv.setChanges(jsonObject.getString("change_value"));

            rv.setIdealLevel("适量(300克/天)");
            String food_milk = JsonPath.read(document, "$.food_milk") + "";
            String food_dairy = JsonPath.read(document, "$.food_dairy") + "";
            if ("1".equals(food_milk)) {
                rv.setThisTime("不足");// 不喝
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_milk)) {
                rv.setThisTime("不足");// 少于0.5杯
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_milk)) {
                rv.setThisTime("充足");// 0.5-1杯
                rv.setThisScore(10);
            } else if ("4".equals(food_milk)) {
                rv.setThisTime("充足");// 1-2杯
                rv.setThisScore(10);
            } else if ("5".equals(food_milk) && "5".equals(food_dairy)) {
                rv.setThisTime("过量");// 2杯以上
                rv.setThisTimeColor("1");
                rv.setThisScore(3);
            } else {
                rv.setThisTime("充足");// 1-2杯
                rv.setThisScore(10);
            }

            String food_milklast = JsonPath.read(lastdocument, "$.food_milk") + "";
            String food_dairylast = JsonPath.read(lastdocument, "$.food_dairy") + "";
            if ("1".equals(food_milklast)) {
                rv.setLastTime("不足");// 不喝

                rv.setLastScore(0);
            } else if ("2".equals(food_milklast)) {
                rv.setLastTime("不足");// 少于0.5杯
                rv.setLastScore(0);
            } else if ("3".equals(food_milklast)) {
                rv.setLastTime("充足");// 0.5-1杯
                rv.setLastScore(10);
            } else if ("4".equals(food_milklast)) {
                rv.setLastTime("充足");// 1-2杯
                rv.setLastScore(10);
            } else if ("5".equals(food_milklast) && "5".equals(food_dairylast)) {
                rv.setLastTime("过量");// 2杯以上
                rv.setLastScore(3);
            } else {
                rv.setLastTime("充足");// 1-2杯
                rv.setLastScore(10);
            }
            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("tfa");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("反式脂肪酸状况");
            rv.setChanges(jsonObject.getString("change_value"));

            rv.setIdealLevel("不吃");
            String food_sweet = JsonPath.read(document, "$.food_sweet") + "";
            String food_sugary = JsonPath.read(document, "$.food_sugary") + "";
            String food_sweetNew = "";
            if (Integer.valueOf(food_sweet) > Integer.valueOf(food_sugary)) {
                food_sweetNew = food_sweet;
            } else {
                food_sweetNew = food_sugary;
            }
            if ("1".equals(food_sweetNew)) {
                rv.setThisTime("不吃");
                rv.setThisScore(10);
            }
            if ("2".equals(food_sweetNew)) {
                rv.setThisTime("适量");
                rv.setThisScore(5);
            }
            if ("3".equals(food_sweetNew)) {
                rv.setThisTime("过量");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }
            if ("4".equals(food_sweetNew)) {
                rv.setThisTime("过量");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }

            String food_sweetlast = JsonPath.read(lastdocument, "$.food_sweet") + "";
            String food_sugarylast = JsonPath.read(lastdocument, "$.food_sugary") + "";
            String food_sweetlastNew = "";
            if (Integer.valueOf(food_sweetlast) > Integer.valueOf(food_sugarylast)) {
                food_sweetlastNew = food_sweetlast;
            } else {
                food_sweetlastNew = food_sugarylast;
            }
            if ("1".equals(food_sweetlastNew)) {
                rv.setLastTime("不吃");
                rv.setLastScore(10);
            }
            if ("2".equals(food_sweetlastNew)) {
                rv.setLastTime("适量");
                rv.setLastScore(5);
            }
            if ("3".equals(food_sweetlastNew)) {
                rv.setLastTime("过量");
                rv.setLastScore(0);
            }
            if ("4".equals(food_sweetlastNew)) {
                rv.setLastTime("过量");
                rv.setLastScore(0);
            }
            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("psy_nervous");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("精神压力");
            rv.setChanges(jsonObject.getString("change_value"));

            rv.setIdealLevel("没有");
            String psy_nervous = JsonPath.read(document, "$.psy_nervous") + "";
            if ("1".equals(psy_nervous)) {
                rv.setThisTime("没有");
                rv.setThisScore(10);
            }
            if ("2".equals(psy_nervous)) {
                rv.setThisTime("偶尔");
                rv.setThisScore(5);
            }
            if ("3".equals(psy_nervous)) {
                rv.setThisTime("经常");
                rv.setThisScore(0);
                rv.setThisTimeColor("1");
            }

            String psy_nervouslast = JsonPath.read(lastdocument, "$.psy_nervous") + "";
            if ("1".equals(psy_nervouslast)) {
                rv.setLastTime("没有");
                rv.setLastScore(10);
            }
            if ("2".equals(psy_nervouslast)) {
                rv.setLastTime("偶尔");
                rv.setLastScore(5);
            }
            if ("3".equals(psy_nervouslast)) {
                rv.setLastTime("经常");
                rv.setLastScore(0);
            }

            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("睡眠状况");
            rv.setIdealLevel("良好");
            String bedtime = JsonPath.read(document, "$.bedtime") + "";
            if ("1".equals(bedtime)) {
                rv.setThisTime("一般");
                rv.setThisScore(5);
            }
            if ("2".equals(bedtime)) {
                rv.setThisTime("良好");
                rv.setThisScore(10);
            }
            if ("3".equals(bedtime)) {
                rv.setThisTime("差");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }

            String bedtimelast = JsonPath.read(lastdocument, "$.bedtime") + "";
            if ("1".equals(bedtimelast)) {
                rv.setLastTime("一般");
                rv.setLastScore(5);
            }
            if ("2".equals(bedtimelast)) {
                rv.setLastTime("良好");
                rv.setLastScore(10);
            }
            if ("3".equals(bedtimelast)) {
                rv.setLastTime("差");
                rv.setLastScore(0);
            }
            if ("1".equals(bedtime)) {
                if ("1".equals(bedtimelast)) {
                    rv.setChanges("无改善");
                }
                if ("2".equals(bedtimelast)) {
                    rv.setChanges("转好");
                }
                if ("3".equals(bedtimelast)) {
                    rv.setChanges("恶化");
                }
            }
            if ("2".equals(bedtime)) {
                if ("1".equals(bedtimelast)) {
                    rv.setChanges("恶化");
                }
                if ("2".equals(bedtimelast)) {
                    rv.setChanges("无改善");
                }
                if ("3".equals(bedtimelast)) {
                    rv.setChanges("恶化");
                }
            }
            if ("3".equals(bedtime)) {
                if ("1".equals(bedtimelast)) {
                    rv.setChanges("恶化");
                }
                if ("2".equals(bedtimelast)) {
                    rv.setChanges("转好");
                }
                if ("3".equals(bedtimelast)) {
                    rv.setChanges("无改善");
                }
            }
            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);


            RiskFactorsVOJO rvojo = new RiskFactorsVOJO();
            rvojo.setRiskFactors("生活方式评分");
            rvojo.setIdealLevel("优秀（80~100分）");

            rvojo.setThisScore(thisNum);
            if (null != findAssessTotalReportLastInfo) {
                rvojo.setLastScore(lastNum);
                if (thisNum == lastNum) {
                    rvojo.setChanges("无改善");
                }
                if (thisNum > lastNum) {
                    rvojo.setChanges("好转");
                }
                if (thisNum < lastNum) {
                    rvojo.setChanges("恶化");
                }

                String evaluateInfo = "";
                if (thisNum == lastNum) {
                    evaluateInfo = "没有发生变化";
                } else if (thisNum > lastNum) {

                    double shangshen = (double) Math.round(((thisNum - lastNum) / Double.valueOf(lastNum) * 100) * 100) / 100;
                    evaluateInfo = "有上升趋势，比上次积分增加了" + (thisNum - lastNum) + "分，上升率" + shangshen + "%。";
                } else {
                    double shangshen = (double) Math.round(((lastNum - thisNum) / Double.valueOf(lastNum) * 100) * 100) / 100;
                    evaluateInfo = "有下降趋势，比上次积分减少了" + (lastNum - thisNum) + "分，下降率" + shangshen + "%。";
                }
                model.addAttribute("evaluateInfo", evaluateInfo);
            }

            if (thisNum >= 80 && thisNum <= 100) {
                rvojo.setThisTime("优秀");
            } else if (thisNum >= 60 && thisNum < 80) {
                rvojo.setThisTime("良好");
            } else if (thisNum >= 40 && thisNum < 60) {
                rvojo.setThisTime("一般");
            } else {
                rvojo.setThisTime("差");
            }
            rlist.add(rvojo);

            model.addAttribute("thisNum", thisNum);
            model.addAttribute("lastNum", lastNum);
            model.addAttribute("evaluateAge", evaluateAge(thisNum, Integer.valueOf(age), sex));
            return rlist;
        } else {


            int thisNum = 0;
            int lastNum = 0;


            List<RiskFactorsVOJO> rlist = new ArrayList<RiskFactorsVOJO>();
            YhDiseaseRiskResultAppVOJO vojo = new YhDiseaseRiskResultAppVOJO();
            vojo.setColumnName("BMI");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            String returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            RiskFactorsVOJO rv = new RiskFactorsVOJO();
            JSONObject jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("体重指数(BMI)");
            rv.setIdealLevel("18.5≤BMI<24");
            String now_value = jsonObject.getString("now_value");
            rv.setThisTime(now_value);
            // 颜色表示
            if (!(Double.valueOf(now_value) >= 18.5 && Double.valueOf(now_value) < 24)) {
                rv.setThisTimeColor("1");
            }
            // 本次得分
            if (Double.valueOf(now_value) < 18.5) {
                rv.setThisScore(5);
            } else if (Double.valueOf(now_value) >= 18.5 && Double.valueOf(now_value) < 24) {
                rv.setThisScore(10);
            } else if (Double.valueOf(now_value) >= 24 && Double.valueOf(now_value) < 28) {
                rv.setThisScore(3);
            } else {
                rv.setThisScore(0);
            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("sport");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("体力活动水平");

            String sport_in = JsonPath.read(document, "$.sport_in") + "";


            if (sport_in.equals("1")) {
                // 参加过
                String sport_freq = JsonPath.read(document, "$.sport_freq") + "";
                if (sport_freq.equals("1") || sport_freq.equals("2")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(0);
                    rv.setThisTimeColor("1");
                }

                if (sport_freq.equals("3")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(5);
                    rv.setThisTimeColor("1");
                }
                if (sport_freq.equals("4")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(10);
                    rv.setThisTimeColor("1");
                }
                if (sport_freq.equals("5")) {
                    rv.setThisTime("充足");
                    rv.setThisScore(20);
                }
            } else {
                // 没参加过
                rv.setThisTime("不足");
                rv.setThisScore(0);
                rv.setThisTimeColor("1");
            }

            rv.setIdealLevel("每周5次以上运动，每次不少于30分钟");
            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("smoke");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("吸烟状况");

            rv.setIdealLevel("不吸烟/戒烟");
            String smoke_status = JsonPath.read(document, "$.smoke_status") + "";
            if (smoke_status.equals("1")) {
                // 吸烟
                rv.setLastTime("吸烟");
                rv.setThisTimeColor("1");
                String smoke_amount = JsonPath.read(document, "$.smoke_amount") + "";
                if (Integer.valueOf(smoke_amount) < 10) {
                    rv.setThisScore(3);
                }
                if (Integer.valueOf(smoke_amount) >= 10 && Integer.valueOf(smoke_amount) <= 40) {
                    rv.setThisScore(1);
                }
                if (Integer.valueOf(smoke_amount) > 40) {
                    rv.setThisScore(0);
                }

            } else if (smoke_status.equals("2")) {
                // 不吸烟
                rv.setThisTime("您不吸烟");
                rv.setThisScore(10);
            } else {
                String smoke_end_age = JsonPath.read(document, "$.smoke_end_age") + "";
                String age2 = JsonPath.read(document, "$.person.age") + "";
                if (StringUtils.isNotEmpty(smoke_end_age)) {
                    if (Integer.valueOf(age2) - Integer.valueOf(smoke_end_age) > 1) {
                        rv.setThisScore(10);
                    } else {
                        rv.setThisScore(3);
                    }
                } else {
                    rv.setThisScore(10);
                }


            }
            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("drink");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("饮酒状况");

            String food_drink = JsonPath.read(document, "$.food_drink") + "";
            rv.setIdealLevel("不饮酒");
            if (food_drink.equals("2")) {
                // 喝酒
                rv.setThisTime("您不喝酒");
                rv.setThisScore(5);
            } else {
                // 不喝酒
                String food_drink_times = JsonPath.read(document, "$.food_drink_times") + "";
                String food_drink_beer = JsonPath.read(document, "$.food_drink_beer") + "";
                if (food_drink_times.equals("1") && (food_drink_beer.equals("1") || food_drink_beer.equals("2"))) {
                    rv.setThisTime("您饮酒适量");
                    rv.setThisScore(2);
                } else {
                    rv.setThisTime("您饮酒过量");
                    rv.setThisScore(0);
                    rv.setThisTimeColor("1");
                }
            }
            rv.setIdealLevel("不饮酒");
            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("蔬菜摄入状况");
            rv.setIdealLevel("适量(300～500克/天)");
            String food_vegetables = JsonPath.read(document, "$.food_vegetables") + "";
            if ("1".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(2);
            } else if ("4".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(5);
            } else if ("5".equals(food_vegetables)) {
                rv.setThisTime("充足");

                rv.setThisScore(10);
            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("水果摄入状况");
            rv.setIdealLevel("适量(200～350克/天)");
            String food_fruits = JsonPath.read(document, "$.food_fruits") + "";
            if ("1".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(2);
            } else if ("4".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(5);
            } else if ("5".equals(food_fruits)) {
                rv.setThisTime("充足");
                rv.setThisScore(10);
            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("milk");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("乳制品摄入状况");

            rv.setIdealLevel("适量(300克/天)");
            String food_milk = JsonPath.read(document, "$.food_milk") + "";
            String food_dairy = JsonPath.read(document, "$.food_dairy") + "";
            if ("1".equals(food_milk)) {
                rv.setThisTime("不足");// 不喝
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_milk)) {
                rv.setThisTime("不足");// 少于0.5杯
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_milk)) {
                rv.setThisTime("充足");// 0.5-1杯
                rv.setThisScore(10);
            } else if ("4".equals(food_milk)) {
                rv.setThisTime("充足");// 1-2杯
                rv.setThisScore(10);
            } else if ("5".equals(food_milk) && "5".equals(food_dairy)) {
                rv.setThisTime("过量");// 2杯以上
                rv.setThisTimeColor("1");
                rv.setThisScore(3);
            } else {
                rv.setThisTime("充足");// 1-2杯
                rv.setThisScore(10);
            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("tfa");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("反式脂肪酸状况");

            rv.setIdealLevel("不吃");
            String food_sweet = JsonPath.read(document, "$.food_sweet") + "";
            String food_sugary = JsonPath.read(document, "$.food_sugary") + "";
            String food_sweetNew = "";
            if (Integer.valueOf(food_sweet) > Integer.valueOf(food_sugary)) {
                food_sweetNew = food_sweet;
            } else {
                food_sweetNew = food_sugary;
            }
            if ("1".equals(food_sweetNew)) {
                rv.setThisTime("不吃");
                rv.setThisScore(10);
            }
            if ("2".equals(food_sweetNew)) {
                rv.setThisTime("适量");
                rv.setThisScore(5);
            }
            if ("3".equals(food_sweetNew)) {
                rv.setThisTime("过量");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }
            if ("4".equals(food_sweetNew)) {
                rv.setThisTime("过量");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);

            vojo.setColumnName("psy_nervous");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("精神压力");

            rv.setIdealLevel("没有");
            String psy_nervous = JsonPath.read(document, "$.psy_nervous") + "";
            if ("1".equals(psy_nervous)) {
                rv.setThisTime("没有");
                rv.setThisScore(10);
            }
            if ("2".equals(psy_nervous)) {
                rv.setThisTime("偶尔");
                rv.setThisScore(5);
            }
            if ("3".equals(psy_nervous)) {
                rv.setThisTime("经常");
                rv.setThisScore(0);
                rv.setThisTimeColor("1");
            }


            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("睡眠状况");
            rv.setIdealLevel("良好");
            String bedtime = JsonPath.read(document, "$.bedtime") + "";
            if ("1".equals(bedtime)) {
                rv.setThisTime("一般");
                rv.setThisScore(5);
            }
            if ("2".equals(bedtime)) {
                rv.setThisTime("良好");
                rv.setThisScore(10);
            }
            if ("3".equals(bedtime)) {
                rv.setThisTime("差");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }

            thisNum = thisNum + rv.getThisScore();
            lastNum = lastNum + (null == rv.getLastScore() ? 0 : rv.getLastScore());
            rlist.add(rv);


            RiskFactorsVOJO rvojo = new RiskFactorsVOJO();
            rvojo.setRiskFactors("生活方式评分");
            rvojo.setIdealLevel("优秀（80~100分）");

            rvojo.setThisScore(thisNum);
            if (null != findAssessTotalReportLastInfo) {
                rvojo.setLastScore(lastNum);
                if (thisNum == lastNum) {
                    rvojo.setChanges("无改善");
                }
                if (thisNum > lastNum) {
                    rvojo.setChanges("好转");
                }
                if (thisNum < lastNum) {
                    rvojo.setChanges("恶化");
                }

                String evaluateInfo = "";
                if (thisNum == lastNum) {
                    evaluateInfo = "没有发生变化";
                } else if (thisNum > lastNum) {

                    double shangshen = (double) Math.round(((thisNum - lastNum) / Double.valueOf(lastNum) * 100) * 100) / 100;
                    evaluateInfo = "有上升趋势，比上次积分增加了" + (thisNum - lastNum) + "分，上升率" + shangshen + "%。";
                } else {
                    double shangshen = (double) Math.round(((lastNum - thisNum) / Double.valueOf(lastNum) * 100) * 100) / 100;
                    evaluateInfo = "有下降趋势，比上次积分减少了" + (lastNum - thisNum) + "分，下降率" + shangshen + "%。";
                }
                model.addAttribute("evaluateInfo", evaluateInfo);
            }

            if (thisNum >= 80 && thisNum <= 100) {
                rvojo.setThisTime("优秀");
            } else if (thisNum >= 60 && thisNum < 80) {
                rvojo.setThisTime("良好");
            } else if (thisNum >= 40 && thisNum < 60) {
                rvojo.setThisTime("一般");
            } else {
                rvojo.setThisTime("差");
            }
            rlist.add(rvojo);

            model.addAttribute("thisNum", thisNum);
            model.addAttribute("lastNum", lastNum);
            model.addAttribute("evaluateAge", evaluateAge(thisNum, Integer.valueOf(age), sex));
            return rlist;

        }
    }

    public List<RiskFactorsVOJO> getLifeRiskInfo(String rs, Object document,
                                                 YhDiseaseAssessTotalReport2web findAssessTotalReportLastInfo) {
        if (null != findAssessTotalReportLastInfo) {

            int thisNum = 0;
            int lastNum = 0;
            String lastRs = findAssessTotalReportLastInfo.getRequestSerialnum();
            YhQuestionCheckJson2Web selectByRsLimit1 = YhQuestionCheckJson2WebMapper.selectByRsLimit1(lastRs);
            String findQuestionCheckForCheckAnswerByrs = selectByRsLimit1.getUserCheckAnswer();
            Object lastdocument = null;
            lastdocument = Configuration.defaultConfiguration().jsonProvider()
                    .parse(findQuestionCheckForCheckAnswerByrs);
            List<RiskFactorsVOJO> rlist = new ArrayList<RiskFactorsVOJO>();
            YhDiseaseRiskResultAppVOJO vojo = new YhDiseaseRiskResultAppVOJO();
            vojo.setColumnName("BMI");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            String returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            RiskFactorsVOJO rv = new RiskFactorsVOJO();
            JSONObject jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("体重指数(BMI)");
            rv.setChanges(jsonObject.getString("change_value"));
            rv.setIdealLevel("18.5≤BMI<24");
            String now_value = jsonObject.getString("now_value");
            rv.setThisTime(now_value);
            // 颜色表示
            if (!(Double.valueOf(now_value) >= 18.5 && Double.valueOf(now_value) < 24)) {
                rv.setThisTimeColor("1");
            }
            // 本次得分
            if (Double.valueOf(now_value) < 18.5) {
                rv.setThisScore(5);
            } else if (Double.valueOf(now_value) >= 18.5 && Double.valueOf(now_value) < 24) {
                rv.setThisScore(10);
            } else if (Double.valueOf(now_value) >= 24 && Double.valueOf(now_value) < 28) {
                rv.setThisScore(3);
            } else {
                rv.setThisScore(0);
            }
            String last_value = jsonObject.getString("last_value");
            if (StringUtils.isEmpty(last_value) || "-".equals(last_value)) {
                rv.setLastScore(0);
            } else {
                if (Double.valueOf(last_value) < 18.5) {
                    rv.setLastScore(5);
                } else if (Double.valueOf(last_value) >= 18.5 && Double.valueOf(last_value) < 24) {
                    rv.setLastScore(10);
                } else if (Double.valueOf(last_value) >= 24 && Double.valueOf(last_value) < 28) {
                    rv.setLastScore(3);
                } else {
                    rv.setLastScore(0);
                }
            }


            rlist.add(rv);

            vojo.setColumnName("sport");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("体力活动水平");
            rv.setChanges(jsonObject.getString("change_value"));

            String sport_in = JsonPath.read(document, "$.sport_in") + "";
            String sport_inlast = JsonPath.read(lastdocument, "$.sport_in") + "";
            if (sport_in.equals("1")) {
                // 参加过
                String sport_freq = JsonPath.read(document, "$.sport_freq") + "";
                if (sport_freq.equals("1") || sport_freq.equals("2")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(0);
                    rv.setThisTimeColor("1");
                }

                if (sport_freq.equals("3")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(5);
                    rv.setThisTimeColor("1");
                }
                if (sport_freq.equals("4")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(10);
                    rv.setThisTimeColor("1");
                }
                if (sport_freq.equals("5")) {
                    rv.setThisTime("充足");
                    rv.setThisScore(20);
                }
            } else {
                // 没参加过
                rv.setThisTime("不足");
                rv.setThisScore(0);
                rv.setThisTimeColor("1");
            }

            if (sport_inlast.equals("1")) {
                // 参加过
                String sport_freq = JsonPath.read(lastdocument, "$.sport_freq") + "";
                if (sport_freq.equals("1") || sport_freq.equals("2")) {
                    rv.setLastScore(0);
                }

                if (sport_freq.equals("3")) {
                    rv.setLastScore(5);
                }
                if (sport_freq.equals("4")) {
                    rv.setLastScore(10);
                }
                if (sport_freq.equals("5")) {
                    rv.setLastScore(20);
                }
            } else {
                // 没参加过
                rv.setLastScore(0);
            }
            rv.setIdealLevel("每周5次以上运动，每次不少于30分钟");
            rlist.add(rv);

            vojo.setColumnName("smoke");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("吸烟状况");
            rv.setChanges(jsonObject.getString("change_value"));

            rv.setIdealLevel("不吸烟/戒烟");
            String smoke_status = JsonPath.read(document, "$.smoke_status") + "";
            if (smoke_status.equals("1")) {
                // 吸烟
                rv.setThisTime("吸烟");
                rv.setThisTimeColor("1");
                String smoke_amount = JsonPath.read(document, "$.smoke_amount") + "";
                if (Integer.valueOf(smoke_amount) < 10) {
                    rv.setThisScore(3);
                }
                if (Integer.valueOf(smoke_amount) >= 10 && Integer.valueOf(smoke_amount) <= 40) {
                    rv.setThisScore(1);
                }
                if (Integer.valueOf(smoke_amount) > 40) {
                    rv.setThisScore(0);
                }

            } else if (smoke_status.equals("2")) {
                // 不吸烟
                rv.setThisTime("您不吸烟");
                rv.setThisScore(10);
            } else {
                rv.setThisTime("已戒烟");
                String smoke_end_age = JsonPath.read(document, "$.smoke_end_age") + "";
                String age = JsonPath.read(document, "$.person.age") + "";
                if (StringUtils.isNotEmpty(smoke_end_age)) {
                    if (Integer.valueOf(age) - Integer.valueOf(smoke_end_age) > 1) {
                        rv.setThisScore(10);
                    } else {
                        rv.setThisScore(3);
                    }
                } else {
                    rv.setThisScore(10);
                }

            }

            String smoke_statuslast = JsonPath.read(lastdocument, "$.smoke_status") + "";
            if (smoke_statuslast.equals("1")) {
                // 吸烟
                rv.setLastTime("吸烟");
                String smoke_amount = JsonPath.read(lastdocument, "$.smoke_amount") + "";
                if (Integer.valueOf(smoke_amount) < 10) {
                    rv.setLastScore(3);
                }
                if (Integer.valueOf(smoke_amount) >= 10 && Integer.valueOf(smoke_amount) <= 40) {
                    rv.setLastScore(1);
                }
                if (Integer.valueOf(smoke_amount) > 40) {
                    rv.setLastScore(0);
                }

            } else if (smoke_statuslast.equals("2")) {
                // 不吸烟
                rv.setLastTime("您不吸烟");
                rv.setLastScore(10);
            } else if (smoke_statuslast.equals("3")) {
                rv.setLastTime("您已戒烟");
                String smoke_end_age = JsonPath.read(lastdocument, "$.smoke_end_age") + "";
                String age = JsonPath.read(lastdocument, "$.person.age") + "";
                if (StringUtils.isNotEmpty(smoke_end_age)) {
                    if (Integer.valueOf(age) - Integer.valueOf(smoke_end_age) > 1) {
                        rv.setLastScore(10);
                    } else {
                        rv.setLastScore(3);
                    }
                } else {
                    rv.setLastScore(10);
                }


            }

            rlist.add(rv);

            vojo.setColumnName("drink");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("饮酒状况");
            rv.setChanges(jsonObject.getString("change_value"));

            String food_drink = JsonPath.read(document, "$.food_drink") + "";
            rv.setIdealLevel("不饮酒");
            //1喝2不喝
            if (food_drink.equals("1")) {
                // 喝酒

                String food_drink_times = JsonPath.read(document, "$.food_drink_times") + "";
                String food_drink_beer = JsonPath.read(document, "$.food_drink_beer") + "";
                if (food_drink_times.equals("1") && (food_drink_beer.equals("1") || food_drink_beer.equals("2"))) {
                    rv.setThisTime("您饮酒适量");
                    rv.setThisScore(2);
                } else {
                    rv.setThisTime("您饮酒过量");
                    rv.setThisScore(0);
                    rv.setThisTimeColor("1");
                }
            } else {
                // 不喝酒
                rv.setThisTime("您不喝酒");
                rv.setThisScore(5);
            }

            String food_drinklast = JsonPath.read(lastdocument, "$.food_drink") + "";
            rv.setIdealLevel("不饮酒");
            if (food_drinklast.equals("1")) {
                // 喝酒
                rv.setLastTime("您不喝酒");
                rv.setLastScore(5);
            } else {
                // 不喝酒
                String food_drink_times = JsonPath.read(lastdocument, "$.food_drink_times") + "";
                String food_drink_beer = JsonPath.read(lastdocument, "$.food_drink_beer") + "";
                if (food_drink_times.equals("1") && (food_drink_beer.equals("1") || food_drink_beer.equals("2"))) {
                    rv.setLastTime("您饮酒适量");
                    rv.setLastScore(2);
                } else {
                    rv.setLastTime("您饮酒过量");
                    rv.setLastScore(0);
                }
            }
            rlist.add(rv);

            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("蔬菜摄入状况");
            rv.setIdealLevel("适量(300～500克/天)");
            String food_vegetables = JsonPath.read(document, "$.food_vegetables") + "";
            if ("1".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(2);
            } else if ("4".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(5);
            } else if ("5".equals(food_vegetables)) {
                rv.setThisTime("充足");

                rv.setThisScore(10);
            }

            String food_vegetableslast = JsonPath.read(lastdocument, "$.food_vegetables") + "";
            if ("1".equals(food_vegetableslast)) {
                rv.setLastTime("不足");

                rv.setLastScore(0);
            } else if ("2".equals(food_vegetableslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(0);
            } else if ("3".equals(food_vegetableslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(2);
            } else if ("4".equals(food_vegetableslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(5);
            } else if ("5".equals(food_vegetableslast)) {
                rv.setLastTime("充足");
                rv.setLastScore(10);
            }
            if (Integer.valueOf(food_vegetables) == Integer.valueOf(food_vegetableslast)) {
                rv.setChanges("无改善");
            } else if (Integer.valueOf(food_vegetables) > Integer.valueOf(food_vegetableslast)) {
                rv.setChanges("好转");
            } else {
                rv.setChanges("恶化");
            }
            rlist.add(rv);

            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("水果摄入状况");
            rv.setIdealLevel("适量(200～350克/天)");
            String food_fruits = JsonPath.read(document, "$.food_fruits") + "";
            if ("1".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(2);
            } else if ("4".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(5);
            } else if ("5".equals(food_fruits)) {
                rv.setThisTime("充足");
                rv.setThisScore(10);
            }

            String food_fruitslast = JsonPath.read(lastdocument, "$.food_fruits") + "";
            if ("1".equals(food_fruitslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(0);
            } else if ("2".equals(food_fruitslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(0);
            } else if ("3".equals(food_fruitslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(2);
            } else if ("4".equals(food_fruitslast)) {
                rv.setLastTime("不足");
                rv.setLastScore(5);
            } else if ("5".equals(food_fruitslast)) {
                rv.setLastTime("充足");
                rv.setLastScore(10);
            }

            if (Integer.valueOf(food_fruits) == Integer.valueOf(food_fruitslast)) {
                rv.setChanges("无改善");
            } else if (Integer.valueOf(food_fruits) > Integer.valueOf(food_fruitslast)) {
                rv.setChanges("好转");
            } else {
                rv.setChanges("恶化");
            }
            rlist.add(rv);

            vojo.setColumnName("milk");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("乳制品摄入状况");
            rv.setChanges(jsonObject.getString("change_value"));

            rv.setIdealLevel("适量(300克/天)");
            String food_milk = JsonPath.read(document, "$.food_milk") + "";
            String food_dairy = JsonPath.read(document, "$.food_dairy") + "";
            if ("1".equals(food_milk)) {
                rv.setThisTime("不足");// 不喝
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_milk)) {
                rv.setThisTime("不足");// 少于0.5杯
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_milk)) {
                rv.setThisTime("充足");// 0.5-1杯
                rv.setThisScore(10);
            } else if ("4".equals(food_milk)) {
                rv.setThisTime("充足");// 1-2杯
                rv.setThisScore(10);
            } else if ("5".equals(food_milk) && "5".equals(food_dairy)) {
                rv.setThisTime("过量");// 2杯以上
                rv.setThisTimeColor("1");
                rv.setThisScore(3);
            } else {
                rv.setThisTime("充足");// 1-2杯
                rv.setThisScore(10);
            }

            String food_milklast = JsonPath.read(lastdocument, "$.food_milk") + "";
            String food_dairylast = JsonPath.read(lastdocument, "$.food_dairy") + "";
            if ("1".equals(food_milklast)) {
                rv.setLastTime("不足");// 不喝

                rv.setLastScore(0);
            } else if ("2".equals(food_milklast)) {
                rv.setLastTime("不足");// 少于0.5杯
                rv.setLastScore(0);
            } else if ("3".equals(food_milklast)) {
                rv.setLastTime("充足");// 0.5-1杯
                rv.setLastScore(10);
            } else if ("4".equals(food_milklast)) {
                rv.setLastTime("充足");// 1-2杯
                rv.setLastScore(10);
            } else if ("5".equals(food_milklast) && "5".equals(food_dairylast)) {
                rv.setLastTime("过量");// 2杯以上
                rv.setLastScore(3);
            } else {
                rv.setLastTime("充足");// 1-2杯
                rv.setLastScore(10);
            }
            rlist.add(rv);

            vojo.setColumnName("tfa");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("反式脂肪酸状况");
            rv.setChanges(jsonObject.getString("change_value"));

            rv.setIdealLevel("不吃");
            String food_sweet = JsonPath.read(document, "$.food_sweet") + "";
            String food_sugary = JsonPath.read(document, "$.food_sugary") + "";
            String food_sweetNew = "";
            if (Integer.valueOf(food_sweet) > Integer.valueOf(food_sugary)) {
                food_sweetNew = food_sweet;
            } else {
                food_sweetNew = food_sugary;
            }
            if ("1".equals(food_sweetNew)) {
                rv.setThisTime("不吃");
                rv.setThisScore(10);
            }
            if ("2".equals(food_sweetNew)) {
                rv.setThisTime("适量");
                rv.setThisScore(5);
            }
            if ("3".equals(food_sweetNew)) {
                rv.setThisTime("过量");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }
            if ("4".equals(food_sweetNew)) {
                rv.setThisTime("过量");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }

            String food_sweetlast = JsonPath.read(lastdocument, "$.food_sweet") + "";
            String food_sugarylast = JsonPath.read(lastdocument, "$.food_sugary") + "";
            String food_sweetlastNew = "";
            if (Integer.valueOf(food_sweetlast) > Integer.valueOf(food_sugarylast)) {
                food_sweetlastNew = food_sweetlast;
            } else {
                food_sweetlastNew = food_sugarylast;
            }
            if ("1".equals(food_sweetlastNew)) {
                rv.setLastTime("不吃");
                rv.setLastScore(10);
            }
            if ("2".equals(food_sweetlastNew)) {
                rv.setLastTime("适量");
                rv.setLastScore(5);
            }
            if ("3".equals(food_sweetlastNew)) {
                rv.setLastTime("过量");
                rv.setLastScore(0);
            }
            if ("4".equals(food_sweetlastNew)) {
                rv.setLastTime("过量");
                rv.setLastScore(0);
            }
            rlist.add(rv);

            vojo.setColumnName("psy_nervous");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("精神压力");
            rv.setChanges(jsonObject.getString("change_value"));

            rv.setIdealLevel("没有");
            String psy_nervous = JsonPath.read(document, "$.psy_nervous") + "";
            if ("1".equals(psy_nervous)) {
                rv.setThisTime("没有");
                rv.setThisScore(10);
            }
            if ("2".equals(psy_nervous)) {
                rv.setThisTime("偶尔");
                rv.setThisScore(5);
            }
            if ("3".equals(psy_nervous)) {
                rv.setThisTime("经常");
                rv.setThisScore(0);
                rv.setThisTimeColor("1");
            }

            String psy_nervouslast = JsonPath.read(lastdocument, "$.psy_nervous") + "";
            if ("1".equals(psy_nervouslast)) {
                rv.setLastTime("没有");
                rv.setLastScore(10);
            }
            if ("2".equals(psy_nervouslast)) {
                rv.setLastTime("偶尔");
                rv.setLastScore(5);
            }
            if ("3".equals(psy_nervouslast)) {
                rv.setLastTime("经常");
                rv.setLastScore(0);
            }

            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("睡眠状况");
            rv.setIdealLevel("良好");
            String bedtime = JsonPath.read(document, "$.bedtime") + "";
            if ("1".equals(bedtime)) {
                rv.setThisTime("一般");
                rv.setThisScore(5);
            }
            if ("2".equals(bedtime)) {
                rv.setThisTime("良好");
                rv.setThisScore(10);
            }
            if ("3".equals(bedtime)) {
                rv.setThisTime("差");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }

            String bedtimelast = JsonPath.read(lastdocument, "$.bedtime") + "";
            if ("1".equals(bedtimelast)) {
                rv.setLastTime("一般");
                rv.setLastScore(5);
            }
            if ("2".equals(bedtimelast)) {
                rv.setLastTime("良好");
                rv.setLastScore(10);
            }
            if ("3".equals(bedtimelast)) {
                rv.setLastTime("差");
                rv.setLastScore(0);
            }
            if ("1".equals(bedtime)) {
                if ("1".equals(bedtimelast)) {
                    rv.setChanges("无改善");
                }
                if ("2".equals(bedtimelast)) {
                    rv.setChanges("转好");
                }
                if ("3".equals(bedtimelast)) {
                    rv.setChanges("恶化");
                }
            }
            if ("2".equals(bedtime)) {
                if ("1".equals(bedtimelast)) {
                    rv.setChanges("恶化");
                }
                if ("2".equals(bedtimelast)) {
                    rv.setChanges("无改善");
                }
                if ("3".equals(bedtimelast)) {
                    rv.setChanges("恶化");
                }
            }
            if ("3".equals(bedtime)) {
                if ("1".equals(bedtimelast)) {
                    rv.setChanges("恶化");
                }
                if ("2".equals(bedtimelast)) {
                    rv.setChanges("转好");
                }
                if ("3".equals(bedtimelast)) {
                    rv.setChanges("无改善");
                }
            }
            rlist.add(rv);

            return rlist;
        } else {


            int thisNum = 0;
            int lastNum = 0;


            List<RiskFactorsVOJO> rlist = new ArrayList<RiskFactorsVOJO>();
            YhDiseaseRiskResultAppVOJO vojo = new YhDiseaseRiskResultAppVOJO();
            vojo.setColumnName("BMI");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            String returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            RiskFactorsVOJO rv = new RiskFactorsVOJO();
            JSONObject jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("体重指数(BMI)");
            rv.setIdealLevel("18.5≤BMI<24");
            String now_value = jsonObject.getString("now_value");
            rv.setThisTime(now_value);
            // 颜色表示
            if (!(Double.valueOf(now_value) >= 18.5 && Double.valueOf(now_value) < 24)) {
                rv.setThisTimeColor("1");
            }
            // 本次得分
            if (Double.valueOf(now_value) < 18.5) {
                rv.setThisScore(5);
            } else if (Double.valueOf(now_value) >= 18.5 && Double.valueOf(now_value) < 24) {
                rv.setThisScore(10);
            } else if (Double.valueOf(now_value) >= 24 && Double.valueOf(now_value) < 28) {
                rv.setThisScore(3);
            } else {
                rv.setThisScore(0);
            }
		/*	String last_value = jsonObject.getString("last_value");
			if (Double.valueOf(last_value) < 18.5) {
				rv.setLastScore(5);
			} else if (Double.valueOf(last_value) >= 18.5 && Double.valueOf(last_value) < 24) {
				rv.setLastScore(10);
			} else if (Double.valueOf(last_value) >= 24 && Double.valueOf(last_value) < 28) {
				rv.setLastScore(3);
			} else {
				rv.setLastScore(0);
			}*/

            rlist.add(rv);

            vojo.setColumnName("sport");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("体力活动水平");

            String sport_in = JsonPath.read(document, "$.sport_in") + "";


            if (sport_in.equals("1")) {
                // 参加过
                String sport_freq = JsonPath.read(document, "$.sport_freq") + "";
                if (sport_freq.equals("1") || sport_freq.equals("2")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(0);
                    rv.setThisTimeColor("1");
                }

                if (sport_freq.equals("3")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(5);
                    rv.setThisTimeColor("1");
                }
                if (sport_freq.equals("4")) {
                    rv.setThisTime("不足");
                    rv.setThisScore(10);
                    rv.setThisTimeColor("1");
                }
                if (sport_freq.equals("5")) {
                    rv.setThisTime("充足");
                    rv.setThisScore(20);
                }
            } else {
                // 没参加过
                rv.setThisTime("不足");
                rv.setThisScore(0);
                rv.setThisTimeColor("1");
            }

            rv.setIdealLevel("每周5次以上运动，每次不少于30分钟");
            rlist.add(rv);

            vojo.setColumnName("smoke");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("吸烟状况");

            rv.setIdealLevel("不吸烟/戒烟");
            String smoke_status = JsonPath.read(document, "$.smoke_status") + "";
            if (smoke_status.equals("1")) {
                // 吸烟
                rv.setLastTime("吸烟");
                rv.setThisTimeColor("1");
                String smoke_amount = JsonPath.read(document, "$.smoke_amount") + "";
                if (Integer.valueOf(smoke_amount) < 10) {
                    rv.setThisScore(3);
                }
                if (Integer.valueOf(smoke_amount) >= 10 && Integer.valueOf(smoke_amount) <= 40) {
                    rv.setThisScore(1);
                }
                if (Integer.valueOf(smoke_amount) > 40) {
                    rv.setThisScore(0);
                }

            } else if (smoke_status.equals("2")) {
                // 不吸烟
                rv.setThisTime("您不吸烟");
                rv.setThisScore(10);
            } else {
                String smoke_end_age = JsonPath.read(document, "$.smoke_end_age") + "";
                String age = JsonPath.read(document, "$.person.age") + "";
                if (StringUtils.isNotEmpty(smoke_end_age)) {
                    if (Integer.valueOf(age) - Integer.valueOf(smoke_end_age) > 1) {
                        rv.setThisScore(10);
                    } else {
                        rv.setThisScore(3);
                    }
                } else {
                    rv.setThisScore(10);
                }


            }

            rlist.add(rv);

            vojo.setColumnName("drink");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("饮酒状况");

            String food_drink = JsonPath.read(document, "$.food_drink") + "";
            rv.setIdealLevel("不饮酒");
            if (food_drink.equals("2")) {
                // 喝酒
                rv.setThisTime("您不喝酒");
                rv.setThisScore(5);
            } else {
                // 不喝酒
                String food_drink_times = JsonPath.read(document, "$.food_drink_times") + "";
                String food_drink_beer = JsonPath.read(document, "$.food_drink_beer") + "";
                if (food_drink_times.equals("1") && (food_drink_beer.equals("1") || food_drink_beer.equals("2"))) {
                    rv.setThisTime("您饮酒适量");
                    rv.setThisScore(2);
                } else {
                    rv.setThisTime("您饮酒过量");
                    rv.setThisScore(0);
                    rv.setThisTimeColor("1");
                }
            }
            rv.setIdealLevel("不饮酒");
            rlist.add(rv);

            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("蔬菜摄入状况");
            rv.setIdealLevel("适量(300～500克/天)");
            String food_vegetables = JsonPath.read(document, "$.food_vegetables") + "";
            if ("1".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(2);
            } else if ("4".equals(food_vegetables)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(5);
            } else if ("5".equals(food_vegetables)) {
                rv.setThisTime("充足");

                rv.setThisScore(10);
            }


            rlist.add(rv);

            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("水果摄入状况");
            rv.setIdealLevel("适量(200～350克/天)");
            String food_fruits = JsonPath.read(document, "$.food_fruits") + "";
            if ("1".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(2);
            } else if ("4".equals(food_fruits)) {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
                rv.setThisScore(5);
            } else if ("5".equals(food_fruits)) {
                rv.setThisTime("充足");
                rv.setThisScore(10);
            }


            rlist.add(rv);

            vojo.setColumnName("milk");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("乳制品摄入状况");

            rv.setIdealLevel("适量(300克/天)");
            String food_milk = JsonPath.read(document, "$.food_milk") + "";
            String food_dairy = JsonPath.read(document, "$.food_dairy") + "";
            if ("1".equals(food_milk)) {
                rv.setThisTime("不足");// 不喝
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("2".equals(food_milk)) {
                rv.setThisTime("不足");// 少于0.5杯
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            } else if ("3".equals(food_milk)) {
                rv.setThisTime("充足");// 0.5-1杯
                rv.setThisScore(10);
            } else if ("4".equals(food_milk)) {
                rv.setThisTime("充足");// 1-2杯
                rv.setThisScore(10);
            } else if ("5".equals(food_milk) && "5".equals(food_dairy)) {
                rv.setThisTime("过量");// 2杯以上
                rv.setThisTimeColor("1");
                rv.setThisScore(3);
            } else {
                rv.setThisTime("充足");// 1-2杯
                rv.setThisScore(10);
            }


            rlist.add(rv);

            vojo.setColumnName("tfa");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("反式脂肪酸状况");

            rv.setIdealLevel("不吃");
            String food_sweet = JsonPath.read(document, "$.food_sweet") + "";
            String food_sugary = JsonPath.read(document, "$.food_sugary") + "";
            String food_sweetNew = "";
            if (Integer.valueOf(food_sweet) > Integer.valueOf(food_sugary)) {
                food_sweetNew = food_sweet;
            } else {
                food_sweetNew = food_sugary;
            }
            if ("1".equals(food_sweetNew)) {
                rv.setThisTime("不吃");
                rv.setThisScore(10);
            }
            if ("2".equals(food_sweetNew)) {
                rv.setThisTime("适量");
                rv.setThisScore(5);
            }
            if ("3".equals(food_sweetNew)) {
                rv.setThisTime("过量");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }
            if ("4".equals(food_sweetNew)) {
                rv.setThisTime("过量");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }


            rlist.add(rv);

            vojo.setColumnName("psy_nervous");
            vojo.setDid(String.valueOf(0));
            vojo.setRs(rs);
            returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
            rv = new RiskFactorsVOJO();
            jsonObject = JSONObject.parseObject(returnStr);
            rv.setRiskFactors("精神压力");

            rv.setIdealLevel("没有");
            String psy_nervous = JsonPath.read(document, "$.psy_nervous") + "";
            if ("1".equals(psy_nervous)) {
                rv.setThisTime("没有");
                rv.setThisScore(10);
            }
            if ("2".equals(psy_nervous)) {
                rv.setThisTime("偶尔");
                rv.setThisScore(5);
            }
            if ("3".equals(psy_nervous)) {
                rv.setThisTime("经常");
                rv.setThisScore(0);
                rv.setThisTimeColor("1");
            }


            rv = new RiskFactorsVOJO();
            rv.setRiskFactors("睡眠状况");
            rv.setIdealLevel("良好");
            String bedtime = JsonPath.read(document, "$.bedtime") + "";
            if ("1".equals(bedtime)) {
                rv.setThisTime("一般");
                rv.setThisScore(5);
            }
            if ("2".equals(bedtime)) {
                rv.setThisTime("良好");
                rv.setThisScore(10);
            }
            if ("3".equals(bedtime)) {
                rv.setThisTime("差");
                rv.setThisTimeColor("1");
                rv.setThisScore(0);
            }


            rlist.add(rv);

            return rlist;

        }
    }
    public YhUserAssessmentReportVOJO getAssessVOJO(YhQuestionCheckJson2Web selectByRsLimit1, String rs, Object document, Model model, String lastTime, JSONObject jsonObject, String sex) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        int metabolize = 0;

        YhUserAssessmentReportVOJO vojo = new YhUserAssessmentReportVOJO();
        //医学健康指标 异常
        String height = JsonPath.read(document, "$.height") + "";
        String weight = JsonPath.read(document, "$.weight") + "";
        String serumCreatinine = JsonPath.read(document, "$.serum_creatinine") + "";

        String age2 = JsonPath.read(document, "$.person.age") + "";
        String sexStr = JsonPath.read(document, "$.person.sex") + "";
        YhUserReginfo userReginfo = yhUserReginfoService.selectByPrimaryKey(selectByRsLimit1.getUserCode());
        vojo.setAge(age2);
        vojo.setName(userReginfo.getName());
        vojo.setSex(sexStr.equals("1") ? "男" : "女");
        String s = yhUserReginfoService.selectOrgName(selectByRsLimit1.getUserCode());
        vojo.setUnit(s);

        YhDiseaseRiskResultAppVOJO findAssessResultInfo = findAssessResultInfo(rs);
        //家族史
        List<YhUserAssessmentReportVOJO> mainRiskFactors = mainRiskFactors(rs, document);
        boolean flagMlist = false;
        if (null != mainRiskFactors) {
            for (int i = 0; i < mainRiskFactors.size(); i++) {
                YhUserAssessmentReportVOJO mList = mainRiskFactors.get(i);
                String riskFactors = mList.getRiskFactors();

                if (StringUtil.isNotEmpty(riskFactors)) {
                    flagMlist = true;
                }

            }
        }
        if (flagMlist) {
            model.addAttribute("mainRiskFactors", mainRiskFactors);
        } else {
            model.addAttribute("mainRiskFactors", null);
        }


        //获取String类型的时间
        String createdate = sdf2.format(selectByRsLimit1.getUserCheckTime());
        vojo.setPinggutime(createdate);
        //血糖
        String fbg = jsonObject.getString("fbg");
        if (Double.valueOf(fbg) >= 6.1) {
            metabolize++;
        }
        vojo.setBs(fbg);
        //收缩压
        String sbp = jsonObject.getString("sbp");
        //舒张压
        String dbp = jsonObject.getString("dbp");
        if (StringUtil.isNotEmpty(sbp) && StringUtil.isNotEmpty(dbp)) {
            vojo.setSbp(sbp);
            vojo.setDbp(dbp);
            if (Double.valueOf(sbp) >= 140 || Double.valueOf(dbp) >= 90) {
                metabolize++;
            }
        }
        //腰围
        String waist = jsonObject.getString("waist");
        vojo.setTheWaist(waist);
        //身高
        //体重
        if (StringUtil.isNotEmpty(height) && StringUtil.isNotEmpty(weight)) {
            Double heightOf = Double.valueOf(height);
            Double weightOf = Double.valueOf(weight);
            Double bmi = weightOf / ((heightOf / 100) * (heightOf / 100));
            String format = String.format("%.1f", bmi);

            if (Double.valueOf(format) >= 25) {
                metabolize++;
            }
            vojo.setBmi(format);
        }
        //总胆固醇
        String cholesterol = jsonObject.getString("cholesterol");
        vojo.setTc(cholesterol);
        //甘油三酯
        String tg = jsonObject.getString("tg");
        vojo.setTg(tg);
        //高密度脂蛋白
        String hdlc = jsonObject.getString("hdlc");
        vojo.setHdlc(hdlc);
        //低密度脂蛋白胆固醇
        String ldlc = jsonObject.getString("ldlc");
        vojo.setLdlc(ldlc);
        if(StringUtils.isNotEmpty(tg)&&StringUtils.isNotEmpty(hdlc)){
            if ("1".equals(sex)) {
                if (Double.valueOf(tg) >= 1.7 || Double.valueOf(hdlc) < 0.9) {
                    metabolize++;
                }
            } else {
                if (Double.valueOf(tg) >= 1.7 || Double.valueOf(hdlc) < 1) {
                    metabolize++;
                }
            }
        }

        if (null != findAssessResultInfo) {
            //膳食结构
            vojo.setB4("您的膳食结果基本合理".equals(lastTime) ? "合理" : "不合理");

            //体力活动
            String sport = findAssessResultInfo.getSport();
            if (StringUtil.isNotEmpty(sport)) {
                JSONObject sportobj = JSONObject.parseObject(sport);
                //0：不充足1：充足
                String sportStr = sportobj.getString("now_value");
                if (StringUtil.isNotEmpty(sportStr)) {
                    vojo.setSport(sportStr.equals("1") ? "充足" : "不充足");
                }
            }
        }


        //吸烟
        String smoke_status = jsonObject.getString("smoke_status");
        if (StringUtil.isNotEmpty(smoke_status)) {
            if (smoke_status.equals("1")) {
                vojo.setSmoke("吸烟");
            }
            if (smoke_status.equals("2")) {
                vojo.setSmoke("不吸烟");
            }
            if (smoke_status.equals("3")) {
                vojo.setSmoke("已戒烟");
            }

        }

        //饮酒
        String food_drink = jsonObject.getString("food_drink");
        if (StringUtil.isNotEmpty(food_drink)) {
            if (food_drink.equals("1")) {
                vojo.setFoodDrink("喝酒");
            }
            if (food_drink.equals("2")) {
                vojo.setFoodDrink("不喝");
            }


        }
        //T值
        String t_score = jsonObject.getString("t_score");
        vojo.setT_score(t_score);
        //血尿酸
        String bua = jsonObject.getString("bua");

        if (StringUtil.isNotEmpty(bua)) {

            vojo.setBua(bua);

        } else {
            vojo.setBua("--");
        }
        if (StringUtil.isNotEmpty(serumCreatinine)) {
            vojo.setSerumCreatinine(serumCreatinine);
        } else {
            vojo.setSerumCreatinine("--");
        }

        model.addAttribute("vojo", vojo);
        //代谢综合征判断
        if (metabolize == 0) {
            //低危
            model.addAttribute("metabolizeNum", 375);
        }
        if (metabolize == 1 || metabolize == 2) {
            //高危
            model.addAttribute("metabolizeNum", 0);
        }
        if (metabolize > 2) {
            //现患
            model.addAttribute("metabolizeNum", -375);
        }

        return vojo;
    }

    public RiskFactorsVOJO getMetabolizeInfo(String xpath, String rs, String name, String sex) {
        YhDiseaseRiskResultAppVOJO vojo = new YhDiseaseRiskResultAppVOJO();
        String diseaseid = null;
        vojo.setColumnName(xpath);
        vojo.setDid(String.valueOf(0));
        vojo.setRs(rs);
        String returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
        RiskFactorsVOJO rv = new RiskFactorsVOJO();
        JSONObject jsonObject = JSONObject.parseObject(returnStr);
        rv.setRiskFactors(name);
        rv.setThisTime(jsonObject.getString("now_value"));
        rv.setLastTime(jsonObject.getString("last_value"));
        rv.setChanges(jsonObject.getString("change_value"));

        rv.setIdealLevel(jsonObject.getString("Ideal_value"));
        if ("甘油三脂".equals(name) || "甘油三酯".equals(name)) {
            rv.setIdealLevel("<1.70mmol/L");
            diseaseid = "6";
        }
        if ("糖尿病病史".equals(name)) {
            rv.setThisTime("1".equals(jsonObject.getString("now_value")) ? "有" : "无");
            rv.setLastTime("1".equals(jsonObject.getString("last_value")) ? "有" : "无");
        }
        if ("高血压病史".equals(name)) {
            rv.setThisTime("1".equals(jsonObject.getString("now_value")) ? "有" : "无");
            rv.setLastTime("1".equals(jsonObject.getString("last_value")) ? "有" : "无");
        }

        rv.setThisTimeColor(exceedingTheStandard(xpath, jsonObject.getString("now_value"), sex,
                diseaseid));
        rv.setLastTimeColor(exceedingTheStandard(xpath, jsonObject.getString("last_value"), sex,
                diseaseid));
        if ("舒张压".equals(name)) {
            rv.setIdealLevel("<90mmHg");
            rv.setThisTimeColor(exceedingTheStandard(xpath, jsonObject.getString("now_value"), sex,
                    "4"));
            rv.setLastTimeColor(exceedingTheStandard(xpath, jsonObject.getString("last_value"), sex,
                    "4"));
        }
        if ("高密度脂蛋白胆固醇".equals(name)) {
            if ("1".equals(sex)) {
                rv.setIdealLevel("≥0.9mmol/L");
            } else {
                rv.setIdealLevel("≥1.0mmol/L");
            }

        }


        return rv;
    }

    public RiskFactorsVOJO getRiskInfo(String xpath, String rs, String name) {
        YhDiseaseRiskResultAppVOJO vojo = new YhDiseaseRiskResultAppVOJO();
        vojo.setColumnName(xpath);
        vojo.setDid(String.valueOf(0));
        vojo.setRs(rs);
        String returnStr = yhDiseaseAssessReportMapper.findDiseaseRiskResultByRsForResultString(vojo);
        RiskFactorsVOJO rv = new RiskFactorsVOJO();
        JSONObject jsonObject = JSONObject.parseObject(returnStr);
        rv.setRiskFactors(name);

        rv.setChanges(jsonObject.getString("change_value"));
        rv.setIdealLevel(jsonObject.getString("Ideal_value"));
        if ("饮酒".equals(name)) {
            if (jsonObject.getString("now_value").equals("0")) {
                rv.setThisTime("几乎不喝");
            } else if (jsonObject.getString("now_value").equals("1")) {
                rv.setThisTime("偶尔");
                rv.setThisTimeColor("1");
            } else {
                rv.setThisTime("酗酒");
                rv.setThisTimeColor("1");
            }
            if (jsonObject.getString("last_value").equals("0")) {
                rv.setLastTime("几乎不喝");
            } else if (jsonObject.getString("last_value").equals("1")) {
                rv.setLastTime("偶尔");
                rv.setLastTimeColor("1");
            } else {
                rv.setLastTime("酗酒");
                rv.setLastTimeColor("1");
            }
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
                rv.setLastTimeColor("0");
            }
        }
        if ("高脂饮食".equals(name)) {
            if (jsonObject.getString("now_value").equals("0")) {
                rv.setThisTime("无");
            } else {
                rv.setThisTime("是");
                rv.setThisTimeColor("1");
            }

            if (jsonObject.getString("last_value").equals("0")) {
                rv.setLastTime("无");
            } else {
                rv.setLastTime("是");
                rv.setLastTimeColor("1");
            }
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
                rv.setLastTimeColor("0");
            }
        }

        if ("反式脂肪酸摄入".equals(name)) {
            if (jsonObject.getString("now_value").equals("0")) {
                rv.setThisTime("适量");
            } else {
                rv.setThisTime("过量");
                rv.setThisTimeColor("1");
            }

            if (jsonObject.getString("last_value").equals("0")) {
                rv.setLastTime("适量");
            } else {
                rv.setLastTime("过量");
                rv.setLastTimeColor("1");
            }
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
                rv.setLastTimeColor("0");
            }
        }

        if ("蔬菜水果摄入".equals(name)) {
            if (jsonObject.getString("now_value").equals("0")) {
                rv.setThisTime("充足");
            } else {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
            }

            if (jsonObject.getString("last_value").equals("0")) {
                rv.setLastTime("充足");
            } else {
                rv.setLastTime("不足");
                rv.setLastTimeColor("1");
            }
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
                rv.setLastTimeColor("0");
            }
        }

        if ("体力活动水平".equals(name)) {
            if (jsonObject.getString("now_value").equals("1")) {
                rv.setThisTime("充足");
            } else {
                rv.setThisTime("不足");
                rv.setThisTimeColor("1");
            }

            if (jsonObject.getString("last_value").equals("1")) {
                rv.setLastTime("充足");
            } else {
                rv.setLastTime("不足");
                rv.setLastTimeColor("1");
            }
            if (jsonObject.getString("last_value").equals("-") || jsonObject.getString("last_value").equals("")) {
                rv.setLastTime("-");
                rv.setLastTimeColor("0");
            }
        }


        return rv;
    }

    public List<YhUserAssessmentReportVOJO> suggestions(String rs, Object document) {
        YhDiseaseRiskResult2Web selectByPrimaryByRs = YhDiseaseRiskResult2WebMapper.selectByPrimaryByRs(rs);
        if (null == selectByPrimaryByRs) {
            return null;
        }
        List<YhUserAssessmentReportVOJO> vlist = new ArrayList<>();
        YhUserAssessmentReportVOJO vojo = new YhUserAssessmentReportVOJO();
        String food = selectByPrimaryByRs.getFood();
        String b4 = selectByPrimaryByRs.getB4();
        String tfa = selectByPrimaryByRs.getTfa();
        String milk = selectByPrimaryByRs.getMilk();
        String foodPurine = selectByPrimaryByRs.getFoodPurine();
        JSONObject foodJson = JSONObject.parseObject(food);
        JSONObject b4Json = JSONObject.parseObject(b4);
        JSONObject tfaJson = JSONObject.parseObject(tfa);
        JSONObject milkJson = JSONObject.parseObject(milk);
        JSONObject foodPurineJson = JSONObject.parseObject(foodPurine);
        String food_pickled = JsonPath.read(document, "$.food_pickled") + "";

        if (foodJson.getString("now_value").equals("1") && b4Json.getString("now_value").equals("0")
                && tfaJson.getString("now_value").equals("0") && milkJson.getString("now_value").equals("1")
                && foodPurineJson.getString("now_value").equals("0") && Integer.valueOf(food_pickled) >= 2) {

        } else {
            vojo.setThisTime("饮食");
            vojo.setProposal(
                    "膳食有指南，健康不再难。膳食结构与健康有着密切关系，膳食结构的改善可使总死亡率(全因死亡)下降10%。膳食结构不合理是导致糖尿病、高血压、高脂血症等慢性病的主要危险因素，有意识的控制饮食结构和摄入量，对健康益处多多。您可以参考后文的膳食指南或者个人端的饮食推荐方案，更加科学的改变膳食习惯。");
            vlist.add(vojo);
        }

        vojo = new YhUserAssessmentReportVOJO();
        String sport_in = JsonPath.read(document, "$.sport_in") + "";
        //参加过
        if (sport_in.equals("1")) {
            //次数
            String sport_freq = JsonPath.read(document, "$.sport_freq") + "";
            String sport_time = JsonPath.read(document, "$.sport_time") + "";
            if (!"4".equals(sport_freq) && "1".equals(sport_time)) {
                vojo.setThisTime("运动");
                vojo.setProposal(
                        "需要提高体力活动水平，运动是有效预防慢性疾病的重要途径，包括高血、高血脂症、冠心病、糖尿病、骨质疏松等。科研报告表示，每周7天的30分钟运动，可以让寿命延长 5岁，每周3天的30分钟运动，可以让寿命延长3岁，少锻炼或者不锻炼会让寿命缩减 1岁。您可以参考后文的运动指南或者个人端的运动推荐方案来选择自己的运动方式，养成规律运动的习惯。");
                vlist.add(vojo);
            }
        } else {
            vojo.setThisTime("运动");
            vojo.setProposal(
                    "需要提高体力活动水平，运动是有效预防慢性疾病的重要途径，包括高血、高血脂症、冠心病、糖尿病、骨质疏松等。科研报告表示，每周7天的30分钟运动，可以让寿命延长 5岁，每周3天的30分钟运动，可以让寿命延长3岁，少锻炼或者不锻炼会让寿命缩减 1岁。您可以参考后文的运动指南或者个人端的运动推荐方案来选择自己的运动方式，养成规律运动的习惯。");
            vlist.add(vojo);
        }

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setThisTime("吸烟");
        String smoke_status = JsonPath.read(document, "$.smoke_status") + "";
        if (smoke_status.equals("1")) {
            // 吸烟
            vojo.setProposal(
                    "吸烟对健康的危害无需强调，无论吸烟有多厉害，戒烟都能马上改善您的健康情况。日常生活中，也要尽量避免被动吸烟。根据美国科学家珀尔的科研报告，在基数寿命的基础上，少量吸烟会减少寿命0.5岁 ，若每天吸10支减少寿命5岁，20支减少寿命10岁，40支以上减少寿命高达15岁。");
            vlist.add(vojo);
        } else if (smoke_status.equals("2")) {
            // 不吸烟
        } else {
            String smoke_sencod = JsonPath.read(document, "$.smoke_sencod") + "";
            if (smoke_sencod.equals("1")) {
                vojo.setProposal(
                        "吸烟对健康的危害无需强调，无论吸烟有多厉害，戒烟都能马上改善您的健康情况。日常生活中，也要尽量避免被动吸烟。根据美国科学家珀尔的科研报告，在基数寿命的基础上，少量吸烟会减少寿命0.5岁 ，若每天吸10支减少寿命5岁，20支减少寿命10岁，40支以上减少寿命高达15岁。");
                vlist.add(vojo);
            } else {
            }
        }

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setThisTime("饮酒");
        String food_drink = JsonPath.read(document, "$.food_drink") + "";
        if (food_drink.equals("1")) {
            // 喝酒
            vojo.setProposal(
                    "喝酒是绝对有害的，酒精对肝脏、胃部、神经系统等均有损伤，摄入10克酒精（一小杯红酒）出现健康问题的风险上升0.5%，第20克和第50克的时候风险会暴涨到7%和37%。戒酒需要依赖强有力的主观意愿，实在戒不掉，可以考虑专业实践戒酒的机构，通过外力和内力相结合达成目标。");
            vlist.add(vojo);

        }

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setThisTime("精神压力");
        String psy_nervous = JsonPath.read(document, "$.psy_nervous") + "";

        if (psy_nervous.equals("2")) {
            vojo.setProposal(
                    "有精神压力要及时缓解，要学会劳逸结合，工作压力大，但是身体健康才是最重要的。掌握调节压力的方法尤其重要，放松自己紧张的精神状态，可以选择散步，爬山等运动方式，来转移注意力。或者听听音乐，对于压力的环节可以起到一定的作用。还可以选择合适的对象进行倾诉，释放一下。");
            vlist.add(vojo);
        }
        if (psy_nervous.equals("3")) {
            vojo.setProposal(
                    "有精神压力要及时缓解，要学会劳逸结合，工作压力大，但是身体健康才是最重要的。掌握调节压力的方法尤其重要，放松自己紧张的精神状态，可以选择散步，爬山等运动方式，来转移注意力。或者听听音乐，对于压力的环节可以起到一定的作用。还可以选择合适的对象进行倾诉，释放一下。");
            vlist.add(vojo);
        }

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setThisTime("睡眠状况");
        String bedtime = JsonPath.read(document, "$.bedtime") + "";
        if ("3".equals(bedtime)) {
            vojo.setProposal(
                    "长期睡眠不足，容易产生某些健康问题，甚至疾病等，时间久了也容易产生癌变。睡眠是一切精力的源泉，是病患者的灵药，对于脑信息处理、情绪加工、脑疾病的预防具有重要的意义。请重视睡眠，能眠者，能食，能长生。保障充足的睡眠时间，一般不低于6小时，但也不要超过9小时。");
            vlist.add(vojo);
        }

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setThisTime("努力保持");
        if (vlist.size() == 0) {
            vojo.setProposal(
                    "您的生活方式非常优秀，日常生活中要时刻注意科学的饮食、运动和睡眠。控烟控酒，有精神压力即时进行缓解。您可以参考后文的饮食指南和运动指南，以及个人端的饮食和运动推荐方案来微调自己的日常习惯。");
            vlist.add(vojo);
        }

        return vlist;

    }

    // 当前生活方式评估
    public List<YhUserAssessmentReportVOJO> currentLifestyle(String rs, Object document) {
        YhDiseaseRiskResult2Web selectByPrimaryByRs = YhDiseaseRiskResult2WebMapper.selectByPrimaryByRs(rs);
        if (null == selectByPrimaryByRs) {
            return null;
        }
        List<YhUserAssessmentReportVOJO> vlist = new ArrayList<>();
        YhUserAssessmentReportVOJO vojo = new YhUserAssessmentReportVOJO();
        String food = selectByPrimaryByRs.getFood();
        String b4 = selectByPrimaryByRs.getB4();
        String tfa = selectByPrimaryByRs.getTfa();
        String milk = selectByPrimaryByRs.getMilk();
        String foodPurine = selectByPrimaryByRs.getFoodPurine();
        JSONObject foodJson = JSONObject.parseObject(food);
        JSONObject b4Json = JSONObject.parseObject(b4);
        JSONObject tfaJson = JSONObject.parseObject(tfa);
        JSONObject milkJson = JSONObject.parseObject(milk);
        JSONObject foodPurineJson = JSONObject.parseObject(foodPurine);
        String food_pickled = JsonPath.read(document, "$.food_pickled") + "";
        vojo.setThisTime("饮食");
        if (foodJson.getString("now_value").equals("5") && b4Json.getString("now_value").equals("0")
                && tfaJson.getString("now_value").equals("0") && milkJson.getString("now_value").equals("1")
                && foodPurineJson.getString("now_value").equals("0") && Integer.valueOf(food_pickled) < 2) {
            vojo.setLastTime("您的膳食结果基本合理");
        } else {
            String gaishan = "有待改善：";
            if (!foodJson.getString("now_value").equals("5")) {
                gaishan = gaishan + "蔬菜或者水果摄入不足;";
            }
            if (!b4Json.getString("now_value").equals("0")) {
                gaishan = gaishan + "存在高脂饮食;";
            }
            if (!tfaJson.getString("now_value").equals("0")) {
                gaishan = gaishan + "反式脂肪酸摄入过量;";
            }
            if (Integer.valueOf(food_pickled) >= 2) {
                gaishan = gaishan + "咸菜腌渍食品摄入过量;";
            }
            if (!milkJson.getString("now_value").equals("1")) {
                gaishan = gaishan + "乳制品摄入不足;";
            }
            if (!foodPurineJson.getString("now_value").equals("0")) {
                gaishan = gaishan + "存在高嘌呤饮食;";
            }

            vojo.setLastTime(gaishan);
        }
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setThisTime("运动");
        String sport_in = JsonPath.read(document, "$.sport_in") + "";
        if (sport_in.equals("1")) {
            // 参加过
            String sport_freq = JsonPath.read(document, "$.sport_freq") + "";
            if (sport_freq.equals("3") || sport_freq.equals("4")) {
                vojo.setLastTime("您的体力活动水平一般");
            } else if (sport_freq.equals("5")) {
                vojo.setLastTime("您的体力活动水平充足");
            } else {
                vojo.setLastTime("您的体力活动水平不足");
            }
        } else {
            // 没参加过
            vojo.setLastTime("您的体力活动水平不足");
        }
        vlist.add(vojo);
        vojo = new YhUserAssessmentReportVOJO();
        vojo.setThisTime("吸烟");
        String smoke_status = JsonPath.read(document, "$.smoke_status") + "";
        if (smoke_status.equals("1")) {
            // 吸烟
            vojo.setLastTime("您吸烟");
        } else if (smoke_status.equals("2")) {
            // 不吸烟

            vojo.setLastTime("您不吸烟");

        } else {
            String smoke_sencod = JsonPath.read(document, "$.smoke_sencod") + "";
            if (smoke_sencod.equals("1")) {
                vojo.setLastTime("您已戒烟,存在被动吸烟");
            } else {
                vojo.setLastTime("您已戒烟,也无被动吸烟");
            }

        }

        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setThisTime("饮酒");
        String food_drink = JsonPath.read(document, "$.food_drink") + "";
        if (food_drink.equals("1")) {
            // 喝酒
            String food_drink_times = JsonPath.read(document, "$.food_drink_times") + "";
            String food_drink_beer = JsonPath.read(document, "$.food_drink_beer") + "";
            if (food_drink_times.equals("1") && (food_drink_beer.equals("1") || food_drink_beer.equals("2"))) {
                vojo.setLastTime("您饮酒适量");
            } else {
                vojo.setLastTime("您饮酒过量");
            }

        } else {
            // 不喝酒
            vojo.setLastTime("您不喝酒");
        }
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        vojo.setThisTime("精神压力");
        String psy_nervous = JsonPath.read(document, "$.psy_nervous") + "";
        if (psy_nervous.equals("1")) {
            vojo.setLastTime("您几乎没有精神压力");
        }
        if (psy_nervous.equals("2")) {
            vojo.setLastTime("您有一点精神压力");
        }
        if (psy_nervous.equals("3")) {
            vojo.setLastTime("您精神压力较大");
        }
        vlist.add(vojo);

        return vlist;
    }

    public List<YhUserAssessmentReportVOJO> findAssessTotalReportLastInfo(
            YhDiseaseAssessTotalReport2web findAssessTotalReportLastInfo, Object document, String rs) {
        String sexStr = JsonPath.read(document, "$.person.sex") + "";
        if (null == findAssessTotalReportLastInfo) {
            return null;
        }
        List<YhUserAssessmentReportVOJO> vlist = new ArrayList<>();
        YhUserAssessmentReportVOJO vojo = new YhUserAssessmentReportVOJO();
        String lastRs = findAssessTotalReportLastInfo.getRequestSerialnum();
        YhQuestionCheckJson2Web selectByRsLimit1 = YhQuestionCheckJson2WebMapper.selectByRsLimit1(lastRs);
        String findQuestionCheckForCheckAnswerByrs = selectByRsLimit1.getUserCheckAnswer();
        Object lastdocument = null;
        lastdocument = Configuration.defaultConfiguration().jsonProvider().parse(findQuestionCheckForCheckAnswerByrs);
        String weight = JsonPath.read(document, "$.weight") + "";
        String height = JsonPath.read(document, "$.height") + "";
        String weightlast = JsonPath.read(lastdocument, "$.weight") + "";
        vojo.setIndicators("体重");
        if (com.yihe.system.utils.StringUtil.isEmpty(weight)) {
            weight = "0";
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(weightlast)) {
            weightlast = "0";
        }
        if (weight.equals(weightlast)) {
            vojo.setTrend("=");
        }
        if (Double.valueOf(weight) > Double.valueOf(weightlast)) {
            vojo.setTrend("↑");
        }
        if (Double.valueOf(weight) < Double.valueOf(weightlast)) {
            vojo.setTrend("↓");
        }
        vojo.setThisTime(weight);
        vojo.setLastTime(weightlast);
        Double heightOf = Double.valueOf(height);
        double v = ((heightOf / 100) * (heightOf / 100)) * 23.9;
        vojo.setReferenceValue("<" + String.format("%.1f", v) + "kg");
        vlist.add(vojo);
        String waistValue = JsonPath.read(document, "$.waist") + "";
        String waistValuelast = JsonPath.read(lastdocument, "$.waist") + "";
        vojo = new YhUserAssessmentReportVOJO();
        vojo.setIndicators("腰围");
        vojo.setThisTime(waistValue);
        vojo.setLastTime(waistValuelast);

        if (com.yihe.system.utils.StringUtil.isEmpty(waistValuelast)) {
            waistValuelast = "0";
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(waistValue)) {
            waistValue = "0";
        }
        if (sexStr == "1") {
            vojo.setReferenceValue("<85 cm");
            if (Double.valueOf(waistValue) >= 85) {
                vojo.setThisTimeColor("1");
            }
            if (Double.valueOf(waistValuelast) >= 85) {
                vojo.setLastTimeColor("1");
            }
        } else {
            vojo.setReferenceValue("<80 cm");
            if (Double.valueOf(waistValue) >= 80) {
                vojo.setThisTimeColor("1");
            }
            if (Double.valueOf(waistValuelast) >= 80) {
                vojo.setLastTimeColor("1");
            }
        }
        if (waistValue.equals(waistValuelast)) {
            vojo.setTrend("=");
        }
        if (Double.valueOf(waistValue) > Double.valueOf(waistValuelast)) {
            vojo.setTrend("↑");
        }
        if (Double.valueOf(waistValue) < Double.valueOf(waistValuelast)) {
            vojo.setTrend("↓");
        }
        vlist.add(vojo);
        vojo = new YhUserAssessmentReportVOJO();
        String dbp = JsonPath.read(document, "$.dbp") + "";
        String dbplast = JsonPath.read(lastdocument, "$.dbp") + "";
        vojo.setIndicators("舒张压");
        vojo.setThisTime(dbp);
        vojo.setLastTime(dbplast);
        vojo.setReferenceValue("<80 mmHg");
        if (com.yihe.system.utils.StringUtil.isEmpty(dbp)) {
            dbp = "0";
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(dbplast)) {
            dbplast = "0";
        }
        if (Double.valueOf(dbp) >= 80) {
            vojo.setThisTimeColor("1");
        }
        if (Double.valueOf(dbplast) >= 80) {
            vojo.setLastTimeColor("1");
        }
        if (dbp.equals(dbplast)) {
            vojo.setTrend("=");
        }
        if (Double.valueOf(dbp) > Double.valueOf(dbplast)) {
            vojo.setTrend("↑");
        }
        if (Double.valueOf(dbp) < Double.valueOf(dbp)) {
            vojo.setTrend("↓");
        }
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        String sbp = JsonPath.read(document, "$.sbp") + "";
        String sbplast = JsonPath.read(lastdocument, "$.sbp") + "";
        vojo.setIndicators("收缩压");
        vojo.setThisTime(sbp);
        vojo.setLastTime(sbplast);
        vojo.setReferenceValue("<120 mmHg");
        if (com.yihe.system.utils.StringUtil.isEmpty(sbp)) {
            sbp = "0";
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(sbplast)) {
            sbplast = "0";
        }
        if (Double.valueOf(sbp) >= 120) {
            vojo.setThisTimeColor("1");
        }
        if (Double.valueOf(sbplast) >= 120) {
            vojo.setLastTimeColor("1");
        }
        if (sbp.equals(sbplast)) {
            vojo.setTrend("=");
        }
        if (Double.valueOf(sbp) > Double.valueOf(sbplast)) {
            vojo.setTrend("↑");
        }
        if (Double.valueOf(sbp) < Double.valueOf(sbplast)) {
            vojo.setTrend("↓");
        }
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        String fbg = JsonPath.read(document, "$.fbg") + "";
        String fbglast = JsonPath.read(lastdocument, "$.fbg") + "";
        vojo.setIndicators("血糖");
        vojo.setThisTime(fbg);
        vojo.setLastTime(fbglast);
        vojo.setReferenceValue("3.9～6.0 mmol/L");
        if (com.yihe.system.utils.StringUtil.isEmpty(fbg)) {
            fbg = "0";
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(fbglast)) {
            fbglast = "0";
        }
        if (Double.valueOf(fbg) > 6.1 || Double.valueOf(fbg) < 3.9) {
            vojo.setThisTimeColor("1");
        }
        if (Double.valueOf(fbglast) > 6.1 || Double.valueOf(fbglast) < 3.9) {
            vojo.setLastTimeColor("1");
        }
        if (fbg.equals(fbglast)) {
            vojo.setTrend("=");
        }
        if (Double.valueOf(fbg) > Double.valueOf(fbglast)) {
            vojo.setTrend("↑");
        }
        if (Double.valueOf(fbg) < Double.valueOf(fbglast)) {
            vojo.setTrend("↓");
        }
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        String cholesterol = JsonPath.read(document, "$.cholesterol") + "";
        String cholesterollast = JsonPath.read(lastdocument, "$.cholesterol") + "";
        vojo.setIndicators("总胆固醇");
        vojo.setThisTime(cholesterol);
        vojo.setLastTime(cholesterollast);
        vojo.setReferenceValue("2.9~5.17 mmol/l");
        if (com.yihe.system.utils.StringUtil.isEmpty(cholesterollast)) {
            cholesterollast = "0";
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(cholesterol)) {
            cholesterol = "0";
        }
        if (Double.valueOf(cholesterol) > 5.17 || Double.valueOf(cholesterol) < 2.9) {
            vojo.setThisTimeColor("1");
        }
        if (Double.valueOf(cholesterollast) > 5.17 || Double.valueOf(cholesterollast) < 2.9) {
            vojo.setLastTimeColor("1");
        }
        if (cholesterol.equals(cholesterollast)) {
            vojo.setTrend("=");
        }
        if (Double.valueOf(cholesterol) > Double.valueOf(cholesterollast)) {
            vojo.setTrend("↑");
        }
        if (Double.valueOf(cholesterol) < Double.valueOf(cholesterollast)) {
            vojo.setTrend("↓");
        }
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        String tg = JsonPath.read(document, "$.tg") + "";
        String tglast = JsonPath.read(lastdocument, "$.tg") + "";
        vojo.setIndicators("甘油三酯");
        vojo.setThisTime(tg);
        vojo.setLastTime(tglast);
        vojo.setReferenceValue("0.57~1.69 mmol/l");
        if (com.yihe.system.utils.StringUtil.isEmpty(tg)) {
            tg = "0";
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(tglast)) {
            tglast = "0";
        }
        if (Double.valueOf(tg) > 1.69 || Double.valueOf(tg) < 0.57) {
            vojo.setThisTimeColor("1");
        }
        if (Double.valueOf(tglast) > 1.69 || Double.valueOf(tglast) < 0.57) {
            vojo.setLastTimeColor("1");
        }
        if (tg.equals(tglast)) {
            vojo.setTrend("=");
        }
        if (Double.valueOf(tg) > Double.valueOf(tglast)) {
            vojo.setTrend("↑");
        }
        if (Double.valueOf(tg) < Double.valueOf(tglast)) {
            vojo.setTrend("↓");
        }
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        String hdlc = JsonPath.read(document, "$.hdlc") + "";
        String hdlclast = JsonPath.read(lastdocument, "$.hdlc") + "";
        vojo.setIndicators("高密度脂蛋白");
        vojo.setThisTime(hdlc);
        vojo.setLastTime(hdlclast);
        vojo.setReferenceValue("≥1.04 mmol/l");
        if (com.yihe.system.utils.StringUtil.isEmpty(hdlc)) {
            hdlc = "0";
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(hdlclast)) {
            hdlclast = "0";
        }
        if (Double.valueOf(hdlc) < 1.04) {
            vojo.setThisTimeColor("1");
        }
        if (Double.valueOf(hdlclast) < 1.04) {
            vojo.setLastTimeColor("1");
        }
        if (hdlc.equals(hdlclast)) {
            vojo.setTrend("=");
        }
        if (Double.valueOf(hdlc) > Double.valueOf(hdlclast)) {
            vojo.setTrend("↑");
        }
        if (Double.valueOf(hdlc) < Double.valueOf(hdlclast)) {
            vojo.setTrend("↓");
        }
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        String ldlc = JsonPath.read(document, "$.ldlc") + "";
        String ldlclast = JsonPath.read(lastdocument, "$.ldlc") + "";
        vojo.setIndicators("低密度脂蛋白");
        vojo.setThisTime(ldlc);
        vojo.setLastTime(ldlclast);
        vojo.setReferenceValue("0~3.36 mmol/l");
        if (com.yihe.system.utils.StringUtil.isEmpty(ldlc)) {
            ldlc = "0";
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(ldlclast)) {
            ldlclast = "0";
        }
        if (Double.valueOf(ldlc) >= 3.37) {
            vojo.setThisTimeColor("1");
        }
        if (Double.valueOf(ldlclast) >= 3.37) {
            vojo.setLastTimeColor("1");
        }
        if (ldlc.equals(ldlclast)) {
            vojo.setTrend("=");
        }
        if (Double.valueOf(ldlc) > Double.valueOf(ldlclast)) {
            vojo.setTrend("↑");
        }
        if (Double.valueOf(ldlc) < Double.valueOf(ldlclast)) {
            vojo.setTrend("↓");
        }
        vlist.add(vojo);

        vojo = new YhUserAssessmentReportVOJO();
        String t_score = JsonPath.read(document, "$.t_score") + "";
        String t_scorelast = JsonPath.read(lastdocument, "$.t_score") + "";
        vojo.setIndicators("骨密度检查指标-T值");
        vojo.setThisTime(t_score);
        vojo.setLastTime(t_scorelast);
        vojo.setReferenceValue("≥-1");
        if (com.yihe.system.utils.StringUtil.isEmpty(t_score)) {
            t_score = "0";
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(t_scorelast)) {
            t_scorelast = "0";
        }
        if (Double.valueOf(t_score) < -1) {
            vojo.setThisTimeColor("1");
        }
        if (Double.valueOf(t_scorelast) < -1) {
            vojo.setLastTimeColor("1");
        }
        if (t_score.equals(t_scorelast)) {
            vojo.setTrend("=");
        }
        if (Double.valueOf(t_score) > Double.valueOf(t_scorelast)) {
            vojo.setTrend("↑");
        }
        if (Double.valueOf(t_score) < Double.valueOf(t_scorelast)) {
            vojo.setTrend("↓");
        }
        vlist.add(vojo);


        vojo = new YhUserAssessmentReportVOJO();
        String bua = JsonPath.read(document, "$.bua") + "";
        String bualast = JsonPath.read(lastdocument, "$.bua") + "";
        vojo.setIndicators("血尿酸");
        vojo.setThisTime(bua);
        vojo.setLastTime(bualast);


        if (com.yihe.system.utils.StringUtil.isEmpty(bua)) {
            bua = "--";
            vojo.setThisTime(bua);
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(bualast)) {
            bualast = "--";
            vojo.setLastTime(bualast);
        }

        if ("1".equals(sexStr)) {
            vojo.setReferenceValue("210~420μmol/L");
            if (StringUtils.isNotEmpty(bua) && !"--".equals(bua)) {
                if (Double.valueOf(bua) > 420 || Double.valueOf(bua) < 210 && !"0".equals(bua)) {
                    vojo.setThisTimeColor("1");
                }
            }
            if (StringUtils.isNotEmpty(bualast) && !"--".equals(bualast)) {
                if (Double.valueOf(bualast) > 420 || Double.valueOf(bualast) < 210 && !"0".equals(bualast)) {
                    vojo.setLastTimeColor("1");
                }
            }

        } else {
            vojo.setReferenceValue("150~360μmol/L");
            if (StringUtils.isNotEmpty(bua) && !"--".equals(bua)) {
                if (Double.valueOf(bua) > 360 || Double.valueOf(bua) < 150 && !"0".equals(bua)) {
                    vojo.setThisTimeColor("1");
                }
            }
            if (StringUtils.isNotEmpty(bualast) && !"--".equals(bualast)) {
                if (Double.valueOf(bualast) > 360 || Double.valueOf(bualast) < 150 && !"0".equals(bualast)) {
                    vojo.setLastTimeColor("1");
                }
            }

        }


        if (StringUtils.isNotEmpty(bua) && StringUtils.isNotEmpty(bualast) && !"--".equals(bua) && !"--".equals(bualast)) {
            if (bua.equals(bualast)) {
                vojo.setTrend("=");
            }
            if (Double.valueOf(bua) > Double.valueOf(bualast)) {
                vojo.setTrend("↑");
            }
            if (Double.valueOf(bua) < Double.valueOf(bualast)) {
                vojo.setTrend("↓");
            }
        }


        vlist.add(vojo);


        vojo = new YhUserAssessmentReportVOJO();
        String serum_creatinine = JsonPath.read(document, "$.serum_creatinine") + "";
        String serum_creatininelast = JsonPath.read(lastdocument, "$.serum_creatinine") + "";
        vojo.setIndicators("血肌酐");
        vojo.setThisTime(serum_creatinine);
        vojo.setLastTime(serum_creatininelast);


        if (com.yihe.system.utils.StringUtil.isEmpty(serum_creatinine)) {
            serum_creatinine = "--";
            vojo.setThisTime(serum_creatinine);
        }
        if (com.yihe.system.utils.StringUtil.isEmpty(serum_creatininelast)) {
            serum_creatininelast = "--";
            vojo.setLastTime(serum_creatininelast);
        }

        if ("1".equals(sexStr)) {
            vojo.setReferenceValue("54~106μmol/L");
            if (com.yihe.system.utils.StringUtil.isNotEmpty(serum_creatinine) && !"--".equals(serum_creatinine)) {
                if (Double.valueOf(serum_creatinine) > 106 || Double.valueOf(serum_creatinine) < 54 && !"0".equals(serum_creatinine)) {
                    vojo.setThisTimeColor("1");
                }
            }
            if (com.yihe.system.utils.StringUtil.isNotEmpty(serum_creatininelast) && !"--".equals(serum_creatininelast)) {
                if (Double.valueOf(serum_creatininelast) > 106 || Double.valueOf(serum_creatininelast) < 54 && !"0".equals(serum_creatininelast)) {
                    vojo.setLastTimeColor("1");
                }
            }

        } else {
            vojo.setReferenceValue("44~97μmol/L");
            if (com.yihe.system.utils.StringUtil.isNotEmpty(serum_creatinine) && !"--".equals(serum_creatinine)) {
                if (Double.valueOf(serum_creatinine) > 97 || Double.valueOf(serum_creatinine) < 44 && !"0".equals(serum_creatinine)) {
                    vojo.setThisTimeColor("1");
                }
            }
            if (com.yihe.system.utils.StringUtil.isNotEmpty(serum_creatininelast) && !"--".equals(serum_creatininelast)) {
                if (Double.valueOf(serum_creatininelast) > 97 || Double.valueOf(serum_creatininelast) < 44 && !"0".equals(serum_creatininelast)) {
                    vojo.setLastTimeColor("1");
                }
            }

        }

        if (StringUtils.isNotEmpty(serum_creatinine) && StringUtils.isNotEmpty(serum_creatininelast) && !"--".equals(serum_creatinine) && !"--".equals(serum_creatininelast)) {
            if (serum_creatinine.equals(serum_creatininelast)) {
                vojo.setTrend("=");
            }
            if (Double.valueOf(serum_creatinine) > Double.valueOf(serum_creatininelast)) {
                vojo.setTrend("↑");
            }
            if (Double.valueOf(serum_creatinine) < Double.valueOf(serum_creatininelast)) {
                vojo.setTrend("↓");
            }
        }

        vlist.add(vojo);


        return vlist;
    }

    public YhDiseaseAssessTotalReport2web lastRsInfo(String usercode, String time) {
        YhDiseaseAssessTotalReport2web findAssessTotalReportLastInfo = YhDiseaseAssessTotalReport2Mapper
                .findAssessTotalReportLastInfo(usercode, time);
        return findAssessTotalReportLastInfo;
    }

    public Set<String> noGoodList(String rs) {
        Set<String> thisList = new HashSet<>();
        List<YHDiseaseAssessResolve> findAssessResolveInfoByrsInfo = yhDiseaseAssessReportMapper
                .findAssessResolveInfoByrsInfo(rs);
        if (null != findAssessResolveInfoByrsInfo && findAssessResolveInfoByrsInfo.size() > 0) {
            for (YHDiseaseAssessResolve yhDiseaseAssessResolve : findAssessResolveInfoByrsInfo) {
                String type = yhDiseaseAssessResolve.getType();
                if (com.yihe.system.utils.StringUtil.isNotEmpty(type)) {
                    int indexOf = type.indexOf("努力目标");
                    String substring = type.substring(0, indexOf);
                    String replace = substring.replace("目前状况：", "");
                    thisList.add(replace);

                }
            }
        }
        return thisList;
    }


    public YhUserAssessmentReportVOJO findResolveInfo(String rs,
                                                      YhDiseaseAssessTotalReport2web findAssessTotalReportLastInfo) {

        YhUserAssessmentReportVOJO vojo = new YhUserAssessmentReportVOJO();
        // 医学健康指标
        String thisInfo = "";
        List<String> thisList = new ArrayList<>();
        List<YHDiseaseAssessResolve> findAssessResolveInfoByrsInfo = yhDiseaseAssessReportMapper
                .findAssessResolveInfoByrsInfo(rs);
        if (null != findAssessResolveInfoByrsInfo && findAssessResolveInfoByrsInfo.size() > 0) {
            for (YHDiseaseAssessResolve yhDiseaseAssessResolve : findAssessResolveInfoByrsInfo) {
                String type = yhDiseaseAssessResolve.getType();
                if (com.yihe.system.utils.StringUtil.isNotEmpty(type)) {
                    int indexOf = type.indexOf("努力目标");
                    String substring = type.substring(0, indexOf);
                    String replace = substring.replace("目前状况：", "");
                    thisList.add(replace);

                }
            }
        }
        if (thisList.size() > 0) {
            List<String> listTemp = new ArrayList<String>();
            for (int i = 0; i < thisList.size(); i++) {
                if (!listTemp.contains(thisList.get(i))) {
                    listTemp.add(thisList.get(i));
                }
            }
            for (String string : listTemp) {
                thisInfo = thisInfo + string + "<br> ";
            }
            vojo.setThisTime(thisInfo);
        }

        String lastInfo = "";
        List<String> lastList = new ArrayList<>();
        if (null != findAssessTotalReportLastInfo) {
            List<YHDiseaseAssessResolve> findAssessResolveInfoByrsInfolast = yhDiseaseAssessReportMapper
                    .findAssessResolveInfoByrsInfo(findAssessTotalReportLastInfo.getRequestSerialnum());
            if (null != findAssessResolveInfoByrsInfolast && findAssessResolveInfoByrsInfolast.size() > 0) {
                for (YHDiseaseAssessResolve yhDiseaseAssessResolve : findAssessResolveInfoByrsInfolast) {
                    String type = yhDiseaseAssessResolve.getType();
                    if (com.yihe.system.utils.StringUtil.isNotEmpty(type)) {
                        int indexOf = type.indexOf("努力目标");
                        String substring = type.substring(0, indexOf);
                        String replace = substring.replace("目前状况：", "");
                        lastList.add(replace);

                    }
                }
            }
            if (lastList.size() > 0) {
                List<String> listTemp = new ArrayList<String>();
                for (int i = 0; i < lastList.size(); i++) {
                    if (!listTemp.contains(lastList.get(i))) {
                        listTemp.add(lastList.get(i));
                    }
                }
                for (String string : listTemp) {
                    lastInfo = lastInfo + string + "<br> ";
                }
                vojo.setLastTime(lastInfo);
            }
        }


        return vojo;
    }
}