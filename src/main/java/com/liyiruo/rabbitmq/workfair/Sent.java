package com.liyiruo.rabbitmq.workfair;

import com.liyiruo.rabbitmq.util.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Sent {
    private static final String QUEUE_NAME = "test_work_fair_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectUtil.getConnection();
        //获取channel
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /**
         * 每一个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，依次值处理一个消息，
         * 限制发送给同一个消费者不得超过一条消息
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        for (int i = 0; i < 50; i++) {
            String msg = "第" + i + "消息";
            //消息发送
            System.out.println(msg);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            Thread.sleep(i * 30);
        }
        channel.close();
        connection.close();
    }
}
