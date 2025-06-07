package com.yihe;


import com.alibaba.fastjson.JSONObject;
import com.yihe.common.constant.Constants;
import com.yihe.common.redis.RedisService;
import com.yihe.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 登录拦截器
 *
 * @author
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {


    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    public RedisService redisService;
    public LoginInterceptor(RedisService redisService){
        this.redisService = redisService;
    }

    /***
     * 在申请解决之前进行调用(Controller办法调用之前)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行了拦截器的preHandle办法"+request.getRequestURI());
        boolean isLogin=false;
        String osType = System.getProperty("os.name").toUpperCase();
//        if( osType .contains("WINDOWS") ){
//            isLogin=true;
//        }

        try {
            HttpSession session = request.getSession();
            //对立拦挡（查问以后session是否存在user）(这里user会在每次登录胜利后，写入session)
            String appToken = (String) session.getAttribute("token");
            if(StringUtils.isEmpty(appToken)){
                appToken = request.getParameter("token");
            }
            if(StringUtils.isEmpty(appToken)){
                appToken = request.getHeader("token");
            }
          /*  if(StringUtils.isEmpty(appToken)){
                //获取请求方式
                String method = request.getMethod();
                if ("POST".equals(method)){
                    isLogin = true;
                }
            }*/
            String token = redisService.getCacheObject(Constants.XCX_LOGIN_TOKEN + appToken);
            if (StringUtils.isNotEmpty(appToken) && StringUtils.isNotEmpty(token) && appToken.equals(token)) {
                isLogin = true;
                String falg = Constants.XCX_LOGIN_FALG + appToken;
                String loginFalg = redisService.getCacheObject(falg);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());
                System.out.println(loginFalg+": ---------------------------------");
                if(StringUtils.isEmpty(loginFalg) || !loginFalg.equals(date)){
                    redisService.setCacheObject(falg, date);
                    redisService.expire(falg, Constants.ONE_DAY_TIME);
                    redisService.expire(Constants.XCX_LOGIN_TOKEN + appToken, Constants.LOGIN_OVERDUE_TIME);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(isLogin==false){
            log.error("未登录", isLogin);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            JSONObject res = new JSONObject();
            res.put("code", HttpStatus.UNAUTHORIZED.value());//返回401 未登录
            res.put("msg","未登录:"+isLogin);
            PrintWriter out = null ;
            out = response.getWriter();
            out.write(res.toString());
            out.flush();
            out.close();
        }
        return isLogin;
    }

    /***
     * 申请解决之后进行调用，然而在视图被渲染之前（Controller办法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("执行了拦截器的postHandle办法"+request.getRequestURI());
    }

    /***
     * 整个申请完结之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（次要用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,    Object handler, Exception ex) throws Exception {
        System.out.println("执行了拦截器的afterCompletion办法"+request.getRequestURI());
    }


}
