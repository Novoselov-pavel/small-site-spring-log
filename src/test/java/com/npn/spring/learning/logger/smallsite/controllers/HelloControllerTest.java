package com.npn.spring.learning.logger.smallsite.controllers;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class HelloControllerTest extends TestCase {

    private final HelloController controller = new HelloController();
    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testSayHello() {
        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("Hello"))
                    .andExpect(MockMvcResultMatchers.model().attribute("hello","Hello world"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testTestSayHello() {
    }

    public void testSayHelloForm() {
    }

    public void testGetRegistryFrom() {
    }

    public void testRegistrationNewUser() {
    }

    public void testRegistryThymeleaf() {
    }

    public void testRegistryThymeleafPost() {
    }

    public void testGetJson() {
    }

    public void testGetJsonObject() {
    }

    public void testPostJsonObject() {
    }
}