package com.chanshiyu.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author chanshiyu
 * @date 2019/8/27 14:06
 * @description 商品类目实体类
 */
@Entity
@Data
@DynamicUpdate
@ApiModel(value = "商品类目")
public class Category {

    @ApiModelProperty(value = "类目ID", dataType = "Integer")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "类目名称", dataType = "String")
    @NotBlank(message = "类目名称不能为空")
    private String name;

    @ApiModelProperty(value = "创建时间", dataType = "String")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "更新时间", dataType = "String")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateTime;

}
