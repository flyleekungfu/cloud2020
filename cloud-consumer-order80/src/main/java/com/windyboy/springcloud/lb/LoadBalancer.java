package com.windyboy.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {

    ServiceInstance serviceInstances(List<ServiceInstance> serviceInstances);
}
