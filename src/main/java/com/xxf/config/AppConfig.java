package com.xxf.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.xxf"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
@PropertySource("classpath:application.properties")
@MapperScan("com.xxf.mapper")
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource(PropertyConfig propertiesConfig) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(propertiesConfig.getUserName());
        dataSource.setPassword(propertiesConfig.getPassword());
        dataSource.setUrl(propertiesConfig.getUrl());
        dataSource.setDriverClassName(propertiesConfig.getDriver());
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource, PropertyConfig propertiesConfig) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(propertiesConfig.getMybatisTypeAliasPackage());
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "false");
        pageInterceptor.setProperties(properties);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});
        return sqlSessionFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
