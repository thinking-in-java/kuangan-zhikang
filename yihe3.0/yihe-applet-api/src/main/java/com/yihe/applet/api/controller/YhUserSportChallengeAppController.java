/**
 *
 */
package com.yihe.applet.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yihe.applet.api.domain.YhUserSportChRecordApp;
import com.yihe.applet.api.domain.YhUserSportChallengeApp;
import com.yihe.applet.api.mapper.YhUserSportChRecordAppMapper;
import com.yihe.applet.api.service.YhUserSportChallengeAppMapperService;
import com.yihe.applet.api.util.common.util.Result;
import com.yihe.system.domain.YhUserReginfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dong
 *
 */
@Controller
@RequestMapping("/api/YhUserSportChallengeAppController")
@Api(value = "YhUserSportChallengeAppController控制器", description = "YhUserSportChallengeAppController管理")
public class YhUserSportChallengeAppController {
	@Autowired
	YhUserSportChallengeAppMapperService yhUserSportChallengeAppMapperService;

	@Autowired
	YhUserSportChRecordAppMapper yhUserSportChRecordAppMapper;

    @Resource
	private YhSportDiaryController yhSportDiaryController;



	/**
	 * 全部挑战个人历史
	 * @param curPage
	 * @param pageSize
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/showChWhetherNow")
	@ResponseBody
	public Result showChWhetherNow(String userCode) {
		Map<String, Object> map=new HashMap<String, Object>();

		map.put("userCode", userCode);
		int all=yhUserSportChallengeAppMapperService.showChWhetherNow(map);//全部

		map.put("chWhether", 1);
		int fulfill=yhUserSportChallengeAppMapperService.showChWhetherNow(map);//完成率

		 BigDecimal a = new BigDecimal(fulfill);
		BigDecimal b = new BigDecimal(all);
		BigDecimal bai = new BigDecimal(100);

		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("all", all);
		map1.put("fulfill", fulfill);
		map1.put("bfb", a.divide(b).multiply(bai));
		return Result.ok(map1);
	}



	/**
	 * 对于官方加密数据（encryptData）解密说明如下： 加密数据解密算法 接口如果涉及敏感数据（如wx.getUserInfo当中的
	 * openId 和unionId ），接口的明文内容将不包含这些敏感数据。开发者如需要获取敏感数据，需要对接口返回的加密数据(
	 * encryptedData )进行对称解密。 解密算法如下： 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。
	 * 对称解密的目标密文为 Base64_Decode(encryptedData), 对称解密秘钥 aeskey =
	 * Base64_Decode(session_key), aeskey 是16字节 对称解密算法初始向量 iv 会在数据接口中返回。
	 *
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @param encryptedData
	 *            加密内容
	 * @param sessionKey
	 *            小程序登录sessionKey
	 * @param iv
	 *            解密算法初始向量 iv 会在数据接口中返回。
	 * @param encodingFormat
	 *            编码格式默认UTF-8
	 *
	 * @param isSynchronize 用来标识是否同步【今日处方】的步数
	 * @return 返回解密后的字符串
	 * @throws Exception
	 */
	@RequestMapping(value = "/challengeStepAdd")
	@ResponseBody
	public Result decrypt(String encryptedData, String sessionKey, String iv,int chid,@RequestParam(required = false,defaultValue = "true") Boolean isSynchronize) throws Exception {
	    try {
	        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	        BASE64Decoder base64Decoder = new BASE64Decoder();
	        byte[] _encryptedData = base64Decoder.decodeBuffer(encryptedData);
	        byte[] _sessionKey = base64Decoder.decodeBuffer(sessionKey);
	        byte[] _iv = base64Decoder.decodeBuffer(iv);
	        SecretKeySpec secretKeySpec = new SecretKeySpec(_sessionKey, "AES");
	        IvParameterSpec ivParameterSpec = new IvParameterSpec(_iv);
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
	        byte[] original = cipher.doFinal(_encryptedData);
	        byte[] bytes = MyAESUtil.decode(original);
	        String originalString = new String(bytes, "UTF-8");
	        JSONObject jsonObject = JSONObject.parseObject(originalString);
	        //解析json，获取stepInfoList下面的步数
	        JSONArray stepInfoList = JSON.parseArray(jsonObject.getString("stepInfoList"));
	        //获取今天的步数
	        JSONObject today = stepInfoList.getJSONObject(stepInfoList.size()-1);
	        String message = today.get("step").toString();
	        Date date=new Date();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        String dateString = formatter.format(date);
	        Map<String, Object> map=new HashMap<String, Object>();
	        map.put("chId", chid);
	        map.put("uploadTome", dateString);
	        YhUserSportChRecordApp list=yhUserSportChRecordAppMapper.showChidRecordMap(map);
			if(isSynchronize){
				//获取用户userCode
				YhUserReginfo yhUserReginfo = yhUserSportChRecordAppMapper.selectByChId(chid, dateString);
				//同步运动记录的步数
				yhSportDiaryController.decrypt(encryptedData, sessionKey, iv, (null == yhUserReginfo ? null : yhUserReginfo.getUserCode()), false);
			}
			YhUserSportChRecordApp recapp=new YhUserSportChRecordApp();
        	recapp.setChId(chid);
        	recapp.setStep(message);
        	recapp.setUploadTome(date);
        	if(Integer.valueOf(message)>=6000) {
        		recapp.setWhether("1");
        	}else {
        		recapp.setWhether("2");
        	}
	        if(list==null) {//增加
	        	yhUserSportChRecordAppMapper.insertSelective(recapp);

	        	YhUserSportChallengeApp chall=yhUserSportChallengeAppMapperService.selectByPrimaryKey(chid);
	        	   String date1 = formatter.format(chall.getChFiaishTime());
	        	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        	   Date date3 = sdf.parse(dateString);
	   				Date date2 = sdf.parse(date1);
				if(date3.equals(date2)) {
					//相等
					if(Integer.valueOf(message)>=6000) {//当前步数是否大于6000
						int count=yhUserSportChallengeAppMapperService.selectByWhetherCount(chid);
						if(count>=6) {
							chall.setChWhether("1");
						}else {
							chall.setChWhether("2");
						}
		        	}else {
		        		chall.setChWhether("2");
		        	}
					int setp=0;
					List<YhUserSportChRecordApp> chrecord=yhUserSportChRecordAppMapper.showChidRecord(chid);
					for (YhUserSportChRecordApp yhUserSportChRecordApp : chrecord) {
						setp+=Integer.valueOf(yhUserSportChRecordApp.getStep());
					}
					double zong=(setp/42000f)*100;
					chall.setChAverageStep(String.valueOf(setp/7));
					chall.setChReach(String.format("%.2f", zong));
					yhUserSportChallengeAppMapperService.updateByPrimaryKeySelective(chall);
				}

	        }else {
	        	 recapp.setId(list.getId());
	        	yhUserSportChRecordAppMapper.updateByPrimaryKeySelective(recapp);
	        	YhUserSportChallengeApp chall=yhUserSportChallengeAppMapperService.selectByPrimaryKey(chid);
	        	   String date1 = formatter.format(chall.getChFiaishTime());
	        	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        	   Date date3 = sdf.parse(dateString);
	   				Date date2 = sdf.parse(date1);
				if(date3.equals(date2)) {
					//相等
					if(Integer.valueOf(message)>=6000) {//当前步数是否大于6000
						int count=yhUserSportChallengeAppMapperService.selectByWhetherCount(chid);
						if(count>=6) {
							chall.setChWhether("1");
						}else {
							chall.setChWhether("2");
						}
		        	}else {
		        		chall.setChWhether("2");
		        	}
					int setp=0;
					List<YhUserSportChRecordApp> chrecord=yhUserSportChRecordAppMapper.showChidRecord(chid);
					for (YhUserSportChRecordApp yhUserSportChRecordApp : chrecord) {
						setp+=Integer.valueOf(yhUserSportChRecordApp.getStep());
					}
					double zong=(setp/42000f)*100;
					chall.setChAverageStep(String.valueOf(setp/7));
					chall.setChReach(String.format("%.2f", zong));
					yhUserSportChallengeAppMapperService.updateByPrimaryKeySelective(chall);
				}
	        }

	        return Result.ok(message);

	    } catch (Exception ex) {
	        return null;
	    }
	}


	/**
	 * 根据code查询是否有执行的数据
	 * selectByCodeDnow
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/selectByCodeDnow")
	@ResponseBody
	public Result selectByCodeDnow(String userCode) {
		Map<String, Object> map=yhUserSportChallengeAppMapperService.selectByCodeDnow(userCode);
		return Result.ok(map);
	}


	/**
	 * 根据code查询是否有执行的数据
	 * selectByCodeDnow
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/selectByCodeXnow")
	@ResponseBody
	public Result selectByCodeXnow(int curPage,int pageSize,String userCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("curPage", (curPage-1)*pageSize);
		map.put("pageSize", pageSize);
		map.put("userCode", userCode);
		List<YhUserSportChallengeApp> map1=yhUserSportChallengeAppMapperService.selectByCodeXnow(map);
		for (YhUserSportChallengeApp yhUserSportChallengeApp : map1) {
			 SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
			   String dateString = formatter.format(yhUserSportChallengeApp.getChBeginTime());
			   yhUserSportChallengeApp.setBackups(dateString);
		}
		return Result.ok(map1);
	}


	@RequestMapping(value = "/addchallenge")
	@ResponseBody
	public Result addchallenge(YhUserSportChallengeApp chall) {
		Date date=new Date();
		String fetu=getFetureDate(6);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		chall.setChBeginTime(date);
		  ParsePosition pos = new ParsePosition(0);
		chall.setChFiaishTime(formatter.parse(fetu,pos));
		int i=yhUserSportChallengeAppMapperService.insertSelective(chall);
		return Result.ok(i);
	}


	/**
	    * 获取未来 第 past 天的日期
	    * @param past
	    * @return
	    */
	   public static String getFetureDate(int past) {
	       Calendar calendar = Calendar.getInstance();
	       calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
	       Date today = calendar.getTime();
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	       String result = format.format(today);
	       return result;
	   }



	   public static void main(String[] args) throws ParseException {
		   BigDecimal a = new BigDecimal(1);
			BigDecimal b = new BigDecimal(4);
				BigDecimal bai = new BigDecimal(100);

				//相等
			System.out.println(a.divide(b).multiply(bai));
	}



}
