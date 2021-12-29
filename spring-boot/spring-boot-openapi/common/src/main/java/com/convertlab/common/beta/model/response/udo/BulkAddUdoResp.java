package com.convertlab.common.beta.model.response.udo;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 批量新增自定对象接口返回参数
 *
 * @author LIUJUN
 * @date 2021-03-02 11:43:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BulkAddUdoResp extends DmHubErrorResp {

    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 更新数据的bk列表 1.87之前版本* */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> update;

    /** 新增数据的bk列表 1.87之前版本 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> save;

    /** 更新数据的bk列表  1.87+版本 */
    private List<String> updated;

    /** 新增数据的bk列表  1.87+版本 */
    private List<String> added;
}
