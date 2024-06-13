package com.yihe.applet.api.util.common.util;

import com.alibaba.fastjson.JSONException;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

public class ApiOrcUtil {

    //设置APPID/AK/SK
//    private static String APP_ID = "23086434";
//    private static String API_KEY ="Vt5s3RNGQPLF4jcQ95SRM3Ia";
//    private static String SECRET_KEY = "TbILGFHPH1vlrxLWWXMZQXlpXQyUxahy";
    private static String APP_ID = "25589596";
    private static String API_KEY ="Q4h1gAbA67tGkEldnsRbG5h0";
    private static String SECRET_KEY = "I37xcKHmjEwnGqGAktPzA2h7CpHfiaBq";

    public static String getPictureString(String photoPath){

        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        // 是否检测朝向
        options.put("detect_direction", "false");
        // 是否检测风险
        options.put("detect_risk", "false");
        // 正反面front /back
        String idCardSide = "front";	
        // 参数为本地图片路径
        try {
            JSONObject res = client.idcard(photoPath, idCardSide, options);
            String stringJsonObj = res.toString();
            System.out.println("识别返回"+res);
            //判断请求次数是否已经用完
            if(stringJsonObj.contains("Open api qps request limit reached"))
                return "limit reached";
            if (!res.get("image_status").equals("non_idcard")) {
                JSONObject idCard = new JSONObject();
                JSONObject words_result = res.getJSONObject("words_result");
                idCard.put("image_status", res.get("image_status"));
                idCard.put("name", words_result.getJSONObject("姓名").get("words"));
                idCard.put("nation", words_result.getJSONObject("民族").get("words"));
                idCard.put("address", words_result.getJSONObject("住址").get("words"));
                idCard.put("sex", words_result.getJSONObject("性别").get("words"));
                idCard.put("birth", words_result.getJSONObject("出生").get("words"));
                idCard.put("number", words_result.getJSONObject("公民身份号码").get("words"));
                System.out.println(idCard.get("name"));
                return idCard.toString(2);
            } else {
                return "error";
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getPictureString("C:\\images\\imguploads/wx51ba2097b74c3854.o6zAJs97dSgKOKBVz1Mt4JJGhB2g.jNmbAGuMebtgca6182106793790119279c50c71aa14c.jpg"));
    }
}