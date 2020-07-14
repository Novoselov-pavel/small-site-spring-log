package com.npn.spring.learning.logger.smallsite.models.driver;

/**
 * Интерфейс для преобразования списка файлов из папки, в объект Json
 */
public interface GetDirToJsonInterface {

    /**
     * Преобразует список файлов внутри папки в объект Json
     *
     * @return Json object
     * @throws Exception
     */
    String getObjectsListAsJson() throws Exception;

    /**
     * Получает папку, для работы с которой предназначена реализация
     * @return имя папки
     */
    String getBaseDir();
}
