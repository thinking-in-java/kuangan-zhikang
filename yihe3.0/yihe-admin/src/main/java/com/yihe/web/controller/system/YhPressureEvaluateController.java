package com.yihe.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.yihe.common.annotation.Log;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.common.core.page.TableDataInfo;
import com.yihe.common.enums.BusinessType;
import com.yihe.common.utils.poi.ExcelUtil;
import com.yihe.system.domain.PressureVOJO;
import com.yihe.system.domain.YhPressureEvaluate;
import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.*;
import com.yihe.system.service.impl.YhPressureEvaluateServiceImpl;
import com.yihe.system.utils.PdfUtils;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 压力管理三大评估问卷数据Controller
 * 
 * @author yihe
 * @date 2020-04-07
 */
@Controller
@RequestMapping("/system/evaluate")
public class YhPressureEvaluateController  extends BaseController

{
	private String prefix = "system/evaluate";

	@Autowired
	private IYhPressureEvaluateService yhPressureEvaluateService;
	// A型性格评估问卷
	@Autowired
	YhPersonalityAssessmentService yhPersonalityAssessmentService;
	// PSTRI压力评估问卷
	@Autowired
	YhpressureAssessmentService yhpressureAssessmentService;
	// PSTRI压力评估问卷
	@Autowired
	YhPsychologicalAssessmentService yhPsychologicalAssessmentService;
	@Autowired
	YhOrgTreeService yhOrgTreeService;
	@Autowired
	YhUserReginfoService yhUserReginfoService;
	@Autowired
	YhUserMakeCardService yhUserMakeCardService;
	@Autowired
	YhPressureEvaluateServiceImpl yhPressureEvaluateServiceImpl;
	@Autowired
	private YhPeriodComparisonService userService;

	@RequiresPermissions("system:evaluate:view")
	@GetMapping()
	public String evaluate() {
		return prefix + "/evaluate";
	}

	@RequestMapping(value = "/findpressureList", method = RequestMethod.GET)
	@ResponseBody
	public Result findpressureList(@RequestParam(required = false) String orgid,
			@RequestParam(required = false) String nameLike,

			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String userCode = principal.getUserCode();
		PageInfo<PressureVOJO> findStressAssessment = yhPressureEvaluateService.findStressAssessment(orgid, nameLike,
				userCode, page, limit);
		List<PressureVOJO> list = findStressAssessment.getList();
		for (PressureVOJO pressureVOJO : list) {
			String usercode = pressureVOJO.getUsercode();
			if (null == usercode) {
				continue;
			}
			String type1 = yhPressureEvaluateService.selectYhPressureEvaluateListType(1, usercode);
			String type2 = yhPressureEvaluateService.selectYhPressureEvaluateListType(2, usercode);
			String type3 = yhPressureEvaluateService.selectYhPressureEvaluateListType(3, usercode);
			if (null != type1) {
				String assessmentResults1 = assessmentResults("1", type1, "");
				pressureVOJO.setType1(assessmentResults1);
			}

			if (null != type2) {
				String assessmentResults2 = assessmentResults("2", type2, "");
				pressureVOJO.setType2(assessmentResults2);
			}
			if (null != type3) {
				String assessmentResults3 = assessmentResults("3", type3, pressureVOJO.getSex());
				pressureVOJO.setType3(assessmentResults3);
			}

			/*
			 * if(null!=type1){ // 获取评估分数 String score[] = type1.split(";");
			 * if(score.length==3){ Integer s_score = Integer.valueOf(score[0] +
			 * score[1] + score[2]); if (s_score < 20) {
			 * pressureVOJO.setType1("B型"); } else if (s_score < 27) {
			 * pressureVOJO.setType1("中间型偏B型"); }else if(s_score < 30){
			 * pressureVOJO.setType1("中间型"); }else if(s_score < 37){
			 * pressureVOJO.setType1("中间型偏A型"); } else{
			 * pressureVOJO.setType1("A型"); } } }
			 * 
			 * if(null!=type2){ Integer score = Integer.valueOf(type2); if(score
			 * < 43){ pressureVOJO.setType2("压力过低"); }else if(score <= 65){
			 * pressureVOJO.setType2("压力适中"); }else{
			 * pressureVOJO.setType2("压力过高"); } }
			 * 
			 * if(null!=type3){ Integer score = Integer.valueOf(type3); String
			 * sex = pressureVOJO.getSex(); switch (sex) { case "女": if(score <
			 * 25){ pressureVOJO.setType3("疑似心理疾患"); }else if(score <= 45){
			 * pressureVOJO.setType3("橙色预警"); }else{
			 * pressureVOJO.setType3("正常"); } break;
			 * 
			 * default: if(score < 10){ pressureVOJO.setType3("疑似心理疾患"); }else
			 * if(score <= 65){ pressureVOJO.setType3("橙色预警"); }else{
			 * pressureVOJO.setType3("正常"); } break; }
			 * 
			 * }
			 */

		}

		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", findStressAssessment.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}

	@RequestMapping(value = "/findpressureRecordList", method = RequestMethod.GET)
	@ResponseBody
	public Result findpressureRecordList(@RequestParam(required = false) String nameLike,
			@RequestParam(required = false) String usernameLike, @RequestParam(required = false) String type,
			@RequestParam(required = false) String strTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false) String startTime,

			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {
		if (StringUtil.isNotEmpty(startTime)) {
			String[] split = startTime.split(" - ");
			strTime = split[0];
			endTime = split[1];
		}
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String userCode = principal.getUserCode();
		PageInfo<PressureVOJO> findStressAssessmentRecord = yhPressureEvaluateService
				.findStressAssessmentRecord(usernameLike, nameLike, type, userCode, strTime, endTime, page, limit);
		List<PressureVOJO> list = findStressAssessmentRecord.getList();
		for (PressureVOJO pressureVOJO : list) {
			String evaluateScore = pressureVOJO.getEvaluateScore();// 评估分
			String assessmentType = pressureVOJO.getAssessmentType();// 评估类型
			if (null != evaluateScore) {
				String assessmentResults = assessmentResults(assessmentType, evaluateScore, pressureVOJO.getSex());
				pressureVOJO.setAssessmentResults(assessmentResults);
			}

		}
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", findStressAssessmentRecord.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}

	@RequestMapping(value = "/findpressureRecordListByuser", method = RequestMethod.GET)
	@ResponseBody
	public Result findpressureRecordListByuser(@RequestParam(required = false) String type,
			@RequestParam(required = false) String strTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false) String startTime, @RequestParam(required = false) String usercode,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {
		System.out.println("--------" + usercode);
		if (StringUtil.isNotEmpty(startTime)) {
			String[] split = startTime.split(" - ");
			strTime = split[0];
			endTime = split[1];
		}

		PageInfo<PressureVOJO> findStressAssessmentRecord = yhPressureEvaluateService
				.findStressAssessmentRecordByUser(type, usercode, strTime, endTime, page, limit);
		List<PressureVOJO> list = findStressAssessmentRecord.getList();

		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", findStressAssessmentRecord.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}

	String assessmentResults(String type, String scoreString, String sex) {

		if ("1".equals(type)) {
			Integer s_score = 0; 
			// 获取评估分数 
			String score[] = scoreString.split(";");
			for (int i = 0; i < score.length; i++) {
				if(StringUtil.isEmpty(score[i])){
					continue;
				}
				s_score=s_score+Integer.valueOf(score[i]);
			}
			if (score.length == 3) {
				
				if (s_score < 20) {
					return "B型";
				} else if (s_score < 27) {
					return "中间型偏B型";
				} else if (s_score < 30) {
					return "中间型";
				} else if (s_score < 37) {
					return "中间型偏A型";
				} else {
					return "A型";
				}
			}

		}

		if ("2".equals(type)) {
			Integer score = Integer.valueOf(scoreString);
			if (score < 43) {
				return "压力过低";
			} else if (score <= 65) {
				return "压力适中";
			} else {
				return "压力过高";
			}
		}

		if ("3".equals(type)) {

			Integer score = Integer.valueOf(scoreString);
			switch (sex) {
			case "女":
				if (score < 25) {
					return "疑似心理疾患";
				} else if (score <= 45) {
					return "橙色预警";
				} else {
					return "正常";

				}
			default:
				if (score < 10) {
					return "疑似心理疾患";
				} else if (score <= 65) {
					return "橙色预警";
				} else {
					return "正常";
				}
			}

		}

		return "";
	}

	/**
	 * 查询压力管理三大评估问卷数据列表
	 */
	@RequiresPermissions("system:evaluate:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(YhPressureEvaluate yhPressureEvaluate) {
		startPage();
		List<YhPressureEvaluate> list = yhPressureEvaluateService.selectYhPressureEvaluateList(yhPressureEvaluate);
		return getDataTable(list);
	}

	/**
	 * 导出压力管理三大评估问卷数据列表
	 */
	@RequiresPermissions("system:evaluate:export")
	@Log(title = "压力管理三大评估问卷数据", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(YhPressureEvaluate yhPressureEvaluate) {
		List<YhPressureEvaluate> list = yhPressureEvaluateService.selectYhPressureEvaluateList(yhPressureEvaluate);
		ExcelUtil<YhPressureEvaluate> util = new ExcelUtil<YhPressureEvaluate>(YhPressureEvaluate.class);
		return util.exportExcel(list, "evaluate");
	}

	/**
	 * 新增压力管理三大评估问卷数据
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存压力管理三大评估问卷数据
	 */
	@RequiresPermissions("system:evaluate:add")
	@Log(title = "压力管理三大评估问卷数据", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(YhPressureEvaluate yhPressureEvaluate) {
		return toAjax(yhPressureEvaluateService.insertYhPressureEvaluate(yhPressureEvaluate));
	}

	/**
	 * 修改压力管理三大评估问卷数据
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		YhPressureEvaluate yhPressureEvaluate = yhPressureEvaluateService.selectYhPressureEvaluateById(id);
		mmap.put("yhPressureEvaluate", yhPressureEvaluate);
		return prefix + "/edit";
	}

	/**
	 * 修改保存压力管理三大评估问卷数据
	 */
	@RequiresPermissions("system:evaluate:edit")
	@Log(title = "压力管理三大评估问卷数据", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(YhPressureEvaluate yhPressureEvaluate) {
		return toAjax(yhPressureEvaluateService.updateYhPressureEvaluate(yhPressureEvaluate));
	}

	/**
	 * 删除压力管理三大评估问卷数据
	 */
	@RequiresPermissions("system:evaluate:remove")
	@Log(title = "压力管理三大评估问卷数据", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(yhPressureEvaluateService.deleteYhPressureEvaluateByIds(ids));
	}

	public static boolean checkStr(String str) {
		return str != null && !"".equals(str);
	}

	@RequestMapping(value = "/exportPdf", method = RequestMethod.GET)
	@ResponseBody
	public void exportXls(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (null == id) {
			return;
		}
		YhPressureEvaluate selectByPrimaryKey = yhPressureEvaluateServiceImpl.selectYhPressureEvaluateById(id);
		if (null == selectByPrimaryKey) {
			return;
		}
		// 评估类型
		Long type = selectByPrimaryKey.getEvaluateType();
		// 评估类型名称
		String title = "";
		// 评估时间
		String evaluateData = selectByPrimaryKey.getEvaluateDate();
		// 用户code
		String usercode = selectByPrimaryKey.getUserCode();
		YhUserReginfo userInfo = yhUserReginfoService.selectByPrimaryKey(usercode);
		// 用户年龄
		int ageByBirth = getAgeByBirth(userInfo.getBirthDate());
		// 评估结果
		String info2Icon = "";
		// 简介
		String info2 = "";
		// 建议
		String info3 = "";
		String text1 = "";
		String text2 = "";
		if (1 == type) {
			title = "A型性格评估报告";
			String evaluateScore = selectByPrimaryKey.getEvaluateScore();
			// 获取评估分数
			String score[] = evaluateScore.split(";");
			int int_score[] = new int[3];
			int step = 0;
			for (int j = 0; j < score.length; j++)
				if (checkStr(score[j])) {
					int_score[step] = Integer.parseInt(score[j]);
					step++;
				}
			String info1 = "";
			// 计算L量表得分
			Integer l_score = Integer.valueOf(int_score[0]);
			if (l_score < 7) {
				info1 = "您此次L量表评分为" + l_score + "分，说明您的回答能真实地反映您的情况，该问卷结果有效。您能够诚恳地面对对自己不利的评价，为人真诚，不好掩饰";
			} else {
				info1 = "您此次L量表评分为" + l_score
						+ "分，说明您回答某些题时不够诚实，该问卷结果无效。您喜欢对自己不利的评价掩饰，为人容易表现出虚伪、圆滑，也可能由于自身定位不准确，自我认识不清或理解能力不足造成的";

			}

			// 计算性格评估量表得分
			Integer s_score = Integer.valueOf(int_score[0] + int_score[1] + int_score[2]);

			if (s_score < 20) {
				info2Icon = "B型";
				info2 = "您性情随和，顺从，随遇而安，不喜争斗；生活方式悠闲自在，对成败得失看得较淡，不太在意成就的大小，有时缺乏抱负和主见；时间观念不强，对于布置给自己的任务能否完成，从不着急；情绪不易激动，容易与人相处，人际关系较好。";
				info3 = "您的工作生活悠闲自得，但现代社会节奏日益加快，您需要提速以便更有效率地学习和工作。1、制定一个符合自己实际能力并可实现的目标； 2、对工作进行有效的规划，制定出完成的时间；如果您要完成的任务有很多，首先区分他们的先后、主次次序，完成一件工作后再做第二件；3、勇于弘扬自己的优点：一些B型性格的人认为在别人面前承认自己的优点非常不自在。其实不然，正是我们自身的优点使我们迎来一个又一个机会。4、敢于交流和发表自己的看法，对待不公平的人与事，要适当的宣泄自己的情感。";
			} else if (s_score < 27) {
				info2Icon = "中间型偏B型";
				info2 = "你兼具A、B两种类型人格特征，但更偏向于B型特征，大多数时间里，你的生活是按部就班、有条不紊的，会用比较松弛、稳健的方式来处理问题和完成任务，但偶尔也会有时间紧迫感和易怒情绪。\n" +
						"你并不争强好胜，分秒必争，但也不会过多的浪费时间，对成功有追求，但不苛求。尽管也会偶尔感到来自工作的压力和疲劳，但是大多数时间里，还是可以淡然处之，以自己的方式权衡得失并处理问题。\n" +
						"在人群中，你会奉行一种低调的行为准则，没有强烈的支配欲、占有欲和控制欲。遇到挫折时，你也能较好的进行自我调节，尽管也会偶尔受到过激情绪的影响。";
				info3 = "缺少了激情和冲动的生活不免有些单调，适当的放大自己追求成功的特质，更加主动积极的面对工作和生活，可以让你有更好的表现。\n" +
						"您也许可以尝试为自己制定更高的目标，确定每件事的完成时间，以获得更大的成就感。";
			} else if (s_score < 30) {
				info2Icon = "中间型";
				info2 = "现实中大多数人可能都跟您一样，居于典型的A型和B型性格之间。您不像典型的A型性格那样追求成就，好胜心切；您也不像典型B型那样内向、逆来顺受。您有一定的时间观念和竞争意识，总之您同时具有A型和B型性格特征，得分越高，A型倾向（进取、好胜、冲动）越明显；得分越低，B型倾向（悠然、顺从、沉稳）越明显。 ";
				info3 = "根据您的性格是更多的倾向A型还是B型，有不同的改善方法。\n如果您更多地倾向A型，那么请您减缓工作生活的节奏，为每件事情制定好计划，不追赶时间；学会承认自己的不足，接受现实；学会微笑和夸奖别人；为自己留出一定的休闲时间，培养自己的体育、音乐、文艺方面的业余爱好，享受生活。\n如果您更多的倾向B型，那么您可能很会享受生活，人际关系较好。您也许可以尝试为自己制定更高的目标，确定每件事的完成时间，以获得更大的成就感。";
			} else if (s_score < 37) {
				info2Icon = "中间型偏A型";
				info2 = "你兼具A、B两种类型人格特征，但更偏向于A型特征，大多数时候会感到时间紧迫，并具有竞争意识，工作努力，雄心勃勃而又急躁易怒，偶尔也会令自己助于放松的状态中，把生活看成是享受而不是战斗，以自己的方式和观点处理问题。\n" +
						"你的时间观念较强，做事不拖沓，对待工作和学习富有激情。与简单的任务比较，你更愿意接受有挑战性工作，能够主动克服困难，努力达到目标。你喜欢在有竞争的情境下工作，对成功有渴望、有激情，但也不至于令自己过于紧张，很多时候仍然可以松弛稳健的处理问题和完成任务。\n" +
						"在人群中，你喜欢成为核心，大部分时间处于控制支配别人的角色。面对挫折，首先你的反应是易被激惹，容易产生攻击性和敌意，但也会逐渐找到调节自己和解决问题的途径。\n" +
						"你的行为方式会使你更加接近某些疾病，如心脏病、胆固醇升高，建议你在面对问题的时候，可以控制自己，及时调节情绪，正确的看待事情。";
				info3 = "请您减缓工作生活的节奏，为每件事情制定好计划，不追赶时间；\n" +
						"学会承认自己的不足，接受现实；\n" +
						"学会微笑和夸奖别人；\n" +
						"为自己留出一定的休闲时间，培养自己的体育、音乐、文艺方面的业余爱好，享受生活。";
			} else {
				info2Icon = "A型";
				info2 = "您好胜心强，追求成就，有很强的事业心，做工作总要尽善尽美，有很强的竞争欲；时间观念强，总觉得时间紧张、不够用，做事匆忙、急躁，不干完事不踏实；反应快而强烈，行动迅速；情绪易波动，易被激怒，对人怀有戒心，人际关系比较紧张。 ";
				info3 = "您的生活充满激情但行事匆匆。科学家们发现A型性格与心脏病关系密切，您需要采取一些措施让自己慢下来。\n1、 每日记录自己的匆忙事例，检查出现匆忙的原因，每周小结一次，及时定制解决方案。\n2、放弃自己要做一个超人的意图，勇于接受自己的缺点和不足，相信他人也能做好工作。\n3、尽量淡化荣誉及成就感，注意学习生活与工作的和谐及生活质量。\n4、放弃同时思考多个问题或完成几件事的习惯，在时间安排上井然有序，留有余地，踏实稳健地干每一件事情。\n5、主动控制调节自己的情绪，避免争论，冲突，以幽默的态度化解生活的挫折。\n6、善于倾听他人的意见，不要随意打断别人的讲话。学会对所有认识的人微笑。\n7、有目的地培养耐心和悠闲感。每天留给自己一定的时间，散散步，听听音乐，放松一下。\n8、多交知心朋友，建立归宿感，安全感，多与朋友交流自己内心的真切感受。";
			}

			selectByPrimaryKey.setL_score(l_score);
			selectByPrimaryKey.setS_score(s_score);
			text1 = "此次性格评估量表得分：" + s_score + "分";
			text2 = "此次L量表得分：" + l_score + "分";
		}
		if (2 == type) {
			title = "PSTRI压力评估报告";
			String evaluateScore = selectByPrimaryKey.getEvaluateScore();
			Integer score = Integer.valueOf(evaluateScore);
			selectByPrimaryKey.setS_score(score);
			if (score < 43) {
				info2Icon = "压力过低";
				info2 = "您需要更主动些，在工作、社交、娱乐等活动上多寻求些刺激，增加自己的压力。您也许可以尝试制定更高的目标，留给自己更多的工作，规定更紧的期限。事实上，适当的压力有助于您保持旺盛的精力和敏捷的思维。做放松练习对您没有用，它只会让您更加放松。";
				info3 = "1、健康饮食，平衡膳食能够有效稳定情绪。\n2、适当运动，如慢跑、散步、游泳等。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。\n3、睡眠充足，尽量固定睡觉和起床的时间。 \n4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上而不是头疼的问题上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。 \n5、学会坚守底线，不要接受不必要的负担。";
			} else if (score <= 65) {
				info2Icon = "压力适中";

				info2 = "您可以适度寻求刺激，比如参与文娱活动，增加生活的乐趣。您也许可以多接触新鲜的人和事，并表达出自己的感受，是惊喜、快乐还是厌恶。您能够感受到压力，但是您常常把它忽略掉，您需要增加压力反应的强度，适度的压力反应会让您感受到生活的多姿多彩。";
				info3 = "1、健康饮食，平衡膳食能够有效稳定情绪。\n2、适当运动，如慢跑、散步、游泳等。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。\n3、睡眠充足，尽量固定睡觉和起床的时间。 \n4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上而不是头疼的问题上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。 \n5、学会坚守底线，不要接受不必要的负担。";
			} else {
				info2Icon = "压力过高";
				info2 = "您需要仔细反省自己对压力做出的反应，并学习在压力出现时，控制自己的肌肉紧张，以消除生理激活反应。以下几点将有助于您减轻压力反应。 ";
				info3 = "1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，以便在日后尽量避免这些事件的发生，或是有所针对的各个击破。\n2、了解自己最易受制于压力的时刻并在这一时刻到来之前做好准备。你最易受到影响的时候是在早晨？或是周一？抑或是冬季？\n3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应。在同等压力下，做运动的人比不做运动的人血压要低，心率要慢。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。\n4、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉能使您心情舒畅。有条件的话，不妨出去旅行一段时间，让自己的心灵休息一会儿。\n5、深呼吸。将注意力转移到腹部，在用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中。呼气时想象空气反向流动。";
			}

			text1 = "此次PSTRI压力评估得分：" + score + "分";

		}
		if (3 == type) {
			title = "心理健康评估报告";
			String evaluateScore = selectByPrimaryKey.getEvaluateScore();
			Integer score = Integer.valueOf(evaluateScore);
			selectByPrimaryKey.setS_score(score);
			if (userInfo.getGender() == 1) {
				if (score < 10) {
					info2Icon = "疑似心理疾患";
					info2 = "特别声明：此量表仅用于自测，不具有诊断价值。请确定您是否有文字理解错误、故意 犯错、没有认真作答等情况。若无，您需要寻求专业人士的帮助，请咨询心理医生。";
					info3 = "您可以尝试以下建议，也许有助于您恢复平稳的心境。\n1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，有所针对的各个击破。\n2、健康饮食。平衡膳食能够有效稳定情绪。\n3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应，增加血液循环，对改善情绪有好处。\n4、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。\n5、睡眠充足。压力会干扰人们放松的状态，影响睡眠质量，从而导致疲劳和问题处理能力下降。为了能够保证好的睡眠，应该尽量固定睡觉和起床的时间。\n6、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。\n7、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好的释放压力并塑造积极的人生态度。\n8、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉不但可增强人们的友谊和信任，更能使您心情舒畅。\n9、学会接受现实。现实中没有人能事事如意，对于一些不能改变的事实，慢慢地试着去接受它，改变一下自己看待问题的角度和心态，也许心情会好一些。";
				}
				if (score <= 65) {
					info2Icon = "橙色预警";
					info2 = "特别声明：此量表仅用于自测，不具有诊断价值。请确定您是否有文字理解错误、故意 犯错、没有认真作答等情况。若无，您需要寻求专业人士的帮助，请咨询心理医生。";
					info3 = "您可以尝试以下建议，也许有助于您恢复平稳的心境。\n1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，有所针对的各个击破。\n2、健康饮食。平衡膳食能够有效稳定情绪。\n3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应，增加血液循环，对改善情绪有好处。\n4、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。\n5、睡眠充足。压力会干扰人们放松的状态，影响睡眠质量，从而导致疲劳和问题处理能力下降。为了能够保证好的睡眠，应该尽量固定睡觉和起床的时间。\n6、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。\n7、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好的释放压力并塑造积极的人生态度。\n8、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉不但可增强人们的友谊和信任，更能使您心情舒畅。\n9、学会接受现实。现实中没有人能事事如意，对于一些不能改变的事实，慢慢地试着去接受它，改变一下自己看待问题的角度和心态，也许心情会好一些。";
				} else {
					info2Icon = "正常";
					info2 = "特别声明：此量表仅用于自测，不具有诊断价值。请确定您是否有文字理解错误、故意 犯错、没有认真作答等情况。若无，您需要寻求专业人士的帮助，请咨询心理医生。";
					info3 = "下面是一些常见的提高压力应对能力的技巧，希望对您有所帮助。\n1、健康饮食，平衡膳食能够有效稳定情绪。\n\n2、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应。在同等压力下，做运动的人比不做运动的人血压要低，心率要慢。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。\n\n3、睡眠充足，尽量固定睡觉和起床的时间。\n\n4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。\n\n5、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好地释放压力并塑造积极的人生态度。\n\n6、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。\n\n7、回忆快乐的事，给自己正性的暗示，将有助于您积极向上地生活。";
				}
			} else {

				if (score < 25) {
					info2Icon = "疑似心理疾患";
					info2 = "特别声明：此量表仅用于自测，不具有诊断价值。请确定您是否有文字理解错误、故意 犯错、没有认真作答等情况。若无，您需要寻求专业人士的帮助，请咨询心理医生。";
					info3 = "您可以尝试以下建议，也许有助于您恢复平稳的心境。1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，有所针对的各个击破。\n2、健康饮食。平衡膳食能够有效稳定情绪。\n3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应，增加血液循环，对改善情绪有好处。\n4、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。\n5、睡眠充足。压力会干扰人们放松的状态，影响睡眠质量，从而导致疲劳和问题处理能力下降。为了能够保证好的睡眠，应该尽量固定睡觉和起床的时间。\n6、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。\n7、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好的释放压力并塑造积极的人生态度。\n8、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉不但可增强人们的友谊和信任，更能使您心情舒畅。\n9、学会接受现实。现实中没有人能事事如意，对于一些不能改变的事实，慢慢地试着去接受它，改变一下自己看待问题的角度和心态，也许心情会好一些。";
				}
				if (score <= 45) {
					info2Icon = "橙色预警";
					info2 = "特别声明：此量表仅用于自测，不具有诊断价值。请确定您是否有文字理解错误、故意 犯错、没有认真作答等情况。若无，您需要寻求专业人士的帮助，请咨询心理医生。";
					info3 = "您可以尝试以下建议，也许有助于您恢复平稳的心境。\n1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，有所针对的各个击破。\n2、健康饮食。平衡膳食能够有效稳定情绪。\n3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应，增加血液循环，对改善情绪有好处。\n4、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。\n5、睡眠充足。压力会干扰人们放松的状态，影响睡眠质量，从而导致疲劳和问题处理能力下降。为了能够保证好的睡眠，应该尽量固定睡觉和起床的时间。\n6、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。\n7、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好的释放压力并塑造积极的人生态度。\n8、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉不但可增强人们的友谊和信任，更能使您心情舒畅。\n9、学会接受现实。现实中没有人能事事如意，对于一些不能改变的事实，慢慢地试着去接受它，改变一下自己看待问题的角度和心态，也许心情会好一些。";
				} else {
					info2Icon = "正常";
					info2 = "特别声明：此量表仅用于自测，不具有诊断价值。请确定您是否有文字理解错误、故意 犯错、没有认真作答等情况。若无，您需要寻求专业人士的帮助，请咨询心理医生。";
					info3 = "下面是一些常见的提高压力应对能力的技巧，希望对您有所帮助。\n1、健康饮食，平衡膳食能够有效稳定情绪。\n2、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应。在同等压力下，做运动的人比不做运动的人血压要低，心率要慢。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。\n3、睡眠充足，尽量固定睡觉和起床的时间。\n4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。\n5、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好地释放压力并塑造积极的人生态度。\n6、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。\n7、回忆快乐的事，给自己正性的暗示，将有助于您积极向上地生活。";
				}
			}
			text1 = "此次心理健康评估得分：" + score + "分";
		}

		Map<String, String> map = new HashMap();
		map.put("title", title);
		map.put("usercode", usercode);
		map.put("username", userInfo.getName());
		map.put("gender", userInfo.getGender() == 1 ? "男" : "女");
		map.put("age", String.valueOf(ageByBirth));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date data = sdf.parse(evaluateData);

		map.put("time", sdf.format(data));
		map.put("result", info2Icon);
		map.put("text1", text1);
		map.put("text2", text2);
		map.put("title2", info2Icon);
		map.put("content2", info2);
		map.put("title3", "建议");
		map.put("content3", info3);

		
		  Map<String,String> map2 = new HashMap();
		  String getrepotimg = userService.getrepotimg();
		  map2.put("img",getrepotimg);
		 

		Map<String, Object> o = new HashMap();
		o.put("datemap", map);
		 o.put("imgmap",map2); 

		// 设置response参数，可以打开下载页面
		response.reset();
		response.setCharacterEncoding("UTF-8");
		// 定义输出类型
		response.setContentType("application/PDF;charset=utf-8");
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = title + f.format(new Date()) + ".pdf";
		response.setHeader("content-disposition",
				"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));

		try {
			
			ServletOutputStream out = response.getOutputStream();
			String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
			System.out.println(path+"static/template/mytest.pdf");
			PdfUtils.pdfout(o, out, new File(path+"static/template/mytest.pdf").getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;

	}

	public String selectInfo(String index){
		switch (index){
			case "0":
				return "常有";
			case "1":
				return "偶有";
			case "2":
				return "罕有";
			case "3":
				return "从无";
		}
		return "";
	}

	@RequestMapping(value = "/exportXls", method = RequestMethod.GET)
	@ResponseBody
	public void exportXls(HttpServletResponse response, String usercode, Integer id, String evaluateType)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = null;
		YhUserReginfo userReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		String curUserName = userReginfo.getUserName();

		List<YhUserReginfo> exportDataList = yhUserReginfoService.exportYhUserReginfoData(curUserName);
		// 设置文件名
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fileName = "压力管理导出" + ".xls";// 设置要导出的文件的名字
		String[] headers = null;
		// 新增数据行，并且设置单元格数据
		int rowNum = 1;
		// headers表示excel表中第一行的表头
		if ("1".equals(evaluateType)) {
			sheet = workbook.createSheet("A型性格");
			headers = new String[] { "用户编码", "姓名", "性别", "评估年龄", "单位", "评估时间", "我总是力图说服别人同意我的观点", "即使没有什么要紧的事,我走路也快",
					"我经常感到应该做的事太多,有压力", "我自己决定的事,别人很难让我改变主意", "有些人和事常常使我十分恼火", "在急需买东西但又要排长队时,我宁愿不买",
					"有些工作我根本安排不过来,只能临时挤时间去做", "上班或赴约会时,我从来不迟到", "我正在做事,谁打扰我,不管有意无意,我总是感到恼火", "我总看不惯那些慢条斯理,不紧不慢的人",
					"我常常忙得透不过气来,因为该做的事情太多了", "即使跟别人合作,我也总想单独完成一些更重要的部分", "有时我真想骂人", "我做事总是喜欢慢慢来,而且思前想后,拿不定主意",
					"排队买东西,要是有人加塞,我就忍不住要指责他", "我觉得自己是一个无忧无虑,悠闲自在的人", "有时连我都觉得我操心的事远超过我应该操心的范围", "无论做什么事,即使比别人差,我也无所谓",
					"做什么事也不着急,着急也没有用,不着急也误不了事", "我从来没想过要按自己的想法办事", "每天的事情都使我精神十分紧张", "逛公园,赏花,观鱼等,我也总是先看完,等着同来的人",
					"我常常不能宽容别人的缺点和毛病", "在我认识的人里,个个我都喜欢", "听到别人发表不正确的见解,我总想立即就去纠正他", "无论做什么事,我都比别人快一些",
					"当别人对我无礼时,我对他也不客气", "我总觉得我有能力把一切事情办好", "聊天时我也总是急于说出自己想法,甚至打断别人的话", "人们认为我是个安静,沉着,有耐心的人",
					"我觉得在我认识的人之中值得我信任和佩服的人不多", "我觉得在我认识的人之中值得我信任和佩服的人不多", "有时我也会说人家的闲话", "尽管时间很宽裕,我吃饭也快",
					"听人讲话或报告讲得不好,我就着急,总想还不如我来", "即使有人欺侮了我,我也不在乎", "我有时会把今天该做的事拖到明天去做", "人们认为我是一个干脆,利落,高效率的人",
					"有人对我的工作吹毛求疵时,很容易挫伤我的积极性", "我常常感到时间已经晚了,可一看表还早呢", "我觉得我是一个非常敏感的人", "我做事匆匆忙忙的,力图用最少的时间办尽量多的事情",
					"如果犯有错误,不管大小,我全都主动承认", "坐公共汽车时,我常常感到车开得太慢", "无论做什么事,看着别人做不好我也不想拿来替他做", "我常常为工作没做完,一天又过去了而感到忧虑",
					"很多事情如果由我来负责,情况要比现在好得多", "有时我会想到一些说不出口的坏念头", "即使领导我的人能力差,水平低,不怎么样,我也能服从", "必须等待什么的时候,我总是心急如焚,缺乏耐心",
					"我常感到自己能力不够,在做事遇到不顺就想放弃不干", "我每天都看电视,也看电影,不然心里就不舒服", "别人托我办的事,只要答应了,我从不拖延", "人们都说我很有耐心,干什么事都不着急",
					"乘车或跟人约定时间,我很少迟到,如对方耽误就恼火", "偶尔我也会说一两句假话", "许多事本来可以大家分担,可我喜欢一个人去干", "我觉得别人对我的话理解慢,甚至理解不了我的意思",
					"我是一个性子暴躁的人", "我常常容易看到别人的短处而忽视别人的长处" };
		}

		if ("2".equals(evaluateType)) {
			sheet = workbook.createSheet("PSTRI压力");
			headers = new String[] { "用户编码", "姓名", "性别", "评估年龄", "单位", "评估时间", "我受背痛之苦", "我的睡眠不定，且睡不安稳", "我头痛", "我颚部疼痛",
					"若需等候，我会不安", "我的后颈感到疼痛", "我比多数人更神经紧张", "我很难入睡", "我的头感到紧或痛", "我的胃有病", "我对自己没有信心", "我对自己说话",
					"我忧虑财务问题", "与人见面时我会窘怯", "我怕发生可怕的事", "白天我觉得累", "下午我感到喉咙痛但并非染上感冒", "我心情不安，无法静坐", "我感到非常口干", "我心脏有病",
					"我觉得自己不是很有用", "我吸烟", "我肚子不舒服", "我觉得不快乐", "我流汗", "我喝酒", "我很自觉", "我觉得自己像四分五裂", "我的眼睛又酸又累", "我的腿或脚抽筋",
					"我的心跳过速", "我怕结识人", "我手脚冰冷", "我患便秘", "我未经医师指示使用各种药物", "我发现自己很容易哭", "我消化不良", "我咬指甲", "我耳中有嗡嗡声",
					"我小便频密", "我有胃溃疡", "我有皮肤方面的病", "我的咽喉很紧", "我有十二指肠溃疡病", "我担心我的工作", "我口腔溃烂", "我为琐事忧虑", "我呼吸浅促",
					"我觉得胸部紧迫", "我发现很难做决定" };
		}

		if ("3".equals(evaluateType)) {
			sheet = workbook.createSheet("心理健康");
			headers = new String[] { "用户编码", "姓名", "性别", "评估年龄", "单位", "评估时间", "害羞 ", "为丢脸而烦恼很久", "登高怕从高处跌下来", "易伤感",
					"做事常半途而废", "无故悲欢", "白天常想入非非", "行路故意避见某人", "易对娱乐厌倦", "易气馁", "感到事事不如意", "常喜欢独处", "讨厌别人看你做事，虽然做得很好",
					"对批评毫不介意", "易改变兴趣", "感到自己有许多不足", "常感到不高兴", "常感到寂寞", "觉得心里难过、痛苦", "在长辈或权威面前很不自然", "缺乏自信", "工作有预定计划",
					"做事心中无主见", "做事有强迫感", "自认运气好", "常有重复思想", "不喜欢进入地道或地下室", "想自杀", "觉得有人故意找你碴儿", "易发火、烦恼", "易对工作产生厌倦",
					"迟疑不决", "寻求人家同情", "不易结交朋友", "心理懊丧影响工作", "可怜自己", "梦见性的活动", "在许多境遇中感到害怕", "觉得智力不如他人", "为性的问题而烦恼",
					"遭遇失败", "心神不定", "为琐事而烦恼", "怕死", "自己觉得自己有罪", "想谋害人" };

		}
		// 在excel表中添加表头
		HSSFRow row = sheet.createRow(0);

		// 列数
		/*
		 * int cno=exportDataList.get(0).getClass().getDeclaredFields().length;
		 */
		// 合并单元格

		// 设置行高
		row.setHeightInPoints(36);
		// 设置列宽度
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 10 * 256);
		sheet.setColumnWidth(2, 10 * 256);

		// 在excel表中添加表头
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			// 设置为居中加粗
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());// 设置背景色
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			style.setBorderBottom(BorderStyle.THIN); // 下边框
			style.setBorderLeft(BorderStyle.THIN);// 左边框
			style.setBorderTop(BorderStyle.THIN);// 上边框
			style.setBorderRight(BorderStyle.THIN);// 右边框

			font.setBold(true);
			style.setFont(font);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
			cell.setCellStyle(style);

		}

		java.util.Date mydate = null;
		// 在表中存放查询到的数据放入对应的列
		YhUserReginfo yhUserReginfo = yhUserReginfoService.selectByPrimaryKey(usercode);
		Date birthDate = yhUserReginfo.getBirthDate();
		System.out.println(birthDate + "出生日期");
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String birthDate2 = myFormatter.format(birthDate);
		if (birthDate2 != null && !"".equals(birthDate2)) {
			mydate = myFormatter.parse(birthDate2);

		}

		YhUserMakeCard userMakeCard = yhUserMakeCardService.selectOrgIdbyParam(usercode);

		String orgName = "";
		if (userMakeCard.getOrgId() != null) {
			orgName = yhOrgTreeService.selectFullOrgName(userMakeCard.getOrgId().toString(), orgName);

		}

		YhPressureEvaluate yhPressureEvaluate = yhPressureEvaluateService.selectYhPressureEvaluateData(usercode, id);


		String[] evaluateData =null;
		if(yhPressureEvaluate.getEvaluateType() ==3){
			String substring = yhPressureEvaluate.getEvaluateSelect();

			if(StringUtil.isEmpty(substring)){
				return;
			}

			//判断第一个字符是否为“[”
			if (substring.startsWith("[")) {
				substring = substring.substring(1);
			}

			//判断最后一个字符是否为“]”
			if (substring.endsWith("]")) {
				substring = substring.substring(0,substring.length() - 1);
			}

			evaluateData = substring.split(",");

		}else{
			String substring = yhPressureEvaluate.getEvaluateData().substring(0,
					yhPressureEvaluate.getEvaluateData().length() - 1);
			String str2 = substring.substring(1, substring.length());

			 evaluateData = str2.split(",");
		}

		System.out.println("数据" + evaluateData);


		// 创建excel中的行
		HSSFRow row1 = sheet.createRow(rowNum);
		// 在表中存放查询到的数据放入对应的列
		row1.createCell(0).setCellValue(yhUserReginfo.getUserCode());
		row1.createCell(1).setCellValue(yhUserReginfo.getName());
		row1.createCell(2).setCellValue(yhUserReginfo.getGender() == 1 ? "男" : "女");
		row1.createCell(3).setCellValue(getAge(mydate));
		row1.createCell(4).setCellValue(orgName);
		row1.createCell(5).setCellValue(yhPressureEvaluate.getEvaluateDate());

		if (yhPressureEvaluate.getEvaluateType() == 1) {
			row1.createCell(6).setCellValue(Integer.valueOf(evaluateData[0]) == 0 ? "是" : "否");

			row1.createCell(7).setCellValue(Integer.valueOf(evaluateData[1]) == 1 ? "是" : "否");
			row1.createCell(8).setCellValue(Integer.valueOf(evaluateData[2]) == 1 ? "是" : "否");
			row1.createCell(9).setCellValue(Integer.valueOf(evaluateData[3]) == 1 ? "是" : "否");
			row1.createCell(10).setCellValue(Integer.valueOf(evaluateData[4]) == 1 ? "是" : "否");
			row1.createCell(11).setCellValue(Integer.valueOf(evaluateData[5]) == 1 ? "是" : "否");
			row1.createCell(12).setCellValue(Integer.valueOf(evaluateData[6]) == 1 ? "是" : "否");
			row1.createCell(13).setCellValue(Integer.valueOf(evaluateData[7]) == 1 ? "是" : "否");
			row1.createCell(14).setCellValue(Integer.valueOf(evaluateData[8]) == 1 ? "是" : "否");
			row1.createCell(15).setCellValue(Integer.valueOf(evaluateData[9]) == 1 ? "是" : "否");
			row1.createCell(16).setCellValue(Integer.valueOf(evaluateData[10]) == 1 ? "是" : "否");
			row1.createCell(17).setCellValue(Integer.valueOf(evaluateData[11]) == 1 ? "是" : "否");

			row1.createCell(18).setCellValue(Integer.valueOf(evaluateData[12]) == 0 ? "是" : "否");
			row1.createCell(19).setCellValue(Integer.valueOf(evaluateData[13]) == 0 ? "是" : "否");

			row1.createCell(20).setCellValue(Integer.valueOf(evaluateData[14]) == 1 ? "是" : "否");
			row1.createCell(21).setCellValue(Integer.valueOf(evaluateData[15]) == 1 ? "是" : "否");
			row1.createCell(22).setCellValue(Integer.valueOf(evaluateData[16]) == 1 ? "是" : "否");
			row1.createCell(23).setCellValue(Integer.valueOf(evaluateData[17]) == 0 ? "是" : "否");
			row1.createCell(24).setCellValue(Integer.valueOf(evaluateData[18]) == 0 ? "是" : "否");

			row1.createCell(25).setCellValue(Integer.valueOf(evaluateData[19]) == 1 ? "是" : "否");
			row1.createCell(26).setCellValue(Integer.valueOf(evaluateData[20]) == 1 ? "是" : "否");
			row1.createCell(27).setCellValue(Integer.valueOf(evaluateData[21]) == 1 ? "是" : "否");
			row1.createCell(28).setCellValue(Integer.valueOf(evaluateData[22]) == 1 ? "是" : "否");
			row1.createCell(29).setCellValue(Integer.valueOf(evaluateData[23]) == 1 ? "是" : "否");
			row1.createCell(30).setCellValue(Integer.valueOf(evaluateData[24]) == 1 ? "是" : "否");
			row1.createCell(31).setCellValue(Integer.valueOf(evaluateData[25]) == 1 ? "是" : "否");
			row1.createCell(32).setCellValue(Integer.valueOf(evaluateData[26]) == 1 ? "是" : "否");
			row1.createCell(33).setCellValue(Integer.valueOf(evaluateData[27]) == 1 ? "是" : "否");
			row1.createCell(34).setCellValue(Integer.valueOf(evaluateData[28]) == 1 ? "是" : "否");

			row1.createCell(35).setCellValue(Integer.valueOf(evaluateData[29]) == 0 ? "是" : "否");
			row1.createCell(36).setCellValue(Integer.valueOf(evaluateData[30]) == 1 ? "是" : "否");
			row1.createCell(37).setCellValue(Integer.valueOf(evaluateData[31]) == 1 ? "是" : "否");
			row1.createCell(38).setCellValue(Integer.valueOf(evaluateData[32]) == 0 ? "是" : "否");
			row1.createCell(39).setCellValue(Integer.valueOf(evaluateData[33]) == 1 ? "是" : "否");
			row1.createCell(40).setCellValue(Integer.valueOf(evaluateData[34]) == 1 ? "是" : "否");
			row1.createCell(41).setCellValue(Integer.valueOf(evaluateData[35]) == 0 ? "是" : "否");
			row1.createCell(42).setCellValue(Integer.valueOf(evaluateData[36]) == 0 ? "是" : "否");

			row1.createCell(43).setCellValue(Integer.valueOf(evaluateData[37]) == 1 ? "是" : "否");
			row1.createCell(44).setCellValue(Integer.valueOf(evaluateData[38]) == 1 ? "是" : "否");
			row1.createCell(45).setCellValue(Integer.valueOf(evaluateData[39]) == 1 ? "是" : "否");
			row1.createCell(46).setCellValue(Integer.valueOf(evaluateData[40]) == 1 ? "是" : "否");
			row1.createCell(47).setCellValue(Integer.valueOf(evaluateData[41]) == 1 ? "是" : "否");
			row1.createCell(48).setCellValue(Integer.valueOf(evaluateData[42]) == 1 ? "是" : "否");
			row1.createCell(49).setCellValue(Integer.valueOf(evaluateData[43]) == 1 ? "是" : "否");

			row1.createCell(50).setCellValue(Integer.valueOf(evaluateData[44]) == 0 ? "是" : "否");
			row1.createCell(51).setCellValue(Integer.valueOf(evaluateData[45]) == 1 ? "是" : "否");
			row1.createCell(52).setCellValue(Integer.valueOf(evaluateData[46]) == 1 ? "是" : "否");
			row1.createCell(53).setCellValue(Integer.valueOf(evaluateData[47]) == 0 ? "是" : "否");
			row1.createCell(54).setCellValue(Integer.valueOf(evaluateData[48]) == 0 ? "是" : "否");

			row1.createCell(55).setCellValue(Integer.valueOf(evaluateData[49]) == 1 ? "是" : "否");
			row1.createCell(56).setCellValue(Integer.valueOf(evaluateData[50]) == 0 ? "是" : "否");
			row1.createCell(57).setCellValue(Integer.valueOf(evaluateData[51]) == 1 ? "是" : "否");
			row1.createCell(58).setCellValue(Integer.valueOf(evaluateData[52]) == 1 ? "是" : "否");
			row1.createCell(59).setCellValue(Integer.valueOf(evaluateData[53]) == 0 ? "是" : "否");

			row1.createCell(60).setCellValue(Integer.valueOf(evaluateData[54]) == 1 ? "是" : "否");
			row1.createCell(61).setCellValue(Integer.valueOf(evaluateData[55]) == 0 ? "是" : "否");
			row1.createCell(62).setCellValue(Integer.valueOf(evaluateData[56]) == 1 ? "是" : "否");
			row1.createCell(63).setCellValue(Integer.valueOf(evaluateData[57]) == 1 ? "是" : "否");
			row1.createCell(64).setCellValue(Integer.valueOf(evaluateData[58]) == 1 ? "是" : "否");
			row1.createCell(65).setCellValue(Integer.valueOf(evaluateData[59]) == 1 ? "是" : "否");
			// rowNum++;
		}
		if (yhPressureEvaluate.getEvaluateType() == 2) {
			row1.createCell(0).setCellValue(yhUserReginfo.getUserCode());
			row1.createCell(1).setCellValue(yhUserReginfo.getName());
			row1.createCell(2).setCellValue(yhUserReginfo.getGender() == 1 ? "男" : "女");
			row1.createCell(3).setCellValue(getAge(mydate));
			row1.createCell(4).setCellValue(orgName);
			row1.createCell(5).setCellValue(yhPressureEvaluate.getEvaluateDate());
			for (int i = 0; i < evaluateData.length; i++) {
				row1.createCell(i + 6)
						.setCellValue(Integer.valueOf(evaluateData[i]) == 0 ? "总是"
								: Integer.valueOf(evaluateData[i]) == 1 ? "经常"
										: (Integer.valueOf(evaluateData[i]) == 2 ? "有时"
												: (Integer.valueOf(evaluateData[i]) == 3 ? "很少"
														: (Integer.valueOf(evaluateData[i]) == 4 ? "从不" : ""))));
			}
		}

		if (yhPressureEvaluate.getEvaluateType() == 3) {
			row1.createCell(0).setCellValue(yhUserReginfo.getUserCode());
			row1.createCell(1).setCellValue(yhUserReginfo.getName());
			row1.createCell(2).setCellValue(yhUserReginfo.getGender() == 1 ? "男" : "女");
			row1.createCell(3).setCellValue(getAge(mydate));
			row1.createCell(4).setCellValue(orgName);
			row1.createCell(5).setCellValue(yhPressureEvaluate.getEvaluateDate());
			for (int i=0;i<46;i++){
				row1.createCell(i+6).setCellValue(selectInfo(evaluateData[i]));
			}
		}

		// 必须在单元格设值以后进行
		// 设置为根据内容自动调整列宽
		for (int k = 0; k < exportDataList.size(); k++) {
			sheet.autoSizeColumn(k);
		}
		// 处理中文不能自动调整列宽的问题
		setSizeColumn(sheet, exportDataList.size());

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		/*
		 * response.setHeader("Content-disposition", "attachment;filename=" +
		 * fileName);
		 */
		response.flushBuffer();
		workbook.write(response.getOutputStream());

	}

	private void setSizeColumn(HSSFSheet sheet, int size) {
		for (int columnNum = 0; columnNum < size; columnNum++) {
			int columnWidth = sheet.getColumnWidth(columnNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				HSSFRow currentRow;
				// 当前行未被使用过
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}
				if (currentRow.getCell(columnNum) != null) {
					HSSFCell currentCell = currentRow.getCell(columnNum);
					if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						int length = currentCell.getStringCellValue().getBytes().length;
						if (columnWidth < length) {
							columnWidth = length;
						}
					}
				}
			}
			sheet.setColumnWidth(columnNum, columnWidth * 256);
		}

	}

	public static int getAge(Date dateOfBirth) {

		int age = 0;

		Calendar born = Calendar.getInstance();

		Calendar now = Calendar.getInstance();

		if (dateOfBirth != null) {

			now.setTime(new Date());

			born.setTime(dateOfBirth);

			if (born.after(now)) {

				throw new IllegalArgumentException("年龄不能超过当前日期");

			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);

			int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);

			int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);

			System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);

			if (nowDayOfYear < bornDayOfYear) {

				age -= 1;

			}

		}

		return age;

	}

	/**
	 * 根据出生年月计算年龄
	 * 
	 * @param birthday
	 * @return
	 */
	private int getAgeByBirth(Date birthday) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间

			Calendar birth = Calendar.getInstance();
			birth.setTime(birthday);

			if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
					age += 1;
				}
			}
			return age;
		} catch (Exception e) {// 兼容性更强,异常后返回数据
			return 0;
		}
	}

}
