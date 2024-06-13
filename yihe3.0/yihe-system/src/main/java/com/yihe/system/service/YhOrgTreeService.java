package com.yihe.system.service;

import com.yihe.system.domain.YhOrgTree;
import com.yihe.system.domain.YhOrgTreeVojo;

import java.util.List;

/**
 * YhOrgTreeService接口
 * Created by dong on 2020/3/18.
 */
public interface YhOrgTreeService {
    /**
     * 查询所有
     *
     * @return
     */
    List<YhOrgTree> showlistTree();

    List<YhOrgTreeVojo> selectalltree();

    /**
     * 根据code查询子节点
     *
     * @param orgCode
     * @return
     */
    List<YhOrgTree> selectBYLikeCode(String orgCode);

    List<YhOrgTree> selectBYLikeCodeByNew(String orgCode);

    YhOrgTree selectBYCodeByNew(String orgCode);

    /**
     * 根据子节点查询下级最大的code
     *
     * @param superOrgCode
     * @return
     */
    String selectChildnodeMax(String superOrgCode);

    /**
     * 机构需要的子节点
     *
     * @param orgCode
     * @return
     */
    List<YhOrgTreeVojo> selectalltreeOrg(String orgCode);

    List<YhOrgTreeVojo> selectalltreeOrg1(String orgCode);

    /**
     * 根据orgCode、orgName获取组织全名
     * 例：10010101 --》 公司1001（顶级组织）>公司100101>公司10010101（当前查询组织）
     *
     * @param orgCode     当前
     * @param orgFullName
     */
    public String selectFullOrgName(String orgCode, String orgName);

    public List<String> selectparentOrg(List<String> orgs, String orgCode);

    /**
     * 根据父节点查询所有子节点
     *
     * @param superOrgCode
     * @param list
     * @return
     */
    public void selectChildOrg(String superOrgCode, List<YhOrgTreeVojo> list);

    List<YhOrgTreeVojo> selectalltreeOrg2(String doctorid);

    YhOrgTreeVojo selectalltreeOrg3(String orgCode);

    int insertSelective(YhOrgTree record);

    YhOrgTree selectByPrimaryKey(String orgCode);

    YhOrgTree selectorg(String orgCode);

    YhOrgTree selectByPrimaryKeyshowinfo(String orgCode);

    int updateByPrimaryKeySelective(YhOrgTree record);

    String selectadddate(String cardid);

    String selectcardid(String usercode);

    String selectlinetime(String cardid);

    int setlasttime(YhOrgTree record);

    String queryOaTreeId(String orgCode);

    String queryOaTreeId2(String enrolCardId);
}