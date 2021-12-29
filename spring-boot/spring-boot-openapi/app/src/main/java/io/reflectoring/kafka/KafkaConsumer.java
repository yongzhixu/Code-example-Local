package io.reflectoring.kafka;

import com.convertlab.common.beta.utils.JsonUtil;
import com.convertlab.kafkapipe.utils.KafkaUtil;
import io.reflectoring.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    /**
     * 监听鸿联九五单条短信
     * 将kafka的listener放在方法投可以避免topic缺失是服务启动报错（KafkaListener放在类头部时候会报错）
     * @param record 消息体.可以定义为ConsumerRecord<String, String> record或String message
     *
     */
    @KafkaListener(id = "${spring.kafka.template.test.group-id}", topics = "${spring.kafka.template.test.topic}",
            concurrency = "1")
    public void sendSmsListener(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("【监听鸿联九五单条短信接受到Kafka消息][线程:{} 消息内容：{} partition：{}]", Thread.currentThread().getName(), record, record.partition());
        try {
            // 手动提交偏移量 进来就提交，防止短信发送成功后，自已业务异常导致重复提交
            ack.acknowledge();
            // 获取消息
            String message = record.value();
            User user = JsonUtil.parse(message, User.class);
            log.info("=================== "+user);
        } catch (Exception e) {
            log.error(KafkaUtil.getRecord4NoValue(record) + "监听鸿联九五单条短信-异常：", e);
            //ack.nack(500);
        }
    }

}
