package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.EquipmentInfoVOJO;
import com.yihe.system.domain.YhUserDevice;

/**
 * 用户绑定设备Service接口
 * 
 * @author yihe
 * @date 2020-04-20
 */
public interface IYhUserDeviceService 
{
	 YhUserDevice selectTerminalId(String terminalId);
	  public List<YhUserDevice> selectUserAll(String userCode);
	 public List<EquipmentInfoVOJO> selectFullOrgName(String userCode);
	 public String selectNewDeviceUserCode(String terminalId);
	  public int selectCount (String userCode);
	  public PageInfo<YhUserDevice> queryUserYhUserDevicePage(String userCode,int page,int limit,String name,String terminalid);
	List<EquipmentInfoVOJO> findregInfoBYUsercodeForName(@Param("usercode")String usercode);
	
	PageInfo<EquipmentInfoVOJO> findTerMinalByDoctorAndNameAndTid(String usercode,String nameLike,String tid,int pageNum,int pageSize);
    /**
     * 查询用户绑定设备
     * 
     * @param terminalid 用户绑定设备ID
     * @return 用户绑定设备
     */
    public YhUserDevice selectYhUserDeviceById(int tid);
    
    public int deleteYhUserDevice(Integer tid);

    /**
     * 查询用户绑定设备列表
     * 
     * @param yhUserDevice 用户绑定设备
     * @return 用户绑定设备集合
     */
    public List<YhUserDevice> selectYhUserDeviceList(YhUserDevice yhUserDevice);

    /**
     * 新增用户绑定设备
     * 
     * @param yhUserDevice 用户绑定设备
     * @return 结果
     */
    public int insertYhUserDevice(YhUserDevice yhUserDevice);

    /**
     * 修改用户绑定设备
     * 
     * @param yhUserDevice 用户绑定设备
     * @return 结果
     */
    public int updateYhUserDevice(YhUserDevice yhUserDevice);

    /**
     * 批量删除用户绑定设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhUserDeviceByIds(String ids);

    /**
     * 删除用户绑定设备信息
     * 
     * @param terminalid 用户绑定设备ID
     * @return 结果
     */
    public int deleteYhUserDeviceById(String terminalid);
    
    
}
