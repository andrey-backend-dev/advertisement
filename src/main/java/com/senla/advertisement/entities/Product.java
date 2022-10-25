package com.senla.advertisement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"advertisement", "purchases"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer cost;
    private Integer available;
    @ManyToOne
    @JoinColumn(name = "ad_id", referencedColumnName = "id")
    private Advertisement advertisement;
    @OneToMany(mappedBy = "product")
    private Set<Purchase> purchases;
}
