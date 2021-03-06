package com.npn.spring.learning.logger.smallsite.models;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс эмулятор хранилища пользователей
 */
public class UserStorage {
    private static final ConcurrentHashMap<Long, UserObject> storage = new ConcurrentHashMap<>(16,6);

    public Long addUser(UserObject userObject) {
        Long val = 0L;
        boolean isExist = false;
        while (!isExist) {
            Random random = new Random();
            val = Math.abs(random.nextLong());
            UserObject object = storage.putIfAbsent(val,userObject);
            if (object == null) isExist = true;
        }
        return val;
    }
}
