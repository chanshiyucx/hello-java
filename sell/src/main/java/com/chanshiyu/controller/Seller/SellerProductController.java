package com.chanshiyu.controller.Seller;

import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.dataobject.ProductInfo;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
import com.chanshiyu.form.ProductInfoForm;
import com.chanshiyu.service.ProductInfoService;
import com.chanshiyu.utils.KeyUtil;
import com.chanshiyu.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unchecked")
@Slf4j
@RestController
@RequestMapping("/seller/product")
@Api(tags = "商品管理")
public class SellerProductController {

    private final ProductInfoService productInfoService;

    public SellerProductController(ProductInfoService productInfoService) {
        this.productInfoService = productInfoService;
    }

    @ApiOperation(value = "商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Integer"),
    })
    @GetMapping("/list")
    public ResultVO<List<ProductInfo>> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
        return ResultVOUtil.successPage(productInfoPage);
    }

    @ApiOperation(value = "新增商品")
    @ApiImplicitParam(name = "productInfoForm", value = "商品详情", required = true, dataType = "ProductInfoForm")
    @CacheEvict(cacheNames = "product", key = "123456")
    @PostMapping("/create")
    public ResultVO<ProductInfo> create(@Valid @RequestBody ProductInfoForm productInfoForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【新增商品】参数不正确，productInfoForm={}", productInfoForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productInfoForm, productInfo);
        productInfo.setProductId(KeyUtil.genUniqueKey());
        ProductInfo result = productInfoService.save(productInfo);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "更新商品")
    @ApiImplicitParam(name = "productInfoForm", value = "商品详情", required = true, dataType = "ProductInfoForm")
    @CacheEvict(cacheNames = "product", key = "123456")
    @PutMapping("/update")
    public ResultVO<ProductInfo> update(@Valid @RequestBody ProductInfoForm productInfoForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【更新商品】参数不正确，productInfoForm={}", productInfoForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        // 判断商品是否已存在
        ProductInfo productInfo = productInfoService.findOne(productInfoForm.getProductId());
        if (productInfo == null) {
            log.error("【更新商品】商品不存在，productInfoForm={}", productInfoForm);
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        BeanUtils.copyProperties(productInfoForm, productInfo);
        ProductInfo result = productInfoService.save(productInfo);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "移除商品")
    @ApiImplicitParam(name = "productId", value = "商品ID", required = true, dataType = "Integer")
    @CacheEvict(cacheNames = "product", key = "123456")
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("productId") String productId) {
        productInfoService.delete(productId);
        return ResultVOUtil.success();
    }
}
