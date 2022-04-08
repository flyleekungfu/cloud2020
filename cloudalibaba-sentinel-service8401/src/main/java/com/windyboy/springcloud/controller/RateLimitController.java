package com.windyboy.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.windyboy.springcloud.entities.CommonResult;
import com.windyboy.springcloud.entities.Payment;
import com.windyboy.springcloud.myhandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200, "按照资源名称限流测试", new Payment(2020L, "serial001"));
    }

    public CommonResult handleException(BlockException blockException) {
        return  new CommonResult(444,blockException.getClass().getCanonicalName() + "\t 服务不可用");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl() {
        return  new CommonResult(200,"按照byUrl限流测试",new Payment(2020L,"serial002"));
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler", blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handlerException")
    public CommonResult customerBlockHandler() {
        return  new CommonResult(200,"按照客户自定义限流测试",new Payment(2020L,"serial003"));
    }
}
