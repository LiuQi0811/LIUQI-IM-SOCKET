package com.liuqi.im.controller;

import com.alibaba.fastjson.JSON;
import com.liuqi.im.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/*
 *@ClassName UserController
 *@Description im用户信息
 *@Author LiuQi
 *@Date 2023/1/5 11:33
 *@Version 1.0
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
@CrossOrigin
public class UserController
{
    @Autowired
    private MessageHandler messageHandler;

    @GetMapping(value = "/list")
    public Object list(){
        Set<String> all = messageHandler.SESSION_MAP.keySet();
        log.info("会话连接 信息  {}", JSON.toJSON(all));
        return all;
    }
}
