package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserPersonagePharmacy;
import com.yihe.system.mapper.YhUserPersonagePharmacyMapper;
import com.yihe.system.service.YhUserPersonagePharmacyService;
@Service
public class YhUserPersonagePharmacyServiceImpl implements YhUserPersonagePharmacyService{

	@Autowired
	YhUserPersonagePharmacyMapper yhUserPersonagePharmacyMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonagePharmacyMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(YhUserPersonagePharmacy yhUserPersonagePharmacy) {
		// TODO Auto-generated method stub
		return yhUserPersonagePharmacyMapper.insert(yhUserPersonagePharmacy);
	}

	@Override
	public List<YhUserPersonagePharmacy>  selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonagePharmacyMapper.selectByPrimaryKey(userCode);
	}

	@Override
	public int updateByPrimaryKey(YhUserPersonagePharmacy yhUserPersonagePharmacy) {
		// TODO Auto-generated method stub
		
		return yhUserPersonagePharmacyMapper.updateByPrimaryKey(yhUserPersonagePharmacy);
	}
	public static void main(String[] args) {
		boolean isSuccess = false;
		if (!isSuccess) {
			System.out.println("111111111");
		}
	}
}
