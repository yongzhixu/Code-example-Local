package com.convertlab.common.beta.model.request.udo;

import com.convertlab.common.beta.constants.BaseConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * DmHUb 客户子档案UDO 接口入参通用字段
 * 自定义属性、自定义事件等非固定字段不要写在这里
 *
 * @author LIUJUN
 * @date 2021-02-28 14:03:26
 */
@Data
public class BaseProfileUdoReq<T extends BaseProfileUdoRowReq> {

    /** 对象ID，在DM hub自定义页定义对象的ID */
    @NotNull
    private String objectId;

    /** 1.87前版本 对象数据列表 */
    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> rows;

    /** 1.87+版本 对象数据列表 */
    @NotNull
    private List<T> data;

    public BaseProfileUdoReq(String objectId, List<T> data) {
        this.objectId = objectId;
        this.data = data;
    }

    public BaseProfileUdoReq(String objectId, List<T> data, Integer version) {
        this.objectId = objectId;
        // 1.87之前版本，是rows传参数 入参version后面用环境变量
        if (version != null && version < BaseConstant.DM_HUB_VERSION_1_87) {
            this.rows = data;
        } else {
            this.data = data;
        }
    }
}
