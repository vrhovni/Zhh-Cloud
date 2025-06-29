package com.zhh.product;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

import static com.alibaba.cloud.nacos.discovery.ServiceCache.getInstances;

@SpringBootTest
public class DiscoveryTest {

    @Resource
    DiscoveryClient discoveryClient;

    @Resource
    NacosServiceDiscovery nacosServiceDiscovery;

    @Test
    void discoveryClientTest() {
        for (String service : discoveryClient.getServices()) {
            System.out.println("Service: " + service);
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println("Instance: " + instance.getServiceId() + "ip:" + instance.getHost() + "port:" + instance.getPort());
            }
        }
    }
}
