package com.hephaestus.infrastructure.exception;

/**
 * @fileName: IllegalityArgumentException.java
 * @description: illegality argument exception
 * @author: huangshimin
 * @date: 2024/6/27 17:59
 */
public class IllegalityArgumentException extends RuntimeException {
    public IllegalityArgumentException() {
    }

    public IllegalityArgumentException(String message) {
        super(message);
    }

    public IllegalityArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
