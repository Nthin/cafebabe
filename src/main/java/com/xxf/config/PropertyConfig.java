package com.xxf.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Getter
@Configuration
@PropertySource("classpath:application.properties")
public class PropertyConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver}")
    private String driver;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.web.view.prefix}")
    private String webViewPrefix;

    @Value("${spring.web.view.suffix}")
    private String webViewSuffix;

    @Value("${spring.web.static.handler}")
    private String webStaticHandler;

    @Value("${spring.web.static.resource}")
    private String webStaticResource;

    @Value("${spring.web.static.cache.period}")
    private Integer webStaticCachedPeriod;

    @Value("${mybatis.type.alias.package}")
    private String mybatisTypeAliasPackage;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
