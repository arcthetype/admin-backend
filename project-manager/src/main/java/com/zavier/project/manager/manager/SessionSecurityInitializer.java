package com.zavier.project.manager.manager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 用于初始化session security
 */
@Component
public class SessionSecurityInitializer {

    private final Realm realm;

    public SessionSecurityInitializer(Realm realm) {
        this.realm = realm;
    }

    @PostConstruct
    public void init() {
        SessionsSecurityManager securityManager = new DefaultSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);
    }
}
