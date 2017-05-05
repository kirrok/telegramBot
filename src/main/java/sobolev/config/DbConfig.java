package sobolev.config;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "hikari.datasource")
@EnableTransactionManagement
public class DbConfig extends HikariConfig {
    private String dialect;
    private String hbm2dll;
    private String showSql;

    @Bean
    public DataSource dataSourse() {
        System.out.println(getDriverClassName());
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(getDriverClassName());
        dataSource.setUrl(getJdbcUrl());
        dataSource.setUsername(getUsername());
        dataSource.setPassword(getPassword());
        System.out.println(getJdbcUrl());
        return dataSource;
    }
    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSourse());

        sessionFactory.setHibernateProperties(new Properties() {{
            put("hibernate.dialect", dialect);
            put("hibernate.show_sql", showSql);
            put("hibernate.hbm2ddl.auto", hbm2dll);
            put("hibernate.query.substitutions", "true=1, false=0");
            put("hibernate.cache.use_second_level_cache", "false");
            put("hibernate.cache.use_query_cache", "false");
            put("hibernate.bytecode.provider ", "cglib");
            put("hibernate.jdbc.use_scrollable_resultset", "false");
            put("hibernate.connection.autocommit", String.valueOf(isAutoCommit()));
            put("hibernate.enable_lazy_load_no_trans", "true");
        }});
        return sessionFactory;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager platformTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public void setHbm2dll(String hbm2dll) {
        this.hbm2dll = hbm2dll;
    }

    public void setShowSql(String showSql) {
        this.showSql = showSql;
    }
}
