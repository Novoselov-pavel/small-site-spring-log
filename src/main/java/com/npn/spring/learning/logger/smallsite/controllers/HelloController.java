package com.npn.spring.learning.logger.smallsite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npn.spring.learning.logger.smallsite.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Контроллер
 */
@Controller
public class HelloController {

    private AbstractPageStorage storage;

    private Dog dog = null;


    public AbstractPageStorage getStorage() {
        return storage;
    }

    @Autowired
    @Qualifier("requestPageStorage")
    public void setStorage(AbstractPageStorage storage) {
        this.storage = storage;
    }

    /**
     * Обработчик GET запроса с получением параметра из адресной строки
     *
     * @param name парметр
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/hello")
    public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "world") String name,
                           Model model) {
        RequestPageStorage.PageAndViewMatching current = RequestPageStorage.PageAndViewMatching.GET_HREF;

        createHTMLTemplate(model);
        model.addAttribute("hello", new HelloObject().getMessage(name));
        model.addAttribute("message", storage.getDescription(current.getHrefName()));

        return storage.getHtmlName(current.getHrefName());
    }

    /**
     * Обработчик GET запроса без параметров для главной таблицы
     *
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/")
    public String sayHello(Model model) {
        RequestPageStorage.PageAndViewMatching current = RequestPageStorage.PageAndViewMatching.HOME;

        createHTMLTemplate(model);
        model.addAttribute("message", storage.getDescription(current.getHrefName()));
        return storage.getHtmlName(current.getHrefName());
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
                           Model model) {
        RequestPageStorage.PageAndViewMatching current = RequestPageStorage.PageAndViewMatching.GET_FORM;

        createHTMLTemplate(model);

        model.addAttribute("hello",  new HelloObject().getMessage(name));
        model.addAttribute("message", storage.getDescription(current.getHrefName()));
        return storage.getHtmlName(current.getHrefName());
    }

    /**
     * Обработчик GET запроса
     *
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/registry")
    public String getRegistryFrom(Model model){
        RequestPageStorage.PageAndViewMatching current = RequestPageStorage.PageAndViewMatching.POST_FORM;

        createHTMLTemplate(model);
        model.addAttribute("message", storage.getDescription(current.getHrefName()));
        model.addAttribute("readonly", false);
        model.addAttribute("action",current.getHrefName());

        //Требуется добавление UserObject иначе шаблон выведет ошибку
        UserObject userObject = new UserObject();
        model.addAttribute("user", userObject);
        return storage.getHtmlName(current.getHrefName());
    }

    /**
     * Обработчик пост запроса с ручным разбором тела запроса через HttpServletRequest
     * @param request HttpServletRequest
     * @param model модель
     * @return имя представления
     */
    @PostMapping("/registry")
    public String registrationNewUser(HttpServletRequest request, Model model){
        RequestPageStorage.PageAndViewMatching current = RequestPageStorage.PageAndViewMatching.POST_FORM;

        UserStorage userStorage = new UserStorage();
        UserObject user = UserObject.createFromMap(request.getParameterMap());
        createHTMLTemplate(model);
        model.addAttribute("user",user);
        model.addAttribute("readonly", true);
        model.addAttribute("action",current.getHrefName());
        model.addAttribute("message", storage.getDescription(current.getHrefName()));

        long id = userStorage.addUser(user);
        user.setId(id);
        model.addAttribute("id",id);
        return storage.getHtmlName(current.getHrefName());
    }

    /**
     * Обработчик GET запроса для автоматического маппинга через Thymeleaf
     *
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/postThymeleaf")
    public String registryThymeleaf(Model model) {
        RequestPageStorage.PageAndViewMatching current = RequestPageStorage.PageAndViewMatching.POST_THYMELEAF;

        UserObject user = new UserObject();
        model.addAttribute("user", user);
        createHTMLTemplate(model);

        model.addAttribute("message", storage.getDescription(current.getHrefName()));
        model.addAttribute("readonly", false);
        model.addAttribute("action",current.getHrefName());

        return storage.getHtmlName(current.getHrefName());
    }

    /**
     * Обработчик POST запроса для автоматического маппинга через Thymeleaf
     * @param userObject userObject новый объект того же типа, что переданный в GET запросе
     * @param model модель
     * @return имя представления
     */
    @PostMapping("/postThymeleaf")
    public String registryThymeleafPost(@ModelAttribute UserObject userObject, Model model) {

        RequestPageStorage.PageAndViewMatching current = RequestPageStorage.PageAndViewMatching.POST_THYMELEAF;

        UserStorage userStorage = new UserStorage();
        long id = userStorage.addUser(userObject);
        userObject.setId(id);
        model.addAttribute("id",id);

        createHTMLTemplate(model);

        model.addAttribute("user",userObject);
        model.addAttribute("message", storage.getDescription(current.getHrefName()));
        model.addAttribute("readonly", true);
        model.addAttribute("action",current.getHrefName());

        return storage.getHtmlName(current.getHrefName());
    }

    /**
     * Обработчик GET запроса для работы через Json
     *
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/postJson")
    public String getJson(Model model) {
        RequestPageStorage.PageAndViewMatching current = RequestPageStorage.PageAndViewMatching.POST_JSON;

        createHTMLTemplate(model);

        model.addAttribute("message", storage.getDescription(current.getHrefName()));
        model.addAttribute("url","/jsonTest");

        return storage.getHtmlName(current.getHrefName());
    }

    /**
     * Обработчик GET запроса собственно для получения Json
     *
     * @return json
     */
    @GetMapping(value = "/jsonTest", produces = {"application/json;charset=UTF-8"})
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
        return "{}";
    }

    /**
     * Обработчик POST запроса собственно для получения Json
     *
     * @return json
     */
    @PostMapping(value = "/jsonTest", produces = {"application/json;charset=UTF-8"}, consumes = MediaType.APPLICATION_JSON_VALUE)
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
        return "{}";
    }

    private void createHTMLTemplate(Model model) {
        addHeader(model);
    }

    private void addHeader(Model model) {
        model.addAttribute("pages",storage.getPages());
    }



}
