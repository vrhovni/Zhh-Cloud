package com.zhh.order.service.Impl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.zhh.order.bean.Order;
import com.zhh.order.service.OrderService;
import com.zhh.product.bean.Product;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    //服务发现
    @Resource
    DiscoveryClient discoveryClient;
    @Resource
    RestTemplate restTemplate;
    @Resource
    LoadBalancerClient loadBalancerClient;

    @Override
    public Order createOrder(Long productId, Long userId) {
        //从远程服务获取商品信息
        Product product = getProductFromRemoteWithLoadBalancerAnnotation(productId);
        Order order = new Order();
        order.setId(1L);
        //总金额
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickname("zhanghong");
        order.setAddress("zhh");
        //这里应该是从数据库中查询商品列表，获取远程商品后可以直接设置到订单中
        order.setProductList(Arrays.asList(product));

        return order;
    }

    //远程调用的三个方法
    //1.discoveryClient
    private Product getProductFromRemote(Long productId) {
        //TODO: 从远程服务获取商品信息
        List<ServiceInstance> instances = discoveryClient.getInstances("services-product");
        ServiceInstance instance = instances.get(0);
        //HTTP://localhost:9000/product/4
        String url = "http://"+instance.getHost() + ":" + instance.getPort()+"/product/"+productId;
        log.info("远程请求地址：{}", url);
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    //2.基于负载均衡的远程服务获取商品信息
    private Product getProductFromRemoteWithLoadBalancer(Long productId) {
        //TODO: 从远程服务获取商品信息
        ServiceInstance choose = loadBalancerClient.choose("services-product");
        //HTTP://localhost:9000/product/4
        String url = "http://"+choose.getHost() + ":" + choose.getPort()+"/product/"+productId;
        log.info("远程请求地址：{}", url);
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    //3。基于注解的负载均衡远程服务获取商品信息
    private Product getProductFromRemoteWithLoadBalancerAnnotation(Long productId) {

        String url = "http://services-product/product/"+productId;
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }


}
