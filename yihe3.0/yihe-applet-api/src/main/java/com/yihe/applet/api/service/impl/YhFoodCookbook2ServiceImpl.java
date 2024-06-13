package com.yihe.applet.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.yihe.system.domain.YhFoodCookBook;
import com.yihe.system.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhFoodCookbook2;
import com.yihe.applet.api.mapper.YhFoodCookbook2Mapper;
import com.yihe.applet.api.service.YhFoodCookbook2Service;
@Service
public class YhFoodCookbook2ServiceImpl implements YhFoodCookbook2Service {

	@Autowired
	YhFoodCookbook2Mapper  YhFoodCookbook2Mapper;
	
	@Override
	public List<YhFoodCookbook2> selectFoodCookBookName(String foodName, String usercode, Integer curPage,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.selectFoodCookBookName(foodName, usercode, curPage, pageSize);
	}

	@Override
	public List<YhFoodCookbook2> findFoodCookCollectionByUserCodeForFoodInfo(String usercode) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.findFoodCookCollectionByUserCodeForFoodInfo(usercode);
	}

	@Override
	public List<YhFoodCookbook2> findFoodCookBookInfoByUserCodeAndDescCommon(String usercode) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.findFoodCookBookInfoByUserCodeAndDescCommon(usercode);
	}

	@Override
	public int deleteByPrimaryKey(Integer fCId) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.deleteByPrimaryKey(fCId);
	}

	@Override
	public int insert(YhFoodCookbook2 record) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.insert(record);
	}

	@Override
	public int insertSelective(YhFoodCookbook2 record) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.insertSelective(record);
	}

	@Override
	public YhFoodCookbook2 selectByPrimaryKey(Integer fCId) { 
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.selectByPrimaryKey(fCId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhFoodCookbook2 record) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhFoodCookbook2 record) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.updateByPrimaryKey(record);
	}

	@Override
	public int findFoodCookBookAllNumByType(Integer fcid) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.findFoodCookBookAllNumByType(fcid);
	}

	@Override
	public int findFoodCookBookNumByTypeGTFood(Integer fcid) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.findFoodCookBookNumByTypeGTFood(fcid);
	}

	@Override
	public String findFoodInfoByIDForName(String id) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.findFoodInfoByIDForName(id);
	}

	@Override
	public YhFoodCookbook2 selectBYshowName(String fCName) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.selectBYshowName(fCName);
	}


	@Override
	public List<YhFoodCookbook2> findZuiJinEat(String usercode) {
		List<YhFoodCookbook2> books=new ArrayList<>();

		for (int i = 0; i < 15; i++) {
			List<String> zuiJinEat = YhFoodCookbook2Mapper.findZuiJinEat(usercode, i);
			if(null!=zuiJinEat&&zuiJinEat.size()>0&&books.size()<15){
				for (int j = zuiJinEat.size()-1; j >= 0; j--) {
					String s = zuiJinEat.get(j);
					if(StringUtil.isNotEmpty(s)){
						String[] s1 = s.split("_");
						String s2 = s1[2];
						YhFoodCookbook2 book=YhFoodCookbook2Mapper.selectByPrimaryKey(Integer.valueOf(s2));
						if(null==book){
							continue;
						}
						boolean b = books.stream().anyMatch(element -> element.getfCName().equals(book.getfCName()));
						if(!b){
							books.add(book);
						}

					}
				}

			}
		}


		return books;
	}

	@Override
	public List<String> findUserFoodDiaryAllInfo(String usercode) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.findUserFoodDiaryAllInfo(usercode);
	}

	@Override
	public List<YhFoodCookbook2> findFoodName(String fCName) {
		// TODO Auto-generated method stub
		return YhFoodCookbook2Mapper.findFoodName(fCName);
	}

}
