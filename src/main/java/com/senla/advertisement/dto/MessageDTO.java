package com.senla.advertisement.dto;

import com.senla.advertisement.entities.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private String content;

    private LocalDateTime sentAt;

    private String sender;

    private String receiver;


    public MessageDTO(Message message) {
        this.content = message.getContent();
        this.sentAt = message.getSentAt();
        this.sender = message.getSender().getUsername();
        this.receiver = message.getReceiver().getUsername();
    }
}
