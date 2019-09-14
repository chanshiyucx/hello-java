package com.chanshiyu.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author chanshiyu
 * @date 2019/8/29 9:21
 * @description 列表数据
 */
@Data
@AllArgsConstructor
public class CommListResult<T> {

    /** 响应中的数据 */
    private List<T> list;

    /** 描述信息 */
    private JSONResultAttributes attributes;

}
