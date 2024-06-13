package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhHealthLectureComment;

/**
* YhHealthLectureCommentService接口
* Created by dong on 2020/3/28.
*/
public interface YhHealthLectureCommentService {

	
	int findCommentByUserCodeForCount(String usercode);
	
	
	/**
	 * 根据课堂id查询评论次数
	 * @param lectureinfoid
	 * @return
	 */
	int selectByCount(int lectureinfoid);
	
	/**
	 * 一级评论
	 * @param map
	 * @return
	 */
List<YhHealthLectureComment> selectComment(Map<String, Object> map);
    
/**
 * 一级评论总行数
 * @param map
 * @return
 */
    int selectCommentConut(Map<String, Object> map);
    
    /**
     * 二级评论
     * @param map
     * @return
     */
List<YhHealthLectureComment> selectsecondComment(Map<String, Object> map);
    
/**
 * 二级评论总行数
 * @param map
 * @return
 */
    int selectsecondCommentConut(Map<String, Object> map);
    
    int selectnumber(int pid);
    
    int insertSelective(YhHealthLectureComment record);
    
    int deleteByPrimaryKeyPid(int pid);
    
    int deleteByPrimaryKey(Integer commentid);


	  List<YhHealthLectureComment> showHabitExport(Map<String, Object> map);
}