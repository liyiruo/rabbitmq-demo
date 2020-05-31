package com.liyiruo.rabbitmq.rb0_util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectUtil {


    /**
     * 获取一个链接
     *
     * @return
     */
    public static Connection getConnection() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/liyiruo");

        factory.setUsername("liyiruo");
        factory.setPassword("liyiruo");
        return factory.newConnection();
    }
}
