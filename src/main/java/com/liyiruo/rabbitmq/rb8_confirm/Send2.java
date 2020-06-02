package com.liyiruo.rabbitmq.rb8_confirm;

import com.liyiruo.rabbitmq.rb0_util.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 同步发送一批数据，发送完，并且接收完以后，
 */
@Slf4j
public class Send2 {
    private static final String QUEUE_NAME = "test_queue_configrm1";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用 confirmSelect  将channel 设置为confirm 模式
        channel.confirmSelect();

        for (int i = 0; i < 100; i++) {
            String msg = "hello confirm1==>"+i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            log.info("msg==>{}",msg);
            Thread.sleep(10*i);
        }

        if (!channel.waitForConfirms()) {
            System.out.println("message send2 is failed");
        } else {
            System.out.println("message send2 is ok");
        }
        channel.close();
        connection.close();
    }
}
