package com.chanshiyu.controller.Admin;

import com.chanshiyu.service.ProductCategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/category")
@Api(tags = "商品类目")
public class AdminCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;
}
