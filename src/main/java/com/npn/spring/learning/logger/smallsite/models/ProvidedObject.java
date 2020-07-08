package com.npn.spring.learning.logger.smallsite.models;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Описывает сторонний, предоставаляемый контроллеру объект (файлы JPG, и т.д.)
 */
public class ProvidedObject {
    private final String location;
    private final String contentType;
    private final String href;

    /**
     * Конструктор
     *
     * @param location расположение файла на сервере
     * @param contentType тип контента файла
     * @param href ссылка для get запроса контроллера
     */
    public ProvidedObject(String location, String contentType, String href) {
        if (location == null || contentType == null || href == null || location.isBlank() || contentType.isBlank() || href.isBlank()) {
            throw new IllegalArgumentException("Null element not allowed in ProvidedObject's constructor arguments.");
        }
        this.location = location;
        this.contentType = contentType;
        this.href = href;
    }

    public String getLocation() {
        return location;
    }

    public Path getPath() {
        return Paths.get(location);
    }

    public URI getURI() {
        return URI.create(location);
    }

    public String getContentType() {
        return contentType;
    }

    public String getHref(){
        return href;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvidedObject that = (ProvidedObject) o;
        return location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
