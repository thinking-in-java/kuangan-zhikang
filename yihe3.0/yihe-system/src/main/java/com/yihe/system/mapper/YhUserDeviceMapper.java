package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.EquipmentInfoVOJO;
import com.yihe.system.domain.YhUserDevice;

/**
 * 用户绑定设备Mapper接口
 * 
 * @author yihe
 * @date 2020-04-20
 */
public interface YhUserDeviceMapper 
{
	
	List<EquipmentInfoVOJO> findTerMinalByDoctorAndNameAndTid(@Param("usercode")String usercode,@Param("nameLike")String nameLike,@Param("tid")String tid);
	List<EquipmentInfoVOJO> findregInfoBYUsercodeForName(@Param("usercode")String usercode);
	List<EquipmentInfoVOJO> selectAll(@Param("userCode")String userCode);
    /**
     * 查询用户绑定设备
     * 
     * @param terminalid 用户绑定设备ID
     * @return 用户绑定设备
     */
    public YhUserDevice selectYhUserDeviceById(int tid);
    
    public List<YhUserDevice> selectUserAll(String userCode);
    
    public int selectCount (String userCode);

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
     * 删除用户绑定设备
     * 
     * @param terminalid 用户绑定设备ID
     * @return 结果
     */
    public int deleteYhUserDeviceById(String terminalid);
    
    YhUserDevice selectTerminalId(String terminalId);

    /**
     * 批量删除用户绑定设备
     * 
     * @param terminalids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhUserDeviceByIds(String[] terminalids);
    
    public String selectNewDeviceUserCode(String terminalId);
    
    public int deleteYhUserDevice(Integer tid);
    
    public List<YhUserDevice> selectUserYhUserDeviceLimitParam(@Param("userCode") String userCode,@Param("name")String name,@Param("terminalid")String terminalid);
}
