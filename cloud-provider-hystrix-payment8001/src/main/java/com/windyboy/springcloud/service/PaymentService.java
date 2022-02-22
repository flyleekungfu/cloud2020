package com.windyboy.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.cloud.netflix.hystrix.HystrixProperties;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfoOk(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfoOk，id：" + id + " 正常访问！";
    }

    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    public String paymentInfoTimeout(Integer id) {
        int timeNumber = 5;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "线程池： " + Thread.currentThread().getName()
                + "   paymentInfoTimeout,id:" + id + " 耗时(秒):" + timeNumber;
    }

    public String paymentInfoTimeoutHandler(Integer id) {
        return "调用支付接口超时或异常、\t" + "\t当前线程池名字" + Thread.currentThread().getName();
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求数达到后才计算
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 休眠时间窗
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 错误率达到多少跳闸
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("id不能为负数");
        }

        String simpleUUID = IdUtil.simpleUUID();

        return  Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + simpleUUID;
    }

    public String paymentCircuitBreakerFallback(Integer id) {
        return "id 不能为负数,请稍后再试，id: " + id;
    }

}
