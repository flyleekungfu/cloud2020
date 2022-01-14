package com.windyboy.springcloud.service;

import com.windyboy.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author jonathon
 */
public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
