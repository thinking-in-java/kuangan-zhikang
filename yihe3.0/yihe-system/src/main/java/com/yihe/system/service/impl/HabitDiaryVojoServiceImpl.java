/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.HabitDiaryVojo;
import com.yihe.system.domain.YHUserSportDiaryVOJO;
import com.yihe.system.mapper.YhLifeHabitSignMapper;
import com.yihe.system.service.HabitDiaryVojoService;

/**
 * @author Administrator
 *
 */
@Service
public class HabitDiaryVojoServiceImpl implements HabitDiaryVojoService{
	
	  @Autowired
	  private YhLifeHabitSignMapper yhLifeHabitSignMapper;
	@Override
	public PageInfo<HabitDiaryVojo> selectHabitDiaryUserAll(String usercode, String nameLike,
			String usernameLike, String strTime, String endTime, String standard, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
				PageHelper.startPage(pageNum, pageSize);
				List<HabitDiaryVojo> list = yhLifeHabitSignMapper.selectHabitDiaryUserAll(usercode, nameLike,
						usernameLike, strTime, endTime, standard);
				return new PageInfo<>(list);
	}

}
