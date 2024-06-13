package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserFolderApp;
import com.yihe.applet.api.mapper.YhUserFolderAppMapper;
import com.yihe.applet.api.service.YhUserFolderAppService;
@Service
public class YhUserFolderAppServiceImpl implements YhUserFolderAppService{

	@Autowired
	YhUserFolderAppMapper  yhUserFolderAppMapper;
	@Override
	public int deleteByPrimaryKey(Long folderId) {
		// TODO Auto-generated method stub
		return yhUserFolderAppMapper.deleteByPrimaryKey(folderId);
	}

	@Override
	public int insertSelective(YhUserFolderApp record) {
		// TODO Auto-generated method stub
		return yhUserFolderAppMapper.insertSelective(record);
	}

	@Override
	public YhUserFolderApp selectByPrimaryKey(Long folderId) {
		// TODO Auto-generated method stub
		return yhUserFolderAppMapper.selectByPrimaryKey(folderId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserFolderApp record) {
		// TODO Auto-generated method stub
		return yhUserFolderAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<YhUserFolderApp> selectYhUserFolderLimitParent(String userCode, Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
		return yhUserFolderAppMapper.selectYhUserFolderLimitParent(userCode, page, pageSize);
	}

	@Override
	public List<YhUserFolderApp> selectYhUserFolderByparam(String userCode, String folderName, Integer page,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return yhUserFolderAppMapper.selectYhUserFolderByparam(userCode, folderName, page, pageSize);
	}

}
