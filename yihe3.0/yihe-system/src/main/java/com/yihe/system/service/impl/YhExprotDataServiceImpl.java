package com.yihe.system.service.impl;

import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhExprotDataMapper;
import com.yihe.system.service.YhExprotDataService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class YhExprotDataServiceImpl implements YhExprotDataService{
	@Autowired
	YhExprotDataMapper yhexportdata;
	@Override
	public List<YhOrgTreeVojo> selectorg(String code) {
		// TODO Auto-generated method stub
		return yhexportdata.selectorg(code);
	}

	@Override
	public List<YhUserReginfoVOJO> exportnow(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhUserReginfoVOJO> reginfo = new ArrayList<YhUserReginfoVOJO>();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		if(yhexportnow.getReginfo()!=null) {
			ArrayList<YhUserReginfoVOJO> reginfoadd=new ArrayList<YhUserReginfoVOJO>();
			String ismin=null;
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
			for (int i = 0; i < arr1.length; i++) {
					ismin=arr1[i];
			}
			if(yh.getOrgCode().equals(ismin)) {
				yhexportnow.setNowselect(ismin);
				reginfoadd=(ArrayList<YhUserReginfoVOJO>) yhexportdata.selectreginbyorg(yhexportnow);
				reginfo.addAll(reginfoadd);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					reginfoadd=(ArrayList<YhUserReginfoVOJO>) yhexportdata.selectreginbyorg(yhexportnow);
					reginfo.addAll(reginfoadd);
				}
			}


		}
		return reginfo;

	}

	@Override
	public List<YhExportCheck> exportcheck(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportCheck> check = new ArrayList<YhExportCheck>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportCheck> addcheck = new ArrayList<YhExportCheck>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportCheck>) yhexportdata.selectexportdate(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportCheck>) yhexportdata.selectexportdate(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportHealthAssess> exportassessreport(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportHealthAssess> check = new ArrayList<YhExportHealthAssess>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportHealthAssess> addcheck = new ArrayList<YhExportHealthAssess>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				addcheck=null;
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportHealthAssess>) yhexportdata.selectriskresult(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					addcheck=null;
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportHealthAssess>) yhexportdata.selectriskresult(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}
	@Override
	public List<yhDiseaseAssessTotalReport2> exportassessreport2(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<yhDiseaseAssessTotalReport2> check = new ArrayList<yhDiseaseAssessTotalReport2>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<yhDiseaseAssessTotalReport2> addcheck = new ArrayList<yhDiseaseAssessTotalReport2>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				addcheck=null;
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<yhDiseaseAssessTotalReport2>) yhexportdata.exportassessreport2(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					addcheck=null;
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<yhDiseaseAssessTotalReport2>) yhexportdata.exportassessreport2(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}
		if(!yhexportnow.getTime()){
			List<yhDiseaseAssessTotalReport2> check2 = new ArrayList<yhDiseaseAssessTotalReport2>();
			for (int i=0;i<check.size();i++){
				boolean jia=true;
				for (int j=0;j<check2.size();j++){
					if(check.get(i).getUserCode().equals(check2.get(j).getUserCode())){
						jia=false;
					}
				}
				if(jia){
					check2.add(check.get(i));
				}
			}
			return check2;
		}
		return check;
	}
	@Override
	public List<YhExportApressure> exportapressure(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportApressure> check = new ArrayList<YhExportApressure>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportApressure> addcheck = new ArrayList<YhExportApressure>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportApressure>) yhexportdata.selectapressure(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportApressure>) yhexportdata.selectapressure(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportApressure> exportxinli(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportApressure> check = new ArrayList<YhExportApressure>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportApressure> addcheck = new ArrayList<YhExportApressure>();

		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportApressure>) yhexportdata.exportxinli(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportApressure>) yhexportdata.exportxinli(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}
		return check;
	}

	@Override
	public List<YhExportApressure> exportrstripressure(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportApressure> check = new ArrayList<YhExportApressure>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportApressure> addcheck = new ArrayList<YhExportApressure>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportApressure>) yhexportdata.exportrstripressure(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportApressure>) yhexportdata.exportrstripressure(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExprotBloodPressure> exportbloodpressure(YhExportData yhexportnow) {
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExprotBloodPressure> check = new ArrayList<YhExprotBloodPressure>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExprotBloodPressure> addcheck = new ArrayList<YhExprotBloodPressure>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExprotBloodPressure>) yhexportdata.exportbloodpressure(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExprotBloodPressure>) yhexportdata.exportbloodpressure(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExprotBloodSuger> exportbloodsuger(YhExportData yhexportnow) {
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExprotBloodSuger> check = new ArrayList<YhExprotBloodSuger>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExprotBloodSuger> addcheck = new ArrayList<YhExprotBloodSuger>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExprotBloodSuger>) yhexportdata.exportbloodsuger(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExprotBloodSuger>) yhexportdata.exportbloodsuger(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportBodyFat> exportbodyfat(YhExportData yhexportnow) {
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportBodyFat> check = new ArrayList<YhExportBodyFat>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportBodyFat> addcheck = new ArrayList<YhExportBodyFat>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportBodyFat>) yhexportdata.exportbodyfat(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportBodyFat>) yhexportdata.exportbodyfat(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExprotBMI> exportbmi(YhExportData yhexportnow) {
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExprotBMI> check = new ArrayList<YhExprotBMI>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExprotBMI> addcheck = new ArrayList<YhExprotBMI>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExprotBMI>) yhexportdata.exportbmi(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExprotBMI>) yhexportdata.exportbmi(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExprotBloodFat> exportbloodfat(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExprotBloodFat> check = new ArrayList<YhExprotBloodFat>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExprotBloodFat> addcheck = new ArrayList<YhExprotBloodFat>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExprotBloodFat>) yhexportdata.exportbloodfat(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExprotBloodFat>) yhexportdata.exportbloodfat(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportLiver> exportliver(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportLiver> check = new ArrayList<YhExportLiver>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportLiver> addcheck = new ArrayList<YhExportLiver>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportLiver>) yhexportdata.exportliver(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportLiver>) yhexportdata.exportliver(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportUserUBA> exportuba(YhExportData YhExportUserUBA) {
		// TODO Auto-generated method stub
		String[] arr1=YhExportUserUBA.getSelect().split(",");
		List<YhExportUserUBA> check = new ArrayList<YhExportUserUBA>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		if(YhExportUserUBA.getTime()) {
			YhExportUserUBA.setBegintime(YhExportUserUBA.getTimes().substring(0, 10));
			YhExportUserUBA.setEndtime(YhExportUserUBA.getTimes().substring(13));
		}
		List<YhExportUserUBA> addcheck = new ArrayList<YhExportUserUBA>();
		if(YhExportUserUBA.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				YhExportUserUBA.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportUserUBA>) yhexportdata.exportuba(YhExportUserUBA);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					YhExportUserUBA.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportUserUBA>) yhexportdata.exportuba(YhExportUserUBA);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportOther> other(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportOther> check = new ArrayList<YhExportOther>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportOther> addcheck = new ArrayList<YhExportOther>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportOther>) yhexportdata.other(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportOther>) yhexportdata.other(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExprotBloodSuger> allexportbloodsuger(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExprotBloodSuger> check = new ArrayList<YhExprotBloodSuger>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExprotBloodSuger> addcheck = new ArrayList<YhExprotBloodSuger>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExprotBloodSuger>) yhexportdata.allexportbloodsuger(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExprotBloodSuger>) yhexportdata.allexportbloodsuger(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportBodyFat> allexportbodyfat(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportBodyFat> check = new ArrayList<YhExportBodyFat>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportBodyFat> addcheck = new ArrayList<YhExportBodyFat>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportBodyFat>) yhexportdata.allexportbodyfat(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportBodyFat>) yhexportdata.allexportbodyfat(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExprotBloodPressure> allexportbloodpressure(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExprotBloodPressure> check = new ArrayList<YhExprotBloodPressure>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExprotBloodPressure> addcheck = new ArrayList<YhExprotBloodPressure>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExprotBloodPressure>) yhexportdata.allexportbloodpressure(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExprotBloodPressure>) yhexportdata.allexportbloodpressure(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExprotBMI> allexportbmi(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExprotBMI> check = new ArrayList<YhExprotBMI>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExprotBMI> addcheck = new ArrayList<YhExprotBMI>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExprotBMI>) yhexportdata.allexportbmi(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExprotBMI>) yhexportdata.allexportbmi(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExprotBloodFat> allexportbloodfat(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExprotBloodFat> check = new ArrayList<YhExprotBloodFat>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExprotBloodFat> addcheck = new ArrayList<YhExprotBloodFat>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExprotBloodFat>) yhexportdata.allexportbloodfat(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExprotBloodFat>) yhexportdata.allexportbloodfat(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportLiver> allexportliver(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportLiver> check = new ArrayList<YhExportLiver>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportLiver> addcheck = new ArrayList<YhExportLiver>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportLiver>) yhexportdata.allexportliver(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportLiver>) yhexportdata.allexportliver(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportRenalFunction> allexportfunction(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportRenalFunction> check = new ArrayList<YhExportRenalFunction>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportRenalFunction> addcheck = new ArrayList<YhExportRenalFunction>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportRenalFunction>) yhexportdata.allexportfunction(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportRenalFunction>) yhexportdata.allexportfunction(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportUserUBA> allexportuba(YhExportData YhExportUserUBA) {
		String[] arr1=YhExportUserUBA.getSelect().split(",");
		List<YhExportUserUBA> check = new ArrayList<YhExportUserUBA>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		if(YhExportUserUBA.getTime()) {
			YhExportUserUBA.setBegintime(YhExportUserUBA.getTimes().substring(0, 10));
			YhExportUserUBA.setEndtime(YhExportUserUBA.getTimes().substring(13));
		}
		List<YhExportUserUBA> addcheck = new ArrayList<YhExportUserUBA>();
		if(YhExportUserUBA.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				YhExportUserUBA.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportUserUBA>) yhexportdata.allexportuba(YhExportUserUBA);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					YhExportUserUBA.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportUserUBA>) yhexportdata.allexportuba(YhExportUserUBA);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportOther> allother(YhExportData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportOther> check = new ArrayList<YhExportOther>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportOther> addcheck = new ArrayList<YhExportOther>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportOther>) yhexportdata.allother(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportOther>) yhexportdata.allother(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhExportRenalFunction> renal(YhExportData yhexportnow) {
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhExportRenalFunction> check = new ArrayList<YhExportRenalFunction>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhExportRenalFunction> addcheck = new ArrayList<YhExportRenalFunction>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhExportRenalFunction>) yhexportdata.renal(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhExportRenalFunction>) yhexportdata.renal(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public List<YhUserArtery> exportyhUserArteries(YhExportData yhexportnow) {
		String[] arr1=yhexportnow.getSelect().split(",");
		List<YhUserArtery> check = new ArrayList<YhUserArtery>();
		long ismin=Integer.MAX_VALUE;
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//if(yhexportnow.getTime()) {
		yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
		yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));
		//}
		List<YhUserArtery> addcheck = new ArrayList<YhUserArtery>();
		if(yhexportnow.getSelect()!=null) {
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				yhexportnow.setNowselect(ismin+"");
				addcheck=(ArrayList<YhUserArtery>) yhexportdata.exportyhUserArteries(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					yhexportnow.setNowselect(arr1[i]);
					addcheck=(ArrayList<YhUserArtery>) yhexportdata.exportyhUserArteries(yhexportnow);
					check.addAll(addcheck);
				}
			}
		}

		return check;
	}

	@Override
	public YhExportCheck selectexportdateByRs(String rs) {
		// TODO Auto-generated method stub
		return yhexportdata.selectexportdateByRs(rs);
	}

	@Override
	public YhExportCheck findCheckJsonInfoByRs(String rs) {
		// TODO Auto-generated method stub
		return yhexportdata.findCheckJsonInfoByRs(rs);
	}

	@Override
	public List<yhDiseaseAssessTotalReport2> exportassessreport3(String rs) {
		// TODO Auto-generated method stub
		return yhexportdata.exportassessreport3(rs);
	}
}
