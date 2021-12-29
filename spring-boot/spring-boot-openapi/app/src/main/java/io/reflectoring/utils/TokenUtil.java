package io.reflectoring.utils;

import com.convertlab.common.beta.enums.BuEnum;
import com.convertlab.common.beta.exception.BizException;
import io.reflectoring.services.TokenService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Token工具类
 *
 * @author LIUJUN
 * @date 2021-01-20 10:13
 */
@Component
public class TokenUtil {

    private TokenUtil() {

    }

    /** 当前对象指针 */
    private static TokenUtil self;

    /** TokenService */
    @Resource
    private TokenService tokenService;

    @PostConstruct
    public void init() {
        self = this;
        self.tokenService = this.tokenService;
    }

    /**
     * 获取API的Token  -->  一般是给API接口调用的,没有上下文概念（即无前端传后端Token）
     *
     * @param buName 品牌名称
     * @return 万家的Token，或者Ole的Token，或 万家和Ole的租户ID
     */
    public static List<String> getApiToken(String buName) {
        BuEnum buEnum = BuEnum.getByKey(buName);
        if (buEnum == null) {
            throw new BizException(buName + "品牌名称不存在");
        }
        if (buEnum == BuEnum.CRV || buEnum == BuEnum.OLE) {
            return Stream.of(self.tokenService.getDmToken(buEnum)).collect(Collectors.toList());
        } else if (buEnum == BuEnum.CRV_OLE || buEnum == BuEnum.HUA_RUN) {
            return Stream.of(self.tokenService.getDmToken(BuEnum.CRV), self.tokenService.getDmToken(BuEnum.OLE))
                    .collect(Collectors.toList());
        } else {
            throw new BizException(buName + "品牌名称对应的枚举类不存在");
        }
    }

    /**
     * 获取API的Token，只支持万家、Ole，不支持"万家,Ole"  -->  一般是给API接口调用的,没有上下文概念（即无前端传后端Token）
     *
     * @param buName 品牌名称
     * @return 万家的Token，或者Ole的Token，或 万家和Ole的租户ID
     */
    public static String getApiTokenOne(String buName) {
        BuEnum buEnum = BuEnum.getByKey(buName);
        if (buEnum == null) {
            throw new BizException(buName + "品牌名称不存在");
        }
        if (buEnum != BuEnum.CRV_OLE && buEnum != BuEnum.HUA_RUN) {
            return self.tokenService.getDmToken(buEnum);
        }
        throw new BizException(buName + "品牌名称只能是万家、Ole");
    }
}
