package com.npn.spring.learning.logger.smallsite.controllers;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
public class ScriptControllerTest extends TestCase {

    @InjectMocks
    private ScriptController controller;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /**
     * Тестирование GET метода отправки времени от сервера
     */
    @Test
    public void testGetTimeFromServer() {
        try {
            MvcResult result = mockMvc.perform(get("/scripts/date")).andReturn();

            assertEquals(200, result.getResponse().getStatus());
            assertEquals("text/plain;charset=UTF-8", result.getResponse().getContentType());
            assertEquals("UTF-8", result.getResponse().getCharacterEncoding());
            assertTrue(result.getResponse().getContentAsString().contains("UTC"));
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
    }

    /**
     * Тестирование POST метода получения времени от клиента
     */
    @Test
    public void testPostJsonObject() {
        try {

            RequestBuilder builder = MockMvcRequestBuilders
                    .post("/scripts/date")
                    .contentType(MediaType.TEXT_PLAIN_VALUE)
                    .characterEncoding("UTF-8")
                    .content("Tue, 07 Jul 2020 22:21:58 GMT");
            mockMvc.perform(builder)
                    .andExpect(status().isOk());

        } catch (Exception e) {

        }

    }
}