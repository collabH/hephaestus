package com.hephaestus.application.container;

import com.hephaestus.infrastructure.anno.DependencyInjectionConfiguration;
import com.hephaestus.infrastructure.di.DependencyInjectionContext;
import com.hephaestus.infrastructure.di.spring.SpringDependencyInjectionContext;

/**
 * @fileName: HephaestusContainer.java
 * @description: hephaestus container
 * @author: huangshimin
 * @date: 2024/6/27 19:34
 */
@DependencyInjectionConfiguration(scanPackageNames = {"com.hephaestus"})
public class HephaestusContainer {
    /**
     * hephaestus container start
     */
    public void startContainer() {
        new SpringDependencyInjectionContext(HephaestusContainer.class).startContainer();
    }
}
