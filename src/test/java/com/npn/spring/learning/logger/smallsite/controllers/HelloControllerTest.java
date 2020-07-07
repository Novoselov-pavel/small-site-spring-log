package com.npn.spring.learning.logger.smallsite.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npn.spring.learning.logger.smallsite.models.*;
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

/***
 * Класс тестирования HelloController
 * внизу в комментариях реализация запуска через SpringJUnit4ClassRunner.class
 * При использовании SpringJUnit4ClassRunner.class требуется включать
 * MockitoAnnotations.initMocks(this);
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//        "file:src/main/webapp/WEB-INF/applicationContext.xml"
//})
@RunWith(MockitoJUnitRunner.class)
public class HelloControllerTest extends TestCase {
    private static UserObject userObject = new UserObject();
    private static String userObjectString = "name=test&email=test%40test.com&password=test&check=true";
    private static Dog testDog = new Dog();


    @InjectMocks
    private RequestPageStorage storage;

    @InjectMocks
    private OperationPageStorage operationPageStorage;

    @InjectMocks
    private HelloController controller;

    private MockMvc mockMvc;

    @Before
    public void init(){
        /**
         * Метод MockitoAnnotations.initMocks(this) заново инициализирует все объекты с аннатоцией Mockito
         * в связи с тем, что JUnit создаёт отдельный экземпляр тестового класса при вызове каждого из тестирующих методов
         * при тестировании JUnit через MockitoJUnitRunner.class не требуется.
         */
//        MockitoAnnotations.initMocks(this);
        controller.setRequestPageStorage(storage);
        controller.setOperationStorage(operationPageStorage);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        userObject.setName("test");
        userObject.setEmail("test@test.com");
        userObject.setPassword("test");
        userObject.setCheck(true);
        userObjectString = "name=test&email=test%40test.com&password=test&check=true";
        testDog.setName("TestName");
        testDog.setBreed("TestBread");
    }

    /**
     * собственно метод тестирования
     */
    @Test
    public void testSayHello() {
        try{
            String hrefName = RequestPageStorage.PageAndViewMatching.GET_HREF.getHrefName();

            mockMvc.perform(get(hrefName))
                    .andExpect(status().isOk())
                    .andExpect(view().name("Hello"))
                    .andExpect(model().attribute("hello","Hello world"))
                    .andExpect(model().attributeExists("pages"));

            mockMvc.perform(get(hrefName+"?name=me"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("Hello"))
                    .andExpect(model().attribute("hello","Hello me"))
                    .andExpect(model().attributeExists("pages"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /**
     * собственно метод тестирования
     */
    @Test
    public void testSayHelloForm() {
        try{
            RequestPageStorage.PageAndViewMatching form = RequestPageStorage.PageAndViewMatching.GET_FORM;

            mockMvc.perform(get(form.getHrefName()))
                    .andExpect(status().isOk())
                    .andExpect(view().name(form.getHtmlName()))
                    .andExpect(model().attribute("hello","Hello world"))
                    .andExpect(model().attributeExists("pages"));

            mockMvc.perform(get(form.getHrefName()+"?name=me"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(form.getHtmlName()))
                    .andExpect(model().attribute("hello","Hello me"))
                    .andExpect(model().attributeExists("pages"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * собственно метод тестирования
     */
    @Test
    public void testGetRegistryFrom() {
        try{
            RequestPageStorage.PageAndViewMatching form = RequestPageStorage.PageAndViewMatching.POST_FORM;

            mockMvc.perform(get(form.getHrefName()))
                    .andExpect(status().isOk())
                    .andExpect(view().name(form.getHtmlName()))
                    .andExpect(model().attribute("readonly",false))
                    .andExpect(model().attribute("action",form.getHrefName()))
                    .andExpect(model().attributeExists("pages"))
                    .andExpect(model().attributeExists("user"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    /**
     * собственно метод тестирования
     */
    @Test
    public void testRegistrationNewUser() {
        try {
            RequestPageStorage.PageAndViewMatching form = RequestPageStorage.PageAndViewMatching.POST_FORM;
            RequestBuilder builder = MockMvcRequestBuilders
                    .post(form.getHrefName())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .characterEncoding("UTF-8")
                    .content(userObjectString);


            MvcResult result = mockMvc.perform(builder).andReturn();
            assertEquals(200, result.getResponse().getStatus());
            assertEquals(form.getHtmlName(), result.getModelAndView().getViewName());
            assertEquals(userObject, result.getModelAndView().getModel().get("user"));
            assertEquals(true, result.getModelAndView().getModel().get("readonly"));
            assertEquals(form.getHrefName(), result.getModelAndView().getModel().get("action"));
            assertTrue(result.getModelAndView().getModel().get("id")!=null);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    /**
     * собственно метод тестирования
     */
    @Test
    public void testRegistryThymeleaf() {
        try{
            RequestPageStorage.PageAndViewMatching form = RequestPageStorage.PageAndViewMatching.POST_THYMELEAF;

            mockMvc.perform(get(form.getHrefName()))
                    .andExpect(status().isOk())
                    .andExpect(view().name(form.getHtmlName()))
                    .andExpect(model().attribute("action",form.getHrefName()))
                    .andExpect(model().attributeExists("pages"))
                    .andExpect(model().attributeExists("user"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * собственно метод тестирования
     */
    @Test
    public void testRegistryThymeleafPost() {
        try {
            RequestPageStorage.PageAndViewMatching form = RequestPageStorage.PageAndViewMatching.POST_THYMELEAF;

            RequestBuilder builder = MockMvcRequestBuilders
                    .post(form.getHrefName())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .characterEncoding("UTF-8")
                    .content(userObjectString);

            UserObject userObject = new UserObject();
            userObject.setName("test");
            userObject.setEmail("test@test.com");
            userObject.setPassword("test");
            userObject.setCheck(true);

            MvcResult result = mockMvc.perform(builder).andReturn();
            assertEquals(200, result.getResponse().getStatus());
            assertEquals(form.getHtmlName(), result.getModelAndView().getViewName());
            assertEquals(userObject, result.getModelAndView().getModel().get("user"));
            assertEquals(true, result.getModelAndView().getModel().get("readonly"));
            assertEquals(form.getHrefName(), result.getModelAndView().getModel().get("action"));
            assertTrue(result.getModelAndView().getModel().get("id")!=null);
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
    }

    /**
     * собственно метод тестирования
     */
    @Test
    public void testGetJson() {
        try{
            RequestPageStorage.PageAndViewMatching form = RequestPageStorage.PageAndViewMatching.POST_JSON;
            String postAddress = "/request" + "/jsonTest";

            mockMvc.perform(get(form.getHrefName()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("PostJson"))
                    .andExpect(model().attribute("url",postAddress))
                    .andExpect(model().attributeExists("pages"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * собственно метод тестирования
     */
    @Test
    public void testGetJsonObject() {
        try{
            String postAddress = "/request" + "/jsonTest";

            RequestBuilder builder = MockMvcRequestBuilders
                    .get(postAddress)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .characterEncoding("UTF-8");
            MvcResult result = mockMvc.perform(builder).andReturn();
            assertEquals(200, result.getResponse().getStatus());
            assertEquals("{}", result.getResponse().getContentAsString());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * собственно метод тестирования
     */
    @Test
    public void testPostJsonObject() {
        try{
            String postAddress = "/request" + "/jsonTest";

            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(testDog);

            RequestBuilder builder = MockMvcRequestBuilders
                    .post(postAddress)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .characterEncoding("UTF-8")
                    .content(value);
            MvcResult result = mockMvc.perform(builder).andReturn();
            assertEquals(200, result.getResponse().getStatus());
            assertEquals(value, result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }
}