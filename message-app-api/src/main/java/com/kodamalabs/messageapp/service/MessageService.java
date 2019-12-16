package com.kodamalabs.messageapp.service;

import com.kodamalabs.messageapp.entity.Message;
import org.springframework.data.domain.Page;

import javax.persistence.EntityNotFoundException;

public interface MessageService {

  void add(final Message message);

  void update(final Message message);

  void remove(final Long messageId) throws EntityNotFoundException;

  Message getMessageById(final Long messageId) throws EntityNotFoundException;

  Page<Message> getAllMessages(final int page, final int size);
}
