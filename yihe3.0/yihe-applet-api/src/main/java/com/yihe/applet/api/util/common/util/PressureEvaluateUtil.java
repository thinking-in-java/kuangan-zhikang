package com.yihe.applet.api.util.common.util;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2019年8月2日 上午10:32:23 
 *@version 2.0 
 */
public class PressureEvaluateUtil {
	
	
	/**
	 * @Title: getPressureInfo  
	 * @Description: 计算指标距离 
	 * @param @param l_score
	 * @param @return    设定文件  
	 * @return String    返回类型  
	 * @throws
	 */
	public static String getPressureInfo(int l_score)
	{
		String flag = null;
		if (l_score < 20){
			flag="5%";
		}else if (l_score >= 20 && l_score < 27){
			flag="25%";
	    }else if (l_score >= 27 && l_score < 30){
	    	flag="45%";
	    }else if (l_score >= 30 && l_score < 37){
	    	flag="65%";
	    }else{
	    	flag="88%";
	    }	
		return flag;
	}
	
	/**
	 * @Title: getPstriInfo  
	 * @Description: 计算指标距离 
	 * @param @param l_score
	 * @param @return    设定文件  
	 * @return String    返回类型  
	 * @throws
	 */
	public static String getPstriInfo(int score)
	{
		String flag = null;
		if (score < 43){
			flag="15%";
		}else if (score >= 43 && score <= 65){
			flag="48%";
	    }else if(score > 65){
	    	flag="80%";
	    }	
		return flag;
	}
	
	/**
	 * @Title: getHealthInfo  
	 * @Description: 计算指标距离 
	 * @param @param l_score
	 * @param @return    设定文件  
	 * @return String    返回类型  
	 * @throws
	 */
	public static String getHealthInfo(int gender, int score)
	{
		String flag = null;
		switch (gender)
		{
		case 2: // '\002'
			if (score < 25){
				flag="15%";
			}else if (score >= 25 && score <= 45){
				flag="48%";
		    }else if(score > 45){
		    	flag="80%";
		    }	
				
			break;

		default:
			if (score < 10){
				flag="15%";
			}else if (score >= 10 && score <= 65){
				flag="48%";
		    }else if(score > 65){
		    	flag="80%";
		    }	
			break;
		}
		return flag;
	}
	
	/**
	 * 
	 * @Title: getPressureNote  
	 * @Description: 获取评估评价
	 * @param @param l_score
	 * @param @param s_score
	 * @param @return    设定文件  
	 * @return String    返回类型  
	 * @throws
	 */
	public static String getPressureNote(int l_score, int s_score)
	{
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_flag = new StringBuffer();
		if (l_score < 7)
			sb_flag.append("<div class=\"bg-zitneir\"><p>您此次L量表评分为").append(l_score).append("分，说明您的回答能真实地反映您的情况，该问卷结果有效。您能够诚恳地面对对自己不利的评价，为人真诚，不好掩饰。 </p></div>");
		else
			sb_flag.append("<div class=\"bg-zitneir\"><p>您此次L量表评分为").append(l_score).append("分，说明您回答某些题时不够诚实，该问卷结果无效。您喜欢对自己不利的评价掩饰，为人容易表现出虚伪、圆滑，也可能由于自身定位不准确，自我认识不清或理解能力不足造成的。 </p></div>");
		if (s_score < 20)
		{
			sb.append("<div class=\"jianju\">");
			sb.append("<p>B型：您具有较典型的B型性格特征： <br />");
			sb.append("您性情随和，顺从，随遇而安，不喜争斗；生活方式悠闲自在，对成败得失看得较淡，不太在意成就的大小，有时缺乏抱负和主见；时间观念不强，对于布置给自己的任务能否完成，从不着急；情绪不易激动，容易与人相处，人际关系较好。 </p>");
			sb.append(sb_flag);
			sb.append("<div class=\"aui-hr2\" style=\"margin-top: 0.5rem;\"></div></div>");
			sb.append(" <div class=\"jianju\"><p>建议</p>");
			sb.append("<p>您的工作生活悠闲自得，但现代社会节奏日益加快，您需要提速以便更有效率地学习和工作。 </p>");
			sb.append("<p>1、制定一个符合自己实际能力并可实现的目标； </p>");
			sb.append("<p>2、对工作进行有效的规划，制定出完成的时间；如果您要完成的任务有很多，首先区分他们的先后、主次次序，完成一件工作后再做第二件； </p>");
			sb.append("<p>3、勇于弘扬自己的优点：一些B型性格的人认为在别人面前承认自己的优点非常不自在。其实不然，正是我们自身的优点使我们迎来一个又一个机会。 </p>");
			sb.append("<p>4、敢于交流和发表自己的看法，对待不公平的人与事，要适当的宣泄自己的情感。</p>");
			sb.append("</div>");
		} else
		if (s_score < 27)
		{
			sb.append("<div class=\"jianju\">");
			sb.append("<p>中间型<br />");
			sb.append("现实中大多数人可能都跟您一样，居于典型的A型和B型性格之间。您不像典型的A型性格那样追求成就，好胜心切；您也不像典型B型那样内向、逆来顺受。您有一定的时间观念和竞争意识，总之您同时具有A型和B型性格特征，得分越高，A型倾向（进取、好胜、冲动）越明显；得分越低，B型倾向（悠然、顺从、沉稳）越明显。 </p>");
			sb.append(sb_flag);
			sb.append("<div class=\"aui-hr2\" style=\"margin-top: 0.5rem;\"></div></div>");
			sb.append("<div class=\"jianju\"><p>建议</p>");
			sb.append("<p>根据您的性格是更多的倾向A型还是B型，有不同的改善方法。 <br />");
			sb.append("如果您更多地倾向A型，那么请您减缓工作生活的节奏，为每件事情制定好计划，不追赶时间；学会承认自己的不足，接受现实；学会微笑和夸奖别人；为自己留出一定的休闲时间，培养自己的体育、音乐、文艺方面的业余爱好，享受生活。 <br />");
			sb.append("如果您更多的倾向B型，那么您可能很会享受生活，人际关系较好。您也许可以尝试为自己制定更高的目标，确定每件事的完成时间，以获得更大的成就感。 </p>");
			sb.append("</div>");
		} else
		if (s_score < 30)
		{
			sb.append("<div class=\"jianju\">");
			sb.append("<p>中间型：<br />");
			sb.append("现实中大多数人可能都跟您一样，居于典型的A型和B型性格之间。您不像典型的A型性格那样追求成就，好胜心切；您也不像典型B型那样内向、逆来顺受。您有一定的时间观念和竞争意识，总之您同时具有A型和B型性格特征，得分越高，A型倾向（进取、好胜、冲动）越明显；得分越低，B型倾向（悠然、顺从、沉稳）越明显。 </p>");
			sb.append(sb_flag);
			sb.append("<div class=\"aui-hr2\" style=\"margin-top: 0.5rem;\"></div></div>");
			sb.append("<div class=\"jianju\"><p>建议</p>");
			sb.append("<p>根据您的性格是更多的倾向A型还是B型，有不同的改善方法。 <br />");
			sb.append("如果您更多地倾向A型，那么请您减缓工作生活的节奏，为每件事情制定好计划，不追赶时间；学会承认自己的不足，接受现实；学会微笑和夸奖别人；为自己留出一定的休闲时间，培养自己的体育、音乐、文艺方面的业余爱好，享受生活。 <br />");
			sb.append("如果您更多的倾向B型，那么您可能很会享受生活，人际关系较好。您也许可以尝试为自己制定更高的目标，确定每件事的完成时间，以获得更大的成就感。 </p>");
			sb.append("</div>");
		} else
		if (s_score < 37)
		{
			sb.append("<div class=\"jianju\">");
			sb.append("<p>中间型：<br />");
			sb.append("现实中大多数人可能都跟您一样，居于典型的A型和B型性格之间。您不像典型的A型性格那样追求成就，好胜心切；您也不像典型B型那样内向、逆来顺受。您有一定的时间观念和竞争意识，总之您同时具有A型和B型性格特征，得分越高，A型倾向（进取、好胜、冲动）越明显；得分越低，B型倾向（悠然、顺从、沉稳）越明显。 </p>");
			sb.append(sb_flag);
			sb.append("<div class=\"aui-hr2\" style=\"margin-top: 0.5rem;\"></div></div>");
			sb.append("<div class=\"jianju\"><p>建议</p>");
			sb.append("<p>根据您的性格是更多的倾向A型还是B型，有不同的改善方法。 <br />");
			sb.append("如果您更多地倾向A型，那么请您减缓工作生活的节奏，为每件事情制定好计划，不追赶时间；学会承认自己的不足，接受现实；学会微笑和夸奖别人；为自己留出一定的休闲时间，培养自己的体育、音乐、文艺方面的业余爱好，享受生活。 <br />");
			sb.append("如果您更多的倾向B型，那么您可能很会享受生活，人际关系较好。您也许可以尝试为自己制定更高的目标，确定每件事的完成时间，以获得更大的成就感。 </p>");
			sb.append("</div>");
		} else
		{
			sb.append("<div class=\"jianju\">");
			sb.append("<p>A型：您具有较典型的A型性格特征： <br />");
			sb.append("您好胜心强，追求成就，有很强的事业心，做工作总要尽善尽美，有很强的竞争欲；时间观念强，总觉得时间紧张、不够用，做事匆忙、急躁，不干完事不踏实；反应快而强烈，行动迅速；情绪易波动，易被激怒，对人怀有戒心，人际关系比较紧张。 </p>");
			sb.append(sb_flag);
			sb.append("<div class=\"aui-hr2\" style=\"margin-top: 0.5rem;\"></div></div>");
			sb.append("<div class=\"jianju\"><p>建议</p>");
			sb.append("<p>您的生活充满激情但行事匆匆。科学家们发现A型性格与心脏病关系密切，您需要采 <br />");
			sb.append("取一些措施让自己慢下来。 </p>");
			sb.append("<p>1、 每日记录自己的匆忙事例，检查出现匆忙的原因，每周小结一次，及时定制解决方案。 </p>");
			sb.append("<p>2、放弃自己要做一个超人的意图，勇于接受自己的缺点和不足，相信他人也能做好工作。 </p>");
			sb.append("<p>3、尽量淡化荣誉及成就感，注意学习生活与工作的和谐及生活质量。 </p>");
			sb.append("<p>4、放弃同时思考多个问题或完成几件事的习惯，在时间安排上井然有序，留有余地，踏实稳健地干每一件事情。 </p>");
			sb.append("<p>5、主动控制调节自己的情绪，避免争论，冲突，以幽默的态度化解生活的挫折。</p>");
			sb.append("<p>6、善于倾听他人的意见，不要随意打断别人的讲话。学会对所有认识的人微笑。 </p>");
			sb.append("<p>7、有目的地培养耐心和悠闲感。每天留给自己一定的时间，散散步，听听音乐，放松一下。</p>");
			sb.append("<p>8、多交知心朋友，建立归宿感，安全感，多与朋友交流自己内心的真切感受。</p>");
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	
	
	public static String getPstriNote(int score)
	{
		StringBuffer sb = new StringBuffer();
		if (score < 43)
		{
			sb.append("<div class=\"jianju\">");
			sb.append("<p>您需要更主动些，在工作、社交、娱乐等活动上多寻求些刺激，增加自己的压力。您也许可以尝试制定更高的目标，留给自己更多的工作，规定更紧的期限。事实上，适当的压力有助于您保持旺盛的精力和敏捷的思维。做放松练习对您没有用，它只会让您更加放松。</p>");
			sb.append("<div class=\"jianju\"><p>建议</p>");
			sb.append("<p>1、健康饮食，平衡膳食能够有效稳定情绪。</p>");
			sb.append("<p>2、适当运动，如慢跑、散步、游泳等。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。 </p>");
			sb.append("<p>3、睡眠充足，尽量固定睡觉和起床的时间。 </p>");
			sb.append("<p>4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上而不是头疼的问题上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。 </p>");
			sb.append("<p>5、学会坚守底线，不要接受不必要的负担。 </p>");
			sb.append("</div>");
		} else
		if (score <= 65)
		{
			sb.append("<div class=\"jianju\">");
			sb.append("<p>您可以适度寻求刺激，比如参与文娱活动，增加生活的乐趣。您也许可以多接触新鲜的人和事，并表达出自己的感受，是惊喜、快乐还是厌恶。您能够感受到压力，但是您常常把它忽略掉，您需要增加压力反应的强度，适度的压力反应会让您感受到生活的多姿多彩。</p>");
			sb.append("<div class=\"jianju\"><p>建议</p>");
			sb.append("<p>1、健康饮食，平衡膳食能够有效稳定情绪。 </p>");
			sb.append("<p>2、适当运动，如慢跑、散步、游泳等。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。 </p>");
			sb.append("<p>3、睡眠充足，尽量固定睡觉和起床的时间。 </p>");
			sb.append("<p>4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上而不是头疼的问题上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。</p>");
			sb.append("<p>5、学会坚守底线，不要接受不必要的负担。 </p>");
			sb.append("</div>");
		} else
		{
			sb.append("<div class=\"jianju\">");
			sb.append("<p>您需要仔细反省自己对压力做出的反应，并学习在压力出现时，控制自己的肌肉紧张，以消除生理激活反应。以下几点将有助于您减轻压力反应。 </p>");
			sb.append("<div class=\"jianju\"><p>建议</p>");
			sb.append("<p>1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，以便在日后尽量避免这些事件的发生，或是有所针对的各个击破。 </p>");
			sb.append("<p>2、了解自己最易受制于压力的时刻并在这一时刻到来之前做好准备。你最易受到影响的时候是在早晨？或是周一？抑或是冬季？ </p>");
			sb.append("<p>3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应。在同等压力下，做运动的人比不做运动的人血压要低，心率要慢。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。 </p>");
			sb.append("<p>4、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉能使您心情舒畅。有条件的话，不妨出去旅行一段时间，让自己的心灵休息一会儿。 </p>");
			sb.append("<p>5、深呼吸。将注意力转移到腹部，在用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中。呼气时想象空气反向流动。 </p>");
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	
	public static String getHealthNote(int gender, int score)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"jianju\">");
		switch (gender)
		{
		case 2: // '\002'
			if (score < 25)
			{
				sb.append("<p>特别声明：此量表仅用于自测，不具有诊断价值。请确定您是否有文字理解错误、故意 犯错、没有认真作答等情况。若无，您需要寻求专业人士的帮助，请咨询心理医生。</p>");
				break;
			}
			if (score <= 45)
			{
				sb.append("<p>建议：");
				sb.append("您可以尝试以下建议，也许有助于您恢复平稳的心境。<p>");
				sb.append("<p>1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，有所针对的各个击破。</p>");
				sb.append("<p>2、健康饮食。平衡膳食能够有效稳定情绪。</p>");
				sb.append("<p>3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应，增加血液循环，对改善情绪有好处。</p>");
				sb.append("<p>4、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。</p>");
				sb.append("<p>5、睡眠充足。压力会干扰人们放松的状态，影响睡眠质量，从而导致疲劳和问题处理能力下降。为了能够保证好的睡眠，应该尽量固定睡觉和起床的时间。</p>");
				sb.append("<p>6、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。</p>");
				sb.append("<p>7、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好的释放压力并塑造积极的人生态度。</p>");
				sb.append("<p>8、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉不但可增强人们的友谊和信任，更能使您心情舒畅。</p>");
				sb.append("<p>9、学会接受现实。现实中没有人能事事如意，对于一些不能改变的事实，慢慢地试着去接受它，改变一下自己看待问题的角度和心态，也许心情会好一些。</p>");
			} else
			{
				sb.append("<p>建议：");
				sb.append("下面是一些常见的提高压力应对能力的技巧，希望对您有所帮助。</p>");
				sb.append("<p>1、健康饮食，平衡膳食能够有效稳定情绪。</p>");
				sb.append("<p>2、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应。在同等压力下，做运动的人比不做运动的人血压要低，心率要慢。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。</p>");
				sb.append("<p>3、睡眠充足，尽量固定睡觉和起床的时间。</p>");
				sb.append("<p>4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。</p>");
				sb.append("<p>5、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好地释放压力并塑造积极的人生态度。</p>");
				sb.append("<p>6、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。</p>");
				sb.append("<p>7、回忆快乐的事，给自己正性的暗示，将有助于您积极向上地生活。 </p>");
			}
			break;

		default:
			if (score < 10)
			{
				sb.append("<p>特别声明：此量表仅用于自测，不具有诊断价值。请确定您是否有文字理解错误、故意 犯错、没有认真作答等情况。若无，您需要寻求专业人士的帮助，请咨询心理医生。</p>");
				break;
			}
			if (score <= 65)
			{
				sb.append("<p>建议：");
				sb.append("您可以尝试以下建议，也许有助于您恢复平稳的心境。</p>");
				sb.append("<p>1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，有所针对的各个击破。</p>");
				sb.append("<p>2、健康饮食。平衡膳食能够有效稳定情绪。</p>");
				sb.append("<p>3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应，增加血液循环，对改善情绪有好处。</p>");
				sb.append("<p>4、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。</p>");
				sb.append("<p>5、睡眠充足。压力会干扰人们放松的状态，影响睡眠质量，从而导致疲劳和问题处理能力下降。为了能够保证好的睡眠，应该尽量固定睡觉和起床的时间。</p>");
				sb.append("<p>6、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。</p>");
				sb.append("<p>7、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好的释放压力并塑造积极的人生态度。</p>");
				sb.append("<p>8、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉不但可增强人们的友谊和信任，更能使您心情舒畅。</p>");
				sb.append("<p>9、学会接受现实。现实中没有人能事事如意，对于一些不能改变的事实，慢慢地试着去接受它，改变一下自己看待问题的角度和心态，也许心情会好一些。</p>");
			} else
			{
				sb.append("<p>建议：");
				sb.append("下面是一些常见的提高压力应对能力的技巧，希望对您有所帮助。</p>");
				sb.append("<p>1、健康饮食，平衡膳食能够有效稳定情绪。</p>");
				sb.append("<p>2、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应。在同等压力下，做运动的人比不做运动的人血压要低，心率要慢。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。</p>");
				sb.append("<p>3、睡眠充足，尽量固定睡觉和起床的时间。</p>");
				sb.append("<p>4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。</p>");
				sb.append("<p>5、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好的释放压力并塑造积极的人生态度。</p>");
				sb.append("<p>6、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。</p>");
				sb.append("<p>7、回忆快乐的事，给自己正性的暗示，将有助于您积极向上地生活。 </p>");
			}
			break;
		}
		sb.append("</div>");
		return sb.toString();
	}
	

}
