package com.yc.configuration;

import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/*
 配置 负载均衡策略
  自定义负载均衡策略
  三个选一个  注释掉其他的  否则会报错
  单双号   轮询  随机
 */
public class MyLoadBalancerClientConfiguration {


    // 单双号  负载均衡策略
//    @Bean
//    public OddEvenTrafficLoadBalancer oddEvenTrafficLoadBalancer(ServiceInstanceListSupplier serviceInstanceListSupplier) {
//        return new OddEvenTrafficLoadBalancer(serviceInstanceListSupplier);
//    }

//    //配置成轮询
    @Bean
    public ReactorServiceInstanceLoadBalancer reactorServiceInstanceLoadBalancer(Environment environment,
                                                                                 LoadBalancerClientFactory loadBalancerClientFactory) {

        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RoundRobinLoadBalancer( loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class) , name);
    }

    //配置成随机
//    @Bean
//    public ReactorServiceInstanceLoadBalancer reactorServiceInstanceLoadBalancer(Environment environment,
//                                                                                     LoadBalancerClientFactory loadBalancerClientFactory) {
//
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new RandomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
//    }

}
