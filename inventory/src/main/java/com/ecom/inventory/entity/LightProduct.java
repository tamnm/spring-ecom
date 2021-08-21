package com.ecom.inventory.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class LightProduct {

    private long id;

    @Size(min = 3, max = 64)
    private String sku;

    @Size(min = 3)
    private String name;

    public LightProduct(long id, String sku, String name) {
        this.id = id;
        this.sku = sku;
        this.name = name;
    }
}