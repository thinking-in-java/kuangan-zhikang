package com.yihe.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yihe.system.domain.YhUserReginfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhExportHealthAssess;
import com.yihe.system.domain.YhExportPDFData;
import com.yihe.system.mapper.YhExprotPDFMapper;
import com.yihe.system.service.YhExprotPDFService;

@Service
public class YhExprotPDFServiceImpl implements YhExprotPDFService {
	@Autowired
	YhExprotPDFMapper yhexprotpdfmapper;
	@Override
	public List<YhExportHealthAssess> selectdiseaseriskresult(YhExportPDFData yhexportnow) {
		// TODO Auto-generated method stub
		String[] arr1 = yhexportnow.getSelect().split(",");
		List<YhExportHealthAssess> check = new ArrayList<YhExportHealthAssess>();
		List<YhExportHealthAssess> addcheck = new ArrayList<YhExportHealthAssess>();
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		if (yhexportnow.getSelect() != null) {
			Long ismin=Long.MAX_VALUE;
			for (int i = 0; i < arr1.length; i++) {
				if(ismin>Long.valueOf(arr1[i])) {
					ismin=Long.valueOf(arr1[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				addcheck=null;
				yhexportnow.setNowselect(ismin+"");
				addcheck=yhexprotpdfmapper.selectdiseaseriskresult(yhexportnow);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < arr1.length; i++) {
					addcheck=null;
					yhexportnow.setNowselect(arr1[i]);
					addcheck=yhexprotpdfmapper.selectdiseaseriskresult(yhexportnow);
					check.addAll(addcheck);
				}
			}

		}
		return check;
	}
	@Override
	public List<YhExportHealthAssess> selectdiseasestatistical(YhExportPDFData yhexportnow) {
		// TODO Auto-generated method stub
				String[] arr1 = yhexportnow.getSelect().split(",");
				List<YhExportHealthAssess> check = new ArrayList<YhExportHealthAssess>();
				List<YhExportHealthAssess> addcheck = new ArrayList<YhExportHealthAssess>();
				Subject subject = SecurityUtils.getSubject();
				YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
				if (yhexportnow.getSelect() != null) {
					Long ismin=Long.MAX_VALUE;
					for (int i = 0; i < arr1.length; i++) {
						if(ismin>Long.valueOf(arr1[i])) {
							ismin=Long.valueOf(arr1[i]);
						}
					}
					if(yh.getOrgCode().equals(ismin+"")) {
						addcheck=null;
						yhexportnow.setNowselect(ismin+"");
						addcheck=yhexprotpdfmapper.selectdiseasestatistical(yhexportnow);
						check.addAll(addcheck);
					}else {
						for (int i = 0; i < arr1.length; i++) {
							addcheck=null;
							yhexportnow.setNowselect(arr1[i]);
							addcheck=yhexprotpdfmapper.selectdiseasestatistical(yhexportnow);
							check.addAll(addcheck);
						}
					}

				}
				return check;
	}

}
