package com.windyboy.springcloud.alibaba.dao;

import com.windyboy.springcloud.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    /**
     * 新建订单
     * @param order 订单
     */
    void create(Order order);

    /**
     * 2 修改订单状态,从0改为1
     * @param userId 用户id
     * @param status 订单状态
     * @return 修改结果
     */
    int update(@Param("userId") Long userId, @Param("status") Integer status);

}
