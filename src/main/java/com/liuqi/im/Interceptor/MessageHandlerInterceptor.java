package com.liuqi.im.Interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/*
 *@ClassName MessageHandlerInterceptor
 *@Description TODO
 *@Author LiuQi
 *@Date 2023/1/4 12:27
 *@Version 1.0
 */
@Component
@Slf4j
public class MessageHandlerInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String path = request.getURI().getPath();
        log.info("获取接口路径 ===> {}", path);
        if (StringUtils.isEmpty(path)) {
            return false;
        }

        String[] split = path.split("/");
        log.info("解析分割 接口路径 返回结果 ===> {}", JSON.toJSONString(split));
        if (split == null) {
            return false;
        }
        log.info("获取 分割后的 name ===> {}", split[2]);
        attributes.put("username", split[2]);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
