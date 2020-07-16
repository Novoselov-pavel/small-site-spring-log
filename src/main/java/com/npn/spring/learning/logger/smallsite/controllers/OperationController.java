package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.AbstractPageStorage;
import com.npn.spring.learning.logger.smallsite.models.OperationPageStorage;
import com.npn.spring.learning.logger.smallsite.models.UserObject;
import com.npn.spring.learning.logger.smallsite.models.factories.SendFilesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Контроллер типовых действий
 */
@Controller
@RequestMapping("/operation")
public class OperationController extends AbstractController {
    private static final int COOKIE_EXPIRED_SECOND = 30;

    private SendFilesFactory picsFactory;

    /**
     * Имя директории, в которой сканируются файлы для карусели формы sendFile
     */
    private String dirName;

    /**
     * Имя директории, в которой сканируются файлы для формы uploadFile
     *
     */
    private String uploadDirName;

    private int maxUploadedFileLength;

    private String musicDirName;

    @Value("${operation.form.PlayMusic.dir}")
    public void setMusicDirName(String musicDirName) {
        this.musicDirName = musicDirName;
    }

    @Autowired
    public void setPicsFactory(SendFilesFactory picsFactory) {
        this.picsFactory = picsFactory;
    }

    /**
     * Позволяет выполнить автопривязку свойства operation.form.sendFile.dir файла settings.properties
     *
     * @param dirName Имя директории, в которой сканируются файлы для карусели формы sendFile
     */
    @Value("${operation.form.sendFile.dir}")
    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    @Value("${operation.form.UploadFile.dir}")
    public void setUploadDirName(String uploadDirName) {
        this.uploadDirName = uploadDirName;
    }

    @Value("${files.max.uploaded.file.length}")
    public void setMaxUploadedFileLength(int maxUploadedFileLength) {
        this.maxUploadedFileLength = maxUploadedFileLength;
    }

    /**
     * GET метод для работы с Cookie, выдает страницу в зависимости от того, есть имя (name) в cookie или нет.
     *
     * @return
     */
    @GetMapping("/cookie")
    public String getCookieRequest(@CookieValue(value = "name", defaultValue = "", required = false)String name, Model model){
        createHTMLTemplate(model);
        OperationPageStorage.OperationMatching form = OperationPageStorage.OperationMatching.COOKIE;
        model.addAttribute("message", form.getDescription());
        if (name == null || name.isBlank()) {
            guestToModel(model,form);
        } else {
            userToModel(name,model,form);
        }
        return form.getHtmlName();
    }

    /**
     * Post метод для работы с Cookie. Создает Cookie и перенаправляет на страницу /cookie для загрузки через GET запрос.
     * Использовать forward нельзя, так как иначе запрос придет на эту же функцию и все закольцуется.
     *
     * @param user UserObject
     * @param response HttpServletResponse
     * @param model model
     * @return ModelAndView c редиректом.
     */
    @PostMapping("/cookie")
    public String getCookiePost(@ModelAttribute UserObject user, HttpServletResponse response, Model model) {
        createHTMLTemplate(model);
        OperationPageStorage.OperationMatching form = OperationPageStorage.OperationMatching.COOKIE;
        Cookie cookie = new Cookie("name", user.getName());
        cookie.setMaxAge(COOKIE_EXPIRED_SECOND);
        response.addCookie(cookie);
        return "redirect:" + form.getHrefName();
        ///return new ModelAndView("redirect:"+form.getHrefName(), model.asMap());
    }

    /**
     * GET метод для странички с датой
     * @return имя предстваления
     */
    @GetMapping("/date")
    public String getDatePage(Model model){
        createHTMLTemplate(model);
        OperationPageStorage.OperationMatching page = OperationPageStorage.OperationMatching.DATE_UTC;
        model.addAttribute("message", page.getDescription());
        return page.getHtmlName();
    }

    /**
     * GET метод для странички со скачиванием файлов
     * @return имя представления
     */
    @GetMapping("/sendFile")
    public String getSendFileForm(Model model) {
        createHTMLTemplate(model);
        OperationPageStorage.OperationMatching page = OperationPageStorage.OperationMatching.SEND_FILE;
        model.addAttribute("message", page.getDescription());
        model.addAttribute("myPics",picsFactory.getObjectList(dirName));

        return page.getHtmlName();
    }

    /**
     * GET метод для странички загрузки файлов на сервер
     * @return имя представления
     */
    @GetMapping("/uploadFile")
    public String getUploadFileForm(Model model) {
        String stringDirRequest = "?baseDir=" + uploadDirName;

        createHTMLTemplate(model);
        OperationPageStorage.OperationMatching page = OperationPageStorage.OperationMatching.UPLOAD_FILE;
        model.addAttribute("message", page.getDescription());
        model.addAttribute("tableRef", ScriptController.DIR_GET_MAPPING_URL+stringDirRequest);
        model.addAttribute("action", ScriptController.POST_FILE_MAPPING_URL+stringDirRequest);
        model.addAttribute("maxlength", maxUploadedFileLength);
        return page.getHtmlName();
    }

    /**
     * GET метод для странички музыкального плеера
     * @return имя представления
     */
    @GetMapping("/playMusic")
    public String getPlayMusicForm(Model model) {
        createHTMLTemplate(model);
        String stringDirRequest = "?baseDir=" + musicDirName;
        OperationPageStorage.OperationMatching page = OperationPageStorage.OperationMatching.PLAY_MUSIC;
        model.addAttribute("message", page.getDescription());
        model.addAttribute("ref",ScriptController.DIR_GET_MAPPING_URL+stringDirRequest);
        return page.getHtmlName();
    }

    @Override
    @Autowired
    @Qualifier("requestPageStorage")
    public void setRequestPageStorage(AbstractPageStorage requestPageStorage) {
        this.requestPageStorage = requestPageStorage;
    }

    @Override
    @Autowired
    @Qualifier("operationPageStorage")
    public void setOperationStorage(AbstractPageStorage storage) {
        this.operationStorage = storage;
    }

    private void guestToModel(Model model, OperationPageStorage.OperationMatching form) {
        model.addAttribute("hello", "Привет Гость, назови себя:");
        UserObject userObject = new UserObject();
        model.addAttribute("user", userObject);
        model.addAttribute("showForm", true);
        model.addAttribute("action", form.getHrefName());
    }

    private void userToModel(String name,Model model, OperationPageStorage.OperationMatching form) {
        model.addAttribute("hello", "Привет " + name);
        UserObject userObject = new UserObject();
        model.addAttribute("user", userObject);
        model.addAttribute("action", form.getHrefName());
        model.addAttribute("showForm", false);
    }

}
