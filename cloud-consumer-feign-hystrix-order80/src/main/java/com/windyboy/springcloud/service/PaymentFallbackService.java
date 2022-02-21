package com.windyboy.springcloud.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfoOk(Integer id) {
        return "paymentInfoOk fallback";
    }

    @Override
    public String paymentInfoTimeout(Integer id) {
        return "paymentInfoTimeout fallback";
    }
}
