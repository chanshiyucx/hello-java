package com.chanshiyu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chanshiyu
 * @Description: 分页描述信息
 */
@Data
@AllArgsConstructor
public class ResultAttributesVO {

    /** 当前页数 */
    private Integer pageNum;

    /** 页大小 */
    private Integer pageSize;

    /** 总数 */
    private Long total;

}
