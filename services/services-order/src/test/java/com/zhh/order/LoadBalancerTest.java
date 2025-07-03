package com.zhh.order;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@SpringBootTest
public class LoadBalancerTest {


    @Resource
    LoadBalancerClient loadBalancingClient;

    @Test
    public void test() {
        ServiceInstance choose = loadBalancingClient.choose("services-product");
        System.out.println("choose: "+choose.getHost()+":"+choose.getPort());
        choose = loadBalancingClient.choose("services-product");
        System.out.println("choose: "+choose.getHost()+":"+choose.getPort());
        choose = loadBalancingClient.choose("services-product");
        System.out.println("choose: "+choose.getHost()+":"+choose.getPort());
    }
}
