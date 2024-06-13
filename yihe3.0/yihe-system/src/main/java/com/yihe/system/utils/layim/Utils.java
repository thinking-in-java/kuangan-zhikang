package com.yihe.system.utils.layim;

public class Utils {
    /**
     * 生成房间ID
     */
    public static String generateRoomId(String from, String to, Integer msgType) {
       if(msgType==1) {
    	   return from + "" + to; 
       }else {
    	   return to+ "" + from;
       }
    }
}
