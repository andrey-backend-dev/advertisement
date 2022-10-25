package com.senla.advertisement.dao;

import com.senla.advertisement.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageDAO extends JpaRepository<Message, Integer> {
    @Query(value = "select * from message where " +
            "sender_id = ?1 && receiver_id = ?2 || " +
            "receiver_id = ?1 && sender_id = ?2 " +
            "order by sent_at", nativeQuery = true)
    List<Message> getChat(int senderId, int receiverId);
}
