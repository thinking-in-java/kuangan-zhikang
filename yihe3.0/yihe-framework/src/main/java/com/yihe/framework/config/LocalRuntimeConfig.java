package com.yihe.framework.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/** Local preview starts the web services without automatic business jobs. */
@Configuration
@Profile("local")
public class LocalRuntimeConfig {
    @Bean
    public static BeanFactoryPostProcessor disableLocalScheduledTasks(Environment environment) {
        return beanFactory -> {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            // A second local web instance must not claim or clean up the shared queue.
            if (environment.getProperty("ehr.local.passive", Boolean.class, false)) {
                for (String name : new String[]{"ocrLeaseReaper", "digitalWorkerAiExecutor"}) {
                    if (registry.containsBeanDefinition(name)) registry.removeBeanDefinition(name);
                }
            }
            String processor = "org.springframework.context.annotation.internalScheduledAnnotationProcessor";
            if (registry.containsBeanDefinition(processor)) {
                registry.removeBeanDefinition(processor);
            }
        };
    }
}
