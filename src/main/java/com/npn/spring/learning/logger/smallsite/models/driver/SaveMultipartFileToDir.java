package com.npn.spring.learning.logger.smallsite.models.driver;

import org.springframework.web.multipart.MultipartFile;

public interface SaveMultipartFileToDir {


    /**
     * Сохраняет файлы в папке.
     * @param files MultipartFile[]
     */
    void saveMultipartFiles(MultipartFile[] files);

    /**
     * Возвращает имя папки, куда сохраняет данные этот класс
     *
     * @return имя папки назначения
     */
    String getBaseDir();
}
