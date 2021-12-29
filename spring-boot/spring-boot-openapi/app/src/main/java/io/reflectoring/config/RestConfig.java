package io.reflectoring.config;

import com.convertlab.common.beta.utils.MdcUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * RestTemplate 配置
 *
 * @author LIUJUN
 * @date 2021-01-18 16:04
 */
@Configuration
@Slf4j
@Getter
public class RestConfig {

    /** http请求代理地址 */
    @Value("${http.proxy.host:}")
    private String proxyHost;

    /** http请求代理端口 */
    @Value("${http.proxy.port:22}")
    private int proxyPort;

    @Autowired
    private HttpClientPoolConfig httpClientPoolConfig;

    @Bean("restTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient());
        //单位为ms
        factory.setReadTimeout(20 * 1000);
        //单位为ms
        factory.setConnectTimeout(60 * 1000);

        RestTemplate restTemplate = new RestTemplate(factory);
        // 添加Header拦截器
        restTemplate.setInterceptors(MdcUtil.addRestInterceptors());
        // 处理字符集
        setMessageConverter(restTemplate);
        return restTemplate;
    }

    @Bean("restTemplateProxy")
    public RestTemplate restTemplateProxy() {
        if (StringUtils.isEmpty(this.proxyHost)) {
            log.warn("-------http代理配置http.proxy.port={},故restTemplateProxy将不走代理，等同restTemplate请求-------", this.proxyHost);
            return restTemplate(null);
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(getProxyFactory());
        // 处理字符集
        setMessageConverter(restTemplate);
        return restTemplate;
    }

    /**
     * 设置消息转换
     *
     * @param restTemplate RestTemplate
     */
    private void setMessageConverter(RestTemplate restTemplate) {
        // 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为 "ISO-8859-1"）
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
        // 解决text/plain的，比如微信等
        restTemplate.getMessageConverters().add(new NewMappingJackson2HttpMessageConverter());
    }


    /**
     * 由于默认构造的MappingJackson2HttpMessageConverter中的supportedMediaTypes只支持：applicantion/json的，
     * 所以再添加MediaType.TEXT_PLAIN的MediaType的支持
     */
    private static final class NewMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

        @Autowired
        public NewMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            //添加text/plain类型的支持.微信接口会用到
            mediaTypes.add(MediaType.TEXT_PLAIN);
            setSupportedMediaTypes(mediaTypes);
        }
    }


    /**
     * http客户端配置
     *
     * @return HttpClient
     */
    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(httpClientPoolConfig.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(httpClientPoolConfig.getDefaultMaxPerRoute());
        connectionManager.setValidateAfterInactivity(httpClientPoolConfig.getValidateAfterInactivity());
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(httpClientPoolConfig.getSocketTimeout())
                .setConnectTimeout(httpClientPoolConfig.getConnectTimeout())
                .setConnectionRequestTimeout(httpClientPoolConfig.getConnectionRequestTimeout())
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }


    /**
     * 代理转发调用工厂
     *
     * @return SimpleClientHttpRequestFactory
     */
    public ClientHttpRequestFactory getProxyFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //单位为ms
        factory.setReadTimeout(10 * 1000);
        //单位为ms
        factory.setConnectTimeout(30 * 1000);
        // 代理的url网址或ip, port端口
        InetSocketAddress address = new InetSocketAddress(this.proxyHost, this.proxyPort);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        factory.setProxy(proxy);
        log.info("http.proxy.url 请求代理{}", address);
        return factory;
    }

}
