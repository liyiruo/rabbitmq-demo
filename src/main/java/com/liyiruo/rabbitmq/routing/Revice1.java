package com.liyiruo.rabbitmq.routing;

import com.liyiruo.rabbitmq.util.ConnectUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Revice1 {
    private static final String EXCHANGE_NAME = "test_exchange_direct";
    private static final String QUEUE_NAME = "test_queue_direct1";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        //这个地方写错了 声明队列  中的参数写成了交换机
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        //queueBind 写成了exechangeBind
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println(msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        boolean autoAck=false;
        channel.basicConsume(QUEUE_NAME, autoAck, defaultConsumer);
    }
}
