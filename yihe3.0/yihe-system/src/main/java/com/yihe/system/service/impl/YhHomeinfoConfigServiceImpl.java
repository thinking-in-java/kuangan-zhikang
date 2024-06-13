/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhHomeinfoConfig;
import com.yihe.system.mapper.YhHomeinfoConfigMapper;
import com.yihe.system.service.YhHomeinfoConfigService;

/**
 * @author dong
 *
 */
@Service
public class YhHomeinfoConfigServiceImpl implements YhHomeinfoConfigService{
	
	@Autowired
    private YhHomeinfoConfigMapper yhHomeinfoConfigMapper;

	@Override
	public List<YhHomeinfoConfig> selectByslideshow() {
		// TODO Auto-generated method stub
		return yhHomeinfoConfigMapper.selectByslideshow();
	}

	@Override
	public int selectByslideshownumber() {
		// TODO Auto-generated method stub
		return yhHomeinfoConfigMapper.selectByslideshownumber();
	}

	@Override
	public List<YhHomeinfoConfig> selectByheadline() {
		// TODO Auto-generated method stub
			return yhHomeinfoConfigMapper.selectByheadline();
	}

	@Override
	public int selectByheadlinenumber() {
		// TODO Auto-generated method stub
		return yhHomeinfoConfigMapper.selectByheadlinenumber();
	}

	@Override
	public int insertSelective(YhHomeinfoConfig record) {
		// TODO Auto-generated method stub
		return yhHomeinfoConfigMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhHomeinfoConfig record) {
		// TODO Auto-generated method stub
		return yhHomeinfoConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhHomeinfoConfigMapper.deleteByPrimaryKey(id);
	}

	@Override
	public YhHomeinfoConfig amendinfotip(int infoTip) {
		// TODO Auto-generated method stub
		return yhHomeinfoConfigMapper.amendinfotip(infoTip);
	}

	@Override
	public String InfoTipMax() {
		// TODO Auto-generated method stub
		return yhHomeinfoConfigMapper.InfoTipMax();
	}

	@Override
	public List<YhHomeinfoConfig> selectByOrder() {
		// TODO Auto-generated method stub
		return yhHomeinfoConfigMapper.selectByOrder();
	}
}
