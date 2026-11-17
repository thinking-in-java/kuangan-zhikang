package com.yihe.framework.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/** Local preview starts the web services without automatic business jobs. */
@Configuration
@Profile("local")
public class LocalRuntimeConfig {
    @Bean
    public static BeanFactoryPostProcessor disableLocalScheduledTasks() {
        return beanFactory -> {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            String processor = "org.springframework.context.annotation.internalScheduledAnnotationProcessor";
            if (registry.containsBeanDefinition(processor)) {
                registry.removeBeanDefinition(processor);
            }
        };
    }
}
