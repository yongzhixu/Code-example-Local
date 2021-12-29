package com.convertlab.kafkapipe.interceptor;

import com.convertlab.common.beta.constants.HeaderConstant;
import com.convertlab.common.beta.model.dto.TenantDto;
import com.convertlab.common.beta.utils.ContextUtil;
import com.convertlab.common.beta.utils.MdcUtil;
import com.convertlab.kafkapipe.utils.KafkaMdcUtil;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * kafka消费者拦截器
 *
 * @author LIUJUN
 * @date 2021-05-18 11:11:27
 */
public class KafkaConsumerInterceptor implements ConsumerInterceptor<String, String> {

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerInterceptor.class);

    @Override
    public ConsumerRecords onConsume(ConsumerRecords<String, String> consumerRecords) {
        TenantDto tenantDto = ContextUtil.get();
        if (tenantDto == null) {
            tenantDto = new TenantDto();
            ContextUtil.set(tenantDto);
        }
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            Headers headers = consumerRecord.headers();
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
            // 只循环一次，因为相同的
            break;
        }

        return consumerRecords;
    }

    @Override
    public void close() {
        // 清理登录租户信息
        ContextUtil.remove();
        // 清理日志
        MdcUtil.removeMdc();
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {
    }

    @Override
    public void configure(Map<String, ?> map) {

    }

}
