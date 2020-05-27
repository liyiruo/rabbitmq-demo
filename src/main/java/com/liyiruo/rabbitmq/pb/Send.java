package com.liyiruo.rabbitmq.pb;

import com.liyiruo.rabbitmq.util.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机  交换机的名字和类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
       /* for (int i = 0; i < 50; i++) {
            //定义消息
            String msg = "fanout_message=>" + i;
            System.out.println(msg);
            //发送消息
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        }
        */


        String msg = "this is send";

        //发送消息
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println(msg);
        channel.close();
        connection.close();
    }
}
