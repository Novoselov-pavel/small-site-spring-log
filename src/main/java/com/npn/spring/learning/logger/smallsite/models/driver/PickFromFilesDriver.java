package com.npn.spring.learning.logger.smallsite.models.driver;

import com.npn.spring.learning.logger.smallsite.models.ProvidedObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Драйвер для получения списка картинок из указанной папки на сервере
 */
@Scope("prototype")
public class PickFromFilesDriver implements GetFilesInterface{
    private static final String BASE_HREF_PATH_FORMAT = "/scripts/get?baseDir=%s&name=%s";
    private Path basePath;
    private String extension;
    private String contentType;
    private List<Path> filePaths = new ArrayList<>();
    private String baseDir;


    private PickFromFilesDriver() {
    }

    public PickFromFilesDriver(final String basePath,final String extension,final String contentType) throws IOException {
        this.basePath = Paths.get(basePath);
        this.extension = extension.toLowerCase();
        this.contentType = contentType;
        this.baseDir = this.basePath.subpath(this.basePath.getNameCount()-1,this.basePath.getNameCount()).toString();
        init();
    }

    /**
     * Предоставляет список сторонних объектов для контроллера
     *
     * @return List<ProvidedObject>
     */
    @Override
    public List<ProvidedObject> getObjectsList() {
        return filePaths.stream()
                .map(x->new ProvidedObject(x.toString(),
                        contentType,
                        String.format(BASE_HREF_PATH_FORMAT,
                                baseDir,
                                x.getFileName())))
                .sorted(Comparator.comparing(ProvidedObject::getLocation))
                .collect(Collectors.toUnmodifiableList());
    }

    private void init() throws IOException {
        Files.walk(basePath,FileVisitOption.FOLLOW_LINKS).forEach(x->{
            if (Files.isRegularFile(x) && x.getFileName().toString().toLowerCase().endsWith(extension)) {
                filePaths.add(x);
            }
        });
    }
    /**
     * Возвращает имя папки, где находится файлы в файлом хранилище
     *
     * @return имя папки, где находится файлы в файлом хранилище
     */
    @Override
    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }
}
