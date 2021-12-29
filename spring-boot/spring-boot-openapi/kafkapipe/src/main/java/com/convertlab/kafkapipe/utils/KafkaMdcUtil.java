package com.convertlab.kafkapipe.utils;

import com.convertlab.common.beta.constants.HeaderConstant;
import com.convertlab.common.beta.constants.LogConstants;
import com.convertlab.common.beta.utils.MdcUtil;
import com.convertlab.common.beta.utils.PropertyUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.logging.log4j.ThreadContext;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;

public class KafkaMdcUtil {


    /**
     * 添加访问信息（header）到MDC-针对kafka消息队列消费者
     *
     * @param headers header信息
     */
    public static void addKafkaConsumerToMdc(Headers headers) {
        if (headers == null) {
            return;
        }
        for (Header header : headers) {
            String key = header.key();
            if (HeaderConstant.X_REQUEST_ID.equals(key) || HeaderConstant.X_UPSTREAM.equals(key)
                    || HeaderConstant.X_USER_ID.equals(key) || HeaderConstant.LOGIN_NAME.equals(key)
                    || HeaderConstant.X_TENANT_ID.equals(key)) {
                ThreadContext.put(key, header.value() == null ? null : new String(header.value()));
            }
        }
    }

    /**
     * 将MDC的信息添加header-针对Kafka消息队列
     *
     * @param record 租户信息
     */
    public static void setKafkaHeader(ProducerRecord<String, Object> record) {
        if (record == null) {
            return;
        }
        Charset utf8 = StandardCharsets.UTF_8;
        String xRequestId = MdcUtil.getRequestId();
        record.headers().add(HeaderConstant.X_REQUEST_ID, xRequestId.getBytes(utf8));
        String tenantId = ThreadContext.get(LogConstants.X_TENANT_ID);
        tenantId = StringUtils.defaultIfBlank(tenantId, null);
        record.headers().add(HeaderConstant.X_TENANT_ID, tenantId == null ? null : tenantId.getBytes(utf8));
        // 服务名
        String applicationName = PropertyUtil.getValue("spring.application.name");
        record.headers().add(HeaderConstant.X_UPSTREAM, applicationName == null ? null : applicationName.getBytes(utf8));
    }

}
