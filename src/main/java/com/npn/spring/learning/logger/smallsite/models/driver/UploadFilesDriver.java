package com.npn.spring.learning.logger.smallsite.models.driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

@Scope("prototype")
public class UploadFilesDriver extends AbstractFilesDriver implements GetDirToJsonInterface {
    private final String loadAllHref = "/scripts/getJson?baseDir=%s";

    /**
     * Конструктор драйвера для получения списка файлов из указанной папки на сервере
     *
     * @param basePath    путь к папке хранения файлов на сервере
     * @param extension   расширение файлов
     * @param contentType тип Content-Type запроса.
     * @throws IOException при ошибке чтения файлов
     */
    public UploadFilesDriver(String basePath, String extension, String contentType) throws IOException {
        super(basePath, extension, contentType);
    }

    /**
     * Возвращает строку запроса файла в формате String.format например "/scripts/get?baseDir=%s&name=%s"
     *
     * @return строку запроса файла в формате String.format
     */
    @Override
    public String getBaseHrefFormatString() {
        return "/scripts/get?baseDir=%s&name=%s&download=true";
    }

    @Override
    public String getObjectsListAsJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(getObjectsList());
    }
}
