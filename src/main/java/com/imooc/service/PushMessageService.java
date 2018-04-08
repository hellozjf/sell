package com.imooc.service;

import com.imooc.dto.OrderDTO;

/**
 * @author hellozjf
 * @date 2018/4/8 22:48
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
