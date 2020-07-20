package com.npn.spring.learning.logger.smallsite.configs;

import com.npn.spring.learning.logger.smallsite.models.driver.Mp3FilesDriver;
import com.npn.spring.learning.logger.smallsite.models.driver.PickFromFilesDriver;
import com.npn.spring.learning.logger.smallsite.models.driver.UploadFilesDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

@Configuration
@PropertySource(value = "classPath:settings.properties", encoding = "UTF-8")
public class BeanConfig {

    @Value("${files.storage.location.pick.small}")
    private String smallPickPath;

    @Value("${files.storage.location.pick.large}")
    private String largePickPath;

    @Value("${files.storage.location.pick.uploadfiles}")
    private String uploadFilesPath;

    @Value("${files.storage.location.pick.musicFiles}")
    private String mp3FilesDriverPath;

    @Value("${upload.temp.dir}")
    private String multipartResolverTempPath;

    @Bean("smallPick")
    public PickFromFilesDriver getSmallPick() throws IOException {
      return new PickFromFilesDriver(smallPickPath,".jpg","image/jpeg");
    }

    @Bean("largePick")
    public PickFromFilesDriver getLargePick() throws IOException {
        return new PickFromFilesDriver(largePickPath,".jpg","image/jpeg");
    }

    @Bean("uploadFiles")
    public UploadFilesDriver getUploadFiles() throws IOException {
        return new UploadFilesDriver(uploadFilesPath,"","application/octet-stream");
    }

    @Bean("mp3FilesDriver")
    public Mp3FilesDriver getMp3FilesDriver() throws IOException {
        return new Mp3FilesDriver(mp3FilesDriverPath,".mp3","audio/mpeg");
    }

    @Bean("multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() throws IOException {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setMaxUploadSize(15728640L);
        resolver.setUploadTempDir(new FileSystemResource(multipartResolverTempPath));
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

}
