package com.zhh.order.service.Impl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.zhh.order.bean.Order;
import com.zhh.order.service.OrderService;
import com.zhh.product.bean.Product;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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

    @Override
    public Order createOrder(Long productId, Long userId) {
        //从远程服务获取商品信息
        Product product = getProductFromRemote(productId);
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

}
