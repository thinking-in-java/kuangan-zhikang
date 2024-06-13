package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.mapper.YhHealthyPlanMapper;
import com.yihe.applet.api.mapper.YhPlanDetailMapper;
import com.yihe.applet.api.service.YhHealthyPlanService;
import com.yihe.system.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * YhHealthyPlanService实现 Created by dong on 2020/4/13.
 */
@Service
@Transactional
public class YhHealthyPlanServiceImpl implements YhHealthyPlanService {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthyPlanServiceImpl.class);

	@Autowired
	YhHealthyPlanMapper yhHealthyPlanMapper;
	@Autowired
	YhPlanDetailMapper yhPlanDetailMapper;
	/***
	 * curPage 当前页数 pageSize 页数大小
	 */
	@Override
	public List<YhHealthyPlanApp> yhhealthyplan(Integer curPage, Integer pageSize) {
		// TODO Auto-generated method stub
		curPage = (curPage - 1) * pageSize;
		List<YhHealthyPlanApp> yhhealthyplans = yhHealthyPlanMapper.yhhealthyplan(curPage, pageSize);
		return yhhealthyplans;
	}

	@Override
	public int selectyhhealthycount() {
		// TODO Auto-generated method stub
		return yhHealthyPlanMapper.selectyhhealthycount();
	}

	@Override
	public int yhhealthplanwatch(Integer id) {
		// TODO Auto-generated method stub
		return yhHealthyPlanMapper.yhhealthplanwatch(id);
	}

	@Override
	public YhHealthyPlanApp selectyhhealthyplanbyid(Integer id) {
		// TODO Auto-generated method stub
		return yhHealthyPlanMapper.selectyhhealthyplanbyid(id);
	}

	@Override
	public int addHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan) {
		// TODO Auto-generated method stub
		//添加个人计划
		//先查询maxunique在加一添加
		yhHealthyPlan isshenhe=yhHealthyPlanMapper.selectisshenhe(yhuserhealthyplan);
		YhJzServicePerson person=new YhJzServicePerson();
		int maxunique=yhHealthyPlanMapper.selectmaxunique();
		yhuserhealthyplan.setPlan_unique(maxunique+1);
		if(isshenhe.getPlan_isapply()==1){
			YhDiseaseAssessReport reports=new YhDiseaseAssessReport();
			reports.setUserCode(yhuserhealthyplan.getUserCode());
			String servicerecord="";
			if(yhuserhealthyplan.getPlanId()==1){
				reports.setDiseasid(3);
				servicerecord="高血压:";
				servicerecord+=yhHealthyPlanMapper.selectdisname(reports);
			}else if(yhuserhealthyplan.getPlanId()==2){
				reports.setDiseasid(4);
				servicerecord="糖尿病:";
				servicerecord+=yhHealthyPlanMapper.selectdisname(reports);
			}else if(yhuserhealthyplan.getPlanId()==3){
				reports.setDiseasid(11);
				servicerecord="BMI:";
				servicerecord+=yhHealthyPlanMapper.selectdisname2(reports);
			}else if(yhuserhealthyplan.getPlanId()==4){
				reports.setDiseasid(6);
				servicerecord="高血脂:";
				servicerecord+=yhHealthyPlanMapper.selectdisname(reports);
			}
			//先查询这个人有没有在总表有
			int count=yhHealthyPlanMapper.selecttotoservice(yhuserhealthyplan.getUserCode());
			if(count==0){
				yhHealthyPlanMapper.inserttotoservice(yhuserhealthyplan.getUserCode());
			}
			yhuserhealthyplan.setPlanState(4);
			person.setUserCode(yhuserhealthyplan.getUserCode());
			person.setServiceState(0);
			person.setServiceWaitingTime(getFetureDate(7));
			person.setServiceContent("筛选出适合参与健康计划管理人群，受理代表同意，关闭代表拒绝");
			person.setServiceType(27);
			person.setServiceRecord(servicerecord);
			person.setIsrede(1);
			person.setReturnvisit(1);
			person.setPhone("10000");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			person.setInsertTime(df.format(new Date()));
			person.setServiceSubtitle(isshenhe.getPlan_name());
		}
		if(isshenhe.getPlan_isapply()==1) {
			yhHealthyPlanMapper.deleteselectperson(person);
			yhHealthyPlanMapper.insertSelectiveperson(person);

		}
		return yhHealthyPlanMapper.addHealthPlanDetail(yhuserhealthyplan);
	}

	@Override
	public int boolenaddHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan) {
		// TODO Auto-generated method stub
		return yhHealthyPlanMapper.boolenaddHealthPlanDetail(yhuserhealthyplan);
	}
	//已有健康计划添加
	@Override
	public int updateHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan) {
		// TODO Auto-generated method stub
		//查询现在的计划是否需要审核
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		yhHealthyPlan isshenhe=yhHealthyPlanMapper.selectisshenhe(yhuserhealthyplan);
		YhJzServicePerson person=new YhJzServicePerson();
		if(isshenhe.getPlan_isapply()==1){
			//先查询这个人有没有在总表有
			int count=yhHealthyPlanMapper.selecttotoservice(yhuserhealthyplan.getUserCode());
			if(count==0){
				yhHealthyPlanMapper.inserttotoservice(yhuserhealthyplan.getUserCode());
			}
			yhuserhealthyplan.setPlanState(4);
			person.setUserCode(yhuserhealthyplan.getUserCode());
			person.setServiceState(0);
			person.setServiceWaitingTime(getFetureDate(7));
			person.setServiceContent("筛选出适合参与健康计划管理人群，受理代表同意，关闭代表拒绝");
			person.setServiceType(27);
			person.setPhone("10000");
			YhDiseaseAssessReport reports=new YhDiseaseAssessReport();
			reports.setUserCode(yhuserhealthyplan.getUserCode());
			String servicerecord="";
			if(yhuserhealthyplan.getPlanId()==1){
				reports.setDiseasid(3);
				servicerecord="高血压:";
				 servicerecord+=yhHealthyPlanMapper.selectdisname(reports);
			}else if(yhuserhealthyplan.getPlanId()==2){
				reports.setDiseasid(4);
				servicerecord="糖尿病:";
				 servicerecord+=yhHealthyPlanMapper.selectdisname(reports);
			}else if(yhuserhealthyplan.getPlanId()==3){
				reports.setDiseasid(11);
				servicerecord="BMI:";
				servicerecord+=yhHealthyPlanMapper.selectdisname2(reports);
			}else if(yhuserhealthyplan.getPlanId()==4){
				reports.setDiseasid(6);
				servicerecord="高血脂:";
				servicerecord+=yhHealthyPlanMapper.selectdisname(reports);
			}

			person.setServiceRecord(servicerecord);
			person.setIsrede(1);
			person.setReturnvisit(1);
			person.setInsertTime(df.format(new Date()));
			person.setServiceSubtitle(isshenhe.getPlan_name());
		}

		YhUserHealthyPlanApp plan=yhHealthyPlanMapper.selectnowday(yhuserhealthyplan);
		// 先改变以前计划的状态，已开始的改为取消状态开始加入新的计划
		//如果是第0天并且还未开始
		if(plan!=null){
		if(plan.getPlanDay()==0&&(plan.getPlanState()==0 ||plan.getPlanState()==4 )) {
			yhuserhealthyplan.setId(plan.getId());
			person.setAge(yhuserhealthyplan.getId());
			//添加审核提醒(需把上一个人未处理的审核停掉)
			if(isshenhe.getPlan_isapply()==1) {
				yhHealthyPlanMapper.deleteselectperson(person);
				yhHealthyPlanMapper.insertSelectiveperson(person);
			}
			return yhHealthyPlanMapper.updateaddplan(yhuserhealthyplan);
		}else {
			yhHealthyPlanMapper.changehealthplan(yhuserhealthyplan);
			// 然后添加新的计划
			int maxunique=yhHealthyPlanMapper.selectmaxunique();
			yhuserhealthyplan.setPlan_unique(maxunique+1);
			int count=yhHealthyPlanMapper.addHealthPlanDetail(yhuserhealthyplan);
			person.setAge(yhuserhealthyplan.getId());
			//添加审核提醒
			if(isshenhe.getPlan_isapply()==1) {
				yhHealthyPlanMapper.deleteselectperson(person);
				yhHealthyPlanMapper.insertSelectiveperson(person);
			}
			return count;
		}
		}else{
			int maxunique=yhHealthyPlanMapper.selectmaxunique();
			yhuserhealthyplan.setPlan_unique(maxunique+1);
			int count=yhHealthyPlanMapper.addHealthPlanDetail(yhuserhealthyplan);
			//添加审核提醒
			if(isshenhe.getPlan_isapply()==1) {
				yhHealthyPlanMapper.deleteselectperson(person);
				yhHealthyPlanMapper.insertSelectiveperson(person);
			}
			return  count;
		}

	}
	public  String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}
	//查询用户开始或者已经结束的计划
	@Override
	public List<YhUserHealthyPlanApp> selectyhuserhealthplanisbegin(YhUserHealthyPlanApp yhuserhealthyplan) {
		// TODO Auto-generated method stub
		//先查出做过哪些天数的任务，然后循环查询出每天任务的所有完成的任务
		//循环查找完成任务条数，并且循环相加设置
		List<YhUserHealthyPlanApp> yu=yhHealthyPlanMapper.selectyhuserhealthplanisbegin(yhuserhealthyplan);
		for (int i = 0; i < yu.size(); i++) {
			List<YhUserHealthyPlanApp> selectplancount=yhHealthyPlanMapper.selectallplancount(yu.get(i));
			int plancount=0;
			for (int j = 0; j < selectplancount.size(); j++) {
				plancount=plancount+selectplancount.get(j).getCourseseq();
			}
			yu.get(i).setPlan_complate(plancount);
		}
		return yu;
	}

	@Override
	public YhUserHealthyPlanApp selectalreadybegun(YhUserHealthyPlanApp yhuserhealthyplan) {
		// TODO Auto-generated method stub
		//查询用户当天记录总数
		YhUserHealthyPlanApp yhplan = null;
		yhplan=yhHealthyPlanMapper.selectalreadybegun(yhuserhealthyplan);
		List<YhUserHealthyPlanApp> yhplans=null;
		yhplans=yhHealthyPlanMapper.selectalreadybegunoneplan(yhuserhealthyplan);
		//再次查询当天的所有条数，然后相加条数个数
		//查询当天拥有哪些计划
		YhHealthyPlandays plandays = yhHealthyPlanMapper.selectplandays(yhplan);
		String planditalid = "";
		for (int i = 0; i < yhplans.size(); i++) {
			if(yhplans.get(i).getPlandetailId()!=null) {
				if(planditalid.equals("") || planditalid==null) {
					planditalid=yhplans.get(i).getPlandetailId();
				}else {
					planditalid=planditalid+";"+yhplans.get(i).getPlandetailId();
				}

			}

		}
		if (plandays != null) {
			String[] deplan = plandays.getPlandetailId().split(";");
			List<YhHealthyPlandetail> plandetails = new ArrayList<YhHealthyPlandetail>();
			//循环查找用户当天计划详情
			for (int i = 0; i < deplan.length; i++) {
				plandetails.add(yhHealthyPlanMapper.selectyhhealthplandetailbyid(Integer.valueOf(deplan[i])));

			}
			yhplan.setPlandetailId(planditalid);
			plandays.setYhhealthyplandetail(plandetails);
			yhplan.setYhhealthyplandays(plandays);
		}
		return yhplan;
	}
	//获取任务的详情
	@Override
	public YhHealthyPlandetail plandetail(Integer id) {
		// TODO Auto-generated method stub
		return yhHealthyPlanMapper.selectyhhealthplandetailbyid(id);
	}
	//完成一个任务后判断是否全部完成
	@Override
	public int tofinishoneajax(YhUserHealthyPlanApp yhUserhealthypaln) {
		int resize=1;
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		//获取该次任务的全部头像
		YhHealthyPlandetail yhhealthyplandetail=yhHealthyPlanMapper.selectyhhealthplandetailbyid(Integer.valueOf(yhUserhealthypaln.getPlandetailId()));
		// TODO Auto-generated method stub
		//获取用户今天需要完成的任务
		YhUserHealthyPlanApp plans = yhHealthyPlanMapper.selectalreadybegun(yhUserhealthypaln);
		YhHealthyPlandays plandays = yhHealthyPlanMapper.selectplandays(yhUserhealthypaln);
		String nowday=plans.getPlanStartdate();
		List<YhUserHealthyPlanApp> yhplans=null;
		yhplans=yhHealthyPlanMapper.selectalreadybegunoneplan(yhUserhealthypaln);
		Boolean isfinish=true;
		//判断这个任务是否完成
		for (int i = 0; i < yhplans.size(); i++) {
			if(yhplans.get(i).getPlandetailId()!=null || ("").equals(yhplans.get(i).getPlandetailId())) {
				String[] arr=yhplans.get(i).getPlandetailId().split(";");
				for (int j = 0; j <arr.length; j++) {
					if(arr[j].equals(yhUserhealthypaln.getPlandetailId())) {
						isfinish=false;
					}
				}
			}

		}
		if(isfinish) {
			//先添加积分
			int count= yhHealthyPlanMapper.selectalljifen(yhUserhealthypaln.getUserCode());
			if(count==0){
				yhHealthyPlanMapper.insertzong(yhUserhealthypaln.getUserCode());
			}else{
				yhHealthyPlanMapper.changezong(yhUserhealthypaln.getUserCode());
			}
			yhHealthyPlanMapper.changefenbiao(yhUserhealthypaln.getUserCode());
			//如果用户完成任务的时间还是今天的话，更改今天的数据，否则添加一条数据
			if(dfs.format(new Date()).equals(nowday.substring(0, 10))) {
				if (plans.getPlandetailId() != null) {
					//拼接完成的节数
					yhUserhealthypaln.setPlandetailId(plans.getPlandetailId() + ";" + yhUserhealthypaln.getPlandetailId());
				}
				if(plans.getPlan_choose()!=null&&yhUserhealthypaln.getPlan_choose()!=null) {
					yhUserhealthypaln.setPlan_choose(plans.getPlan_choose()+";"+yhUserhealthypaln.getPlan_choose());
				}
				int plancount=0;
				for (int i = 0; i < yhplans.size(); i++) {
					if(yhplans.get(i).getPlandetailId()!=null) {
						plancount=plancount+yhplans.get(i).getPlandetailId().split(";").length;
					}
				}
				YhHealthyPlanApp healthyplan=yhHealthyPlanMapper.selectonehealthplan(plans.getPlanId());
				//最后一个任务就设置截至的时间
				if(plancount==plandays.getPlandetailId().split(";").length-1) {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					yhUserhealthypaln.setPlanEnddate(df.format(new Date()));
					if(plans.getPlanDay()==healthyplan.getPlanNumber()) {
						resize=10000;
						yhUserhealthypaln.setPlanState(2);
						yhHealthyPlanMapper.changeplanstate(yhUserhealthypaln.getUserCode());
						yhPlanDetailMapper.upatefoodlogispaln(yhUserhealthypaln.getUserCode());
						yhPlanDetailMapper.isfinishi(yhUserhealthypaln.getUserCode());
					}else {
						//如果到了最后一天的话就改状态，否则添加一条明天要完成的数据
						YhUserHealthyPlanApp newplan =new YhUserHealthyPlanApp();
						newplan.setUserCode(plans.getUserCode());
						newplan.setCourseseq(0);
						Calendar cal=Calendar.getInstance();
						cal.add(Calendar.DATE,1);
						Date time=cal.getTime();
						newplan.setPlanDay(plans.getPlanDay()+1);
						newplan.setPlanId(plans.getPlanId());
						newplan.setPlanStartdate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(time));
						newplan.setPlanState(0);
						newplan.setPlan_unique(plans.getPlan_unique());
						yhHealthyPlanMapper.addHealthPlanDetail(newplan);
					}

				}
				yhUserhealthypaln.setPlanStartdate(plans.getPlanStartdate());
				yhHealthyPlanMapper.updateuserhealthplan(yhUserhealthypaln);
			}else {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				int plancount=0;
				for (int i = 0; i < yhplans.size(); i++) {
					if(yhplans.get(i).getPlandetailId()!=null) {
						plancount=plancount+yhplans.get(i).getPlandetailId().split(";").length;
					}
				}
				//最后一个任务就设置截至的时间
				if(plancount==plandays.getPlandetailId().split(";").length-1) {
					YhHealthyPlanApp healthyplan=yhHealthyPlanMapper.selectonehealthplan(plans.getPlanId());
					yhUserhealthypaln.setPlanEnddate(df.format(new Date()));
					if(plans.getPlanDay()==healthyplan.getPlanNumber()) {
						yhUserhealthypaln.setPlanState(2);
						yhHealthyPlanMapper.changeplanstate(yhUserhealthypaln.getUserCode());
						resize=10000;
					}else {
						//如果到了最后一天的话就改状态，否则添加一条明天要完成的数据
						YhUserHealthyPlanApp newplan =new YhUserHealthyPlanApp();
						newplan.setUserCode(plans.getUserCode());
						newplan.setCourseseq(0);


						newplan.setPlanDay(plans.getPlanDay()+1);
						newplan.setPlanId(plans.getPlanId());
						Calendar cal=Calendar.getInstance();
						cal.add(Calendar.DATE,1);
						Date time=cal.getTime();
						newplan.setPlanStartdate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(time));
						newplan.setPlanState(0);
						newplan.setPlan_unique(plans.getPlan_unique());
						yhHealthyPlanMapper.addHealthPlanDetail(newplan);
					}
				}
				yhUserhealthypaln.setPlanStartdate(df.format(new Date()));
				yhUserhealthypaln.setCourseseq(1);
				yhUserhealthypaln.setPlanState(1);
				yhUserhealthypaln.setPlan_unique(yhHealthyPlanMapper.nowplanunique(yhUserhealthypaln.getUserCode(),yhUserhealthypaln.getPlanId()));
				yhHealthyPlanMapper.addHealthPlanDetail(yhUserhealthypaln);
			}

			//获得微信用户的头像
			String wxuser=yhHealthyPlanMapper.getwxuserimgs(plans.getUserCode());
			if(wxuser==null){
				wxuser="http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png";
			}

			//如果top8的头像不是为空就判断是否大于八位，大于就替换否则就
			if(yhhealthyplandetail.getPlandetail_finishportrait()!=null) {
				String [] img=null;
				img=yhhealthyplandetail.getPlandetail_finishportrait().split(";");
				if(img.length<8) {
					yhhealthyplandetail.setPlandetail_finishportrait(yhhealthyplandetail.getPlandetail_finishportrait()+";"+wxuser);
				}else {
					String finish="";
					finish+=wxuser+";";
					for (int i = 0; i < img.length-1; i++) {
						if(i!=img.length-2) {
							finish+=img[i]+";";
						}else {
							finish+=img[i];
						}
					}
					yhhealthyplandetail.setPlandetail_finishportrait(finish);
				}
			}else {
				//设置top8的头像
				yhhealthyplandetail.setPlandetail_finishportrait(wxuser);
			}
			// 增加次数，并带入头像
			yhHealthyPlanMapper.addplandetailtotal(yhhealthyplandetail);
			//修改用户计划完成的数量
			return resize;
		}else {
			return resize;
		}
	}

	@Override
	public List<YhUserHealthyPlanApp> selectreportsteptwo(YhUserHealthyPlanApp YhUserHealthyPlan) {
		// TODO Auto-generated method stub
		List<YhUserHealthyPlanApp> repot=yhHealthyPlanMapper.selectplancount(YhUserHealthyPlan);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		int plancount=0;
		for (int j = 0; j < repot.size(); j++) {
			plancount=plancount+repot.get(j).getCourseseq();
			repot.get(j).setPlanStartdate((String)repot.get(j).getPlanStartdate().subSequence(0, 16));
			if(repot.get(j).getPlanEnddate()!=null) {
				repot.get(j).setPlanEnddate((String)repot.get(j).getPlanEnddate().substring(0, 16));
			}

		}
		for (int i = 0; i < repot.size(); i++) {
			repot.get(i).setPlan_complate(plancount);
		}
		return repot;
	}

	@Override
	public int selectbloodpressurenowday(String UserCode) {
		// TODO Auto-generated method stub
		return yhHealthyPlanMapper.selectbloodpressurenowday(UserCode);
	}

	@Override
	public int selectbminowday(String UserCode) {
		// TODO Auto-generated method stub
		return yhHealthyPlanMapper.selectbminowday(UserCode);
	}

	@Override
	public int selectbloodsugernowday(String UserCode) {
		// TODO Auto-generated method stub
		return yhHealthyPlanMapper.selectbloodsugernowday(UserCode);
	}

	@Override
	public int selectfoodnowday(String UserCode) {
		// TODO Auto-generated method stub
		return yhHealthyPlanMapper.selectfoodnowday(UserCode);
	}

	@Override
	public YhFoodSrecipeRecord selectrecode(String Usercode) {
		return yhHealthyPlanMapper.selectrecode(Usercode);
	}

	@Override
	public Integer selectday(String Usercode) {
		return yhHealthyPlanMapper.selectdays(Usercode);
	}

	@Override
	public YhFoodSrecipe selectsrecipe(String id) {
		return yhHealthyPlanMapper.selectsrecipe(id);
	}

	@Override
	public YhFoodUserScrecipe selectscrecipe(String ids) {
		return yhHealthyPlanMapper.selectscrecipe(ids);
	}

	@Override
	public YhFoodCookbook2 selectcookbook(String foodsid) {
		return yhHealthyPlanMapper.selectcookbook(foodsid);
	}



	public YhFoodUserScrecipe  selectIsHaveFood(String Usercode){
		//查询这个人是否有健康计划的套餐
		YhFoodSrecipeRecord recode=yhHealthyPlanMapper.selectrecode(Usercode);
		if(null==recode){
			return null;
		}
		//查询出现在是第几天的健康计划
		Integer day=yhHealthyPlanMapper.selectdays(Usercode);
		if(null==day){
			return null;
		}
		//查询出28天的饮食
		YhFoodSrecipe yhfoodsrecipe=yhHealthyPlanMapper.selectsrecipe(recode.getMealId());
		String[] ids=yhfoodsrecipe.getfElements().split(";");
		//找到28天中那一天的饮食下标
		String nowfood=ids[day-1];
		//查询当前一天推荐的饮食
		YhFoodUserScrecipe  selectscrecipe=yhHealthyPlanMapper.selectscrecipe(nowfood);
		return selectscrecipe;
	}

	@Override
	public int delHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan) {
		int a=yhHealthyPlanMapper.updhealthfood(yhuserhealthyplan.getUserCode());
		if(a>0){
			yhHealthyPlanMapper.updhealthfood1(yhuserhealthyplan.getUserCode());
		}
		return yhHealthyPlanMapper.changehealthplan(yhuserhealthyplan);
	}
}