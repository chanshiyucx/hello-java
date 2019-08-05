package com.chanshiyu.controller.Seller;

import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.dataobject.ProductCategory;
import com.chanshiyu.service.ProductCategoryService;
import com.chanshiyu.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller/category")
@SuppressWarnings("unchecked")
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryName", value = "类目名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryType", value = "类目编号", required = true, dataType = "Integer")
    })
    @PostMapping("/create")
    public ResultVO<ProductCategory> create(@RequestParam("categoryName") String categoryName,
                                            @RequestParam("categoryType") Integer categoryType) {
        ProductCategory productCategory = new ProductCategory(categoryName, categoryType);
        ProductCategory result = productCategoryService.save(productCategory);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "更新类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "类目ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "categoryName", value = "类目名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryType", value = "类目编号", required = true, dataType = "Integer")
    })
    @PutMapping("/update")
    public ResultVO<ProductCategory> update(@RequestParam("categoryId") Integer categoryId,
                                            @RequestParam("categoryName") String categoryName,
                                            @RequestParam("categoryType") Integer categoryType) {
        ProductCategory productCategory = productCategoryService.findOne(categoryId);
        productCategory.setCategoryName(categoryName);
        productCategory.setCategoryType(categoryType);
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
