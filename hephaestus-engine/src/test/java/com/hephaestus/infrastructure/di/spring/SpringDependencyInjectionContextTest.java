package com.hephaestus.infrastructure.di.spring;

import com.hephaestus.infrastructure.anno.DependencyInjectionConfiguration;


/**
 * @fileName: SpringDependencyInjectionContextTest.java
 * @description: SpringDependencyInjectionContextTest.java类说明
 * @author: huangshimin
 * @date: 2024/6/27 17:23
 */
@DependencyInjectionConfiguration(scanPackageNames = "com.hephaestus")
public class SpringDependencyInjectionContextTest {
    public static void main(String[] args) {
        // start spring di container
        new SpringDependencyInjectionContext(SpringDependencyInjectionContextTest.class).startContainer();
        // test di service if it is injected
        DiServiceTest diServiceTest = SpringDependencyInjectionContext.getDiApplicationContext()
                .getBean(DiServiceTest.class);
        diServiceTest.say();
    }
}