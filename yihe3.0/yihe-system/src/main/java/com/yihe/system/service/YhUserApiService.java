package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhUserLog;
import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserRole;
import com.yihe.system.domain.YhUserRolePermission;
import com.yihe.system.domain.YhUserRoleRelation;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年3月6日 下午2:33:44 
 *@version 1.0 
 */
public interface  YhUserApiService {

	 /**
     * 根据用户id获取所拥有的权限(用户和角色权限合集)
     * @param upmsUserId
     * @return
     */
    List<YhUserPermission> selectUserPermissionByUpmsUserId(String upmsUserId);

    /**
     * 根据用户id获取所拥有的权限(用户和角色权限合集)
     * @param upmsUserId
     * @return
     */
    List<YhUserPermission> selectUserPermissionByUpmsUserIdByCache(String upmsUserId);

    /**
     * 根据用户id获取所属的角色
     * @param upmsUserId
     * @return
     */
    List<YhUserRole> selectUserRoleByUpmsUserId(String upmsUserId);

    /**
     * 根据用户id获取所属的角色
     * @param upmsUserId
     * @return
     */
    List<YhUserRole> selectUserRoleByUpmsUserIdByCache(String upmsUserId);

    /**
     * 根据角色id获取所拥有的权限
     * @param upmsRoleId
     * @return
     */
    List<YhUserRolePermission> selectUserRolePermisstionByUpmsRoleId(Integer upmsRoleId);

    /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
    List<YhUserRoleRelation> selectUserUserPermissionByUpmsUserId(String upmsUserId);

   

    /**
     * 根据username获取UpmsUser
     * @param username
     * @return
     */
    YhUserReginfo selectUpmsUserByUsername(String username);

    /**
     * 写入操作日志
     * @param record
     * @return
     */
    int insertUpmsLogSelective(YhUserLog record);
    
    /**
     * 根据userid获取role
     */
    List<YhUserRole> yhuserrole(String roleid);
    
    
    /**
     * 根据usercode查询用户信息
     */
    
    YhUserReginfo selectUserRegInfoByUserCode(String usercode);
}
