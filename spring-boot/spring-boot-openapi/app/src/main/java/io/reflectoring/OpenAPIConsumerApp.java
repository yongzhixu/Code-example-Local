package io.reflectoring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = {"io.reflectoring","com.convertlab.kafkapipe",
        "com.convertlab.cache",
        "com.convertlab.common",
        "com.convertlab.elasticsearch"},
        // 屏蔽 Kafka 、Rabbit自动加载
        exclude = {RabbitAutoConfiguration.class})
@MapperScan("io.reflectoring.mapper")
public class OpenAPIConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(OpenAPIConsumerApp.class, args);
    }
}
