package com.yihe.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.DoctorChatGroupVOJO;
import com.yihe.system.domain.YHDocGroup;
import com.yihe.system.mapper.YHDocGroupMapper;
import com.yihe.system.service.YHDocGroupService;
import com.yihe.system.utils.Data;
import com.yihe.system.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class YHDocGroupServiceImpl implements YHDocGroupService {

	@Autowired
	YHDocGroupMapper YHDocGroupMapper;
	@Override
	public int deleteByPrimaryKey(Integer uDocgroupId) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.deleteByPrimaryKey(uDocgroupId);
	}

	@Override
	public int insert(YHDocGroup record) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.insert(record);
	}

	@Override
	public int insertSelective(YHDocGroup record) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.insertSelective(record);
	}

	@Override
	public YHDocGroup selectByPrimaryKey(Integer uDocgroupId) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.selectByPrimaryKey(uDocgroupId);
	}

	@Override
	public int updateByPrimaryKeySelective(YHDocGroup record) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YHDocGroup record) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageInfo<YHDocGroup> selectByPrimaryKeyByDoc(String docid, int pagenum, int pagesize,String nikename) {
		// TODO Auto-generated method stub
		 PageHelper.startPage(pagenum, pagesize);
		 List<YHDocGroup> list = YHDocGroupMapper.selectByPrimaryKeyByDoc(docid,nikename);
		 
		 for (YHDocGroup yhDocGroup : list) {
			 Integer getuDocgroupId = yhDocGroup.getuDocgroupId();
			 String ginfo="f"+getuDocgroupId+"_g";
			 int num = YHDocGroupMapper.findEnrolinfoByDoctorInfoForNum(docid,ginfo);
			 
			 yhDocGroup.setUserCount(num);
		}
		 
		 
		return new PageInfo<YHDocGroup>(list);
	}

	@Override
	public int updateByPrimaryKeyByUserCount(YHDocGroup record) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.updateByPrimaryKeyByUserCount(record);
	}

	@Override
	public List<YHDocGroup> selectByPrimaryKeyByDocInfo(String docid) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.selectByPrimaryKeyByDoc(docid,"");
	}

	@Override
	public int updateByPrimaryKeyByUserCountUp(YHDocGroup record) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.updateByPrimaryKeyByUserCountUp(record);
	}

	@Override
	public List<YHDocGroup> findDocGroupInfoByIdList(String idList) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.findDocGroupInfoByIdList(idList);
	}

	@Override
	public int modifyInfoUserCountJianYi(String gid) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.modifyInfoUserCountJianYi(gid);
	}

	@Override
	public List<YHDocGroup> selectAllInfo(String userCode) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.selectAllInfo(userCode);
	}

	@Override
	public PageInfo<DoctorChatGroupVOJO> findGroupInfoByDoctor(String usercode, String gname, int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);
		List<DoctorChatGroupVOJO> list = YHDocGroupMapper.findGroupInfoByDoctor(usercode, gname);
		if(null!=list){
			for (int i = 0; i < list.size(); i++) {
				if(StringUtil.isNotEmpty(list.get(i).getNewest())){
					list.get(i).setNewest(Data.CalculateTime(list.get(i).getNewest()));

				}

			}
		}else{
			list=new ArrayList<>();
		}
		if(pagenum==1){
			DoctorChatGroupVOJO allUserGroupInfo = YHDocGroupMapper.findAllUserGroupInfo(usercode);
			if(StringUtil.isNotEmpty(allUserGroupInfo.getNewest())) {
				allUserGroupInfo.setNewest(Data.CalculateTime(allUserGroupInfo.getNewest()));

			}
			list.add(0,allUserGroupInfo);
		}
		return new PageInfo<DoctorChatGroupVOJO>(list);
	}

	@Override
	public List<YHDocGroup> findGroupListByName(String gname) {
		return YHDocGroupMapper.findGroupListByName(gname);
	}

	@Override
	public Integer findGroupInfoByDisCode(String code) {
		// TODO Auto-generated method stub
		return YHDocGroupMapper.findGroupInfoByDisCode(code);
	}

}
