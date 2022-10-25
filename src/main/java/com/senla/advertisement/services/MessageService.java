package com.senla.advertisement.services;

import com.senla.advertisement.dao.MessageDAO;
import com.senla.advertisement.dao.UserDAO;
import com.senla.advertisement.dto.ContentDTO;
import com.senla.advertisement.dto.MessageDTO;
import com.senla.advertisement.dto.MessageRequestDTO;
import com.senla.advertisement.entities.Message;
import com.senla.advertisement.entities.User;
import com.senla.advertisement.security.jwt.JwtProvider;
import com.senla.advertisement.services.interfaces.IMessageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MessageService implements IMessageService {

    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDAO userDAO;

    
    @Override
    @Transactional
    public MessageDTO create(MessageRequestDTO messageDTO, String token) {
        String senderUsername = jwtProvider.getUsername(token);
        User receiver = userDAO.findByUsername(messageDTO.getReceiver());
        User sender = userDAO.findByUsername(senderUsername);

        if (receiver.getAdvertisements() != null || sender.getAdvertisements() != null) {
            Message message = new Message();
            message.setContent(messageDTO.getContent());
            message.setReceiver(receiver);
            message.setSender(sender);
            message.setSentAt(LocalDateTime.now());
            return new MessageDTO(messageDAO.save(message));
        }

        return null;
    }

    @Override
    @Transactional
    public boolean deleteById(int id, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        Message message = messageDAO.findById(id).orElse(null);

        if (user == message.getSender()) {
            messageDAO.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public MessageDTO updateById(int id, ContentDTO contentDTO, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        Message message = messageDAO.findById(id).orElse(null);

        if (user == message.getSender()) {

            if (contentDTO.getContent() != null && !contentDTO.getContent().equals(message.getContent())) {
                message.setContent(contentDTO.getContent());
            }

            return new MessageDTO(message);
        }

        return null;
    }
}
