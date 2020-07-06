package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.AbstractPageStorage;
import org.springframework.ui.Model;

/**
 * Шаблон контроллера
 */
public abstract class AbstractController {

    protected AbstractPageStorage storage;

    protected AbstractPageStorage operationStorage;

    public abstract void setOperationStorage(AbstractPageStorage storage);

    public abstract void setStorage(AbstractPageStorage storage);

    protected void createHTMLTemplate(Model model) {
        addHeader(model);
    }

    protected void addHeader(Model model) {
        model.addAttribute("pages",storage.getPages());
        model.addAttribute("operations",operationStorage.getPages());
    }

}
