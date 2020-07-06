package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.RequestPageStorage;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(MockitoJUnitRunner.class)
public class MainPageControllerTest extends TestCase {

    @InjectMocks
    private RequestPageStorage storage;

    @InjectMocks
    private MainPageController controller;

    private MockMvc mockMvc;

    @Before
    public void init(){
        /**
         * Метод MockitoAnnotations.initMocks(this) заново инициализирует все объекты с аннатоцией Mockito
         * в связи с тем, что JUnit создаёт отдельный экземпляр тестового класса при вызове каждого из тестирующих методов
         * при тестировании JUnit через MockitoJUnitRunner.class не требуется.
         */
//        MockitoAnnotations.initMocks(this);
        controller.setStorage(storage);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /**
     * собственно метод тестирования
     */
    @Test
    public void testSayHello() {
        try{
            String href = MainPageController.HREF;
            String template = MainPageController.HTML_NAME;
            String message = MainPageController.DESCRIPTION;

            mockMvc.perform(get(href))
                    .andExpect(status().isOk())
                    .andExpect(view().name(template))
                    .andExpect(model().attribute("message",message))
                    .andExpect(model().attributeExists("pages"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}