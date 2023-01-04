package com.liuqi.im.handler;

import com.alibaba.fastjson.JSON;
import com.liuqi.im.pojo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnMessage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 *@ClassName MessageHandler
 *@Description  消息 MessageHandler
 *@Author LiuQi
 *@Date 2023/1/4 11:54
 *@Version 1.0
 */
@Component
@Slf4j
public class MessageHandler extends TextWebSocketHandler {

    public static final Map<String, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 进入 websocket
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String name = (String) session.getAttributes().get("name");
        SESSION_MAP.put(name, session);
    }

    /**
     * 关闭 websocket
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }


    /**
     * 发送消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info(" 获取发送的消息: ==> {}",payload);
         Message msg = JSON.parseObject(payload, Message.class); //{"to":"李桂雪","source":"刘奇","type":"1","msg":"你好啊 小雪"} 刘奇发送 你好啊 给李桂雪
         log.info("Message 对象 ==> {}",JSON.toJSON(msg));
         String receive = msg.getTo();//消息接收方
         log.info("消息接收方 ==> {}",receive);
         WebSocketSession webSocketSession = SESSION_MAP.get(receive); // 获取消息接收方 会话信息
         log.info(" {} ",webSocketSession );
         if(webSocketSession!=null)
         {
             String infoMessage = "来自 "+msg.getSource()+" 的消息: " +msg.getMsg(); //显示消息内容 编辑
            webSocketSession.sendMessage(new TextMessage(infoMessage)); //获取发送的消息
         }


    }

    /**
     * 发送信息
     *
     * @param session
     * @param message
     * @throws Exception
     */
    protected void sendTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //ws://127.0.0.1:9501/websocket/msg
        WebSocketSession webSocketSession = SESSION_MAP.get("msg"); //获取指定用户的名称的会话信息
        if (webSocketSession != null) {
            session.sendMessage(new TextMessage("我在发送消息。。。。。。。"));
        }
        String payload = message.getPayload();
        log.info(" 获取发送的消息: {} ==>",payload);
    }
}
