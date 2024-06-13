package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.*;
import com.yihe.system.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface YhHealthyPlanMapper {
    List<YhHealthyPlanApp> yhhealthyplan(@Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize);
    YhHealthyPlanApp selectonehealthplan(@Param("id") Integer id);
    Integer selectyhhealthycount();
    Integer yhhealthplanwatch(Integer id);
    YhHealthyPlanApp selectyhhealthyplanbyid(Integer id);
	int addHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan);
	int boolenaddHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan);
	int changehealthplan(YhUserHealthyPlanApp yhuserhealthyplan);
	int updhealthfood(@Param("userCode") String userCode);
	int updhealthfood1(@Param("userCode") String userCode);
	int updateHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan);
	List<YhUserHealthyPlanApp> selectyhuserhealthplanisbegin(YhUserHealthyPlanApp yhuserhealthyplan);
	YhUserHealthyPlanApp selectalreadybegun(YhUserHealthyPlanApp yhuserhealthyplan);
	List<YhUserHealthyPlanApp> selectalreadybegunoneplan(YhUserHealthyPlanApp yhuserhealthyplan);
	YhUserHealthyPlanApp selectalreadyagin(YhUserHealthyPlanApp yhuserhealthyplan);
	YhHealthyPlandays selectplandays(YhUserHealthyPlanApp yhuserhealthyplan);
	List<YhHealthyPlandays> selectplandaysa(YhUserHealthyPlanApp yhuserhealthyplan);
	YhHealthyPlandetail selectyhhealthplandetailbyid(Integer id);
	int updateuserhealthplan(YhUserHealthyPlanApp yhuserhealthyplan);
	int addplandetailtotal(YhHealthyPlandetail yhhealthyplandetail);
	int nowplanunique(@Param("user_code") String user_code, @Param("plan_id") Integer plan_id);
	//YhWxUser getwxuserimg(String usercode);
	int selectmaxunique();
	List<YhUserHealthyPlanApp> selectplancount(YhUserHealthyPlanApp yhuserhealthyplan);
	List<YhUserHealthyPlanApp> selectallplancount(YhUserHealthyPlanApp yhuserhealthyplan);
	int changeplanstate(@Param("user_code") String user_code);
	int selectbloodpressurenowday(@Param("UserCode") String UserCode);
	int selectbminowday(@Param("UserCode") String UserCode);
	int selectbloodsugernowday(@Param("UserCode") String UserCode);
	int selectfoodnowday(@Param("UserCode") String UserCode);
	YhUserHealthyPlanApp selectnowday(YhUserHealthyPlanApp yhuserhealthyplan);
	int updateaddplan(YhUserHealthyPlanApp yhuserhealthyplan);
	yhHealthyPlan selectisshenhe(YhUserHealthyPlanApp yhuserhealthyplan);
	int insertSelectiveperson(YhJzServicePerson person);
	int deleteselectperson(YhJzServicePerson person);
	int selecttotoservice(String usercode);
	int inserttotoservice(String usercode);
	String selectdisname(YhDiseaseAssessReport reports);
	String selectdisname2(YhDiseaseAssessReport reports);
	YhFoodSrecipeRecord selectrecode(String Usercode);
	Integer selectdays(String Usercode);
	YhFoodSrecipe selectsrecipe(String id);
	YhFoodUserScrecipe selectscrecipe(String ids);
	YhFoodCookbook2 selectcookbook(String foodsid);
	String getwxuserimgs(String usercode);
	Integer selectalljifen(String usercode);
	Integer insertzong(String usercode);
	Integer changezong(String usercode);
	Integer changefenbiao(String usercode);
}