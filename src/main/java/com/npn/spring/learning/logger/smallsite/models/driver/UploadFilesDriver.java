package com.npn.spring.learning.logger.smallsite.models.driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для преобразования списка файлов из папки, в объект Json, и в ProvidedObject,
 * а также для сохранения файлов в папке
 */
@Scope("prototype")
public class UploadFilesDriver extends AbstractFilesDriver implements GetDirToJsonInterface,SaveMultipartFileToDir {
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

    /**
     * Преобразует список файлов внутри папки в объект Json
     *
     * @return Json object
     * @throws Exception
     */
    @Override
    public String getObjectsListAsJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(getObjectsList());
    }

    /**
     * Сохраняет файлы в папке, исключения игнорируются.
     *
     * @param files MultipartFile[]
     */
    @Override
    public void saveMultipartFiles(MultipartFile[] files){
        for (MultipartFile file : files) {
            try {
                String fileName = file.getOriginalFilename();
                Path filePath = basePath.resolve(Paths.get(fileName));
                file.transferTo(filePath);
            } catch (Exception ignored) {}

        }
    }
}
