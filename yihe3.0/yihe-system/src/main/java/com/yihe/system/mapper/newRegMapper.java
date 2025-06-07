package com.yihe.system.mapper;


import com.yihe.system.domain.newReg;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.vo.NewEdicalExaminationRecordsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface newRegMapper {
    int deleteByPrimaryKey(Integer id);
    Integer selectByUserCodeUnsignedCount(String usercode);

    List<NewEdicalExaminationRecordsVO> selectByUserCodeUnsigned(String usercode);

    int insert(newRegWithBLOBs row);

    int insertSelective(newRegWithBLOBs row);

    newRegWithBLOBs selectByPrimaryKey(Integer id);

    List<newRegWithBLOBs> getOrderList();

    int updateByPrimaryKeySelective(newRegWithBLOBs row);

    int insertCount(newRegWithBLOBs newRegWithBLOBs);

    int updateByPrimaryKeyWithBLOBs(newRegWithBLOBs row);

    int updateByPrimaryKey(newReg row);

    List<newRegWithBLOBs> selectRegInfo(newRegWithBLOBs row);

    List<newRegWithBLOBs> queryLocallyManagedArchives(newRegWithBLOBs row);

    int queryLocallyManagedArchivesCount(newRegWithBLOBs row);

    int selectRegCount(newRegWithBLOBs row);

    int updateByNewMedical(@Param("id") int id, @Param("img") String img);

    List<newRegWithBLOBs> selectByList(List<Integer> evaluateType);

}