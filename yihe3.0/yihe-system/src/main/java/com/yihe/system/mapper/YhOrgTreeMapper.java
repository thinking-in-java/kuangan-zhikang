package com.yihe.system.mapper;

import com.yihe.system.domain.YhOrgTree;
import com.yihe.system.domain.YhOrgTreeExample;
import com.yihe.system.domain.YhOrgTreeVojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhOrgTreeMapper {
    long countByExample(YhOrgTreeExample example);

    int deleteByExample(YhOrgTreeExample example);

    int deleteByPrimaryKey(String orgCode);

    int insert(YhOrgTree record);

    int insertSelective(YhOrgTree record);

    List<YhOrgTree> selectByExampleWithBLOBs(YhOrgTreeExample example);

    List<YhOrgTree> selectByExample(YhOrgTreeExample example);

    YhOrgTree selectByPrimaryKey(String orgCode);
    YhOrgTree selectorg(String orgCode);

    YhOrgTree selectByPrimaryKeyshowinfo(String orgCode);

    int updateByExampleSelective(@Param("record") YhOrgTree record, @Param("example") YhOrgTreeExample example);

    int updateByExampleWithBLOBs(@Param("record") YhOrgTree record, @Param("example") YhOrgTreeExample example);

    int updateByExample(@Param("record") YhOrgTree record, @Param("example") YhOrgTreeExample example);

    int updateByPrimaryKeySelective(YhOrgTree record);

    int updateByPrimaryKeyWithBLOBs(YhOrgTree record);

    int updateByPrimaryKey(YhOrgTree record);
    
    List<YhOrgTree> showlistTree();
    
    List<YhOrgTreeVojo> selectalltree();
    
    List<YhOrgTree> selectBYLikeCode(String orgCode);

    List<YhOrgTree> selectBYLikeCodeByNew(String orgCode);

    YhOrgTree selectBYCodeByNew(String orgCode);

    String selectChildnodeMax(String superOrgCode);
    
    List<YhOrgTreeVojo> selectalltreeOrg(String orgCode);
    List<YhOrgTreeVojo> selectalltreeOrg1(String orgCode);

    /**
     * 查询当前机构以及所有子机构
     * @param orgCode 机构编号
     * @return List<String>
     */
    List<String> selectallOrgLowerLevel(String orgCode);

    List<YhOrgTreeVojo> selectalltreeOrg2(String doctorid);
    
    YhOrgTreeVojo selectalltreeOrg3(String orgCode);
    
    List<YhOrgTreeVojo> selectAllChild(String superOrgCode);
    
    List<YhOrgTree> showCodeByOrg(String superOrgCode);
    
    YhOrgTree showNameByOrg(String orgName);
    
    List<YhOrgTree> showprovincial(String orgCode);
    
    List<YhOrgTree> showCity(String name);
    
    List<YhOrgTree> showCounty(String name);
    
    List<YhOrgTree> indexprovince(String province);
    
    String showAddDate(String orgCode);

    String getOrgNameInfo(@Param("orgName") String orgName);

    int updateOrg(String info);

    String selectadddate(String cardid);

    String selectcardid(String usercode);

    String selectlinetime(String cardid);

    int setlasttime(YhOrgTree record);

    String queryOaTreeId(String orgCode);

    String queryOaTreeId2(String enrolCardId);
}