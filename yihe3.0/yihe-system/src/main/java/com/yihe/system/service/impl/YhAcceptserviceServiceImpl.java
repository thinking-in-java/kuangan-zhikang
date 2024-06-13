package com.yihe.system.service.impl;

import com.yihe.system.domain.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.mapper.YhAcceptserviceMapper;
import com.yihe.system.service.YhAcceptserviceService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class YhAcceptserviceServiceImpl implements YhAcceptserviceService{
	@Autowired
	YhAcceptserviceMapper yhacceptservicemapper;
	@Override
	public YhManageTotalTask selectservicecountbyusercode(YhServicePage page) {
		// TODO Auto-generated method stub
		return yhacceptservicemapper.selectservicecountbyusercode(page);
	}
	@Override
	public YhManageTotalTask selectservicebyusercode(YhServicePage page) {
		// TODO Auto-generated method stub
		page.setPage((page.getPage()-1)*20);
		return yhacceptservicemapper.selectservicebyusercode(page);
	}
	@Override
	public int acceptservice(String[] serviceid,String Usercode) {
		// TODO Auto-generated method stub
		
		int ret=0;
		for (int i = 0; i < serviceid.length; i++) {
			ret=ret+yhacceptservicemapper.acceptservice(serviceid[i],Usercode);
		}
		return ret;
	}

	@Override
	public double selectCooperate(String usercode) {
		int core=yhacceptservicemapper.selectCooperate(usercode);
		int corecount=yhacceptservicemapper.selectcount(usercode);
		if(corecount==0){
			return 0;
		}else{
			return deciMal(core,corecount*3);
		}

	}

	@Override
	public List<YhUserReginfo> selectusername(String usercode) {
		return yhacceptservicemapper.selectusername(usercode);
	}

	@Override
	public int insertserviceplan(YhJzServicePerson yhJzServicePerson) {
		String[] usercodelist=yhJzServicePerson.getSelect().split(",");
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		//String doctor=yhacceptservicemapper.selectdoctor(yh.getUserCode());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(yhJzServicePerson.getNowtime().equals(yhJzServicePerson.getDatetime()));
		System.out.println(yhJzServicePerson.getNowtime());
		System.out.println(yhJzServicePerson.getDatetime());
		if(yhJzServicePerson.getNowtime().equals(yhJzServicePerson.getDatetime())){


			for (int i=0;i< usercodelist.length;i++){
				yhJzServicePerson.setUserCode(usercodelist[i]);
				//查询在总表是否拥有
				int ifcount=yhacceptservicemapper.selectcountbyuserocde(yhJzServicePerson);
				YhUserReginfo reginfo=yhacceptservicemapper.selectregininfobyusercode(yhJzServicePerson);
				yhJzServicePerson.setGender(reginfo.getGender());
				yhJzServicePerson.setPhone(reginfo.getMobile());
				yhJzServicePerson.setStartServiceTime(df.format(new Date()));
				yhJzServicePerson.setServiceState(1);
				yhJzServicePerson.setServiceType(25);
				yhJzServicePerson.setIsrede(1);
				yhJzServicePerson.setTotaltype(3);
				yhJzServicePerson.setFinishi_doctor(yh.getUserCode());
				yhJzServicePerson.setInsertTime(df.format(new Date()));
				if(ifcount==1){
					yhacceptservicemapper.insertserviceplan(yhJzServicePerson);
				}else{
					yhacceptservicemapper.inserttotalper(yh.getUserCode(),yhJzServicePerson.getUserCode());
					yhacceptservicemapper.insertserviceplan(yhJzServicePerson);
				}
			}
		}else{
			for (int i=0;i< usercodelist.length;i++){
				yhJzServicePerson.setUserCode(usercodelist[i]);
				int ifcount=yhacceptservicemapper.selecttempcount(yhJzServicePerson);
				YhTemplateDetail templateDetail=new YhTemplateDetail();
				templateDetail.setEcdate(yhJzServicePerson.getDatetime());
				templateDetail.setServiceWay(yhJzServicePerson.getReturnvisit());
				templateDetail.setUserCode(yhJzServicePerson.getUserCode());
				templateDetail.setCountPrompt(yhJzServicePerson.getServiceRecord());
				templateDetail.setVar_count(yhJzServicePerson.getServiceSubtitle());
				if(ifcount==1){
					//先更改总数
					yhacceptservicemapper.updateallcount(yhJzServicePerson.getUserCode());
					yhacceptservicemapper.inserttemp(templateDetail);
				}else{
					yhacceptservicemapper.templateDetail(templateDetail);
					yhacceptservicemapper.inserttemp(templateDetail);
				}
			}
		}
		return 1;
	}

	private double deciMal(int top, int below) {
		double result = new BigDecimal((float)top / below).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		return result;
	}

	@Override
	public YhManageTotalTask selectservicecountconditions(YhServicePage page) {
		// TODO Auto-generated method stub
		return yhacceptservicemapper.selectservicecountconditions(page);
	}

}
