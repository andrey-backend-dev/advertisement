package com.senla.advertisement.entities;

import com.senla.advertisement.utils.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"rating", "user", "products", "comments", "created"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition="ENUM('ALIVE','BLOCKED')")
    private Status status;
    @Column(name = "created_at")
    private LocalDateTime created;
    private String about;
    @OneToOne(mappedBy = "advertisement")
    private AdvertisementRating rating;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "advertisement")
    private Set<Product> products;
    @OneToMany(mappedBy = "advertisement")
    private Set<Comment> comments;
}
