package com.convertlab.common.beta.utils;

import com.convertlab.common.beta.model.dto.TenantDto;

/**
 * 上下文工具类
 *
 * @author LIUJUN
 * @date 2021-01-26 20:13
 */
public class ContextUtil {

    /** 构造方法私有化 */
    private ContextUtil() {
    }

    /** 线程隔离 */
    private static final ThreadLocal<TenantDto> context = new ThreadLocal<>();

    /**
     * 存放用户信息
     *
     * @param tenantDto 租户信息
     */
    public static void set(TenantDto tenantDto) {
        context.set(tenantDto);
    }

    /**
     * 获取用户信息
     *
     * @return TenantDto
     */
    public static TenantDto get() {
        return context.get();
    }

    /**
     * 清除当前线程内引用，防止内存泄漏
     */
    public static void remove() {
        context.remove();
    }

}