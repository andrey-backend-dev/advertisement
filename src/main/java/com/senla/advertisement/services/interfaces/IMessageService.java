package com.senla.advertisement.services.interfaces;

import com.senla.advertisement.dto.ContentDTO;
import com.senla.advertisement.dto.MessageDTO;
import com.senla.advertisement.dto.MessageRequestDTO;

public interface IMessageService {
    MessageDTO create(MessageRequestDTO messageDTO, String token);
    boolean deleteById(int id, String token);
    MessageDTO updateById(int id, ContentDTO contentDTO, String token);
}
