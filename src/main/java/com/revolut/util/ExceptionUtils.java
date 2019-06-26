package com.revolut.util;


public class ExceptionUtils {

    public static Throwable getFromChain(Throwable throwable, Class<?> clazz) {
        return ExceptionUtils.existsInChain(throwable, clazz) ?
                org.apache.commons.lang3.exception.ExceptionUtils.getThrowables(throwable)[org.apache.commons.lang3.exception.ExceptionUtils.indexOfType(throwable, clazz)] :
                throwable;
    }

    private static boolean existsInChain(Throwable throwable, Class<?> clazz) {
        return org.apache.commons.lang3.exception.ExceptionUtils.indexOfType(throwable, clazz) != -1;
    }

}
