package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.HelloObject;
import com.npn.spring.learning.logger.smallsite.models.UserObject;
import com.npn.spring.learning.logger.smallsite.models.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    @Autowired
    private ApplicationContext ctx;

    @GetMapping("/")
    public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "world") String name,
                           ModelMap model) {
        HelloObject helloObject = ctx.getBean("hello", HelloObject.class);
        model.addAttribute("message", helloObject.getMessage(name));
        return "Main";
    }

    @PostMapping("/registry")
    public String registrationNewUser(HttpServletRequest request, ModelMap model){
        UserStorage storage = ctx.getBean("userStorage", UserStorage.class);
        UserObject user = UserObject.createFromMap(request.getParameterMap());
        model.addAttribute("user",user);
        Long id = storage.addUser(user);
        model.addAttribute("id",id);
        return "UserInfo";
    }
}
