package com.kodamalabs.messageapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kodamalabs.messageapp.entity.Message;
import com.kodamalabs.messageapp.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
class MessageServiceImplTest {

  @Mock
  private MessageRepository repository;
  @InjectMocks
  private MessageService messageService = new MessageServiceImpl();

  @Test
  public void testAdd() {
    Message mockMessage = mock(Message.class);
    messageService.add(mockMessage);
    verify(repository, times(1)).save(eq(mockMessage));
  }

  @Test
  public void testAddValidationException() {
    Message mockMessage = mock(Message.class);
    when(repository.save(eq(mockMessage))).thenThrow(new RuntimeException());
    assertThrows(RuntimeException.class, () -> messageService.add(mockMessage));
  }

  @Test
  public void testUpdate() {
    Message mockMessage = mock(Message.class);
    messageService.update(mockMessage);
    verify(repository, times(1)).save(eq(mockMessage));
  }

  @Test
  public void testUpdateValidationException() {
    Message mockMessage = mock(Message.class);
    when(repository.save(eq(mockMessage))).thenThrow(new RuntimeException());
    assertThrows(RuntimeException.class, () -> messageService.update(mockMessage));
  }

  @Test
  public void testRemove() {
    Long messageId = 1L;
    Message message = mock(Message.class);
    Optional<Message> optionalMessage = Optional.of(message);
    when(repository.findById(eq(messageId))).thenReturn(optionalMessage);

    messageService.remove(messageId);
    verify(repository, times(1)).delete(eq(message));
  }

  @Test
  public void testRemoveMessageNotFound() {
    Long messageId = 1L;
    Optional<Message> optionalMessage = Optional.empty();
    when(repository.findById(eq(messageId))).thenReturn(optionalMessage);

    assertThrows(EntityNotFoundException.class, () -> messageService.remove(messageId));
  }

  @Test
  public void testGetMessageById() {
    Long messageId = 1L;
    Long createdDate = 1L;
    String content = "test";
    Message message = new Message();
    message.setId(messageId);
    message.setCreatedDate(createdDate);
    message.setContent(content);
    Optional<Message> optionalMessage = Optional.of(message);

    when(repository.findById(eq(messageId))).thenReturn(optionalMessage);
    Message messageById = messageService.getMessageById(messageId);
    assertEquals(messageId, messageById.getId());
    assertEquals(content, messageById.getContent());
    assertEquals(createdDate, messageById.getCreatedDate());

  }

  @Test
  public void testGetMessageByIdNotFound() {
    Long messageId = 1L;
    Optional<Message> optionalMessage = Optional.empty();
    when(repository.findById(eq(messageId))).thenReturn(optionalMessage);

    assertThrows(EntityNotFoundException.class, () -> messageService.getMessageById(messageId));
  }


  @Test
  public void testGetAllMessages() {
    int page = 0;
    int size = 2;
    Page<Message> pagedMessages = mock(Page.class);
    List<Message> messages = Arrays.asList(mock(Message.class), mock(Message.class));
    when(pagedMessages.getTotalElements()).thenReturn(2L);
    when(pagedMessages.getContent()).thenReturn(messages);
    when(repository.findAll(any(Pageable.class))).thenReturn(pagedMessages);

    Page<Message> allMessages = messageService.getAllMessages(page, size);

    assertEquals(pagedMessages.getTotalElements(), allMessages.getTotalElements());
    assertEquals(pagedMessages.getContent().size(), allMessages.getContent().size());
  }
}