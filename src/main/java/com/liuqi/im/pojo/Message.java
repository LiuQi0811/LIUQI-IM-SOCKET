package com.liuqi.im.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *@ClassName Message
 *@Description 消息实体类
 *@Author LiuQi
 *@Date 2023/1/4 14:45
 *@Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    /**
     * 消息发送方
     */
    private String to;
    /**
     * 来源
     */
    private String source;
    /**
     * 类型
     * 0给所有人发消息
     * 1单个人发消息
     * 2发送视频通话消息
     * 3确定进行视频通话
     */
    private String type;
    /**
     * 消息内容
     */
    private String msg;
}
