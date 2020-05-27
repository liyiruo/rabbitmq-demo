# rabbitmq-demo

## 简单模式
- 一对一的消息接收和发送

[https://www.rabbitmq.com/getstarted.html](https://www.rabbitmq.com/getstarted.html)

## work 工作模式
- 一个任务 轮流着给 多个消息消费者，每个消费者 依次获取到消息
## work fair公平模式
- 能者多劳，消费完一个，手动确认，就进行下一个。
## 订阅模式
订阅模式有问题：
`channel.exchangeBind(QUEUE_NAME, EXCHANGE_NAME, "");`
在消费者代码里的第18行；一直报错
```bash
Exception in thread "main" java.io.IOException
	at com.rabbitmq.client.impl.AMQChannel.wrap(AMQChannel.java:105)
	at com.rabbitmq.client.impl.AMQChannel.wrap(AMQChannel.java:101)
	at com.rabbitmq.client.impl.AMQChannel.exnWrappingRpc(AMQChannel.java:123)
	at com.rabbitmq.client.impl.ChannelN.exchangeBind(ChannelN.java:873)
	at com.rabbitmq.client.impl.recovery.AutorecoveringChannel.exchangeBind(AutorecoveringChannel.java:297)
	at com.rabbitmq.client.impl.recovery.AutorecoveringChannel.exchangeBind(AutorecoveringChannel.java:292)
	at com.liyiruo.rabbitmq.pb.Reveice1.main(Reveice1.java:18)
Caused by: com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'test_queue_fanout_email' in vhost '/liyiruo', class-id=40, method-id=30)
	at com.rabbitmq.utility.ValueOrException.getValue(ValueOrException.java:66)
	at com.rabbitmq.utility.BlockingValueOrException.uninterruptibleGetValue(BlockingValueOrException.java:32)
	at com.rabbitmq.client.impl.AMQChannel$BlockingRpcContinuation.getReply(AMQChannel.java:366)
	at com.rabbitmq.client.impl.AMQChannel.privateRpc(AMQChannel.java:229)
	at com.rabbitmq.client.impl.AMQChannel.exnWrappingRpc(AMQChannel.java:117)
	... 4 more
Caused by: com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'test_queue_fanout_email' in vhost '/liyiruo', class-id=40, method-id=30)
	at com.rabbitmq.client.impl.ChannelN.asyncShutdown(ChannelN.java:505)
	at com.rabbitmq.client.impl.ChannelN.processAsync(ChannelN.java:336)
	at com.rabbitmq.client.impl.AMQChannel.handleCompleteInboundCommand(AMQChannel.java:143)
	at com.rabbitmq.client.impl.AMQChannel.handleFrame(AMQChannel.java:90)
	at com.rabbitmq.client.impl.AMQConnection.readFrame(AMQConnection.java:634)
	at com.rabbitmq.client.impl.AMQConnection.access$300(AMQConnection.java:47)
	at com.rabbitmq.client.impl.AMQConnection$MainLoop.run(AMQConnection.java:572)
	at java.lang.Thread.run(Thread.java:748)

```
需要手动的在：
`http://localhost:15672/#/exchanges/%2Fliyiruo/test_exchange_fanout`上，手动绑定，然后注释掉报错代码，然后可以正常发送和接收；