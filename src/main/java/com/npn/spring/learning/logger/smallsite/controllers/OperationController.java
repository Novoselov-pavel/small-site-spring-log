package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.AbstractPageStorage;
import com.npn.spring.learning.logger.smallsite.models.OperationPageStorage;
import com.npn.spring.learning.logger.smallsite.models.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Контроллер типовых действий
 */
@Controller
@RequestMapping("/operation")
public class OperationController extends AbstractController {
    private static final int COOKIE_EXPIRED_SECOND = 30;


    /**
     * GET метод для работы с Cookie, выдает страницу в зависимости от того, есть имя (name) в cookie или нет.
     *
     * @return
     */
    @GetMapping("/cookie")
    public String getCookieRequest(@CookieValue(value = "name", defaultValue = "", required = false)String name, Model model){
        createHTMLTemplate(model);
        OperationPageStorage.OperationMatching form = OperationPageStorage.OperationMatching.COOKIE;
        model.addAttribute("message", form.getDescription());
        if (name == null || name.isBlank()) {
            guestToModel(model,form);
        } else {
            userToModel(name,model,form);
        }
        return form.getHtmlName();
    }

    /**
     * Post метод для работы с Cookie. Создает Cookie и перенаправляет на страницу /cookie для загрузки через GET запрос.
     * Использовать forward нельзя, так как иначе запрос придет на эту же функцию и все закольцуется.
     *
     * @param user UserObject
     * @param response HttpServletResponse
     * @param model model
     * @return ModelAndView c редиректом.
     */
    @PostMapping("/cookie")
    public String getCookiePost(@ModelAttribute UserObject user, HttpServletResponse response, Model model) {
        createHTMLTemplate(model);
        OperationPageStorage.OperationMatching form = OperationPageStorage.OperationMatching.COOKIE;
        Cookie cookie = new Cookie("name", user.getName());
        cookie.setMaxAge(COOKIE_EXPIRED_SECOND);
        response.addCookie(cookie);
        return "redirect:" + form.getHrefName();
        ///return new ModelAndView("redirect:"+form.getHrefName(), model.asMap());
    }

    /**
     * GET метод для странички с датой
     * @return имя предстваления
     */
    @GetMapping("/date")
    public String getDatePage(Model model){
        createHTMLTemplate(model);
        OperationPageStorage.OperationMatching page = OperationPageStorage.OperationMatching.DATE_UTC;
        model.addAttribute("message", page.getDescription());
        return page.getHtmlName();
    }

    @Override
    @Autowired
    @Qualifier("requestPageStorage")
    public void setRequestPageStorage(AbstractPageStorage requestPageStorage) {
        this.requestPageStorage = requestPageStorage;
    }

    @Override
    @Autowired
    @Qualifier("operationPageStorage")
    public void setOperationStorage(AbstractPageStorage storage) {
        this.operationStorage = storage;
    }

    private void guestToModel(Model model, OperationPageStorage.OperationMatching form) {
        model.addAttribute("hello", "Привет Гость, назови себя:");
        UserObject userObject = new UserObject();
        model.addAttribute("user", userObject);
        model.addAttribute("showForm", true);
        model.addAttribute("action", form.getHrefName());
    }

    private void userToModel(String name,Model model, OperationPageStorage.OperationMatching form) {
        model.addAttribute("hello", "Привет " + name);
        UserObject userObject = new UserObject();
        model.addAttribute("user", userObject);
        model.addAttribute("action", form.getHrefName());
        model.addAttribute("showForm", false);
    }

}
