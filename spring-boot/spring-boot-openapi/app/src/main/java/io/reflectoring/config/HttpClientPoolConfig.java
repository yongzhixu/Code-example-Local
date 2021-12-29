package io.reflectoring.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wade
 * 用于声明连接池的相关配置信息
 */
@Data
@Configuration
public class HttpClientPoolConfig {

    @Value("${httpclient.maxtotal:1000}")
    private Integer maxTotal;

    @Value("${httpclient.defaultMaxPerRoute:1000}")
    private Integer defaultMaxPerRoute;

    /** 连接上服务器(握手成功)的时间，超出抛出connect timeout */
    @Value("${httpclient.connectTimeout:10000}")
    private Integer connectTimeout;

    /** 从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool */
    @Value("${httpclient.connectionRequestTimeout:10000}")
    private Integer connectionRequestTimeout;

    /** 服务器返回数据(response)的时间，超过抛出read timeout */
    @Value("${httpclient.socketTimeout:10000}")
    private Integer socketTimeout;

    /** 存活时间 */
    @Value("${httpclient.validateAfterInactivity:10000}")
    private Integer validateAfterInactivity;


}