package com.epam.sp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class APIGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(APIGatewayApplication.class, args);
    }


}
