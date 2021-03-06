package librarySystem.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import librarySystem.util.CodeUtil;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"librarySystem"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
        value = EnableWebMvc.class), @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
@PropertySource(value = "classpath:db.properties")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true,proxyTargetClass = true)
public class RootConfig {
    @Bean(destroyMethod = "close")
    public DataSource dataSource(Environment env) {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(env.getProperty("driver"));
            dataSource.setJdbcUrl(env.getProperty("url"));
            dataSource.setUser("root");
            dataSource.setPassword("yayuanzi8");
            dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("initialSize")));
            dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("maxActive")));
            dataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("maxIdle")));
            dataSource.setMaxConnectionAge(Integer.parseInt(env.getProperty("maxWait")));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        bean.setTypeAliasesPackage("librarySystem.domain");
        String mapping = "librarySystem/mapper/*.xml";
        try {
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapping));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("librarySystem.dao");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return configurer;
    }

    @Bean
    public CodeUtil codeUtil() {
        return new CodeUtil();
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.163.com");
        sender.setUsername("yayuanzi8@163.com");
        sender.setPassword("gaoyisanban67hao");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        sender.setJavaMailProperties(properties);
        return sender;
    }
}
