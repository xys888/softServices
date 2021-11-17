package com.jack.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }


    @Bean  // 向Spring IoC容器注入一个Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean  // 向Spring IoC容器注入一个Bean
    @LoadBalanced // 具备了Ribbon能力的RestTemplate
    public RestTemplate restTemplateRibbon(){
        return new RestTemplate();
    }
}
