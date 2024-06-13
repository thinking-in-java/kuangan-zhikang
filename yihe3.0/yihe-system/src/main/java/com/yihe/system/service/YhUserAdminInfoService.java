package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.TestingVOJO;
import com.yihe.system.domain.YhUserAdminInfo;
import com.yihe.system.domain.YhUserAdminInfoExample;
import com.yihe.system.domain.YhUserRole;

import java.util.List;

/**
* YhUserAdminInfoService接口
* Created by dyb on 2020/3/19.
*/
public interface YhUserAdminInfoService{
	/**
	 * 根据code查询医生个数 
	 * @param orgCode
	 * @return
	 */
	int selectByadminCount(String orgCode);
	List<YhUserAdminInfo> selectByExample(YhUserAdminInfoExample example);
	
	YhUserAdminInfo selectByPrimaryKeyByusercode(String usercode);
	
	PageInfo<TestingVOJO> selectByPrimaryKeyByDoctor(Integer doctor,Integer orgid,String username,String usernameLike,String  order,int pageNum,int pageSize);

	List<YhUserRole>  selectRole(Integer roleid);

	Integer changemanager(Integer roleid,Integer[] arry);

	Integer changeusermanager(Integer roleid,String[] userCodes);
}