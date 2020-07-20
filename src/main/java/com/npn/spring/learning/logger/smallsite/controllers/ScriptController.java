package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.ProvidedObject;
import com.npn.spring.learning.logger.smallsite.models.driver.GetDirToJsonInterface;
import com.npn.spring.learning.logger.smallsite.models.factories.GetDirToJsonFactory;
import com.npn.spring.learning.logger.smallsite.models.factories.SaveMultipartFileFactory;
import com.npn.spring.learning.logger.smallsite.models.factories.SendFilesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    private SaveMultipartFileFactory saveFactory;

    @Autowired
    public void setDirFactory(GetDirToJsonFactory dirFactory) {
        this.dirFactory = dirFactory;
    }

    @Autowired
    public void setPicsFactory(SendFilesFactory picsFactory) {
        this.picsFactory = picsFactory;
    }

    @Autowired
    public void setSaveFactory(SaveMultipartFileFactory saveFactory) {
        this.saveFactory = saveFactory;
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
        response.setCharacterEncoding("UTF-8");

        /*Обращаю внимание, что по стандартам в строке Content-disposition имя файла должно быть в  US-ASCII и других вариантов нет.
         * По этому тут используется перекодирование в строку URLEncoder, что является лучшим выбором, так как использование предиката
         * filename*=UTF-8'' работает от браузера к браузеру.
         */
        response.setHeader("Content-disposition", "filename=" + URLEncoder.encode(uploadedObject.getFileName(), StandardCharsets.UTF_8));

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
    @GetMapping(value = "/scripts/dir", produces = {"text/plain; charset=UTF-8"})
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


    /**
     * Получение файла из данных формы.
     * <br></br>
     * <br>Ключевым моментом получения файла является - создание бина класса org.springframework.web.multipart.commons.CommonsMultipartResolver.</br>
     * <br>Реализация же функции контроллера может быть выполнена через @RequestParam или через @ModelAttribute (см. закомментированный код ниже).
     * </br>
     * <br>CommonsMultipartResolver - это класс обработчика процесса получения файла. Описание см. beanConfig.xml</br>
     * <br></br>
     *
     * @param baseDir директория в которую загружается файл
     * @param files
     * @param inputData не используются, просто пример, как линковать другие данные переданные формой
     */
    @PostMapping(value = "/script/post")
    public @ResponseBody String postFile(@RequestParam(name = "baseDir", required = true) String baseDir,
                         @RequestParam("files") MultipartFile[] files,
                         @RequestParam("somedata") String inputData){
        saveFactory.getDriver(baseDir).saveMultipartFiles(files);
        return "";
    }

//    @PostMapping(value = "/script/post", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void postFile(@RequestParam(name = "baseDir", required = true) String baseDir, @ModelAttribute UploadModel model){
//        model.getFiles();
//
//
//    }
//    public static class UploadModel {
//        private String somedata;
//
//        private MultipartFile[]files;
//
//        public MultipartFile[] getFiles() {
//            return files;
//        }
//
//        public void setFiles(MultipartFile[] files) {
//            this.files = files;
//        }
//
//        public String getSomedata() {
//            return somedata;
//        }
//
//        public void setSomedata(String somedata) {
//            this.somedata = somedata;
//        }
//    }


}
