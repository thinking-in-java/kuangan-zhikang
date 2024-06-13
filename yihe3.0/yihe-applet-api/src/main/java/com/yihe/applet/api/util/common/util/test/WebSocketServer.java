package com.yihe.applet.api.util.common.util.test;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.yihe.applet.api.domain.YhUserChatRecord;
import com.yihe.applet.api.mapper.YhUserChatRecordMapper;
import com.yihe.applet.api.service.YhUserChatRecordService;
import com.yihe.applet.api.util.common.util.test.OnlineUserEntity;
import com.yihe.common.utils.StringUtils;


//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint("/websocket/{id}/{to}")
@Component
public class WebSocketServer {

 //调用另外的服务层方法，如：将聊天信息插入数据库等，需要定义静态对象，再之后重写构造方法
	private static YhUserChatRecordService yhUserChatRecordService;
 //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
 private static int onlineCount = 0;

 //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
 //private static CopyOnWriteArraySet<WebSocketServer >  onlineUserSet = new CopyOnWriteArraySet<WebSocketServer >();
 private static Map<String, OnlineUserEntity> onlineUserMap = new ConcurrentHashMap<String, OnlineUserEntity>(); 
 
//重写构造，并加入自动注入的标签，实现自动注入对象
 @Autowired
 public WebSocketServer(YhUserChatRecordService yhUserChatRecordService){
	   this.yhUserChatRecordService = yhUserChatRecordService;
 }
 //原来的构造方法要保留
 public WebSocketServer(){
	   
 }
 
 /**
  * 连接建立成功调用的方法
  * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
  */
	@OnOpen
	public void onOpen(Session session, @PathParam("id") String id, @PathParam("to") String to) {
      // 将当前用户存到在线用户列表中
      OnlineUserEntity onlineUser = new OnlineUserEntity();
      onlineUser.setUserid(id);
      onlineUser.setTo(to);
      onlineUser.setSession(session);
      onlineUserMap.put(id,onlineUser);
      yhUserChatRecordService.modifyRead1(to, id);
		addOnlineCount(); // 在线数加1
		 System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

  

 /**
  * 连接关闭调用的方法
  */
 @OnClose
 public void onClose(Session session, @PathParam("id") String id, @PathParam("to") String to){
     // 移除的用户信息
	   OnlineUserEntity removeUser = onlineUserMap.remove(id);
     onlineUserMap.remove(id);
     
     subOnlineCount();           //在线数减1    
     System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
 }

  
 public static void main(String[] args) { 
    
 } 
 /**
  * 收到客户端消息后调用的方法
  * @param message 客户端发送过来的消息
  * @param session 可选的参数
  */
	@OnMessage
	public void onMessage(Session session, @PathParam("id") String id, @PathParam("to") String to, String message) {
		 long now = System.currentTimeMillis(); // 排序前取得当前时间 
	     Calendar c = Calendar.getInstance(); 
	     c.setTimeInMillis(now); 
	     //年月日 时分
	     String time1=c.get(Calendar.YEAR) + "年 "+ (c.get(Calendar.MONTH) + 1) + "月 "+ c.get(Calendar.DATE) 
         + "日    "+ c.get(Calendar.HOUR) + "时 " + c.get(Calendar.MINUTE) + "分 ";
	     boolean itonDialogue = isItonDialogue(to,id);
	     YhUserChatRecord num=new  YhUserChatRecord(id,to,message,itonDialogue?1:0);
		if (!to.equals(id)) {
			if (onlineUserMap.get(to) != null) {
				
				singleSend(num,message, onlineUserMap.get(to)); // 分别发送给指定的用户
			}
		}
		// 将信息插入数据库
		System.out.println("插入数据库"); 
		System.out.println("id:"+id);
		System.out.println("to:"+to);
		System.out.println("message:"+message);
		System.out.println("isItonLine:"+isItonLine(to));
		
		yhUserChatRecordService.insertSelective(num);
		//YhUserChatRecordMapper.insertMessage(id, to, message);
		// 返回用户
		singleSend(num,message, onlineUserMap.get(id));
 }
 
 
 /**
  * 发生错误时调用
  * @param session
  * @param error
  */
 @OnError
 public void onError(Session session, Throwable error){
     System.out.println("发生错误");
     error.printStackTrace();
 }
  
 /**
  * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。对特定用户发送消息
  * @param message
  * @throws IOException
  */
 public static void singleSend(YhUserChatRecord num,String message, OnlineUserEntity onlineUser){
     try {
    	  Object obj = JSONArray.toJSON(num);
          String json = obj.toString();

         onlineUser.getSession().getAsyncRemote().sendText(json);
         //onlineUser.getSession().getBasicRemote().sendText(message);
         //getAsyncRemote()和getBasicRemote()是异步与同步的区别，大部分情况下，推荐使用getAsyncRemote()异步
     } catch (Exception e) {
         e.printStackTrace();
     }
 }
//是否在对话
public static synchronized boolean isItonDialogue(String id,String to) {
    if(onlineUserMap.containsKey(id)){
    	OnlineUserEntity onlineUserEntity = onlineUserMap.get(id);
    	if(null!=onlineUserEntity){
    		String to2 = onlineUserEntity.getTo();
    		if(StringUtils.isNotEmpty(to2)){
    			if(to2.equals(to)){
    				return true;
    			}
    		}
    	}
    }
	
    return false;
}
 
 //是否在线
 public static synchronized boolean isItonLine(String id) {
     return onlineUserMap.containsKey(id);
 }

 public static synchronized int getOnlineCount() {
     return onlineCount;
 }


 public static synchronized void addOnlineCount() {
	   WebSocketServer.onlineCount++;
 }


 public static synchronized void subOnlineCount() {
	   WebSocketServer.onlineCount--;
 }

}
