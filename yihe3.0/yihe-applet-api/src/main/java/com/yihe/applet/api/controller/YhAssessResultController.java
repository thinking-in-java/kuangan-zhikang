package com.yihe.applet.api.controller;

import com.yihe.system.domain.YhAssessConclusion;
import com.yihe.system.domain.YhAssessResult;
import com.yihe.system.domain.YhAssessTopic;
import com.yihe.system.service.YhAssessResultService;
import com.yihe.system.service.YhAssessTopicService;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.common.utils.Arith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assessResult")
public class YhAssessResultController {
    @Autowired
    private YhAssessResultService yhAssessResultService;
    @Autowired
    private YhAssessTopicService yhAssessTopicService;

    @PostMapping("/selectTopic")
    public AjaxResult selectTopic(@RequestParam(value = "userCode") String userCode, @RequestParam(value = "assess") String assess){
        List<YhAssessTopic> topics = yhAssessTopicService.findByAssess(assess);
        String [] answers = null;
        String answer = yhAssessResultService.findByTs(userCode, assess);
        if (answer != null && !"".equals(answer)){
            answers = answer.substring(1,answer.length()-1).split(",");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("answers", answers);
        map.put("topics", topics);
        return AjaxResult.success(map);
    }

    @PostMapping("/submitAssess")
    public AjaxResult submitAssess(@RequestParam(value = "userCode") String userCode,
                                   @RequestParam(value = "assess") String assess,
                                   @RequestParam(value = "answers") String answers){
        boolean falg = yhAssessResultService.submitAssess(userCode, assess, answers);
        return AjaxResult.success(falg);
    }

    @PostMapping("/assessConcent")
    public AjaxResult assessResult(@RequestParam(value = "userCode") String userCode){
        return AjaxResult.success(yhAssessResultService.findByUser(userCode));
    }

    @PostMapping("/findByAssessInfo")
    public AjaxResult findByAssessInfo(@RequestParam(value = "userCode") String userCode,
                                       @RequestParam(value = "assess") String assess){
        YhAssessResult result = yhAssessResultService.findByAssess(userCode, assess);
        YhAssessConclusion conclusion = yhAssessResultService.findByConcent(result.getState(), assess);
        Map<String,Object> map = new HashMap<>();
        map.put("score", result.getScore());
        map.put("conclusion", conclusion.getConclusion());
        map.put("suggest", conclusion.getSuggest());
        if(assess.equals("A型性格评估")){
            String scorelText = "您此次L量表评分为"+ result.getScoreL() + "分,";
            map.put("scorelText", scorelText+yhAssessResultService.findScoreL(result.getScoreL()));
        }else map.put("scorelText", null);
        map.put("leftWidth", conclusion.getLeftwidth());
        if(conclusion.getGuidance()!=null){
            String[] guidance = conclusion.getGuidance().split(";");
            String[] link = conclusion.getLink().split(";");
            map.put("guidance", guidance);
            map.put("link", link);
        }else{
            map.put("guidance", null);
            map.put("link", null);
        }
        if(assess.equals("心理健康综合评估")){
            List<YhAssessConclusion> con = yhAssessResultService.findBydivisor("心理健康综合评估(因子)");
            String[] answer = result.getAnswer().substring(1,result.getAnswer().length()-1).split(",");
            List<Map<String,String>> list = new ArrayList<>();
            for (YhAssessConclusion record : con) {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("title", record.getState());
                stringMap.put("text", record.getConclusion());
                String[] divisor = record.getSuggest().split(",");
                int score = 0;
                for (String s : divisor) {
                    score += Integer.parseInt(answer[Integer.parseInt(s)-1]);
                }
                double a = Arith.div(score, divisor.length, 2);
                stringMap.put("score", String.valueOf(a));
                stringMap.put("state", a >= 3 ? "阳性":"阴性");
                list.add(stringMap);
            }
            map.put("divisor", list);
        }else  map.put("divisor", null);
        if(assess.equals("职业倦怠评估")){
            List<YhAssessConclusion> con = yhAssessResultService.findBydivisor("职业倦怠评估(因子)");
            String[] answer = result.getAnswer().substring(1,result.getAnswer().length()-1).split(",");
            List<Map<String,String>> list = new ArrayList<>();
            for (YhAssessConclusion record : con) {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("title", record.getState());
                String[] divisor = record.getSuggest().split(",");
                int score = 0;
                for (String s : divisor) {
                    score += Integer.parseInt(answer[Integer.parseInt(s)-1]);
                }
                double a = Arith.div(score, divisor.length, 0);
                stringMap.put("score", String.valueOf(a));
                list.add(stringMap);
            }
            map.put("jobburnout", list);
        }else map.put("jobburnout", null);
        if(null != map.get("suggest")){
            String[] suggests = map.get("suggest").toString().split("/");
            StringBuffer sb = new StringBuffer();
            for (String s : suggests) {
                sb.append(s+"\n");
            }
            map.put("suggest", sb.toString());
        }else {
            map.put("suggest", null);
        }
        return AjaxResult.success(map);
    }
}
