package com.npn.spring.learning.logger.smallsite.models.driver;

import com.npn.spring.learning.logger.smallsite.models.ProvidedObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Драйвер для получения списка картинок из указанной папки на сервере
 */
@Service
@Scope("prototype")
public class PickFromFilesDriver implements GetFilesInterface{
    private static final String BASE_HREF_PATH_FORMAT = "/scripts/get?baseDir=%s&name=%s";
    private Path basePath;
    private String extension;
    private String contentType;
    private List<Path> filePaths = new ArrayList<>();


    private PickFromFilesDriver() {
    }

    public PickFromFilesDriver(String basePath, String extension, String contentType) throws IOException {
        this.basePath = Paths.get(basePath);
        this.extension = extension.toLowerCase();
        this.contentType = contentType;
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
                                basePath.subpath(basePath.getNameCount()-1,basePath.getNameCount()),
                                x.getFileName())))
                .collect(Collectors.toUnmodifiableList());
    }

    private void init() throws IOException {
        Files.walk(basePath,FileVisitOption.FOLLOW_LINKS).forEach(x->{
            if (x.getFileName().toString().toLowerCase().endsWith(extension)) {
                filePaths.add(x);
            }
        });
    }
}
