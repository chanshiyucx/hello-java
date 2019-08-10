package com.chanshiyu.service.impl;

import com.chanshiyu.dataobject.SellerInfo;
import com.chanshiyu.repository.SellerInfoRepository;
import com.chanshiyu.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

   private SellerInfoRepository sellerInfoRepository;

    public SellerServiceImpl(SellerInfoRepository sellerInfoRepository) {
        this.sellerInfoRepository = sellerInfoRepository;
    }

    @Override
    public SellerInfo findSellerByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }

    @Override
    public SellerInfo findSellerByUsername(String username) {
        return sellerInfoRepository.findByUsername(username);
    }

    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
        return sellerInfoRepository.save(sellerInfo);
    }
}
