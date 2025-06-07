package com.yihe.system.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yihe.system.domain.NewEmployeePersonalInformationCard;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewEmployeePersonalInformationCardMapper;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.mapper.newRegMapper;
import com.yihe.system.service.newRegService;
import com.yihe.system.utils.CommonUtils;
import com.yihe.system.utils.StringUtil;
import com.yihe.system.vo.NewEdicalExaminationRecordsVO;
import com.yihe.system.vo.Notice;
import com.yihe.system.vo.NoticeMain;
import com.yihe.system.vo.SignatureConfirmationVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class newRegServiceImpl implements newRegService {
    @Resource
    newRegMapper newRegMapper;
    @Resource
    YhUserReginfoMapper yhUserReginfoMapper;
    @Resource
    NewEmployeePersonalInformationCardMapper cardMapper;

    @Override
    public Integer selectByUserCodeUnsignedCount(String usercode) {

        Integer num=0;

        List<NewEmployeePersonalInformationCard> newEmployeePersonalInformationCards = cardMapper.selectByUser(usercode);

        if (CommonUtils.isEmpty(newEmployeePersonalInformationCards)) {
            return num;
        }


        List<SignatureConfirmationVO> v1=new ArrayList<>();
        List<SignatureConfirmationVO> v2=new ArrayList<>();


        for (int i = 0; i < newEmployeePersonalInformationCards.size(); i++) {
            NewEmployeePersonalInformationCard vo = newEmployeePersonalInformationCards.get(i);

            String pre = vo.getPreviousOccupationalHealthExaminationResults();

            String exa = vo.getExaminationResults();



            if (StringUtil.isNotEmpty(pre)) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    // 如果你知道具体的类型，也可以直接转换
                    v1 = mapper.readValue(pre, new TypeReference<List<SignatureConfirmationVO>>() {
                    });

                    if (CommonUtils.isEmpty(v1)) {
                        continue;
                    }

                    for (int j = 0; j < v1.size(); j++) {
                        SignatureConfirmationVO signatureConfirmationVO = v1.get(j);
                        String add8 = signatureConfirmationVO.getAdd8();
                        if(StringUtil.isEmpty(add8)){
                            num++;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (StringUtil.isNotEmpty(exa)) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    // 如果你知道具体的类型，也可以直接转换
                    v2 = mapper.readValue(exa, new TypeReference<List<SignatureConfirmationVO>>(){});

                    if (CommonUtils.isEmpty(v2)) {
                        continue;
                    }
                    for (int j = 0; j < v2.size(); j++) {
                        SignatureConfirmationVO signatureConfirmationVO = v2.get(j);
                        String add10 = signatureConfirmationVO.getAdd10();
                        if(StringUtil.isEmpty(add10)){
                            num++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        return num;
    }

    @Override
    public Integer insertCount(newRegWithBLOBs newRegWithBLOBs) {
        return newRegMapper.insertCount(newRegWithBLOBs);
    }

    @Override
    public List<NewEdicalExaminationRecordsVO> selectByUserCodeUnsigned(String usercode) {
        List<NewEdicalExaminationRecordsVO> list = newRegMapper.selectByUserCodeUnsigned(usercode);
        if (null != list && list.size() > 0) {
            list.get(0).setHidden(true);
        }
        return list;
    }

    @Override
    public List<newRegWithBLOBs> selectByList(List<Integer> evaluateType) {
        return newRegMapper.selectByList(evaluateType);
    }

    @Override
    public List<SignatureConfirmationVO> selectByUser(String usercode) {
        List<SignatureConfirmationVO> list = new ArrayList<>();
        List<NewEmployeePersonalInformationCard> newEmployeePersonalInformationCards = cardMapper.selectByUser(usercode);

        if (CommonUtils.isEmpty(newEmployeePersonalInformationCards)) {
            return list;
        }

        List<SignatureConfirmationVO> v1=new ArrayList<>();
        List<SignatureConfirmationVO> v2=new ArrayList<>();
        List<SignatureConfirmationVO> v3=new ArrayList<>();

        for (int i = 0; i < newEmployeePersonalInformationCards.size(); i++) {
            NewEmployeePersonalInformationCard newEmployeePersonalInformationCard = newEmployeePersonalInformationCards.get(i);
            Integer id = newEmployeePersonalInformationCard.getId();

            String pre = newEmployeePersonalInformationCard.getPreviousOccupationalHealthExaminationResults();

            String exa = newEmployeePersonalInformationCard.getExaminationResults();


            if (StringUtil.isNotEmpty(pre)) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    // 如果你知道具体的类型，也可以直接转换
                   v1 = mapper.readValue(pre, new TypeReference<List<SignatureConfirmationVO>>(){});

                    if (CommonUtils.isEmpty(v1)) {
                        continue;
                    }
                    for (int j = 0; j < v1.size(); j++) {
                        SignatureConfirmationVO signatureConfirmationVO = v1.get(j);
                        signatureConfirmationVO.setId(id);
                        signatureConfirmationVO.setType("0");
                        signatureConfirmationVO.setIndex(j);
                        signatureConfirmationVO.setLable1("检查日期");
                        signatureConfirmationVO.setLable2("检测种类");
                        signatureConfirmationVO.setLable3("检查机构");
                        signatureConfirmationVO.setLable4("检查结论");
                        signatureConfirmationVO.setLable5("岗位");
                        signatureConfirmationVO.setLable6("体检针对的职业病危害因素名称");
                        signatureConfirmationVO.setLable7("人员处理情况");
                        signatureConfirmationVO.setLable8("本人签字（告知）");
                        signatureConfirmationVO.setLable9("现场处理情况");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                list.addAll(v1);

            }
            if (StringUtil.isNotEmpty(exa)) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    // 如果你知道具体的类型，也可以直接转换
                      v2 = mapper.readValue(exa, new TypeReference<List<SignatureConfirmationVO>>(){});

                    if (CommonUtils.isEmpty(v2)) {
                        continue;
                    }
                    for (int j = 0; j < v2.size(); j++) {
                        SignatureConfirmationVO signatureConfirmationVO = v2.get(j);
                        signatureConfirmationVO.setId(id);
                        signatureConfirmationVO.setType("1");
                        signatureConfirmationVO.setIndex(j);
                        signatureConfirmationVO.setLable1("罹患职业病名称");
                        signatureConfirmationVO.setLable2("诊断机构名称");
                        signatureConfirmationVO.setLable3("诊断日期");
                        signatureConfirmationVO.setLable4("鉴定结果（期、级别）");
                        signatureConfirmationVO.setLable5("伤残鉴定机构名称");
                        signatureConfirmationVO.setLable6("伤残鉴定日期");
                        signatureConfirmationVO.setLable7("调离原工作");
                        signatureConfirmationVO.setLable8("治疗");
                        signatureConfirmationVO.setLable9("病退/病休");
                        signatureConfirmationVO.setLable10("告知本人（签字）");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                list.addAll(v2);
            }



        }
        if(!CollectionUtils.isEmpty(list)){
            list.get(0).setHidden(true);
        }


        return list;
    }

    @Override
    public int updateByNewMedical(Integer id,Integer index, String type, String img) {


        if (null == id ||null == index || StringUtil.isEmpty(img) || StringUtil.isEmpty(type)) {
            throw new IllegalArgumentException(String.format("参数异常:id:%s,img:%s", id, img));
        }
        NewEmployeePersonalInformationCard card = cardMapper.selectByPrimaryKey(id);

        if(null==card){
            return 0;
        }


        if("0".equals(type)){

            String pre = card.getPreviousOccupationalHealthExaminationResults();

            ObjectMapper mapper = new ObjectMapper();
            try {
                // 将JSON字符串转换为JsonNode
                ArrayNode jsonArray = (ArrayNode) mapper.readTree(pre);

                // 检查数组长度是否至少有两个元素
                if (jsonArray.size() > 0) {
                    // 获取第二个对象（索引为1）
                    JsonNode secondObject = jsonArray.get(index);
                    if (secondObject.isObject()) {
                        // 修改add8字段
                        ((ObjectNode) secondObject).put("add8", img);

                        // 获取notice对象
                        JsonNode noticeNode = secondObject.get("notice");
                        if (noticeNode.isObject()) {
                            // 修改notice对象内的v19字段
                            ((ObjectNode) noticeNode).put("v19", img);
                        }

                    }

                }

                // 将修改后的JsonNode转换回JSON字符串
                String modifiedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonArray);
                System.out.println(modifiedJsonString);
                card.setPreviousOccupationalHealthExaminationResults(modifiedJsonString);
                card.setSign(checkSign(modifiedJsonString,"add8"));



                NewEmployeePersonalInformationCard card1=new NewEmployeePersonalInformationCard();
                card1.setId(id);
                card1.setSign(checkSign(modifiedJsonString,"add8"));
                card1.setPreviousOccupationalHealthExaminationResults(modifiedJsonString);

                cardMapper.updateByPrimaryKeySelective(card1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 1;
        }

        if("1".equals(type)){
            String exa = card.getExaminationResults();

            ObjectMapper mapper = new ObjectMapper();
            try {
                // 将JSON字符串转换为JsonNode
                ArrayNode jsonArray = (ArrayNode) mapper.readTree(exa);

                // 检查数组长度是否至少有两个元素
                if (jsonArray.size() > 0) {
                    // 获取第二个对象（索引为1）
                    JsonNode secondObject = jsonArray.get(index);
                    if (secondObject.isObject()) {
                        // 修改add8字段
                        ((ObjectNode) secondObject).put("add10", img);
                    }
                }

                // 将修改后的JsonNode转换回JSON字符串
                String modifiedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonArray);
                System.out.println(modifiedJsonString);
                card.setPreviousOccupationalHealthExaminationResults(modifiedJsonString);

                card.setExaminationResultsSign(checkSign(modifiedJsonString,"add10"));

                NewEmployeePersonalInformationCard card1=new NewEmployeePersonalInformationCard();
                card1.setId(id);
                card1.setExaminationResultsSign(checkSign(modifiedJsonString,"add10"));
                card1.setExaminationResults(modifiedJsonString);

                cardMapper.updateByPrimaryKeySelective(card1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 1;
        }

        if("2".equals(type)){
            String noti = card.getNoticeOccupationalHealthExaminationResults();

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                NoticeMain noticeMain = objectMapper.readValue(noti, NoticeMain.class);
                if(null!=noticeMain){
                    noticeMain.setV19(img);
                    String jsonOutput = objectMapper.writeValueAsString(noticeMain);
                    NewEmployeePersonalInformationCard card1=new NewEmployeePersonalInformationCard();
                    card1.setId(id);
                    card1.setNoticeOccupationalHealthExaminationResults(jsonOutput);
                    cardMapper.updateByPrimaryKeySelective(card1);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return 1;
    }

    private String checkSign(String json,String column){
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 将JSON字符串转换为JsonNode
            JsonNode jsonArray = mapper.readTree(json);

            // 检查每个对象的add8字段
            boolean allAdd8NonNull = true;
            for (JsonNode jsonObject : jsonArray) {
                JsonNode add8Node = jsonObject.get(column);
                if (add8Node == null || add8Node.isNull() || add8Node.asText().isEmpty()) {
                    allAdd8NonNull = false;
                    break;
                }
            }
            // 根据检查结果返回值
          return allAdd8NonNull ? "1" : "2";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "2";
    }

    public static void main(String[] args) {
        String jsonString = "[{\"add1\":\"2024-10-02\",\"add2\":\"上岗前\",\"add3\":\"1\",\"add4\":\"未见异常\",\"add5\":\"4\",\"add6\":\"5\",\"add7\":\"—\",\"add9\":\"5\",\"add8\":\"https://inews.gtimg.com/om_bt/Os3eJ8u3SgB3Kd-zrRRhgfR5hUvdwcVPKUTNO6O7sZfUwAA/641\"},{\"add1\":\"2024-10-27\",\"add2\":\"离岗后\",\"add3\":\"2\",\"add4\":\"职业禁忌证\",\"add5\":\"8\",\"add6\":\"8\",\"add7\":\"—\",\"add9\":\"1\",\"add8\":\"23\"}]";

        ObjectMapper mapper = new ObjectMapper();
        try {
            // 将JSON字符串转换为JsonNode
            JsonNode jsonArray = mapper.readTree(jsonString);

            // 检查每个对象的add8字段
            boolean allAdd8NonNull = true;
            for (JsonNode jsonObject : jsonArray) {
                JsonNode add8Node = jsonObject.get("add8");
                if (add8Node == null || add8Node.isNull() || add8Node.asText().isEmpty()) {
                    allAdd8NonNull = false;
                    break;
                }
            }

            // 根据检查结果返回值
            int result = allAdd8NonNull ? 1 : 2;
            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int insertNewReg(newRegWithBLOBs newRegWithBLOBs) {
        return newRegMapper.insertSelective(newRegWithBLOBs);
    }

    @Override
    public newRegWithBLOBs selectNewReg(newRegWithBLOBs newRegWithBLOBs) {
        newRegWithBLOBs item = newRegMapper.selectByPrimaryKey(newRegWithBLOBs.getId());

//        item.getArchivist();
//        YhUserReginfo yhUserReginfo=yhUserReginfoMapper.selectByPrimaryKey(item.getResponsibleHealthManagerCode());
//        if(yhUserReginfo!=null){
//            item.setResponsibleHealthManagerCode(yhUserReginfo.getName());
//        }
        YhUserReginfo yhUserReginfo2 = yhUserReginfoMapper.selectByPrimaryKey(item.getArchivist());
        if (yhUserReginfo2 != null) {
            item.setArchivist(yhUserReginfo2.getName());
        }
        return item;
    }

    @Override
    public List<newRegWithBLOBs> selectRegInfo(newRegWithBLOBs newRegWithBLOBs, int page, int limit) {
        int begin = (page - 1) * limit;
        newRegWithBLOBs.setBegin(begin);
        newRegWithBLOBs.setLimit(limit);
        return newRegMapper.selectRegInfo(newRegWithBLOBs);
    }

    @Override
    public List<newRegWithBLOBs> getOrderList() {
        return newRegMapper.getOrderList();
    }

    @Override
    public List<newRegWithBLOBs> queryLocallyManagedArchives(newRegWithBLOBs newRegWithBLOBs) {
        return newRegMapper.queryLocallyManagedArchives(newRegWithBLOBs);
    }

    @Override
    public int selectRegCount(newRegWithBLOBs newRegWithBLOBs) {
        return newRegMapper.selectRegCount(newRegWithBLOBs);
    }

    @Override
    public int updateReg(newRegWithBLOBs newRegWithBLOBs) {
        return newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
    }

    @Override
    public int delete(newRegWithBLOBs newRegWithBLOBs) {
        return newRegMapper.deleteByPrimaryKey(newRegWithBLOBs.getId());
    }

    @Override
    public int queryLocallyManagedArchivesCount(newRegWithBLOBs newRegWithBLOBs) {
        return newRegMapper.queryLocallyManagedArchivesCount(newRegWithBLOBs);
    }


}
