/**
 * 
 */
package com.yihe.applet.api.controller;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.util.common.util.OrderPay;
import com.yihe.applet.api.util.common.util.PayUtil;
import com.yihe.system.utils.Result;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/WxPayment")
public class WxPayment {
	
	 private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	 private static final Random RANDOM = new SecureRandom();
	 
	@RequestMapping(value="/payment")
	@ResponseBody
    public Result payment(String openid){
		String nonce_str = generateNonceStr();//随机字符串
		String out_trade_no = getStringToday();//订单编号
		String total_fee="1";//金额
		String spbill_create_ip=getShowIpv4();//获取当前ip
		String body = "购买商品 小米11";
		//上面一部分代码就是构造Orderpay中地参数，需要改，上面部分地代码都需要改成你自己地
		String AppId="wx51ba2097b74c3854";//小程序id
		String MchId="1603617616";//商户id
		String NotifyUrl="http://www.weixin.qq.com/wxpay/pay.php";//通知地址
		String key="HunanYiheCloudHealthTechnologyCo";//商户密钥
		//构造成类
		OrderPay orderPay = new OrderPay(AppId, MchId, nonce_str, body, out_trade_no, total_fee, spbill_create_ip, NotifyUrl, "JSAPI", openid,key,"https://api.mch.weixin.qq.com/pay/unifiedorder");
		//调用方法，得到统一下单地数据
		Map<String, Object> pac = PayUtil.wxPay(orderPay);
		//直接返回给前端，前端调用即可
		return Result.ok(pac);
    }
	
	

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
    
    
   /**
    * 商户订单号
    * @return
    */
  public static String getStringToday() {
     Date currentTime = new Date();
     SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
     String dateString = formatter.format(currentTime);
     
     char[] nonceChars = new char[10];
     for (int index = 0; index < nonceChars.length; ++index) {
         nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
     }
     
     return dateString+new String(nonceChars);
  }
  
  
  /**
   * 获取当前ip
   * @return
   */
 public static String getShowIpv4() {
	 try {
			InetAddress ip4 = Inet4Address.getLocalHost();
			 return ip4.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}	
    
    return "ip地址错误";
 }
  
  
  
  public static void main(String[] args){
			System.out.println(getShowIpv4());
	}
}
