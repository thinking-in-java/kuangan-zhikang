package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserFolderWeb;
import com.yihe.system.mapper.YhUserFolderWebMapper;
import com.yihe.system.service.YhUserFolderWebService;
@Service
public class YhUserFolderWebServiceImpl implements YhUserFolderWebService{
	@Autowired
	YhUserFolderWebMapper yhUserFolderWebMapper;
	@Override
	public int deleteByPrimaryKey(Long folderId) {
		// TODO Auto-generated method stub
		return yhUserFolderWebMapper.deleteByPrimaryKey(folderId);
	}

	@Override
	public int insertSelective(YhUserFolderWeb record) {
		// TODO Auto-generated method stub
		return yhUserFolderWebMapper.insertSelective(record);
	}

	@Override
	public YhUserFolderWeb selectByPrimaryKey(Long folderId) {
		// TODO Auto-generated method stub
		return yhUserFolderWebMapper.selectByPrimaryKey(folderId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserFolderWeb record) {
		// TODO Auto-generated method stub
		return yhUserFolderWebMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public PageInfo<YhUserFolderWeb> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
		//设置分页参数
        PageHelper.startPage(page,limit);
        List<YhUserFolderWeb> list = yhUserFolderWebMapper.selectPageByParam(userCode);
		return new PageInfo<YhUserFolderWeb>(list);
	}

	@Override
	public YhUserFolderWeb selectFolderName(String userCode, String folderName) {
		// TODO Auto-generated method stub
		return yhUserFolderWebMapper.selectFolderName(userCode, folderName);
	}

}
