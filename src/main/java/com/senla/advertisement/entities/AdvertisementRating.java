package com.senla.advertisement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"advertisement", "advertisementRates"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "advertisementrating")
public class AdvertisementRating {
    @Id
    private Integer id;
    private Short value;
    @Column(name = "promoted_value")
    private Short promotedValue;
    @Column(name = "payment_period")
    private LocalDateTime paymentPeriod;
    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Advertisement advertisement;
    @OneToMany(mappedBy = "advertisementRating")
    private Set<AdvertisementRate> advertisementRates;
}
