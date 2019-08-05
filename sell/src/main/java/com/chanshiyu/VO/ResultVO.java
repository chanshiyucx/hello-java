package com.chanshiyu.VO;

import com.chanshiyu.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Http 请求返回结果
 */
@Data
public class ResultVO<T> {

    @ApiModelProperty("状态码")
    private Integer status;

    @ApiModelProperty("状态信息")
    private String msg;

    @ApiModelProperty("返回数据")
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("分页信息")
    private PageDTO attributes;
}
