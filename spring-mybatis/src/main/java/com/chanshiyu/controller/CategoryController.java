package com.chanshiyu.controller;

import com.chanshiyu.dto.category.CategoryDetail;
import com.chanshiyu.pojo.Category;
import com.chanshiyu.service.CategoryService;
import com.chanshiyu.util.CommJSONResult;
import com.chanshiyu.util.CommListResult;
import com.chanshiyu.util.JSONResultAttributes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author chanshiyu
 * @date 2019/8/28 19:37
 * @description 商品类目controller
 */
@RestController
@RequestMapping("/category")
@Api(value = "商品类目管理", tags = {"商品类目管理Controller"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "类目列表", notes = "类目列表")
    @GetMapping("/list")
    public CommJSONResult<List<Category>> list(
            @ApiParam(value = "页码", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", defaultValue = "10") Integer pageSize
    ) {
        CommListResult<Category> list = this.categoryService.list(pageNum, pageSize);
        JSONResultAttributes attributes = list.getAttributes();
        return CommJSONResult.ok(list.getList(), attributes);
    }

    @ApiOperation(value = "创建类目", notes = "创建类目")
    @PostMapping("/create")
    public CommJSONResult<Category> create(@ApiParam(value = "创建类目", required = true) @Valid @RequestBody Category bean) throws Exception {
            return CommJSONResult.ok(this.categoryService.create(bean));
    }

    @ApiOperation(value = "更新类目", notes = "更新类目")
    @PutMapping("/update")
    public CommJSONResult<Category> update(@ApiParam(value = "更新类目", required = true) @Valid @RequestBody Category bean) throws Exception {
        return CommJSONResult.ok(this.categoryService.update(bean));
    }

    @ApiOperation(value = "移除类目", notes = "移除类目")
    @DeleteMapping("/delete/{id}")
    public CommJSONResult<String> delete(@ApiParam(value = "移除类目", required = true) int id) throws Exception {
        this.categoryService.delete(id);
        return CommJSONResult.ok("删除成功");
    }

    @ApiOperation(value = "类目详情", notes = "类目详情")
    @GetMapping("/detail")
    public CommJSONResult<CategoryDetail> detail(@ApiParam(value = "类目ID", required = true) int id) {
        return CommJSONResult.ok(this.categoryService.detail(id));
    }

}
