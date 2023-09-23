package com.hang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName ResourcesConfigAdapter
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/23 023 20:04
 * @Version 1.0
 */

@Configuration
public class ResourcesConfigAdapter extends WebMvcConfigurerAdapter {

    @Value(value = "${file.localUrl}")
    private String resourceDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/download/**").addResourceLocations("file:" + resourceDir);
        super.addResourceHandlers(registry);
    }

}