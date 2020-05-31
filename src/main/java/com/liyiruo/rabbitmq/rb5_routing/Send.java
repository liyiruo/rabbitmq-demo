package com.liyiruo.rabbitmq.rb5_routing;

import com.liyiruo.rabbitmq.rb0_util.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String EXCHANGE_NAME = "test_exchange_direct";
    static List list = new LinkedList();
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        list.add("info");
        list.add("error");
        list.add("warning");
        for (Object s : list) {
            String msg = (String) s;
            System.out.println(msg);
            String routingKey = (String) s;
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        }

        /*String msg = "hello direct";
        String routingKey = "error";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
   */
    }
}
