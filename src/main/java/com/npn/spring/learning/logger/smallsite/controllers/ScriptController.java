package com.npn.spring.learning.logger.smallsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Контроллер для получения/пересылки информации из скриптов JS форм контроллера {@link OperationController}
 */
@Controller
public class ScriptController {

    /**
     * Отправка Get запросом даты пользователю
     * @return
     */
    @GetMapping(value = "/scripts/date", produces = {"text/plain; charset=UTF-8"})
    public @ResponseBody String getTimeFromServer() {
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Получение от пользователя POST запроса с типом "text/plain"
     *
     * @param dateString
     * @return
     */
    @PostMapping(value = "/scripts/date", produces = {"text/plain; charset=UTF-8"}, consumes = {"text/plain; charset=UTF-8"})
    public @ResponseBody String postJsonObject (@RequestBody String dateString) {
        /*
         * Строка парсинга по стандарту rfc 822
         */
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        try{
            Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
            Date date =  format.parse(dateString);
            calendar.setTime(date);
            System.out.println(calendar.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
