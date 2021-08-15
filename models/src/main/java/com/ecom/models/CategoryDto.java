package com.ecom.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@ApiModel(value = "Category", description = "Category model")
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryDto extends CategorySubmitDto {

    @ApiModelProperty(example = "100", required = true)
    private long id;
}
