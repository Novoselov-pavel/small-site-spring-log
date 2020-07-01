package com.npn.spring.learning.logger.smallsite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npn.spring.learning.logger.smallsite.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;

/**
 * Контроллер
 */
@Controller
public class HelloController {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private PageStorage storage;

    private Dog dog = null;

    /**
     * Обработчик GET запроса с получением параметра из адресной строки
     *
     * @param name парметр
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/hello")
    public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "world") String name,
                           ModelMap model) {
        HelloObject helloObject = ctx.getBean("hello", HelloObject.class);
        model.addAttribute("hello", helloObject.getMessage(name));
        model.addAttribute("message", storage.getDescription("/hello"));
        model.addAttribute("pages",storage.getPages());
        return storage.getHtmlName("/hello");
    }

    /**
     * Обработчик GET запроса без параметров для главной таблицы
     *
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/")
    public String sayHello(ModelMap model) {
        model.addAttribute("pages",storage.getPages());
        model.addAttribute("message", storage.getDescription("/"));
        return storage.getHtmlName("/");
    }

    /**
     * Обработчик GET запроса с получением параметра из адресной строки
     *
     * @param name параметр
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/helloForm")
    public String sayHelloForm(@RequestParam(name = "name", required = false, defaultValue = "world") String name,
                           ModelMap model) {
        HelloObject helloObject = ctx.getBean("hello", HelloObject.class);
        model.addAttribute("hello", helloObject.getMessage(name));
        model.addAttribute("message", storage.getDescription("/helloForm"));
        model.addAttribute("pages",storage.getPages());
        return storage.getHtmlName("/helloForm");
    }

    /**
     * Обработчик GET запроса
     *
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/registry")
    public String getRegistryFrom(ModelMap model){
        model.addAttribute("message", storage.getDescription("/registry"));
        model.addAttribute("readonly", false);
        model.addAttribute("pages",storage.getPages());
        model.addAttribute("action","/registry");

        //Требуется добавление UserObject иначе шаблон выведет ошибку
        UserObject userObject = new UserObject();
        model.addAttribute("user", userObject);
        return storage.getHtmlName("/registry");
    }

    /**
     * Обработчик пост запроса с ручным разбором тела запроса через HttpServletRequest
     * @param request HttpServletRequest
     * @param model модель
     * @return имя представления
     */
    @PostMapping("/registry")
    public String registrationNewUser(HttpServletRequest request, ModelMap model){
        UserStorage userStorage = ctx.getBean("userStorage", UserStorage.class);
        UserObject user = UserObject.createFromMap(request.getParameterMap());
        model.addAttribute("user",user);
        model.addAttribute("readonly", true);
        model.addAttribute("pages",storage.getPages());
        model.addAttribute("action","/registry");
        model.addAttribute("message", storage.getDescription("/registry"));

        long id = userStorage.addUser(user);
        user.setId(id);
        return storage.getHtmlName("/registry");
    }

    /**
     * Обработчик GET запроса для автоматического маппинга через Thymeleaf
     *
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/postThymeleaf")
    public String registryThymeleaf(Model model) {
        UserObject user = new UserObject();
        model.addAttribute("user", user);

        model.addAttribute("message", storage.getDescription("/postThymeleaf"));
        model.addAttribute("readonly", false);
        model.addAttribute("pages",storage.getPages());
        model.addAttribute("action","/postThymeleaf");

        return storage.getHtmlName("/postThymeleaf");
    }

    /**
     * Обработчик POST запроса для автоматического маппинга через Thymeleaf
     * @param userObject userObject новый объект того же типа, что переданный в GET запросе
     * @param model модель
     * @return имя представления
     */
    @PostMapping("/postThymeleaf")
    public String registryThymeleafPost(@ModelAttribute UserObject userObject, Model model) {

        UserStorage userStorage = ctx.getBean("userStorage", UserStorage.class);
        long id = userStorage.addUser(userObject);
        userObject.setId(id);
        model.addAttribute("user",userObject);
        model.addAttribute("message", storage.getDescription("/postThymeleaf"));
        model.addAttribute("readonly", true);
        model.addAttribute("pages",storage.getPages());
        model.addAttribute("action","/postThymeleaf");
        return storage.getHtmlName("/postThymeleaf");
    }

    /**
     * Обработчик GET запроса для работы через Json
     *
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/postJson")
    public String getJson(Model model) {

        model.addAttribute("message", storage.getDescription("/postJson"));
        model.addAttribute("pages",storage.getPages());
        model.addAttribute("url","/jsonTest");

        return storage.getHtmlName("/postJson");
    }

    /**
     * Обработчик GET запроса собственно для получения Json
     *
     * @return json
     */
    @GetMapping(value = "/jsonTest", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getJsonObject() {
        if (dog!=null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String value = mapper.writeValueAsString(dog);
                return value;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * Обработчик POST запроса собственно для получения Json
     *
     * @return json
     */
    @PostMapping(value = "/jsonTest", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String postJsonObject (HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Dog myDog = mapper.readValue(request.getInputStream(),Dog.class);
            dog = myDog;
            String value = mapper.writeValueAsString(dog);
            return value;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



}
