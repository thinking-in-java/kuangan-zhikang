package com.yihe.system.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserMakeCardLogVOJO;
import com.yihe.system.mapper.YhMakeCardInFoMapper;
import com.yihe.system.service.YhMakeCardInFoService;

@Service
@Transactional

public class YhMakeCardInFoServiceImpl  implements YhMakeCardInFoService{
	 private static final Logger LOGGER = LoggerFactory.getLogger(YhMakeCardInFoServiceImpl.class);
	@Autowired
	YhMakeCardInFoMapper yhmakecardinfomapper; 
	@Override
	public List<YhUserMakeCardLogVOJO> selectmakekardlog(Integer page,Integer limit,String guishu,String dates) {
		// TODO Auto-generated method stub
		//先算查询多少条
		String begintime="";
		String endtime="";
		int begin=(page-1)*limit;
		if(dates==null||"".equals(dates)) {
			
		}else {
			begintime=dates.substring(0,19);
			endtime=dates.substring(22,dates.length()-1);
		}

		//查询记录
		List<YhUserMakeCardLogVOJO> cardlogvojo= yhmakecardinfomapper.selectmakekardlog(begin,limit,guishu,begintime,endtime);
		
		List<YhUserMakeCard> makecard=null;
		for (int i = 0; i < cardlogvojo.size(); i++) {
			makecard=yhmakecardinfomapper.selectmakecard(cardlogvojo.get(i).getMarke_Ttime());
			int number=0;
			for (int j = 0; j < makecard.size(); j++) {
				if("".equals(makecard.get(j).getUserCode())||null==makecard.get(j).getUserCode()) {
					number++;
				}
			}
			cardlogvojo.get(i).setRemaining(number);
		}
		
		return cardlogvojo;
	}

	@Override
	public int makecard(Integer count,String Org_code,String miaoshu,Integer doctor) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String data=df.format(new Date());
		String handel="YHY";
		String jishu="00000";
		int kaishi=0;
		//先查出最大的number的编号
		String EnrolCardid=yhmakecardinfomapper.selectzuida();
		if(EnrolCardid!=null||!("").equals(EnrolCardid)) {
			handel=EnrolCardid.substring(0,3);
			jishu=EnrolCardid.substring(3);
		}
		String begin=getNewEquipmentNo(handel,jishu);	
		YhUserMakeCardLogVOJO cardlogvojo=new YhUserMakeCardLogVOJO();
		cardlogvojo.setMarke_Amount(count);
		cardlogvojo.setBegin_Number(begin);
		cardlogvojo.setMarke_Note(miaoshu);
		cardlogvojo.setOrg_Code(Org_code);
		if(kaishi==0) {
			kaishi=Integer.parseInt(jishu)+1;
		}
		YhUserMakeCard makcar=new YhUserMakeCard();
		for (int i = 0; i < count; i++) {
			String str=Integer.toString(kaishi);
			
			while(str.length()<5)
			 {
			  str="0"+str;
			  }
			makcar.setNumber(kaishi);
			makcar.setEnrolCardId(getNewEquipmentNo(handel, str));
			makcar.setOrgId(Org_code);
			makcar.setEnrolUserId("110");
			makcar.setDoctor(doctor);
			makcar.setEndTime(yhmakecardinfomapper.selectorgadddate(Org_code));
			try {
				cardlogvojo.setMarke_Ttime(df.parse(data));
				makcar.setEnrolTime(df.parse(data));
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			yhmakecardinfomapper.makelistcard(makcar);
			kaishi++;
		}
		cardlogvojo.setEnd_Number(makcar.getEnrolCardId());
		
		return yhmakecardinfomapper.makecardlog(cardlogvojo);
	}
	
	 public static String getNewEquipmentNo(String equipmentType, String equipmentNo){
	        String newEquipmentNo = "00001";
	 
	        if(equipmentNo != null && !equipmentNo.isEmpty()){
	            int newEquipment = Integer.parseInt(equipmentNo) + 1;
	            newEquipmentNo = String.format(equipmentType + "%05d", newEquipment);
	        }
	 
	        return newEquipmentNo;
	    }

	@Override
	public int selectcount(String guishu, String dates) {
		// TODO Auto-generated method stub
		String begintime="";
		String endtime="";
		if(dates==null||"".equals(dates)) {
			
		}else {
			begintime=dates.substring(0,19);
			endtime=dates.substring(22,dates.length()-1);
		}
		return yhmakecardinfomapper.selectcount(guishu,begintime,endtime);
	}

}
