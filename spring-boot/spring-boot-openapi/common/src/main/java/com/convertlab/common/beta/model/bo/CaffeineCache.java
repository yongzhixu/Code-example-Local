package com.convertlab.common.beta.model.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 本地缓存
 *
 * @author LIUJUN
 * @date 2021-01-22 10:20
 */
@Data
public class CaffeineCache implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 缓存名称 */
    @NotNull
    private String cacheName;

    /** 有效时间 秒*/
    @NotNull
    private  Long expired;

    /** 最大数量 */
    @NotNull
    private  Long maxSize;

}
