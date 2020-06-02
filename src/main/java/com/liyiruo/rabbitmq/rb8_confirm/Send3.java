package com.liyiruo.rabbitmq.rb8_confirm;

import com.liyiruo.rabbitmq.rb0_util.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * 异步确认
 */
@Slf4j
public class Send3 {
    private static final String QUEUE_NAME = "test_queue_configrm5";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.confirmSelect();
        //未确认的消息标识
        final SortedSet<Long> comfirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
        //通道添加监听
        channel.addConfirmListener(new ConfirmListener() {
            //没有问题的 handleAck
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                if (b) {
                    System.out.println("handleAck----multiple");

                    comfirmSet.headSet(l + 1).clear();
                } else {
                    System.out.println("handleAck----multiple-false--");
                    comfirmSet.remove(l);
                }
            }

            //handleNack
            @Override
            public void handleNack(long l, boolean b) throws IOException {
                if (b) {
                    System.out.println("handleNack----multiple");
                    comfirmSet.headSet(l + 1).clear();
                } else {
                    System.out.println("handleNack----multiple-false--");
                    comfirmSet.remove(l);
                }

            }
        });

        int i = 0;
        while (true) {
            String msg = "ssss" + i++;
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            comfirmSet.add(seqNo);
        }

    }
}
