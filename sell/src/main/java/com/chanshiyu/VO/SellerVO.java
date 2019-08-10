package com.chanshiyu.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SellerVO {

    @ApiModelProperty("用户ID")
    @JsonProperty("id")
    private String sellerId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("Token")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;
}
