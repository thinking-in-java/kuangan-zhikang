package com.yihe.system.mapper;

import com.yihe.system.domain.YHDocGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YHDocGroupMapper {

	List<YHDocGroup> findGroupListByName(String gname);
	
	int findEnrolinfoByDoctorInfoForNum(@Param("doctor") String doctor,@Param("ginfo") String ginfo);
	
	Integer findGroupInfoByDisCode(@Param("code") String code);
	
	int modifyInfoUserCountJianYi(@Param("gid") String gid);
	
	int deleteByPrimaryKey(Integer uDocgroupId);

	int insert(YHDocGroup record);

	int insertSelective(YHDocGroup record);

	YHDocGroup selectByPrimaryKey(Integer uDocgroupId);

	List<YHDocGroup> selectByPrimaryKeyByDoc(@Param("docid")String docid,@Param("nikename")String nikename);
	
	List<YHDocGroup> findDocGroupInfoByIdList(@Param("idList") String idList);

	int updateByPrimaryKeySelective(YHDocGroup record);

	int updateByPrimaryKey(YHDocGroup record);

	int updateByPrimaryKeyByUserCount(YHDocGroup record);

	int updateByPrimaryKeyByUserCountUp(YHDocGroup record);

	List<YHDocGroup> selectAllInfo(String userCode);
}