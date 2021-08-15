package com.ecom.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@ApiModel(
        value = "CategorySubmit",
        description = "This model is for update and create category")
public class CategorySubmitDto {

    @Size(max = 255, min = 3)
    @ApiModelProperty(example = "Television", required = true)
    private String name;
}
