package com.imooc.service;

import com.imooc.dataobject.SellerInfo;

/**
 * @author hellozjf
 * @date 2018/4/8 18:51
 */
public interface SellerService {

    /**
     * 通过openid查询卖家端信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
