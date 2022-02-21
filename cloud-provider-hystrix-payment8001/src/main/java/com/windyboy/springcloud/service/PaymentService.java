package com.windyboy.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
}
