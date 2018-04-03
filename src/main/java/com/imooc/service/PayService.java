package com.imooc.service;

import com.imooc.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;

/**
 * @author hellozjf
 * @date 2018/4/3 22:38
 */
public interface PayService {

    PayResponse create(OrderDTO orderDTO);
}
