package com.chanshiyu.service.impl;

import com.chanshiyu.dataobject.ProductInfo;
import com.chanshiyu.dto.CartDTO;
import com.chanshiyu.enums.ProductStatusEnum;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
import com.chanshiyu.repository.ProductInfoRepository;
import com.chanshiyu.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<ProductInfo> findAllByCategoryId(Integer categoryId) {
        return repository.findAllByCategoryId(categoryId);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer stock = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(stock);
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

             Integer stock = productInfo.getProductStock() - cartDTO.getProductQuantity();
             if(stock < 0) {
                 throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
             }

             productInfo.setProductStock(stock);
             save(productInfo);
         }
    }

    @Override
    public void delete(String productId) {
        repository.deleteById(productId);
    }
}
