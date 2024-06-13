package com.yihe.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yihe.system.mapper.YhUserDeviceMapper;
import com.yihe.system.domain.EquipmentInfoVOJO;
import com.yihe.system.domain.YhUserDevice;
import com.yihe.system.service.IYhUserDeviceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.common.core.text.Convert;

/**
 * 用户绑定设备Service业务层处理
 * 
 * @author yihe
 * @date 2020-04-20
 */
@Service
public class YhUserDeviceServiceImpl implements IYhUserDeviceService 
{
    @Autowired
    private YhUserDeviceMapper yhUserDeviceMapper;

    /**
     * 查询用户绑定设备
     * 
     * @param terminalid 用户绑定设备ID
     * @return 用户绑定设备
     */
    @Override
    public YhUserDevice selectYhUserDeviceById(int tid)
    {
        return yhUserDeviceMapper.selectYhUserDeviceById(tid);
    }

    /**
     * 查询用户绑定设备列表
     * 
     * @param yhUserDevice 用户绑定设备
     * @return 用户绑定设备
     */
    @Override
    public List<YhUserDevice> selectYhUserDeviceList(YhUserDevice yhUserDevice)
    {
        return yhUserDeviceMapper.selectYhUserDeviceList(yhUserDevice);
    }

    /**
     * 新增用户绑定设备
     * 
     * @param yhUserDevice 用户绑定设备
     * @return 结果
     */
    @Override
    public int insertYhUserDevice(YhUserDevice yhUserDevice)
    {
        return yhUserDeviceMapper.insertYhUserDevice(yhUserDevice);
    }

    /**
     * 修改用户绑定设备
     * 
     * @param yhUserDevice 用户绑定设备
     * @return 结果
     */
    @Override
    public int updateYhUserDevice(YhUserDevice yhUserDevice)
    {
        return yhUserDeviceMapper.updateYhUserDevice(yhUserDevice);
    }

    /**
     * 删除用户绑定设备对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYhUserDeviceByIds(String ids)
    {
        return yhUserDeviceMapper.deleteYhUserDeviceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户绑定设备信息
     * 
     * @param terminalid 用户绑定设备ID
     * @return 结果
     */
    @Override
    public int deleteYhUserDeviceById(String terminalid)
    {
        return yhUserDeviceMapper.deleteYhUserDeviceById(terminalid);
    }

	@Override
	public PageInfo<EquipmentInfoVOJO> findTerMinalByDoctorAndNameAndTid(String usercode, String nameLike, String tid,
			int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<EquipmentInfoVOJO> list = yhUserDeviceMapper.findTerMinalByDoctorAndNameAndTid(usercode, nameLike, tid);
		return new PageInfo<>(list);
	}

	@Override
	public List<EquipmentInfoVOJO> findregInfoBYUsercodeForName(String usercode) {
		// TODO Auto-generated method stub
		return yhUserDeviceMapper.findregInfoBYUsercodeForName(usercode);
	}

	@Override
	public String selectNewDeviceUserCode(String terminalId) {
		// TODO Auto-generated method stub
		return yhUserDeviceMapper.selectNewDeviceUserCode(terminalId);
	}

	@Override
	public List<EquipmentInfoVOJO> selectFullOrgName(String userCode) {

		List<EquipmentInfoVOJO> list = yhUserDeviceMapper.selectAll(userCode);

			
			
		
		return list;
	}

	@Override
	public List<YhUserDevice> selectUserAll(String userCode) {
		// TODO Auto-generated method stub
		return yhUserDeviceMapper.selectUserAll(userCode);
	}

	@Override
	public int deleteYhUserDevice(Integer tid) {
		// TODO Auto-generated method stub
		return yhUserDeviceMapper.deleteYhUserDevice(tid);
	}

	@Override
	public int selectCount(String userCode) {
		// TODO Auto-generated method stub
		return yhUserDeviceMapper.selectCount(userCode);
	}

	@Override
	public PageInfo<YhUserDevice> queryUserYhUserDevicePage(String userCode, int page, int limit,String name,String terminalid) {
		   //设置分页参数
        PageHelper.startPage(page,limit);
        List<YhUserDevice> yhUserDevice = yhUserDeviceMapper.selectUserYhUserDeviceLimitParam(userCode,name,terminalid);
        return new PageInfo<YhUserDevice>(yhUserDevice);
	}

	@Override
	public YhUserDevice selectTerminalId(String terminalId) {
		// TODO Auto-generated method stub
		return yhUserDeviceMapper.selectTerminalId(terminalId);
	}
}
