package com.yihe.system.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserMakeCardLogVOJO;

public interface YhMakeCardInFoMapper {
		List<YhUserMakeCardLogVOJO> selectmakekardlog(@Param("begin") Integer begin,@Param("limit") Integer limit,@Param("guishu") String guishu,@Param("begintime") String begintime,@Param("endtime") String endtime);
		List<YhUserMakeCard> selectmakecard(Date data);
		int makelistcard(YhUserMakeCard yhusermakecard);
		int makecardlog(YhUserMakeCardLogVOJO yhusermakecardlog);
		String selectzuida();
		int selectcount(@Param("guishu") String guishu,@Param("begintime") String begintime,@Param("endtime") String endtime);
		Date selectorgadddate(String orgcode);
}
