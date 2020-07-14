package com.npn.spring.learning.logger.smallsite.models.driver;

import com.npn.spring.learning.logger.smallsite.models.ProvidedObject;

import java.util.List;

/**
 * Интерфейс для получения списка файлов из указанной папки на сервере
 */
public interface GetFilesInterface {

    /**
     * Предоставляет список сторонних объектов для контроллера
     * @return List<ProvidedObject>
     */
    List<ProvidedObject> getObjectsList();

    /**
     * Возвращает имя папки, где находится файлы в файлом хранилище
     *
     * @return имя папки, где находится файлы в файлом хранилище
     */
    String getBaseDir();

}
