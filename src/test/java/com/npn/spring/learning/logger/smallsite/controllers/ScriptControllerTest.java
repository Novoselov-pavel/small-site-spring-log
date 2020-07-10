package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.driver.GetFilesInterface;
import com.npn.spring.learning.logger.smallsite.models.driver.PickFromFilesDriver;
import com.npn.spring.learning.logger.smallsite.models.factories.SendFilesFactory;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
public class ScriptControllerTest extends TestCase {

    @InjectMocks
    private ScriptController controller;

    private MockMvc mockMvc;

    @InjectMocks
    private SendFilesFactory picsFactory;

    private String dirName = "small";

    @Before
    public void init(){
        try {
            PickFromFilesDriver driver = new PickFromFilesDriver("/home/pavel/IdeaProjects/small-site-spring-log/storage/small",
                    ".jpg","image/jpeg");
            List<GetFilesInterface> drivers = new ArrayList<>();
            drivers.add(driver);
            picsFactory.setFilesDrivers(drivers);
            controller.setPicsFactory(picsFactory);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
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
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void provideObject() {
        try {
            String baseDir = "small";
            String filename = "pick2.jpg";
            boolean download = true;
            RequestBuilder builder = MockMvcRequestBuilders
                    .get(String.format("/scripts/get?baseDir=%s&name=%s&download=%b",baseDir,filename,download));
            mockMvc.perform(builder)
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/octet-stream"));

        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }
}