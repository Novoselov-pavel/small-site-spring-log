package com.npn.spring.learning.logger.smallsite.models;

import org.springframework.stereotype.Component;

/**
 * Класс для хранения всех страниц с типовыми действиями
 */
@Component("operationPageStorage")
public class OperationPageStorage extends AbstractPageStorage {

    @Override
    public void init() {
        for (OperationMatching value : OperationMatching.values()) {
            Page page = new Page(value.getName(),value.getHrefName(), value.getHtmlName(),value.getDescription());
            pageSet.add(page);
        }
    }

    /**
     * Enum для хранения страниц
     */
    public enum OperationMatching {


        COOKIE("CookieHello","/cookie","Работа с cookie","Запоминание пользователя через cookie на 30 секунд."),
        DATE_UTC("DateUTC","/date", "Работа с датой и временем", "Отправка даты и времени на сервер, перекодирование из локального в UTC и получение даты от сервера"),
        SEND_FILE("SendFile", "/sendFile", "Скачивание файла с сервера", "Скачивание файла с сервера (динамическое получение файла) через GET запрос"),
        UPLOAD_FILE("UploadFile","/uploadFile", "Загрузка файла на сервер","Загрузка файла на сервер");

        private static final String BASE_DIR_PATH = "/operation";
        private String htmlName;
        private String hrefName;
        private String name;
        private String description;

        OperationMatching(String htmlName, String hrefName, String name, String description) {
            this.htmlName = htmlName;
            this.hrefName = hrefName;
            this.name = name;
            this.description = description;
        }

        /**
         * Возвращает имя шаблона HTML
         * @return имя шаблона HTML
         */
        public String getHtmlName() {
            return htmlName;
        }

        /**
         * Возвращает путь к странице на сервере
         * @return путь к странице на сервере
         */
        public String getHrefName() {
            return BASE_DIR_PATH + hrefName;
        }

        /**
         * Возвращает описание страницы
         * @return описание страницы
         */
        public String getDescription() {
            return description;
        }

        /**
         * Возвращает имя пункта в меню
         * @return имя пункта в меню
         */
        public String getName() {
            return name;
        }
    }

}
