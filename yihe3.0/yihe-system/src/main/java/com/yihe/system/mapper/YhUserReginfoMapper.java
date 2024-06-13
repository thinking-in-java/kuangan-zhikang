package com.yihe.system.mapper;

import com.yihe.system.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhUserReginfoMapper {

    List<YhUserReginfo> selectAllUserinfoByuserCode(String userCode);

    String selectOrgName(String usercode);

    int showOrgrenCount(String orgCode);

    long countByExample(YhUserReginfoExample example);

    String selectCardId(YhUserMakeCard yhUserMakeCard);

    YhUserMakeCard selectActivationCardId(String key);

    int deleteByExample(YhUserReginfoExample example);

    int deleteByPrimaryKey(String userCode);

    int insert(YhUserReginfo record);

    int insertSelective(YhUserReginfo record);

    List<YhUserReginfo> selectByExample(YhUserReginfoExample example);

    InterveneVOJO findinterveneByUserCode(@Param("usercode") String usercode);

    int modifyinterveneByUserCode(@Param("usercode") String usercode);

    int modifyinterveneByUserCodeAndGroupInfo(@Param("groupInfo") String groupInfo, @Param("usercode") String usercode);

    YhUserReginfo selectByPrimaryKey(String userCode);

    List<YhUserReginfo> selectByPrimaryKeyBydoctor(String userCode);

    List<InterveneVOJO> selectByPrimaryKeyBydoctorNew(@Param("usercode") String usercode, @Param("strTime") String strTime, @Param("endTime") String endTime, @Param("dgroup") String dgroup, @Param("nameLike") String nameLike);

    int updateByExampleSelective(@Param("record") YhUserReginfo record, @Param("example") YhUserReginfoExample example);

    int updateByExample(@Param("record") YhUserReginfo record, @Param("example") YhUserReginfoExample example);

    int updateByPrimaryKeySelective(YhUserReginfo record);

    String selectorgcrd(String usercode);

    int resetPwd(@Param("userCode") String userCode, @Param("userPwd") String userPwd);

    int updateByPrimaryKey(YhUserReginfo record);

    int regSubmit(YhUserReginfo yhUserReginfo);

    int regSubmitActivation(YhUserReginfo yhUserReginfo);

    String selectMaxUserCode(Integer userType);

    String selectMaxUserCode1(Integer userType);

    String selectMaxUserCodeDa(Integer userType);


    String selectMaxUserCode2();

    YhUserReginfo selectMobileAndName(String userName);

    YhUserReginfo selectUserPwd(@Param("userCode") String userCode, @Param("userPwd") String userPwd);

    YhUserReginfo selectMobileAndName2(String mobile);

    List<YhUserReginfo> selectPhoneAndName(@Param("mobile") String mobile, @Param("userName") String userName);

    String selectType(@Param("userCode") String userCode, @Param("userType") Integer userType);

    /**
     * 导入数据
     *
     * @param list
     * @return
     */
    public int importYhUserReginfoData(@Param("Onlist") List<YhUserReginfo> Onlist);

    public int selectRegCardSurplus(@Param("userCode") String userCode, @Param("orgCode") String orgCode);

    public List<YhUserReginfo> selectRegCard(@Param("userCode") String userCode, @Param("orgCode") String orgCode);

    public YhUserReginfo selectYhUserReginfoData(String userCode);

    /**
     * 导出
     *
     * @param userName
     * @return
     */
    public List<YhUserReginfo> exportYhUserReginfoData(String userCode);

    public List<YhUserReginfo> exportYhUserReginfoData1(@Param("userCode") String[] userCode);

    /**
     * 查询最新数据监测时间
     */
    public String findMonitorInfoByTimeOrder(@Param("usercode") String usercode);

    public List<Map<String, Object>> selecthealthRecordManagePageByParam(Map<String, Object> healthMap);

    public List<Map<String, Object>> selecthealthRecordManagePageByParam2(@Param("userCode") String userCode);

    /**
     * 修改用户状态
     *
     * @param userCode
     * @param lockFlag
     * @return
     */
    public int updateuserStatus(@Param("userCode") String userCode, @Param("lockFlag") String lockFlag);

    int showReginfoGenderNan();

    int showReginfoGenderNv();

    int showageLessThirty(Map<String, Object> map);

    int showageThirtyForty(Map<String, Object> map);

    int showageFortyFive(Map<String, Object> map);

    int showageFortyFiveFifty(Map<String, Object> map);

    int showageFiftyFive(Map<String, Object> map);

    int showageFiftyFiveSixty(Map<String, Object> map);

    int showageSixty(Map<String, Object> map);

    int showaddCount();

    int showPersonnum();

    int indexnum(Map<String, Object> map);

    List<YhUserReginfo> showDoctor(Map<String, Object> map);

    int showDoctorCount(Map<String, Object> map);

    int showMobileFalse(Map<String, Object> map);

    int showuserNameFalse(Map<String, Object> map);

    List<YhUserReginfo> showAdministrator(Map<String, Object> map);

    int showAdministratorCount(Map<String, Object> map);

    List<YhUserReginfo> showUser(Map<String, Object> map);

    int showUserCount(Map<String, Object> map);

    List<YhUserReginfo> showOrgCodedoctor(String orgCode);

    List<YhUserReginfo> showOrgCodedoctorFacility(String orgCode);

    // 2021/10/20 添加
    List<YhUserAdminInfo> showOrgCodeDoctorById(String orgCode);

    String findByMoblie(String mobile);

    YhUserReginfo showmobileByUserName(String userName);

    YhUserReginfo showmobile(String mobile);

    YhUserReginfo showCardId(String cardId);

    YhUserReginfo inquire(Map<String, Object> map);

    int selectphone(String mobile);

    int updateByUserCodeNew(YhUserReginfo wellRegUserinfo);

    int showchushen(Map<String, Object> map);

    String selectuser();

    YhUserReginfo findYhUserReginfo(String userCode);

    int setmakecardlinetime(String orgcard);

    String selectlintime(String cardtime);

    int setmakecarlinetimes(@Param("linetime") String linetime, @Param("orgcode") String orgcode);

    int updatelinetime(@Param("time") String time, @Param("orgcode") String orgcode);

    YhUserReginfo showdoctorCode(String userCode);

    List<YhJzService> selecttotalservice();

    Integer updateshidoctor(YhJzService yhjzService);

    List<YhUserReginfo> yhuserenrolinfo();

    YhUserReginfo finddoctorHderer1(String userCode);

    YhUserReginfo selectUserCodeByIdCard(String cardidentity);
}
