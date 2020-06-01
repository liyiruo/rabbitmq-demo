package com.liyiruo.rabbitmq.rb8_confirm;

import com.liyiruo.rabbitmq.rb0_util.ConnectUtil;
import com.rabbitmq.client.*;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send1 {
    private static final String QUEUE_NAME = "test_queue_configrm1";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用 confirmSelect  将channel 设置为confirm 模式
        channel.confirmSelect();
        String msg = "hello configrm1";
        channel.basicPublish("","QUEUE_NAME",null,msg.getBytes());

        if (!channel.waitForConfirms()) {
            System.out.println("message send is failed");
        } else {
            System.out.println("message send is ok");
        }
        channel.close();
        connection.close();
    }
}
