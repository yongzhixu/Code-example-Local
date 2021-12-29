package com.convertlab.common.beta.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * IP工具类
 *
 * @author LIUJUN
 * @date 2021-02-16 20:49:48
 */
public class IpUtil {
    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(IpUtil.class);

    /** 服务器ip地址 */
    private static String serverIp;

    /** 未知 */
    private static final String UNKNOWN = "unknown";

    /** ip最大长度 */
    private static final int IP_MAX_LENGTH = 15;

    /** 本地ip地址 */
    private static final String LOCAL_HOST = "127.0.0.1";

    /** 无限制访问ip地址 */
    private static final String ANY_ACCESS_HOST = "0:0:0:0:0:0:0:1";

    /** 多个代理ip地址分隔符 */
    private static final String IP_PROXY_SEPARATE = ",";

    static {
        InetAddress ia = null;
        try {
            // 根据网卡取本机配置的IP
            ia = InetAddress.getLocalHost();
            serverIp = ia.getHostAddress();
            LOG.info("=============根据网卡取本机配置的IP:{}===============", serverIp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取ip地址
     *
     * @param request HttpServletRequest
     * @return string
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // cast localhost
        if (LOCAL_HOST.equals(ip) || ANY_ACCESS_HOST.equals(ip)) {
            ip = getServerIp();
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割 ,如："***.***.***.***".length() = 15
        if (ip != null && ip.length() > IP_MAX_LENGTH) {
            if (ip.indexOf(IP_PROXY_SEPARATE) > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }


    /**
     * 获取服务器的ip地址
     *
     * @return String
     */
    public static String getServerIp() {
        return serverIp;
    }

    /**
     * 获取所有的网卡的ip v4地址,key为网卡地址，value为ip地址
     *
     * @return hash map
     */
    public static Map<String, String> getLocalIPV4() {
        Map<String, String> map = new HashMap<>(16);
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    if (ip instanceof Inet4Address) {
                        map.put(ni.getName(), ip.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取所有ipv6地址
     *
     * @return hash map
     */
    public static Map<String, String> getLocalIPV6() {
        Map<String, String> map = new HashMap<>(16);
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    if (ip instanceof Inet6Address) {
                        map.put(ni.getName(), ip.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
