package com.crrcdt.ioc;

import com.crrcdt.ioc.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lj on 2020/9/16.
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        final ClassPathXmlApplicationContext cx = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) cx.getBean("user");
        final User user1 = cx.getBean("user4", User.class);
//        final User user2 = cx.getBean(User.class);
        //对象的创建 销毁 初始化都交给容器来做
        System.out.println(user);
        System.out.println(user1);
//        System.out.println(user2);
    }
}
