package com.liyiruo.rabbitmq.rb6_topic;

import com.liyiruo.rabbitmq.rb0_util.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Send {

    private final static String EXCHANGE_NAME = "test_exechange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String msg = "test_exechange_topic";

        Map map = new HashMap();
        map.put("goods.add", "商品添加……");
        map.put("goods.delete", "商品删除……");
        map.put("goods.update", "商品修改……");
        map.put("goods.select", "商品查询……");
        map.put("goods.update.add", "商品add and update……");

        for (Object s:map.keySet()
             ) {
            String key = (String) s;
            String msg_ = (String) map.get(key);
            System.out.println(key + "<===>" + msg_);
            channel.basicPublish(EXCHANGE_NAME,key,false,null,msg_.getBytes());

        }
        channel.close();
        connection.close();
    }
}
