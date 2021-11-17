package com.jack.userservice.controller;

import com.jack.userservice.openfeign.OrderServiceFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RefreshScope   // 自动能够获取到nacos-server-config的数据更改信息
public class UserController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring Cloud. ";
    }

    // 从Spring IoC容器中找到能够服务发现的类
    @Resource
    private DiscoveryClient discoveryClient;

    // 测试服务发现：根据服务名称是否可以找到urls
    @RequestMapping("/discovery")
    public String discovery() {
        /*
                [
                    "http://192.168.56.1:9091",
                    "http://192.168.56.1:9092"

                    "http://192.168.56.1:9091",
                    "http://192.168.56.1:9092"
                ]
         */
        List<ServiceInstance> instances = this.discoveryClient.getInstances("order-service");
        List<String> urls = instances.stream().map(instance -> instance.getUri().toString()).collect(Collectors.toList());
        // 随机负载均衡算法
        int index = ThreadLocalRandom.current().nextInt(urls.size());
        return urls.get(index);
    }


    // 从Spring IoC容器中找到能够负载均衡的类
    @Resource
    private LoadBalancerClient loadBalancerClient;

    // 使用ribbon的负载均衡能力
    @RequestMapping("/ribbonApi")
    public String ribbonApi() {
        /*
                [
                    "http://192.168.56.1:9091",
                    "http://192.168.56.1:9092"
         */

        // http://192.168.56.1:9091   猜测ribbon默认使用的负载均衡方式是轮询
        return this.loadBalancerClient.choose("order-service").getUri().toString();
    }


    // 从Spring IoC容器中找到...
    @Resource
    private RestTemplate restTemplate;

    // xxx
    @RequestMapping("/restTemplateCall")
    public String restTemplateCall() {
        // http://192.168.56.1:9091/order/query
        String url = this.loadBalancerClient.choose("order-service").getUri().toString()+"/order/query";
        return this.restTemplate.getForObject(url,String.class);
    }

    @Resource
    private RestTemplate restTemplateRibbon;

    // xxx
    @RequestMapping("/restTemplateRibbonCall")
    public String restTemplateRibbonCall() {
        // TODO: 是不是@LoadBalanced注解，相当于通过这个order-service，已经进行了服务发现+负载均衡，然后替换了http://order-service/order/query为   [Interceptor  拦截器？]
                            // http://192.168.56.1:9091/order/query
        // 本质：就是调用一个远程服务的接口   OrderController#query
        return this.restTemplateRibbon.getForObject("http://order-service/order/query",String.class);
    }


    // 思考：对于RestTemplate这种调用方式   希望在user-service中能够通过这种方式进行调用Object#method   面向对象

    @Resource
    private OrderServiceFeignClient orderServiceFeignClient;   // 要创建该接口的实现类，并且放到Spring IoC容器中

    // 这就是我们想要的面向对象的调用方式
    @RequestMapping("/testOpenFeign")
    public String testOpenFeign() {
        return this.orderServiceFeignClient.query();
    }


    @Value("${person.username}")
    private String personUsername;

    @RequestMapping("/testNacosConfig")
    public String testNacosConfig() {
        return this.personUsername;
    }
}












