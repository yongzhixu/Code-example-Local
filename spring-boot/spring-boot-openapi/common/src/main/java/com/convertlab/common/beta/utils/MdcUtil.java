package com.convertlab.common.beta.utils;

import com.convertlab.common.beta.constants.HeaderConstant;
import com.convertlab.common.beta.constants.LogConstants;
import com.convertlab.common.beta.interceptor.HeaderRequestInterceptor;
import com.convertlab.common.beta.model.dto.TenantDto;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * MDC工具类
 * 注意：在log4j 2.0 中，使用ThreadContext代替了MDC和NDC
 *
 * @author LIUJUN
 * @date 2021-05-17 10:39:00
 */
public class MdcUtil {
    private MdcUtil() {

    }

    /**
     * 添加header到MDC
     *
     * @param tenantDto 租户信息
     */
    public static void addHttpHeaderToMdc(TenantDto tenantDto) {
        if (tenantDto == null) {
            return;
        }
        // MDC.put(LogConstants.X_REQUEST_ID, tenantDto.getRequestId());
        // MDC.put(LogConstants.X_TENANT_ID, tenantDto.getTenantId() == null ? null : tenantDto.getTenantId().toString());
        // MDC.put(LogConstants.X_REQUEST_ID, tenantDto.getRequestId());
        CloseableThreadContext.put(LogConstants.X_REQUEST_ID, tenantDto.getRequestId());
        CloseableThreadContext.put(LogConstants.X_TENANT_ID, tenantDto.getTenantId() == null ? null : tenantDto.getTenantId().toString());
        CloseableThreadContext.put(LogConstants.X_UPSTREAM, tenantDto.getUpstream());
        CloseableThreadContext.put(LogConstants.X_USER_ID, tenantDto.getUserId() == null ? null : tenantDto.getUserId().toString());
        CloseableThreadContext.put(LogConstants.LOGIN_NAME, tenantDto.getUserName());
        CloseableThreadContext.put(LogConstants.ENV, "");
        // 服务名
        String applicationName = PropertyUtil.getValue("spring.application.name");
        ThreadContext.put(LogConstants.SERVICE, applicationName);
    }

    /**
     * 添加访问信息（header）到MDC-针对RabbitMQ消息队列消费者
     *
     * @param tenantDto 租户信息
     */
    public static void addRabbitConsumerToMdc(TenantDto tenantDto) {
        if (tenantDto == null) {
            return;
        }
        // MDC.put(LogConstants.X_REQUEST_ID, tenantDto.getRequestId());
        //MDC.put(LogConstants.X_TENANT_ID, tenantDto.getTenantId() == null ? null : tenantDto.getTenantId().toString());
        // MDC.put(LogConstants.X_REQUEST_ID, tenantDto.getRequestId());
        // 堆栈或映射中放置项时，有必要在适当的时候再次移除。为了帮助实现这一点，CloseableThreadContext 实现了 AutoCloseable 接口。
        // 这允许将项推送到堆栈或放入映射，并在调用 close ()方法时删除项，或作为 try-with-resources 的一部分自动删除项。例如，暂时将某个内容推到堆栈上，然后将其删除:
        CloseableThreadContext.put(LogConstants.X_REQUEST_ID, tenantDto.getRequestId());
        CloseableThreadContext.put(LogConstants.X_TENANT_ID, tenantDto.getTenantId() == null ? null : tenantDto.getTenantId().toString());
        // 服务名
        String applicationName = PropertyUtil.getValue("spring.application.name");
        CloseableThreadContext.put(LogConstants.X_UPSTREAM, applicationName);
        CloseableThreadContext.put(LogConstants.SERVICE, applicationName);
    }

    /**
     * 添加消息队列的信息到MDC-针对RabbitMQ消息队列
     *
     * @param exchange   交换器
     * @param routingKey 路由关键字
     * @param queue      队列名
     */
    public static void addRabbitExchangeToMdc(String exchange, String routingKey, String queue) {
        CloseableThreadContext.put(LogConstants.RABBIT_EXCHANGE, exchange);
        CloseableThreadContext.put(LogConstants.RABBIT_ROUTING_KEY, routingKey);
        CloseableThreadContext.put(LogConstants.RABBIT_QUEUE, queue);
    }

    /**
     * 添加主线程MDC日志到子线程
     *
     * @param tenantDto 租户信息
     */
    public static void addMdcToThread(TenantDto tenantDto) {
        addHttpHeaderToMdc(tenantDto);
    }

    /**
     * 移除MDC
     */
    public static void removeMdc() {
        ThreadContext.clearAll();
    }

    /**
     * 根据MDC来添加Rest请求Header拦截器(HeaderRequestInterceptor)
     */
    public static List<ClientHttpRequestInterceptor> addRestInterceptors() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        // interceptors.add(new HeaderRequestInterceptor(HeaderConstant.X_REQUEST_ID, MDC.get(LogConstants.X_REQUEST_ID)));
        // interceptors.add(new HeaderRequestInterceptor(HeaderConstant.X_TENANT_ID, MDC.get(LogConstants.X_TENANT_ID)));
        // interceptors.add(new HeaderRequestInterceptor(HeaderConstant.X_UPSTREAM, MDC.get(LogConstants.X_UPSTREAM)));
        String xRequestId = getRequestId();
        interceptors.add(new HeaderRequestInterceptor(HeaderConstant.X_REQUEST_ID, xRequestId));
        String xTenantId = ThreadContext.get(LogConstants.X_TENANT_ID);
        interceptors.add(new HeaderRequestInterceptor(HeaderConstant.X_TENANT_ID, StringUtils.defaultIfBlank(xTenantId, null)));
        // 服务名
        String applicationName = PropertyUtil.getValue("spring.application.name");
        interceptors.add(new HeaderRequestInterceptor(HeaderConstant.X_UPSTREAM, applicationName));
        return interceptors;
    }

    /**
     * 获取请求ID
     *
     * @return 请求ID
     */
    public static String getRequestId() {
        String xRequestId = ThreadContext.get(LogConstants.X_REQUEST_ID);
        if (xRequestId != null) {
            String[] splitReqIds = xRequestId.split(",");
            if (splitReqIds.length > 5) {
                xRequestId = splitReqIds[0] + "," + splitReqIds[1] + "," + splitReqIds[2] + "," + splitReqIds[3]
                        + "," + splitReqIds[4] + "," + UUID.randomUUID().toString();
            }
        } else {
            xRequestId = UUID.randomUUID().toString();
        }
        return xRequestId;
    }

    /**
     * 添加header到MDC
     *
     * @param tenantId 租户ID
     */
    public static void addJobToMdc(Long tenantId) {
        String xRequestId = getRequestId();
        ThreadContext.put(LogConstants.X_REQUEST_ID, xRequestId);
        ThreadContext.put(LogConstants.X_TENANT_ID, tenantId == null ? null : tenantId.toString());
        ThreadContext.put(LogConstants.X_UPSTREAM, "vanguard-admin-job");
        ThreadContext.put(LogConstants.X_USER_ID, "System");
        ThreadContext.put(LogConstants.LOGIN_NAME, "System");
        ThreadContext.put(LogConstants.ENV, "");
        // 服务名
        String applicationName = PropertyUtil.getValue("spring.application.name");
        ThreadContext.put(LogConstants.SERVICE, applicationName);
    }
}
