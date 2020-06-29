package com.npn.spring.learning.logger.smallsite.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Описывает элемент страница
 */
public class Page {
    private final String name;
    private final String href;
    private final String htmlName;
    private String description = "";

    public Page(final String name,final String href,final String htmlName) {
        this.name = name;
        this.href = href;
        this.htmlName = htmlName;
    }

    public String getName() {
        return name;
    }

    public String getHref() {
        return href;
    }

    public String getHtmlName() {
        return htmlName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(name, page.name) &&
                Objects.equals(href, page.href) &&
                Objects.equals(htmlName, page.htmlName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, href, htmlName);
    }
}
