package com.yihe.system.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class GetDistance {
    public static Long getDistance(String startLonLat, String endLonLat,String key){
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Long result = 0L;
        String queryUrl = "http://restapi.amap.com/v3/distance?type=0&key="+key+"&origins="+startLonLat+"&destination="+endLonLat;
        String queryResult = getResponse(queryUrl);
        JSONObject jo = new JSONObject().fromObject(queryResult);
        JSONArray ja = jo.getJSONArray("results");
        result = Long.parseLong(new JSONObject().fromObject(ja.getString(0)).get("distance").toString());
        return result;
    }

    private static String getResponse(String serverUrl){
        //用JAVA发起http请求，并返回json格式的结果
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String[] args){
        String startLonLat="113.018522,27.941739";
        String endLonLat="113.01996,27.941064";
        String key="d07c5595e42b41bb3de6eed154bc6946";

        System.out.println(startLonLat);
        System.out.println(endLonLat);
        Long dis = getDistance(startLonLat,endLonLat,key);
        System.out.println(dis);
    }
}