package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhChineseAnwser;
import com.yihe.applet.api.domain.YhChinesePhysique;
import com.yihe.applet.api.domain.YhHealthyPlandays;
import com.yihe.applet.api.domain.YhHealthyPlandetail;
import com.yihe.system.domain.YhUserHealthyPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhChinesePhysiqueMapper {
    int selectsex(String Usercode);
    List<YhChinesePhysique> selectallinfo(@Param("sex") Integer sex);
    int addhinesePhysique(YhChineseAnwser yhChineseAnwser);
    YhChineseAnwser selectanwser(String Usercode);
    int isfirstzy(String Usercode);
    int alltims();
    YhUserHealthyPlan getmaxplan(String Usercode);
    YhUserHealthyPlan getbeginplan(String Usercode);
    List<YhHealthyPlandays> selectplandays(@Param("plandays") Integer plandays,@Param("planid") Integer planid);
    YhHealthyPlandetail getplandetail(String id);
    List<YhUserHealthyPlan> getyhuserplan(@Param("Usercode")String Usercode,@Param("getPlanday")Integer getPlanday,@Param("getPlanId")Integer getPlanId);
}
