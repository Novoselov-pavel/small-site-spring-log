package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.AbstractPageStorage;
import com.npn.spring.learning.logger.smallsite.models.RequestPageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для главной страницы
 */
@Controller
public class MainPageController extends AbstractController {
    public static final String HREF ="/";
    public static final String HTML_NAME ="Main";
    public static final String DESCRIPTION ="Привет, это тестовый сервер с разными реализациями элементов HTML и их взаимодействия с сервером.";

    /**
     * Обработчик GET запроса без параметров для главной таблицы
     *
     * @param model модель
     * @return имя представления
     */
    @GetMapping("/")
    public String sayHello(Model model) {
        createHTMLTemplate(model);
        model.addAttribute("message", DESCRIPTION);
        return HTML_NAME;
    }

    @Override
    @Autowired
    @Qualifier("requestPageStorage")
    public void setStorage(AbstractPageStorage storage) {
        this.storage = storage;
    }

    @Override
    @Autowired
    @Qualifier("operationPageStorage")
    public void setOperationStorage(AbstractPageStorage storage) {
        this.operationStorage = storage;
    }
}
