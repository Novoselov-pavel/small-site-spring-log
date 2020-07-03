package com.npn.spring.learning.logger.smallsite.models;

import org.springframework.stereotype.Component;

/**
 * Класс для хранения всех страниц в разделе "GET и POST запросы"
 */
@Component("requestPageStorage")
public class RequestPageStorage extends AbstractPageStorage {

    /**
     * В связи с использованием Mockito данный вариант инициализации более удобен чем через аннотацию
     * @PostConstruct
     * Иначе придется запускать тесты через @RunWith(SpringJUnit4ClassRunner.class) что неудобно,
     * так как запуск занимает время, или описывать поведение данного класса через Моск поведение через @Mock
     * что конечно правильнее с точки зрения логики тестирования, но занимает значительное время.
     *
     */
    @Override
    public void init() {
        for (PageAndViewMatching value : PageAndViewMatching.values()) {
            Page page = new Page(value.getName(),value.getHrefName(), value.getHtmlName(),value.getDescription());
            pageSet.add(page);
        }
    }

    /**
     * Enum для хранения страниц
     */
    public enum PageAndViewMatching {
        HOME("Main","/","Home","Привет, это тестовый сервер с разными реализациями элементов HTML и их взаимодействия с сервером."),
        GET_HREF("Hello","/hello", "GET через location.href","Страница с GET запросом через window.location.href"),
        GET_FORM("HelloFromForm","/helloForm","GET через form","Страница с GET запросом через html form" ),
        POST_FORM("RegistryForm","/registry", "POST через form","Страница с POST запросом через html form, ручная работа с данными"),
        POST_THYMELEAF("RegistryFormLeaf","/postThymeleaf", "POST через Thymeleaf Form", "Страница с POST запросом через Thymeleaf form, автоматическая работа с данными через Thymeleaf"),
        POST_JSON("PostJson","/postJson","POST через JScript в Json","Страница с POST запросом через JavaScript и Json");


        private String htmlName;
        private String hrefName;
        private String name;
        private String description;

        PageAndViewMatching(String htmlName, String hrefName, String name, String description) {
            this.htmlName = htmlName;
            this.hrefName = hrefName;
            this.name = name;
            this.description = description;
        }

        public String getHtmlName() {
            return htmlName;
        }

        public String getHrefName() {
            return hrefName;
        }

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }
    }
}
