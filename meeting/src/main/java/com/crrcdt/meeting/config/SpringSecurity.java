package com.crrcdt.meeting.config;



import com.crrcdt.meeting.service.EmployeeService;
import com.crrcdt.meeting.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author lj on 2020/9/10.
 * @version 1.0
 */
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    @Autowired
    EmployeeServiceImpl employeeService;

    //请求授权验证
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // .denyAll();    //拒绝访问
        // .authenticated();    //需认证通过
        // .permitAll();    //无条件允许访问
        // 访问权限
        http.authorizeRequests()
                .antMatchers("/toLogin","/peopleManager/register","/peopleManager/doReg","/static/**").permitAll()
                .antMatchers("/**").authenticated()
                .antMatchers("/admin/**","/album/**","/tag/**").hasRole("admin");

        // 登录配置
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/toLogin") //自己的登入页
                .loginProcessingUrl("/login") // 登陆表单提交请求
                .defaultSuccessUrl("/employee/newNoticeMeetings"); // 设置默认登录成功后跳转的页面

        // 注销配置
        http.headers().contentTypeOptions().disable();
        http.headers().frameOptions().disable(); // 图片跨域
        http.csrf().disable();//关闭csrf功能:跨站请求伪造,默认只能通过post方式提交logout请求
        http.logout().logoutSuccessUrl("/toLogin");

        // 记住我配置
        http.rememberMe().rememberMeParameter("remember");

        //无权限用户提示字符串消息设置
        http.exceptionHandling()
                // getAccessDeniedHandler()是上文的方法
                .accessDeniedHandler(getAccessDeniedHandler());
    }

    // 用户授权验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeService).passwordEncoder(passwordEncoder());
    }

    // 密码加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
