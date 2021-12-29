package com.convertlab.common.beta.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * 封装了XML转换成object，object转换成XML的代码
 *
 * @author liujun
 * @date 2021-01-31 21:13
 */
public class JaxbUtil {

    private JaxbUtil() {

    }

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(JaxbUtil.class);

    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj 对象
     * @return xml格式的字符串
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 输出文件时编码格式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            // 格式化xml输出的格式：指定是否使用换行和缩排对已编组
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            // 是否输出头信息
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            LOG.error("JAXB将对象直接转换成String类型的 XML输出错误", e);
            sw.flush();
            try {
                sw.close();
            } catch (IOException ioException) {
                LOG.error("JAXB ... StringWriter close错误", ioException);
            }
        }
        return sw.toString();
    }

    /**
     * 将对象根据路径转换成xml文件
     *
     * @param obj     对象
     * @param xmlPath xml文件路径
     * @return xml格式的字符串
     */
    public static String convertToXml(Object obj, String xmlPath) {
        FileWriter fw = null;
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            // 创建输出流
            fw = new FileWriter(xmlPath);
            marshaller.marshal(obj, fw);
        } catch (JAXBException | IOException e) {
            LOG.error("JAXB将对象根据路径转换成xml文件错误", e);
        }
        return fw == null ? null : fw.toString();
    }

    /**
     * 将String类型的xml转换成对象
     *
     * @param xmlStr String类型的xml
     * @param clazz  转化后的类
     * @return clazz的对象
     */
    public static <T> T convertXmlStrToObject(String xmlStr, Class<T> clazz) {
        T xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = (T) unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            LOG.error("JAXB将String类型的xml转换成对象错误", e);
        }
        return xmlObject;
    }

    /**
     * 将file类型的xml转换成对象
     *
     * @param xmlPath xml文件路径
     * @param clazz   转化后的类
     * @return clazz的对象
     */
    public static <T> T convertXmlFileToObject(String xmlPath, Class<T> clazz) {
        T xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fr = null;
            fr = new FileReader(xmlPath);
            xmlObject = (T) unmarshaller.unmarshal(fr);
        } catch (JAXBException | FileNotFoundException e) {
            LOG.error("JAXB将file类型的xml转换成对象错误", e);
        }
        return xmlObject;
    }
}