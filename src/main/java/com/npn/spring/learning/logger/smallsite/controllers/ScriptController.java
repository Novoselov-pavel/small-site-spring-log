package com.npn.spring.learning.logger.smallsite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npn.spring.learning.logger.smallsite.models.ProvidedObject;
import com.npn.spring.learning.logger.smallsite.models.driver.GetDirToJsonInterface;
import com.npn.spring.learning.logger.smallsite.models.factories.GetDirToJsonFactory;
import com.npn.spring.learning.logger.smallsite.models.factories.SendFilesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Контроллер для получения/пересылки информации из скриптов JS форм контроллера {@link OperationController}
 */
@Controller
public class ScriptController {
    public static final String DATE_MAPPING_URL = "/scripts/date";
    public static final String FILE_GET_MAPPING_URL = "/scripts/get";
    public static final String DIR_GET_MAPPING_URL = "/scripts/dir";
    public static final String POST_FILE_MAPPING_URL = "/script/post";


    private SendFilesFactory picsFactory;

    private GetDirToJsonFactory dirFactory;

    @Autowired
    public void setDirFactory(GetDirToJsonFactory dirFactory) {
        this.dirFactory = dirFactory;
    }

    @Autowired
    public void setPicsFactory(SendFilesFactory picsFactory) {
        this.picsFactory = picsFactory;
    }

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
    public @ResponseBody String postDate(@RequestBody String dateString) {
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

    /**
     * Отправляет Пользователю запрошенный файл.
     * Классы которые принимают участие в данном методе:
     * <br>ProvidedObject - объект представление картинки (или любого другого файла на сервере).</br>
     * <br>GetFilesInterface - интерфейс для работы с файлами.</br>
     * <br>PickFromFilesDriver - имплементация интерфейса для работы с картинками</br>
     * <br>SendFilesFactory - фабричный метод, хранит все имплементации GetFilesInterface, и возвращает
     * списки ProvidedObject или конкретный ProvidedObject согласно переданному запросу</br>
     *
     * @param baseDir базовая директория на сервере
     * @param name имя файла
     * @param isDownload передать как рисунок, или как application/octet-stream
     * @param response собственно ответ сервера, куда мы это направляем
     */
    @GetMapping("/scripts/get")
    public void provideObject (@RequestParam(name = "baseDir", required = true) String baseDir,
                                               @RequestParam(name = "name",required = true) String name,
                                               @RequestParam(name = "download", required = false, defaultValue = "false") boolean isDownload,
                                               HttpServletResponse response) {
        ProvidedObject uploadedObject = picsFactory.getObject(baseDir,name);
        if (uploadedObject == null) {
            return;
        }

        response.setHeader("Content-disposition", "attachment;filename=" + uploadedObject.getFileName());
        if (!isDownload) {
            response.setContentType(uploadedObject.getContentType());
        } else {
            response.setContentType(uploadedObject.getDownloadContentType());
        }

        long length = uploadedObject.getLength();

        if (length<Integer.MAX_VALUE) {
            response.setContentLength((int)length);
        } else {
            /// Для больших файлов не устанавливаем значение ContentLength
            /// по идее, Tomcat установит Тransfer-Encoding: chunked (включен по умолчанию)
        }

        /*
         * Собственно загрузка файла в тело ответа
         */
        try {
            Files.copy(uploadedObject.getPath(), response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException("IOError writing file to output stream", e);
        }
    }


    /**
     *Отправляет Пользователю список файлов из указанной папки в объекте Json.
     *
     * @param dir псевдоним папки на сервере
     * @return Json
     */
    @GetMapping("/scripts/dir")
    public @ResponseBody String getDir(@RequestParam(name = "baseDir", required = true) String dir) {
        if (dir!=null) {
            GetDirToJsonInterface dirDriver = dirFactory.getDriver(dir);
            if (dirDriver == null) return "{}";
            try {
                return dirDriver.getObjectsListAsJson();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "{}";
    }

    @PostMapping("/script/post")
    public void postFile(@RequestParam(name = "baseDir", required = true) String baseDir, HttpServletRequest request){
        request.getParameterMap();


    }


}
