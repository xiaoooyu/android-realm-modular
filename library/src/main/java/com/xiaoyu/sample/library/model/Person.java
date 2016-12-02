package com.xiaoyu.sample.library.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Copyright ©2016 by Teambition
 */
public class Person extends RealmObject {

    // All fields are by default persisted
    private String name;
    private int age;

    // Other objects in a one-to-one relation must also subclass RealmObject
    private Dog dog;

    private RealmList<Cat> cats;

    private int tempReference;

    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public RealmList<Cat> getCats() {
        return cats;
    }

    public void setCats(RealmList<Cat> cats) {
        this.cats = cats;
    }

    public int getTempReference() {
        return tempReference;
    }

    public void setTempReference(int tempReference) {
        this.tempReference = tempReference;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
