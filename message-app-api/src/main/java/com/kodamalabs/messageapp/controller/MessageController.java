package com.kodamalabs.messageapp.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.kodamalabs.messageapp.entity.Message;
import com.kodamalabs.messageapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/message")
public class MessageController {

  @Autowired
  private MessageService messageService;

  @PostMapping()
  public ResponseEntity add(@Valid @RequestBody final Message message) {
    messageService.add(message);
    return new ResponseEntity(CREATED);
  }

  @PutMapping()
  public ResponseEntity update(@Valid @RequestBody final Message message) {
    messageService.update(message);
    return new ResponseEntity(NO_CONTENT);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable final Long id) throws EntityNotFoundException {
    messageService.remove(id);
    return new ResponseEntity(OK);
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity getById(@PathVariable final Long id) throws EntityNotFoundException {
    Message message = messageService.getMessageById(id);
    return new ResponseEntity(message, OK);
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity getAll(@RequestParam("page") final int page, @RequestParam("size") final int size) {
    Page<Message> allMessages = messageService.getAllMessages(page, size);
    return new ResponseEntity(allMessages, OK);
  }

}
