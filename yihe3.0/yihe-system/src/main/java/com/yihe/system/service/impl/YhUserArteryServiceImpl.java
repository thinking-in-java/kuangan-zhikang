package com.yihe.system.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserArtery;
import com.yihe.system.mapper.YhUserArteryMapper;
import com.yihe.system.service.YhUserArteryService;
	@Service
public class YhUserArteryServiceImpl implements YhUserArteryService{
	@Autowired
	YhUserArteryMapper yhUserArteryMapper;

		@Override
		public YhUserArtery selectByPrimaryKeyNewest(String usercode) {
			return yhUserArteryMapper.selectByPrimaryKeyNewest(usercode);
		}

		@Override
	public PageInfo<YhUserArtery> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
		  //设置分页参数
        PageHelper.startPage(page,limit);
        List<YhUserArtery> yhUserArtery = yhUserArteryMapper.selectAll(userCode);
        return new PageInfo<YhUserArtery>(yhUserArtery);
	}

	@Override
	public YhUserArtery selectUserYhUserArteryByLimitParm(String userCode) {
		// TODO Auto-generated method stub
		YhUserArtery limitParm = yhUserArteryMapper.selectUserYhUserArteryByLimitParm(userCode);
		return limitParm;
	}

		@Override
		public YhUserArtery findNewInfoByUserCode(String usercode) {
			return yhUserArteryMapper.findNewInfoByUserCode(usercode);
		}

		@Override
		public List<YhUserArtery> findArteryAllByUserCode(String usercode, Integer timetype, String strTime, String endTime) {
			return yhUserArteryMapper.findArteryAllByUserCode(usercode, timetype, strTime, endTime);
		}

		@Override
		public int updateByPrimaryKeySelective(YhUserArtery record) {
			return yhUserArteryMapper.updateByPrimaryKeySelective(record);
		}

		@Override
		public int insertSelective(YhUserArtery record) {
			return yhUserArteryMapper.insertSelective(record);
		}

		@Override
		public List<YhUserArtery> selectByPrimaryKeyFindEntryTime(String usercode) {
			return yhUserArteryMapper.selectByPrimaryKeyFindEntryTime(usercode);
		}

		@Override
		public List<YhUserArtery> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
			return yhUserArteryMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
		}

		@Override
		public YhUserArtery selectByPrimaryKey(Integer id) {
			YhUserArtery yhUserArtery = yhUserArteryMapper.selectByPrimaryKey(id);
			String time = yhUserArtery.getTime();

			SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");

			Date tempDate = null;
			String outTime = null;
			try {
				tempDate = s1.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			outTime = s1.format(tempDate);
			yhUserArtery.setInserttime(outTime);
			return yhUserArtery;
		}
	}
