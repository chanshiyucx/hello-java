package com.chanshiyu.controller.Seller;

import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.dataobject.ProductCategory;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
import com.chanshiyu.form.ProductCategoryForm;
import com.chanshiyu.service.ProductCategoryService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seller/category")
@SuppressWarnings("unchecked")
@Slf4j
@Api(tags = "商品类目")
public class SellerCategoryController {

    private final ProductCategoryService productCategoryService;

    public SellerCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @ApiOperation(value = "类目列表")
    @GetMapping("/list")
    public ResultVO<List<ProductCategory>> list() {
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        return ResultVOUtil.success(productCategoryList);
    }

    @ApiOperation(value = "创建类目")
    @PostMapping("/create")
    public ResultVO<ProductCategory> create(@Valid @RequestBody ProductCategoryForm productCategoryForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【创建类目】参数不正确，productCategoryForm={}", productCategoryForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        // 判断是否已存在该类目编号
        List<Integer> categoryTypeList = new ArrayList<>();
        categoryTypeList.add(productCategoryForm.getCategoryType());
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);
        if (!CollectionUtils.isEmpty(productCategoryList)) {
            log.error("【创建类目】类目已存在，categoryForm={}", productCategoryForm);
            throw new SellException(ResultEnum.CATEGORY_IS_EXIST);
        }

        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryForm, productCategory);
        ProductCategory result = productCategoryService.save(productCategory);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "更新类目")
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
        productCategoryService.delete(categoryId);
        return ResultVOUtil.success();
    }
}
