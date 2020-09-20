package com.kuang.pojo;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "person")
//@PropertySource("classpath:liujun.properties")
@Validated
public class Person {
//    @Value("${name}")
//    @Email//name必须是邮箱格式
     private String name;
     private Integer age;
     private Map<Object,Object> maps;
     private List<Object> lists;
     private Date birth;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", maps=" + maps +
                ", lists=" + lists +
                ", birth=" + birth +
                '}';
    }

    public Person() {
    }

    public Person(String name, Integer age, Map<Object, Object> maps, List<Object> lists, Date birth) {
        this.name = name;
        this.age = age;
        this.maps = maps;
        this.lists = lists;
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Map<Object, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<Object, Object> maps) {
        this.maps = maps;
    }

    public List<Object> getLists() {
        return lists;
    }

    public void setLists(List<Object> lists) {
        this.lists = lists;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
