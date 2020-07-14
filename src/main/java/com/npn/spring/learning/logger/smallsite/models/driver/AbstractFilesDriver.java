package com.npn.spring.learning.logger.smallsite.models.driver;

import com.npn.spring.learning.logger.smallsite.models.ProvidedObject;

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
 * Шаблон драйвера для получения списка файлов из указанной папки на сервере
 */
public abstract class AbstractFilesDriver implements GetFilesInterface {
    Path basePath;
    private String extension;
    private String contentType;
    private List<Path> filePaths = new ArrayList<>();
    private String baseDir;

    /**
     * Конструктор драйвера для получения списка файлов из указанной папки на сервере
     *
     * @param basePath путь к папке хранения файлов на сервере
     * @param extension расширение файлов
     * @param contentType тип Content-Type запроса.
     *
     * @throws IOException при ошибке чтения файлов
     */
    public AbstractFilesDriver(final String basePath,final String extension,final String contentType) throws IOException {
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
        try {
            init();
        } catch (IOException ignored) {}

        return filePaths.stream()
                .map(x->new ProvidedObject(x.toString(),
                        contentType,
                        String.format(getBaseHrefFormatString(),
                                baseDir,
                                x.getFileName())))
                .sorted(Comparator.comparing(ProvidedObject::getLocation))
                .collect(Collectors.toUnmodifiableList());
    }

    private void init() throws IOException {
        filePaths.clear();
        Files.walk(basePath, FileVisitOption.FOLLOW_LINKS).forEach(x->{
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

    /**
     * Возвращает строку запроса файла в формате String.format например "/scripts/get?baseDir=%s&name=%s"
     * @return строку запроса файла в формате String.format
     */
    public abstract String getBaseHrefFormatString();

}
