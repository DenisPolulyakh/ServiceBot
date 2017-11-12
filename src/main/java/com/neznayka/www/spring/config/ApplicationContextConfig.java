package com.neznayka.www.spring.config;

import com.neznayka.www.dao.config.ConfigDAO;
import com.neznayka.www.dao.config.ConfigDAOStubImpl;
import com.neznayka.www.dao.config.ConfigDictionaryDAOImpl;
import com.neznayka.www.dao.config.ConfigDictionaryDAOIntf;
import com.neznayka.www.hibernate.Tag;
import com.neznayka.www.hibernate.Message;
import com.neznayka.www.hibernate.Logging;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import javax.sql.DataSource;

/**
 * @author Denis Polulyakh
 *         17.03.2017.
 */
@Configuration
@ComponentScan("com.neznayka.www")
@EnableTransactionManagement
public class ApplicationContextConfig {

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
       /* BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/bot");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;*/

        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        String username = System.getenv("JDBC_DATABASE_USERNAME");
        String password = System.getenv("JDBC_DATABASE_PASSWORD");

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }
    @Bean(name = "ConfigDAOStub")
    public ConfigDAO getConfigDAO() {
        return new ConfigDAOStubImpl();
    }

    @Bean(name = "ConfigDAO")
    public ConfigDictionaryDAOIntf getConfigDictionaryDAO() {
        return new ConfigDictionaryDAOImpl();
    }


    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

        sessionBuilder.addAnnotatedClasses(Message.class);
        sessionBuilder.addAnnotatedClasses(Tag.class);
        sessionBuilder.addAnnotatedClasses(Logging.class);

        sessionBuilder.setProperty("hibernate.hbm2ddl.auto", "create");
        sessionBuilder.setProperty("hibernate.show_sql", "true");
        sessionBuilder.setProperty("hibernate.format_sql", "true");
        sessionBuilder.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        //sessionBuilder.setProperty("hibernate.jdbc.use_streams_for_binary","true");
        //sessionBuilder.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        sessionBuilder.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        return sessionBuilder.buildSessionFactory();
    }


    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(
            SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(
                sessionFactory);

        return transactionManager;
    }
}
