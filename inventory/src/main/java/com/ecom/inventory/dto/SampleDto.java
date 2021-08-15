package com.ecom.inventory.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "Sample DTO, for testing purpose")
@Data
public class SampleDto {
    @Size(min = 3, max = 128)
    @NotNull
    private String name;

    @Min(1)
    @Max(1000)
    @NotNull
    private int size;
}
