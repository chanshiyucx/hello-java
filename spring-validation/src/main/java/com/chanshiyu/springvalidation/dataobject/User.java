package com.chanshiyu.springvalidation.dataobject;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class User {

    private Integer age;

    @NotBlank
    private String name;
}
