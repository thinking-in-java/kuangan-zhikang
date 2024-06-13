package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhUserMakeCardLogVOJO;

public interface YhMakeCardInFoService   {
		List<YhUserMakeCardLogVOJO> selectmakekardlog(Integer page,Integer limit,String guishu,String dates);
		int makecard(Integer count,String Org_code,String miaoshu,Integer doctor);
		int selectcount(String guishu,String dates);
}
