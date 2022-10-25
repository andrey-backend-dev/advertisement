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
@EqualsAndHashCode(exclude = {"sender", "receiver", "sentAt"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiver;
}
