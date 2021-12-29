package com.convertlab.common.beta.utils;

import com.convertlab.common.beta.enums.YesOrNoEnum;
import com.convertlab.common.beta.exception.BizException;
import com.convertlab.common.beta.model.dto.JobParamDto;
import com.xxl.job.core.context.XxlJobHelper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * job 工具类
 *
 * @author liujun
 * @date 2021-04-15 22:20:10
 */
public final class JobUtil {
    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(JobUtil.class);

    private JobUtil() {

    }

    /**
     * 获取xxlJob的json参数值，比如获取入参的json字符串的a属性值：{"a":1,"b":"2021-01-02"}
     * 支持整个json以//或-- 开头的注解，注释整个json字符串
     *
     * @param property xxlJob定义的属性
     * @return 属性的值
     */
    public static String getJsonParam(String property) {
        String jobParam = XxlJobHelper.getJobParam();
        if (StringUtils.isEmpty(jobParam)) {
            return null;
        }
        String prefix1 = "//";
        String prefix2 = "--";
        if (jobParam.startsWith(prefix1) || jobParam.startsWith(prefix2)) {
            return null;
        }
        if (!JsonUtil.isJsonType(jobParam)) {
            throw new BizException("该方法只支持json字符串");
        }

        Map<String, Object> params = JsonUtil.parse(jobParam, Map.class);
        if (MapUtils.isEmpty(params)) {
            return null;
        }
        Object obj = params.get(property);
        return obj == null ? null : String.valueOf(obj);
    }

    /**
     * 获取xxlJob的json参数值,比如在页面执行任务输入参数
     *
     * @param param        参数
     * @param defaultValue 默认值
     * @return String, 如果入参有dataDate值，则取之，否则取defaultValue默认值
     */
    public static String getJsonParam(String param, String defaultValue) {
        String value = getJsonParam(param);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    /**
     * 获取xxlJob的json参数值,比如在页面执行任务输入参数
     *
     * @param param        参数
     * @param defaultValue 默认值
     * @return Integer, 如果入参有dataDate值，则取之，否则取defaultValue默认值
     */
    public static Integer getJsonParamInteger(String param, Integer defaultValue) {
        String value = getJsonParam(param);
        return StringUtils.isEmpty(value) ? defaultValue : Integer.valueOf(value);
    }

    /**
     * 获取xxlJob的json参数值,比如在页面执行任务输入参数
     *
     * @param param 参数
     * @return Integer, 如果入参有dataDate值，则取之，否则取defaultValue默认值
     */
    public static List<String> getJsonParamList(String param) {
        String value = getJsonParam(param);
        if (StringUtils.isEmpty(value)) {
            return new ArrayList<>();
        }
        String[] split = value.split(",");
        return Arrays.asList(split);
    }

    /**
     * 获取手动执行Job的参数-不通用，只适合万家项目
     *
     * @return JobParamManualDto
     */
    public static JobParamDto getJobParamDto() {
        LocalDate transTime = LocalDate.now().plusDays(-1);
        String transTimeStr = transTime.toString();
        String dmJobTransTime = transTime.toString();
        // 默认当天
        String dataDate = LocalDate.now().toString().replace("-", "");
        List<String> ids = null;
        // {"manualFlag":1,"repeatExec":1, "transTime":"2021-06-13 00:00:00","dataDate":"2021-06-17","startPage":""}
        Integer manualFlag = JobUtil.getJsonParamInteger("manualFlag", YesOrNoEnum.N.getCode());
        Integer suspendedTaskFlag = YesOrNoEnum.N.getCode();
        int startPage = 0;
        Integer endPage = null;
        // 如果是手动执行
        if (YesOrNoEnum.Y.getCode().equals(manualFlag)) {
            // 执行哪一天的业务数据
            dmJobTransTime = JobUtil.getJsonParam("dmJobTransTime");
            // 执行哪一天的业务数据
            transTimeStr = JobUtil.getJsonParam("transTime");
            // 哪天执行的
            dataDate = JobUtil.getJsonParam("dataDate");
            // 执行某些ID的业务数据
            ids = JobUtil.getJsonParamList("ids");
            // 第N页开始查询
            startPage = JobUtil.getJsonParamInteger("startPage", 0);
            // 第N页结束查询
            endPage = JobUtil.getJsonParamInteger("endPage", null);
            // 中断任务，可以手动中断正在进行中的任务，包括任务停止了但上次任务还在进行的任务
            suspendedTaskFlag = JobUtil.getJsonParamInteger("suspendedTaskFlag", YesOrNoEnum.N.getCode());
        }
        JobParamDto jobParamDto = new JobParamDto();
        jobParamDto.setManualFlag(manualFlag);
        jobParamDto.setDmJobTransTime(dmJobTransTime);
        jobParamDto.setTransTime(transTimeStr);
        jobParamDto.setDataDate(dataDate);
        jobParamDto.setIds(ids);
        jobParamDto.setStartPage(startPage);
        jobParamDto.setEndPage(endPage);
        jobParamDto.setSuspendedTaskFlag(suspendedTaskFlag);
        return jobParamDto;

    }

    /**
     * xxlJob日志
     *
     * @param msg 日志信息
     */
    public static void log(String msg) {
        LOGGER.info(msg);
        XxlJobHelper.log(msg);
    }

    /**
     * xxlJob成功日志
     *
     * @param msg 日志信息
     */
    public static void successLog(String msg) {
        LOGGER.info(msg);
        XxlJobHelper.handleSuccess(msg);
    }

    /**
     * xxlJob失败日志
     *
     * @param msg 日志信息
     */
    public static void failLog(String msg, Exception e) {
        LOGGER.error(msg, e);
        XxlJobHelper.handleFail(msg);
    }
}
