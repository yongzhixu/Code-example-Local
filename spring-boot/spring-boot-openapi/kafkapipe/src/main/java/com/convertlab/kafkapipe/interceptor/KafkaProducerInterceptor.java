package com.convertlab.kafkapipe.interceptor;

import com.convertlab.common.beta.constants.HeaderConstant;
import com.convertlab.common.beta.enums.KafkaMsgStatusEnum;
import com.convertlab.common.beta.model.dto.TenantDto;
import com.convertlab.common.beta.utils.ContextUtil;
import com.convertlab.common.beta.utils.MdcUtil;
import com.convertlab.common.beta.utils.SpringContextUtil;
import com.convertlab.kafkapipe.config.KafkaConfig;
import com.convertlab.kafkapipe.service.KafkaCallback;
import com.convertlab.kafkapipe.utils.KafkaMdcUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Headers;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.UUID;

/**
 * kafka生产者拦截器
 *
 * @author LIUJUN
 * @date 2021-05-24 11:11:27
 */
public class KafkaProducerInterceptor implements ProducerInterceptor<String, Object> {

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerInterceptor.class);

    @Override
    public ProducerRecord<String, Object> onSend(ProducerRecord<String, Object> producerRecord) {
        TenantDto tenantDto = ContextUtil.get();
        if (tenantDto == null) {
            tenantDto = new TenantDto();
            ContextUtil.set(tenantDto);
        }
        Headers headers = producerRecord.headers();
        // 添加生产者到header到MDC
        KafkaMdcUtil.addKafkaConsumerToMdc(headers);
        // 添加mdc到信息到tenantDto
        String tenantId = ThreadContext.get(HeaderConstant.X_TENANT_ID);
        tenantDto.setTenantId(tenantId == null ? null : Long.valueOf(tenantId));
        String userId = ThreadContext.get(HeaderConstant.X_USER_ID);
        tenantDto.setUserId(userId == null ? null : Long.valueOf(userId));
        tenantDto.setUserName(ThreadContext.get(HeaderConstant.LOGIN_NAME));
        tenantDto.setRequestId(ThreadContext.get(HeaderConstant.X_REQUEST_ID));
        tenantDto.setUpstream(ThreadContext.get(HeaderConstant.X_UPSTREAM));

        KafkaConfig kafkaConfig = SpringContextUtil.getBean(KafkaConfig.class);
        String initSendMethod = kafkaConfig == null ? null : kafkaConfig.getInitSendMethod();

        // 调用 回调方法-推送RabbitMq消息前，初始化消息日志
        if (kafkaConfig != null && !ObjectUtils.isEmpty(initSendMethod)) {
            String messageId = producerRecord.key();
            messageId = StringUtils.isEmpty(messageId) ? UUID.randomUUID().toString().replace("-", "")
                    : messageId;
            KafkaCallback kafkaCallback = SpringContextUtil.getBean(KafkaCallback.class);
            kafkaCallback.invokeMsgLogMethod4Producer(initSendMethod, messageId, KafkaMsgStatusEnum.SENDING.getCode(), null,
                    producerRecord);
        }
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {
        // 清理登录租户信息
        ContextUtil.remove();
        // 清理日志
        MdcUtil.removeMdc();
    }

    @Override
    public void configure(Map<String, ?> map) {

    }

}
