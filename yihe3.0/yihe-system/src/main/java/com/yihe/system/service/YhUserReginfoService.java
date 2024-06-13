package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.InterveneVOJO;
import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserReginfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * YhUserReginfoService接口 Created by lll on 2020/3/6.
 */
public interface YhUserReginfoService {

    List<YhUserReginfo> selectAllUserinfoByuserCode(String userCode);

    String selectOrgName(String usercode);

    int modifyinterveneByUserCode(String usercode);

    YhUserReginfo findYhUserReginfo(String userCode);

    String selectCardId(YhUserMakeCard yhUserMakeCard);

    YhUserMakeCard selectActivationCardId(String key);

    int modifyinterveneByUserCodeAndGroupInfo(String groupInfo, String usercode);

    InterveneVOJO findinterveneByUserCode(String usercode);

    List<YhUserReginfo> yhuserenrolinfo();

    int insertSelective(YhUserReginfo yhUserReginfo);

    int regSubmitActivation(YhUserReginfo yhUserReginfo);

    String selectMaxUserCode(Integer userType);

    String selectMaxUserCod2();

    YhUserReginfo selectByPrimaryKey(String userCode);

    YhUserReginfo selectMobileAndName(String userName);

    YhUserReginfo selectMobileAndName2(String mobile);

    PageInfo<YhUserReginfo> selectByPrimaryKeyBydoctor(String userCode, int pageNum, int pageSize);

    public Map<String, Object> ajaxUploadExcel(String orgCode, InputStream in, MultipartFile file, HttpServletRequest request) throws Exception;

    public String findMonitorInfoByTimeOrder(String usercode);

    PageInfo<InterveneVOJO> selectByPrimaryKeyBydoctorNew(String usercode, String strTime, String endTime,
                                                          String dgroup, String nameLike, int pageNum, int pageSize);

    public List<YhUserReginfo> exportYhUserReginfoData(String userCode);

    public List<YhUserReginfo> exportYhUserReginfoData1(String[] userCode);

    public YhUserReginfo selectYhUserReginfoData(String userCode);

    int updateByPrimaryKeySelective(YhUserReginfo record);

    int updateByPrimaryKeySelectives(YhUserReginfo record);

    int resetPwd(String userCode, String userPwd);

    public PageInfo<Map<String, Object>> selecthealthRecordManagePageByParam(Map<String, Object> healthMap, int page, int limit);

    public int updateuserStatus(@Param("userCode") String userCode, @Param("lockFlag") String lockFlag);

    YhUserReginfo selectUserPwd(String userCode, String userPwd);

    List<YhUserReginfo> selectPhoneAndName(String mobile, String userName);

    int updatelinetime(String time, String orgcode);

    public List<Map<String, Object>> selecthealthRecordManagePageByParam2(@Param("userCode") String userCode);

    int changefinishidoctor();

    Map<String, Object> findUserInfo(String userCode);

    YhUserReginfo selectByCardNum(String cardNum);
}