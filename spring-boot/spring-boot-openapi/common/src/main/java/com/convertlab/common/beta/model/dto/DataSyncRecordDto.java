package com.convertlab.common.beta.model.dto;

import com.convertlab.common.beta.model.entity.DataSyncRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据同步记录DTO-前后端接口参数
 *
 * @author LIUJUN
 * @date 2021-02-04 13:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataSyncRecordDto extends DataSyncRecord {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

}
