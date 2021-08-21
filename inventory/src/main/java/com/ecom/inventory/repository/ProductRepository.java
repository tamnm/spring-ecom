package com.ecom.inventory.repository;

import com.ecom.inventory.entity.LightProduct;
import com.ecom.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new com.ecom.inventory.entity.LightProduct(p.id, p.sku, p.name) from product p where upper(p.name) like upper(concat('%',?1,'%')) order by p.name")
    List<LightProduct> findByName(String name);
}