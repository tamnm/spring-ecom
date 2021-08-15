package com.ecom.inventory.repository;

import com.ecom.inventory.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface CategoryRepository extends CrudRepository<Category, Long> {
  @Query("select c from category c where upper(c.name) like upper(concat('%', ?1, '%')) order by c.name")
  Iterable<Category> findByName(String name);
}