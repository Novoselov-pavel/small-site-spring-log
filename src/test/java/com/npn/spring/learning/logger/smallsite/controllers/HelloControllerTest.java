package com.npn.spring.learning.logger.smallsite.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npn.spring.learning.logger.smallsite.models.AbstractPageStorage;
import com.npn.spring.learning.logger.smallsite.models.Dog;
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
        controller.setStorage(storage);
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
            mockMvc.perform(get("/hello"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("Hello"))
                    .andExpect(model().attribute("hello","Hello world"))
                    .andExpect(model().attributeExists("pages"));

            mockMvc.perform(get("/hello?name=me"))
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
    public void testMain() {
        try{
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("Main"))
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
            mockMvc.perform(get("/helloForm"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("HelloFromForm"))
                    .andExpect(model().attribute("hello","Hello world"))
                    .andExpect(model().attributeExists("pages"));

            mockMvc.perform(get("/helloForm?name=me"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("HelloFromForm"))
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
            mockMvc.perform(get("/registry"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("RegistryForm"))
                    .andExpect(model().attribute("readonly",false))
                    .andExpect(model().attribute("action","/registry"))
                    .andExpect(model().attributeExists("pages"))
                    .andExpect(model().attributeExists("user"));

            mockMvc.perform(get("/helloForm?name=me"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("HelloFromForm"))
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
    public void testRegistrationNewUser() {
        try {
            RequestBuilder builder = MockMvcRequestBuilders
                    .post("/registry")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .characterEncoding("UTF-8")
                    .content(userObjectString);


            MvcResult result = mockMvc.perform(builder).andReturn();
            assertEquals(200, result.getResponse().getStatus());
            assertEquals("RegistryForm", result.getModelAndView().getViewName());
            assertEquals(userObject, result.getModelAndView().getModel().get("user"));
            assertEquals(true, result.getModelAndView().getModel().get("readonly"));
            assertEquals("/registry", result.getModelAndView().getModel().get("action"));
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
    public void testRegistryThymeleaf() {
        try{
            mockMvc.perform(get("/postThymeleaf"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("RegistryFormLeaf"))
                    .andExpect(model().attribute("action","/postThymeleaf"))
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
            RequestBuilder builder = MockMvcRequestBuilders
                    .post("/postThymeleaf")
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
            assertEquals("RegistryFormLeaf", result.getModelAndView().getViewName());
            assertEquals(userObject, result.getModelAndView().getModel().get("user"));
            assertEquals(true, result.getModelAndView().getModel().get("readonly"));
            assertEquals("/postThymeleaf", result.getModelAndView().getModel().get("action"));
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
            mockMvc.perform(get("/postJson"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("PostJson"))
                    .andExpect(model().attribute("url","/jsonTest"))
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
            RequestBuilder builder = MockMvcRequestBuilders
                    .get("/jsonTest")
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
            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(testDog);

            RequestBuilder builder = MockMvcRequestBuilders
                    .post("/jsonTest")
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