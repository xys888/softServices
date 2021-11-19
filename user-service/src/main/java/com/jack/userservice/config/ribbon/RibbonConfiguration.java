package com.jack.userservice.config.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2021年11月19日15:05
 */
@Configuration
public class RibbonConfiguration {

    //Ribbon的默认负载均衡规则是
    //RoundRobinRule 轮询选择，轮询 index，选择 index 对应位置的 Server
    //WeightedResponseTimeRule 继承于RoundRobinRule 根据节点的响应时间来试算权重。响应时间越长权重越低，反之，响应时间越小的机器，被选中的概率大。
    //BestAvailableRule 在过滤掉故障服务后，它会基于过去30分钟的统计结果选取当前并发量的最小的服务节点，也就是说最闲的节点，如果统计结果未生成，则会采用轮询的方式
    //ZoneAvoidanceRule 组合过滤条件 包括zone级和可用级别 在eureka注册中一个服务节点有一个zone region 和 url 三个身份 zone可以被认为机房大区 这里会对zone的健康状况进行检测。可用级别的和availabilityFilteringRule非常像，会过滤到当前并发量大 或者处于熔断状态的服务节点
    //availabilityFilterRule 依赖randomRule来选取节点 但是并非来着不拒 必须满足 节点处于非熔断阶段或者当前活跃数量不超过阈值
    @Bean
    public IRule rule(){
        //随机选择
        return new RandomRule();
    }
}
