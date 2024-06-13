package com.yihe.system.mapper;

import com.yihe.system.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhAcceptserviceMapper {
	YhManageTotalTask selectservicecountbyusercode(YhServicePage page);
	YhManageTotalTask	selectservicecountconditions(YhServicePage page);
	YhManageTotalTask selectservicebyusercode(YhServicePage page);
	int acceptservice(@Param(value = "serviceid")String serviceid,@Param(value = "Usercode")String Usercode);
	int selectCooperate(String usercode);
	int selectcount(String usercode);
	List<YhUserReginfo> selectusername(String usercode);
	int insertserviceplan(YhJzServicePerson yhJzServicePerson);
	int selectcountbyuserocde(YhJzServicePerson yhJzServicePerson);
	int selecttempcount(YhJzServicePerson yhJzServicePerson);
	String selectdoctor(@Param(value = "usercode") String usercode);
	int inserttotalper(@Param(value = "doctor") String doctor,@Param(value = "usercode") String usercode);
	YhUserReginfo selectregininfobyusercode(YhJzServicePerson yhJzServicePerson);
	int updateallcount(String usercode);
	int inserttemp(YhTemplateDetail templateDetail);
	int templateDetail(YhTemplateDetail templateDetail);
}
