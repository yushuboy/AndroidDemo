package com.axaet.ipc.beans;

import java.io.Serializable;

/**
 * date: 2019/3/16
 *
 * @author yuShu
 */
public class Person implements Serializable {

    private int age;
    private String name;
    private String identity;

    public Person(int age, String name, String identity) {
        this.age = age;
        this.name = name;
        this.identity = identity;
    }

    public Person() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }
}
