package com.yihe.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhExportHealthAssess;
import com.yihe.system.domain.YhSeniorFactorsAnalyze;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhExprotPDFSeniorFactorsAnalyzeMapper;
import com.yihe.system.service.YhExprotPDFSeniorFactorsAnalyzeService;
@Service
public class YhExprotPDFSeniorFactorsAnalyzeServiceImpl implements YhExprotPDFSeniorFactorsAnalyzeService{
	@Autowired
	YhExprotPDFSeniorFactorsAnalyzeMapper analyzemapper;

	@Override
	public List<YhExportHealthAssess> exportfactors(YhSeniorFactorsAnalyze factor) {
		String[] selectnows=factor.getSelect1().split(",");
		if(factor.getTimes()!=null) {
			factor.setBegintime(factor.getTimes().substring(0, 10));
			factor.setEndtime(factor.getTimes().substring(13));	
		}
		List<YhExportHealthAssess> check = new ArrayList<YhExportHealthAssess>();
		List<YhExportHealthAssess> addcheck = new ArrayList<YhExportHealthAssess>();
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		if (factor.getSelect1() != null) {
			Long ismin=Long.MAX_VALUE;
			for (int i = 0; i < selectnows.length; i++) {
				if(ismin>Long.valueOf(selectnows[i])) {
					ismin=Long.valueOf(selectnows[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				addcheck=null;
				factor.setNowselect(ismin+"");
				addcheck=analyzemapper.selectdiseaseriskresult(factor);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < selectnows.length; i++) {
					addcheck=null;
					factor.setNowselect(selectnows[i]);
					addcheck=analyzemapper.selectdiseaseriskresult(factor);
					check.addAll(addcheck);
				}
			}

		}
		return check;
	}

	@Override
	public List<YhExportHealthAssess> selectallcount(YhSeniorFactorsAnalyze factor) {
		String[] selectnows=factor.getSelect1().split(",");
		if(factor.getTimes()!=null) {
			factor.setBegintime(factor.getTimes().substring(0,4));
			factor.setEndtime(factor.getTimes().substring(5));
		}
		List<YhExportHealthAssess> check = new ArrayList<YhExportHealthAssess>();
		List<YhExportHealthAssess> addcheck = new ArrayList<YhExportHealthAssess>();
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		if (factor.getSelect1() != null) {
			Long ismin=Long.MAX_VALUE;
			for (int i = 0; i < selectnows.length; i++) {
				if(ismin>Long.valueOf(selectnows[i])) {
					ismin=Long.valueOf(selectnows[i]);
				}
			}
			if(yh.getOrgCode().equals(ismin+"")) {
				addcheck=null;
				factor.setNowselect(ismin+"");
				addcheck=analyzemapper.selectallcount(factor);
				check.addAll(addcheck);
			}else {
				for (int i = 0; i < selectnows.length; i++) {
					addcheck=null;
					factor.setNowselect(selectnows[i]);
					addcheck=analyzemapper.selectallcount(factor);
					check.addAll(addcheck);
				}
			}

		}
		return check;
	}
}
