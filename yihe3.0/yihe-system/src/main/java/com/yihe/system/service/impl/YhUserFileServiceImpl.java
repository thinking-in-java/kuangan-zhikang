package com.yihe.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserFile;
import com.yihe.system.domain.YhUserFolderWeb;
import com.yihe.system.mapper.YhUserFileMapper;
import com.yihe.system.service.YhUserFileService;
/**
 * 附件上传实现类
 * @author 阳雄
 *2020年6月1日16:46:23
 */
@Service
public class YhUserFileServiceImpl implements YhUserFileService{
	@Autowired
	YhUserFileMapper yhUserFileMapper;

	@Override
	public List<Date> selectfilCreateDate(String userCode) {
		// TODO Auto-generated method stub
		return yhUserFileMapper.selectfilCreateDate(userCode);
	}

	@Override
	public List<YhUserFile> selectFileByParam(YhUserFile yhUserFile) {
		// TODO Auto-generated method stub
		return yhUserFileMapper.selectFileByParam(yhUserFile);
	}

	@Override
	public PageInfo<YhUserFile> queryUserByCriteriaWithPage(String userCode, String fileParentFolder, int page,
			int limit) {
		//设置分页参数
        PageHelper.startPage(page,limit);
        List<YhUserFile> list = yhUserFileMapper.selectPageByParam(userCode, fileParentFolder);
		return new PageInfo<YhUserFile>(list);
	}

	@Override
	public int deleteByPrimaryKey(Long fileId) {
		// TODO Auto-generated method stub
		return yhUserFileMapper.deleteByPrimaryKey(fileId);
	}

	@Override
	public YhUserFile selectFileName(String fileName, String userCode) {
		// TODO Auto-generated method stub
		return yhUserFileMapper.selectFileName(fileName, userCode);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserFile record) {
		// TODO Auto-generated method stub
		return yhUserFileMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(YhUserFile record) {
		// TODO Auto-generated method stub
		return yhUserFileMapper.insertSelective(record);
	}

	@Override
	public YhUserFile selectByPrimaryKey(Long fileId) {
		// TODO Auto-generated method stub
		return yhUserFileMapper.selectByPrimaryKey(fileId);
	}
}
