package com.cs.ClearingStreams;

import com.cs.ClearingStreams.util.kafka.KafkaTopics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableConfigurationProperties(KafkaTopics.class)
@EnableAspectJAutoProxy(exposeProxy = true)
public class ClearingStreamsApplication {


    public static void main(String[] args) {
        SpringApplication.run(ClearingStreamsApplication.class, args);
    }

}
