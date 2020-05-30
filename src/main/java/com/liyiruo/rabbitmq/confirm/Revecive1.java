package com.liyiruo.rabbitmq.confirm;

import com.liyiruo.rabbitmq.util.ConnectUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
@Slf4j
public class Revecive1 {

    private static final String QUEUE_NAME = "test_queue_configrm1";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                log.info("=====");
                System.out.println("msg==>" + msg);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
