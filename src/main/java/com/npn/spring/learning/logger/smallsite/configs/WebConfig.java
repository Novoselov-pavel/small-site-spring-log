package com.npn.spring.learning.logger.smallsite.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Класс настройки главного сервелета
 */
@Configuration
public class WebConfig implements WebApplicationInitializer {


    /**
     * Configure the given {@link ServletContext} with any servlets, filters, listeners
     * context-params and attributes necessary for initializing this web application. See
     * examples {@linkplain WebApplicationInitializer above}.
     *
     * @param servletContext the {@code ServletContext} to initialize
     * @throws ServletException if any call against the given {@code ServletContext}
     *                          throws a {@code ServletException}
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Один из вариантов подключения дополнительных классов настроек.
        //Можно использовать аннотацию импорта у класса настроек DispatcherServlet, а можно таким способом:
        //Создаем контекст, в котором мы загружаем настройки
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        //регистрируем классы настроек в контексте
        rootContext.register(BeanConfig.class);
        //Загружаем их в основной контекст
        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(ApplicationConfig.class);
        //Создаем dispatcher, настраиваем и региструруем
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
