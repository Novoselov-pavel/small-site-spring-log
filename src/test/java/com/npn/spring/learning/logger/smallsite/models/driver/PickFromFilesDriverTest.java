package com.npn.spring.learning.logger.smallsite.models.driver;

import com.npn.spring.learning.logger.smallsite.models.ProvidedObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Класс тестирования PickFromFilesDriver обращаю внимание, что путь следует корректировать при переносе на другой компьютер
 */
public class PickFromFilesDriverTest {
    private static final String BASE_FILE_PATH = "/home/pavel/IdeaProjects/small-site-spring-log/src/test/TestFiles";
    private static final String EXTENSION = ".jpg";
    private static final String CONTENT_TYPE = "image/jpeg";
    @Test
    public void getObjectsList() {
        try {
            PickFromFilesDriver driver = new PickFromFilesDriver(BASE_FILE_PATH, EXTENSION,CONTENT_TYPE);
            List<ProvidedObject> list = driver.getObjectsList();
            assertEquals(3, list.size());
            list.forEach(x->{
                assertEquals(CONTENT_TYPE,x.getContentType());
                String expectedPath = BASE_FILE_PATH + File.separator + "pick" + list.indexOf(x) + EXTENSION;
                assertEquals(expectedPath, x.getLocation());
                String expectedHref = "/scripts/get?baseDir=TestFiles&name="+ "pick" + list.indexOf(x) + EXTENSION;
                assertEquals(expectedHref,x.getHref());
            });

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

    }
}