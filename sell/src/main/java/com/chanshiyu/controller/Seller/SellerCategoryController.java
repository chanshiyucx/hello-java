package com.chanshiyu.controller.Seller;

import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.dataobject.ProductCategory;
import com.chanshiyu.dataobject.ProductInfo;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
import com.chanshiyu.form.ProductCategoryForm;
import com.chanshiyu.service.ProductCategoryService;
import com.chanshiyu.service.ProductInfoService;
import com.chanshiyu.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unchecked")
@Slf4j
@RestController
@RequestMapping("/seller/category")
@Api(tags = "商品类目")
public class SellerCategoryController {

    private final ProductCategoryService productCategoryService;

    private final ProductInfoService productInfoService;

    public SellerCategoryController(ProductCategoryService productCategoryService, ProductInfoService productInfoService) {
        this.productCategoryService = productCategoryService;
        this.productInfoService = productInfoService;
    }

    @ApiOperation(value = "类目列表")
    @GetMapping("/list")
    public ResultVO<List<ProductCategory>> list() {
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        return ResultVOUtil.success(productCategoryList);
    }

    @ApiOperation(value = "创建类目")
    @ApiImplicitParam(name = "productCategoryForm", value = "商品类目", required = true, dataType = "ProductCategoryForm")
    @PostMapping("/create")
    public ResultVO<ProductCategory> create(@Valid @RequestBody ProductCategoryForm productCategoryForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【创建类目】参数不正确，productCategoryForm={}", productCategoryForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryForm, productCategory);
        ProductCategory result = productCategoryService.save(productCategory);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "更新类目")
    @ApiImplicitParam(name = "productCategoryForm", value = "商品类目", required = true, dataType = "ProductCategoryForm")
    @PutMapping("/update")
    public ResultVO<ProductCategory> update(@Valid @RequestBody ProductCategoryForm productCategoryForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【更新类目】参数不正确，categoryForm={}", productCategoryForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        // 判断类目是否已存在
        ProductCategory productCategory = productCategoryService.findOne(productCategoryForm.getCategoryId());
        if (productCategory == null) {
            log.error("【更新类目】类目不存在，categoryForm={}", productCategoryForm);
            throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
        }

        BeanUtils.copyProperties(productCategoryForm, productCategory);
        ProductCategory result = productCategoryService.save(productCategory);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "移除类目")
    @ApiImplicitParam(name = "categoryId", value = "类目ID", required = true, dataType = "Integer")
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestParam("categoryId") Integer categoryId) {

        // 判断是否类目已绑定商品
        List<ProductInfo> productInfoList = productInfoService.findAllByCategoryId(categoryId);
        if (!CollectionUtils.isEmpty(productInfoList)) {
            log.error("【移除类目】类目尚在使用，不能删除，categoryId={}", categoryId);
            throw new SellException(ResultEnum.CATEGORY_IS_ACTIVE);
        }

        productCategoryService.delete(categoryId);
        return ResultVOUtil.success();
    }
}
