package io.reflectoring.services.impl;


import com.convertlab.cache.enums.BaseRedisKeyEnum;
import com.convertlab.cache.utils.RedisUtil;
import com.convertlab.common.beta.config.DmHubConfig;
import com.convertlab.common.beta.enums.BuEnum;
import com.convertlab.common.beta.model.response.TokenResp;
import com.convertlab.common.beta.utils.JsonUtil;
import io.reflectoring.services.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Token 服务实现类
 *
 * @author LIUJUN
 * @date 2021-01-19 17:33
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private DmHubConfig dmHubConfig;

    /**
     * 获取DMHub的Token
     * 缓存参考caffeineCache.json 时间是秒
     *
     * @param buEnum 品牌枚举
     * @return String
     */
    @Override
    @Cacheable(cacheNames = {"clab.cd.getDmToken"}, key = "#buEnum.toString()", unless = "#result == null")
    public String getDmToken(BuEnum buEnum) {
        // 1. 先查缓存
        String key = BaseRedisKeyEnum.getToken(buEnum);
        String value = (String) RedisUtil.get(key);
        if (!StringUtils.isEmpty(value)) {
            LOG.info("Redis获取token------->品牌={}的token={}", buEnum.getKey(), value);
            return value;
        }

        String appId = dmHubConfig.getAppId(buEnum);
        String appSecret = dmHubConfig.getAppSecret(buEnum);
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(appSecret)) {
            return null;
        }
        // 2. 再调用接口
        String url = dmHubConfig.getApiUrl() + "/oauth2/token?"
                + "app_id=" + appId
                + "&secret=" + appSecret
                + "&grant_type=client_credentials";
        // app_id有特殊字符，需要转义,否则请求报错
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        try {
            TokenResp response = restTemplate.getForObject(uri, TokenResp.class);
            LOG.info("品牌={},获取token, 入参组装为URI={}, 出参={}", buEnum.getKey(), uri.toString(), JsonUtil.toJsonString(response));
            if (response == null || response.getError() != null) {
                LOG.error("品牌=" + buEnum.getKey() + ",获取token异常");
                return null;
            }
            // 3. 存入缓存
            String accessToken = response.getAccess_token();
            Long expiresIn = response.getExpires_in();
            // 1秒目的防止获取token接口返回超时，导致下次从缓存获取时，token可能已失效
            RedisUtil.set(key, accessToken, expiresIn - 2000L);
            return accessToken;
        } catch (Exception e) {
            LOG.error("品牌=" + buEnum.getKey() + ",获取token异常:", e);
            throw new RuntimeException(e);
        }
    }
}
