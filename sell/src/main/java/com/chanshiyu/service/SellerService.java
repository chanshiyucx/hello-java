package com.chanshiyu.service;

import com.chanshiyu.dataobject.SellerInfo;

public interface SellerService {

    SellerInfo findSellerByOpenid(String openid);

    SellerInfo findSellerByUsername(String username);

    SellerInfo save(SellerInfo sellerInfo);
}
