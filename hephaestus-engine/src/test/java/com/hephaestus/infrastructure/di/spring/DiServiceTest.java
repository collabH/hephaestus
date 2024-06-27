package com.hephaestus.infrastructure.di.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @fileName: DiServiceTest.java
 * @description: di service test
 * @author: huangshimin
 * @date: 2024/6/27 17:23
 */
@Component
@Slf4j
public class DiServiceTest {
    public void say() {
        System.out.println("hello world，my first spring component！");
    }
}
