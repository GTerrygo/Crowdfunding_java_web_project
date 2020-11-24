package com.gtw.crowd.exception;

/**
 * @author
 * @create 2020-10-26-20:43
 */
public class LoginFailedException extends RuntimeException{

    private static final long serialVersionUID = 1l;

    public LoginFailedException(String message) {
        super(message);
    }
}
