package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.yihe.common.constant.ShiroConstants;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.common.utils.ServletUtils;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.service.YhUserLoginlogService;
import com.yihe.system.service.YhUserPageSettingService;
import com.yihe.system.service.YhUserRoleService;
import com.yihe.web.controller.tool.RedisUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录验证
 * 
 * @author lll
 */
@Controller
public class SysLoginController extends BaseController
{
    @Autowired
    YhUserRoleService yhUserRoleService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private YhUserPageSettingService yhUserPageSettingService;

    @Autowired
    private YhUserLoginlogService yhUserLoginlogService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, String org)
    {

//        for (int i=0;i<2500;i++){
//            YhUserMakeCard card= yhUserRoleService.selectmakecard(i);
//            if(card!=null){
//                if(card.getUserCode()!=null){
//                    yhUserRoleService.updatemakecard(card);
//                }
//            }
//        }
//        System.out.println("1324678456");
        String getimg = yhUserPageSettingService.getOrgNameInfo(org);
        String topimg, leftimg, name;
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            topimg = jsonObject.getString("21");
            leftimg = jsonObject.getString("19");
            name = jsonObject.getString("name");
            if(StringUtils.isNotEmpty(org)){
                redisUtils.set("ORG_CODE", org);
            }else{
                redisUtils.delete("ORG_CODE");
            }
        }else{
            topimg = yhUserPageSettingService.getimg(21);
            leftimg = yhUserPageSettingService.getimg(19);
            name = "陕西谷宜健康管理有限公司";
        }
        modelMap.put("topimg", topimg);
        modelMap.put("leftimg", leftimg);
        modelMap.put("name", name);
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "sso/login";
    }
    private Cache<String, AtomicInteger> loginRecordCache;
    @Autowired
    private CacheManager cacheManager;
    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;
    @PostConstruct
    public void init()
    {
        loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
    }
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(HttpServletRequest request,String username, String password, Boolean rememberMe,String code,String logintype,HttpServletResponse response)
    {
        /*YhUserLoginlog yhUserLoginlog = new YhUserLoginlog();
        yhUserLoginlog.setLoginTime(new Date());
        yhUserLoginlog.setLoginName(username);*/

    	if(!("NOPASSWORD").equals(logintype)) {
    	 HttpSession session = request.getSession();
    	 String codes=(String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            codes="132465";
    	 codes=codes.toUpperCase();
    	 code=codes;

    	 if(!codes.equals(code)) {
             /*yhUserLoginlog.setStatus("1");
             yhUserLoginlog.setMsg("登录失败,验证码错误");
             yhUserLoginlogService.insertUserLoginlog(yhUserLoginlog);*/
    		 String msg = "验证码错误";
    		 return error(msg);
    	 }
    	}
        if(yhUserRoleService.selectcountbyname(username)==0){
            /*yhUserLoginlog.setStatus("1");
            yhUserLoginlog.setMsg("登录失败,账号错误");
            yhUserLoginlogService.insertUserLoginlog(yhUserLoginlog);*/
            String msg = "账号错误";
            return error(msg);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();

        try
        {
            subject.login(token);
            if(rememberMe!=null) {
            	 Cookie usernames = new Cookie("username",username);
                 Cookie passwords = new Cookie("password",password);
                 usernames.setMaxAge(604800);
                 //usernames.setMaxAge(0);
                 usernames.setPath("/");
                 response.addCookie(usernames);
                 passwords.setMaxAge(604800);
                 //passwords.setMaxAge(0);
                 passwords.setPath("/");
                 response.addCookie(passwords);
            }else {
            	Cookie[] cookies = request.getCookies();//获取cookie数组
                for (Cookie c:cookies) {
                    if (c.getName().equals("username")){
                        Cookie cookie = new Cookie(c.getName(), null);//删除前必须要new 一个valu为空；
                        cookie.setPath("/");//路径要相同
                        cookie.setMaxAge(0);//生命周期设置为0
                    }
                    if (c.getName().equals("password")){
                        Cookie cookie = new Cookie(c.getName(), null);//删除前必须要new 一个valu为空；
                        cookie.setPath("/");//路径要相同
                        cookie.setMaxAge(0);//生命周期设置为0
                        break;
                    }

                }
            }


//            yhUserLoginlog.setIpaddr();
//            yhUserLoginlog.setLoginLocation();
//            yhUserLoginlog.setBrowser();
//            yhUserLoginlog.setOs();



            /*yhUserLoginlog.setStatus("0");
            yhUserLoginlog.setMsg("登录成功");
            yhUserLoginlogService.insertUserLoginlog(yhUserLoginlog);*/
            return success();
        }
        catch (AuthenticationException e)
        {
            AtomicInteger retryCount = loginRecordCache.get(username);
            if (retryCount == null)
            {
                retryCount = new AtomicInteger(0);
                loginRecordCache.put(username, retryCount);
            }
            if(retryCount.incrementAndGet()>=Integer.valueOf(maxRetryCount).intValue()){
                String msg="您的账号已被锁定十分钟";
                /*yhUserLoginlog.setStatus("1");
                yhUserLoginlog.setMsg("登录失败,密码错误");
                yhUserLoginlogService.insertUserLoginlog(yhUserLoginlog);*/
                return error(msg);
            }else{
                retryCount.decrementAndGet();
                String msg = "密码错误,剩余次数"+(Integer.valueOf(maxRetryCount).intValue()-retryCount.incrementAndGet());
                /*yhUserLoginlog.setStatus("1");
                yhUserLoginlog.setMsg("登录失败,密码错误");
                yhUserLoginlogService.insertUserLoginlog(yhUserLoginlog);*/
                return error(msg);
            }


        }
    }

    @PostMapping("/getfoodimg")
    @ResponseBody
    public List<String> getfoodimg()
    {
        List<String> listimg = yhUserRoleService.listimg();
        return listimg;
    }

    @PostMapping("/loginout")
    @ResponseBody
    public void ajaxLoginout()
    {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/error-403";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap, String org)
    {
        String getimg = yhUserPageSettingService.getOrgNameInfo(org);
        String topimg, leftimg, name;
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            topimg = jsonObject.getString("21");
            leftimg = jsonObject.getString("19");
            name = jsonObject.getString("name");
            if(StringUtils.isNotEmpty(org)){
                redisUtils.set("ORG_CODE", org);
            }else{
                redisUtils.delete("ORG_CODE");
            }
        }else{
            topimg = yhUserPageSettingService.getimg(21);
            leftimg = yhUserPageSettingService.getimg(19);
            name = "陕西谷宜健康管理有限公司";
        }
        modelMap.put("topimg", topimg);
        modelMap.put("leftimg", leftimg);
        modelMap.put("name", name);
        Cookie usernames = new Cookie("username", null);
        usernames.setMaxAge(0);
        Cookie password = new Cookie("password", null);
        password.setMaxAge(0);
        usernames.setPath("/");
        response.addCookie(usernames);
        usernames.setPath("/");
        response.addCookie(password);
        return "sso/login";
    }
}
