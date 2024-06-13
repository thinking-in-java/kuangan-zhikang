package com.yihe.applet.api.util.request;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.yihe.applet.api.util.common.util.RSAUtil;
import org.apache.commons.codec.binary.Hex;


/**
 * 加签帮助类
 *
 * @author lll
 */
public class SignHelper {


   /**
     * 加签
     * @param signField  加签的属性Bean
     * @param privateKey 私钥，去掉BEGIN开头和END结尾，去掉换行
     * @return 签名
     */
    public static String sign(SignFieldBean signField, String privateKey) {
        String signString = createSignString(signField.signFiledMap());
        return RSAUtil.signWithSHA256(signString, privateKey, "UTF-8");
    }

    
    /**
     * @throws Exception 
     * @Description: 秘钥时间戳加用户id加签
     * @param @param personId
     * @param @param date
     * @param @param privateKey
     * @param @return    设定文件  
     * @return String    返回类型  
     * @throws
     */
    public static String signTimeId(String personId,String date, String privateKey) throws Exception {
        String signString = date+""+personId;
        return encode(privateKey,signString);
    }
    
    
    public static String encode(String key, String str) throws Exception {
    	  // KEY转换
    	  DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
    	  SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
    	  Key convertSecretKey = factory.generateSecret(desKeySpec);

    	  // 加密（加解密方式：..工作模式/填充方式）
    	  Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    	  cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
    	  byte[] result = cipher.doFinal(str.getBytes());
    	  System.out.println("jdk des encrypt:" + Hex.encodeHexString(result));
    	  return Hex.encodeHexString(result);
    	 }
    
    
    private static String createSignString(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            StringBuilder prestr = new StringBuilder();
            List<String> keys = new ArrayList<String>(params.keySet());
            Collections.sort(keys);
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = params.get(key);
                if (value == null || value.toString().length() == 0) {
                    continue;
                }
                if (i != 0) {
                    prestr.append("&");
                }
                prestr.append(key).append("=");
                prestr.append(value);
            }
            return prestr.toString();
        }
        return "";
    }
     
 
   
    /**
     * 加签Bean，定义了需要加签的属性
     *
     * @author yangsongbo
     */
    public static class SignFieldBean {

        /**
         * 应用ID
         */
        private String appId;

        /**
         * 随机字符串，建议使用UUID
         */
        private String nonceStr;

        /**
         * 请求时间， yyyy-MM-dd HH:mm:ss
         */
        private String timestamp;

        /**
         * 版本号,固定为2.0
         */
        private String version;

        public SignFieldBean(String appId, String nonceStr, String timestamp, String version) {
            super();
            this.appId = appId;
            this.nonceStr = nonceStr;
            this.timestamp = timestamp;
            this.version = version;
        }

        @Override
        public String toString() {
            return "SignFieldBean [appId=" + appId + ", nonceStr=" + nonceStr
                    + ", timestamp=" + timestamp + ", version=" + version + "]";
        }

        public Map<String, String> signFiledMap() {
            Map<String, String> map = new HashMap<String, String>(4);
            map.put("appId", this.appId);
            map.put("nonceStr", this.nonceStr);
            map.put("timestamp", this.timestamp);
            map.put("version", this.version);
            return map;
        }
    }
}
