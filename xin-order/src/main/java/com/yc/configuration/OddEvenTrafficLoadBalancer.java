package com.yc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/*
  自定义负载均衡策略
  单双号负载均衡策略
 */
@Slf4j
public class OddEvenTrafficLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private final ServiceInstanceListSupplier serviceInstanceListSupplier;
    public OddEvenTrafficLoadBalancer(ServiceInstanceListSupplier serviceInstanceListSupplier) {
        this.serviceInstanceListSupplier = serviceInstanceListSupplier;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        return serviceInstanceListSupplier.get( request ).next().map(serviceInstances -> {
            return getInstanceResponse(serviceInstances);
        });
    }

    //单双号
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        log.info("自定义单双号负载");
        if (instances.isEmpty()){
            return new EmptyResponse();
        }
        int size = instances.size();
        if ( size == 1){
            return new DefaultResponse(instances.get(0));
        }
        int date = LocalDateTime.now().getDayOfMonth(); // 日期
        Random random = new Random();
        int randomNum; // 随机数
        if ( date % 2 == 0) { // 偶数
            do {
                randomNum = random.nextInt( size ); //生成 0 到 size-1 的随机数
            }while ( randomNum % 2 != 0); // 偶数
            return new DefaultResponse(instances.get(  randomNum  ));
        }else { // 奇数
            do {
                randomNum = random.nextInt( size ); //生成 0 到 size-1 的随机数
            }while ( randomNum % 2 == 0); // 偶数
            return new DefaultResponse(instances.get(  randomNum  ));
        }

    }
}
