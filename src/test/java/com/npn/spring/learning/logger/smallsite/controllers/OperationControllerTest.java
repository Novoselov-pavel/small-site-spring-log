package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.OperationPageStorage;
import com.npn.spring.learning.logger.smallsite.models.RequestPageStorage;
import com.npn.spring.learning.logger.smallsite.models.UserObject;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import javax.servlet.http.Cookie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @InjectMocks
    private SendFilesFactory picsFactory;

    private MockMvc mockMvc;

    private String dirName = "small";



    @Before
    public void init(){
        controller.setRequestPageStorage(requestPageStorage);
        controller.setOperationStorage(operationPageStorage);
        controller.setDirName(dirName);
        try {
            PickFromFilesDriver driver = new PickFromFilesDriver("/home/pavel/IdeaProjects/small-site-spring-log/storage/small",
                    ".jpg","image/jpeg");
            List<GetFilesInterface> drivers = new ArrayList<>();
            drivers.add(driver);
            picsFactory.setFilesDrivers(drivers);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        controller.setPicsFactory(picsFactory);
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

    @Test
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

    @Test
    public void getSendFileForm() {
        OperationPageStorage.OperationMatching page = OperationPageStorage.OperationMatching.SEND_FILE;
        try {
            mockMvc.perform(get(page.getHrefName()))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("message", page.getDescription()))
                    .andExpect(model().attributeExists("myPics"))
                    .andExpect(view().name(page.getHtmlName()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }
}