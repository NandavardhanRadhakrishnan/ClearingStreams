package com.cs.ClearingStreams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.TimeZone;

@SpringBootApplication
public class ClearingStreamsApplication {


	public static void main(String[] args) {
        SpringApplication.run(ClearingStreamsApplication.class, args);
	}

}
