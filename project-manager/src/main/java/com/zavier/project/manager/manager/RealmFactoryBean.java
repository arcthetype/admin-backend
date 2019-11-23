package com.zavier.project.manager.manager;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class RealmFactoryBean implements FactoryBean<Realm> {

    private final DataSource dataSource;

    public RealmFactoryBean(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Realm getRealm() {
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setAuthenticationQuery("select password from t_user where user_name = ?");
        return jdbcRealm;
    }

    @Override
    public Realm getObject() throws Exception {
        return getRealm();
    }

    @Override
    public Class<?> getObjectType() {
        return Realm.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
