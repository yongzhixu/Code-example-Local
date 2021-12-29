package com.convertlab.common.beta.model.bo;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DmHUb客户接口：客户信息请求入参
 *
 * @author liujun
 * @date 2021-05-06 14:33:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerInfo extends DmHubErrorResp implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 对外系统的客户id */
    private String externalId;

    /** 姓名 */
    private String name;
    /** 用户头像地址URL */
    private String img;
    /** 性别，0和空代表未知，1代表男，2代表女 */
    private Integer gender;
    /** 手机号码 */
    private String mobile;
    /** 手机号码是否验证，值为true或false */
    private Boolean mobileVerified = false;
    /** 邮箱 */
    private String email;
    /** 邮箱是否验证 */
    private Boolean emailVerified = false;
    /** 生日 格式为: "2019-11-19" */
    private String birthday;
    /** 微信号 */
    private String wechat;
    /** 微信昵称 */
    private String nickName;
    /** 国家 */
    private String country;
    /** 省份 */
    private String province;
    /** 城市 */
    private String city;
    /** 区/县 */
    private String county;
    /** 家庭地址 */
    private String homeAddress;
    /** 公司地址 */
    private String officeAddress;
    /** QQ */
    private String qq;
    /** 当前阶段 */
    private String stage;
    /** 创建时间 */
    private String dateJoin;
    /** 创建来源 */
    private String createFrom;
    /** 创建来源名称 */
    private String createFromName;
    /** 创建方法 */
    private String createMethod;
    /** 初始来源 */
    private String source;
    /** 初始来源内容 */
    private String contentName;
    /** 公司 */
    private String company;
    /** 职位 */
    private String title;
    /** 公司行业 */
    private String industry;
    /** 公司员工数 */
    private Integer employeeNumber;
    /** 公司年收入 */
    private BigDecimal annualRevenue;
    /** 公司网站 */
    private String website;
    /** 座机 */
    private String telephone;
    /** 物主Id */
    private String ownerId;
    /** 物主 */
    private String ownerName;
    /** 是否本公司员工 */
    private Boolean isEmployee = false;

    /** 是否推荐人 */
    private Boolean isReferrer = false;

    /** 推荐人 */
    private String referrer;

    /** 推荐名称 */
    private String referrerName;
    /** 营销活动 */
    private String campaignId;
    /** 营销名称 */
    private String campaignName;
    /** 修改时间 */
    private String lastUpdated;
    /** 备注 */
    private String remark;

    /** 是否会员 */
    private Boolean isMember = false;

    /** 语言 */
    private String language;

    /** subscribeScene */
    private String subscribeScene;

    /** 系统预留的8个自定义名称的来源字段，可在设置中心设置 */
    private String source1;

    /** 系统预留的8个自定义名称的来源字段，可在设置中心设置 */
    private String source2;

    /** 系统预留的8个自定义名称的来源字段，可在设置中心设置 */
    private String source3;

    /** 系统预留的8个自定义名称的来源字段，可在设置中心设置 */
    private String source4;

    /** 系统预留的8个自定义名称的来源字段，可在设置中心设置 */
    private String source5;

    /** 系统预留的8个自定义名称的来源字段，可在设置中心设置 */
    private String source6;

    /** 系统预留的8个自定义名称的来源字段，可在设置中心设置 */
    private String source7;

    /** 系统预留的8个自定义名称的来源字段，可在设置中心设置 */
    private String source8;

    private String dateCreated;

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
