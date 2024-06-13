package com.yihe.applet.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhJzServiceBodydataSwitch;
import com.yihe.system.domain.YhManageTask;
import com.yihe.system.service.YhTaskServiceAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.QuestionnaireVojo;
import com.yihe.applet.api.domain.Sonquestion;
import com.yihe.applet.api.domain.SubjectVojo;
import com.yihe.applet.api.domain.YhLifeHabitDictApp;
import com.yihe.applet.api.domain.YhLifeHabitImprovementDictApp;
import com.yihe.applet.api.domain.YhLifeHabitPersonDictApp;
import com.yihe.applet.api.domain.YhLifeHabitQuestion;
import com.yihe.applet.api.domain.YhLifeHabitSkip;
import com.yihe.applet.api.domain.YhMyLifeHabitAssessResultApp;
import com.yihe.applet.api.mapper.YhLifeHabitDictAppMapper;
import com.yihe.applet.api.mapper.YhLifeHabitImprovementDictAppMapper;
import com.yihe.applet.api.mapper.YhLifeHabitPersonDictAppMapper;
import com.yihe.applet.api.mapper.YhLifeHabitQuestionMapper;
import com.yihe.applet.api.mapper.YhLifeHabitSkipMapper;
import com.yihe.applet.api.mapper.YhMyLifeHabitAssessResultAppMapper;
import com.yihe.system.domain.YhLifeHabitImprovementDict;

import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * YhLifeHabitQuestioncontroller
 * Created by dong on 2020/1/14.
 */
@Controller
@RequestMapping("/api/YhLifeHabitQuestionController")
@Api(value = "YhLifeHabitQuestion控制器", description = "YhLifeHabitQuestion管理")
public class YhLifeHabitQuestionController{

    private static final Logger LOGGER = LoggerFactory.getLogger(YhLifeHabitQuestionController.class);
	@Resource
	YhTaskServiceAPIService yhtaskservuceapiservice;
	@Autowired
    YhLifeHabitQuestionMapper yhLifeHabitQuestionService;
    
    @Autowired
    YhLifeHabitPersonDictAppMapper yhLifeHabitPersonDictService;
    
    @Autowired
    YhLifeHabitImprovementDictAppMapper yhLifeHabitImprovementDictService;
    
    @Autowired
    YhLifeHabitDictAppMapper yhLifeHabitDictService;
    
    @Autowired
    YhMyLifeHabitAssessResultAppMapper yhMyLifeHabitAssessResultAppMapper;
    
    
    @Autowired
    YhLifeHabitSkipMapper yhLifeHabitSkipMapper;
    
    
  
    @RequestMapping(value="/showskipCount",method=RequestMethod.POST)
    @ResponseBody
    public int showskipCount(String usercode) {
    	int dict1=yhLifeHabitSkipMapper.showskipCount(usercode);
    	
    	List<YhLifeHabitImprovementDictApp> list=yhLifeHabitImprovementDictService.selectHabitCodeDict(usercode);
    	int i=0;
    	if(dict1!=0 || list.size()!=0) {
    		i=1;
    	}
    	return i;
    }
    
    
    /**
     * 提交问卷信息
     * @param usercode
     * @param subject
     * @return
     */
    @RequestMapping(value="/addskip",method=RequestMethod.POST)
    @ResponseBody
    public int addskip(String usercode) {
    	YhLifeHabitSkip yhl=new YhLifeHabitSkip();
    	yhl.setUserCode(usercode);
    	int dict1=yhLifeHabitSkipMapper.insertSelective(yhl);
    	return dict1;
    }
    
    
    /**
     * 提交问卷信息
     * @param usercode
     * @param subject
     * @return
     */
    @RequestMapping(value="/addscore",method=RequestMethod.POST)
    @ResponseBody
    public int addscore(String usercode,SubjectVojo subject) {
    	String habit="";
    	String analyze="";
    	
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userCode", usercode);
    	if(subject.getAnswers1()<=5) {
    		habit+="睡太晚,";
    		analyze+="每天做到11点前睡觉,";
    		
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(59);
    	
    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    			YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
        		yuhLifeHabitPersonDict.setUserCode(usercode);
        		yuhLifeHabitPersonDict.setHabitId(dict.getId());
        		yuhLifeHabitPersonDict.setMissingDay(1);
        		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
        		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
        		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    	}
    	
    	if(subject.getAnswers2()<=5) {
    		habit+="睡眠不足,";
    		analyze+="至少保持每天7个小时的正常睡眠,";
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(60);
    		
    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
    		yuhLifeHabitPersonDict.setUserCode(usercode);
    		yuhLifeHabitPersonDict.setHabitId(dict.getId());
    		yuhLifeHabitPersonDict.setMissingDay(1);
    		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
    		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
    		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    	}

    	if(subject.getAnswers3()<=5) {
    		habit+="饮酒过量,";
    		analyze+="减少喝酒,";
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(65);
    		
    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
    		yuhLifeHabitPersonDict.setUserCode(usercode);
    		yuhLifeHabitPersonDict.setHabitId(dict.getId());
    		yuhLifeHabitPersonDict.setMissingDay(1);
    		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
    		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
    		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    	}
    	
    	
    	if(subject.getAnswers4()<=5) {
    		habit+="蔬菜摄入不足,";
    		analyze+="每日多吃蔬菜（300～500克/天）,";
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(42);

    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
    		yuhLifeHabitPersonDict.setUserCode(usercode);
    		yuhLifeHabitPersonDict.setHabitId(dict.getId());
    		yuhLifeHabitPersonDict.setMissingDay(1);
    		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
    		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
    		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    	}
    	
    	if(subject.getAnswers5()<=5) {
    		habit+="抽烟过多,";
    		analyze+="减少抽烟,";
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(64);
    		
    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
    		yuhLifeHabitPersonDict.setUserCode(usercode);
    		yuhLifeHabitPersonDict.setHabitId(dict.getId());
    		yuhLifeHabitPersonDict.setMissingDay(1);
    		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
    		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
    		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    	}
    	
    	if(subject.getAnswers6()<=5) {
    		habit+="水果摄入不足,";
    		analyze+="每日多吃水果（200～350克/天）,";
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(43);
    		
    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
    		yuhLifeHabitPersonDict.setUserCode(usercode);
    		yuhLifeHabitPersonDict.setHabitId(dict.getId());
    		yuhLifeHabitPersonDict.setMissingDay(1);
    		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
    		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
    		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    	}
    	
    	
    	if(subject.getAnswers7()<=5 && subject.getAnswers8()<=5) {
    		habit+="运动不足,";
    		analyze+="增加运动时长以及运动次数,每次不少于30分钟,";
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(54);
    		
    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
    		yuhLifeHabitPersonDict.setUserCode(usercode);
    		yuhLifeHabitPersonDict.setHabitId(dict.getId());
    		yuhLifeHabitPersonDict.setMissingDay(1);
    		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
    		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
    		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    	}else if(subject.getAnswers7()<=5) {
    		habit+="运动不足,";
    		analyze+="增加运动次数,";
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(54);
    		
    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
    		yuhLifeHabitPersonDict.setUserCode(usercode);
    		yuhLifeHabitPersonDict.setHabitId(dict.getId());
    		yuhLifeHabitPersonDict.setMissingDay(1);
    		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
    		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
    		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    		
    	}else if(subject.getAnswers8()<=5) {
    		habit+="运动不足,";
    		analyze+="增加运动时长,每次不少于30分钟,";
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(54);
    		
    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
    		yuhLifeHabitPersonDict.setUserCode(usercode);
    		yuhLifeHabitPersonDict.setHabitId(dict.getId());
    		yuhLifeHabitPersonDict.setMissingDay(1);
    		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
    		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
    		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    	}
    	
    	
    	
    	if(subject.getAnswers9()<=7) {
    		habit+="吃宵夜,";
    		analyze+="不吃宵夜,“睡前餐”只适合老年人和需要熬夜工作的人,";
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(41);
    		
    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
    		yuhLifeHabitPersonDict.setUserCode(usercode);
    		yuhLifeHabitPersonDict.setHabitId(dict.getId());
    		yuhLifeHabitPersonDict.setMissingDay(1);
    		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
    		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
    		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    	}
    	
    	if(subject.getAnswers10()<=5) {
    		habit+="不按时吃饭,";
    		analyze+="按时三餐,";
    		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(40);
    		
    		map.put("habitId", dict.getId());
    		int son=yhLifeHabitPersonDictService.showHabitidcun(map);
    		if(son==0) {
    		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
    		yuhLifeHabitPersonDict.setUserCode(usercode);
    		yuhLifeHabitPersonDict.setHabitId(dict.getId());
    		yuhLifeHabitPersonDict.setMissingDay(1);
    		yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
    		yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
    		yhLifeHabitPersonDictService.insertSelective(yuhLifeHabitPersonDict);
    		}
    	}
    	
    	int assessScores=subject.getAnswers1()+subject.getAnswers2()+subject.getAnswers3()+subject.getAnswers4()+subject.getAnswers5()+subject.getAnswers6()+subject.getAnswers7()+subject.getAnswers8()+subject.getAnswers9()+subject.getAnswers10();
    	YhLifeHabitImprovementDictApp yhl=new YhLifeHabitImprovementDictApp();
    	yhl.setTitle(habit);
    	yhl.setAssessScores(assessScores);
    	if(assessScores<=60) {
    		yhl.setResult("较差");
    	}else if(assessScores>60 && assessScores<=80) {
    		yhl.setResult("良好");
    	}else {
    		yhl.setResult("很棒");
    	}
    	if(analyze.length()==0) {
    		yhl.setAnalyzename("请继续保持。");
    	}else {
    		 String  s = analyze.substring(0,analyze.length() - 1);
    		 yhl.setAnalyzename(s+"。");
    	}
    	yhl.setUserCode(usercode);
    	
    	int dict1=yhLifeHabitImprovementDictService.insertSelective(yhl);
    	
    	
    	YhMyLifeHabitAssessResultApp resr=new YhMyLifeHabitAssessResultApp();
    	resr.setUserCode(usercode);
    	resr.setDictId(yhl.getId());
    	resr.setAssessImproved(subject.getIndex1()+","+subject.getIndex2()+","+subject.getIndex3()+","+subject.getIndex4()+","+subject.getIndex5()+","+subject.getIndex6()+","+subject.getIndex7()+","+subject.getIndex8()+","+subject.getIndex9()+","+subject.getIndex10());
    	yhMyLifeHabitAssessResultAppMapper.insertSelective(resr);
    	
    	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String createdate = sdf.format(date);
		YhJzServiceBodydataSwitch ok=yhtaskservuceapiservice.queryswitchbyid1(7,2, "健康习惯评估");
		if(ok.getEvery_switch()==1) {
			YhManageTask task=new YhManageTask();
			task.setUsercode(usercode);
			Date d = null;
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				d = sd.parse(createdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000);
			String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
			task.setService_waiting_time(newtime);
			task.setService_type(7);
			task.setService_content(ok.getService_content());
			task.setService_record("完成健康习惯评估");
			task.setReturnvisit(ok.getReturnvisit());
			task.setChildnodes(ok.getId());
			task.setChildtag("2");
			yhtaskservuceapiservice.insertperson(task);
		}
    	System.out.println(subject);
    	
    	YhLifeHabitSkip skip=new YhLifeHabitSkip();
    	skip.setUserCode(usercode);
    	yhLifeHabitSkipMapper.insertSelective(skip);
    	
    	
		return dict1;
    }
    
    /**
               * 查询所有问卷
     * 
     * @return
     */
	@RequestMapping(value = "/showquestionnaire", method = RequestMethod.POST)
	@ResponseBody // ajax注解
	public List<QuestionnaireVojo> showquestionnaire(){
		List<QuestionnaireVojo> quest=new ArrayList<QuestionnaireVojo>();
		 List<YhLifeHabitQuestion> list=yhLifeHabitQuestionService.selectByExample();
		 int i=0;
		 for (YhLifeHabitQuestion yhLifeHabitQuestion : list) {
			 i++;
			 List<Sonquestion> tion=new ArrayList<Sonquestion>();
			 QuestionnaireVojo stat=new QuestionnaireVojo();
			 stat.setId(yhLifeHabitQuestion.getId());
			 stat.setTitle(yhLifeHabitQuestion.getTitle());
			 stat.setzIndex(100-i);
			 
			if(yhLifeHabitQuestion.getChoose1()!=null && !yhLifeHabitQuestion.getChoose1().equals("")) {
				Sonquestion son=new Sonquestion();
				son.setSelect(false);
				son.setScoreid(yhLifeHabitQuestion.getId()+1);
				son.setScore(yhLifeHabitQuestion.getChoose1Value());
				son.setTopic(yhLifeHabitQuestion.getChoose1());
				tion.add(son);
			}
			
			if(yhLifeHabitQuestion.getChoose2()!=null && !yhLifeHabitQuestion.getChoose2().equals("")) {
				Sonquestion son=new Sonquestion();
				son.setScoreid(yhLifeHabitQuestion.getId()+2);
				son.setSelect(false);
				son.setScore(yhLifeHabitQuestion.getChoose2Value());
				son.setTopic(yhLifeHabitQuestion.getChoose2());
				tion.add(son);
			}
			
			if(yhLifeHabitQuestion.getChoose3()!=null && !yhLifeHabitQuestion.getChoose3().equals("")) {
				Sonquestion son=new Sonquestion();
				son.setScoreid(yhLifeHabitQuestion.getId()+3);
				son.setSelect(false);
				son.setScore(yhLifeHabitQuestion.getChoose3Value());
				son.setTopic(yhLifeHabitQuestion.getChoose3());
				tion.add(son);
			}

			if(yhLifeHabitQuestion.getChoose4()!=null && !yhLifeHabitQuestion.getChoose4().equals("")) {
				Sonquestion son=new Sonquestion();
				son.setScoreid(yhLifeHabitQuestion.getId()+4);
				son.setSelect(false);
				son.setScore(yhLifeHabitQuestion.getChoose4Value());
				son.setTopic(yhLifeHabitQuestion.getChoose4());
				tion.add(son);
			}
			
			if(yhLifeHabitQuestion.getChoose5()!=null && !yhLifeHabitQuestion.getChoose5().equals("")) {
				Sonquestion son=new Sonquestion();
				son.setScoreid(yhLifeHabitQuestion.getId()+5);
				son.setSelect(false);
				son.setScore(yhLifeHabitQuestion.getChoose5Value());
				son.setTopic(yhLifeHabitQuestion.getChoose5());
				tion.add(son);
			}
			stat.setSonquestion(tion);
			quest.add(stat);
		}
		 System.out.println(quest);
		return quest;
	}



}