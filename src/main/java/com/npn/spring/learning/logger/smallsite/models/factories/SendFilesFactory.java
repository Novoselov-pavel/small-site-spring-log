package com.npn.spring.learning.logger.smallsite.models.factories;

import com.npn.spring.learning.logger.smallsite.models.ProvidedObject;
import com.npn.spring.learning.logger.smallsite.models.driver.GetFilesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Фабрика для получения ProvidedObject из реализаций GetFilesInterface
 */
@Component
public class SendFilesFactory {

    List<GetFilesInterface> filesDrivers;

    @Autowired
    public void setFilesDrivers(List<GetFilesInterface> filesDrivers) {
        this.filesDrivers = filesDrivers;
    }


    /**
     * Возвращает список из ProvidedObject соотвествующий переданной папке
     *
     * @param baseDir папка, среди объектов которой ведется поиск файла
     * @return UnmodifiableList
     */
    public List<ProvidedObject> getObjectList(String baseDir) {
        return  filesDrivers.stream()
                .filter(x->x.getBaseDir().equals(baseDir))
                .flatMap(x->x.getObjectsList().stream())
                .sorted(Comparator.comparing(ProvidedObject::getFileName))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Возвращает объект ProvidedObject соотвествующий переданной папке и имени файла
     *
     * @param baseDir папка, среди объектов которой ведется поиск файла
     * @param fileName имя файла
     * @return ProvidedObject или null, если объект не найден
     */
    public ProvidedObject getObject(String baseDir, String fileName){
        return filesDrivers.stream()
                .filter(x->x.getBaseDir().equals(baseDir))
                .flatMap(x->x.getObjectsList().stream())
                .filter(x->x.getFileName().equals(fileName)).findFirst().orElse(null);
    }

}
