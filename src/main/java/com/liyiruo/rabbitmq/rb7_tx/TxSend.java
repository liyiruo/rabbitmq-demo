package com.liyiruo.rabbitmq.rb7_tx;

import com.liyiruo.rabbitmq.rb0_util.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSend {
    private static final String QUEUE_NAME = "test_work_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "hello tx message";

        try {
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            //如果报错了会回滚的
            int i = 1 / 0;
            channel.txCommit();
            System.out.println("send success" + msg);
        } catch (Exception e) {
            System.out.println("回滚了");
            channel.txRollback();
        }
        channel.close();
        connection.close();

    }
}
