package com.chanshiyu.controller;

import com.chanshiyu.VO.ProductCategoryVO;
import com.chanshiyu.VO.ProductInfoVO;
import com.chanshiyu.VO.ProductVO;
import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.dataobject.ProductCategory;
import com.chanshiyu.dataobject.ProductInfo;
import com.chanshiyu.service.ProductCategoryService;
import com.chanshiyu.service.ProductInfoService;
import com.chanshiyu.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查询所有上架的商品
     * @return 上架商品列表
     */
    @GetMapping("/list")
    public ResultVO getList() {
        // 1. 查询所有商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        // 2. 查询所有类目
        // 传统方式 for 循环查询
        //List<Integer> categoryTypeList = new ArrayList<>();
        //for (ProductInfo productInfo : productInfoList) {
        //    categoryTypeList.add(productInfo.getCategoryType());
        //}

        // 精简方式（lambda java8）
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        // 3. 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                ProductInfoVO productInfoVO = new ProductInfoVO();
                // 复制属性
                BeanUtils.copyProperties(productInfo, productInfoVO);
                productInfoVOList.add(productInfoVO);
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

    @GetMapping("/category")
    public ResultVO getCategory() {
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        List<ProductCategoryVO> productCategoryVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList) {
            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            BeanUtils.copyProperties(productCategory, productCategoryVO);
            productCategoryVOList.add(productCategoryVO);
        }

        return ResultVOUtil.success(productCategoryVOList);
    }

    //@PostMapping("/category")
    //public ResultVO createCategory(@RequestBody Map<String,Object> reqMap) {
    //    String categoryName = reqMap.get("categoryName").toString();
    //    Integer categoryType = reqMap.get("categoryType");
    //
    //    ProductCategory productCategory = new ProductCategory();
    //    productCategory.setCategoryName(categoryName);
    //    productCategory.setCategoryType(categoryType);
    //    return ResultVOUtil.success(productCategoryService.save(productCategory));
    //}
}
