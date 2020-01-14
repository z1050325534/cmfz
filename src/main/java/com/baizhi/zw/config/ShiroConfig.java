package com.baizhi.zw.config;

import com.baizhi.zw.realm.MyRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroConfig {
    @Bean
    // 创建过滤器工厂对象
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        // 创建过滤器工厂对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 过滤器链 --> 配置哪些需要拦截(认证)|那些不需要拦截(认证)
        /*
            anon : 匿名过滤器 声明的资源可以不认证进行访问
            authc : 认证过滤器 必须认证通过才可以访问
         */
        HashMap hashMap = new HashMap();
        hashMap.put("/**","authc");
        // 配置匿名资源 --> 登陆方法
        hashMap.put("/getCode/**","anon");
        hashMap.put("/boot/**","anon");
        hashMap.put("/echarts/**","anon");
        hashMap.put("/image/**","anon");
        hashMap.put("/jqgrid/**","anon");
        hashMap.put("/upload/**","anon");
        hashMap.put("/kindeditor-4.1.11-zh-CN/**","anon");
        hashMap.put("/admin/login","anon");
        // 配置登陆页面
        shiroFilterFactoryBean.setLoginUrl("/jsp/login.jsp");
        // 配置过滤器链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);
        return shiroFilterFactoryBean;
    }
    @Bean
    // 将安全管理器交由工厂管理
    public SecurityManager securityManager(DefaultWebSessionManager defaultWebSessionManager){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        CacheManager cacheManager = new EhCacheManager();
        defaultWebSecurityManager.setCacheManager(cacheManager);
        defaultWebSecurityManager.setRealm(myRealm());
        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager);
        return defaultWebSecurityManager;
    }
    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(RedisSessionDao redisSessionDao) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session的全局过期时间
        sessionManager.setGlobalSessionTimeout(1800 * 1000);
        // 是否删除无效会话
        sessionManager.setDeleteInvalidSessions(true);
        // 配置如何操作SessionManger
        sessionManager.setSessionDAO(redisSessionDao);
        // 启用Session验证
        sessionManager.setSessionValidationSchedulerEnabled(true);
        /**
         * 修改Cookie中的SessionId的key，默认为JSESSIONID，自定义名称
         */
        // 设置SessionId
        sessionManager.setSessionIdCookie(new SimpleCookie("JSESSIONID"));
        return sessionManager;
    }
    @Bean
    // 将MyRealm对象交由工厂管理
    public MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        // 自定义Realm需要MD5凭证匹配器支持
        myRealm.setCredentialsMatcher(credentialsMatcher());
        return myRealm;
    }
    @Bean
    // 将凭证匹配器交由工厂管理
    public CredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
}
