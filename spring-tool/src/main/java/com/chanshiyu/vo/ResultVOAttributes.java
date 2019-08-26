package com.chanshiyu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chanshiyu
 * @Description: 分页描述信息
 */
@Data
@AllArgsConstructor
public class ResultVOAttributes {

    /** 当前页数 */
    private int pageNum;

    /** 页大小 */
    private int pageSize;

    /** 总数 */
    private long total;

}
