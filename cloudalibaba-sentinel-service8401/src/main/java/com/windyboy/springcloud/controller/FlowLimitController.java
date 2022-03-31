package com.windyboy.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() {
        return "----testA";
    }

    /**
     * 测试并发线程数限流
     * @return String
     */
    @GetMapping("/testB")
    public String testB() {
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "----testB";
    }

    /**
     * 测试排队
     * @return String
     */
    @GetMapping("/testC")
    public String testC() {
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "----testC";
    }

    /**
     * 测试RT，慢调用
     */
    @GetMapping("/testD")
    public String testD() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testD 测试RT");
        return "----testD";
    }

    @GetMapping("/testE")
    public String testE() {
        log.info("testE 测试异常数");
        int age = 10 / 0;
        return "----testE 测试异常数";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "dealTestHotKey")
    public String testHotKey(@RequestParam(required = false, value = "p1") String p1,
                             @RequestParam(required = false, value = "p2") String p2) {
        return "----testHotKey";
    }

    public String dealTestHotKey(String p1, String p2, BlockException blockException) {
        return "----deal_testHotKey"; // sentinel的默认提示都是： Blocked by Sentinel (flow limiting)
    }

}
