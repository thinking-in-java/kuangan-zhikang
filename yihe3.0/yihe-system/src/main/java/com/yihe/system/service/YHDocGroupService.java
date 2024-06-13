package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHDocGroup;

import java.util.List;

public interface YHDocGroupService {

	List<YHDocGroup> findGroupListByName(String gname);
	
	Integer findGroupInfoByDisCode( String code);
	
	List<YHDocGroup> selectAllInfo(String userCode);
	
	int modifyInfoUserCountJianYi(String gid);
	
	List<YHDocGroup> findDocGroupInfoByIdList(String idList);
	
	int deleteByPrimaryKey(Integer uDocgroupId);

	int insert(YHDocGroup record);

	int insertSelective(YHDocGroup record);

	YHDocGroup selectByPrimaryKey(Integer uDocgroupId);

	int updateByPrimaryKeySelective(YHDocGroup record);

	int updateByPrimaryKey(YHDocGroup record);

	PageInfo<YHDocGroup> selectByPrimaryKeyByDoc(String docid, int pagenum, int pagesize,String nikename);

	List<YHDocGroup> selectByPrimaryKeyByDocInfo(String docid);

	int updateByPrimaryKeyByUserCount(YHDocGroup record);
	
	int updateByPrimaryKeyByUserCountUp(YHDocGroup record);
}
