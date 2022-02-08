package com.windyboy.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MyLB implements LoadBalancer {

    AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public ServiceInstance serviceInstances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }

    /**
     * 负载均衡轮询算法，rest接口第几次请求数 % 服务器集群总数 = 实际调用服务器位置下标
     *
     */
    private int getAndIncrement() {
        int current;
        int next;

        do {
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!atomicInteger.compareAndSet(current, next));
        System.out.println("****第" + next + "次访问");
        return next;
    }
}
