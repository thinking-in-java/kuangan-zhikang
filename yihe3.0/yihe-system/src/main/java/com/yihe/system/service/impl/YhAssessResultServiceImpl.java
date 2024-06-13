package com.yihe.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.common.config.Global;
import com.yihe.common.utils.DateUtils;
import com.yihe.common.utils.RestfulDemo;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.DataExportVojo;
import com.yihe.system.domain.YhAssessConclusion;
import com.yihe.system.domain.YhAssessResult;
import com.yihe.system.domain.YhAssessResultVojo;
import com.yihe.system.mapper.YhAssessConclusionMapper;
import com.yihe.system.mapper.YhAssessResultMapper;
import com.yihe.system.service.YhAssessResultService;
import com.yihe.system.service.YhUserReginfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class YhAssessResultServiceImpl implements YhAssessResultService {
    @Autowired
    private YhAssessResultMapper yhAssessResultMapper;
    @Autowired
    private YhAssessConclusionMapper yhAssessConclusionMapper;
    @Autowired
    private YhUserReginfoService yhUserReginfoService;

    @Override
    public String findByTs(String userCode, String assess) {
        return yhAssessResultMapper.findByTs(userCode,findByAssess(assess));
    }

    @Override
    public boolean submitAssess(String userCode, String assess, String answers) {
        Map<String, Object> map = yhUserReginfoService.findUserInfo(userCode);
        YhAssessResult anxiety = new YhAssessResult();
        String [] answer = answers.substring(1,answers.length()-1).split(",");
        int score = 0;
        for (String s : answer) {
            score += Integer.parseInt(s);
        }
        if("A型性格评估".equals(assess)){
            getByAdis(anxiety, answer);
        }
        if("心理健康综合评估".equals(assess)){
            anxiety.setState(getpsychology(score, answer));
        }else{
            String state = yhAssessConclusionMapper.findByState(score, assess);
            anxiety.setState(state);
        }
        anxiety.setUserCode(userCode);
        anxiety.setScore(score);
        anxiety.setAge((Integer) map.get("age"));
        anxiety.setSex(map.get("sex").toString());
        anxiety.setQtime(new Date());
        anxiety.setAnswer(answers);
        anxiety.setIsfalg(2);
        anxiety.setAssess(findByAssess(assess));
        int count = yhAssessResultMapper.insertSelective(anxiety);
        if (count > 0){
            JSONArray jsonArray = new JSONArray();
            JSONObject postData = new JSONObject();

            JSONObject operationinfo = new JSONObject();
            operationinfo.put("operator", "1");
            postData.put("operationinfo", operationinfo);

            JSONObject mainTable = new JSONObject();
            mainTable.put("xm", map.get("xm"));
            mainTable.put("sjh", map.get("sjh"));
            mainTable.put("pgcs", yhAssessResultMapper.assessCount(userCode, anxiety.getAssess()));
            mainTable.put("zxpgrq", DateUtils.getDate());
            mainTable.put("mc", assess);
            mainTable.put("pgjg", anxiety.getState());
            mainTable.put("pgrq", DateUtils.getDate());
            mainTable.put("pgsj", DateUtils.parseDateToStr("HH:mm", anxiety.getQtime()));

            postData.put("mainTable", mainTable);
            jsonArray.add(postData);
            String s = RestfulDemo.doAction("http://" + Global.getOaIPAddress() + "/api/cube/restful/interface/saveOrUpdateModeData/syncXLPG", jsonArray);
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> findByUser(String userCode) {
        List<Map<String, Object>> mapList = yhAssessResultMapper.findConcent();
        for (Map<String, Object> map : mapList) {
            Integer id = Integer.valueOf(map.get("id").toString());
            YhAssessResult result = yhAssessResultMapper.findByUser(userCode, id);
            if(result != null){
                map.put("isfalg", false);
                map.put("text", result.getState());
                map.put("rusult", calculateTime(result.getQtime()));
                map.put("num", 0);
            }else{
                map.put("isfalg", true);
                map.put("text", "");
                map.put("rusult", "");
                map.put("num", yhAssessResultMapper.assessCount(null, id)+(20/id));
            }
        }
        return mapList;
    }

    @Override
    public YhAssessResult findByAssess(String userCode, String assess) {
        return yhAssessResultMapper.findByAssess(userCode, assess);
    }

    @Override
    public YhAssessConclusion findByConcent(String state, String assess) {
        return yhAssessConclusionMapper.findByConcent(state, assess);
    }

    @Override
    public String findScoreL(Integer scorel) {
        return yhAssessConclusionMapper.findScoreL(scorel);
    }

    @Override
    public int findMaxScore(String assess) {
        return yhAssessConclusionMapper.findMaxScore(assess);
    }

    @Override
    public List<YhAssessConclusion> findBydivisor(String assess) {
        return yhAssessConclusionMapper.findBydivisor(assess);
    }

    @Override
    public List<String> findConcents() {
        return yhAssessResultMapper.findNames();
    }

    @Override
    public String findName(Integer id) {
        return yhAssessResultMapper.findName(id);
    }

    @Override
    public YhAssessResult findById(Integer id) {
        return yhAssessResultMapper.findById(id);
    }

    @Override
    public PageInfo findByList(YhAssessResultVojo record) {
        PageHelper.startPage(record.getPage(), record.getLimit());
        List<YhAssessResultVojo> resultVojos = yhAssessResultMapper.findByList(record);
        PageInfo pageInfo = new PageInfo(resultVojos);
        return pageInfo;
    }

    @Override
    public List<Map<String, Object>> findAssess() {
        return yhAssessResultMapper.findAssess();
    }

    @Override
    public List<Map<String, Object>> findAssessAll() {
        return yhAssessResultMapper.findAssessAll();
    }

    @Override
    public YhAssessResult findAssessInfo(String userCode, Integer assess) {
        return yhAssessResultMapper.findAssessInfo(userCode, assess);
    }

    @Override
    public PageInfo findUserAssList(YhAssessResultVojo record) {
        PageHelper.startPage(record.getPage(), record.getLimit());
        List<YhAssessResult> results = yhAssessResultMapper.findUserAssList(record);
        PageInfo pageInfo = new PageInfo(results);
        return pageInfo;
    }

    @Override
    public List<Map<String, Object>> findAssRes() {
        List<Map<String, Object>> mapList = yhAssessResultMapper.findAssess();
        List<Map<String, Object>> options = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            if(Integer.parseInt(map.get("id").toString())>=1000){
                continue;
            }
            List<Map<String, Object>> twooptions = new ArrayList<>();
            List<YhAssessConclusion> conclusionWebs = yhAssessConclusionMapper.findBydivisor(map.get("name").toString());
            for (YhAssessConclusion conclusionWeb : conclusionWebs) {
                Map<String, Object> twovalue = new HashMap<>();
                twovalue.put("label", conclusionWeb.getState());
                twovalue.put("value", conclusionWeb.getState());
                twovalue.put("children", null);
                twooptions.add(twovalue);
            }
            Map<String, Object> onevalue = new HashMap<>();
            onevalue.put("label", map.get("name").toString());
            onevalue.put("value", map.get("id").toString());
            onevalue.put("children", twooptions);
            options.add(onevalue);
        }
        return options;
    }

    @Override
    public List<YhAssessResult> selPsychologyAssess(DataExportVojo record) {
        return yhAssessResultMapper.selPsychologyAssess(record);
    }

    private int findByAssess(String assess){
        if("焦虑情绪评估".equals(assess)){
            return 1;
        }else if("抑郁情绪评估".equals(assess)){
            return 2;
        }else if("失眠指数评估".equals(assess)){
            return 3;
        }else if("心理健康综合评估".equals(assess)){
            return 4;
        }else if("职业倦怠评估".equals(assess)){
            return 5;
        }else if("压力水平评估".equals(assess)){
            return 6;
        }else if("A型性格评估".equals(assess)){
            return 7;
        }else if("强迫症评估".equals(assess)){
            return 8;
        }else if("躯体症状评估".equals(assess)){
            return 9;
        }else{
            return 15;
        }
    }

    private String calculateTime(Date time) {
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 指定时间格式
        long reset = time.getTime(); // 获取指定时间的毫秒数
        long dateDiff = nowTime - reset;
        if (dateDiff < 0) {
            msg = "输入的时间不对";
        } else {
            long dateTemp1 = dateDiff / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60; // 分钟
            long dateTemp3 = dateTemp2 / 60; // 小时
            long dateTemp4 = dateTemp3 / 24; // 天数
            long dateTemp5 = dateTemp4 / 30; // 月数
            long dateTemp6 = dateTemp5 / 12; // 年数
            if (dateTemp6 > 0) {
                msg = dateTemp6 + "年前";
            } else if (dateTemp5 > 0) {
                msg = dateTemp5 + "个月前";
            } else if (dateTemp4 > 0) {
                msg = dateTemp4 + "天前";
            } else if (dateTemp3 > 0) {
                msg = dateTemp3 + "小时前";
            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";
            } else if (dateTemp1 > 0) {
                msg = "不久前";
            }
        }
        return msg;
    }

    private void getByAdis(YhAssessResult record, String [] answer){
        int[] l = {8, 20, 24, 43, 56, 13, 33, 37, 48, 52};
        int scoreL = 0;
        for (int a : l){
            scoreL += Integer.parseInt(answer[a-1]);
        }
        record.setScoreL(scoreL);
    }

    private String getpsychology(int score, String [] answer){
        int yan = 0;
        int yan2 = 0;
        for (String s : answer) {
            if(Integer.parseInt(s)>0){
                yan++;
            }
        }
        yan2 += getdivisor(new int[]{1, 4, 12, 27, 40, 42, 48, 49, 52, 53, 56, 58}, answer);
        yan2 += getdivisor(new int[]{3, 9, 10, 28, 38, 45, 46, 51, 55, 65}, answer);
        yan2 += getdivisor(new int[]{6, 21, 34, 36, 37, 41, 61, 69, 73}, answer);
        yan2 += getdivisor(new int[]{5, 14, 15, 20, 22, 26, 29, 30, 31, 32, 54, 71, 79}, answer);
        yan2 += getdivisor(new int[]{2, 17, 23, 33, 39, 57, 72, 78, 80, 86}, answer);
        yan2 += getdivisor(new int[]{11, 24, 63, 67, 74, 81}, answer);
        yan2 += getdivisor(new int[]{13, 25, 47, 50, 70, 75, 82}, answer);
        yan2 += getdivisor(new int[]{8, 18, 43, 68, 76, 83}, answer);
        yan2 += getdivisor(new int[]{7, 16, 35, 62, 77, 84, 85, 87, 88, 90}, answer);
        yan2 += getdivisor(new int[]{19, 44, 59, 60, 64, 66, 89}, answer);
        if (score > 160 || yan > 43 || yan2 > 0){
            return "阳性";
        }else{
            return "阴性";
        }
    }

    private int getdivisor(int[] ids, String [] answer){
        int score = 0;
        for (int id : ids) {
            score += Integer.parseInt(answer[id-1]);
        }
        if((score/ids.length) >=3){
            return 1;
        }else{
            return 0;
        }
    }
}
