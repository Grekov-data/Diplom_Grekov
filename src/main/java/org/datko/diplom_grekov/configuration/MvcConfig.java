package org.datko.diplom_grekov.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // связываем путь /login с предсатвлением log-in.html
        registry.addViewController("/authorization/log-in").setViewName("login");
    }
}
