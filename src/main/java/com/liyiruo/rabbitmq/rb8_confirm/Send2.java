package com.liyiruo.rabbitmq.rb8_confirm;

import com.liyiruo.rabbitmq.rb0_util.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
@Slf4j
public class Send2 {
    private static final String QUEUE_NAME = "test_queue_configrm1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用 confirmSelect  将channel 设置为confirm 模式
        channel.confirmSelect();
        String msg = "hello confirm1";
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            log.info("msg==>{}",msg);
        }

        if (!channel.waitForConfirms()) {
            System.out.println("message send is failed");
        } else {
            System.out.println("message send is ok");
        }
        channel.close();
        connection.close();
    }
}
