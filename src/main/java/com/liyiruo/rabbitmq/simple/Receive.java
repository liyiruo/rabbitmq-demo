package com.liyiruo.rabbitmq.simple;

import com.liyiruo.rabbitmq.util.ConnectUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive {
    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        newApi();
    }

    private static void newApi() throws IOException, TimeoutException, InterruptedException {
        //获取链接
        Connection connection = ConnectUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
         //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义消费值
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String s = new String(body, "utf-8");
                System.out.println("new api==>" + s);
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);

        //发送的时候 发送完了 就结束了，但是接受的时候不需要关闭。
        /*channel.close();
        connection.close();*/

    }


    private static void oldApi() throws IOException, TimeoutException, InterruptedException {
        //获取链接
        Connection connection = ConnectUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //定义消费值
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
        int i = 0;
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String body = new String(delivery.getBody());
            System.out.println(i + "接收到的消息：==》" + body);
            i++;
        }
    }
}
