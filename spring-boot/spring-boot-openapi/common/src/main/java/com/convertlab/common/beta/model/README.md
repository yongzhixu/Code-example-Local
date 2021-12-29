* DTO：前端传参数给后端的，如Controller参数；或者controller参数转service用
* VO：返回给前端页面
* bo：业务定义的，service之间用的
* entity：数据库与实体的映射
* request：后端直接调用第三方API接口时的入参
* response：后端直接调用第三方API接口时的出参### 消息队列使用
  如果不使用Kafka 、Rabbit，则直接在屏蔽其自动加载，即在@SpringBootApplication如下配置
 ```
 // 屏蔽 Kafka 、Rabbit自动加载 
exclude = {KafkaAutoConfiguration.class, RabbitAutoConfiguration.class})
```