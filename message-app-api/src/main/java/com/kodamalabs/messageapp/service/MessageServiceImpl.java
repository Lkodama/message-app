package com.kodamalabs.messageapp.service;

import com.kodamalabs.messageapp.entity.Message;
import com.kodamalabs.messageapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
public class MessageServiceImpl implements MessageService {

  @Autowired
  private MessageRepository messageRepository;

  @Override
  public void add(final Message message) {
    message.setCreatedDate(new Date().getTime());
    messageRepository.save((message));
  }

  @Override
  public void update(final Message message) {
    messageRepository.save(message);
  }

  @Override
  public void remove(final Long messageId) throws EntityNotFoundException {
    Optional<Message> message = messageRepository.findById(messageId);
    if (!message.isPresent()) {
      throw new EntityNotFoundException("Message not found");
    }
    messageRepository.delete(message.get());
  }

  @Override
  public Message getMessageById(final Long messageId) throws EntityNotFoundException {
    Optional<Message> message = messageRepository.findById(messageId);
    if (!message.isPresent()) {
      throw new EntityNotFoundException("Message not found");
    }
    return message.get();
  }

  @Override
  public Page<Message> getAllMessages(final int page, final int size) {
    Pageable pageRequest = PageRequest.of(page, size);

    Page<Message> messages = messageRepository.findAll(pageRequest);
    return messages;
  }
}
