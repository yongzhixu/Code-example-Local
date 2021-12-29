package com.convertlab.common.beta.utils;

import com.convertlab.common.beta.config.DmHubConfig;
import com.convertlab.common.beta.enums.BuEnum;
import com.convertlab.common.beta.exception.BizException;
import com.convertlab.common.beta.model.dto.TenantDto;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 租户工具类
 *
 * @author LIUJUN
 * @date 2021-01-27 15:51
 */
@Component
public class TenantUtil {

    private TenantUtil() {

    }

    /** 当前对象指针 */
    private static TenantUtil self;

    /** TokenService */
    @Resource
    private DmHubConfig dmHubConfig;

    @PostConstruct
    public void init() {
        self = this;
        self.dmHubConfig = this.dmHubConfig;
    }

    /**
     * 获取API的tenantId  -->  一般是给API接口调用的,没有上下文概念（即无前端传后端tenantId）
     *
     * @param buName 品牌名称 万家、Ole、万家,Ole
     * @return 万家的租户ID，或者Ole的租户ID，或 万家和Ole的租户ID
     */
    public static List<Long> getApiTenantId(String buName) {
        BuEnum buEnum = BuEnum.getByKey(buName);
        if (buEnum == BuEnum.CRV) {
            return Stream.of(self.dmHubConfig.getCrvTenantId()).collect(Collectors.toList());
        } else if (buEnum == BuEnum.OLE) {
            return Stream.of(self.dmHubConfig.getOleTenantId()).collect(Collectors.toList());
        } else if (buEnum == BuEnum.CRV_OLE || buEnum == BuEnum.HUA_RUN) {
            return Stream.of(self.dmHubConfig.getCrvTenantId(), self.dmHubConfig.getOleTenantId())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 获取访问的tenantId  -->  适合与controller接口相关接口调用，即前端传后端tenantId
     *
     * @return 前端传后端tenantId
     */
    public static Long getAccessTenantId() {
        TenantDto tenantDto = ContextUtil.get();
        if (tenantDto == null) {
            return null;
        }
        return tenantDto.getTenantId();
    }

    /**
     * 获取访问的userId  -->  适合与controller接口相关接口调用，即前端传后端userId
     *
     * @return 前端传后端用户Id
     */
    public static Long getAccessUserId() {
        TenantDto tenantDto = ContextUtil.get();
        if (tenantDto == null) {
            return null;
        }
        return tenantDto.getUserId();
    }

    /**
     * 根据租户获取BuEnum枚举
     *
     * @param tenantId 租户ID
     * @return BuEnum
     */
    public static BuEnum getBuEnum(Long tenantId) {
        if (self.dmHubConfig.getCrvTenantId().equals(tenantId)) {
            return BuEnum.CRV;
        } else if (self.dmHubConfig.getOleTenantId().equals(tenantId)) {
            return BuEnum.OLE;
        }
        throw new BizException(tenantId + "租户ID无法匹配到枚举");
    }
}
