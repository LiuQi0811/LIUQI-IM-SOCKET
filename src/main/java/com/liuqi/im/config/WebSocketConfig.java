package com.liuqi.im.config;

import com.liuqi.im.Interceptor.MessageHandlerInterceptor;
import com.liuqi.im.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
 *@ClassName WebSocketConfig
 *@Description WebSocket配置类
 *@Author LiuQi
 *@Date 2023/1/4 11:51
 *@Version 1.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private MessageHandlerInterceptor messageHandlerInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler,"/websocket/{name}")
                .setAllowedOrigins("*")
                .addInterceptors(messageHandlerInterceptor);

    }
}
