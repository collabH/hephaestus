package com.hephaestus.infrastructure.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @fileName: DependencyInjectionConfiguration.java
 * @description: Dependency injection annotation configuration
 * @author: huangshimin
 * @date: 2024/6/27 16:59
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DependencyInjectionConfiguration {
    /**
     * scan package names
     *
     * @return package names path array
     */
    String[] scanPackageNames() default {};

    /**
     * scan classes
     *
     * @return class array
     */
    Class[] scanClasses() default {};
}
