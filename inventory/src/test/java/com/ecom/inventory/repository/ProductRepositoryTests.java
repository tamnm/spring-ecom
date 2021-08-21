package com.ecom.inventory.repository;

import com.ecom.inventory.entity.LightProduct;
import com.ecom.inventory.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest()
class ProductRepositoryTests {
    @Autowired
    ProductRepository repository;

    @Test
    void countEmpty() {
        long count = repository.count();
        assertEquals(0, count, "There must be no item");
    }

    @Test
    void projection() {
        Product iphone = new Product();
        iphone.setName("iPhone 123");
        iphone.setSku("SMARTPHONE_123");
        iphone.setDescription("This is a smart phone");
        iphone.setThumbUrl("https://iphone.jpg");

        repository.save(iphone);
        repository.flush();

        List<LightProduct> phones = repository.findByName("phone");

        assertEquals(1, phones.size());
        assertTrue(phones.get(0).getName().toUpperCase().contains("PHONE"));
    }
}
