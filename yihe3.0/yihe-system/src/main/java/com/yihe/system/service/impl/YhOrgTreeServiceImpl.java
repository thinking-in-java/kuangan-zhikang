package com.yihe.system.service.impl;

import com.yihe.system.domain.YhOrgTree;
import com.yihe.system.domain.YhOrgTreeVojo;
import com.yihe.system.mapper.YhOrgTreeMapper;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
* YhOrgTreeService实现
* Created by dong on 2020/3/18.
*/
@Service
@Transactional
public class YhOrgTreeServiceImpl  implements YhOrgTreeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhOrgTreeServiceImpl.class);

    @Autowired
    YhOrgTreeMapper yhOrgTreeMapper;

	@Override
	public List<YhOrgTree> showlistTree() {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.showlistTree();
	}

	@Override
	public List<YhOrgTreeVojo> selectalltree() {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.selectalltree();
	}

	@Override
	public List<YhOrgTree> selectBYLikeCode(String orgCode) {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.selectBYLikeCode(orgCode);
	}

	@Override
	public List<YhOrgTree> selectBYLikeCodeByNew(String orgCode) {
		return yhOrgTreeMapper.selectBYLikeCodeByNew(orgCode);
	}

	@Override
	public YhOrgTree selectBYCodeByNew(String orgCode) {
		return yhOrgTreeMapper.selectBYCodeByNew(orgCode);
	}

	@Override
	public String selectChildnodeMax(String superOrgCode) {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.selectChildnodeMax(superOrgCode);
	}

	@Override
	public List<YhOrgTreeVojo> selectalltreeOrg(String orgCode) {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.selectalltreeOrg(orgCode);
	}

	@Override
	public List<YhOrgTreeVojo> selectalltreeOrg1(String orgCode) {
		return yhOrgTreeMapper.selectalltreeOrg1(orgCode);
	}
	
	//1000010000 --> 1000010000  10000  集团总公司
		//10000  集团总公司 --->10000 0 中国水利水电第八集团有限公司
		//0 中国水利水电第八集团有限公司>集团总公司
		
		//1000010000 --->   中国水利水电第八集团有限公司>集团总公司
		@Override
		public String selectFullOrgName(String orgCode,String orgName) {//0,中国水利水电第八集团有限公司 > 集团总公司
			YhOrgTree yhOrgTree = yhOrgTreeMapper.selectByPrimaryKey(orgCode);//0
			//yhOrgTree --> null
			if(yhOrgTree!=null) {
				String fullOrgName = StringUtil.isEmpty(orgName)
						? yhOrgTree.getOrgName() : String.format("%s>%s", yhOrgTree.getOrgName(),orgName);
				//fullOrgName --> 中国水利水电第八集团有限公司 > 集团总公司
				return selectFullOrgName(yhOrgTree.getSuperOrgCode(), fullOrgName);//
			}
			return orgName;
		}
		
		//传入当前机构的orgcode
		@Override
		public List<String> selectparentOrg(List<String> orgs,String orgCode) {
		
			//根据传入的orgcode查询父级的orgcode
			YhOrgTree yhOrgTree = yhOrgTreeMapper.selectByPrimaryKey(orgCode);
			//如果查询的父级orgcode不为空，把orgcode添加到集合中，否则返回所有orgcode
			if(yhOrgTree!=null) {
				orgs.add(yhOrgTree.getOrgCode());
				return selectparentOrg(orgs,yhOrgTree.getSuperOrgCode());//
			}
			return orgs;
		
			
		}
		
		@Override
	public void selectChildOrg(String superOrgCode,List<YhOrgTreeVojo> list) {
		List<YhOrgTreeVojo> orglist = yhOrgTreeMapper.selectAllChild(superOrgCode);
		for (Iterator<YhOrgTreeVojo> iterator = orglist.iterator(); iterator.hasNext();) {
			YhOrgTreeVojo yhOrgTree = (YhOrgTreeVojo) iterator.next();
			list.add(yhOrgTree);
			selectChildOrg(yhOrgTree.getId(), list);
		}
	}

	@Override
	public List<YhOrgTreeVojo> selectalltreeOrg2(String doctorid) {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.selectalltreeOrg2(doctorid);
	}

	@Override
	public int insertSelective(YhOrgTree record) {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.insertSelective(record);
	}

	@Override
	public YhOrgTree selectByPrimaryKey(String orgCode) {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.selectByPrimaryKey(orgCode);
	}

	@Override
	public YhOrgTree selectorg(String orgCode) {
		return yhOrgTreeMapper.selectorg(orgCode);
	}

	@Override
	public YhOrgTree selectByPrimaryKeyshowinfo(String orgCode) {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.selectByPrimaryKeyshowinfo(orgCode);
	}

	@Override
	public int updateByPrimaryKeySelective(YhOrgTree record) {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public String selectadddate(String cardid) {
		return yhOrgTreeMapper.selectadddate(cardid);
	}

	@Override
	public String selectcardid(String usercode) {
		return yhOrgTreeMapper.selectcardid(usercode);
	}

	@Override
	public String selectlinetime(String cardid) {
		return yhOrgTreeMapper.selectlinetime(cardid);
	}

	@Override
	public int setlasttime(YhOrgTree record) {
		return yhOrgTreeMapper.setlasttime(record);
	}

	@Override
	public YhOrgTreeVojo selectalltreeOrg3(String orgCode) {
		// TODO Auto-generated method stub
		return yhOrgTreeMapper.selectalltreeOrg3(orgCode);
	}

	@Override
	public String queryOaTreeId(String orgCode) {
		return yhOrgTreeMapper.queryOaTreeId(orgCode);
	}

	@Override
	public String queryOaTreeId2(String enrolCardId) {
		return yhOrgTreeMapper.queryOaTreeId2(enrolCardId);
	}
}