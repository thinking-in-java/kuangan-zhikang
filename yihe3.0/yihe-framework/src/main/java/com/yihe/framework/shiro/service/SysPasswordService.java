package com.yihe.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yihe.common.constant.Constants;
import com.yihe.common.constant.ShiroConstants;
import com.yihe.common.exception.user.UserPasswordNotMatchException;
import com.yihe.common.exception.user.UserPasswordRetryLimitExceedException;
import com.yihe.common.utils.MessageUtils;
import com.yihe.framework.manager.AsyncManager;
import com.yihe.framework.manager.factory.AsyncFactory;
import com.yihe.system.domain.YhUserReginfo;

/**
 * 登录密码方法
 * 
 * @author lll
 */
@Component
public class SysPasswordService
{
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    @PostConstruct
    public void init()
    {
        loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
    }

    public void validate(YhUserReginfo user, String password)
    {
        String loginName = user.getUserName();

        AtomicInteger retryCount = loginRecordCache.get(loginName);

        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue())
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
        }

        if (!matches(user, password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(YhUserReginfo user, String newPassword)
    {
        return user.getUserPwd().equals(encryptPassword(user.getUserName(), newPassword));
    }

    public void clearLoginRecordCache(String username)
    {
        loginRecordCache.remove(username);
    }

    public  String encryptPassword(String username, String password)
    {
        return new Md5Hash(username + password).toHex();
    }

    public void unlock(String loginName){
        loginRecordCache.remove(loginName);
    }

   
}
