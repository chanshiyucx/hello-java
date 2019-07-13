package com.chanshiyu.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductCategoryVO {

    @JsonProperty("id")
    private Integer categoryId;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;
}
