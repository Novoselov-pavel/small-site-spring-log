package com.npn.spring.learning.logger.smallsite.models.driver;

import com.npn.spring.learning.logger.smallsite.models.ProvidedObject;

import java.util.List;

public interface GetFilesInterface {

    /**
     * Предоставляет список сторонних объектов для контроллера
     * @return List<ProvidedObject>
     */
    List<ProvidedObject> getObjectsList();



}
