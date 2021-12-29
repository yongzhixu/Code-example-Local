package com.convertlab.common.beta.model.bo;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * DmHUb客户接口：客户信息基础信息（不含自定义属性）
 *
 * @author liujun
 * @date 2021-05-06 14:33:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerAttr extends DmHubErrorResp implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 客户id */
    private String id;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr1;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr2;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr3;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr4;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr5;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr6;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr7;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr8;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr9;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr10;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr11;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr12;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr13;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr14;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr15;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr16;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr17;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr18;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr19;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr20;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr21;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr22;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr23;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr24;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr25;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr26;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr27;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr28;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr29;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr30;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr31;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr32;

    /** 自定义字段，可在系统客户属性中查看其类型。当属性为数值类型则传值为数值字符；当为日期类型则传值为yyyy-MM-dd格式字符串；当为日期时间类型则传值为UTC时区、yyyy-MM-dd'T'HH:mm:ss'Z'格式字符串 */
    private String attr33;
}
