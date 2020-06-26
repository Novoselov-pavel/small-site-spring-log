package com.npn.spring.learning.logger.smallsite.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("hello")
@Scope("prototype")
public class HelloObject {

    public String getMessage(String name){
        return "Hello " + name;
    }
}
