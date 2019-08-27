package com.chanshiyu.controller;

import com.chanshiyu.dataobject.ProductCategory;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.CommException;
import com.chanshiyu.service.ProductCategoryService;
import com.chanshiyu.vo.ResultAttributesVO;
import com.chanshiyu.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author chanshiyu
 * @date 2019/8/27 14:13
 * @description 商品类目
 */
@RestController
@RequestMapping("/category")
@Slf4j
@Api(value = "商品类目管理", tags = {"商品类目管理Controller"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @ApiOperation(value = "类目列表", notes = "类目列表")
    @GetMapping("/list")
    public ResultVO<List<ProductCategory>> list(@ApiParam(value = "页码", defaultValue = "1") Integer pageNum,
                                                @ApiParam(value = "每页大小", defaultValue = "10") Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<ProductCategory> page = productCategoryService.findList(pageRequest);
        ResultAttributesVO resultAttributesVO = new ResultAttributesVO(page.getPageable().getPageNumber() + 1, page.getSize(), page.getTotalElements());
        return ResultVO.ok(page.getContent(), resultAttributesVO);
    }

    @ApiOperation(value = "创建类目", notes = "创建类目")
    @PostMapping("/create")
    public ResultVO<ProductCategory> create(@ApiParam(value = "创建类目", required = true) @Valid @RequestBody ProductCategory bean, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【创建类目】参数不正确，productCategory={}", bean);
            throw new CommException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(bean, productCategory);
        ProductCategory result = productCategoryService.save(productCategory);
        return ResultVO.ok(result);
    }

    @ApiOperation(value = "更新类目", notes = "更新类目")
    @PutMapping("/update")
    public ResultVO<ProductCategory> update(@ApiParam(value = "更新类目", required = true) @Valid @RequestBody ProductCategory bean, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【更新类目】参数不正确，productCategory={}", bean);
            throw new CommException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        // 判断类目是否已存在
        ProductCategory productCategory = productCategoryService.findOne(bean.getId());
        if (productCategory == null) {
            log.error("【更新类目】类目不存在，productCategory={}", bean);
            throw new CommException(ResultEnum.CATEGORY_NOT_EXIST);
        }
        BeanUtils.copyProperties(bean, productCategory);
        ProductCategory result = productCategoryService.save(productCategory);
        return ResultVO.ok(result);
    }

    @ApiOperation(value = "移除类目", notes = "移除类目")
    @DeleteMapping("/delete")
    public ResultVO delete(@ApiParam(value = "类目ID", required = true) Integer id) {
        productCategoryService.delete(id);
        return ResultVO.ok();
    }

}
