package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhDicIntroduction;

public interface YhUserPageSettingMapper {
	List<YhDicIntroduction> selectallduction();
	int updateyhdicintroduction(YhDicIntroduction yhdicdu);
	String getimg(Integer id);
}
