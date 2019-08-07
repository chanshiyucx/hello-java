package com.chanshiyu.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageVO {

    @ApiModelProperty("当前页码")
    private Integer pageNum;

    @ApiModelProperty("分页大小")
    private Integer pageSize;

    @ApiModelProperty("总数")
    private Long total;
}
