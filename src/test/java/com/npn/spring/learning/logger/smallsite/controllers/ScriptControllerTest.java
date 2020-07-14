package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.driver.*;
import com.npn.spring.learning.logger.smallsite.models.factories.GetDirToJsonFactory;
import com.npn.spring.learning.logger.smallsite.models.factories.SaveMultipartFileFactory;
import com.npn.spring.learning.logger.smallsite.models.factories.SendFilesFactory;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ScriptControllerTest extends TestCase {

    private static final String PICK_DIR_NAME = "small";
    private static final String UPLOAD_DIR_NAME = "TestFiles";

    @InjectMocks
    private ScriptController controller;

    private MockMvc mockMvc;

    @InjectMocks
    private SendFilesFactory picsFactory;

    private String dirName = "small";

    @InjectMocks
    private GetDirToJsonFactory dirFactory;

    @InjectMocks
    private SaveMultipartFileFactory saveFactory;



    @Before
    public void init(){
        String smallImageBasePath = "/home/pavel/IdeaProjects/small-site-spring-log/storage/small";
        String dirBasePath = "/home/pavel/IdeaProjects/small-site-spring-log/src/test/TestFiles";

        try {
            PickFromFilesDriver driver = new PickFromFilesDriver(smallImageBasePath,
                    ".jpg","image/jpeg");
            List<GetFilesInterface> drivers = new ArrayList<>();
            drivers.add(driver);


            List<GetDirToJsonInterface> dirDriver = new ArrayList<>();
            UploadFilesDriver uploadDriver = new UploadFilesDriver(dirBasePath,"",
                    "application/octet-stream");
            dirDriver.add(uploadDriver);
            dirFactory.setDirDrivers(dirDriver);

            List<SaveMultipartFileToDir> saveDriver = new ArrayList<>();
            saveDriver.add(uploadDriver);
            saveFactory.setList(saveDriver);


            picsFactory.setFilesDrivers(drivers);
            controller.setPicsFactory(picsFactory);
            controller.setDirFactory(dirFactory);
            controller.setSaveFactory(saveFactory);

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /**
     * Тестирование GET метода отправки времени от сервера
     */
    @Test
    public void testGetTimeFromServer() {
        try {
            MvcResult result = mockMvc.perform(get("/scripts/date")).andReturn();

            assertEquals(200, result.getResponse().getStatus());
            assertEquals("text/plain;charset=UTF-8", result.getResponse().getContentType());
            assertEquals("UTF-8", result.getResponse().getCharacterEncoding());
            assertTrue(result.getResponse().getContentAsString().contains("UTC"));
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
    }

    /**
     * Тестирование POST метода получения времени от клиента
     */
    @Test
    public void testPostJsonObject() {
        try {

            RequestBuilder builder = MockMvcRequestBuilders
                    .post("/scripts/date")
                    .contentType(MediaType.TEXT_PLAIN_VALUE)
                    .characterEncoding("UTF-8")
                    .content("Tue, 07 Jul 2020 22:21:58 GMT");
            mockMvc.perform(builder)
                    .andExpect(status().isOk());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void provideObject() {
        try {

            String filename = "pick2.jpg";
            boolean download = true;
            RequestBuilder builder = MockMvcRequestBuilders
                    .get(String.format("/scripts/get?baseDir=%s&name=%s&download=%b",PICK_DIR_NAME,filename,download));
            mockMvc.perform(builder)
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/octet-stream;charset=UTF-8"));

        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void getDir() {
        RequestBuilder builder = MockMvcRequestBuilders.get("/scripts/dir?baseDir="+UPLOAD_DIR_NAME);
        try {
            mockMvc.perform(builder)
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("text/plain;charset=UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void postFile() {
        try {
        byte[] a = new byte[0];
        MockMultipartFile multipartFile = new MockMultipartFile("files","file.txt","multipart/form-data",a);

        RequestBuilder builder = MockMvcRequestBuilders.
                multipart(ScriptController.POST_FILE_MAPPING_URL+"?baseDir="+UPLOAD_DIR_NAME)
                .file(multipartFile).param("somedata","somedata");

            mockMvc.perform(builder)
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }
}