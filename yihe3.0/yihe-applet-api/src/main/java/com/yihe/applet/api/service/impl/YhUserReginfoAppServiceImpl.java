package com.yihe.applet.api.service.impl;


import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.mapper.YhUserReginfoAppMapper;
import com.yihe.applet.api.service.YhUserReginfoAppService;

import java.util.*;

import com.yihe.common.utils.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* YhUserReginfoService实现
* Created by dyb on 2020/2/25.
*/
@Service
public class YhUserReginfoAppServiceImpl  implements YhUserReginfoAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserReginfoAppServiceImpl.class);

    @Autowired
    YhUserReginfoAppMapper yhUserReginfoAppMapper;

 
	@Override
	public YhUserReginfoApp selectByUserNameAndUserPwd(String userName, String userPwd) {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.selectByUserNameAndUserPwd(userName, userPwd);
	}

	@Override
	public YhUserReginfoApp selectByshoujiAndUsername(String userName, String mobile) {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.selectByshoujiAndUsername(userName, mobile);
	}

	@Override
	public int updateByUsernameAndMobile(String userName, String userPwd, String mobile) {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.updateByUsernameAndMobile(userName, userPwd, mobile);
	}

	@Override
	public int updateByUsercodeAndLoginTime(YhUserReginfoApp record) {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.updateByUsercodeAndLoginTime(record);
	}
	
	@Override
	public YhUserReginfoApp findYhUserReginfo(String userCode) {
		// TODO Auto-generated method stub
		YhUserReginfoApp yhUserReginfo = yhUserReginfoAppMapper.findYhUserReginfo(userCode);
		if(null!=yhUserReginfo && StringUtils.isEmpty(yhUserReginfo.getPostCode())){
			yhUserReginfo.setPostCode(1==yhUserReginfo.getGender()?"https://yjyqny.yhy.ren/new/maleDoctor.png":"https://yjyqny.yhy.ren/new/femaleDoctor.png");
		}
		return yhUserReginfo;
	}

	@Override
	public int updatePwd(YhUserReginfoApp yhUserReginfo) {
		
		return yhUserReginfoAppMapper.updatePwd(yhUserReginfo);
	}
	public static void main(String[] args) {
		System.out.println(new Md5Hash("邓永波"+"123456").toHex());
		System.out.println(new Md5Hash("123456"+"邓永波").toHex());
	}

	@Override
	public YhUserReginfoApp selectUserPwd(String userCode, String userPwd) {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.selectUserPwd(userCode, userPwd);
	}

	@Override
	public int insertSelective(YhUserReginfoApp record) {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserReginfoApp record) {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<String> findUserCodeByUserTypeFor5() {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.findUserCodeByUserTypeFor5();
	}

	@Override
	public int findUserNameExist(String username) {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.findUserNameExist(username);
	}

	@Override
	public Integer findUserRole(String usercode) {
		return yhUserReginfoAppMapper.findUserRole(usercode);
	}

	@Override
	public int findUserCodeByOrgIsExist(String userCode) {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.findUserCodeByOrgIsExist(userCode);
	}

	@Override
	public YhUserReginfoApp finddoctorHderer(String userCode) {
		// TODO Auto-generated method stub
		return yhUserReginfoAppMapper.finddoctorHderer(userCode);
	}

	@Override
	public Map<String, Integer> findUserInfo(String userCode) {
		YhUserReginfoApp reginfoApp = yhUserReginfoAppMapper.findYhUserReginfo(userCode);
		Map<String, Integer> map = new HashMap<>();
		map.put("sex", reginfoApp.getGender()-1);
		map.put("age", getAgeByBirth(reginfoApp.getBirthDate()));
		return map;
	}

	/**
	 * 根据传入的值计算年龄
	 */
	public static Integer getAgeByBirth(Date startDate) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间

			Calendar birth = Calendar.getInstance();
			birth.setTime(startDate);

			if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				if (now.get(Calendar.MONTH) <= birth.get(Calendar.MONTH)) {
					if (now.get(Calendar.MONTH) == birth.get(Calendar.MONTH)) {
						if (now.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
							age--;
						}
					} else {
						age--;
					}
				}
			}
			return age;
		} catch (Exception e) {// 兼容性更强,异常后返回数据
			return 0;
		}
	}
}