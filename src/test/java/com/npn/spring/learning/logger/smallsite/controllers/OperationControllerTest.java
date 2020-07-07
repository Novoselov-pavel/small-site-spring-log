package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.OperationPageStorage;
import com.npn.spring.learning.logger.smallsite.models.RequestPageStorage;
import com.npn.spring.learning.logger.smallsite.models.UserObject;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(MockitoJUnitRunner.class)
public class OperationControllerTest extends TestCase {
    private static UserObject userObject = new UserObject();
    private static String userObjectString = "name=TestName";

    @InjectMocks
    private RequestPageStorage requestPageStorage;

    @InjectMocks
    private OperationPageStorage operationPageStorage;

    @InjectMocks
    private OperationController controller;

    private MockMvc mockMvc;



    @Before
    public void init(){
        controller.setRequestPageStorage(requestPageStorage);
        controller.setOperationStorage(operationPageStorage);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        userObject.setName("TestName");
    }


    /**
     * Тест GET запроса формы тестирования Cookie
     */
    @Test
    public void testGetCookieRequest() {
        OperationPageStorage.OperationMatching page = OperationPageStorage.OperationMatching.COOKIE;
        try {
            Cookie cookie = new Cookie("name", userObject.getName());
            cookie.setMaxAge(10);

            mockMvc.perform(get(page.getHrefName()))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("showForm", true))
                    .andExpect(model().attribute("action",page.getHrefName()))
                    .andExpect(view().name(page.getHtmlName()))
                    .andExpect(model().attributeExists("user"));
            RequestBuilder builder = MockMvcRequestBuilders
                    .get(page.getHrefName())
                    .cookie(cookie)
                    .characterEncoding("UTF-8");

            mockMvc.perform(builder)
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("showForm", false))
                    .andExpect(model().attribute("hello","Привет " + userObject.getName()));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    /**
     * Тест Post запроса формы тестирования Cookie
     */
    @Test
    public void testGetCookiePost() {
        OperationPageStorage.OperationMatching page = OperationPageStorage.OperationMatching.COOKIE;
        RequestBuilder builder = MockMvcRequestBuilders
                .post(page.getHrefName())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("UTF-8")
                .content(userObjectString);
        try {
            /*
             * Единственный найденный способ получить cookie, только через MockMvcResultMatchers, у MvcResult
             * данного свойства нет.
             */
            mockMvc.perform(builder)
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(page.getHrefName()))
                    .andExpect(cookie().value("name","TestName"));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }

    }

    public void testGetDatePage() {
        OperationPageStorage.OperationMatching page = OperationPageStorage.OperationMatching.DATE_UTC;
        try {
            mockMvc.perform(get(page.getHrefName()))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("message",page.getDescription()))
                    .andExpect(view().name(page.getHtmlName()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}