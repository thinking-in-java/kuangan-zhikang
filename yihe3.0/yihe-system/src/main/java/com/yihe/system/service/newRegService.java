package com.yihe.system.service;

import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.vo.NewEdicalExaminationRecordsVO;
import com.yihe.system.vo.SignatureConfirmationVO;

import java.util.List;

public interface newRegService {

    Integer selectByUserCodeUnsignedCount(String usercode);

    Integer insertCount(newRegWithBLOBs newRegWithBLOBs);

    List<NewEdicalExaminationRecordsVO> selectByUserCodeUnsigned(String usercode);

    List<newRegWithBLOBs> selectByList( List<Integer> evaluateType);

    List<SignatureConfirmationVO> selectByUser(String usercode);

    int updateByNewMedical(Integer id,Integer index,String type,String img);


    int insertNewReg(newRegWithBLOBs newRegWithBLOBs);

    newRegWithBLOBs selectNewReg(newRegWithBLOBs newRegWithBLOBs);

    List<newRegWithBLOBs> selectRegInfo(newRegWithBLOBs newRegWithBLOBs, int page,int limit);

    List<newRegWithBLOBs> getOrderList();

    List<newRegWithBLOBs> queryLocallyManagedArchives(newRegWithBLOBs newRegWithBLOBs);

    int selectRegCount(newRegWithBLOBs newRegWithBLOBs);

    int updateReg(newRegWithBLOBs newRegWithBLOBs);

    int delete(newRegWithBLOBs newRegWithBLOBs);

    int queryLocallyManagedArchivesCount(newRegWithBLOBs newRegWithBLOBs);
}
