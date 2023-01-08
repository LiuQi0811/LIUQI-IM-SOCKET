package com.liuqi.im.handler;

import com.alibaba.fastjson.JSON;
import com.liuqi.im.pojo.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnMessage;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
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
        String name = (String) session.getAttributes().get("username");
        SESSION_MAP.put(name, session);
        this.onlineMessage(name);
    }

    /**
     * 上线消息提示
     *
     * @param name
     */
    private void onlineMessage(String name) {
        Set<String> sessions = SESSION_MAP.keySet();
        if (!CollectionUtils.isEmpty(sessions)) { // 数据不为空
            sessions.forEach(item -> {
                if (StringUtils.isNotBlank(item) && !item.equals(name)) {

                    WebSocketSession webSocketSession = SESSION_MAP.get(item);
                    if (webSocketSession != null) {
                        try {
                            Message message = new Message();
                            message.setType("0");
                            message.setMsg("您的好友" + name + "已上线！");
                            log.info("全部发送 {}", message);
                            webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(message)));
                        } catch (Exception e) {

                        }
                    }
                }

            });
        }
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
        String username = (String) session.getAttributes().get("username");
        SESSION_MAP.remove(username);
        log.info("websocket 连接已关闭！");
    }


    /**
     * 发送消息
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info(" 获取发送的消息: ==> {}", payload);
        Message msg = JSON.parseObject(payload, Message.class); //{"to":"李桂雪","source":"刘奇","type":"1","msg":"你好啊 小雪"} 刘奇发送 你好啊 给李桂雪
        log.info("Message 对象 ==> {}", JSON.toJSON(msg));
        String receive = msg.getTo();//消息接收方
        log.info("消息接收方 ==> {}", receive);
        WebSocketSession webSocketSession = SESSION_MAP.get(receive); // 获取消息接收方 会话信息
        log.info(" {} ", webSocketSession);
        if (webSocketSession != null) {
            String infoMessage = "来自 " + msg.getSource() + " 的消息: " + msg.getMsg(); //显示消息内容 编辑
            log.info(infoMessage);
            webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(msg))); //获取发送的消息
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
        log.info(" 获取发送的消息: {} ==>", payload);
    }
}
