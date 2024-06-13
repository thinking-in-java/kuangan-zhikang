/**
 * 
 */
package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhMyLifeHabitAssessResultWebApp;
import com.yihe.system.mapper.YhMyLifeHabitAssessResultWebAppMapper;
import com.yihe.system.service.YhMyLifeHabitAssessResultWebAppService;

/**
 * @author dong
 *
 */
@Service
public class YhMyLifeHabitAssessResultWebAppServiceImpl implements YhMyLifeHabitAssessResultWebAppService{

	
	@Autowired
	YhMyLifeHabitAssessResultWebAppMapper yhMyLifeHabitAssessResultWebAppMapper;

	@Override
	public YhMyLifeHabitAssessResultWebApp selectByResultAssess(Integer dictId) {
		// TODO Auto-generated method stub
		return yhMyLifeHabitAssessResultWebAppMapper.selectByResultAssess(dictId);
	}
	
	
}
