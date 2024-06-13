package com.yihe.framework.shiro.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yihe.common.exception.user.CaptchaException;
import com.yihe.common.exception.user.RoleBlockedException;
import com.yihe.common.exception.user.UserBlockedException;
import com.yihe.common.exception.user.UserNotExistsException;
import com.yihe.common.exception.user.UserPasswordNotMatchException;
import com.yihe.common.exception.user.UserPasswordRetryLimitExceedException;
import com.yihe.common.utils.StringUtils;
import com.yihe.framework.shiro.service.SysLoginService;
import com.yihe.framework.util.ShiroUtils;
import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserRole;
import com.yihe.system.service.YhUserApiService;

/**
 * 自定义Realm 处理登录 权限
 * @author lll
 */
public class UserRealm extends AuthorizingRealm
{
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    private YhUserApiService yhUserApiService;
    @Autowired
    private SysLoginService loginService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0)
    {
    	YhUserReginfo user = ShiroUtils.getSysUser();
        // 当前用户所有角色                                                                             
        List<YhUserRole> userRoles = yhUserApiService.selectUserRoleByUpmsUserId(user.getUserCode());
        Set<String> roles = new HashSet<>();
        for (YhUserRole upmsRole : userRoles) {
            if (StringUtils.isNotBlank(upmsRole.getRoleName())) {
                roles.add(upmsRole.getRoleName());
            }
        }
        // 当前用户所有权限
        List<YhUserPermission> yhUserPermissions = yhUserApiService.selectUserPermissionByUpmsUserId(user.getUserCode());
        Set<String> permissions = new HashSet<>();
        for (YhUserPermission upmsPermission : yhUserPermissions) {
            if (StringUtils.isNotBlank(upmsPermission.getPermissionValue())) {
                permissions.add(upmsPermission.getPermissionValue());
            }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null)
        {
            password = new String(upToken.getPassword());
        }

        YhUserReginfo user = null;
        try
        {
            user = loginService.login(username, password);
        }
        catch (CaptchaException e)
        {
            throw new AuthenticationException(e.getMessage(), e);
        }
        catch (UserNotExistsException e)
        {
            throw new UnknownAccountException(e.getMessage(), e);
        }
        catch (UserPasswordNotMatchException e)
        {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        }
        catch (UserPasswordRetryLimitExceedException e)
        {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        }
        catch (UserBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (RoleBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
