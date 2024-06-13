package com.yihe.web.controller.tool;


import com.yihe.system.domain.HealthRecordJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 业务包
 * 
 * @author terryhuang
 * @date 2016年8月18日 下午4:38:27
 */
public class BizPackage {

	private String protocolVersion;
	private int handleType; // 0 请求；//1 结果 //2总记录数//3,停止
	private int commandId;
	private String encryptedMode; // 0x00 明文方式 不加密
	private int result;
	private Integer totalRows;
	private Integer pageSize;
	private Integer currentPage;
	private String subCommandIds;

	private List dataList; // 有顺序的数据对象
	private List otherDataList;
	private String jsonString;
	private HealthRecordJson healthRecordData;

	public JSONObject getJsonObject() {
		JSONObject jObject = new JSONObject();
		jObject.put(HttpConstants.PACKAGE_INFO_KEY_VERSION, protocolVersion);
		jObject.put(HttpConstants.PACKAGE_INFO_KEY_HANDLETYPE, handleType);
		jObject.put(HttpConstants.PACKAGE_INFO_KEY_COMMANDID, commandId);
		jObject.put(HttpConstants.PACKAGE_INFO_KEY_ENCRYPTEDMODE,encryptedMode);
		jObject.put(HttpConstants.PACKAGE_INFO_KEY_SUBCOMMANDIDS, subCommandIds);
		if(totalRows!=null){
			jObject.put(Router.PACKAGE_INFO_KEY_TOTALROWS, totalRows);
		}
		if(pageSize!=null){
			jObject.put(Router.PACKAGE_INFO_KEY_PAGESIZE, pageSize);
		}
		if(currentPage!=null){
			jObject.put(Router.PACKAGE_INFO_KEY_CURRENTPAGE, currentPage);
		}
		if(healthRecordData!=null){
			try {
				jObject.put(Router.PACKAGE_INFO_KEY_JSON_PATIENT, JsonUtil.objectToJson(healthRecordData));
			} catch (JSONException e) {
			} catch (IOException e) {
			}
		}		
		
		JSONObject jObjectResult20 = new JSONObject();
		JSONObject jObjectData21 = new JSONObject();
		try {
			Object obj = null;
			if (dataList != null && !dataList.isEmpty()) {
				Iterator ir = dataList.iterator();
				int index = 0;

				while (ir.hasNext()) {
					index++;

					obj = ir.next();
					if (obj != null) {
						Field fields[] = obj.getClass().getDeclaredFields();// 获得对象所有属性
						Class clazz = obj.getClass();

						Map<String, Object> map = new HashMap<String, Object>();
						for (int i = 0; i < fields.length; i++) {
							Field field = fields[i];
							field.setAccessible(true);// 修改访问权限
							String fieldName = field.getName();
							PropertyDescriptor pd=null;
							try{
								pd = new PropertyDescriptor(fieldName, clazz);
							}catch(IntrospectionException ee){
								pd=null;
							}							

							if (pd != null) {
								Method getMethod = pd.getReadMethod();// 获得get方法
								
								Object value = getMethod.invoke(obj);// 执行get方法返回一个Object
								// System.out.println(value);
								map.put(fieldName, value);
							}
						}
						
					}
				}
				
				switch (handleType) {
				case 0:
					jObjectData21.put(HttpConstants.PACKAGE_INFO_PATIENT_SIGN_CONTRACTS, dataList);
//					JSONArray jArray=JSONArray.fromObject(dataList);
					break;
				case 1:
					JSONArray jArray=new JSONArray();
					for(int i=0;i<dataList.size();i++){
						Object obj1=dataList.get(i);
//						if(obj1 instanceof Device){
//							DeviceJson dj=new DeviceJson();
//							BeanUtils.copyProperties(dj,obj1);
//							jArray.put(JsonUtil.objectToJson(dj));
//						}else{
//							jArray.put(JsonUtil.objectToJson(dataList.get(i)));
//						}
					}
					jObjectData21.put(HttpConstants.PACKAGE_INFO_KEY_RESULTINFO, jArray);
					break;

				}
			}

			if (handleType == HttpConstants.HANDLE_TYPE_RESULT) {
				jObjectResult20.put(
						HttpConstants.PACKAGE_INFO_KEY_RESULTINFO, result);
				jObject.put(HttpConstants.PACKAGE_INFO_KEY_RESULT,
						jObjectResult20);
				if (dataList != null && !dataList.isEmpty()) {
					jObject.put(HttpConstants.PACKAGE_INFO_KEY_DATE,
							jObjectData21);
				}
			} else {// apply
				if (dataList != null && !dataList.isEmpty()) {
					jObject.put(HttpConstants.PACKAGE_INFO_KEY_DATE,
							jObjectData21);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// other date
		JSONObject jObjectOtherData22 = new JSONObject();
		try {
			Object obj = null;
			if (otherDataList != null && !otherDataList.isEmpty()) {
				Iterator ir = otherDataList.iterator();
				int index = 0;

				while (ir.hasNext()) {
					index++;

					obj = ir.next();
					if (obj != null) {
						Field fields[] = obj.getClass().getDeclaredFields();// 获得对象所有属性
						Class clazz = obj.getClass();

						Map<String, Object> map = new HashMap<String, Object>();
						for (int i = 0; i < fields.length; i++) {
							Field field = fields[i];
							field.setAccessible(true);// 修改访问权限
							String fieldName = field.getName();
							PropertyDescriptor pd;

							pd = new PropertyDescriptor(fieldName, clazz);

							Method getMethod = pd.getReadMethod();// 获得get方法

							if (pd != null) {
								Object value = getMethod.invoke(obj);// 执行get方法返回一个Object
								// System.out.println(value);
								map.put(fieldName, value);
							}
						}
						
					}
				}
				
				switch (handleType) {
				case 0:
					jObjectOtherData22.put(HttpConstants.PACKAGE_INFO_PATIENT_SIGN_CONTRACTS, otherDataList);
					break;
				case 1:
//					jObjectOtherData22.put(HttpConstants.PACKAGE_INFO_KEY_RESULTINFO+ index, map);
					JSONArray jArray=new JSONArray();
					for(int i=0;i<otherDataList.size();i++){
						Object obj1=otherDataList.get(i);
//						if(obj1 instanceof Device){
//							DeviceJson dj=new DeviceJson();
//							BeanUtils.copyProperties(dj,obj1);
//							jArray.put(JsonUtil.objectToJson(dj));
//						}else{
//							jArray.put(JsonUtil.objectToJson(otherDataList.get(i)));
//						}
					}
					//JSONArray.fromObject(otherDataList);
					jObjectOtherData22.put(HttpConstants.PACKAGE_INFO_KEY_RESULTINFO, jArray);
					break;
				}
			}

			if (handleType == HttpConstants.HANDLE_TYPE_RESULT) {
				if (otherDataList != null && !otherDataList.isEmpty()) {
					jObject.put(HttpConstants.PACKAGE_INFO_KEY_OTHERDATE,jObjectOtherData22);
				}
			} 
//			else {
//				if (otherDataList != null && !otherDataList.isEmpty()) {
//					jObject.put(HttpConstants.PACKAGE_INFO_KEY_OTHERDATE,
//							jObjectOtherData22);
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// {"Result":{"DateOther":{"ResultObject1":{"opUserCode":"222","address":"113","email":"11","name":"11","opName":"青春酒店","loginCount":0,"createDate":"Fri Jul 22 17:27:53 CST 2016","telephone":"11","isValid":true,"password":"123458","opUserId":4}},"ResultValue":1,"Date":{"ResultObject1":{"opUserAuzId":12,"auzStatus":1,"period":1,"createDate":"Fri Jul 22 17:46:41 CST 2016","opUserId":4},"ResultObject4":{"opUserAuzId":15,"auzStatus":3,"period":1,"createDate":"Mon Aug 08 17:33:41 CST 2016","opUserId":4},"ResultObject2":{"opUserAuzId":13,"auzStatus":1,"period":3,"createDate":"Fri Jul 22 17:53:19 CST 2016","opUserId":4},"ResultObject3":{"opUserAuzId":14,"auzStatus":2,"period":1,"createDate":"Mon Aug 08 17:33:22 CST 2016","opUserId":4}}},"IsToGateway":false,"GatewayIp":"192.168.2.121","DestIp":"192.168.2.121","IsFromBiz":false,"IsToBiz":false,"CommandId":2,"IsFromGateway":true,"MessageType":1,"IsToCloud":true,"IsFromCloud":false,"SrcIp":"192.168.2.121"}
		return jObject;
	}

	public String getProtocolVersion() {
		protocolVersion = "10";// major=1,minor=0
		return protocolVersion;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public int getHandleType() {
		return handleType;
	}

	public void setHandleType(int handleType) {
		this.handleType = handleType;
	}

	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public String getEncryptedMode() {
		return encryptedMode;
	}

	public void setEncryptedMode(String encryptedMode) {
		this.encryptedMode = encryptedMode;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public List getOtherDataList() {
		return otherDataList;
	}

	public void setOtherDataList(List otherDataList) {
		this.otherDataList = otherDataList;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}	
	
	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getSubCommandIds() {
		return subCommandIds;
	}

	public void setSubCommandIds(String subCommandIds) {
		this.subCommandIds = subCommandIds;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public HealthRecordJson getHealthRecordData() {
		return healthRecordData;
	}

	public void setHealthRecordData(HealthRecordJson healthRecordData) {
		this.healthRecordData = healthRecordData;
	}	
}
