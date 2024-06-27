package com.hephaestus.infrastructure.utils;


import com.hephaestus.infrastructure.exception.IllegalityArgumentException;

import javax.annotation.CheckForNull;

/**
 * @fileName: PreConditionUtils.java
 * @description: argument check utils
 * @author: huangshimin
 * @date: 2024/6/27 13:59
 */
public class PreconditionUtils {

    /**
     * check argument is legal or not
     *
     * @param expression true is legal, false is illegal
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalityArgumentException();
        }
    }

    /**
     * check argument is legal or not
     *
     * @param expression   true is legal, false is illegal
     * @param errorMessage error message
     */
    public static void checkArgument(boolean expression, @CheckForNull Object errorMessage) {
        if (!expression) {
            throw new IllegalityArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * check object is null or not
     *
     * @param reference object
     * @param <T>
     * @return valid object
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new IllegalityArgumentException();
        }
        return reference;
    }


    /**
     * check object is null or not
     *
     * @param reference    object
     * @param <T>
     * @param errorMessage error message
     * @return valid object
     */
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new IllegalityArgumentException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
