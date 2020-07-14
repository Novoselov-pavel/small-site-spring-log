package com.npn.spring.learning.logger.smallsite.models.factories;

import com.npn.spring.learning.logger.smallsite.models.ProvidedObject;
import com.npn.spring.learning.logger.smallsite.models.driver.GetDirToJsonInterface;
import com.npn.spring.learning.logger.smallsite.models.driver.GetFilesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Фабрика для получения реализаций GetDirToJsonInterface (предназначены для преобразования файлов из папки, в объект Json)
 */
@Component
public class GetDirToJsonFactory {

    private List<GetDirToJsonInterface> dirDrivers;

    @Autowired
    public void setDirDrivers(List<GetDirToJsonInterface> dirDrivers) {
        this.dirDrivers = dirDrivers;
    }

    /**
     * Возвращает GetDirToJsonInterface соотвествующий переданной папке или null
     *
     * @param baseDir папка
     * @return GetDirToJsonInterface или null
     */
    public GetDirToJsonInterface getDriver(String baseDir){
        return dirDrivers.stream().filter(x->x.getBaseDir().equals(baseDir)).findFirst().orElse(null);
    }
}
