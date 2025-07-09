package com.zhh.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "order")
public class OrderProperties {

    String orderTimeout;

    String orderAutoConfirm;

    String DbUrl;

}
