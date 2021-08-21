package com.ecom.inventory.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "category")
@Table(name = "category")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
