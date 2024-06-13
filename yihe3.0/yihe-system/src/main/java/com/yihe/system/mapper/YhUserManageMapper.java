package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhUserReginfo;

public interface YhUserManageMapper {
		List<YhUserReginfo> selectuseranrole(YhUserReginfo regin);
		List<YhUserReginfo> selectuseranroles();
		int deleteByPrimaryKey(String userCode);
		int deleterolepowerbyusercode(String userCode);
		int updateuserbyid(YhUserReginfo yhuserreginfo);
		int updaterole(YhUserReginfo yhuserreginfo);
		int selectcount();
}
