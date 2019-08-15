package com.chanshiyu.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SellerVO implements Serializable {

    private static final long serialVersionUID = -932114199811417290L;

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
