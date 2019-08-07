package com.chanshiyu.controller.Buyer;

import com.chanshiyu.VO.ProductInfoVO;
import com.chanshiyu.VO.ProductVO;
import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.dataobject.ProductCategory;
import com.chanshiyu.dataobject.ProductInfo;
import com.chanshiyu.service.ProductCategoryService;
import com.chanshiyu.service.ProductInfoService;
import com.chanshiyu.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/buyer/product")
@Api(tags = "买家商品")
public class BuyerProductController {

    private final ProductInfoService productInfoService;

    private final ProductCategoryService productCategoryService;

    @Autowired
    public BuyerProductController(ProductInfoService productInfoService, ProductCategoryService productCategoryService) {
        this.productInfoService = productInfoService;
        this.productCategoryService = productCategoryService;
    }

    @ApiOperation(value = "上架商品列表")
    @GetMapping("/list")
    public ResultVO<List<ProductVO>> list() {
        // 1. 查询所有商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        // 2. 查询所有类目（lambda java8）
        List<Integer> categoryIdList = productInfoList.stream().map(ProductInfo::getCategoryId).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findAllById(categoryIdList);

        // 3. 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryId().equals(productCategory.getCategoryId())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
