package com.hephaestus.infrastructure.di.spring;

import com.hephaestus.infrastructure.anno.DependencyInjectionConfiguration;
import com.hephaestus.infrastructure.di.DependencyInjectionContext;
import com.hephaestus.infrastructure.exception.IllegalityArgumentException;
import com.hephaestus.infrastructure.utils.PreconditionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @fileName: SpringDependencyInjectionContext.java
 * @description: dependency injection context based on spring
 * @author: huangshimin
 * @date: 2024/6/27 17:04
 */
@Slf4j
public class SpringDependencyInjectionContext implements DependencyInjectionContext {
    private final Class<?> mainClass;
    private static AnnotationConfigApplicationContext SPRING_ANNO_DI_CONTEXT;

    public SpringDependencyInjectionContext(Class<?> mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * get di application context
     * @return
     */
    public static ApplicationContext getDiApplicationContext() {
        return SPRING_ANNO_DI_CONTEXT;
    }

    @Override
    public void startContainer() {
        DependencyInjectionConfiguration anno =
                PreconditionUtils.checkNotNull(this.mainClass.getAnnotation(DependencyInjectionConfiguration.class),
                        "mainClass should be annotated with @DependencyInjectionConfiguration");
        Class[] scanClasses = anno.scanClasses();
        String[] scanPackageNames = anno.scanPackageNames();
        if (ArrayUtils.isNotEmpty(scanPackageNames) && ArrayUtils.isNotEmpty(scanClasses)) {
            throw new IllegalityArgumentException("scanClasses and scanPackageNames can not be used at the same time");
        }
        if (ArrayUtils.isNotEmpty(scanClasses)) {
            SPRING_ANNO_DI_CONTEXT = new AnnotationConfigApplicationContext(scanClasses);
        }
        if (ArrayUtils.isNotEmpty(scanPackageNames)) {
            SPRING_ANNO_DI_CONTEXT = new AnnotationConfigApplicationContext(scanPackageNames);
        }
        log.info("Spring Dependency Injection Context started");
    }
}
