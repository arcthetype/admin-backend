package com.zavier.project.web.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class ShiroConfig {

    private final DataSource dataSource;

    public ShiroConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public Realm realm() {
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setAuthenticationQuery("select password from t_user where user_name = ?");
        jdbcRealm.setCachingEnabled(true);
        return jdbcRealm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/monitor/*", "authc");
        return chainDefinition;
    }

}