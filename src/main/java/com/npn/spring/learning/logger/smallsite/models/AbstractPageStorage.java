package com.npn.spring.learning.logger.smallsite.models;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * Шаблон класса для хранения страниц
 */
public abstract class AbstractPageStorage {
    protected CopyOnWriteArraySet<Page> pageSet = new CopyOnWriteArraySet<>();


    public AbstractPageStorage() {
        init();
    }

    public abstract void init();

    /**
     * Возвращает имя представления
     *
     * @param href ссылка на представление - например "/hello"
     * @return имя представления для использования в контроллере или пустую строку если объект отсутствует
     */
    public String getHtmlName(String href) {
        Page page = pageSet.stream().filter(x->x.getHref().equalsIgnoreCase(href)).findFirst().orElse(null);
        return page == null ? "" : page.getHtmlName();
    }

    /**
     *
     * Возвращает описание предстваления
     *
     * @param href ссылка на представление например "/hello"
     * @return описание представления для использования в контроллере или пустую строку если объект отсутствует
     */
    public String getDescription(String href) {
        Page page = pageSet.stream().filter(x->x.getHref().equalsIgnoreCase(href)).findFirst().orElse(null);
        return page == null ? "" : page.getDescription();
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
