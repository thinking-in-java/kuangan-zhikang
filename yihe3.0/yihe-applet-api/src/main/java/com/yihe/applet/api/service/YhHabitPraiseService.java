package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhHabitPraise;

import java.util.List;
import java.util.Map;

/**
 * 习惯点赞
 * @author Administrator
 *
 */
public interface YhHabitPraiseService {
	/**
	 * 增加点赞数
	 * @param record
	 * @return
	 */
	 int insertSelective(YhHabitPraise record);
	 
	 /**
	  * 点赞总数
	  * @param commentid
	  * @return
	  */
	 int showCountParise(String commentid);
	 
	 /**
	  * 自己是否点赞
	  * @param map
	  * @return
	  */
	 int showCountPariseCode(Map<String, Object> map);
	 
	 /**
	  * 删除点赞
	  * @param map
	  * @return
	  */
	 int deleteByPrimaryKey(Map<String, Object> map);

	/**
	 * 通过点赞内容获取点赞者的code
	 */
	List<YhHabitPraise> selectCodeByCommentId(int id);

	/**
	 * 获取pr_id
	 */
	YhHabitPraise selectprId(YhHabitPraise record);
}
