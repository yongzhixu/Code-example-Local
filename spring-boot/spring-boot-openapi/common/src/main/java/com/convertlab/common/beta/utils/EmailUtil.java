package com.convertlab.common.beta.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;

/**
 * Email工具类
 *
 * @author LIUJUN
 * @date 2021-02-16 20:49:48
 */
@Component
@Slf4j
public class EmailUtil {

    private EmailUtil() {

    }

    /** 当前对象指针 */
    private static EmailUtil self;

    /** email 发送器 */
    private static JavaMailSender javaMailSender;

    @PostConstruct
    public void init() {
        self = this;
    }

    /**
     * 简单文本邮件
     *
     * @param formAddress 发件人
     * @param toAddress   收件人
     * @param title       主题
     * @param content     内容
     */
    public static void sendSimpleMail(String formAddress, String[] toAddress, String title, String content) {
        if (ObjectUtils.isEmpty(formAddress)) {
            log.error("邮件发送人信息未配置");
            return;
        }
        try {
            //建立邮件消息
            SimpleMailMessage mainMessage = new SimpleMailMessage();
            //发送者
            mainMessage.setFrom(formAddress);
            //接收者
            mainMessage.setTo(toAddress);
            //发送的标题
            mainMessage.setSubject(title);
            //发送的内容
            mainMessage.setText(content);
            javaMailSender.send(mainMessage);
        } catch (Exception e) {
            log.error("发送邮件失败", e);
        }
        log.info("发送邮件成功");
    }
}