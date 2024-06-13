package com.yihe.system.utils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
public class EchartsUtil {
	private static String url = "http://120.79.235.31";
    private static final String SUCCESS_CODE = "1";

    public static String generateEchartsBase64(String option) throws ClientProtocolException, IOException {
        String base64 = "";
        if (option == null) {
            return base64;
        }
        //option="{title:{text:'水果',x:'center',y:'center',textStyle:{fontSize:15}},tooltip:{trigger:'item'},series:[{type:'pie',center:['50%','50%'],radius:['24%','45%'],clockwise:true,avoidLabelOverlap:true,hoverOffset:15,label:{show:true,position:'outside',formatter:'{a|{b}：{d}%}\\n{hr|}',rich:{hr:{backgroundColor:'t',borderRadius:3,width:3,height:3,padding:[3,3,0,-12]},a:{padding:[-30,15,-20,15]}}},labelLine:{normal:{length:20,length2:30,lineStyle:{width:1}}},data:[{name:'正常',value:125276},{name:'销户',value:22957},{name:'报停',value:41536},{name:'拆表','value':35783},{name:'关阀',value:5542},{name:'待拆迁',value:5542},{name:'其他',value:63504}],}],}";
        //option="{\"title\":{\"text\":\"销售图\",\"subtext\":\"销售统计\",\"x\":\"CENTER\"},\"toolbox\": {\"feature\": {\"saveAsImage\": {\"show\": true,}}},\"tooltip\": {\"show\": true},\"legend\": {\"data\":[\"直接访问\",\"邮件营销\",\"联盟广告\",\"视频广告\",\"搜索引擎\"]}, \"series\":[{\"name\":\"访问来源\",\"type\":\"pie\",\"radius\": '55%',\"center\": ['50%', '60%'],\"data\":[{\"value\":335, \"name\":\"直接访问\"},{\"value\":310, \"name\":\"邮件营销\"},{\"value\":234, \"name\":\"联盟广告\"},{\"value\":135, \"name\":\"视频广告\"},{\"value\":1548, \"name\":\"搜索引擎\"}]}]}";
        option = option.replaceAll("\\s+", "").replaceAll("\"", "'");
        Map<String, String> params = new HashMap<>();
        params.put("opt", option);
        String response = HttpUtil.post(url, params, "utf-8");

        // 解析echartsConvert响应
        JSONObject responseJson = JSON.parseObject(response);
        String code = responseJson.getString("code");

        // 如果echartsConvert正常返回
        if (SUCCESS_CODE.equals(code)) {
            base64 = responseJson.getString("data");
        }
        // 未正常返回
        else {
            String string = responseJson.getString("msg");
            throw new RuntimeException(string);
        }

        return base64;
    }
}
