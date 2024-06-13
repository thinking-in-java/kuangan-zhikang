package com.yihe.applet.api.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserFileApp;
import com.yihe.applet.api.mapper.YhUserFileAppMapper;
import com.yihe.applet.api.service.YhUserFileAppService;
@Service
public class YhUserFileAppServiceImpl implements YhUserFileAppService{
	@Autowired
	YhUserFileAppMapper yhUserFileAppMapper;

	@Override
	public int insertSelective(YhUserFileApp record) {
		// TODO Auto-generated method stub
		return yhUserFileAppMapper.insertSelective(record);
	}

	@Override
	public YhUserFileApp selectByPrimaryKey(Long fileId) {
		// TODO Auto-generated method stub
		return yhUserFileAppMapper.selectByPrimaryKey(fileId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserFileApp record) {
		// TODO Auto-generated method stub
		return yhUserFileAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<YhUserFileApp> selectFileByParam(YhUserFileApp yhUserFile) {
		// TODO Auto-generated method stub
		return yhUserFileAppMapper.selectFileByParam(yhUserFile);
	}

	@Override
	public int delete(Integer fileId) {
		// TODO Auto-generated method stub
		return yhUserFileAppMapper.delete(fileId);
	}

	@Override
	public List<Date> selectfilCreateDate(String userCode) {
		// TODO Auto-generated method stub
		return yhUserFileAppMapper.selectfilCreateDate(userCode);
	}

	@Override
	public List<YhUserFileApp> selectUserFile(Integer page, Integer pageSize, String fileParentFolder,
			String userCode) {
		// TODO Auto-generated method stub
		return yhUserFileAppMapper.selectUserFile(page, pageSize, fileParentFolder, userCode);
	}

	@Override
	public List<YhUserFileApp> selectUserFileByParam(Integer page, Integer pageSize, String fileParentFolder,
			String userCode, String fileName) {
		// TODO Auto-generated method stub
		return yhUserFileAppMapper.selectUserFileByParam(page, pageSize, fileParentFolder, userCode, fileName);
	}
}
