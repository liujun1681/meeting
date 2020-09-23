package com.kuang.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
         //第三步 ShiroFilterFactoryBean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        final ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);
        //添加内置过滤器
        /**
         * anon 无需认证即可访问
         * authc 需要认证才可访问
         * user 记住我
         * perms 拥有对某个资源的权限才能访问
         * role  拥有某个角色权限才可以访问
         */
        Map<String, String> filterMap=new LinkedHashMap<>();
       filterMap.put("/user/add","authc");
       filterMap.put("/user/update","authc");
       //授权
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","roles[admin]");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登入的请求
        bean.setLoginUrl("/toLogin");
        //设置未授权请求
        bean.setUnauthorizedUrl("/unauth");
        return bean;
    }

         //第二步 DefaultWebSecurityManage
    @Bean(name = "securityManager")
     public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getUserRealm") UserRealm realm){
         final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
         //关联realm
         securityManager.setRealm(realm);
         return securityManager;
     }

        //第一步 创建realm 对象 自定义类UserRealm
    @Bean
       public UserRealm getUserRealm(){
           return new UserRealm();
       }
}
