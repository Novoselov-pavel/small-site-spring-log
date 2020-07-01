package com.npn.spring.learning.logger.smallsite.models;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * Класс для хранения всех страниц
 */
@Component
public class PageStorage {
    private static final CopyOnWriteArraySet<Page> pageSet = new CopyOnWriteArraySet<>();

    /**
     * Инициализация страниц
     *
     */
    @PostConstruct
    private void init() {
        Page main = new Page("Main","/","Main");
        main.setDescription("Привет, это тестовый сервер с разными реализациями элементов HTML и их взаимодействия с сервером.");
        pageSet.add(main);

        Page hello = new Page("GET hello page","/hello","Hello");
        hello.setDescription("Страница с GET запросом через window.location.href");
        pageSet.add(hello);

        Page helloFromForm = new Page("GET hello from form" , "/helloForm","HelloFromForm");
        helloFromForm.setDescription("Страница с GET запросом через html form");
        pageSet.add(helloFromForm);

        Page registry = new Page("POST from form", "/registry", "RegistryForm");
        registry.setDescription("Страница с POST запросом через html form, ручная работа с данными");
        pageSet.add(registry);

        Page registryThymeleaf = new Page("POST from Thymeleaf", "/postThymeleaf", "RegistryFormLeaf");
        registryThymeleaf.setDescription("Страница с POST запросом через Thymeleaf form, автоматическая работа с данными через Thymeleaf");
        pageSet.add(registryThymeleaf);

        Page registryJson = new Page("POST from Json", "/postJson", "PostJson");
        registryThymeleaf.setDescription("Страница с POST запросом через JavaScript и Json");
        pageSet.add(registryJson);

    }

    /**
     * Возвращает имя представления
     *
     * @param href ссылка на предлставление - например "/hello"
     * @return имя представления для использования в контроллере или null если объект отсутсвует
     */
    public String getHtmlName(String href) {
        Page page = pageSet.stream().filter(x->x.getHref().equalsIgnoreCase(href)).findFirst().orElse(null);
        return page == null ? null : page.getHtmlName();
    }

    /**
     *
     * Возвращает описание предстваления
     *
     * @param href ссылка на представление например "/hello"
     * @return описание представления для использования в контроллере или null если объект отсутсвует
     */
    public String getDescription(String href) {
        Page page = pageSet.stream().filter(x->x.getHref().equalsIgnoreCase(href)).findFirst().orElse(null);
        return page == null ? null : page.getDescription();
    }


    /**
     * Возврощает Unmodifiable List страниц
     *
     * @return
     */
    public List<Page> getPages() {
        return pageSet.stream().collect(Collectors.toUnmodifiableList());
    }

}
