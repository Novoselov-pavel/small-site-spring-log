package com.npn.spring.learning.logger.smallsite.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

public class UserObject {
    private long id;
    private String name;
    private String email;
    private String password;
    private boolean check;

    public static UserObject createFromMap(Map map) {
        UserObject user = new UserObject();
        user.setName( getValFromMap(map,"name"));
        user.setEmail(getValFromMap(map,"email"));
        user.setPassword(getValFromMap(map,"password"));
        user.setCheck("true".equalsIgnoreCase(getValFromMap(map, "check")));
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserObject that = (UserObject) o;
        return check == that.check &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, check);
    }

    private static String getValFromMap(Map map, String key) {
       if (map.get(key) == null) {
           return "";
       }
       String[] s = (String[]) map.get(key);
       if (s.length == 0) {
           return  "";
       } else {
           return s[0];
       }
    }




}
