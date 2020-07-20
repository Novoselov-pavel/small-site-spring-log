package com.npn.spring.learning.logger.smallsite.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@ComponentScan("com.npn.spring.learning.logger.smallsite.*")
@EnableAspectJAutoProxy
//@Import({BeanConfig.class})
public class ApplicationConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Конфигурация для включения ThymeLeaf
     *
     * @return Bean templateResolver
     */
    @Bean("templateResolver")
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    /**
     *
     * Конфигурация для включения ThymeLeaf
     *
     * @return Bean templateEngine
     */
    @Bean("templateEngine")
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }


    /**
     * Установка поддержки русских букв
     * @return
     */
    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setViewNames(new String[] {"*"});
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/operation/**").addResourceLocations("classpath:/viewsresource/").setCachePeriod(3500);
        registry.addResourceHandler("/request/**").addResourceLocations("classpath:/viewsresource/").setCachePeriod(3500);
        registry.addResourceHandler("/**").addResourceLocations("classpath:/viewsresource/").setCachePeriod(3500);
        registry.addResourceHandler("/fragments/**").addResourceLocations("classpath:/fragments/").setCachePeriod(3500);
    }


}
