package com.ecom.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "ProductSubmit", description = "This model is for create and update Product")
public class ProductSubmitDto {
    @ApiModelProperty(value = "Stock keeping Unit",
                      notes = "The SKU must be unique",
                      example = "A1B2011",
                      required = true)
    @Size(min = 3, max = 64)
    private String sku;

    @Size(min = 3, max = 255)
    @ApiModelProperty(example = "iPhone 12 Pro Max", required = true)
    private String name;

    @Size(min = 3, max = 512)
    @ApiModelProperty(notes = "Short description about this product", example = "Any description", required = true)
    private String description;

    @ApiModelProperty(value = "Thumbnail URL of this product",
                      example = "https://image.com/iphone.jpg",
                      required = true)
    @Pattern(regexp = "(https?:)?//.{3,}", message = "Invalid Thumb URL", flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String thumbUrl;
}
