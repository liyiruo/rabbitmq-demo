package com.liyiruo.rabbitmq.rb1_simple;

import com.liyiruo.rabbitmq.rb0_util.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
@Slf4j
public class Sent {
    private static final String QUEUE_NAME = "test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取链接
        Connection connection = ConnectUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发送的消息内容
        String message = "this is a message";
        //发送消息
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes() );

        for (int i = 0; i < 100; i++) {
            message="this is Sent i="+i;
            log.info("发送的消息为{}",message);
            System.out.println("发送的消息为："+message);
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());

            Thread.sleep(1000);
        }
        channel.close();
        connection.close();
    }
}
