package com.chanshiyu.controller;

import com.chanshiyu.dataobject.Category;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.service.CategoryService;
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
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "类目列表", notes = "类目列表")
    @GetMapping("/list")
    public ResultVO<List<Category>> list(@ApiParam(value = "页码", defaultValue = "1") Integer pageNum,
                                         @ApiParam(value = "每页大小", defaultValue = "10") Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<Category> page = categoryService.findList(pageRequest);
        ResultAttributesVO resultAttributesVO = new ResultAttributesVO(page.getPageable().getPageNumber() + 1, page.getSize(), page.getTotalElements());
        return ResultVO.ok(page.getContent(), resultAttributesVO);
    }

    @ApiOperation(value = "创建类目", notes = "创建类目")
    @PostMapping("/create")
    public ResultVO<Category> create(@ApiParam(value = "创建类目", required = true) @Valid @RequestBody Category bean, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【创建类目】参数不正确，category={}", bean);
            return ResultVO.errorMsg(bindingResult.getFieldError().getDefaultMessage());
        }
        Category category = new Category();
        BeanUtils.copyProperties(bean, category);
        Category result = categoryService.save(category);
        return ResultVO.ok(result);
    }

    @ApiOperation(value = "更新类目", notes = "更新类目")
    @PutMapping("/update")
    public ResultVO<Category> update(@ApiParam(value = "更新类目", required = true) @Valid @RequestBody Category bean, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【更新类目】参数不正确，productCategory={}", bean);
            return ResultVO.errorMsg(bindingResult.getFieldError().getDefaultMessage());
        }

        // 判断类目是否已存在
        Category category = categoryService.findOne(bean.getId());
        if (category == null) {
            log.error("【更新类目】类目不存在，productCategory={}", bean);
            return ResultVO.errorMsg(ResultEnum.CATEGORY_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(bean, category);
        Category result = categoryService.save(category);
        return ResultVO.ok(result);
    }

    @ApiOperation(value = "移除类目", notes = "移除类目")
    @DeleteMapping("/delete")
    public ResultVO delete(@ApiParam(value = "类目ID", required = true) Integer id) {
        categoryService.delete(id);
        return ResultVO.ok();
    }

}
