package com.chanshiyu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author chanshiyu
 * @date 2019/8/27 9:50
 * @description 分页描述信息
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
