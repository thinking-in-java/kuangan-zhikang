package com.yihe.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.mapper.YhUserLogMapper;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.mapper.YhUserRolePermissionMapper;
import com.yihe.system.mapper.YhUserRoleRelationMapper;
import com.yihe.system.domain.YhUserLog;
import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserReginfoExample;
import com.yihe.system.domain.YhUserRole;
import com.yihe.system.domain.YhUserRolePermission;
import com.yihe.system.domain.YhUserRolePermissionExample;
import com.yihe.system.domain.YhUserRolePowerExample;
import com.yihe.system.domain.YhUserRoleRelation;
import com.yihe.system.domain.YhUserRoleRelationExample;
import com.yihe.system.service.YhUserApiService;
import com.yihe.system.mapper.YhUserApiMapper;

/**
 * YhUserApiService实现
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年3月6日 下午2:52:12 
 *@version 1.0 
 */

@Service
@Transactional
public class YhUserApiServiceImpl implements YhUserApiService{
	
	private static final Logger  LOGGER = LoggerFactory.getLogger(YhUserApiServiceImpl.class);
	
	@Autowired
	YhUserReginfoMapper yhUserReginfoMapper;
    @Autowired
    YhUserApiMapper yhUserApiMapper;
    @Autowired
    YhUserRolePermissionMapper yhUserRolePermissionMapper;
    @Autowired
    YhUserRoleRelationMapper yhUserRoleRelationMapper;
    @Autowired
    YhUserLogMapper yhUserLogMapper;

    
    /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
	@Override
	public List<YhUserPermission> selectUserPermissionByUpmsUserId(String upmsUserId) {
		// 用户不存在或锁定状态
		YhUserReginfo upmsUser = yhUserReginfoMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser || 1 == upmsUser.getLockFlag()) {
            LOGGER.info("selectUpmsPermissionByUpmsUserId : upmsUserId={}", upmsUserId);
            return null;
        }
        List<YhUserPermission> yhUserPermission = yhUserApiMapper.selectYhUserPermissionByUserId(upmsUserId);

        return yhUserPermission;
	}

	
	 /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
	@Override
	@Cacheable(value = "yihe-upms-rpc-service-ehcache", key = "'selectUserPermissionByUpmsUserId' + #upmsUserId")
	public List<YhUserPermission> selectUserPermissionByUpmsUserIdByCache(String upmsUserId) {
		return selectUserPermissionByUpmsUserId(upmsUserId);
	}

	/**
     * 根据用户id获取所属的角色
     * @param upmsUserId
     * @return
     */
	@Override
	public List<YhUserRole> selectUserRoleByUpmsUserId(String upmsUserId) {
		// 用户不存在或锁定状态
		YhUserReginfo upmsUser = yhUserReginfoMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser || 1 == upmsUser.getLockFlag()) {
            LOGGER.info("selectUpmsPermissionByUpmsUserId : upmsUserId={}", upmsUserId);
            return null;
        }
        List<YhUserRole> upmsRoles = yhUserApiMapper.selectYhUserRoleByUserId(upmsUserId);
        return upmsRoles;
	}

	
	 /**
     * 根据用户id获取所属的角色
     * @param upmsUserId
     * @return
     */
	@Override
	@Cacheable(value = "yihe-upms-rpc-service-ehcache", key = "'selectUserRoleByUpmsUserId' + #upmsUserId")
	public List<YhUserRole> selectUserRoleByUpmsUserIdByCache(String upmsUserId) {
		return selectUserRoleByUpmsUserId(upmsUserId);
	}

	 /**
     * 根据角色id获取所拥有的权限
     * @param upmsRoleId
     * @return
     */
	@Override
	public List<YhUserRolePermission> selectUserRolePermisstionByUpmsRoleId(Integer upmsRoleId) {
		YhUserRolePermissionExample yhUserRolePermissionExample = new YhUserRolePermissionExample();
		yhUserRolePermissionExample.createCriteria()
                .andRoleIdEqualTo(upmsRoleId);
        List<YhUserRolePermission> upmsRolePermissions = yhUserRolePermissionMapper.selectByExample(yhUserRolePermissionExample);
        return upmsRolePermissions;
	}

	
	 /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
	@Override
	public List<YhUserRoleRelation> selectUserUserPermissionByUpmsUserId(String upmsUserId) {
		YhUserRoleRelationExample yhUserRoleRelationExample = new YhUserRoleRelationExample();
		yhUserRoleRelationExample.createCriteria()
                .andUserCodeEqualTo(upmsUserId);
        List<YhUserRoleRelation> yhUserRoleRelation = yhUserRoleRelationMapper.selectByExample(yhUserRoleRelationExample);
        return yhUserRoleRelation;
	}

	
	/**
     * 根据username获取UpmsUser
     * @param username
     * @return
     */
	@Override
	public YhUserReginfo selectUpmsUserByUsername(String username) {
		YhUserReginfoExample upmsUserExample = new YhUserReginfoExample();
        upmsUserExample.createCriteria()
                .andUserNameEqualTo(username);
        List<YhUserReginfo> upmsUsers = yhUserReginfoMapper.selectByExample(upmsUserExample);
        if (null != upmsUsers && upmsUsers.size() > 0) {
            return upmsUsers.get(0);
        }
        return null;
	}

	
	 /**
     * 写入操作日志
     * @param record
     * @return
     */
	@Override
	public int insertUpmsLogSelective(YhUserLog record) {
		return yhUserLogMapper.insert(record);
	}


	@Override
	public List<YhUserRole> yhuserrole(String userId) {
		// TODO Auto-generated method stub
		return yhUserApiMapper.selectYhUserRoleByUserId(userId);
	}


	@Override
	public YhUserReginfo selectUserRegInfoByUserCode(String usercode) {
		// TODO Auto-generated method stub
		return yhUserReginfoMapper.selectByPrimaryKey(usercode);
	}
	
	
	
}
