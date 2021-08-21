package com.ecom.inventory.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

@Entity(name = "price")
@Table(name = "price", indexes = {
        @Index(name = "product_id_idx", columnList = "product_id"),
        @Index(name = "created_date_idx", columnList = "created_date"),
        @Index(name = "price_idx", columnList = "product_price")
})
@Getter
@Setter
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false, updatable = false)
    private Long productId;

    @Column(name = "product_price", updatable = false, nullable = false)
    private Integer productPrice;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;
}
