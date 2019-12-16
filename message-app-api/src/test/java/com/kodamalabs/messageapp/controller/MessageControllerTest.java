package com.kodamalabs.messageapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kodamalabs.messageapp.entity.Message;
import com.kodamalabs.messageapp.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class MessageControllerTest {

  private static final String URI = "/message";

  private static final Long MESSAGE_ID = 1L;

  @Mock
  private MessageService messageService;

  @InjectMocks
  private MessageController messageController = new MessageController();

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp(){
    mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
  }

  @Test
  void testAdd() throws Exception {
    final String messageBody = "{\"content\": \"Lorem ipsum message\"}";
    mockMvc.perform(post(URI).contentType(APPLICATION_JSON).content(messageBody)).andExpect(status().isCreated());
    verify(messageService, times(1)).add(any(Message.class));
  }

  @Test
  void testUpdate() throws Exception {
    final String messageBody = "{\"id\": 1,\"content\": \"Lorem Ipsum omini\", \"createdDate\": "
        + "1575835297710}";
    mockMvc.perform(put(URI).contentType(APPLICATION_JSON).content(messageBody)).andExpect(status().isNoContent());
    verify(messageService, times(1)).update(any(Message.class));
  }

  @Test
  void testDelete() throws Exception {
    mockMvc.perform(delete(URI + "/" + MESSAGE_ID)).andExpect(status().isOk());
    verify(messageService, times(1)).remove(eq(MESSAGE_ID));
  }

  @Test
  void testGetById() throws Exception {
    when(messageService.getMessageById(eq(MESSAGE_ID))).thenReturn(new Message());
    MvcResult mvcResult =
        mockMvc.perform(get(URI + "/" + MESSAGE_ID)).andExpect(status().isOk()).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    assertNotNull(response);
    verify(messageService, times(1)).getMessageById(eq(MESSAGE_ID));
  }

  @Test
  void testGetAll() throws Exception {
    final int page = 0;
    final int size = 2;
    when(messageService.getAllMessages(eq(page),eq(size))).thenReturn(mock(Page.class));
    MvcResult mvcResult =
        mockMvc.perform(get(URI + "?page="+page+"&size="+size)).andExpect(status().isOk()).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    assertNotNull(response);
    verify(messageService, times(1)).getAllMessages(eq(page),eq(size));
  }
}