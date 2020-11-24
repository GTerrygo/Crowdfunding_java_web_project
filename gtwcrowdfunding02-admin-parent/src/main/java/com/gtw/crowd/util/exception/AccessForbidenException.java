package com.gtw.crowd.util.exception;

/**
 * @author
 * @create 2020-10-27-1:25
 */
public class AccessForbidenException extends RuntimeException{

    private static final long serialVersionUID = 1l;

    public AccessForbidenException(String message) {
        super(message);
    }
}
