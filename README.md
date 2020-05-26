# rabbitmq-demo

## 简单模式
- 一对一的消息接收和发送

[https://www.rabbitmq.com/getstarted.html](https://www.rabbitmq.com/getstarted.html)

## work 工作模式
- 一个任务 轮流着给 多个消息消费者，每个消费者 依次获取到消息
## work fair公平模式
- 能者多劳，消费完一个，手动确认，就进行下一个。
## 订阅模式