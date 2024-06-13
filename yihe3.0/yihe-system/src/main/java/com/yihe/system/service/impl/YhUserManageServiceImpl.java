package com.yihe.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserRole;
import com.yihe.system.domain.YhUserRoleExample;
import com.yihe.system.mapper.YhUserManageMapper;
import com.yihe.system.mapper.YhUserRoleMapper;
import com.yihe.system.service.YhUserManageService;

@Service
@Transactional

public class YhUserManageServiceImpl  implements YhUserManageService{
	 private static final Logger LOGGER = LoggerFactory.getLogger(YhUserManageServiceImpl.class);
	 @Autowired
	 YhUserManageMapper yhusermanagemapper;
	 @Autowired
	 YhUserRoleMapper yhuserrolemapper;
	 @Override
	public List<YhUserReginfo> selectuseranrole(YhUserReginfo regin) {
		// TODO Auto-generated method stub
		 YhUserRoleExample yhuserrole=new YhUserRoleExample();
		 List<YhUserRole> yhuserroles=new ArrayList<YhUserRole>();
		 yhuserroles=yhuserrolemapper.selectByExample(yhuserrole);
		 regin.setPage((regin.getPage()-1)*10);
		 List<YhUserReginfo> yhuserreginfo=yhusermanagemapper.selectuseranrole(regin);
		 yhuserreginfo.get(0).setYhuserroles(yhuserroles);
		return yhuserreginfo;
	}
	@Override
	public int deleterolepowerbyusercode(String userCode) {
		// TODO Auto-generated method stub
		int result=yhusermanagemapper.deleterolepowerbyusercode(userCode);
		return result;
	}
	@Override
	public int deleteuserbyid(String userCode) {
		// TODO Auto-generated method stub
		int result=yhusermanagemapper.deleteByPrimaryKey(userCode);
		return result;
	}
	@Override
	public int updateuserbyid(YhUserReginfo yhuserreginfo) {
		// TODO Auto-generated method stub
		yhusermanagemapper.updaterole(yhuserreginfo);
		return yhusermanagemapper.updateuserbyid(yhuserreginfo);
	}
	@Override
	public int selectcount() {
		// TODO Auto-generated method stub
		return yhusermanagemapper.selectcount();
	}
}
