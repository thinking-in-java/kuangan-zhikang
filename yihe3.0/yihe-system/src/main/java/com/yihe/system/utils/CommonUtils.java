package com.yihe.system.utils;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年1月8日 下午4:48:44 
 *@version 1.0 
 */
public class CommonUtils {
	
	
	/**
	 * @Title: insert  
	 * @Description: 在数组指定位置内插入数据  
	 * @param @param arr
	 * @param @param i
	 * @param @param l
	 * @param @return    设定文件  
	 * @return String[]    返回类型  
	 * @throws
	 */
	public static String[] insert(String[] arr,int i,String l){
		//新建数组,对原数组扩容
		String[] arr1 = new String[arr.length+1];
		//将原数组数据赋值给新数组
		for(int j = 0;j<arr.length;j++){
			arr1[j] = arr[j];
		}
		//将大于i的数据向后移动一位
		for(int j = arr1.length-2;j>=i;j--){
			arr1[j+1] = arr1[j];
		}
		//赋值到i位置
		arr1[i] = l;
		return arr1;
	}
	
	
	/**
	 * 
	 * @Title: doJsontoModel  
	 * @Description: 获取对象属性为key的值
	 * @param @param dataObject
	 * @param @param key
	 * @param @return    设定文件  
	 * @return String    返回类型  
	 * @throws
	 */
	public static String  doJsontoModel2(net.sf.json.JSONObject dataObject,String key) {
		String keyValue = null;
		 Set<String> jsonSet = dataObject.keySet();
	     Iterator<String> keys = jsonSet.iterator();
	     //循环遍历出的key值
	     while (keys.hasNext()) {
	    	 String nextKey = keys.next();
	    	 Object object = dataObject.get(nextKey);
	    	 if(object instanceof net.sf.json.JSONObject){
	    		 net.sf.json.JSONObject nextObject = net.sf.json.JSONObject.fromObject(object.toString());
	    		 doJsontoModel2(nextObject, key);
	    	 }else{ 
	    		 
	    		 if(key.equals(nextKey)){
	    			 keyValue = object.toString();
	    			 break;
	    		 }
	    	 }
		}
		return keyValue;
	}
	
	
	/**
	 * @Title: isNumeric  
	 * @Description: 判断是否有小数 
	 * @param @param str
	 * @param @return    设定文件  
	 * @return boolean    返回类型  
	 * @throws
	 */
	public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    if(str.indexOf(".")>0){//判断是否有小数点
	        if(str.indexOf(".")==str.lastIndexOf(".") && str.split("\\.").length==2){ //判断是否只有一个小数点
	            return pattern.matcher(str.replace(".","")).matches();
	        }else {
	            return false;
	        }
	    }else {
	        return pattern.matcher(str).matches();
	    }
	}
	
    
	/**
	 * @Title: isChinese  
	 * @Description: 判断是否含有中文
	 * @param @param str
	 * @param @return    设定文件  
	 * @return boolean    返回类型  
	 * @throws
	 */
	public static boolean isChinese(String countname)
    {
         Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(countname);
            if (m.find()) {
                return true;
            }
            return false;
    }

	
	/***
	 * 判断 String 是否是 int<br>通过正则表达式判断
	 * @param input
	 * @return
	 */

	public static boolean isInteger(String input){
		Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
		return mer.find();
	}
	
	/**
	 * 判断 String 是否是 double<br>通过正则表达式判断
	 * @param input
	 * @return
	 */

	public static boolean isDouble(String input){
		Matcher mer = Pattern.compile("^[+-]?[0-9.]+$").matcher(input);
		return mer.find();
	}
		
	public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
}
}
