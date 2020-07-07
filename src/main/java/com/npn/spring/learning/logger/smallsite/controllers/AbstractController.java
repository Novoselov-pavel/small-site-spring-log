package com.npn.spring.learning.logger.smallsite.controllers;

import com.npn.spring.learning.logger.smallsite.models.AbstractPageStorage;
import org.springframework.ui.Model;

/**
 * Шаблон контроллера
 */
public abstract class AbstractController {

    protected AbstractPageStorage requestPageStorage;

    protected AbstractPageStorage operationStorage;

    public abstract void setOperationStorage(AbstractPageStorage storage);

    public abstract void setRequestPageStorage(AbstractPageStorage requestPageStorage);

    protected void createHTMLTemplate(Model model) {
        addHeader(model);
    }

    protected void addHeader(Model model) {
        model.addAttribute("pages", requestPageStorage.getPages());
        model.addAttribute("operations",operationStorage.getPages());
    }

}
