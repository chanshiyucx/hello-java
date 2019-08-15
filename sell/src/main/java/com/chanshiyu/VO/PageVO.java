package com.chanshiyu.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageVO implements Serializable {

    private static final long serialVersionUID = 5854379338435262416L;

    @ApiModelProperty("当前页码")
    private Integer pageNum;

    @ApiModelProperty("分页大小")
    private Integer pageSize;

    @ApiModelProperty("总数")
    private Long total;
}
