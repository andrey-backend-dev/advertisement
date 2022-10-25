package com.senla.advertisement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"purchaser", "product", "purchasedAt"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "purchased_at")
    private LocalDateTime purchasedAt;
    private Integer count;
    @ManyToOne
    @JoinColumn(name = "purchaser_id", referencedColumnName = "id")
    private User purchaser;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
