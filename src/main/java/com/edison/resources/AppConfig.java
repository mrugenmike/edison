package com.edison.resources;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.UnknownHostException;

/**
 * Created by mrugen on 5/7/15.
 */
@SpringBootApplication
public class AppConfig {
    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class,args);
    }

    @Bean
    MongoClient client() throws UnknownHostException {
        return new MongoClient(new MongoClientURI("mongodb://root:root@ds031802.mongolab.com:31802/edison"));
    }

}
