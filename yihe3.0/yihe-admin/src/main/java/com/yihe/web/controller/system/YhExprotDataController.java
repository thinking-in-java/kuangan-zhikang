package com.yihe.web.controller.system;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.yihe.common.utils.poi.ExcelUtilQ;
import com.yihe.system.domain.*;
import com.yihe.system.service.YhExprotDataService;
import com.yihe.system.utils.GetAge;
import com.yihe.system.utils.Result;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Exprot")
public class YhExprotDataController {
	@Autowired
	YhExprotDataService yhexportservice;

	@RequestMapping(value = "/selectorg", method = RequestMethod.GET)
	@ResponseBody
	public Result selectorg() {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		return Result.ok(yhexportservice.selectorg(yh.getOrgCode()));
	}

	@RequestMapping(value = "/selectdoctororg", method = RequestMethod.GET)
	@ResponseBody
	public Result selectdoctororg() {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		return Result.ok(yhexportservice.selectorg(yh.getOrgCode()));
	}

	@RequestMapping(value = "/exportnow", method = RequestMethod.GET)
	public void export(YhExportData yhexportnow, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		if (yh.getUserType()==4){
			yhexportnow.setUserCode(yh.getUserCode());
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建标题
		List<List<String>> titles = new ArrayList<List<String>>();
		// 创建sheet名
		List<String> sheetname = new ArrayList<String>();
		List<YhUserReginfoVOJO> reginfo = new ArrayList<YhUserReginfoVOJO>();
		List<YhExportCheck> check = new ArrayList<YhExportCheck>();
		//健康评估结果
		List<YhExportHealthAssess> riskresult= new ArrayList<YhExportHealthAssess>();
		//健康评估结果2
		List<yhDiseaseAssessTotalReport2> riskresult2=new ArrayList<yhDiseaseAssessTotalReport2>();
		List<yhDiseaseAssessTotalReport2> riskresultei=new ArrayList<yhDiseaseAssessTotalReport2>();
		List<YhExportApressure> exportapre= new ArrayList<YhExportApressure>();
		List<YhExportApressure> exportrstr= new ArrayList<YhExportApressure>();
		List<YhExportApressure> exportxinli= new ArrayList<YhExportApressure>();
		List<YhExprotBloodPressure> bloodpressure= new ArrayList<YhExprotBloodPressure>();
		List<YhExprotBloodSuger> bloodsuger= new ArrayList<YhExprotBloodSuger>();
		List<YhExportBodyFat> bodyfat= new ArrayList<YhExportBodyFat>();
		List<YhExprotBMI> bmi= new ArrayList<YhExprotBMI>();
		List<YhExprotBloodFat> bloofat= new ArrayList<YhExprotBloodFat>();
		List<YhExportLiver> liver= new ArrayList<YhExportLiver>();
		List<YhExportUserUBA> uba= new ArrayList<YhExportUserUBA>();
		List<YhExportOther> other= new ArrayList<YhExportOther>();
		List<YhExportRenalFunction> renalfuction=new ArrayList<>();
		List<String> all=new ArrayList<String>();
		List<String> biaoti=new ArrayList<String>();
		YhExportAll exportall=new YhExportAll(); 
		List<YhExportAll> lastexportall=new ArrayList<YhExportAll>();
		List<YhUserArtery> yhUserArteries=new ArrayList<YhUserArtery>();
		if (yhexportnow.getReginfo() != null) {
			reginfo = yhexportservice.exportnow(yhexportnow);
			List<String> titi = Arrays.asList("用户编码", "姓名", "性别", "出生日期", "手机号", "单位", "注册时间");
			titles.add(titi);
			sheetname.add("用户信息");
		}
		if (yhexportnow.getJiankangwen() != null) {
			sheetname.add("健康评估问卷");
			List<String> titis = Arrays.asList("用户编码", "姓名", "性别", "评估年龄", "单位", "填写时间","您的居住地","所住城市空气质量","高血压","脑卒中","冠心病","严重心律失常","不稳定心绞痛","动脉硬化/狭窄/闭塞","未控制的有症状的心律衰竭","左心室肥大","房颤","其他急性心脏事件","糖尿病（1或2型）","痛风急性发作期","痛风非急性发作期","慢性支气管炎	","肺气肿","肺结核","肺癌","骨质疏松症","既往骨折","其他限制运动的疾病（如关节炎、足部溃疡、肌肉韧带损伤等）","引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、慢性营养不良等）","前列腺炎或肥大","前列腺癌","乳腺癌（男性也有患病几率）","尿路感染","尿频、尿痛、尿不尽、排尿困难等症状","尿血或尿路结石","您第一次来月经的年龄是多大？","您是否生育过？","您生第一胎年龄是多大？","您的孩子是母乳喂养吗？","您的哺乳时间总共有多长时间（累计）","您是否绝经","您的绝经年龄是多大？","您绝经后使用过雌激素替代疗法吗？","您绝经后体重上升超过5公斤吗？","您绝经后使用雌激素替代疗法的时长","长期口服避孕药","长期使用丰乳产品或做过乳房整形手术","曾有子宫、卵巢和输卵管等的手术","是否患过类风湿关节炎","您的居住环境","您的家族中是否有人患有糖尿病","您的家族中是否有人患高血压","您的家族中是否有人患冠心病","您的家族中是否有人患脑卒中","您的家族中是否有人患髋部骨折","您的家族中是否有人患肺癌","您的家族中是否有人患前列腺癌 ","您的家族中是否有人患乳腺癌","您的家族中是否有人患过痛风","服用降压药","注射肾上腺皮质激素","您平均每天吃多少新鲜蔬菜？（1碗炒熟的青菜≈300克）","您平均每天吃多少新鲜水果？（一个中等大小苹果≈200克）","您平均每天喝几杯牛奶或酸奶？（240毫升≈1杯）","您平均每天吃多少奶制品(如奶酪、奶粉等)？（一片奶酪≈40克,一勺奶粉≈5克）","您通常每周吃几次油炸食品（如油饼、油条、炸糕等）","您通常每周吃几次点心或甜食（如蛋糕、巧克力、饼干及各种烘培点心等）?","您通常每周喝几次含糖饮料（如可乐、果汁饮料等）吗？","您通常每周吃几次咸菜或腌渍食品（如咸鱼）吗？","您每周吃虾、蟹、蛤蜊等海鲜次数是","您每天饮茶的次数是","您喝酒吗？(这里喝酒是指平均每周饮酒1次以上）","您每周喝几次酒？","您平均每次喝多少啤酒?","您是否吸烟","您平均每日吸烟量为支？","您开始戒烟的年龄为 ","无论在家还是工作场所，您经常吸二手烟吗","您在过去3个月中是否参加过运动锻炼","一般情况下您参加运动锻炼的频率是？","一般情况下您每次锻炼的持续时间是？","近期（2年）是否新房装修","最近两周，您感到精神紧张，很难放松吗？","您的睡眠时间是？","您的月收入？","有油烟接触史（长期厨房做菜或为厨师职业）","长期从事化工、染料、橡胶及印刷等职业","粉尘接触史（如：矿工、环卫工人等或长期接触过汽油等有害物质）","石棉接触史（长期接触过石棉）","您的血脂四项检查结果","您的肾功能检查结果","您的其它医学指标检查结果 ","您的月经是否规律");
			check = yhexportservice.exportcheck(yhexportnow);
			titles.add(titis);
		}
		if (yhexportnow.getJiankangpinggu() != null) {
			sheetname.add("健康评估结果");
			List<String> titiggu = Arrays.asList("用户编码", "姓名", "性别", "评估年龄", "单位", "评估时间","BMI","饮食情况蔬菜水果","饮食情况高脂饮食","饮食情况高嘌呤饮食","饮食情况反式脂肪酸摄入过量","运动水平","饮酒情况","精神压力","冠心病当前风险（‰）","冠心病理想风险（‰）","冠心病风险等级","脑卒中理想风险（/10万）","脑卒中理想风险（/10万）","脑卒中风险等级","高血压当前风险（‰）","高血压理想风险（‰）","高血压风险等级","糖尿病当前风险（‰）","糖尿病理想风险（‰）","糖尿病风险等级","痛风当前风险（‰）","痛风理想风险（‰）","痛风风险等级","主要部位骨折风险（‰）","髋部骨折风险（‰）","骨质疏松症风险等级","肺癌当前风险（‰）","肺癌理想风险（‰）","肺癌风险等级","前列腺癌当前风险（/10万）","前列腺癌理想风险（/10万）","前列腺癌风险等级","乳腺癌当前风险（‰）","乳腺癌理想风险（‰）","乳腺癌风险等级","血脂异常","全身性肥胖","中心性肥胖");
			//riskresult = yhexportservice.exportassessreport(yhexportnow);
			riskresult2=yhexportservice.exportassessreport2(yhexportnow);
			if(yhexportnow.getBegintime()==null){
				for (int i=riskresult2.size()-1;i>0;i--){
					boolean ishave=false;
					for (int j=0;j<riskresultei.size();j++){
						if(riskresult2.get(i).getUserCode().equals(riskresultei.get(j).getUserCode())){
							ishave=true;
						}
					}
					if(!ishave){
						riskresultei.add(riskresult2.get(i));
					}
				}
				riskresult2=riskresultei;
			}
			titles.add(titiggu);
		}
		if (yhexportnow.getAxingwen() != null) {
			sheetname.add("A型性格评估问卷");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","填写时间","我总是力图说服别人同意我的观点","即使没有什么要紧的事,我走路也快","我经常感到应该做的事太多,有压力","我自己决定的事,别人很难让我改变主意","有些人和事常常使我十分恼火","在急需买东西但又要排长队时,我宁愿不买","有些工作我根本安排不过来,只能临时挤时间去做","上班或赴约会时,我从来不迟到","我正在做事,谁打扰我,不管有意无意,我总是感到恼火","我总看不惯那些慢条斯理,不紧不慢的人","我常常忙得透不过气来,因为该做的事情太多了","即使跟别人合作,我也总想单独完成一些更重要的部分","有时我真想骂人","我做事总是喜欢慢慢来,而且思前想后,拿不定主意","排队买东西,要是有人加塞,我就忍不住要指责他","我觉得自己是一个无忧无虑,悠闲自在的人","有时连我都觉得我操心的事远超过我应该操心的范围","无论做什么事,即使比别人差,我也无所谓","做什么事也不着急,着急也没有用,不着急也误不了事","我从来没想过要按自己的想法办事","每天的事情都使我精神十分紧张","逛公园,赏花,观鱼等,我也总是先看完,等着同来的人","我常常不能宽容别人的缺点和毛病","在我认识的人里,个个我都喜欢","听到别人发表不正确的见解,我总想立即就去纠正他","无论做什么事,我都比别人快一些","当别人对我无礼时,我对他也不客气","我总觉得我有能力把一切事情办好","聊天时我也总是急于说出自己想法,甚至打断别人的话","人们认为我是个安静,沉着,有耐心的人","我觉得在我认识的人之中值得我信任和佩服的人不多","我觉得在我认识的人之中值得我信任和佩服的人不多","有时我也会说人家的闲话","尽管时间很宽裕,我吃饭也快","听人讲话或报告讲得不好,我就着急,总想还不如我来","即使有人欺侮了我,我也不在乎","我有时会把今天该做的事拖到明天去做","人们认为我是一个干脆,利落,高效率的人","有人对我的工作吹毛求疵时,很容易挫伤我的积极性","我常常感到时间已经晚了,可一看表还早呢","我觉得我是一个非常敏感的人","我做事匆匆忙忙的,力图用最少的时间办尽量多的事情","如果犯有错误,不管大小,我全都主动承认","坐公共汽车时,我常常感到车开得太慢","无论做什么事,看着别人做不好我也不想拿来替他做","我常常为工作没做完,一天又过去了而感到忧虑","很多事情如果由我来负责,情况要比现在好得多","有时我会想到一些说不出口的坏念头","即使领导我的人能力差,水平低,不怎么样,我也能服从","必须等待什么的时候,我总是心急如焚,缺乏耐心","我常感到自己能力不够,在做事遇到不顺就想放弃不干","我每天都看电视,也看电影,不然心里就不舒服","别人托我办的事,只要答应了,我从不拖延","人们都说我很有耐心,干什么事都不着急","乘车或跟人约定时间,我很少迟到,如对方耽误就恼火","偶尔我也会说一两句假话","许多事本来可以大家分担,可我喜欢一个人去干","我觉得别人对我的话理解慢,甚至理解不了我的意思","我是一个性子暴躁的人","我常常容易看到别人的短处而忽视别人的长处");
			exportapre = yhexportservice.exportapressure(yhexportnow);
			titles.add(titiggua);
		}
		if (yhexportnow.getAxingjie() != null) {
			sheetname.add("A型性格评估结果");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","填写时间","评估分数","评估类型");
			exportapre = yhexportservice.exportapressure(yhexportnow);
			titles.add(titiggua);
		}
		if (yhexportnow.getYaliwen() != null) {
			sheetname.add("RSTRI压力评估问卷");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","填写时间","我受背痛之苦","我的睡眠不定，且睡不安稳","我头痛","我颚部疼痛","若需等候，我会不安","我的后颈感到疼痛","我比多数人更神经紧张","我很难入睡","我的头感到紧或痛","我的胃有病","我对自己没有信心","我对自己说话","我忧虑财务问题","与人见面时我会窘怯","我怕发生可怕的事","白天我觉得累","下午我感到喉咙痛但并非染上感冒","我心情不安，无法静坐","我感到非常口干","我心脏有病","我觉得自己不是很有用","我吸烟","我肚子不舒服","我觉得不快乐","我流汗","我喝酒","我很自觉","我觉得自己像四分五裂","我的眼睛又酸又累","我的腿或脚抽筋","我的心跳过速","我怕结识人","我手脚冰冷","我患便秘","我未经医师指示使用各种药物","我发现自己很容易哭","我消化不良","我咬指甲","我耳中有嗡嗡声","我小便频密","我有胃溃疡","我有皮肤方面的病","我的咽喉很紧","我有十二指肠溃疡病","我担心我的工作","我口腔溃烂","我为琐事忧虑","我呼吸浅促","我觉得胸部紧迫","我发现很难做决定");
			exportrstr = yhexportservice.exportrstripressure(yhexportnow);
			titles.add(titiggua);
		}
		if (yhexportnow.getYalijie() != null) {
			sheetname.add("RSTRI压力评估结果");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","填写时间","评估分数","评估类型");
			exportrstr = yhexportservice.exportrstripressure(yhexportnow);
			titles.add(titiggua);
		}
		if (yhexportnow.getXinliwen() != null) {
			sheetname.add("心理健康评估问卷");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","填写时间","害羞 ","为丢脸而烦恼很久","登高怕从高处跌下来","易伤感","做事常半途而废","无故悲欢","白天常想入非非","行路故意避见某人","易对娱乐厌倦","易气馁","感到事事不如意","常喜欢独处","讨厌别人看你做事，虽然做得很好","对批评毫不介意","易改变兴趣","感到自己有许多不足","常感到不高兴","常感到寂寞","觉得心里难过、痛苦","在长辈或权威面前很不自然","缺乏自信","工作有预定计划","做事心中无主见","做事有强迫感","自认运气好","常有重复思想","不喜欢进入地道或地下室","想自杀","觉得有人故意找你碴儿","易发火、烦恼","易对工作产生厌倦","迟疑不决","寻求人家同情","不易结交朋友","心理懊丧影响工作","可怜自己","梦见性的活动","在许多境遇中感到害怕","觉得智力不如他人","为性的问题而烦恼","遭遇失败","心神不定","为琐事而烦恼","怕死","自己觉得自己有罪","想谋害人");
			exportxinli = yhexportservice.exportxinli(yhexportnow);
			titles.add(titiggua);
		}
		if (yhexportnow.getXinlijie() != null) {
			sheetname.add("心理健康评估结果");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","填写时间","评估分数","评估类型");
			exportxinli = yhexportservice.exportxinli(yhexportnow);
			titles.add(titiggua);
		}
		if (yhexportnow.getDongmaiyinghua() != null) {
			sheetname.add("动脉硬化");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","填写时间","最大血压  收缩压","最小血压 舒张压","脉博，心率","中心动脉硬度指标","上臂动脉硬度指标","中心动脉血压值","中心动脉脉压值");
			yhUserArteries = yhexportservice.exportyhUserArteries(yhexportnow);
			titles.add(titiggua);
		}
		Boolean isfast=false;
		if (yhexportnow.getXuetang()!= null) {
			all.add("血糖");
		}
		if (yhexportnow.getTizhi()!= null) {
			all.add("体脂");
		}
		if(yhexportnow.getXueya()!=null) {
			all.add("血压");
		}
		if(yhexportnow.getBMI()!=null) {
			all.add("BMI");
		}
		if(yhexportnow.getGaomi()!=null) {
			all.add("血脂四项");
		}
		if(yhexportnow.getShegongneng()!=null) {
			all.add("肾功能");
		}
		if(yhexportnow.getGubing()!=null) {
			all.add("肝功能");
		}
		if(yhexportnow.getXueji()!=null) {
			all.add("尿检");
		}
		if(yhexportnow.getNiaoweiliang()!=null) {
			all.add("其它");
		}
		if(all.size()>1) {
			sheetname.add("检测数据汇总表");
			biaoti.add("用户编码");
			biaoti.add("姓名");
			biaoti.add("性别");
			biaoti.add("单位");
			if(yhexportnow.getBegintime()!=null){
				biaoti.add("时间");
			}
			for (int i = 0; i <all.size(); i++) {
				if(all.get(i).equals("血糖")) {
					List<String> bloosu = Arrays.asList("血糖","测量环境","数据录入时间");
					biaoti.addAll(bloosu);
				}
				if(all.get(i).equals("体脂")) {
					List<String> bodyfat1 = Arrays.asList("基础代谢","骨骼","脂肪率","热量","肌肉","蛋白质","内脏水平","水分","数据录入时间");	
					biaoti.addAll(bodyfat1);
				}
				if(all.get(i).equals("血压")) {
					List<String> bloodpre = Arrays.asList("舒张压","收缩压","心率","数据录入时间");
					biaoti.addAll(bloodpre);
				}
				if(all.get(i).equals("BMI")) {
					List<String> BMI = Arrays.asList("身高","体重","BMI指数","数据录入时间");
					biaoti.addAll(BMI);
				}
				if(all.get(i).equals("血脂四项")) {
					List<String> bloodzhi = Arrays.asList("总胆固醇","甘油三酯","高密度脂蛋白胆固醇","低密度脂蛋白胆固醇","数据录入时间");
					biaoti.addAll(bloodzhi);
				}
				if(all.get(i).equals("肝功能")) {
					List<String> gan = Arrays.asList("谷丙转氨酶","谷草转氨酶","血清白蛋白","血清球蛋白","数据录入时间");
					biaoti.addAll(gan);
				}

				if(all.get(i).equals("肾功能")) {
					List<String> shen = Arrays.asList("血肌酐","尿素氮","血尿酸","数据录入时间");
					biaoti.addAll(shen);
				}
				if(all.get(i).equals("尿检")) {
					List<String> iaojian = Arrays.asList("葡萄糖","胆红素","酮体","比重","红细胞","ph值","尿蛋白","尿胆原","亚硝酸盐","白细胞","测量环境","数据录入时间");
					biaoti.addAll(iaojian);
				}
				if(all.get(i).equals("其它")) {
					List<String> others = Arrays.asList("尿微量蛋白","糖化血红和蛋白","同型半胱氨酸","数据录入时间");
					biaoti.addAll(others);
				}
			}
			titles.add(biaoti);
		}
		if (yhexportnow.getXuetang()!= null) {
			sheetname.add("检测数据血糖");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","血糖","测量场景","数据录入时间");
			bloodsuger = yhexportservice.exportbloodsuger(yhexportnow);
			titles.add(titiggua);
			List<YhExprotBloodSuger> allsug=new ArrayList<YhExprotBloodSuger>();
			allsug=yhexportservice.allexportbloodsuger(yhexportnow);
			exportall.setBloodsuger(allsug);
			
			for (int i = 0; i < allsug.size(); i++) {
				YhExportAll manyall=new YhExportAll();
				manyall.setUser_Code(allsug.get(i).getUser_Code());
				manyall.setName(allsug.get(i).getName());
				manyall.setGender(allsug.get(i).getGender());
				manyall.setOrg_Name(allsug.get(i).getOrg_Name());
				manyall.setBs(allsug.get(i).getBs());
				manyall.setBloodsuger_Entry_Time(allsug.get(i).getEntry_Time());
				manyall.setTime(allsug.get(i).getEntry_Time());
				manyall.setBsevn(allsug.get(i).getEnv());
				lastexportall.add(manyall);
				isfast=true;
			}
		}
		if (yhexportnow.getTizhi()!= null) {
			sheetname.add("检测数据体脂");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","基础代谢","骨骼","脂肪率","热量","肌肉","蛋白质","内脏水平","水分","数据录入时间");
			bodyfat = yhexportservice.exportbodyfat(yhexportnow);
			List<YhExportBodyFat> allbodyfat=new ArrayList<YhExportBodyFat>();
			allbodyfat= yhexportservice.allexportbodyfat(yhexportnow);
			exportall.setBodyfat(allbodyfat);
			titles.add(titiggua);
			
			if(!isfast) {
				for (int i = 0; i < allbodyfat.size(); i++) {
					YhExportAll manyall=new YhExportAll();
					manyall.setUser_Code(allbodyfat.get(i).getUser_Code());
					manyall.setName(allbodyfat.get(i).getName());
					manyall.setGender(allbodyfat.get(i).getGender());
					manyall.setOrg_Name(allbodyfat.get(i).getOrg_Name());
					manyall.setFat_rate(allbodyfat.get(i).getFat_rate());
					manyall.setMuscle(allbodyfat.get(i).getMuscle());
					manyall.setBones(allbodyfat.get(i).getBones());
					manyall.setVisceral_level(allbodyfat.get(i).getVisceral_level());
					manyall.setWater(allbodyfat.get(i).getWater());
					manyall.setHeat(allbodyfat.get(i).getHeat());
					manyall.setProtein(allbodyfat.get(i).getProtein());
					manyall.setBasal_met(allbodyfat.get(i).getBasal_met());
					manyall.setTime(allbodyfat.get(i).getEntry_Time());
					lastexportall.add(manyall);
					isfast=true;
				}
			}
		}
		if(yhexportnow.getXueya()!=null) {
			sheetname.add("检测数据血压");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","舒张压","收缩压","心率","数据录入时间");
			bloodpressure = yhexportservice.exportbloodpressure(yhexportnow);
			titles.add(titiggua);
			List<YhExprotBloodPressure> pressure= new ArrayList<YhExprotBloodPressure>();
			pressure=yhexportservice.allexportbloodpressure(yhexportnow);
			exportall.setBloodpressure(pressure);
			
			if(!isfast) {
				for (int i = 0; i < pressure.size(); i++) {
					YhExportAll manyall=new YhExportAll();
					manyall.setUser_Code(pressure.get(i).getUser_Code());
					manyall.setName(pressure.get(i).getName());
					manyall.setGender(pressure.get(i).getGender());
					manyall.setOrg_Name(pressure.get(i).getOrg_Name());
					manyall.setSbp(pressure.get(i).getSbp());
					manyall.setDbp(pressure.get(i).getDbp());
					if(pressure.get(i).getXinlv()!=null){
						manyall.setXinlv(Integer.valueOf(pressure.get(i).getXinlv())==0?"":pressure.get(i).getXinlv());
					}

					manyall.setTime(pressure.get(i).getEntry_Time());
					lastexportall.add(manyall);
					isfast=true;
				}
			}
		}
		if(yhexportnow.getBMI()!=null) {
			sheetname.add("检测数据BMI");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","身高","体重","BMI指数","数据录入时间");
			bmi = yhexportservice.exportbmi(yhexportnow);
			titles.add(titiggua);
			List<YhExprotBMI> allbmi= new ArrayList<YhExprotBMI>();
			allbmi=yhexportservice.allexportbmi(yhexportnow);
			exportall.setBmi(allbmi);
			
			if(!isfast) {
				for (int i = 0; i < allbmi.size(); i++) {
					YhExportAll manyall=new YhExportAll();
					manyall.setUser_Code(allbmi.get(i).getUser_Code());
					manyall.setName(allbmi.get(i).getName());
					manyall.setGender(allbmi.get(i).getGender());
					manyall.setOrg_Name(allbmi.get(i).getOrg_Name());
					manyall.setU_Height(allbmi.get(i).getU_Height());
					manyall.setU_Weight(allbmi.get(i).getU_Weight());
					manyall.setTime(allbmi.get(i).getEntry_Time());
					manyall.setBmi_Entry_Time(allbmi.get(i).getEntry_Time());
					lastexportall.add(manyall);
					isfast=true;
				}
			}
		}
		if(yhexportnow.getGaomi()!=null) {
			sheetname.add("检测数据血脂四项");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","总胆固醇","甘油三酯","高密度脂蛋白胆固醇","低密度脂蛋白胆固醇","数据录入时间");
			bloofat = yhexportservice.exportbloodfat(yhexportnow);
			titles.add(titiggua);
			List<YhExprotBloodFat> bloodfats=new ArrayList<YhExprotBloodFat>();
			bloodfats=yhexportservice.allexportbloodfat(yhexportnow);
			exportall.setBloofat(bloodfats);
			
			if(!isfast) {
				for (int i = 0; i < bloodfats.size(); i++) {
					YhExportAll manyall=new YhExportAll();
					manyall.setUser_Code(bloodfats.get(i).getUser_Code());
					manyall.setName(bloodfats.get(i).getName());
					manyall.setGender(bloodfats.get(i).getGender());
					manyall.setOrg_Name(bloodfats.get(i).getOrg_Name());
					manyall.setCholesterin(bloodfats.get(i).getCholesterin());
					manyall.setTring(bloodfats.get(i).getTring());
					manyall.setHdl(bloodfats.get(i).getHdl());
					manyall.setLdl(bloodfats.get(i).getLdl());
					manyall.setTime(bloodfats.get(i).getEntry_Time());
					lastexportall.add(manyall);
					isfast=true;
				}
			}
		}
		if(yhexportnow.getShegongneng()!=null){
			List<String> shengongneng = Arrays.asList("用户编码", "姓名", "性别","单位","血肌酐","尿素氮","血尿酸","数据录入时间");
			sheetname.add("肾功能");
			renalfuction=yhexportservice.renal(yhexportnow);
			List<YhExportRenalFunction> renalFunctions=yhexportservice.allexportfunction(yhexportnow);
			titles.add(shengongneng);
			exportall.setYhExportRenalFunctions(renalFunctions);
			if(!isfast) {
				for (int i = 0; i < renalFunctions.size(); i++) {
					YhExportAll manyall=new YhExportAll();
					manyall.setUser_Code(renalFunctions.get(i).getUser_Code());
					manyall.setName(renalFunctions.get(i).getName());
					manyall.setGender(renalFunctions.get(i).getGender());
					manyall.setOrg_Name(renalFunctions.get(i).getOrg_Name());
					manyall.setUrea_nitrogen(renalFunctions.get(i).getUrea_nitrogen());
					manyall.setUric_acid(renalFunctions.get(i).getUric_acid());
					manyall.setCreatinine(renalFunctions.get(i).getCreatinine());
					manyall.setTime(renalFunctions.get(i).getInertTime());
					lastexportall.add(manyall);
					isfast=true;
				}
			}
		}
		if(yhexportnow.getGubing()!=null) {
			sheetname.add("肝功能");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","谷丙转氨酶","谷草转氨酶","血清白蛋白","血清球蛋白","数据录入时间");
			liver = yhexportservice.exportliver(yhexportnow);
			List<YhExportLiver> livers=yhexportservice.allexportliver(yhexportnow);
			titles.add(titiggua);
			exportall.setLiver(livers);
			
			if(!isfast) {
				for (int i = 0; i < livers.size(); i++) {
					YhExportAll manyall=new YhExportAll();
					manyall.setUser_Code(livers.get(i).getUser_Code());
					manyall.setName(livers.get(i).getName());
					manyall.setGender(livers.get(i).getGender());
					manyall.setOrg_Name(livers.get(i).getOrg_Name());
					manyall.setGu_Bing(livers.get(i).getGu_Bing());
					manyall.setMillet_straw(livers.get(i).getMillet_straw());
					manyall.setSerum_albumin(livers.get(i).getSerum_albumin());
					manyall.setSerum_globulin(livers.get(i).getSerum_globulin());
					manyall.setTime(livers.get(i).getInsertTime());
					lastexportall.add(manyall);
					isfast=true;
				}
			}
		}
		
		
		if(yhexportnow.getXueji()!=null) {
			sheetname.add("尿检");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","葡萄糖","胆红素","酮体","比重","红细胞","ph值","尿蛋白","尿胆原","亚硝酸盐","白细胞","测量环境","数据录入时间");
			uba = yhexportservice.exportuba(yhexportnow);
			titles.add(titiggua);
			List<YhExportUserUBA> ubas=new ArrayList<YhExportUserUBA>();
			ubas=yhexportservice.allexportuba(yhexportnow);
			exportall.setUba(ubas);
			
			if(!isfast) {
				for (int i = 0; i < ubas.size(); i++) {
					YhExportAll manyall=new YhExportAll();
					manyall.setUser_Code(ubas.get(i).getUser_Code());
					manyall.setName(ubas.get(i).getName());
					manyall.setGender(ubas.get(i).getGender());
					manyall.setOrg_Name(ubas.get(i).getOrg_Name());
					manyall.setTime(ubas.get(i).getAddDate());
					manyall.setEnv(ubas.get(i).getEnv());
					manyall.setGlucose(ubas.get(i).getGlucose());
					manyall.setBilirubin(ubas.get(i).getBilirubin());
					manyall.setKetone(ubas.get(i).getKetone());
					manyall.setProportion(ubas.get(i).getProportion());
					manyall.setRedBloodCell(ubas.get(i).getRedBloodCell());
					manyall.setPh(ubas.get(i).getPh());
					manyall.setUrineProtein(ubas.get(i).getUrineProtein());
					manyall.setUrobilinogen(ubas.get(i).getUrobilinogen());
					manyall.setNitrite(ubas.get(i).getNitrite());
					manyall.setWhiteBloodCell(ubas.get(i).getWhiteBloodCell());
					lastexportall.add(manyall);
					isfast=true;
				}
			}
		}
		if(yhexportnow.getNiaoweiliang()!=null) {
			sheetname.add("其它");
			List<String> titiggua = Arrays.asList("用户编码", "姓名", "性别","单位","尿微量蛋白","糖化血红和蛋白","同型半胱氨酸","数据录入时间");
			other = yhexportservice.other(yhexportnow);
			List<YhExportOther> others=new ArrayList<YhExportOther>();
			others=yhexportservice.allother(yhexportnow);
			titles.add(titiggua);
			exportall.setOther(others);
			
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < sheetname.size(); i++) {
			// 创建sheet
			HSSFSheet sheet = workbook.createSheet(sheetname.get(i));
			// 循环标题（多页结构）
				if(sheetname.get(i).equals("检测数据汇总表")){
					ExcelUtilQ.createTitles(workbook, sheet, titles.get(i),i);
				}else{
					ExcelUtilQ.createTitle(workbook, sheet, titles.get(i),i);
				}

				if (sheetname.get(i).equals("用户信息")) {
					// 循环表数据
					for (int j = 0; j < reginfo.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(reginfo.get(j).getUser_code());
						row.createCell(1).setCellValue(reginfo.get(j).getUser_name());
						row.createCell(2).setCellValue(reginfo.get(j).getGender() == 1 ? "男" : "女");
						if(reginfo.get(j).getBirth_date()==null){
							row.createCell(3).setCellValue("--");
						}else{
							row.createCell(3).setCellValue(sdfs.format(StringToDate(reginfo.get(j).getBirth_date())));
						}

						row.createCell(4).setCellValue(reginfo.get(j).getMobile());
						row.createCell(5).setCellValue(reginfo.get(j).getOrg_Name());
						row.createCell(6).setCellValue(sdf.format(reginfo.get(j).getReg_time()));
					}
				}
				if (sheetname.get(i).equals("健康评估问卷")) {
					
					// 循环表数据
					for (int j = 0; j < check.size(); j++) {
						System.out.println(j);
						Object document = Configuration.defaultConfiguration().jsonProvider().parse(check.get(j).getUser_check_answer());
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(check.get(j).getUser_code());
						row.createCell(1).setCellValue(check.get(j).getName());
						row.createCell(2).setCellValue(check.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(GetAge.GetAgeByBirth(check.get(j).getBirth_date(), check.get(j).getUser_check_time()));
						row.createCell(4).setCellValue(check.get(j).getOrg_Name());
						row.createCell(5).setCellValue(sdf.format(check.get(j).getUser_check_time()));
							if(JsonPath.read(document, "$.person.jzd").equals("2")){
								if(JsonPath.read(document, "$.person.jzhj").equals("1")){
									row.createCell(6).setCellValue("北方城市");
								}else{
									row.createCell(6).setCellValue("北方农村");
								}
							}else{
								if(JsonPath.read(document, "$.person.jzhj").equals("1")){
									row.createCell(6).setCellValue("南方城市");
								}else{
									row.createCell(6).setCellValue("南方农村");
								}
							}
							row.createCell(7).setCellValue(Integer.valueOf(JsonPath.read(document, "$.pm25"))==1?"优良":"差");
						if(JsonPath.read(document, "$.hbp_his.disFlag")!=null && ! JsonPath.read(document, "$.hbp_his.disFlag").equals("")) {
							row.createCell(8).setCellValue(Integer.valueOf(JsonPath.read(document, "$.hbp_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.stroke_his.disFlag")!=null && ! JsonPath.read(document, "$.stroke_his.disFlag").equals("")) {
							row.createCell(9).setCellValue(Integer.valueOf(JsonPath.read(document, "$.stroke_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.chd_his.disFlag")!=null && ! JsonPath.read(document, "$.chd_his.disFlag").equals("")) {
							row.createCell(10).setCellValue(Integer.valueOf(JsonPath.read(document, "$.chd_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.mi_his.disFlag")!=null && ! JsonPath.read(document, "$.mi_his.disFlag").equals("")) {
							row.createCell(11).setCellValue(Integer.valueOf(JsonPath.read(document, "$.mi_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.angina_his.disFlag")!=null && ! JsonPath.read(document, "$.angina_his.disFlag").equals("")) {
							row.createCell(12).setCellValue(Integer.valueOf(JsonPath.read(document, "$.angina_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.arterial_status")!=null && ! JsonPath.read(document, "$.arterial_status").equals("")) {
							row.createCell(13).setCellValue(Integer.valueOf(JsonPath.read(document, "$.arterial_status").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.ushf_his.disFlag")!=null && ! JsonPath.read(document, "$.ushf_his.disFlag").equals("")) {
							row.createCell(14).setCellValue(Integer.valueOf(JsonPath.read(document, "$.ushf_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.lvd_his.disFlag")!=null && ! JsonPath.read(document, "$.lvd_his.disFlag").equals("")) {
							row.createCell(15).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lvd_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.af_his.disFlag")!=null && ! JsonPath.read(document, "$.af_his.disFlag").equals("")) {
							row.createCell(16).setCellValue(Integer.valueOf(JsonPath.read(document, "$.af_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.ace_his.disFlag")!=null && ! JsonPath.read(document, "$.ace_his.disFlag").equals("")) {
							row.createCell(17).setCellValue(Integer.valueOf(JsonPath.read(document, "$.ace_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.diab2_his.disFlag")!=null && ! JsonPath.read(document, "$.diab2_his.disFlag").equals("")) {
							row.createCell(18).setCellValue(Integer.valueOf(JsonPath.read(document, "$.diab2_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.gae_his.disFlag")!=null && ! JsonPath.read(document, "$.gae_his.disFlag").equals("")) {
							row.createCell(19).setCellValue(Integer.valueOf(JsonPath.read(document, "$.gae_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.gna_his.disFlag")!=null && ! JsonPath.read(document, "$.gna_his.disFlag").equals("")) {
							row.createCell(20).setCellValue(Integer.valueOf(JsonPath.read(document, "$.gna_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.bronch_his.disFlag")!=null && ! JsonPath.read(document, "$.bronch_his.disFlag").equals("")) {
							row.createCell(21).setCellValue(Integer.valueOf(JsonPath.read(document, "$.bronch_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.emphy_his.disFlag")!=null && ! JsonPath.read(document, "$.emphy_his.disFlag").equals("")) {
							row.createCell(22).setCellValue(Integer.valueOf(JsonPath.read(document, "$.emphy_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.decline_his.disFlag")!=null && ! JsonPath.read(document, "$.decline_his.disFlag").equals("")) {
							row.createCell(23).setCellValue(Integer.valueOf(JsonPath.read(document, "$.decline_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.lc_his.disFlag")!=null && ! JsonPath.read(document, "$.lc_his.disFlag").equals("")) {
							row.createCell(24).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lc_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.op_his.disFlag")!=null && ! JsonPath.read(document, "$.op_his.disFlag").equals("")) {
							row.createCell(25).setCellValue(Integer.valueOf(JsonPath.read(document, "$.op_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.fracture_his.disFlag")!=null && ! JsonPath.read(document, "$.fracture_his.disFlag").equals("")) {
							row.createCell(26).setCellValue(Integer.valueOf(JsonPath.read(document, "$.fracture_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.sport_his.disFlag")!=null && ! JsonPath.read(document, "$.sport_his.disFlag").equals("")) {
							row.createCell(27).setCellValue(Integer.valueOf(JsonPath.read(document, "$.sport_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.lc_his.disFlag")!=null && ! JsonPath.read(document, "$.lc_his.disFlag").equals("")) {
							row.createCell(28).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lc_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.prostate_disease.disFlag")!=null && ! JsonPath.read(document, "$.prostate_disease.disFlag").equals("")) {
							row.createCell(29).setCellValue(Integer.valueOf(JsonPath.read(document, "$.prostate_disease.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.pc_his.disFlag")!=null && ! JsonPath.read(document, "$.pc_his.disFlag").equals("")) {
							row.createCell(30).setCellValue(Integer.valueOf(JsonPath.read(document, "$.pc_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.bc_his.disFlag")!=null && ! JsonPath.read(document, "$.bc_his.disFlag").equals("")) {
							row.createCell(31).setCellValue(Integer.valueOf(JsonPath.read(document, "$.bc_his.disFlag").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.urinary_tract_infection")!=null && ! JsonPath.read(document, "$.urinary_tract_infection").equals("")) {
							row.createCell(32).setCellValue(Integer.valueOf(JsonPath.read(document, "$.urinary_tract_infection").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.urine_symptom")!=null && ! JsonPath.read(document, "$.urine_symptom").equals("")) {
							row.createCell(33).setCellValue(Integer.valueOf(JsonPath.read(document, "$.urine_symptom").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.hematuresis")!=null && ! JsonPath.read(document, "$.hematuresis").equals("")) {
							row.createCell(34).setCellValue(Integer.valueOf(JsonPath.read(document, "$.hematuresis").toString())==1?"有":"-");
						}
						if(JsonPath.read(document, "$.female_menstruation")!=null && ! JsonPath.read(document, "$.female_menstruation").equals("")) {
							row.createCell(35).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_menstruation").toString())==1?"<12岁":"≥12岁");
						}
						if(JsonPath.read(document, "$.female_birth")!=null && ! JsonPath.read(document, "$.female_birth").equals("")) {
							row.createCell(36).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_birth").toString())==1?"是":Integer.valueOf(JsonPath.read(document, "$.female_birth"))==2?"产后超过3个月":"否");
						}
						if(JsonPath.read(document, "$.female_tire")!=null && ! JsonPath.read(document, "$.female_tire").equals("")) {
							row.createCell(37).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_tire").toString())==1?"<30岁":"≥30岁");
						}
						if(JsonPath.read(document, "$.female_breast")!=null && ! JsonPath.read(document, "$.female_breast").equals("")) {
							row.createCell(38).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_breast").toString())==1?"是":"否");
						}
						if(JsonPath.read(document, "$.female_breast_time")!=null && ! JsonPath.read(document, "$.female_breast_time").equals("")) {
							row.createCell(39).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_breast_time").toString())==1?"<12个月":"≥12个月");
						}

						if( JsonPath.read(document, "$.female_menopause")!=null && ! JsonPath.read(document, "$.female_menopause").equals("")) {
							row.createCell(40).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_menopause").toString())==1?"是":"否");
						}

						if( JsonPath.read(document, "$.female_menopause_age")!=null && ! JsonPath.read(document, "$.female_menopause_age").equals("")) {
							row.createCell(41).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_menopause_age").toString())==1?"<55岁":"≥55岁");
						}

						if( JsonPath.read(document, "$.has_med_ert")!=null && ! JsonPath.read(document, "$.has_med_ert").equals("")) {
							row.createCell(42).setCellValue(Integer.valueOf(JsonPath.read(document, "$.has_med_ert").toString())==1?"是":"否");
						}
						if( JsonPath.read(document, "$.weight_up")!=null && ! JsonPath.read(document, "$.weight_up").equals("")) {
							row.createCell(43).setCellValue(Integer.valueOf(JsonPath.read(document, "$.weight_up").toString())==1?"是":"否");
						}


						if( JsonPath.read(document, "$.med_ert")!=null && ! JsonPath.read(document, "$.med_ert").equals("")) {
							row.createCell(44).setCellValue(Integer.valueOf(JsonPath.read(document, "$.med_ert").toString())==1?"<5年":">=5年");
						}
						if( JsonPath.read(document, "$.med_use_pill")!=null && ! JsonPath.read(document, "$.med_use_pill").equals("")) {
							row.createCell(45).setCellValue(Integer.valueOf(JsonPath.read(document, "$.med_use_pill").toString())==1?"是":"否");
						}
						if( JsonPath.read(document, "$.breast_plastic_surgery")!=null && ! JsonPath.read(document, "$.breast_plastic_surgery").equals("")) {
							row.createCell(46).setCellValue(Integer.valueOf(JsonPath.read(document, "$.breast_plastic_surgery").toString())==1?"是":"否");
						}
						if( JsonPath.read(document, "$.gynecology_operation")!=null && ! JsonPath.read(document, "$.gynecology_operation").equals("")) {
							row.createCell(47).setCellValue(Integer.valueOf(JsonPath.read(document, "$.gynecology_operation").toString())==1?"是":"否");
						}
						if( JsonPath.read(document, "$.ra_his.disFlag")!=null && ! JsonPath.read(document, "$.ra_his.disFlag").equals("")) {
							row.createCell(48).setCellValue(Integer.valueOf(JsonPath.read(document, "$.ra_his.disFlag").toString().toString())==1?"是":"否");
						}
						if( JsonPath.read(document, "$.person.living_envir")!=null && ! JsonPath.read(document, "$.person.living_envir").equals("")) {
							row.createCell(49).setCellValue(Integer.valueOf(JsonPath.read(document, "$.person.living_envir").toString())==1?"寒冷潮湿":"不寒冷不潮湿");
						}
						if((JsonPath.read(document, "$.family_diab_f")!=null && ! JsonPath.read(document, "$.family_diab_f").equals("")&& JsonPath.read(document, "$.family_diab_f").equals("1")) || (JsonPath.read(document, "$.family_diab_m")!=null && ! JsonPath.read(document, "$.family_diab_m").equals("")&& JsonPath.read(document, "$.family_diab_m").equals("1")) || (JsonPath.read(document, "$.family_diab_bs")!=null && ! JsonPath.read(document, "$.family_diab_bs").equals("")&& JsonPath.read(document, "$.family_diab_bs").equals("1")) || (JsonPath.read(document, "$.family_diab_ch")!=null && ! JsonPath.read(document, "$.family_diab_ch").equals("")&& JsonPath.read(document, "$.family_diab_ch").equals("1"))){
							String html="已有";
							if(Integer.valueOf(JsonPath.read(document, "$.family_diab_f").toString())==1){
								html+="父亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_diab_m").toString())==1){
								html+="母亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_diab_bs").toString())==1){
								html+="兄弟;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_diab_ch").toString())==1){
								html+="子女;";
							}
							html+="患病";
							row.createCell(50).setCellValue(html);
						}else{
							row.createCell(50).setCellValue("否");
						}
						if((JsonPath.read(document, "$.family_hbp_f")!=null && ! JsonPath.read(document, "$.family_hbp_f").equals("")&& JsonPath.read(document, "$.family_hbp_f").equals("1")) || (JsonPath.read(document, "$.family_hbp_m")!=null && ! JsonPath.read(document, "$.family_hbp_m").equals("")&& JsonPath.read(document, "$.family_hbp_m").equals("1")) || (JsonPath.read(document, "$.family_hbp_bs")!=null && ! JsonPath.read(document, "$.family_hbp_bs").equals("")&& JsonPath.read(document, "$.family_hbp_bs").equals("1")) || (JsonPath.read(document, "$.family_hbp_ch")!=null && ! JsonPath.read(document, "$.family_hbp_ch").equals("")&& JsonPath.read(document, "$.family_hbp_ch").equals("1"))){
							String html="已有";
							if(Integer.valueOf(JsonPath.read(document, "$.family_hbp_f").toString())==1){
								html+="父亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_hbp_m").toString())==1){
								html+="母亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_hbp_bs").toString())==1){
								html+="兄弟;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_hbp_ch").toString())==1){
								html+="子女;";
							}
							html+="患病";
							row.createCell(51).setCellValue(html);
						}else{
							row.createCell(51).setCellValue("否");
						}
						if((JsonPath.read(document, "$.family_chd_f")!=null && ! JsonPath.read(document, "$.family_chd_f").equals("")&& JsonPath.read(document, "$.family_chd_f").equals("1")) || (JsonPath.read(document, "$.family_chd_m")!=null && ! JsonPath.read(document, "$.family_chd_m").equals("")&& JsonPath.read(document, "$.family_chd_m").equals("1")) || (JsonPath.read(document, "$.family_chd_bs")!=null && ! JsonPath.read(document, "$.family_chd_bs").equals("")&& JsonPath.read(document, "$.family_chd_bs").equals("1")) || (JsonPath.read(document, "$.family_chd_ch")!=null && ! JsonPath.read(document, "$.family_chd_ch").equals("")&& JsonPath.read(document, "$.family_chd_ch").equals("1"))){
							String html="已有";
							if(Integer.valueOf(JsonPath.read(document, "$.family_chd_f").toString())==1){
								html+="父亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_chd_m").toString())==1){
								html+="母亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_chd_bs").toString())==1){
								html+="兄弟;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_chd_ch").toString())==1){
								html+="子女;";
							}
							html+="患病";
							row.createCell(52).setCellValue(html);
						}else{
							row.createCell(52).setCellValue("否");
						}
						if((JsonPath.read(document, "$.family_stroke_f")!=null && ! JsonPath.read(document, "$.family_stroke_f").equals("")&& JsonPath.read(document, "$.family_stroke_f").equals("1")) || (JsonPath.read(document, "$.family_stroke_m")!=null && ! JsonPath.read(document, "$.family_stroke_m").equals("")&& JsonPath.read(document, "$.family_stroke_m").equals("1")) || (JsonPath.read(document, "$.family_stroke_bs")!=null && ! JsonPath.read(document, "$.family_stroke_bs").equals("")&& JsonPath.read(document, "$.family_stroke_bs").equals("1")) || (JsonPath.read(document, "$.family_stroke_ch")!=null && ! JsonPath.read(document, "$.family_stroke_ch").equals("")&& JsonPath.read(document, "$.family_stroke_ch").equals("1"))){
							String html="已有";
							if(Integer.valueOf(JsonPath.read(document, "$.family_stroke_f").toString())==1){
								html+="父亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_stroke_m").toString())==1){
								html+="母亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_stroke_bs").toString())==1){
								html+="兄弟;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_stroke_ch").toString())==1){
								html+="子女;";
							}
							html+="患病";
							row.createCell(53).setCellValue(html);
						}else{
							row.createCell(53).setCellValue("否");
						}
						if((JsonPath.read(document, "$.family_hf_f")!=null && ! JsonPath.read(document, "$.family_hf_f").equals("")&& JsonPath.read(document, "$.family_hf_f").equals("1")) || (JsonPath.read(document, "$.family_hf_m")!=null && ! JsonPath.read(document, "$.family_hf_m").equals("")&& JsonPath.read(document, "$.family_hf_m").equals("1")) || (JsonPath.read(document, "$.family_hf_bs")!=null && ! JsonPath.read(document, "$.family_hf_bs").equals("")&& JsonPath.read(document, "$.family_hf_bs").equals("1")) || (JsonPath.read(document, "$.family_hf_ch")!=null && ! JsonPath.read(document, "$.family_hf_ch").equals("")&& JsonPath.read(document, "$.family_hf_ch").equals("1"))){
							String html="已有";
							if(Integer.valueOf(JsonPath.read(document, "$.family_hf_f").toString())==1){
								html+="父亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_hf_m").toString())==1){
								html+="母亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_hf_bs").toString())==1){
								html+="兄弟;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_hf_ch").toString())==1){
								html+="子女;";
							}
							html+="患病";
							row.createCell(54).setCellValue(html);
						}else{
							row.createCell(54).setCellValue("否");
						}
						if((JsonPath.read(document, "$.family_lung_f")!=null && ! JsonPath.read(document, "$.family_lung_f").equals("")&& JsonPath.read(document, "$.family_lung_f").equals("1")) || (JsonPath.read(document, "$.family_lung_m")!=null && ! JsonPath.read(document, "$.family_lung_m").equals("")&& JsonPath.read(document, "$.family_lung_m").equals("1")) || (JsonPath.read(document, "$.family_lung_bs")!=null && ! JsonPath.read(document, "$.family_lung_bs").equals("")&& JsonPath.read(document, "$.family_lung_bs").equals("1")) || (JsonPath.read(document, "$.family_lung_ch")!=null && ! JsonPath.read(document, "$.family_lung_ch").equals("")&& JsonPath.read(document, "$.family_lung_ch").equals("1"))){
							String html="已有";
							if(Integer.valueOf(JsonPath.read(document, "$.family_lung_f").toString())==1){
								html+="父亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_lung_m").toString())==1){
								html+="母亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_lung_bs").toString())==1){
								html+="兄弟;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_lung_ch").toString())==1){
								html+="子女;";
							}
							html+="患病";
							row.createCell(55).setCellValue(html);
						}else{
							row.createCell(55).setCellValue("否");
						}
						if((JsonPath.read(document, "$.family_pro_f")!=null && ! JsonPath.read(document, "$.family_pro_f").equals("")&& JsonPath.read(document, "$.family_pro_f").equals("1")) || (JsonPath.read(document, "$.family_pro_b")!=null && ! JsonPath.read(document, "$.family_pro_b").equals("")&& JsonPath.read(document, "$.family_pro_b").equals("1")) || (JsonPath.read(document, "$.family_pro_s")!=null && ! JsonPath.read(document, "$.family_pro_s").equals("")&& JsonPath.read(document, "$.family_pro_s").equals("1")) ){
							String html="已有";
							if(Integer.valueOf(JsonPath.read(document, "$.family_pro_f").toString())==1){
								html+="父亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_pro_b").toString())==1){
								html+="兄弟;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_pro_s").toString())==1){
								html+="儿子;";
							}
							html+="患病";
							row.createCell(56).setCellValue(html);
						}else{
							row.createCell(56).setCellValue("否");
						}
						if((JsonPath.read(document, "$.family_breast_m")!=null && ! JsonPath.read(document, "$.family_breast_m").equals("")&& JsonPath.read(document, "$.family_breast_m").equals("1")) || (JsonPath.read(document, "$.family_breast_s")!=null && ! JsonPath.read(document, "$.family_breast_s").equals("")&& JsonPath.read(document, "$.family_breast_s").equals("1")) || (JsonPath.read(document, "$.family_breast_d")!=null && ! JsonPath.read(document, "$.family_breast_d").equals("")&& JsonPath.read(document, "$.family_breast_d").equals("1")) ){
							String html="已有";
							if(Integer.valueOf(JsonPath.read(document, "$.family_breast_m").toString())==1){
								html+="母亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_breast_s").toString())==1){
								html+="姐妹;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_breast_d").toString())==1){
								html+="女儿;";
							}
							html+="患病";
							row.createCell(57).setCellValue(html);
						}else{
							row.createCell(57).setCellValue("否");
						}
						if((JsonPath.read(document, "$.family_gout_f")!=null && ! JsonPath.read(document, "$.family_gout_f").equals("")&& JsonPath.read(document, "$.family_gout_f").equals("1")) || (JsonPath.read(document, "$.family_gout_m")!=null && ! JsonPath.read(document, "$.family_gout_m").equals("")&& JsonPath.read(document, "$.family_gout_m").equals("1")) || (JsonPath.read(document, "$.family_gout_bs")!=null && ! JsonPath.read(document, "$.family_gout_bs").equals("")&& JsonPath.read(document, "$.family_gout_bs").equals("1")) ){
							String html="已有";
							if(Integer.valueOf(JsonPath.read(document, "$.family_gout_f").toString())==1){
								html+="父亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_gout_m").toString())==1){
								html+="母亲;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_gout_bs").toString())==1){
								html+="兄弟;";
							}
							if(Integer.valueOf(JsonPath.read(document, "$.family_gout_ch").toString())==1){
								html+="子女;";
							}
							html+="患病";
							row.createCell(58).setCellValue(html);
						}else{
							row.createCell(58).setCellValue("否");
						}
						if( JsonPath.read(document, "$.med_hbp")!=null && ! JsonPath.read(document, "$.med_hbp").equals("")) {
							row.createCell(59).setCellValue(Integer.valueOf(JsonPath.read(document, "$.med_hbp").toString())==1?"是":"否");
						}
						if( JsonPath.read(document, "$.med_ach")!=null && ! JsonPath.read(document, "$.med_ach").equals("")) {
							row.createCell(60).setCellValue(Integer.valueOf(JsonPath.read(document, "$.med_ach").toString())==1?"是":"否");
						}
						if( JsonPath.read(document, "$.food_vegetables")!=null && ! JsonPath.read(document, "$.food_vegetables").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString())==1){
								row.createCell(61).setCellValue("不吃");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString())==2){
								row.createCell(61).setCellValue("少于50克");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString())==3){
								row.createCell(61).setCellValue("50-75克");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString())==4){
								row.createCell(61).setCellValue("150-300克");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString())==5){
								row.createCell(61).setCellValue("300克以上");
							}

						}
						if( JsonPath.read(document, "$.food_fruits")!=null && ! JsonPath.read(document, "$.food_fruits").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString())==1){
								row.createCell(62).setCellValue("不吃");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString())==2){
								row.createCell(62).setCellValue("少于50克");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString())==3){
								row.createCell(62).setCellValue("50-100克");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString())==4){
								row.createCell(62).setCellValue("100-200克");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString())==5){
								row.createCell(62).setCellValue("200克以上");
							}
						}
						if( JsonPath.read(document, "$.food_milk")!=null && ! JsonPath.read(document, "$.food_milk").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_milk").toString())==1){
								row.createCell(63).setCellValue("不喝");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_milk").toString())==2){
								row.createCell(63).setCellValue("少于0.5杯");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_milk").toString())==3){
								row.createCell(63).setCellValue("0.5-1杯");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_milk").toString())==4){
								row.createCell(63).setCellValue("1-2杯");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_milk").toString())==5){
								row.createCell(63).setCellValue("2杯以上");
							}
						}
						if( JsonPath.read(document, "$.food_dairy")!=null && ! JsonPath.read(document, "$.food_dairy").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString())==1){
								row.createCell(64).setCellValue("不吃");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString())==2){
								row.createCell(64).setCellValue("少于10克 ");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString())==3){
								row.createCell(64).setCellValue("10-40克");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString())==4){
								row.createCell(64).setCellValue("40-80克");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString())==5){
								row.createCell(64).setCellValue("80克以上");
							}
						}
						if( JsonPath.read(document, "$.food_fired")!=null && ! JsonPath.read(document, "$.food_fired").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_fired").toString())==1){
								row.createCell(65).setCellValue("不吃");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_fired").toString())==2){
								row.createCell(65).setCellValue("1-4次 ");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_fired").toString())==3){
								row.createCell(65).setCellValue("5-7次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_fired").toString())==4){
								row.createCell(65).setCellValue("7次以上");
							}
						}
						if( JsonPath.read(document, "$.food_sweet")!=null && ! JsonPath.read(document, "$.food_sweet").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString())==1){
								row.createCell(66).setCellValue("不吃");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString())==2){
								row.createCell(66).setCellValue("1-4次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString())==3){
								row.createCell(66).setCellValue("5-7次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString())==4){
								row.createCell(66).setCellValue("7次以上");
							}
						}
						if( JsonPath.read(document, "$.food_sugary")!=null && ! JsonPath.read(document, "$.food_sugary").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString())==1){
								row.createCell(67).setCellValue("不喝");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString())==2){
								row.createCell(67).setCellValue("1-4次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString())==3){
								row.createCell(67).setCellValue("5-7次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString())==4){
								row.createCell(67).setCellValue("7次以上");
							}
						}
						if( JsonPath.read(document, "$.food_pickled")!=null && ! JsonPath.read(document, "$.food_pickled").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString())==1){
								row.createCell(68).setCellValue("不吃");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString())==2){
								row.createCell(68).setCellValue("1-4次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString())==3){
								row.createCell(68).setCellValue("5-7次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString())==4){
								row.createCell(68).setCellValue("7次以上");
							}

						}
						if( JsonPath.read(document, "$.seafood_cnt")!=null && ! JsonPath.read(document, "$.seafood_cnt").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString())==1){
								row.createCell(69).setCellValue("几乎不吃");
							}else if(Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString())==2){
								row.createCell(69).setCellValue("1~2次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString())==3){
								row.createCell(69).setCellValue("2~4次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString())==4){
								row.createCell(69).setCellValue("4次以上");
							}

						}
						if( JsonPath.read(document, "$.drink_tea_cnt")!=null && ! JsonPath.read(document, "$.drink_tea_cnt").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.drink_tea_cnt").toString())==1){
								row.createCell(70).setCellValue("几乎不饮茶");
							}else if(Integer.valueOf(JsonPath.read(document, "$.drink_tea_cnt").toString())==2){
								row.createCell(70).setCellValue("不超过1杯");
							}else if(Integer.valueOf(JsonPath.read(document, "$.drink_tea_cnt").toString())==3){
								row.createCell(70).setCellValue("1杯以上");
							}

						}
						if( JsonPath.read(document, "$.food_drink")!=null && ! JsonPath.read(document, "$.food_drink").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_drink").toString())==1){
								row.createCell(71).setCellValue("喝酒");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_drink").toString())==2){
								row.createCell(71).setCellValue("不喝");
							}

						}
						if( JsonPath.read(document, "$.food_drink_times")!=null && ! JsonPath.read(document, "$.food_drink_times").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_drink_times").toString())==1){
								row.createCell(72).setCellValue("1-2次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_drink_times").toString())==2){
								row.createCell(72).setCellValue("3-5次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_drink_times").toString())==3){
								row.createCell(72).setCellValue(">5次");
							}
						}
						if( JsonPath.read(document, "$.food_drink_beer")!=null && ! JsonPath.read(document, "$.food_drink_beer").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString())==1){
								row.createCell(73).setCellValue("少于300毫升");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString())==2){
								row.createCell(73).setCellValue("300-600毫升");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString())==3){
								row.createCell(73).setCellValue("600-900毫升");
							}else if(Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString())==4){
								row.createCell(73).setCellValue("900毫升以上");
							}
						}
						if( JsonPath.read(document, "$.smoke_status")!=null && !JsonPath.read(document, "$.smoke_status") .equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.smoke_status").toString())==1){
								row.createCell(74).setCellValue("吸烟");
							}else if(Integer.valueOf(JsonPath.read(document, "$.smoke_status").toString())==2){
								row.createCell(74).setCellValue("不吸烟");
							}else if(Integer.valueOf(JsonPath.read(document, "$.smoke_status").toString())==3){
								row.createCell(74).setCellValue("已戒烟");
							}
						}
						if( JsonPath.read(document, "$.smoke_amount")!=null && ! JsonPath.read(document, "$.smoke_amount").equals("")) {
							row.createCell(75).setCellValue(Integer.valueOf(JsonPath.read(document, "$.smoke_amount").toString())==1?"有":"无");
						}
						if( JsonPath.read(document, "$.smoke_end_age")!=null && ! JsonPath.read(document, "$.smoke_end_age").equals("")) {
							row.createCell(76).setCellValue(JsonPath.read(document, "$.smoke_end_age").toString());
						}
						if( JsonPath.read(document, "$.smoke_sencod")!=null && ! JsonPath.read(document, "$.smoke_sencod").equals("")) {
							row.createCell(77).setCellValue(Integer.valueOf(JsonPath.read(document, "$.smoke_sencod").toString())==1?"是":"否");
						}
						if( JsonPath.read(document, "$.sport_in")!=null && ! JsonPath.read(document, "$.sport_in").equals("")) {
							row.createCell(78).setCellValue(Integer.valueOf(JsonPath.read(document, "$.sport_in").toString())==1?"参加过":"没参加过");
						}
						if( JsonPath.read(document, "$.sport_freq")!=null && ! JsonPath.read(document, "$.sport_freq").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString())==1){
								row.createCell(79).setCellValue("每月少于1次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString())==2){
								row.createCell(79).setCellValue("每月至少1次，但每周不足1次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString())==3){
								row.createCell(79).setCellValue("每周1-2次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString())==4){
								row.createCell(79).setCellValue("每周3-4次");
							}else if(Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString())==5){
								row.createCell(79).setCellValue("每周5次以上");
							}

						}
						if( JsonPath.read(document, "$.sport_time")!=null && ! JsonPath.read(document, "$.sport_time").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.sport_time").toString())==1){
								row.createCell(80).setCellValue("少于30分钟");
							}else if(Integer.valueOf(JsonPath.read(document, "$.sport_time").toString())==2){
								row.createCell(80).setCellValue("30-60分钟");
							}else if(Integer.valueOf(JsonPath.read(document, "$.sport_time").toString())==3){
								row.createCell(80).setCellValue("60分钟以上");
							}
						}
						if( JsonPath.read(document, "$.decorate_house")!=null && ! JsonPath.read(document, "$.decorate_house").equals("")) {
							row.createCell(81).setCellValue(Integer.valueOf(JsonPath.read(document, "$.decorate_house").toString())==1?"有":"无");
						}
						if( JsonPath.read(document, "$.psy_nervous")!=null && ! JsonPath.read(document, "$.psy_nervous").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.psy_nervous").toString())==1){
								row.createCell(82).setCellValue("没有");
							}else if(Integer.valueOf(JsonPath.read(document, "$.psy_nervous").toString())==2){
								row.createCell(82).setCellValue("偶尔");
							}else if(Integer.valueOf(JsonPath.read(document, "$.psy_nervous").toString())==3){
								row.createCell(82).setCellValue("经常");
							}
						}
						if( JsonPath.read(document, "$.bedtime")!=null && ! JsonPath.read(document, "$.bedtime").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.bedtime").toString())==1){
								row.createCell(83).setCellValue("每天8小时以上");
							}else if(Integer.valueOf(JsonPath.read(document, "$.bedtime").toString())==2){
								row.createCell(83).setCellValue("每天6~8小时");
							}else if(Integer.valueOf(JsonPath.read(document, "$.bedtime").toString())==3){
								row.createCell(83).setCellValue("每天不足6小时");
							}
						}
						if( JsonPath.read(document, "$.person.income")!=null && ! JsonPath.read(document, "$.person.income").equals("")) {
							if(Integer.valueOf(JsonPath.read(document, "$.person.income").toString())==1){
								row.createCell(84).setCellValue("小于1000元");
							}else if(Integer.valueOf(JsonPath.read(document, "$.person.income").toString())==2){
								row.createCell(84).setCellValue("1000~2999元");
							}else if(Integer.valueOf(JsonPath.read(document, "$.person.income").toString())==3){
								row.createCell(84).setCellValue("3000~4999元");
							}else if(Integer.valueOf(JsonPath.read(document, "$.person.income").toString())==4){
								row.createCell(84).setCellValue("5000元以上");
							}

						}
//						if( JsonPath.read(document, "$.hw_check.xt_exception")!=null && ! JsonPath.read(document, "$.hw_check.xt_exception").equals("")) {
//							row.createCell(85).setCellValue(Integer.valueOf(JsonPath.read(document, "$.hw_check.xt_exception").toString())==0?"正常":"异常");
//						}
						if( JsonPath.read(document, "$.lampblack_contact.disFlag")!=null && ! JsonPath.read(document, "$.lampblack_contact.disFlag").equals("")) {
							row.createCell(85).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lampblack_contact.disFlag").toString())==1?"有":"无");
						}
						if( JsonPath.read(document, "$.chemical_occupation")!=null && ! JsonPath.read(document, "$.chemical_occupation").equals("")) {
							row.createCell(86).setCellValue(Integer.valueOf(JsonPath.read(document, "$.chemical_occupation").toString())==1?"是":"否");
						}
						if( JsonPath.read(document, "$.dust_exposure_his.disFlag")!=null && ! JsonPath.read(document, "$.dust_exposure_his.disFlag").equals("")) {
							row.createCell(87).setCellValue(Integer.valueOf(JsonPath.read(document, "$.dust_exposure_his.disFlag").toString())==1?"有":"无");
						}
						if( JsonPath.read(document, "$.asbestos_contact_his.disFlag")!=null && ! JsonPath.read(document, "$.asbestos_contact_his.disFlag").equals("")) {
							row.createCell(88).setCellValue(Integer.valueOf(JsonPath.read(document, "$.asbestos_contact_his.disFlag").toString())==1?"有":"无");
						}
							row.createCell(89).setCellValue("总胆固醇（单位mmol/l）"+JsonPath.read(document, "$.cholesterol").toString()+";甘油三酯（单位mmol/l）"+JsonPath.read(document, "$.tg").toString()+"；高密度脂蛋白胆固醇（单位mmol/l）"+JsonPath.read(document, "$.hdlc").toString()+"；低密度脂蛋白胆固醇（单位mmol/l）"+JsonPath.read(document, "$.ldlc").toString());
							row.createCell(90).setCellValue("血尿酸（单位μmol/L）"+JsonPath.read(document, "$.bua").toString()+";血肌酐（单位mmol/L）"+JsonPath.read(document, "$.serum_creatinine").toString());
							row.createCell(91).setCellValue("空腹血糖（单位mmol/l）"+JsonPath.read(document, "$.fbg").toString()+";收缩压（单位mmHg）"+JsonPath.read(document, "$.sbp").toString()+"；舒张压（单位mmHg）"+JsonPath.read(document, "$.dbp").toString()+"；血常规检查白细胞（单位/L）"+JsonPath.read(document, "$.wbc").toString()+"；骨密度检查指标-T值"+JsonPath.read(document, "$.t_score").toString());
						if( JsonPath.read(document, "$.female_menstruation_regular")!=null && ! JsonPath.read(document, "$.female_menstruation_regular").equals("")) {
							row.createCell(92).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_menstruation_regular").toString())==1?"是":"不是");
						}



					}
				
			}
				if (sheetname.get(i).equals("健康评估结果")) {
					// 循环表数据
					for (int j = 0; j < riskresult2.size(); j++) {
                        System.out.println(riskresult2);
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(riskresult2.get(j).getUserCode());
						row.createCell(1).setCellValue(riskresult2.get(j).getUserName());
						row.createCell(2).setCellValue(riskresult2.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(riskresult2.get(j).getAge());
						row.createCell(4).setCellValue(riskresult2.get(j).getOrgname());
						row.createCell(5).setCellValue(riskresult2.get(j).getQtime());
						for(int h=0 ; h< riskresult2.get(j).getYhDiseaseRiskResult2s().size();h++){
						    if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getBmi()!=null){
                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getBmi());
                                row.createCell(6).setCellValue(JsonPath.read(document, "$.now_value").toString());
                            }
                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFood()!=null){
                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFood());
                                row.createCell(7).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"不足":"充足");
                            }
                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getB4()!=null){
                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getB4());
                                row.createCell(8).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"存在":"不存在");
                            }
                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFoodPurine()!=null){
                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFoodPurine());
                                row.createCell(9).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"不存在":"存在");
                            }
                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getTfa()!=null){
                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getTfa());
                                row.createCell(10).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"适量":"过量");
                            }
                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getSport()!=null){
                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getSport());
                                row.createCell(11).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"不足":"充分");
                            }
                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getDrink()!=null){
                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getDrink());
                                row.createCell(12).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"喝酒":"不喝");
                            }
                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPsyNervous()!=null){
                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPsyNervous());
                                row.createCell(13).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"有":"没有");
                            }
//                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHfHis()!=null){
//                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHfHis());
//                                row.createCell(14).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//                                row.createCell(16).setCellValue(JsonPath.read(document, "$.Ideal_value").toString());
//                            }
//                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHfs()!=null){
//                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHfs());
//                                row.createCell(15).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//                            }
//                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHbps()!=null){
//                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHbps());
//                                row.createCell(17).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//                            }
//                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getB4()!=null){
//                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getB4());
//                                row.createCell(18).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//                            }
//                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getLungs()!=null){
//                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getLungs());
//                                row.createCell(19).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//                            }
//                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFood()!=null){
//                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFood());
//                                row.createCell(20).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"不足":"充足");
//                               row.createCell(24).setCellValue(JsonPath.read(document, "$.Ideal_value").toString());
//                            }
//                            if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getSmoke()!=null){
//                                Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getSmoke());
//                                row.createCell(26).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"不吸烟":"吸烟");
//								row.createCell(25).setCellValue(JsonPath.read(document, "$.Ideal_value").toString());
//                            }
//							if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPcs()!=null){
//								Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPcs());
//								row.createCell(27).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"不足":"充足");
//							}
//							if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPcs()!=null){
//								Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPcs());
//								row.createCell(28).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//							}
							System.out.println(j);
							row.createCell(14).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(0).getNow_risk_value());
							row.createCell(15).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(0).getIdeal_risk_level());
							row.createCell(16).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(0).getRisk_level());

							row.createCell(17).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(1).getNow_risk_value());
							row.createCell(18).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(1).getIdeal_risk_level());
							row.createCell(19).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(1).getRisk_level());
							row.createCell(20).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(2).getNow_risk_value());
							row.createCell(21).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(2).getIdeal_risk_level());
							row.createCell(22).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(2).getRisk_level());
							row.createCell(23).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(3).getNow_risk_value());
							row.createCell(24).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(3).getIdeal_risk_level());
							row.createCell(25).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(3).getRisk_level());
							row.createCell(26).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(4).getNow_risk_value());
							row.createCell(27).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(4).getIdeal_risk_level());
							row.createCell(28).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(4).getRisk_level());
							row.createCell(29).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(6).getNow_risk_value());
							row.createCell(30).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(6).getIdeal_risk_level());
							row.createCell(31).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(6).getRisk_level());
							row.createCell(32).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(7).getNow_risk_value());
							row.createCell(33).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(7).getIdeal_risk_level());
							row.createCell(34).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(7).getRisk_level());
							if(riskresult2.get(j).getYhExportAssessReports().get(8).getDiseasId().equals("9")){
								row.createCell(35).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getNow_risk_value());
								row.createCell(36).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getIdeal_risk_level());
								row.createCell(37).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getRisk_level());
							}
							if(riskresult2.get(j).getYhExportAssessReports().get(8).getDiseasId().equals("10")){
								row.createCell(38).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getNow_risk_value());
								row.createCell(39).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getIdeal_risk_level());
								row.createCell(40).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getRisk_level());
							}

							if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getBf()!=null){
								Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getBf());
								row.createCell(41).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"血脂正常":"血脂异常");
							}
							row.createCell(42).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(9).getRisk_level());
							row.createCell(43).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(9).getIdeal_risk_level());
                        }
					}
				}
				
				
				if (sheetname.get(i).equals("A型性格评估问卷")) {
					// 循环表数据
					for (int j = 0; j < exportapre.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						String anser=exportapre.get(j).getEvaluate_Data();
						anser=anser.trim();
						anser=anser.substring(1,anser.length()-1);
						String[] ansers=anser.split(",");
						for (int l = 0; l < ansers.length; l++) {
							row.createCell(l+5).setCellValue(Integer.valueOf(ansers[l])==0?"否":"是");
						}
						row.createCell(0).setCellValue(exportapre.get(j).getUser_Code());
						row.createCell(1).setCellValue(exportapre.get(j).getName());
						row.createCell(2).setCellValue(exportapre.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(exportapre.get(j).getOrg_Name());
						row.createCell(4).setCellValue(exportapre.get(j).getEvaluate_Date());
					}
				}



				if (sheetname.get(i).equals("RSTRI压力评估问卷")) {
					// 循环表数据
					for (int j = 0; j < exportrstr.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						String anser=exportrstr.get(j).getEvaluate_Data();
						anser=anser.trim();
						anser=anser.substring(1,anser.length()-1);
						String[] ansers=anser.split(",");
						for (int l = 0; l < ansers.length; l++) {
							row.createCell(l+5).setCellValue(Integer.valueOf(ansers[l])==0?"总是":Integer.valueOf(ansers[l])==1?"经常":(Integer.valueOf(ansers[l])==2?"有时":(Integer.valueOf(ansers[l])==3?"很少":(Integer.valueOf(ansers[l])==4?"从不":""))));
						}
						row.createCell(0).setCellValue(exportrstr.get(j).getUser_Code());
						row.createCell(1).setCellValue(exportrstr.get(j).getName());
						row.createCell(2).setCellValue(exportrstr.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(exportrstr.get(j).getOrg_Name());
						row.createCell(4).setCellValue(exportrstr.get(j).getEvaluate_Date());
					}
				}
				if (sheetname.get(i).equals("RSTRI压力评估结果")) {
					// 循环表数据
					for (int j = 0; j < exportrstr.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(exportrstr.get(j).getUser_Code());
						row.createCell(1).setCellValue(exportrstr.get(j).getName());
						row.createCell(2).setCellValue(exportrstr.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(exportrstr.get(j).getOrg_Name());
						row.createCell(4).setCellValue(exportrstr.get(j).getEvaluate_Date());
						row.createCell(5).setCellValue(exportrstr.get(j).getEvaluate_Score());
						if(Integer.valueOf(exportrstr.get(j).getEvaluate_Score())<43) {
							row.createCell(6).setCellValue("压力过低");
						}else if(Integer.valueOf(exportrstr.get(j).getEvaluate_Score())>65) {
							row.createCell(6).setCellValue("压力过高");
						}else{
							row.createCell(6).setCellValue("压力适中");
					}
				}
			}
				if (sheetname.get(i).equals("心理健康评估结果")) {
					// 循环表数据
					for (int j = 0; j < exportxinli.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(exportxinli.get(j).getUser_Code());
						row.createCell(1).setCellValue(exportxinli.get(j).getName());
						row.createCell(2).setCellValue(exportxinli.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(exportxinli.get(j).getOrg_Name());
						row.createCell(4).setCellValue(exportxinli.get(j).getEvaluate_Date());
						row.createCell(5).setCellValue(exportxinli.get(j).getEvaluate_Score());
						if(Integer.valueOf(exportxinli.get(j).getEvaluate_Score())<25) {
							row.createCell(6).setCellValue("疑似心理疾患");
						}else if(Integer.valueOf(exportxinli.get(j).getEvaluate_Score())>45) {
							row.createCell(6).setCellValue("正常");
						}else{
							row.createCell(6).setCellValue("橙色预警");
					}
				}
			}
				
				if (sheetname.get(i).equals("A型性格评估结果")) {
					// 循环表数据
					for (int j = 0; j < exportapre.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						String anser=exportapre.get(j).getEvaluate_Score();
						anser=anser.trim();
						String[] ansers=anser.split(";");
						int souce=0;
						for (int l = 0; l < ansers.length; l++) {
							souce+=Integer.valueOf(ansers[l]);
						}
						row.createCell(0).setCellValue(exportapre.get(j).getUser_Code());
						row.createCell(1).setCellValue(exportapre.get(j).getName());
						row.createCell(2).setCellValue(exportapre.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(exportapre.get(j).getOrg_Name());
						row.createCell(4).setCellValue(exportapre.get(j).getEvaluate_Date());
						row.createCell(5).setCellValue(souce);
						if(souce<20) {
							row.createCell(6).setCellValue("B型");
						}else if(souce<27) {
							row.createCell(6).setCellValue("中间编B型");
						}else if(souce<30) {
							row.createCell(6).setCellValue("中间型");
						}else if(souce<37){
							row.createCell(6).setCellValue("中间编A型");
						}else if(souce>=37) {
							row.createCell(6).setCellValue("中间A型");
						}
					}
				}
				if (sheetname.get(i).equals("检测数据汇总表")) {
					SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
					
					// 循环成一个表
					for (int k = 1; k < all.size(); k++) {
						if(all.get(k).equals("体脂")) {
							int sun=lastexportall.size();
							for (int j2 = 0; j2 < exportall.getBodyfat().size(); j2++) {
									boolean Whether=false;
									int index=0;
									for (int j = 0; j < sun; j++) {

										if(yhexportnow.getTime()){
											if(lastexportall.get(j).getUser_Code().equals(exportall.getBodyfat().get(j2).getUser_Code())&&lastexportall.get(j).getTime().substring(0, 10).equals(exportall.getBodyfat().get(j2).getEntry_Time().substring(0, 10))) {
												index=j;
												Whether=true;
												break;
											}
										}else{
											if(lastexportall.get(j).getUser_Code().equals(exportall.getBodyfat().get(j2).getUser_Code())) {
												index=j;
												Whether=true;
												break;
											}
										}

										
								}
									if(Whether) {
										System.out.println(exportall.getBodyfat().get(j2).getName());
										lastexportall.get(index).setUser_Code(exportall.getBodyfat().get(j2).getUser_Code());
										lastexportall.get(index).setName(exportall.getBodyfat().get(j2).getName());
										lastexportall.get(index).setGender(exportall.getBodyfat().get(j2).getGender());
										lastexportall.get(index).setOrg_Name(exportall.getBodyfat().get(j2).getOrg_Name());
										lastexportall.get(index).setFat_rate(exportall.getBodyfat().get(j2).getFat_rate());
										lastexportall.get(index).setMuscle(exportall.getBodyfat().get(j2).getMuscle());
										lastexportall.get(index).setBones(exportall.getBodyfat().get(j2).getBones());
										lastexportall.get(index).setVisceral_level(exportall.getBodyfat().get(j2).getVisceral_level());
										lastexportall.get(index).setWater(exportall.getBodyfat().get(j2).getWater());
										lastexportall.get(index).setHeat(exportall.getBodyfat().get(j2).getHeat());
										lastexportall.get(index).setProtein(exportall.getBodyfat().get(j2).getProtein());
										lastexportall.get(index).setBasal_met(exportall.getBodyfat().get(j2).getBasal_met());
										lastexportall.get(index).setBodyfat_Entry_Time(exportall.getBodyfat().get(j2).getEntry_Time());
										lastexportall.get(index).setTime(exportall.getBodyfat().get(j2).getEntry_Time());
									}else {
										YhExportAll manyalls=new YhExportAll();
										System.out.println(exportall.getBodyfat().get(j2).getName());
										manyalls.setUser_Code(exportall.getBodyfat().get(j2).getUser_Code());
										manyalls.setName(exportall.getBodyfat().get(j2).getName());
										manyalls.setGender(exportall.getBodyfat().get(j2).getGender());
										manyalls.setOrg_Name(exportall.getBodyfat().get(j2).getOrg_Name());
										manyalls.setFat_rate(exportall.getBodyfat().get(j2).getFat_rate());
										manyalls.setMuscle(exportall.getBodyfat().get(j2).getMuscle());
										manyalls.setBones(exportall.getBodyfat().get(j2).getBones());
										manyalls.setVisceral_level(exportall.getBodyfat().get(j2).getVisceral_level());
										manyalls.setWater(exportall.getBodyfat().get(j2).getWater());
										manyalls.setHeat(exportall.getBodyfat().get(j2).getHeat());
										manyalls.setProtein(exportall.getBodyfat().get(j2).getProtein());
										manyalls.setBasal_met(exportall.getBodyfat().get(j2).getBasal_met());
										manyalls.setBodyfat_Entry_Time(exportall.getBodyfat().get(j2).getEntry_Time());
										manyalls.setTime(exportall.getBodyfat().get(j2).getEntry_Time());
										lastexportall.add(manyalls);
									}
							}
						}
						if(all.get(k).equals("血压")) {
							//总的条数
							System.out.println(lastexportall+"+++++++++++++++++++++++++++++");
							int sun=lastexportall.size();
								//血压的条数
							for (int j2 = 0; j2 < exportall.getBloodpressure().size(); j2++) {
								boolean Whether=false;
								int index=0;
								for (int j = 0; j < sun; j++) {
									if(yhexportnow.getTime()){
										if(lastexportall.get(j).getUser_Code().equals(exportall.getBloodpressure().get(j2).getUser_Code())&&lastexportall.get(j).getTime().substring(0, 10).equals(exportall.getBloodpressure().get(j2).getEntry_Time().substring(0, 10))) {
											index=j;
											Whether=true;
											break;
										}
									}else{
										if(lastexportall.get(j).getUser_Code().equals(exportall.getBloodpressure().get(j2).getUser_Code())) {
											index=j;
											Whether=true;
											break;
										}
									}

								}
								if(Whether) {
									System.out.println(exportall.getBloodpressure().get(j2).getName());
									lastexportall.get(index).setUser_Code(exportall.getBloodpressure().get(j2).getUser_Code());
									lastexportall.get(index).setName(exportall.getBloodpressure().get(j2).getName());
									lastexportall.get(index).setGender(exportall.getBloodpressure().get(j2).getGender());
									lastexportall.get(index).setOrg_Name(exportall.getBloodpressure().get(j2).getOrg_Name());
									lastexportall.get(index).setSbp(exportall.getBloodpressure().get(j2).getSbp());
								    lastexportall.get(index).setDbp(exportall.getBloodpressure().get(j2).getDbp());
								    if(exportall.getBloodpressure().get(j2).getXinlv()!=null){
										lastexportall.get(index).setXinlv(exportall.getBloodpressure().get(j2).getXinlv().equals("0")?"":exportall.getBloodpressure().get(j2).getXinlv());
									}
									lastexportall.get(index).setBloodpressure_Entry_Time(exportall.getBloodpressure().get(j2).getEntry_Time());
									lastexportall.get(index).setTime(exportall.getBloodpressure().get(j2).getEntry_Time());
								}else {
									YhExportAll manyalls=new YhExportAll();
									System.out.println(exportall.getBloodpressure().get(j2).getName());
									manyalls.setUser_Code(exportall.getBloodpressure().get(j2).getUser_Code());
									manyalls.setName(exportall.getBloodpressure().get(j2).getName());
									manyalls.setGender(exportall.getBloodpressure().get(j2).getGender());
									manyalls.setOrg_Name(exportall.getBloodpressure().get(j2).getOrg_Name());
									manyalls.setSbp(exportall.getBloodpressure().get(j2).getSbp());
									manyalls.setDbp(exportall.getBloodpressure().get(j2).getDbp());
									manyalls.setXinlv(exportall.getBloodpressure().get(j2).getXinlv());
									manyalls.setBloodpressure_Entry_Time(exportall.getBloodpressure().get(j2).getEntry_Time());
									manyalls.setTime(exportall.getBloodpressure().get(j2).getEntry_Time());
									lastexportall.add(manyalls);
									System.out.println(manyalls);
									
								}
							}
							System.out.println(lastexportall+"+++++++++++++++++++++++++++++");
						}
						
						
						if(all.get(k).equals("BMI")) {
							int sun=lastexportall.size();
							for (int j2 = 0; j2 < exportall.getBmi().size(); j2++) {
								boolean Whether=false;
								int index=0;
								for (int j = 0; j < sun; j++) {
									if(yhexportnow.getTime()){
										if(lastexportall.get(j).getUser_Code().equals(exportall.getBmi().get(j2).getUser_Code())&&formatter2.parse(lastexportall.get(j).getTime()).equals(formatter2.parse(exportall.getBmi().get(j2).getEntry_Time()))) {
											index=j;
											Whether=true;
											break;
										}
									}else{
										if(lastexportall.get(j).getUser_Code().equals(exportall.getBmi().get(j2).getUser_Code())) {
											index=j;
											Whether=true;
											break;
										}
									}

								}
								if(Whether) {
									lastexportall.get(index).setUser_Code(exportall.getBmi().get(j2).getUser_Code());
									lastexportall.get(index).setName(exportall.getBmi().get(j2).getName());
									lastexportall.get(index).setGender(exportall.getBmi().get(j2).getGender());
									lastexportall.get(index).setOrg_Name(exportall.getBmi().get(j2).getOrg_Name());
									lastexportall.get(index).setU_Height(exportall.getBmi().get(j2).getU_Height());
									lastexportall.get(index).setU_Weight(exportall.getBmi().get(j2).getU_Weight());
									lastexportall.get(index).setBmi_Entry_Time(exportall.getBmi().get(j2).getEntry_Time());
									lastexportall.get(index).setTime(exportall.getBmi().get(j2).getEntry_Time());
								}else{
									YhExportAll manyalls=new YhExportAll();
									manyalls.setUser_Code(exportall.getBmi().get(j2).getUser_Code());
									manyalls.setName(exportall.getBmi().get(j2).getName());
									manyalls.setGender(exportall.getBmi().get(j2).getGender());
									manyalls.setOrg_Name(exportall.getBmi().get(j2).getOrg_Name());
									manyalls.setU_Height(exportall.getBmi().get(j2).getU_Height());
									manyalls.setU_Weight(exportall.getBmi().get(j2).getU_Weight());
									manyalls.setBmi_Entry_Time(exportall.getBmi().get(j2).getEntry_Time());
									manyalls.setTime(exportall.getBmi().get(j2).getEntry_Time());
									lastexportall.add(manyalls);
								}
								
							}
						}
						
						if(all.get(k).equals("血脂四项")) {
							int sun=lastexportall.size();
							for (int j2 = 0; j2 < exportall.getBloofat().size(); j2++) {
								boolean Whether=false;
								int index=0;
								for (int j = 0; j < sun; j++) {
									if(yhexportnow.getTime()){
										if(lastexportall.get(j).getUser_Code().equals(exportall.getBloofat().get(j2).getUser_Code())&& lastexportall.get(j).getTime().substring(0, 10).equals(exportall.getBloofat().get(j2).getEntry_Time().substring(0, 10))) {
											index=j;
											Whether=true;
											break;
										}
									}else{
										if(lastexportall.get(j).getUser_Code().equals(exportall.getBloofat().get(j2).getUser_Code())) {
											index=j;
											Whether=true;
											break;
										}
									}

										
								}
								if(Whether) {
									lastexportall.get(index).setUser_Code(exportall.getBloofat().get(j2).getUser_Code());
									lastexportall.get(index).setName(exportall.getBloofat().get(j2).getName());
									lastexportall.get(index).setGender(exportall.getBloofat().get(j2).getGender());
									lastexportall.get(index).setOrg_Name(exportall.getBloofat().get(j2).getOrg_Name());
									lastexportall.get(index).setCholesterin(exportall.getBloofat().get(j2).getCholesterin());
									lastexportall.get(index).setTring(exportall.getBloofat().get(j2).getTring());
									lastexportall.get(index).setHdl(exportall.getBloofat().get(j2).getHdl());
									lastexportall.get(index).setLdl(exportall.getBloofat().get(j2).getLdl());
									lastexportall.get(index).setBloofat_Entry_Time(exportall.getBloofat().get(j2).getEntry_Time());
									lastexportall.get(index).setTime(exportall.getBloofat().get(j2).getEntry_Time());
								}else {
									YhExportAll manyalls=new YhExportAll();
									manyalls.setUser_Code(exportall.getBloofat().get(j2).getUser_Code());
									manyalls.setName(exportall.getBloofat().get(j2).getName());
									manyalls.setGender(exportall.getBloofat().get(j2).getGender());
									manyalls.setOrg_Name(exportall.getBloofat().get(j2).getOrg_Name());
									manyalls.setCholesterin(exportall.getBloofat().get(j2).getCholesterin());
									manyalls.setTring(exportall.getBloofat().get(j2).getTring());
									manyalls.setHdl(exportall.getBloofat().get(j2).getHdl());
									manyalls.setLdl(exportall.getBloofat().get(j2).getLdl());
									manyalls.setBloofat_Entry_Time(exportall.getBloofat().get(j2).getEntry_Time());
									manyalls.setTime(exportall.getBloofat().get(j2).getEntry_Time());
									lastexportall.add(manyalls);
								}
							}
						}


						if(all.get(k).equals("肾功能")) {
							int sun=lastexportall.size();
							for (int j2 = 0; j2 < exportall.getYhExportRenalFunctions().size(); j2++) {
								boolean Whether=false;
								int index=0;
								for (int j = 0; j < sun; j++) {
									if(yhexportnow.getTime()){
										if(lastexportall.get(j).getUser_Code().equals(exportall.getYhExportRenalFunctions().get(j2).getUser_Code())&& lastexportall.get(j).getTime().substring(0, 10).equals(exportall.getYhExportRenalFunctions().get(j2).getInertTime().substring(0, 10))) {
											index=j;
											Whether=true;
											break;
										}
									}else{
										if(lastexportall.get(j).getUser_Code().equals(exportall.getYhExportRenalFunctions().get(j2).getUser_Code())) {
											index=j;
											Whether=true;
											break;
										}
									}


								}
								if(Whether) {
									lastexportall.get(index).setUser_Code(exportall.getYhExportRenalFunctions().get(j2).getUser_Code());
									lastexportall.get(index).setName(exportall.getYhExportRenalFunctions().get(j2).getName());
									lastexportall.get(index).setGender(exportall.getYhExportRenalFunctions().get(j2).getGender());
									lastexportall.get(index).setOrg_Name(exportall.getYhExportRenalFunctions().get(j2).getOrg_Name());
									lastexportall.get(index).setCreatinine(exportall.getYhExportRenalFunctions().get(j2).getCreatinine());
									lastexportall.get(index).setUrea_nitrogen(exportall.getYhExportRenalFunctions().get(j2).getUrea_nitrogen());
									lastexportall.get(index).setUric_acid(exportall.getYhExportRenalFunctions().get(j2).getUric_acid());

									lastexportall.get(index).setInsertTime(exportall.getYhExportRenalFunctions().get(j2).getInertTime());
									lastexportall.get(index).setTime(exportall.getYhExportRenalFunctions().get(j2).getInertTime());
								}else {
									YhExportAll manyalls=new YhExportAll();
									manyalls.setUser_Code(exportall.getYhExportRenalFunctions().get(j2).getUser_Code());
									manyalls.setName(exportall.getYhExportRenalFunctions().get(j2).getName());
									manyalls.setGender(exportall.getYhExportRenalFunctions().get(j2).getGender());
									manyalls.setOrg_Name(exportall.getYhExportRenalFunctions().get(j2).getOrg_Name());
									manyalls.setCreatinine(exportall.getYhExportRenalFunctions().get(j2).getCreatinine());
									manyalls.setUrea_nitrogen(exportall.getYhExportRenalFunctions().get(j2).getUrea_nitrogen());
									manyalls.setUric_acid(exportall.getYhExportRenalFunctions().get(j2).getUric_acid());
									manyalls.setInsertTime(exportall.getYhExportRenalFunctions().get(j2).getInertTime());

									manyalls.setTime(exportall.getYhExportRenalFunctions().get(j2).getInertTime());
									lastexportall.add(manyalls);
								}

							}
						}

						
						if(all.get(k).equals("肝功能")) {
							for (int j2 = 0; j2 < exportall.getLiver().size(); j2++) {
								boolean Whether=false;
								int index=0;
								for (int j = 0; j < lastexportall.size(); j++) {
									if(yhexportnow.getTime()){
										if(lastexportall.get(j).getUser_Code().equals(exportall.getLiver().get(j2).getUser_Code())&& lastexportall.get(j).getTime().substring(0, 10).equals(exportall.getLiver().get(j2).getInsertTime().substring(0, 10))) {
											System.out.println(j);
											index=j;
											Whether=true;
											break;
										}
									}else{
										if(lastexportall.get(j).getUser_Code().equals(exportall.getLiver().get(j2).getUser_Code())) {
											index=j;
											Whether=true;
											break;
										}
									}

								}
								if(Whether) {
									lastexportall.get(index).setUser_Code(exportall.getLiver().get(j2).getUser_Code());
									lastexportall.get(index).setName(exportall.getLiver().get(j2).getName());
									lastexportall.get(index).setGender(exportall.getLiver().get(j2).getGender());
									lastexportall.get(index).setOrg_Name(exportall.getLiver().get(j2).getOrg_Name());
									lastexportall.get(index).setGu_Bing(exportall.getLiver().get(j2).getGu_Bing());
									lastexportall.get(index).setMillet_straw(exportall.getLiver().get(j2).getMillet_straw());
									lastexportall.get(index).setSerum_albumin(exportall.getLiver().get(j2).getSerum_albumin());
									lastexportall.get(index).setSerum_globulin(exportall.getLiver().get(j2).getSerum_globulin());
									lastexportall.get(index).setInsertTime(exportall.getLiver().get(j2).getInsertTime());
									lastexportall.get(index).setTime(exportall.getLiver().get(j2).getInsertTime());
								}else {
									YhExportAll manyalls=new YhExportAll();
									manyalls.setUser_Code(exportall.getLiver().get(j2).getUser_Code());
									manyalls.setName(exportall.getLiver().get(j2).getName());
									manyalls.setGender(exportall.getLiver().get(j2).getGender());
									manyalls.setOrg_Name(exportall.getLiver().get(j2).getOrg_Name());
									manyalls.setGu_Bing(exportall.getLiver().get(j2).getGu_Bing());
									manyalls.setMillet_straw(exportall.getLiver().get(j2).getMillet_straw());
									manyalls.setSerum_albumin(exportall.getLiver().get(j2).getSerum_albumin());
									manyalls.setSerum_globulin(exportall.getLiver().get(j2).getSerum_globulin());
									manyalls.setInsertTime(exportall.getLiver().get(j2).getInsertTime());
									manyalls.setTime(exportall.getLiver().get(j2).getInsertTime());
									lastexportall.add(manyalls);
								}
							}
						}
						
						if(all.get(k).equals("尿检")) {
							for (int j2 = 0; j2 < exportall.getUba().size(); j2++) {
								boolean Whether=false;
								int index=0;
								for (int j = 0; j < lastexportall.size(); j++) {
									if(yhexportnow.getTime()) {
										if (lastexportall.get(j).getUser_Code().equals(exportall.getUba().get(j2).getUser_Code()) && lastexportall.get(j).getTime().substring(0, 10).equals(exportall.getUba().get(j2).getAddDate().substring(0, 10))) {
											index = j;
											Whether = true;
											break;
										}
									}else{
										if (lastexportall.get(j).getUser_Code().equals(exportall.getUba().get(j2).getUser_Code())) {
											index = j;
											Whether = true;
											break;
										}
									}
								}
								if(Whether) {
									lastexportall.get(index).setUser_Code(exportall.getUba().get(j2).getUser_Code());
									lastexportall.get(index).setName(exportall.getUba().get(j2).getName());
									lastexportall.get(index).setGender(exportall.getUba().get(j2).getGender());
									lastexportall.get(index).setOrg_Name(exportall.getUba().get(j2).getOrg_Name());
									lastexportall.get(index).setAddDate(exportall.getUba().get(j2).getAddDate());
									lastexportall.get(index).setEnv(exportall.getUba().get(j2).getEnv());
									lastexportall.get(index).setGlucose(exportall.getUba().get(j2).getGlucose());
									lastexportall.get(index).setBilirubin(exportall.getUba().get(j2).getBilirubin());
									lastexportall.get(index).setKetone(exportall.getUba().get(j2).getKetone());
									lastexportall.get(index).setProportion(exportall.getUba().get(j2).getProportion());
									lastexportall.get(index).setRedBloodCell(exportall.getUba().get(j2).getRedBloodCell());
									lastexportall.get(index).setPh(exportall.getUba().get(j2).getPh());
									lastexportall.get(index).setUrineProtein(exportall.getUba().get(j2).getUrineProtein());
									lastexportall.get(index).setUrobilinogen(exportall.getUba().get(j2).getUrobilinogen());
									lastexportall.get(index).setNitrite(exportall.getUba().get(j2).getNitrite());
									lastexportall.get(index).setWhiteBloodCell(exportall.getUba().get(j2).getWhiteBloodCell());
								}else {
									YhExportAll manyalls=new YhExportAll();
									manyalls.setUser_Code(exportall.getUba().get(j2).getUser_Code());
									manyalls.setName(exportall.getUba().get(j2).getName());
									manyalls.setGender(exportall.getUba().get(j2).getGender());
									manyalls.setOrg_Name(exportall.getUba().get(j2).getOrg_Name());
									manyalls.setAddDate(exportall.getUba().get(j2).getAddDate());
									manyalls.setEnv(exportall.getUba().get(j2).getEnv());
									manyalls.setGlucose(exportall.getUba().get(j2).getGlucose());
									manyalls.setBilirubin(exportall.getUba().get(j2).getBilirubin());
									manyalls.setKetone(exportall.getUba().get(j2).getKetone());
									manyalls.setProportion(exportall.getUba().get(j2).getProportion());
									manyalls.setRedBloodCell(exportall.getUba().get(j2).getRedBloodCell());
									manyalls.setPh(exportall.getUba().get(j2).getPh());
									manyalls.setUrineProtein(exportall.getUba().get(j2).getUrineProtein());
									manyalls.setUrobilinogen(exportall.getUba().get(j2).getUrobilinogen());
									manyalls.setNitrite(exportall.getUba().get(j2).getNitrite());
									manyalls.setWhiteBloodCell(exportall.getUba().get(j2).getWhiteBloodCell());
									manyalls.setTime(exportall.getUba().get(j2).getAddDate());
									lastexportall.add(manyalls);
								}
							}
						}
						
						if(all.get(k).equals("其它")) {
							for (int j2 = 0; j2 < exportall.getOther().size(); j2++) {
								boolean Whether=false;
								int index=0;
								for (int j = 0; j < lastexportall.size(); j++) {
									if(yhexportnow.getTime()) {
										if(lastexportall.get(j).getUser_Code().equals(exportall.getOther().get(j2).getUser_Code())&&lastexportall.get(j).getTime().substring(0, 10).equals(exportall.getOther().get(j2).getInsertTime().substring(0, 10))) {
											index=j;
											Whether=true;
											break;
										}
									}else{
										if(lastexportall.get(j).getUser_Code().equals(exportall.getOther().get(j2).getUser_Code())) {
											index=j;
											Whether=true;
											break;
										}
									}

								}
								if(Whether) {
									lastexportall.get(index).setUser_Code(exportall.getOther().get(j2).getUser_Code());
									lastexportall.get(index).setName(exportall.getOther().get(j2).getName());
									lastexportall.get(index).setGender(exportall.getOther().get(j2).getGender());
									lastexportall.get(index).setOrg_Name(exportall.getOther().get(j2).getOrg_Name());
									lastexportall.get(index).setUrine_microprotein(exportall.getOther().get(j2).getUrine_microprotein());
									lastexportall.get(index).setGlycosylated_hemoglobin(exportall.getOther().get(j2).getGlycosylated_hemoglobin());
									lastexportall.get(index).setHcy(exportall.getOther().get(j2).getHcy());
									lastexportall.get(index).setOther_insertTime(exportall.getOther().get(j2).getInsertTime());
								}else {
									YhExportAll manyalls=new YhExportAll();
									manyalls.setUser_Code(exportall.getOther().get(j2).getUser_Code());
									manyalls.setName(exportall.getOther().get(j2).getName());
									manyalls.setGender(exportall.getOther().get(j2).getGender());
									manyalls.setOrg_Name(exportall.getOther().get(j2).getOrg_Name());
									manyalls.setUrine_microprotein(exportall.getOther().get(j2).getUrine_microprotein());
									manyalls.setGlycosylated_hemoglobin(exportall.getOther().get(j2).getGlycosylated_hemoglobin());
									manyalls.setHcy(exportall.getOther().get(j2).getHcy());
									manyalls.setOther_insertTime(exportall.getOther().get(j2).getInsertTime());
									manyalls.setTime(exportall.getOther().get(j2).getInsertTime());
									lastexportall.add(manyalls);
								}
							}
						}
						
					}
					// 循环表数据导出总汇表
					for (int j = 0; j < lastexportall.size(); j++) {
						System.out.println(lastexportall);
						HSSFRow row = sheet.createRow(j + 2);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(lastexportall.get(j).getUser_Code());
						row.createCell(1).setCellValue(lastexportall.get(j).getName());
						row.createCell(2).setCellValue(lastexportall.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(lastexportall.get(j).getOrg_Name());
						if(yhexportnow.getBegintime()!=null){
							row.createCell(4).setCellValue(lastexportall.get(j).getTime());
						}
						for (int l = 0; l < biaoti.size(); l++) {
							if(biaoti.get(l).equals("血糖")) {
								row.createCell(l).setCellValue(lastexportall.get(j).getBs()==null?"":lastexportall.get(j).getBs().toString());
								row.createCell(l+1).setCellValue(lastexportall.get(j).getBsevn()==null?"":lastexportall.get(j).getBsevn().toString());
								row.createCell(l+2).setCellValue(lastexportall.get(j).getBloodsuger_Entry_Time()==null?"":lastexportall.get(j).getBloodsuger_Entry_Time().toString());
								l=l+3;
							}
							if(biaoti.get(l).equals("基础代谢")) {
								row.createCell(l+2).setCellValue(lastexportall.get(j).getFat_rate()==null?"":lastexportall.get(j).getFat_rate().toString().equals("0.0")?"":lastexportall.get(j).getFat_rate().toString());
								row.createCell(l+4).setCellValue(lastexportall.get(j).getMuscle()==null?"":lastexportall.get(j).getMuscle().toString().equals("0.0")?"":lastexportall.get(j).getMuscle().toString());
								row.createCell(l+1).setCellValue(lastexportall.get(j).getBones()==null?"":lastexportall.get(j).getBones().toString().equals("0.0")?"":lastexportall.get(j).getBones().toString());
								row.createCell(l+6).setCellValue(lastexportall.get(j).getVisceral_level()==null?"":lastexportall.get(j).getVisceral_level().toString().equals("0.0")?"":lastexportall.get(j).getVisceral_level().toString());
								row.createCell(l+7).setCellValue(lastexportall.get(j).getWater()==null?"":lastexportall.get(j).getWater().toString().equals("0.0")?"":lastexportall.get(j).getWater().toString());
								row.createCell(l+3).setCellValue(lastexportall.get(j).getHeat()==null?"":lastexportall.get(j).getHeat().toString().equals("0.0")?"":lastexportall.get(j).getHeat().toString());
								row.createCell(l+5).setCellValue(lastexportall.get(j).getProtein()==null?"":lastexportall.get(j).getProtein().toString().equals("0.0")?"":lastexportall.get(j).getProtein().toString());
								row.createCell(l).setCellValue(lastexportall.get(j).getBasal_met()==null?"":lastexportall.get(j).getBasal_met().toString().equals("0.0")?"":lastexportall.get(j).getBasal_met().toString());
								row.createCell(l+8).setCellValue(lastexportall.get(j).getBodyfat_Entry_Time()==null?"":lastexportall.get(j).getBodyfat_Entry_Time().toString());
								l=l+9;
								if(l>=biaoti.size()) {
									break;
								}
							}
							if(biaoti.get(l).equals("舒张压")) {
								row.createCell(l+1).setCellValue(lastexportall.get(j).getSbp()==null?"":lastexportall.get(j).getSbp().toString());
								row.createCell(l).setCellValue(lastexportall.get(j).getDbp()==null?"":lastexportall.get(j).getDbp().toString());
								row.createCell(l+2).setCellValue(lastexportall.get(j).getXinlv()==null?"":lastexportall.get(j).getXinlv().toString());
								row.createCell(l+3).setCellValue(lastexportall.get(j).getBloodpressure_Entry_Time()==null?"":lastexportall.get(j).getBloodpressure_Entry_Time().toString());
								l=l+4;
								if(l>=biaoti.size()) {
									break;
								}
							}
							if(biaoti.get(l).equals("身高")) {
								row.createCell(l).setCellValue(lastexportall.get(j).getU_Height()==null?"":lastexportall.get(j).getU_Height().toString());
								row.createCell(l+1).setCellValue(lastexportall.get(j).getU_Weight()==null?"":lastexportall.get(j).getU_Weight().toString());
								if(lastexportall.get(j).getU_Weight()!=null &&lastexportall.get(j).getU_Height()!=null) {
									Double bf= (Double.parseDouble(lastexportall.get(j).getU_Weight().toString())/ ((Double.parseDouble(lastexportall.get(j).getU_Height().toString()) / 100) * (Double.parseDouble(lastexportall.get(j).getU_Height().toString())) / 100));
									row.createCell(l+2).setCellValue(String.format("%.2f", bf));
								}else {
									row.createCell(l+2).setCellValue("");
								}
								
								row.createCell(l+3).setCellValue(lastexportall.get(j).getBmi_Entry_Time()==null?"":lastexportall.get(j).getBmi_Entry_Time().toString());
								l=l+4;
								if(l>=biaoti.size()) {
									break;
								}
							}
							if(biaoti.get(l).equals("总胆固醇")) {
								row.createCell(l).setCellValue(lastexportall.get(j).getCholesterin()==null?"":lastexportall.get(j).getCholesterin().toString());
								row.createCell(l+1).setCellValue(lastexportall.get(j).getTring()==null?"":lastexportall.get(j).getTring().toString());
								row.createCell(l+2).setCellValue(lastexportall.get(j).getHdl()==null?"":lastexportall.get(j).getHdl().toString());
								row.createCell(l+3).setCellValue(lastexportall.get(j).getLdl()==null?"":lastexportall.get(j).getLdl().toString());
								row.createCell(l+4).setCellValue(lastexportall.get(j).getBloofat_Entry_Time()==null?"":lastexportall.get(j).getBloofat_Entry_Time().toString());
								l=l+5;
								if(l>=biaoti.size()) {
									break;
								}
							}

							if(biaoti.get(l).equals("血肌酐")) {
								row.createCell(l).setCellValue(lastexportall.get(j).getCreatinine()==null?"":lastexportall.get(j).getCreatinine().toString());
								row.createCell(l+1).setCellValue(lastexportall.get(j).getUrea_nitrogen()==null?"":lastexportall.get(j).getUrea_nitrogen().toString());
								row.createCell(l+2).setCellValue(lastexportall.get(j).getUric_acid()==null?"":lastexportall.get(j).getUric_acid().toString());
								row.createCell(l+3).setCellValue(lastexportall.get(j).getInsertTime()==null?"":lastexportall.get(j).getInsertTime().toString());
								l=l+4;
								if(l>=biaoti.size()) {
									break;
								}
							}
							if(biaoti.get(l).equals("谷丙转氨酶")) {
								row.createCell(l).setCellValue(lastexportall.get(j).getGu_Bing()==null?"":lastexportall.get(j).getGu_Bing().toString());
								row.createCell(l+1).setCellValue(lastexportall.get(j).getMillet_straw()==null?"":lastexportall.get(j).getMillet_straw().toString());
								row.createCell(l+2).setCellValue(lastexportall.get(j).getSerum_albumin()==null?"":lastexportall.get(j).getSerum_albumin().toString());
								row.createCell(l+3).setCellValue(lastexportall.get(j).getSerum_globulin()==null?"":lastexportall.get(j).getSerum_globulin().toString());
								row.createCell(l+4).setCellValue(lastexportall.get(j).getInsertTime()==null?"":lastexportall.get(j).getInsertTime().toString());
								l=l+5;
								if(l>=biaoti.size()) {
									break;
								}
							}


							
							if(biaoti.get(l).equals("葡萄糖")) {
								row.createCell(l).setCellValue(lastexportall.get(j).getGlucose()==null?"":lastexportall.get(j).getGlucose().toString());
								row.createCell(l+1).setCellValue(lastexportall.get(j).getBilirubin()==null?"":lastexportall.get(j).getBilirubin().toString());
								row.createCell(l+2).setCellValue(lastexportall.get(j).getKetone()==null?"":lastexportall.get(j).getKetone().toString());
								row.createCell(l+3).setCellValue(lastexportall.get(j).getProportion()==null?"":lastexportall.get(j).getProportion().toString());
								row.createCell(l+4).setCellValue(lastexportall.get(j).getRedBloodCell()==null?"":lastexportall.get(j).getRedBloodCell().toString());
								row.createCell(l+5).setCellValue(lastexportall.get(j).getPh()==null?"":lastexportall.get(j).getPh().toString());
								row.createCell(l+6).setCellValue(lastexportall.get(j).getUrineProtein()==null?"":lastexportall.get(j).getUrineProtein().toString());
								row.createCell(l+7).setCellValue(lastexportall.get(j).getUrobilinogen()==null?"":lastexportall.get(j).getUrobilinogen().toString());
								row.createCell(l+8).setCellValue(lastexportall.get(j).getNitrite()==null?"":lastexportall.get(j).getNitrite().toString());
								row.createCell(l+9).setCellValue(lastexportall.get(j).getWhiteBloodCell()==null?"":lastexportall.get(j).getWhiteBloodCell().toString());
								row.createCell(l+10).setCellValue(lastexportall.get(j).getEnv()==null?"":lastexportall.get(j).getEnv().toString());
								row.createCell(l+11).setCellValue(lastexportall.get(j).getAddDate()==null?"":lastexportall.get(j).getAddDate().toString());
								l=l+12;
								if(l>=biaoti.size()) {
									break;
								}
							}
							
							
							if(biaoti.get(l).equals("尿微量蛋白")) {
								row.createCell(l).setCellValue(lastexportall.get(j).getUrine_microprotein()==null?"":lastexportall.get(j).getUrine_microprotein().toString());
								row.createCell(l+1).setCellValue(lastexportall.get(j).getGlycosylated_hemoglobin()==null?"":lastexportall.get(j).getGlycosylated_hemoglobin().toString());
								row.createCell(l+2).setCellValue(lastexportall.get(j).getHcy()==null?"":lastexportall.get(j).getHcy().toString());
								row.createCell(l+3).setCellValue(lastexportall.get(j).getInsertTime()==null?"":lastexportall.get(j).getInsertTime().toString());
								l=l+4;
								if(l>=biaoti.size()) {
									break;
								}
							}
							
						}
						
					}
				}
			if (sheetname.get(i).equals("动脉硬化")) {
				// 循环表数据
				SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (int j = 0; j < yhUserArteries.size(); j++) {
					YhUserArtery artery=yhUserArteries.get(j);
					HSSFRow row = sheet.createRow(j + 1);
					row.setHeightInPoints(18);
					row.createCell(0).setCellValue(artery.getUsesrCode());
					row.createCell(1).setCellValue(artery.getName());
					row.createCell(2).setCellValue(artery.getGender() == 1 ? "男" : "女");
					row.createCell(3).setCellValue(artery.getOrg_Name());
					System.out.println(artery.getTime());
					row.createCell(4).setCellValue(artery.getTime());
					if(artery.getSbp()!=null) row.createCell(5).setCellValue(artery.getSbp());
					if(artery.getDbp()!=null) row.createCell(6).setCellValue(artery.getDbp());
					if(artery.getPulse()!=null) row.createCell(7).setCellValue(artery.getPulse());
					if(artery.getAvi()!=null) row.createCell(8).setCellValue(artery.getAvi());
					if(artery.getApi()!=null) row.createCell(9).setCellValue(artery.getApi());
					if(artery.getCsbp()!=null) row.createCell(10).setCellValue(artery.getCsbp());
					if(artery.getCapp()!=null) row.createCell(11).setCellValue(artery.getCapp());
				}
			}

				if (sheetname.get(i).equals("检测数据血压")) {
					// 循环表数据
					for (int j = 0; j < bloodpressure.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(bloodpressure.get(j).getUser_Code());
						row.createCell(1).setCellValue(bloodpressure.get(j).getName());
						row.createCell(2).setCellValue(bloodpressure.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(bloodpressure.get(j).getOrg_Name());
						row.createCell(4).setCellValue(bloodpressure.get(j).getDbp());
						row.createCell(5).setCellValue(bloodpressure.get(j).getSbp());
						if(bloodpressure.get(j).getXinlv()!=null){
							row.createCell(6).setCellValue(bloodpressure.get(j).getXinlv().equals("0")?"":bloodpressure.get(j).getXinlv());
						}
						row.createCell(7).setCellValue(bloodpressure.get(j).getEntry_Time());
					}
				}
				NumberFormat numberFormat = NumberFormat.getNumberInstance();
		        numberFormat.setMaximumFractionDigits(2);
				if (sheetname.get(i).equals("检测数据BMI")) {
					// 循环表数据
					for (int j = 0; j < bmi.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						Double bf=0.00;
						System.out.println(bmi.get(j).getUser_Code());
						if (bmi.get(j).getU_Weight()!=null && bmi.get(j).getU_Height()!=null){
							 bf= (Double.parseDouble(bmi.get(j).getU_Weight().toString()) / ((Double.parseDouble(bmi.get(j).getU_Height().toString()) / 100) * (Double.parseDouble(bmi.get(j).getU_Height().toString())) / 100));
						}

						String douStr = numberFormat.format(bf);
						row.createCell(0).setCellValue(bmi.get(j).getUser_Code());
						row.createCell(1).setCellValue(bmi.get(j).getName());
						row.createCell(2).setCellValue(bmi.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(bmi.get(j).getOrg_Name());
						if(bmi.get(j).getU_Height()!=null){
							row.createCell(4).setCellValue(bmi.get(j).getU_Height());
						}
						if(bmi.get(j).getU_Weight()!=null) {
							row.createCell(5).setCellValue(bmi.get(j).getU_Weight());
						}
						row.createCell(6).setCellValue(douStr);
						row.createCell(7).setCellValue(bmi.get(j).getEntry_Time());
					}
				}
				
				if (sheetname.get(i).equals("检测数据血糖")) {
					// 循环表数据
					for (int j = 0; j < bloodsuger.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(bloodsuger.get(j).getUser_Code());
						row.createCell(1).setCellValue(bloodsuger.get(j).getName());
						row.createCell(2).setCellValue(bloodsuger.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(bloodsuger.get(j).getOrg_Name());
						row.createCell(4).setCellValue(bloodsuger.get(j).getBs());
						row.createCell(5).setCellValue(bloodsuger.get(j).getEnv());
						row.createCell(6).setCellValue(bloodsuger.get(j).getEntry_Time());
					}
				}
				if (sheetname.get(i).equals("检测数据血脂四项")) {
					// 循环表数据
					for (int j = 0; j < bloofat.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(bloofat.get(j).getUser_Code());
						row.createCell(1).setCellValue(bloofat.get(j).getName());
						row.createCell(2).setCellValue(bloofat.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(bloofat.get(j).getOrg_Name());
						row.createCell(4).setCellValue(String.format("%.2f", bloofat.get(j).getCholesterin()));
						row.createCell(5).setCellValue(String.format("%.2f", bloofat.get(j).getTring()));
						row.createCell(6).setCellValue(String.format("%.2f", bloofat.get(j).getHdl()));
						row.createCell(7).setCellValue(String.format("%.2f", bloofat.get(j).getLdl()));
						row.createCell(8).setCellValue(bloofat.get(j).getEntry_Time());
					}
				}
				
				
				if (sheetname.get(i).equals("肝功能")) {
					// 循环表数据
					for (int j = 0; j < liver.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(liver.get(j).getUser_Code());
						row.createCell(1).setCellValue(liver.get(j).getName());
						row.createCell(2).setCellValue(liver.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(liver.get(j).getOrg_Name());
						row.createCell(4).setCellValue(liver.get(j).getGu_Bing());
						row.createCell(5).setCellValue(liver.get(j).getMillet_straw());
						row.createCell(6).setCellValue(liver.get(j).getSerum_albumin());
						row.createCell(7).setCellValue(liver.get(j).getSerum_globulin());
						row.createCell(8).setCellValue(liver.get(j).getInsertTime());
					}
				}
			if (sheetname.get(i).equals("肾功能")) {
				// 循环表数据
				for (int j = 0; j < renalfuction.size(); j++) {
					HSSFRow row = sheet.createRow(j + 1);
					row.setHeightInPoints(18);
					row.createCell(0).setCellValue(renalfuction.get(j).getUser_Code());
					row.createCell(1).setCellValue(renalfuction.get(j).getName());
					row.createCell(2).setCellValue(renalfuction.get(j).getGender() == 1 ? "男" : "女");
					row.createCell(3).setCellValue(renalfuction.get(j).getOrg_Name());
					row.createCell(4).setCellValue(renalfuction.get(j).getCreatinine());
					row.createCell(5).setCellValue(renalfuction.get(j).getUrea_nitrogen());
					row.createCell(6).setCellValue(renalfuction.get(j).getUric_acid());
					row.createCell(7).setCellValue(renalfuction.get(j).getInertTime());
				}
			}

				if (sheetname.get(i).equals("尿检")) {
					// 循环表数据
					for (int j = 0; j < uba.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(uba.get(j).getUser_Code());
						row.createCell(1).setCellValue(uba.get(j).getName());
						row.createCell(2).setCellValue(uba.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(uba.get(j).getOrg_Name());
						row.createCell(4).setCellValue(uba.get(j).getGlucose());
						row.createCell(5).setCellValue(uba.get(j).getBilirubin());
						row.createCell(6).setCellValue(uba.get(j).getKetone());
						row.createCell(7).setCellValue(uba.get(j).getProportion());
						row.createCell(8).setCellValue(uba.get(j).getRedBloodCell());
						row.createCell(9).setCellValue(uba.get(j).getPh());
						row.createCell(10).setCellValue(uba.get(j).getUrineProtein());
						row.createCell(11).setCellValue(uba.get(j).getUrobilinogen());
						row.createCell(12).setCellValue(uba.get(j).getNitrite());
						row.createCell(13).setCellValue(uba.get(j).getWhiteBloodCell());
						row.createCell(14).setCellValue(uba.get(j).getEnv());
						row.createCell(15).setCellValue(uba.get(j).getAddDate());
					}
				}
				if (sheetname.get(i).equals("其它")) {
					// 循环表数据
					for (int j = 0; j < other.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(other.get(j).getUser_Code());
						row.createCell(1).setCellValue(other.get(j).getName());
						row.createCell(2).setCellValue(other.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(other.get(j).getOrg_Name());
						row.createCell(4).setCellValue(other.get(j).getUrine_microprotein());
						row.createCell(5).setCellValue(other.get(j).getGlycosylated_hemoglobin());
						row.createCell(6).setCellValue(other.get(j).getHcy());
						row.createCell(7).setCellValue(other.get(j).getInsertTime());
					}
				}
				if (sheetname.get(i).equals("检测数据体脂")) {
					// 循环表数据
					for (int j = 0; j < bodyfat.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						row.createCell(0).setCellValue(bodyfat.get(j).getUser_Code());
						row.createCell(1).setCellValue(bodyfat.get(j).getName());
						row.createCell(2).setCellValue(bodyfat.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(bodyfat.get(j).getOrg_Name());
						row.createCell(4).setCellValue(bodyfat.get(j).getBasal_met()==null?"":bodyfat.get(j).getBasal_met()==0?"":bodyfat.get(j).getBasal_met().toString());
						row.createCell(5).setCellValue(bodyfat.get(j).getBones()==null?"":bodyfat.get(j).getBones()==0?"":bodyfat.get(j).getBones().toString());
						row.createCell(6).setCellValue(bodyfat.get(j).getFat_rate()==null?"":bodyfat.get(j).getFat_rate()==0?"":bodyfat.get(j).getFat_rate().toString());
						row.createCell(7).setCellValue(bodyfat.get(j).getHeat()==null?"":bodyfat.get(j).getHeat()==0?"":bodyfat.get(j).getHeat().toString());
						row.createCell(8).setCellValue(bodyfat.get(j).getMuscle()==null?"":bodyfat.get(j).getMuscle()==0?"":bodyfat.get(j).getMuscle().toString());
						row.createCell(9).setCellValue(bodyfat.get(j).getProtein()==null?"":bodyfat.get(j).getProtein()==0?"":bodyfat.get(j).getProtein().toString());
						row.createCell(10).setCellValue(bodyfat.get(j).getVisceral_level()==null?"":bodyfat.get(j).getVisceral_level()==0?"":bodyfat.get(j).getVisceral_level().toString());
						row.createCell(11).setCellValue(bodyfat.get(j).getWater()==null?"":bodyfat.get(j).getWater()==0?"":bodyfat.get(j).getWater().toString()	);
						row.createCell(12).setCellValue(bodyfat.get(j).getEntry_Time());
					}
				}
				
				if (sheetname.get(i).equals("心理健康评估问卷")) {
					// 循环表数据
					for (int j = 0; j < exportxinli.size(); j++) {
						HSSFRow row = sheet.createRow(j + 1);
						row.setHeightInPoints(18);
						String anser=exportxinli.get(j).getEvaluate_Data();
						anser=anser.trim();
						anser=anser.substring(1,anser.length()-1);
						String[] ansers=anser.split(",");
						row.createCell(0).setCellValue(exportxinli.get(j).getUser_Code());
						row.createCell(1).setCellValue(exportxinli.get(j).getName());
						row.createCell(2).setCellValue(exportxinli.get(j).getGender() == 1 ? "男" : "女");
						row.createCell(3).setCellValue(exportxinli.get(j).getOrg_Name());
						row.createCell(4).setCellValue(exportxinli.get(j).getEvaluate_Date());
						row.createCell(5).setCellValue(Integer.valueOf(ansers[0])==1?"常有":Integer.valueOf(ansers[0])==7?"偶有":(Integer.valueOf(ansers[0])==8?"罕有":(Integer.valueOf(ansers[0])==0?"从无":"")));
						row.createCell(6).setCellValue(Integer.valueOf(ansers[1])==0?"常有":Integer.valueOf(ansers[1])==6?"偶有":(Integer.valueOf(ansers[1])==12?"罕有":(Integer.valueOf(ansers[1])==6?"从无":"")));
						row.createCell(7).setCellValue(Integer.valueOf(ansers[2])==0?"常有":Integer.valueOf(ansers[2])==5?"偶有":(Integer.valueOf(ansers[2])==13?"罕有":(Integer.valueOf(ansers[2])==10?"从无":"")));
						row.createCell(8).setCellValue(Integer.valueOf(ansers[3])==0?"常有":Integer.valueOf(ansers[3])==5?"偶有":(Integer.valueOf(ansers[3])==15?"罕有":(Integer.valueOf(ansers[3])==8?"从无":"")));
						row.createCell(9).setCellValue(Integer.valueOf(ansers[4])==0?"常有":Integer.valueOf(ansers[4])==4?"偶有":(Integer.valueOf(ansers[4])==12?"罕有":(Integer.valueOf(ansers[4])==4?"从无":"")));
						row.createCell(10).setCellValue(Integer.valueOf(ansers[5])==0?"常有":Integer.valueOf(ansers[5])==7?"偶有":(Integer.valueOf(ansers[5])==12?"罕有":(Integer.valueOf(ansers[5])==9?"从无":"")));
						row.createCell(11).setCellValue(Integer.valueOf(ansers[6])==3?"常有":Integer.valueOf(ansers[6])==8?"偶有":(Integer.valueOf(ansers[6])==9?"罕有":(Integer.valueOf(ansers[6])==0?"从无":"")));
						row.createCell(12).setCellValue(Integer.valueOf(ansers[7])==0?"常有":Integer.valueOf(ansers[7])==3?"偶有":(Integer.valueOf(ansers[7])==11?"罕有":(Integer.valueOf(ansers[7])==10?"从无":"")));
						row.createCell(13).setCellValue(Integer.valueOf(ansers[8])==0?"常有":Integer.valueOf(ansers[8])==8?"偶有":(Integer.valueOf(ansers[8])==11?"罕有":(Integer.valueOf(ansers[8])==6?"从无":"")));
						row.createCell(14).setCellValue(Integer.valueOf(ansers[9])==0?"常有":Integer.valueOf(ansers[9])==1?"偶有":(Integer.valueOf(ansers[9])==15?"罕有":(Integer.valueOf(ansers[9])==8?"从无":"")));
						row.createCell(15).setCellValue(Integer.valueOf(ansers[10])==0?"常有":Integer.valueOf(ansers[10])==2?"偶有":(Integer.valueOf(ansers[10])==16?"罕有":(Integer.valueOf(ansers[10])==6?"从无":"")));
						row.createCell(16).setCellValue(Integer.valueOf(ansers[11])==0?"常有":Integer.valueOf(ansers[11])==2?"偶有":(Integer.valueOf(ansers[11])==6?"罕有":(Integer.valueOf(ansers[11])==0?"从无":"")));
						row.createCell(17).setCellValue(Integer.valueOf(ansers[12])==0?"常有":Integer.valueOf(ansers[12])==8?"偶有":(Integer.valueOf(ansers[12])==11?"罕有":(Integer.valueOf(ansers[12])==9?"从无":"")));
						row.createCell(18).setCellValue(Integer.valueOf(ansers[13])==8?"常有":Integer.valueOf(ansers[13])==5?"偶有":(Integer.valueOf(ansers[13])==3?"罕有":(Integer.valueOf(ansers[13])==0?"从无":"")));
						row.createCell(19).setCellValue(Integer.valueOf(ansers[14])==2?"常有":Integer.valueOf(ansers[14])==4?"偶有":(Integer.valueOf(ansers[14])==8?"罕有":(Integer.valueOf(ansers[14])==2?"从无":"")));
						row.createCell(20).setCellValue(Integer.valueOf(ansers[15])==0?"常有":Integer.valueOf(ansers[15])==5?"偶有":(Integer.valueOf(ansers[15])==12?"罕有":(Integer.valueOf(ansers[15])==15?"从无":"")));
						row.createCell(21).setCellValue(Integer.valueOf(ansers[16])==0?"常有":Integer.valueOf(ansers[16])==4?"偶有":(Integer.valueOf(ansers[16])==15?"罕有":(Integer.valueOf(ansers[16])==5?"从无":"")));
						row.createCell(22).setCellValue(Integer.valueOf(ansers[17])==0?"常有":Integer.valueOf(ansers[17])==4?"偶有":(Integer.valueOf(ansers[17])==11?"罕有":(Integer.valueOf(ansers[17])==5?"从无":"")));
						row.createCell(23).setCellValue(Integer.valueOf(ansers[18])==0?"常有":Integer.valueOf(ansers[18])==1?"偶有":(Integer.valueOf(ansers[18])==11?"罕有":(Integer.valueOf(ansers[18])==16?"从无":"")));
						row.createCell(24).setCellValue(Integer.valueOf(ansers[19])==0?"常有":Integer.valueOf(ansers[19])==7?"偶有":(Integer.valueOf(ansers[19])==11?"罕有":(Integer.valueOf(ansers[19])==10?"从无":"")));
						row.createCell(25).setCellValue(Integer.valueOf(ansers[20])==0?"常有":Integer.valueOf(ansers[20])==9?"偶有":(Integer.valueOf(ansers[20])==11?"罕有":(Integer.valueOf(ansers[20])==8?"从无":"")));
						row.createCell(26).setCellValue(Integer.valueOf(ansers[21])==8?"常有":Integer.valueOf(ansers[21])==6?"偶有":(Integer.valueOf(ansers[21])==0?"罕有":(Integer.valueOf(ansers[21])==2?"从无":"")));
						row.createCell(27).setCellValue(Integer.valueOf(ansers[22])==0?"常有":Integer.valueOf(ansers[22])==7?"偶有":(Integer.valueOf(ansers[22])==10?"罕有":(Integer.valueOf(ansers[22])==11?"从无":"")));
						row.createCell(28).setCellValue(Integer.valueOf(ansers[23])==0?"常有":Integer.valueOf(ansers[23])==4?"偶有":(Integer.valueOf(ansers[23])==5?"罕有":(Integer.valueOf(ansers[23])==3?"从无":"")));
						row.createCell(29).setCellValue(Integer.valueOf(ansers[24])==11?"常有":Integer.valueOf(ansers[24])==7?"偶有":(Integer.valueOf(ansers[24])==6?"罕有":(Integer.valueOf(ansers[24])==0?"从无":"")));
						row.createCell(30).setCellValue(Integer.valueOf(ansers[25])==0?"常有":Integer.valueOf(ansers[25])==9?"偶有":(Integer.valueOf(ansers[25])==7?"罕有":(Integer.valueOf(ansers[25])==4?"从无":"")));
						row.createCell(31).setCellValue(Integer.valueOf(ansers[26])==0?"常有":Integer.valueOf(ansers[26])==3?"偶有":(Integer.valueOf(ansers[26])==4?"罕有":(Integer.valueOf(ansers[26])==12?"从无":"")));
						row.createCell(32).setCellValue(Integer.valueOf(ansers[27])==0?"常有":Integer.valueOf(ansers[27])==3?"偶有":(Integer.valueOf(ansers[27])==5?"罕有":(Integer.valueOf(ansers[27])==13?"从无":"")));
						row.createCell(33).setCellValue(Integer.valueOf(ansers[28])==0?"常有":Integer.valueOf(ansers[28])==1?"偶有":(Integer.valueOf(ansers[28])==5?"罕有":(Integer.valueOf(ansers[28])==6?"从无":"")));
						row.createCell(34).setCellValue(Integer.valueOf(ansers[29])==0?"常有":Integer.valueOf(ansers[29])==5?"偶有":(Integer.valueOf(ansers[29])==18?"罕有":(Integer.valueOf(ansers[29])==13?"从无":"")));
						row.createCell(35).setCellValue(Integer.valueOf(ansers[30])==0?"常有":Integer.valueOf(ansers[30])==4?"偶有":(Integer.valueOf(ansers[30])==11?"罕有":(Integer.valueOf(ansers[30])==15?"从无":"")));
						row.createCell(36).setCellValue(Integer.valueOf(ansers[31])==0?"常有":Integer.valueOf(ansers[31])==10?"偶有":(Integer.valueOf(ansers[31])==10?"罕有":(Integer.valueOf(ansers[31])==8?"从无":"")));
						row.createCell(37).setCellValue(Integer.valueOf(ansers[32])==0?"常有":Integer.valueOf(ansers[32])==1?"偶有":(Integer.valueOf(ansers[32])==9?"罕有":(Integer.valueOf(ansers[32])==2?"从无":"")));
						row.createCell(38).setCellValue(Integer.valueOf(ansers[33])==0?"常有":Integer.valueOf(ansers[33])==2?"偶有":(Integer.valueOf(ansers[33])==9?"罕有":(Integer.valueOf(ansers[33])==5?"从无":"")));
						row.createCell(39).setCellValue(Integer.valueOf(ansers[34])==0?"常有":Integer.valueOf(ansers[34])==4?"偶有":(Integer.valueOf(ansers[34])==14?"罕有":(Integer.valueOf(ansers[34])==14?"从无":"")));
						row.createCell(40).setCellValue(Integer.valueOf(ansers[35])==0?"常有":Integer.valueOf(ansers[35])==0?"偶有":(Integer.valueOf(ansers[35])==11?"罕有":(Integer.valueOf(ansers[35])==9?"从无":"")));
						row.createCell(41).setCellValue(Integer.valueOf(ansers[36])==2?"常有":Integer.valueOf(ansers[36])==3?"偶有":(Integer.valueOf(ansers[36])==6?"罕有":(Integer.valueOf(ansers[36])==0?"从无":"")));
						row.createCell(42).setCellValue(Integer.valueOf(ansers[37])==1?"常有":Integer.valueOf(ansers[37])==0?"偶有":(Integer.valueOf(ansers[37])==16?"罕有":(Integer.valueOf(ansers[37])==7?"从无":"")));
						row.createCell(43).setCellValue(Integer.valueOf(ansers[38])==0?"常有":Integer.valueOf(ansers[38])==1?"偶有":(Integer.valueOf(ansers[38])==8?"罕有":(Integer.valueOf(ansers[38])==7?"从无":"")));
						row.createCell(44).setCellValue(Integer.valueOf(ansers[39])==0?"常有":Integer.valueOf(ansers[39])==4?"偶有":(Integer.valueOf(ansers[39])==9?"罕有":(Integer.valueOf(ansers[39])==3?"从无":"")));
						row.createCell(45).setCellValue(Integer.valueOf(ansers[40])==0?"常有":Integer.valueOf(ansers[40])==4?"偶有":(Integer.valueOf(ansers[40])==14?"罕有":(Integer.valueOf(ansers[40])==6?"从无":"")));
						row.createCell(46).setCellValue(Integer.valueOf(ansers[41])==0?"常有":Integer.valueOf(ansers[41])==9?"偶有":(Integer.valueOf(ansers[41])==13?"罕有":(Integer.valueOf(ansers[41])==6?"从无":"")));
						row.createCell(47).setCellValue(Integer.valueOf(ansers[42])==0?"常有":Integer.valueOf(ansers[42])==7?"偶有":(Integer.valueOf(ansers[42])==14?"罕有":(Integer.valueOf(ansers[42])==17?"从无":"")));
						row.createCell(48).setCellValue(Integer.valueOf(ansers[43])==0?"常有":Integer.valueOf(ansers[43])==1?"偶有":(Integer.valueOf(ansers[43])==2?"罕有":(Integer.valueOf(ansers[43])==13?"从无":"")));
						row.createCell(49).setCellValue(Integer.valueOf(ansers[44])==0?"常有":Integer.valueOf(ansers[44])==0?"偶有":(Integer.valueOf(ansers[44])==12?"罕有":(Integer.valueOf(ansers[44])==4?"从无":"")));
						if(ansers.length==46){
							row.createCell(50).setCellValue(Integer.valueOf(ansers[45])==2?"常有":Integer.valueOf(ansers[45])==3?"偶有":(Integer.valueOf(ansers[45])==5?"罕有":(Integer.valueOf(ansers[45])==0?"从无":"")));
						}

					}
				}
		}

		ExcelUtilQ.exportExcel(response, workbook);
	}
	public static Date StringToDate(String datetime){
		SimpleDateFormat sdFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = sdFormat.parse(datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

}
