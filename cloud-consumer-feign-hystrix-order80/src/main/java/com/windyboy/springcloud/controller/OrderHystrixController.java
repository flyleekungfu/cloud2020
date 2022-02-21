package com.windyboy.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import com.windyboy.springcloud.service.PaymentHystrixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
//@DefaultProperties(defaultFallback = "paymentInfoGlobalFallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfoOk(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfoOk(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    //@HystrixCommand
    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutFallbackMethod",
            commandProperties = @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "5000"))
    public String paymentInfoTimeout(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfoTimeout(id);
    }

    public String paymentInfoTimeoutFallbackMethod(Integer id) {
        return "我是消费者80，调用8001支付系统繁忙，请10秒钟后重新尝试、\t";
    }

    /**
     * 下面是全局fallback方法
     * @return 结果
     */
    public String paymentInfoGlobalFallbackMethod() {
        return "Global异常处理信息，请稍后再试， /(ToT)/";
    }
}
