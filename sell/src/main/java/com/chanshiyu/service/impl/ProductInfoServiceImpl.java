package com.chanshiyu.service.impl;

import com.chanshiyu.dataobject.ProductInfo;
import com.chanshiyu.dto.CartDTO;
import com.chanshiyu.enums.ProductStatusEnum;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
import com.chanshiyu.repository.ProductInfoRepository;
import com.chanshiyu.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode()); // 查询上架商品
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
         for(CartDTO cartDTO: cartDTOList) {
             ProductInfo productInfo = findOne(cartDTO.getProductId());
             if (productInfo == null) {
                 throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
             }

             Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
             if(result < 0) {
                 throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
             }

             productInfo.setProductStock(result);
             save(productInfo);
         }
    }
}
