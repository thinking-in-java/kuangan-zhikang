package com.yihe.system.utils;

import com.yihe.system.domain.MonitoringDataStandardVOJO;

/**
 * 数据监测标准（数据解读）
 * 
 * @author Administrator
 *
 */
public class MonitoringDataStandard {


	
	/**
	 * 体温
	 */
	public MonitoringDataStandardVOJO temperature(double tnum) {
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(tnum));
		vojo.setTypeName("体温");
		if (tnum >= 38.1) {
			vojo.setUnscramble(
					"这时候要不断地测量体温，需要多喝水，注意休息，补充维生素C，防止交叉感染，要警惕高烧引起的脱水现象。借助于辅助检查，明确发热的原因，及时的给予消炎抗病毒的治疗，以免机体出现反复发热的情况。");
			vojo.setAbnormal(1);
			vojo.setState("高热");
		} else if (tnum >= 37.3 && tnum <= 38) {
			vojo.setUnscramble(
					"可以多喝温热开水，通过排汗、排尿带走体内的热量。另外，还可以用热毛巾或者酒精擦浴腋窝、腹股沟、腘窝等血管比较丰富的地方，促进散热达到降温。同时，建议还要在病因上予以治疗，如果出现反复的低烧症状，建议最好要到医院进行必要的检查，根据病因予以治疗。");
			vojo.setAbnormal(1);
			vojo.setState("低热");
		} else if (tnum >= 36 && tnum <= 37.2) {
			vojo.setUnscramble("维持正常的体温要注意饮食正常，衣着适宜，运动出汗后马上要擦干汗等。");
			vojo.setAbnormal(0);
			vojo.setState("正常");
		} else if (tnum <= 35.9) {
			vojo.setUnscramble("当体温过低时可导致所有生理功能的加速，如果长期处于体温低，要引起重视，请及时去医院查明原因，做进一步的改善。");
			vojo.setAbnormal(1);
			vojo.setState("过低");
		} else {
			vojo.setUnscramble("当前数值异常");
			vojo.setAbnormal(1);
			vojo.setState("数值异常");
		}
		return vojo;
	}
	
	/**
	 * 血氧
	 */
	public MonitoringDataStandardVOJO bloodOxygen(double bnum){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(bnum));
		vojo.setTypeName("血氧");
		if(bnum>=95){
			vojo.setUnscramble("运动前后人体的血氧值数据差距是很大的，所在环境不同同时也对血氧影响很大。血氧的日常调节，可以选择一些有氧运动每日坚持，保持充足的睡眠，合理的饮食，多去环境好的地方，保持良好心态。");
			vojo.setAbnormal(0);
			vojo.setState("正常");
		}else
		if(bnum<95){
			vojo.setUnscramble("血氧的饱和度低于95，表明身体缺氧，血氧越低，缺氧程度越严重。如果长期血氧值偏低，希望引起重视，及时就医检测。如果过低是不正常的，需要特别的注意，及时就医查找病因。");
			vojo.setAbnormal(1);
			vojo.setState("不足");
		}else{
			vojo.setUnscramble("当前数值异常");
			vojo.setAbnormal(1);
			vojo.setState("数值异常");
		}
		
		
		
		return vojo;
	}
	
	/**
	 * 体重
	 */
	public MonitoringDataStandardVOJO  weight(double num){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(num));
		vojo.setTypeName("体重");
		if(num<18.5){
			vojo.setUnscramble("您的BMI显示过轻，建议您均衡饮食并渐进式的增加食欲，只有摄入的能量大于消耗的能量，人才能逐渐变胖哦。");
			vojo.setAbnormal(1);
			vojo.setState("消瘦");
		}else
		if(num>=18.5&&num<24){
			vojo.setUnscramble("您的BMI正常，非常棒，请持续控制体重。");
			vojo.setAbnormal(0);
			vojo.setState("正常");
		}else
		if(num>=24&&num<28){
			vojo.setUnscramble("您已超重了，健康问题征随着您体重的增加而增加，患糖尿病、心脏病、胆固醇过高和高血压风险正在逐渐加大。建议改变生活方式以起到控制甚至减轻体重的目的。");
			vojo.setAbnormal(1);
			vojo.setState("超重");
		}else
		if(num>=28){
			vojo.setUnscramble("您被封为肥胖“达人”了，需要减重。在均衡膳食的同时，应减少热量的摄入，同时辅以高频率的中等强度身体锻炼。减重的速度因人而异，通常以每周减重0.5Kg~1.0Kg为宜。");
			vojo.setAbnormal(1);
			vojo.setState("肥胖");
		}else{
			vojo.setUnscramble("当前数值异常");
			vojo.setAbnormal(1);
			vojo.setState("数值异常");
		}
		
		
		
		return vojo;
	}
	
	 
	/**
	 * 血压 sbp收缩压         dbp舒张压
	 */
	public MonitoringDataStandardVOJO  bloodPressure(int sbp,  int dbp){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(sbp));
		vojo.setParameter2(String.valueOf(dbp));
		vojo.setTypeName("血压");
		if(sbp<90&&dbp<60){
			vojo.setUnscramble("您的血压已经偏低，请注意补充营养，加强锻炼，改善神经、血管的调节功能；饮食注意增加血容量，少吃芹菜，苦瓜等降压食品；坚持锻炼，改善体质；定期检测血压，关注血压变化。");
			vojo.setAbnormal(1);
			vojo.setState("偏低");
		}
		else if(sbp<120&&dbp<80){
			vojo.setUnscramble("您的血压正常，请继续保持健康的生活习惯。合理饮食，控制好体重，定期测量血压，关注血压变化。");
			vojo.setAbnormal(0);
			vojo.setState("正常");
		}
		else if(sbp>=120&&sbp<140&&dbp>=80&&dbp<90){
			vojo.setUnscramble("您的血压不是最理想的哦，请改变不良的生活习惯，否则长期下去很可能导致高血压！");
			vojo.setAbnormal(1);
			vojo.setState("偏高");
		}
		else if(sbp>=140&&sbp<160&&dbp>=90&&dbp<100){
			vojo.setUnscramble("您的血压已触及一级高血压，建议您在服药控制血压的同时，调整膳食结构：低脂、低盐饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持良好心态，坚持运动。");
			vojo.setAbnormal(1);
			vojo.setState("轻度");
		}
		else if(sbp>=160&&sbp<180&&dbp>=100&&dbp<110){
			vojo.setUnscramble("您的血压已触及二级高血压，属于高危人群，建议您听从医生药物控制血压的同时，要关注您的靶器官功能，按时体检监测心脑血管，请勿匆忙变换体位，时刻预防心脑血管疾病的发生几率。调整膳食结构：低脂、低盐饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持良好心态，坚持运动。");
			vojo.setAbnormal(1);
			vojo.setState("中度");
		}
		else if(sbp>=180&&dbp>=110){
			vojo.setUnscramble("您的血压已经严重偏高，已达到三级高血压，建议您及时就医，在医生的指导下合理降压。");
			vojo.setAbnormal(1);
			vojo.setState("重度");
		}
		else if(sbp>=140&&dbp<90){
			vojo.setUnscramble("您的血压为单纯收缩期高血压，建议您调整膳食结构：低脂、低盐饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持良好心态，坚持运动。");
			vojo.setAbnormal(1);
			vojo.setState("单纯收缩期高血压");
		}
		else{
			vojo.setUnscramble("当前数值异常");
			vojo.setAbnormal(1);
			vojo.setState("数值异常");
		}
		return vojo;
	}
	
	/**
	 * 血糖 type 1为空腹 2为随机血糖、餐后血糖
	 */
	public MonitoringDataStandardVOJO  bloodSugar(double num,int type){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(num));
		vojo.setParameter2(String.valueOf(type));
		vojo.setTypeName("血糖");
		if(type==1){
			if(num<3.9){
				vojo.setUnscramble("您的血糖属于低血糖了，出现低血糖的时候一般会有心慌、胸闷、头晕、出冷汗等症状。吃饭的时候先吃点蔬菜再吃饭，这样有助于避免餐后血糖过高，减少血糖值变动幅度。");
				vojo.setAbnormal(1);
				vojo.setState("低血糖");
			}
			else if(num>=3.9&&num<6.1){
				vojo.setUnscramble("您的血糖正常，请继续保持健康的生活习惯。");
				vojo.setAbnormal(0);
				vojo.setState("正常");
			}else if(num>=6.1&&num<7.0){
				vojo.setUnscramble("您的血糖异常但未达到糖尿病诊断标准！建议您定期监测血糖，并就医明确诊断，制定合适的治疗方案或调整原有的控制方案。饮食上建议控制碳水化合物的量，可搭配一些低升糖指数食物，如全谷类和蔬菜，会让血糖上升缓慢。");
				vojo.setAbnormal(1);
				vojo.setState("糖尿病前期");
			}else if(num>=7){
				vojo.setUnscramble("您的血糖已经达到糖尿病诊断标准了！建议您定期监测血糖，并就医明确诊断，制定合适的治疗方案或调整原有的控制方案。饮食上建议控制碳水化合物的量，可搭配一些低升糖指数食物，如全谷类和蔬菜，会让血糖上升缓慢。");
				vojo.setAbnormal(1);
				vojo.setState("高血糖");
			}
		}
		if(type==2){
			if(num<3.9){
				vojo.setUnscramble("您的血糖属于低血糖了，出现低血糖的时候一般会有心慌、胸闷、头晕、出冷汗等症状。吃饭的时候先吃点蔬菜再吃饭，这样有助于避免餐后血糖过高，减少血糖值变动幅度。");
				vojo.setAbnormal(1);
				vojo.setState("低血糖");
			}
			else if(num>=3.9&&num<7.8){
				vojo.setUnscramble("您的血糖正常，请继续保持健康的生活习惯。");
				vojo.setAbnormal(0);
				vojo.setState("正常");
			}else if(num>=7.8&&num<11.1){
				vojo.setUnscramble("您的血糖异常但未达到糖尿病诊断标准！建议您定期监测血糖，并就医明确诊断，制定合适的治疗方案或调整原有的控制方案。饮食上建议控制碳水化合物的量，可搭配一些低升糖指数食物，如全谷类和蔬菜，会让血糖上升缓慢。");
				vojo.setAbnormal(1);
				vojo.setState("糖尿病前期");
			}else if(num>=11.1){
				vojo.setUnscramble("您的血糖已经达到糖尿病诊断标准了！建议您定期监测血糖，并就医明确诊断，制定合适的治疗方案或调整原有的控制方案。饮食上建议控制碳水化合物的量，可搭配一些低升糖指数食物，如全谷类和蔬菜，会让血糖上升缓慢。");
				vojo.setAbnormal(1);
				vojo.setState("高血糖");
			}
		}
		
		 
		return vojo;
	}
	
	/**
	 * 心率
	 */
	public MonitoringDataStandardVOJO  heartRate(int num){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(num));
		vojo.setTypeName("心率");
		if(num<60){
			vojo.setUnscramble("心动过缓可能会引起有头晕、乏力、倦怠等这些情况的发生。通过适当的进行一些比较缓和的体育运动，饮食做到营养全面均衡，荤素搭配和少吃多餐，多注意休息等方面来进行调整。如果心率低于40次/分钟，大多见于心脏病患者。");
			vojo.setAbnormal(1);
			vojo.setState("过缓");
		}
		else if(num>=60&&num<=100){
			vojo.setUnscramble("维持好正常的心率要注意适当运动，控制自己的体重，戒烟，拒绝油腻的食物，多吃水果和蔬菜。还要注意保持充足的睡眠。");
			vojo.setAbnormal(0);
			vojo.setState("正常");
		}else if(num>100){
			vojo.setUnscramble("发生心动过速后，立即蹲下，屏气，通常换几次气即有效。当心动过速发作开始后，间隔几秒再施用此法效果更好。虽然该方法对于缓解心动过速效果很好，但对心动过速只能起到中止发作的作用，而对原发性疾病仍要积极对症治疗，才可完全控制其发作。如果心率超过160次/分钟，大多见于心脏病患者。");
			vojo.setAbnormal(1);
			vojo.setState("过速");
		}else{
			vojo.setUnscramble("当前数值异常");
			vojo.setAbnormal(1);
			vojo.setState("数值异常");
		}
		return vojo;
	}
	
	
	/**
	 * 体脂 num体脂率  sex性别   1男  2女
	 */
	public MonitoringDataStandardVOJO  bodyFat(double num,int sex){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(num));
		vojo.setTypeName("体脂");
		if(sex==1){
			if(num<15){
				vojo.setUnscramble("您的体脂偏低，请合理膳食结构，增强锻炼。");
				vojo.setAbnormal(1);
				vojo.setState("偏低");
			}
			else if(num>=15&&num<=18){
				vojo.setUnscramble("您的体脂正常，请继续保持健康的生活习惯。");
				vojo.setAbnormal(0);
				vojo.setState("正常");
			}
			else if(num>18){
				vojo.setUnscramble("您的体脂偏高，当身体每天摄入的热量小于消耗的热量，就会瘦下来了。你需要主动消耗体内的脂肪。饮食方面，要减少高油脂食物，多吃高纤维蔬菜。运动你可以选择跳绳、跳舞、爬山等运动。例如如果每天慢跑30分钟，一周你就可能减掉1公斤。结合饮食，一个月也可能降低2%-3%的体脂率。");
				vojo.setAbnormal(1);
				vojo.setState("偏高");
			}else{
				vojo.setUnscramble("当前数值异常");
				vojo.setAbnormal(1);
				vojo.setState("数值异常");
			}
		}else
		if(sex==2){
			if(num<20){
				vojo.setUnscramble("您的体脂偏低，请合理膳食结构，增强锻炼。");
				vojo.setAbnormal(1);
				vojo.setState("偏低");
			}
			else if(num>=20&&num<=25){
				vojo.setUnscramble("您的体脂正常，请继续保持健康的生活习惯。");
				vojo.setAbnormal(0);
				vojo.setState("正常");
			}
			else if(num>25){
				vojo.setUnscramble("您的体脂偏高，当身体每天摄入的热量小于消耗的热量，就会瘦下来了。你需要主动消耗体内的脂肪。饮食方面，要减少高油脂食物，多吃高纤维蔬菜。运动你可以选择跳绳、跳舞、爬山等运动。例如如果每天慢跑30分钟，一周你就可能减掉1公斤。结合饮食，一个月也可能降低2%-3%的体脂率。");
				vojo.setAbnormal(1);
				vojo.setState("偏高");
			}else{
				vojo.setUnscramble("当前数值异常");
				vojo.setAbnormal(1);
				vojo.setState("数值异常");
			}
		}else{
			vojo.setUnscramble("当前数值异常");
			vojo.setAbnormal(1);
			vojo.setState("数值异常");
		}
		return vojo;
	}
	
	
	/**
	 * 血脂四项  总胆固醇（TC）、甘油三酯（TG）、高密度脂蛋白胆固醇（HDL-C）、低密度脂蛋白胆固醇（LDL-C）
	 */
	public MonitoringDataStandardVOJO  bloodFat(double tc,double tg,double hdl,double ldl){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(tc));
		vojo.setParameter2(String.valueOf(tg));
		vojo.setParameter3(String.valueOf(hdl));
		vojo.setParameter4(String.valueOf(ldl));
		vojo.setTypeName("血脂四项");
		if(tc<5.18&&tg<1.70&&hdl>=1.04&ldl<3.37){
			vojo.setUnscramble("血脂含量可以反映体内脂类代谢的情况，想要保持血脂正常值，要限制高胆固醇食物的过多摄入，饮食结构应合理调配，其比例为蛋白质15%，脂肪20%，碳水化合物（糖类）为65%，还要补充优质蛋白质，多吃新鲜蔬菜并进食适当的水果。");
			vojo.setAbnormal(0);
			vojo.setState("正常");
		}else{
			vojo.setUnscramble("评估肾脏有没有问题，仅通过血液生化查肾功能三项是不够的。肾功能三项出现异常，可以由医生根据具体情况，进一步选做其他相关检查，最后由医生结合临床资料和其他检查结果综合分析，得出客观结论。");
			vojo.setAbnormal(1);
			vojo.setState("异常");
		}
		
		return vojo;
	}
	
	
	/**
	 * 肾功能 num1 血肌酐 num2 血尿素氮 num3 血尿酸 sex 1为男2为女
	 */
	public MonitoringDataStandardVOJO  renalFunction(double num1,double num2,double num3,int sex){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(num1));
		vojo.setParameter2(String.valueOf(num2));
		vojo.setParameter3(String.valueOf(num3));
		vojo.setTypeName("肾功能 ");
		if(sex==1){
			if(num1>54&&num1<106&&num2>2.9&&num2<7.5&&num3>210&&num3<420){
				vojo.setUnscramble("一般这种情况下，如果没有症状就可以不用担心肾脏疾病。如果伴有具体症状，建议到医院再做其他方面的检查，让医生帮忙检查诊断一下，再给予相应的治疗。");
				vojo.setAbnormal(0);
				vojo.setState("正常");
			}else{
				vojo.setUnscramble("评估肾脏有没有问题，仅通过血液生化查肾功能三项是不够的。肾功能三项出现异常，可以由医生根据具体情况，进一步选做其他相关检查，最后由医生结合临床资料和其他检查结果综合分析，得出客观结论。");
				vojo.setAbnormal(1);
				vojo.setState("异常");
			}
		}else if(sex==2){
			if(num1>44&&num1<97&&num2>2.9&&num2<7.5&&num3>150&&num3<360){
				vojo.setUnscramble("一般这种情况下，如果没有症状就可以不用担心肾脏疾病。如果伴有具体症状，建议到医院再做其他方面的检查，让医生帮忙检查诊断一下，再给予相应的治疗。");
				vojo.setAbnormal(0);
				vojo.setState("正常");
			}else{
				vojo.setUnscramble("评估肾脏有没有问题，仅通过血液生化查肾功能三项是不够的。肾功三项能出现异常，可以由医生根据具体情况，进一步选做其他相关检查，最后由医生结合临床资料和其他检查结果综合分析，得出客观结论。");
				vojo.setAbnormal(1);
				vojo.setState("异常");
			}
		}else{
			vojo.setUnscramble("当前数值异常");
			vojo.setAbnormal(1);
			vojo.setState("数值异常");
		}
		return vojo;
	}


	
	/**
	 * 肝功能 num1 谷丙转氨酶 num2 谷草转氨酶 num3 血清白蛋白 num4 血清球蛋白
	 */
	public MonitoringDataStandardVOJO  liverFunction(double num1,double num2,double num3,double num4){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(num1));
		vojo.setParameter2(String.valueOf(num2));
		vojo.setParameter3(String.valueOf(num3));
		vojo.setParameter4(String.valueOf(num4));
		vojo.setTypeName("肝功能 ");
		if(num1>0&&num1<40&&num2>0&&num2<40&&num3>35&&num3<51&&num4>20&&num4<30){
			vojo.setUnscramble("某项或多项指标偏高/偏低，都不能断定是某种疾病所致，无须过于担心和紧张。通常需要做进一步的检查，通过对各项检查结果以及身体症状表现综合分析，才能评估病情，找出肝功能指标偏高的真正原因，对症用药治疗。");
			vojo.setAbnormal(0);
			vojo.setState("正常");
		}else{
			vojo.setUnscramble("重视肝脏的保健调养，要保持心情舒畅，养成良性的生活习惯，远离酒和烟，尽量不要乱服用激素类的药物，乱用药物会加重肝脏的负担。");
			vojo.setAbnormal(1);
			vojo.setState("异常");
		}
		return vojo;
	}

	/**
	 *动脉硬化
	 */
	public MonitoringDataStandardVOJO artery(Integer avi,Integer api,int ageByBirth){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(avi));
		vojo.setParameter2(String.valueOf(api));
		int aviStatus = 0;
		int apiStatus = 0;
		int avizhibiao = 0;
		int apizhibiao = 0;
		if (null != avi && null != api) {
			if (ageByBirth <= 23) {
				avizhibiao = 15;
			} else if (ageByBirth >= 24 && ageByBirth <= 26) {
				avizhibiao = 16;
			} else if (ageByBirth >= 27 && ageByBirth <= 28) {
				avizhibiao = 17;
			} else if (ageByBirth >= 29 && ageByBirth <= 35) {
				avizhibiao = 18;
			} else if (ageByBirth >= 36 && ageByBirth <= 37) {
				avizhibiao = 19;
			} else if (ageByBirth >= 38 && ageByBirth <= 40) {
				avizhibiao = 20;
			} else if (ageByBirth >= 41 && ageByBirth <= 43) {
				avizhibiao = 21;
			} else if (ageByBirth >= 44 && ageByBirth <= 46) {
				avizhibiao = 22;
			} else if (ageByBirth >= 47 && ageByBirth <= 49) {
				avizhibiao = 23;
			} else if (ageByBirth >= 50 && ageByBirth <= 52) {
				avizhibiao = 24;
			} else if (ageByBirth >= 53 && ageByBirth <= 54) {
				avizhibiao = 25;
			} else if (ageByBirth >= 55 && ageByBirth <= 57) {
				avizhibiao = 26;
			} else if (ageByBirth >= 58 && ageByBirth <= 59) {
				avizhibiao = 27;
			} else if (ageByBirth >= 60 && ageByBirth <= 61) {
				avizhibiao = 28;
			} else if (ageByBirth >= 62 && ageByBirth <= 63) {
				avizhibiao = 29;
			} else if (ageByBirth >= 64 && ageByBirth <= 65) {
				avizhibiao = 30;
			} else if (ageByBirth >= 66 && ageByBirth <= 67) {
				avizhibiao = 31;
			} else if (ageByBirth >= 68 && ageByBirth <= 69) {
				avizhibiao = 32;
			} else if (ageByBirth >= 70 && ageByBirth <= 72) {
				avizhibiao = 33;
			} else if (ageByBirth == 73) {
				avizhibiao = 34;
			} else if (ageByBirth >= 74 && ageByBirth <= 75) {
				avizhibiao = 35;
			} else if (ageByBirth == 76) {
				avizhibiao = 36;
			} else if (ageByBirth >= 77 && ageByBirth <= 78) {
				avizhibiao = 37;
			} else if (ageByBirth == 79) {
				avizhibiao = 38;
			} else if (ageByBirth > 80) {
				avizhibiao = 39;
			}

			if (avi > avizhibiao && avi < (avizhibiao * 0.2)) {
				aviStatus = 1;
			}
			if (avi >= (avizhibiao * 0.2) && avi <= (avizhibiao * 0.5)) {
				aviStatus = 2;
			}
			if (avi > (avizhibiao * 0.5)) {
				aviStatus = 3;
			}
			if (ageByBirth <= 26) {
				apizhibiao = 22;
			} else if (ageByBirth >= 27 && ageByBirth <= 32) {
				apizhibiao = 23;
			} else if (ageByBirth >= 33 && ageByBirth <= 37) {
				apizhibiao = 24;
			} else if (ageByBirth >= 38 && ageByBirth <= 43) {
				apizhibiao = 25;
			} else if (ageByBirth >= 44 && ageByBirth <= 47) {
				apizhibiao = 26;
			} else if (ageByBirth >= 48 && ageByBirth <= 54) {
				apizhibiao = 27;
			} else if (ageByBirth >= 55 && ageByBirth <= 58) {
				apizhibiao = 28;
			} else if (ageByBirth >= 59 && ageByBirth <= 62) {
				apizhibiao = 29;
			} else if (ageByBirth >= 63 && ageByBirth <= 66) {
				apizhibiao = 30;
			} else if (ageByBirth >= 67 && ageByBirth <= 71) {
				apizhibiao = 31;
			} else if (ageByBirth >= 72 && ageByBirth <= 77) {
				apizhibiao = 32;
			} else if (ageByBirth >= 78 && ageByBirth <= 79) {
				apizhibiao = 33;
			} else if (ageByBirth >= 80) {
				apizhibiao = 33;
			}
			if (api > apizhibiao && avi < (apizhibiao * 0.2)) {
				apiStatus = 1;
			}
			if (api >= (apizhibiao * 0.2) && api <= (apizhibiao * 0.5)) {
				apiStatus = 2;
			}
			if (api > (apizhibiao * 0.5)) {
				apiStatus = 3;
			}


			if(aviStatus>=apiStatus){
				if(0==aviStatus){
					vojo.setUnscramble("动脉硬化是是随着年龄增长而出现的血管疾病，要注意防治：戒烟；防止三高，高血压、高血脂、高血糖；控制体重；加强锻炼；进而起到预防动脉硬化的发生。");
					vojo.setAbnormal(0);
					vojo.setState("正常");
				}
				if(1==aviStatus){
					vojo.setUnscramble("做心血管方面的检查（如超声波、心电图等）来进一步了解您的健康状态，同时采取多做户外运动，戒烟酒、多食低脂肪低糖高维生素高纤维食品，控制体重等措施。");
					vojo.setAbnormal(1);
					vojo.setState("轻度偏高");
				}
				if(2==aviStatus){
					vojo.setUnscramble("进行心血管系统全面检查，并在医生的指导下制定适宜的治疗方案。");
					vojo.setAbnormal(1);
					vojo.setState("中度偏高");
				}
				if(3==aviStatus){
					vojo.setUnscramble("一般提示有血管系统可查见的器官或组织病变，需及时就医。");
					vojo.setAbnormal(1);
					vojo.setState("重度偏高");
				}
			}else{
				if(0==apiStatus){
					vojo.setUnscramble("动脉硬化是是随着年龄增长而出现的血管疾病，要注意防治：戒烟；防止三高，高血压、高血脂、高血糖；控制体重；加强锻炼；进而起到预防动脉硬化的发生。");
					vojo.setAbnormal(0);
					vojo.setState("正常");
				}
				if(1==apiStatus){
					vojo.setUnscramble("做心血管方面的检查（如超声波、心电图等）来进一步了解您的健康状态，同时采取多做户外运动，戒烟酒、多食低脂肪低糖高维生素高纤维食品，控制体重等措施。");
					vojo.setAbnormal(1);
					vojo.setState("轻度偏高");
				}
				if(2==apiStatus){
					vojo.setUnscramble("进行心血管系统全面检查，并在医生的指导下制定适宜的治疗方案。");
					vojo.setAbnormal(1);
					vojo.setState("中度偏高");
				}
				if(3==apiStatus){

					vojo.setUnscramble("一般提示有血管系统可查见的器官或组织病变，需及时就医。");
					vojo.setAbnormal(1);
					vojo.setState("重度偏高");
				}
			}
		}
		return  vojo;
	}
	/**
	 * 尿检 尿检红细胞：num1 尿蛋白：num2 酮体：num3
	 */
	public MonitoringDataStandardVOJO  urinalysis(String num1,String num2,String num3){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(num1);
		vojo.setParameter2(num2);
		vojo.setParameter3(num3);
		vojo.setTypeName("尿检");
		if("-".equals(num1)&&"-".equals(num2)&&"-".equals(num3)){
			vojo.setUnscramble("您的尿检正常，请继续保持健康的生活习惯。");
			vojo.setAbnormal(0);
			vojo.setState("正常");
		}else{
			vojo.setUnscramble("可能发生泌尿系统感染，结石，肾脏功能异常，可以根据医生建议进一步进行筛查!");
			vojo.setAbnormal(1);
			vojo.setState("异常");
		}
		return vojo;
	}

	/**
	 * 血红蛋白  sex性别   1男  2女
	 * 过低，仪器显示“HI”；过高，仪器显示“Lo”
	 */
	public MonitoringDataStandardVOJO hemoglobin(double num,int sex){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(num));
		vojo.setTypeName("血红蛋白");
		if(sex==1){
			if(num<13){
				vojo.setUnscramble("您的血红蛋白值偏低。");
				vojo.setAbnormal(1);
				vojo.setState("偏低");
			}
			else if(num>=13&&num<=18){
				vojo.setUnscramble("您的血红蛋白值正常，请继续保持健康的生活习惯。");
				vojo.setAbnormal(0);
				vojo.setState("正常");
			}
			else if(num>18){
				vojo.setUnscramble("您的血红蛋白值偏高。");
				vojo.setAbnormal(1);
				vojo.setState("偏高");
			}else{
				vojo.setUnscramble("当前数值异常");
				vojo.setAbnormal(1);
				vojo.setState("数值异常");
			}
		}else
		if(sex==2){
			if(num<12){
				vojo.setUnscramble("您的血红蛋白值偏低。");
				vojo.setAbnormal(1);
				vojo.setState("偏低");
			}
			else if(num>=12&&num<=16){
				vojo.setUnscramble("您的血红蛋白值正常，请继续保持健康的生活习惯。");
				vojo.setAbnormal(0);
				vojo.setState("正常");
			}
			else if(num>16){
				vojo.setUnscramble("您的血红蛋白值偏高。");
				vojo.setAbnormal(1);
				vojo.setState("偏高");
			}else{
				vojo.setUnscramble("当前数值异常");
				vojo.setAbnormal(1);
				vojo.setState("数值异常");
			}
		}else{
			vojo.setUnscramble("当前数值异常");
			vojo.setAbnormal(1);
			vojo.setState("数值异常");
		}
		return vojo;
	}
	
	
	/**
	 * 其他指标 num1 尿微量蛋白 num2 糖化血红蛋白 num3 同型半胱氨酸
	 */
	public MonitoringDataStandardVOJO  otherIndicators(double num1,double num2,double num3){
		MonitoringDataStandardVOJO vojo = new MonitoringDataStandardVOJO();
		vojo.setParameter1(String.valueOf(num1));
		vojo.setParameter2(String.valueOf(num2));
		vojo.setParameter3(String.valueOf(num3));
		vojo.setTypeName("其他指标");
		if(num1>=0.49&&num1<=2.05&&num2>=4&&num2<=6&&num3>5&&num3<15){
			vojo.setUnscramble("您的检测正常，请继续保持健康的生活习惯。");
			vojo.setAbnormal(0);
			vojo.setState("正常");
		}
		else{
			vojo.setUnscramble("尿微量蛋白异常表示泌尿系统可能存在感染。糖化血红蛋白异常表示三到六个月血糖控制情况。同型半胱氨酸异常表示患心脑血管疾病增加。");
			vojo.setAbnormal(1);
			vojo.setState("异常");
		}
		return vojo;
	}
	
	
	
}
