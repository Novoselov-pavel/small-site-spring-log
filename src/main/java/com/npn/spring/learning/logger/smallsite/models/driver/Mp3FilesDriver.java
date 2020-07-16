package com.npn.spring.learning.logger.smallsite.models.driver;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class Mp3FilesDriver extends UploadFilesDriver {
    /**
     * Конструктор драйвера для получения списка файлов из указанной папки на сервере
     *
     * @param basePath    путь к папке хранения файлов на сервере
     * @param extension   расширение файлов
     * @param contentType тип Content-Type запроса.
     * @throws IOException при ошибке чтения файлов
     */
    public Mp3FilesDriver(String basePath, String extension, String contentType) throws IOException {
        super(basePath, extension, contentType);
    }


    /**
     * Возвращает строку запроса файла в формате String.format например "/scripts/get?baseDir=%s&name=%s"
     *
     * @return строку запроса файла в формате String.format
     */
    @Override
    public String getBaseHrefFormatString() {
        return "/scripts/get?baseDir=%s&name=%s&download=false";
    }

    /**
     *
     * Не поддерживается
     *
     * @throws UnsupportedOperationException
     */
    @Override
    public void saveMultipartFiles(MultipartFile[] files) {
        throw new UnsupportedOperationException("Mp3FilesDriver unsupported save operation");
    }




}
