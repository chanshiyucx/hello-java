package com.chanshiyu.service;

import com.chanshiyu.dataobject.ProductInfo;
import com.chanshiyu.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    /** 查询单个商品 */
    ProductInfo findOne(String productId);

    /** 查询上架商品列表 */
    List<ProductInfo> findUpAll();

    /** 查询所有商品列表 */
    Page<ProductInfo> findAll(Pageable pageable);

    /** 保存商品 */
    ProductInfo save(ProductInfo productInfo);

    /** 增加库存 */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减少库存 */
    void decreaseStock(List<CartDTO> cartDTOList);
}
