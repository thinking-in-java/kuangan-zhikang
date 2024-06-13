/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.InterventionAdjustment;
import com.yihe.system.domain.SportInterveneVojo;
import com.yihe.system.domain.YhSportRecommendRecord;
import com.yihe.system.domain.YhSportRecommendRecordVojo;
import com.yihe.system.mapper.YhSportRecommendRecordMapper;
import com.yihe.system.service.YhSportRecommendRecordService;

/**
 * @author Administrator
 *
 */
@Service
public class YhSportRecommendRecordServiceImpl implements YhSportRecommendRecordService{

	 @Autowired
	 YhSportRecommendRecordMapper yhSportRecommendRecordMapper;

	@Override
	public List<YhSportRecommendRecord> selectPersonageSport(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.selectPersonageSport(map);
	}

	@Override
	public int selectPersonageSportConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.selectPersonageSportConut(map);
	}

	@Override
	public int insertSelective(YhSportRecommendRecord record) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.insertSelective(record);
	}

	@Override
	public List<YhSportRecommendRecord> seelctCode(String userCode) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.seelctCode(userCode);
	}

	@Override
	public int updateByPrimaryKeySelective(YhSportRecommendRecord record) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int seelctCodeCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.seelctCodeCount(map);
	}
	
	@Override
	public List<YhSportRecommendRecordVojo> showSportRecommended(String userCode) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.showSportRecommended(userCode);
	}

	@Override
	public YhSportRecommendRecord selectByRecord(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.selectByRecord(map);
	}

	@Override
	public int insertYhSportRecommendRecord(YhSportRecommendRecord yhSportRecommendRecord) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.insertYhSportRecommendRecord(yhSportRecommendRecord);
	}

	@Override
	public int uptYhSportRecommendRecord(YhSportRecommendRecord yhSportRecommendRecord) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.uptYhSportRecommendRecord(yhSportRecommendRecord);
	}

	@Override
	public int selYhSportRecordCount(YhSportRecommendRecord yhSportRecommendRecord) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.selYhSportRecordCount(yhSportRecommendRecord);
	}

	@Override
	public YhSportRecommendRecord selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<YhSportRecommendRecordVojo> showfitness() {
		// TODO Auto-generated method stub
		return yhSportRecommendRecordMapper.showfitness();
	}

	@Override
	public PageInfo<SportInterveneVojo> SportInterveneInfo(Map<String, Object> map,Integer pageNum,Integer pageSize) {
		// TODO Auto-generated method stub
				PageHelper.startPage(pageNum, pageSize);
				List<SportInterveneVojo> list = yhSportRecommendRecordMapper.SportInterveneInfo(map);
				return new PageInfo<SportInterveneVojo>(list);
	}
}
