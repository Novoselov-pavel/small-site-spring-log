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
    private final String downloadContentType = "application/octet-stream";
    private final String DOWNLOAD_HREF_PARAM = "&download=true";

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

    /**
     * Возвращает имя файла
     * @return имя файла
     */
    public String getFileName(){
        return Paths.get(location).getFileName().toString();
    }

    /**
     * Возвращает тип контента для содержимого
     *
     * @return тип контента
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Возвращает адрес для получения объекта с типом contentType
     * @return адрес для получения объекта с типом contentType
     */
    public String getHref(){
        return href;
    }

    /**
     * Возвращает адрес для получения объекта с типом application/octet-stream
     * @return адрес для получения объекта с типом application/octet-stream
     */
    public String getDownloadHref() {
        return href+DOWNLOAD_HREF_PARAM;
    }

    /**
     * Возвращает размер файла в байтах
     *
     * @return размер файла в байтах
     */
    public long getLength() {
        return getPath().toFile().length();
    }

    /**
     * Возвращает размер файла в килобайтах
     *
     * @return размер файла в байтах
     */
    public long getLengthAtKb(){
        return getLength()/1024;
    }



    /**
     * Возвращает всегда "application/octet-stream"
     *
     * @return "application/octet-stream"
     */
    public String getDownloadContentType() {
        return downloadContentType;
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
