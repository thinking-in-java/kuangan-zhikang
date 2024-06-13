package com.yihe.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yihe.common.annotation.Log;
import com.yihe.common.enums.BusinessStatus;
import com.yihe.common.json.JSON;
import com.yihe.common.utils.ServletUtils;
import com.yihe.common.utils.StringUtils;
import com.yihe.framework.manager.AsyncManager;
import com.yihe.framework.manager.factory.AsyncFactory;
import com.yihe.framework.util.ShiroUtils;
import com.yihe.system.domain.YhUserLog;
import com.yihe.system.domain.YhUserReginfo;

/**
 * 操作日志记录处理
 * 
 * @author lll
 */
@Aspect
@Component
public class LogAspect
{
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    private long startTime = 0L;
	// 结束时间
	private long endTime = 0L;
	
    // 配置织入点
    @Pointcut("@annotation(com.yihe.common.annotation.Log)")
    public void logPointCut()
    {
    	startTime = System.currentTimeMillis();
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult)
    {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     * 
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult)
    {
        try
        {
            // 获得注解
            Method controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null)
            {
                return;
            }
            
            // 获取当前的用户
            YhUserReginfo currentUser = ShiroUtils.getSysUser();
            // *========数据库日志=========*//
            YhUserLog operLog = new YhUserLog();
            //权限值
            if (controllerLog.isAnnotationPresent(RequiresPermissions.class)) {
    			RequiresPermissions requiresPermissions = controllerLog.getAnnotation(RequiresPermissions.class);
    			String[] permissions = requiresPermissions.value();
    			if (permissions.length > 0) {
    				operLog.setPermissions(permissions[0]);
    			}
    		}
            //操作人
            operLog.setUsername(currentUser.getUserName());
            // 请求的地址
            String ip = ShiroUtils.getIp();
            operLog.setIp(ip);
            // 返回参数
            operLog.setResult(JSON.marshal(jsonResult));
            operLog.setUserAgent(ServletUtils.getRequest().getHeader("User-Agent"));
            operLog.setUri(ServletUtils.getRequest().getRequestURI());
            /* if (currentUser != null)
            {
                operLog.setOperName(currentUser.getUserName());
                if (StringUtils.isNotNull(currentUser.getDept())
                        && StringUtils.isNotEmpty(currentUser.getDept().getDeptName()))
                {
                    operLog.setDeptName(currentUser.getDept().getDeptName());
                }
            }*/
            operLog.setStartTime(startTime);
            if (e != null)
            {
                operLog.setResult(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setParameter(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            endTime = System.currentTimeMillis();
            operLog.setSpendTime((int) (endTime - startTime));
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param log 日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(Method method, YhUserLog operLog) throws Exception
    { 
    	Log annotation = method.getAnnotation(Log.class);
        // 设置标题
        operLog.setDescription(annotation.title());
        // 是否需要保存request，参数和值
        if (annotation.isSaveRequestData())
        {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(YhUserLog operLog) throws Exception
    {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        String params = JSON.marshal(map);
        operLog.setParameter(StringUtils.substring(params, 0, 2000));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Method getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null)
        {
            return method;
        }
        return null;
    }
}
