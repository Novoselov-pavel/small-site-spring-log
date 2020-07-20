package com.npn.spring.learning.logger.smallsite.models.driver;

import org.springframework.context.annotation.Scope;

import java.io.IOException;

/**
 * Драйвер для получения списка файлов из указанной папки на сервере
 */
@Scope("prototype")
public class PickFromFilesDriver extends AbstractFilesDriver {
    private static final String BASE_HREF_PATH_FORMAT = "/scripts/get?baseDir=%s&name=%s";

    /**
     * Конструктор драйвера для получения списка файлов из указанной папки на сервере
     *
     * @param basePath    путь к папке хранения файлов на сервере
     * @param extension   расширение файлов
     * @param contentType тип Content-Type запроса.
     * @throws IOException при ошибке чтения файлов
     */
    public PickFromFilesDriver(String basePath, String extension, String contentType) throws IOException {
        super(basePath, extension, contentType);
    }


    /**
     * Возвращает строку запроса файла в формате String.format например "/scripts/get?baseDir=%s&name=%s"
     *
     * @return строку запроса файла в формате String.format
     */
    @Override
    public String getBaseHrefFormatString() {
        return BASE_HREF_PATH_FORMAT;
    }
}
