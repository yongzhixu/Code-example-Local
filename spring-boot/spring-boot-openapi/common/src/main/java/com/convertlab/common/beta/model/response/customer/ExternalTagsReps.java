package com.convertlab.common.beta.model.response.customer;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 导入外部标签返回参数
 *
 * @author LIUJUN
 * @date 2021-02-05 22:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalTagsReps extends DmHubErrorResp {

    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 成功创建标识 */
    private String code;

    /** 成功导入的标签列表 */
    private List<String> success;

    /** 导入失败的标签列表 */
    private List<String> failed;
}
