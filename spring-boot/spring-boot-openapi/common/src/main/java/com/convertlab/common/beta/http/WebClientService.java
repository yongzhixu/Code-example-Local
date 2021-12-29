package com.convertlab.common.beta.http;

import groovy.util.logging.Slf4j;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebClientService {
    @Value("${convertlab.apiUrl}:")
    private String apiUrl;

    private static final String BODY_VALUE = "bodyValue";
    private static final ParameterizedTypeReference<Map<String, String>> MAP_RESPONSE_REF = new ParameterizedTypeReference<Map<String, String>>() {
    };

    public WebClient createWebClientInstance(String baseUrl){
        baseUrl = Optional.of(baseUrl).orElse(apiUrl);
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", baseUrl))
                .build();
        return client;
    }

    private HttpClient createTimeoutHttpclient(Integer timeout){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .responseTimeout(Duration.ofMillis(timeout))
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS)));

        return httpClient;
    }

    public WebClient createTimeoutWebClientInstance(String baseUrl,Integer timeout){

        WebClient timeoutClient = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(createTimeoutHttpclient(timeout)))
                .build();
        return timeoutClient;
    }


    // helper methods to create default instances
    private WebClient createDefaultClient() {
        return WebClient.create(apiUrl);
    }

    private WebClient.RequestBodyUriSpec createDefaultPostRequest() {
        return createDefaultClient().post();
    }

    private WebClient.RequestBodySpec createDefaultPostResourceRequest() {
        return createDefaultPostRequest().uri("/resource");
    }

    private WebClient.RequestHeadersSpec<?> createDefaultPostResourceRequestResponse() {
        return createDefaultPostResourceRequest().bodyValue(BODY_VALUE);
    }

    // helper methods to retrieve a response based on different steps of the process (specs)
    private Mono<String> retrieveResponse(WebClient client) {
        return client.post()
                .uri("/resource")
                .bodyValue(BODY_VALUE)
                .retrieve()
                .bodyToMono(String.class);
    }

    private Mono<String> retrieveResponse(WebClient.RequestBodyUriSpec spec) {
        return spec.uri("/resource")
                .bodyValue(BODY_VALUE)
                .retrieve()
                .bodyToMono(String.class);
    }

    private Mono<Map<String, String>> retrieveGetResponse(WebClient.RequestHeadersUriSpec<?> spec) {
        return spec.uri("/resource")
                .retrieve()
                .bodyToMono(MAP_RESPONSE_REF);
    }

    private Mono<String> retrieveResponse(WebClient.RequestBodySpec spec) {
        return spec.bodyValue(BODY_VALUE)
                .retrieve()
                .bodyToMono(String.class);
    }

    private Mono<String> retrieveResponse(WebClient.RequestHeadersSpec<?> spec) {
        return spec.retrieve()
                .bodyToMono(String.class);
    }



    public <T> T postForObject(String url, @Nullable Object request, Class<T> responseType,
                               Object... uriVariables) throws RestClientException {
        WebClient webClient = createWebClientInstance(url);
        return webClient.post().uri("").bodyValue(request).retrieve().bodyToMono(responseType).block();
    }

    public <T> T getForObject(String url, Class<T> responseType,
                               Object... uriVariables) throws RestClientException {
        WebClient webClient = createWebClientInstance(url);
        return webClient.get().uri("").retrieve().bodyToMono(responseType).block();
    }

}
