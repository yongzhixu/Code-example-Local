package com.convertlab.common.beta.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页Dto
 *
 * @author LIUJUN
 * @date 2021-03-08 17:14:16
 */
@Data
public class PageDto implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 页码 */
    private Integer pageNo = 1;

    /** 每页的数量 */
    private Integer pageSize = Integer.MAX_VALUE;


}
