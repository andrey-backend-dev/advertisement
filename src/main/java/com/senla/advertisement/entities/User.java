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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"roles", "advertisements", "purchases", "outgoingMessages", "incomingMessages", "comments", "rates", "registeredSince"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition="ENUM('BLOCKED', 'ALIVE')")
    private Status status;
    private String name;
    private String phone;
    private String email;
    @Column(name = "registered_since")
    private LocalDateTime registeredSince;
    private String about;
    private Integer money;
    @ManyToMany
    @JoinTable(name = "user2role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToMany(mappedBy = "user")
    private Set<Advertisement> advertisements;
    @OneToMany(mappedBy = "purchaser")
    private Set<Purchase> purchases;
    @OneToMany(mappedBy = "sender")
    private Set<Message> outgoingMessages;
    @OneToMany(mappedBy = "receiver")
    private Set<Message> incomingMessages;
    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;
    @OneToMany(mappedBy = "user")
    private Set<AdvertisementRate> rates;


    public User(Integer id, String username, String password, Status status, String name, String phone, String email,
                LocalDateTime registeredSince, String about, Integer money) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.registeredSince = registeredSince;
        this.about = about;
        this.money = money;
    }
}
