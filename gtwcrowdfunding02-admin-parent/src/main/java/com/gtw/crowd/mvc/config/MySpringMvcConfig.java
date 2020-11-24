package com.gtw.crowd.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author
 * @create 2020-11-05-0:28
 */
@Configuration
public class MySpringMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/to/login/page.html").setViewName("admin-login");
        registry.addViewController("/admin/to/main/page.html").setViewName("admin-main");
        registry.addViewController("/admin/to/add/page.html").setViewName("admin-add");
        registry.addViewController("/role/to/main/page.html").setViewName("role-main");
        registry.addViewController("/menu/to/main/page.html").setViewName("menu-main");

    }
}
