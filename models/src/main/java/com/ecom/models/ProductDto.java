package com.ecom.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Product")
public class ProductDto extends ProductSubmitDto {

    @ApiModelProperty(example = "1", required = true)
    private long id;

    @ApiModelProperty(required = true, notes = "Categories of this product")
    private Set<CategoryDto> categories = new HashSet<>();
}

