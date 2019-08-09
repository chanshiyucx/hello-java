package com.chanshiyu.service;

import com.chanshiyu.dataobject.SellerInfo;

public interface SellerService {

    SellerInfo findSellerByOpenid(String openid);

    SellerInfo save(SellerInfo sellerInfo);
}
