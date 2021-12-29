package com.convertlab.common.beta.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Header拦截器
 *
 * @author LIUJUN
 * @date 2021-05-17 10:45:05
 */
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    /** header名称 */
    private final String name;

    /** header值 */
    private final String value;

    public HeaderRequestInterceptor(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        request.getHeaders().set(name, value);
        return execution.execute(request, body);
    }
}
