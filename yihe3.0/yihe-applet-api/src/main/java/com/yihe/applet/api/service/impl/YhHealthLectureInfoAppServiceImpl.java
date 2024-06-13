package com.yihe.applet.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.applet.api.domain.YhHealthLectureInfoApp;
import com.yihe.applet.api.mapper.YhHealthLectureInfoAppMapper;
import com.yihe.applet.api.service.YhHealthLectureInfoAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class YhHealthLectureInfoAppServiceImpl implements YhHealthLectureInfoAppService{
	@Autowired
	YhHealthLectureInfoAppMapper yhHealthLectureInfoAppMapper;

	@Override
	public YhHealthLectureInfoApp selectByPrimaryKey(Integer infoid) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoAppMapper.selectByPrimaryKey(infoid);
	}

	@Override
	public List<YhHealthLectureInfoApp> selectByExample(YhHealthLectureInfoApp example) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoAppMapper.selectByExample(example);
	}



	@Override
	public List<YhHealthLectureInfoApp> selectByExampleLimit3(Integer categoryId, Integer curPage, Integer pageSize,
			String userCode, Integer type, Integer timeOrder, String title,List<String> orgList) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoAppMapper.selectByExampleLimit3(categoryId, curPage, pageSize, userCode, type, timeOrder, title,orgList);
	}

	@Override
	public List<YhHealthLectureInfoApp> selectByExampleLimitParent(Integer categoryId, Integer curPage,
			Integer pageSize,String userCode,Integer type,Integer timeOrder,String title,List<String> orgList) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoAppMapper.selectByExampleLimitParent(categoryId, curPage, pageSize,userCode,type,timeOrder,title,orgList);
	}

	@Override
	public List<YhHealthLectureInfoApp> findInfoLimitNew3() {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoAppMapper.findInfoLimitNew3();
	}

	@Override
	public List<YhHealthLectureInfoApp> selectByExampleLimitAndLike(String like, Integer curPage, Integer pageSize) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoAppMapper.selectByExampleLimitAndLike(like, curPage, pageSize);
	}

	@Override
	public PageInfo<YhHealthLectureInfoApp> findhealthLectureTitleAndTipsAndInstruction(String likeValue, int pageNum,
			int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhHealthLectureInfoApp> list = yhHealthLectureInfoAppMapper
				.findhealthLectureTitleAndTipsAndInstruction(likeValue);
		for (int i = 0; i < list.size(); i++) {
			String tips = list.get(i).getTips();
			String title = list.get(i).getTitle();
			if (null != title) {
				/*if (title.length() > 10) {
					list.get(i).setTitle(title.substring(0, 9) + "...");
				}*/
			}
			if (null != tips && !"".equals(tips)) {
				/*String[] split = tips.split(",");
				list.get(i).setTipsStr(split);*/
				list.get(i).setTips(tips);
			}
			
		}
		return new PageInfo<YhHealthLectureInfoApp>(list);
	}

	@Override
	public List<YhHealthLectureInfoApp> selectByExampleLimitParentComponents(String cateName,String usercode,Integer timeOrder, String title, Integer type, Integer categoryId, Integer curPage, Integer pageSize) {
		return yhHealthLectureInfoAppMapper.selectByExampleLimitParentComponents(cateName,usercode,timeOrder, title, type, categoryId, curPage, pageSize);
	}

	@Override
	public List<YhHealthLectureInfoApp> selectByExampleLimit3Components(String cateName,String usercode,Integer timeOrder, String title, Integer type, Integer categoryId, Integer curPage, Integer pageSize) {
		return yhHealthLectureInfoAppMapper.selectByExampleLimit3Components(cateName,usercode,timeOrder, title, type, categoryId, curPage, pageSize);
	}

	@Override
	public List<YhHealthLectureInfoApp> findHealthLectureInfoByFoodClass(int categoryId) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoAppMapper.findHealthLectureInfoByFoodClass(categoryId);
	}

	@Override
	public PageInfo<YhHealthLectureInfoApp> findhealthLectureByFoodSchool(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhHealthLectureInfoApp> list = yhHealthLectureInfoAppMapper
				.findhealthLectureByFoodSchool();
		
		return  new PageInfo<YhHealthLectureInfoApp>(list);
	}

	@Override
	public List<YhHealthLectureInfoApp> selectByExampleLimit4(Integer categoryId, Integer curPage, Integer pageSize) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoAppMapper.selectByExampleLimit4(categoryId, curPage, pageSize);
	}

	

	
}
