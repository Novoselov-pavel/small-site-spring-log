package com.npn.spring.learning.logger.smallsite.models.factories;

import com.npn.spring.learning.logger.smallsite.models.driver.SaveMultipartFileToDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SaveMultipartFileFactory {

    private CopyOnWriteArrayList<SaveMultipartFileToDir> list = new CopyOnWriteArrayList<>();

    @Autowired
    public void setList(List<SaveMultipartFileToDir> list) {
        this.list.addAll(list);
    }

    public SaveMultipartFileToDir getDriver(String dir){
        return list.stream().filter(x->x.getBaseDir().equals(dir)).findFirst().orElse(null);
    }

}
