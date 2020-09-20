package com.crrcdt.ioc.model;

/**
 * @author lj on 2020/9/16.
 * @version 1.0
 */
public class Cat {
    private String name;
    private String age;

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
