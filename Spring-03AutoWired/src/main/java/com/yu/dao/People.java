package com.yu.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;

public class People {
    @Autowired
    @Qualifier("cat1")
    private cat cat;
    @Resource(name = "dog1")
    private Dog dog;
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.yu.dao.cat getCat() {
        return cat;
    }

    public void setCat(com.yu.dao.cat cat) {
        this.cat = cat;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", cat=" + cat +
                ", dog=" + dog +
                '}';
    }
}
