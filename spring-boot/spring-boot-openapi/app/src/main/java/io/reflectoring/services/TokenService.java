package io.reflectoring.services;

import com.convertlab.common.beta.enums.BuEnum;

/**
 * Token 接口
 *
 * @author LIUJUN
 * @date 2021-01-19 17:32
 */
public interface TokenService {

    /**
     * 获取DMHub的Token
     * @param buEnum 品牌枚举
     *
     * @return String
     */
    String getDmToken(BuEnum buEnum);
}
