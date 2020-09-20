package com.kuang.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//aop面向切面编程
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    //链式编程
    //授权
    protected void configure(HttpSecurity http) throws Exception {
        //请求授权的规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");
        //没有权限 跳到登入页面
//        http.formLogin();
        http.logout();
        //定制登入页面
        http.formLogin().loginPage("/toLogin").usernameParameter("username")
                .passwordParameter("password").loginProcessingUrl("/login");// 登陆表单提交请求
        //定制用户名和密码
//        http.formLogin().usernameParameter("username").passwordParameter("password");
        //防止网站工具：get post //关闭打开的csrf保护
        http.csrf().disable();
//        http.logout().logoutSuccessUrl("/");//成功登出请求路径、
        //开启记住我功能 cookie 默认保存两周 自定义接受前端的参数
        http.rememberMe().rememberMeParameter("remember");


    }
//    认证
    @Override
    //错误：There is no PasswordEncoder mapped for the id "null"
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("liujun").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
    }
}
