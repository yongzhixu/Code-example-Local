package com.convertlab.rabbitmqpipe.config;

import com.convertlab.rabbitmqpipe.service.RabbitCallback;
import com.convertlab.rabbitmqpipe.service.RabbitPostProcessor;
import lombok.Data;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.batch.BatchingStrategy;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 批量发送
 *
 * @author LIUJUN
 * @date 2021-02-19 10:54:52
 */
@Configuration
@Data
@AutoConfigureAfter({RabbitAutoConfiguration.class})
public class RabbitConfig {
    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(RabbitConfig.class);

    @Value("${spring.rabbitmq.addresses:}")
    private String addresses;

    @Value("${spring.rabbitmq.username:}")
    private String username;

    @Value("${spring.rabbitmq.password:}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host:/}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirm-type:}")
    private String publisherConfirmType;

    @Value("${spring.rabbitmq.publisher-returns:false}")
    private String publisherReturns;

    @Value("${spring.rabbitmq.template.mandatory:true}")
    private String mandatory;

    /** 发送消息回调的业务类 */
    @Value("${rabbit.send.callback.service:}")
    private String callbackService;

    /** 发送消息回调的业务类的方法-消息投递Broker前初始化消息日志 */
    @Value("${rabbit.send.callback.method.init:}")
    private String initSendMethod;

    /** 发送消息回调的业务类的方法--是否成功日志 1:是 */
    @Value("${rabbit.send.callback.isRecordSuccess:0}")
    private Integer recordSendSuccessFlag;

    /** 发送消息回调的业务类的方法-消息投递失败或成功 */
    @Value("${rabbit.send.callback.method.update:}")
    private String updateSendMethod;

    /** 接收消息回调的业务类 */
    @Value("${rabbit.receive.callback.service:}")
    private String receiveCallbackService;

    /** 接收消息回调的业务类的方法-前初始化消息日志 */
    @Value("${rabbit.receive.callback.method.init:}")
    private String initReceiveMethod;

    /** 接收消息回调的业务类的方法-是否成功日志 1:是 */
    @Value("${rabbit.receive.callback.isRecordSuccess:0}")
    private Integer recordReceiveSuccessFlag;

    @Autowired
    private RabbitCallback rabbitCallback;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        if (StringUtils.isEmpty(addresses)) {
            return new CachingConnectionFactory();
        }
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        if (CachingConnectionFactory.ConfirmType.CORRELATED.name().equalsIgnoreCase(publisherConfirmType)) {
            connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        } else if (CachingConnectionFactory.ConfirmType.SIMPLE.name().equalsIgnoreCase(publisherConfirmType)) {
            connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        }
        connectionFactory.setPublisherReturns(BooleanUtils.toBoolean(publisherReturns));
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(rabbitCallback);
        // 开启mandatory模式（开启失败回调）
        rabbitTemplate.setMandatory(BooleanUtils.toBoolean(mandatory));
        rabbitTemplate.setReturnsCallback(rabbitCallback);
        rabbitTemplate.setBeforePublishPostProcessors(RabbitPostProcessor.publishMessagePostProcessor);
        return rabbitTemplate;
    }


    @Bean("batchQueueTaskScheduler")
    public TaskScheduler batchQueueTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        taskScheduler.setThreadNamePrefix("batchQueueTaskScheduler-");
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setAwaitTerminationSeconds(60);
        return taskScheduler;
    }

    /**
     * 批量处理rabbitTemplate
     *
     * @param connectionFactory 连接工厂
     * @param taskScheduler     线程池
     * @return BatchingRabbitTemplate
     */
    @Bean("batchRabbitTemplate")
    public BatchingRabbitTemplate batchRabbitTemplate(ConnectionFactory connectionFactory, @Qualifier("batchQueueTaskScheduler")
            TaskScheduler taskScheduler) {

        // !!! 批量是spring 将多条message重新组成一条message, 发送到mq, 从mq接受到这条message后，在重新解析成多条message
        // 批量压缩20条消息当作一条消息
        int batchSize = 20;
        // 缓存大小限制,单位字节，
        // simpleBatchingStrategy的策略，是判断message数量是否超过batchSize限制或者message的大小是否超过缓存限制，
        // 缓存限制，主要用于限制"组装后的一条消息的大小"
        // 如果主要通过数量来做批量("打包"成一条消息), 缓存设置大点
        // 详细逻辑请看simpleBatchingStrategy#addToBatch()
        int bufferLimit = 10 * 1024 * 1024;
        long timeout = 10 * 1000;

        // 注意，该策略只支持一个exchange/routingKey
        // A simple batching strategy that supports only one exchange/routingKey
        BatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, bufferLimit, timeout);
        BatchingRabbitTemplate template = new BatchingRabbitTemplate(connectionFactory, batchingStrategy, taskScheduler);
        template.setConfirmCallback(rabbitCallback);
        // 开启mandatory模式（开启失败回调）
        template.setMandatory(BooleanUtils.toBoolean(mandatory));
        template.setReturnsCallback(rabbitCallback);
        template.setBeforePublishPostProcessors(RabbitPostProcessor.publishMessagePostProcessor);
        return template;
    }

    /**
     * \@RabbitListener注解指定目标方法来作为消费消息的方法，通过注解参数指定所监听的队列或者Binding。
     * 使用@RabbitListener可以设置一个自己明确默认值的RabbitListenerContainerFactory对象。
     *
     * @return SimpleRabbitListenerContainerFactory
     */
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // connectionFactory就是我们自己配置的连接工厂直接注入进来
        factory.setConnectionFactory(connectionFactory);
        // 设置消息确认方式为手动确认
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置消息预取数量 取值范围是[0,65535]，取值为0也是代表无限制，默认250
        // factory.setPrefetchCount(1);
        factory.setAfterReceivePostProcessors(RabbitPostProcessor.receiveMessagePostProcessor);
        return factory;
    }
}
